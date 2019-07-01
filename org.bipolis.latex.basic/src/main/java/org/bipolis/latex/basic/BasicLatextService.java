package org.bipolis.latex.basic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bipolis.common.builder.BuildException;
import org.bipolis.latex.api.LatexService;
import org.bipolis.latex.api.TexContent;
import org.bipolis.latex.api.TexResource;
import org.bipolis.latex.api.TexResult;
import org.bipolis.latex.api.TexResultType;
import org.bipolis.latex.basic.model.BasicTexResource;
import org.bipolis.latex.basic.model.BasicTexResult;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

@Component(service = LatexService.class)
public class BasicLatextService implements LatexService {

  private static final String RERUN_TO_GET = "Rerun to get ";

  @Reference(service = LoggerFactory.class)
  Logger logger;

  final String latexProg = "pdflatex";
  // "/usr/local/texlive/2018/bin/x86_64-linux/pdflatex";
  // depends on type

  private String version = null;

  @Activate
  private void activate() {

    final String paramVersion = " -v";
    final String latexGetVersionCommand = latexProg + paramVersion;
    CommandResult result;
    try {
      result = runCommand(latexGetVersionCommand, 1, TimeUnit.SECONDS, null);
    } catch (final IOException e) {

      e.printStackTrace();
      throw new RuntimeException("Bad or no Latex Version");

    } catch (final InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Bad or no Latex Version");

    }

    if (result.hasErrors()) {
      throw new RuntimeException("Bad or no Latex Version");
    }
    if (!result.hasOuts()) {
      throw new RuntimeException("Bad or no Latex Version");
    }
    version = result.getOuts();
  }

  @Override
  public TexResult createDocument(TexContent texContent, TexResultType type) {

    boolean draftMode = false;
    String format = null;

    switch (type) {
      case PDF:
        draftMode = false;
        format = "pdf";
        break;

      case DVI:
        draftMode = false;
        format = "dvi";
        break;

      case SRC_ZIP:
        draftMode = true;
        format = "pdf";
        break;
      default:
        throw new IllegalArgumentException("unknown TexResultType: " + type);

    }
    try {
      final Path basePath = Files.createTempDirectory("texContent");

      final Path basePathIn = Files.createDirectory(basePath.resolve("in"));

      final Path basePathOut = Files.createDirectory(basePath.resolve("out"));

      try {

        writeTexContent(basePathIn, texContent);

        final String paramSynctex = " -synctex=1";
        final String paramInteraction = " -interaction=nonstopmode";
        final String paramOutputDir = " -output-directory="
            + basePathOut.toAbsolutePath().toString();
        final File execDirectory = Paths.get(basePathIn.toAbsolutePath().toString()).toFile();

        final String paramOutputFormat = " -output-format=" + format;

        final String paramDraftMode = " -draftmode";

        final String paramShellEscape = " --shell-escape";
        // externe programme eps svg starten
        
        final Path mainTexFilePath = basePathIn.resolve(texContent.getMainTexFile().getName())
            .toAbsolutePath();

        String params = paramSynctex + paramShellEscape + paramInteraction + paramOutputFormat
            + paramOutputDir;

        if (draftMode) {
          params += paramDraftMode;
        }
        final String command = latexProg + " " + params + " " + mainTexFilePath;

        System.out.println(command);
        CommandResult commandResult = null;

        int compileCounter = 0;
        final int maxCompileCounter = 50;
        while ((commandResult = runCommand(command, 10, TimeUnit.SECONDS, execDirectory)).getOuts()
            .contains(RERUN_TO_GET)) {
          compileCounter++;
          if (compileCounter > maxCompileCounter) {
            logger.error("Return command to often: " + commandResult.getOuts() + " "
                + commandResult.getErrors());
            break;
          }
        }

        System.out.println(commandResult.getOuts());

        String plainName = texContent.getMainTexFile().getName();
        plainName = plainName.substring(0, plainName.lastIndexOf(".tex"));
        String name = null;
        byte[] content = null;
        switch (type) {
          case PDF:
            name = plainName + ".pdf";
            content = Files.readAllBytes(basePathOut.resolve(name));
            break;

          case DVI:
            name = plainName + ".dvi";
            content = Files.readAllBytes(basePathOut.resolve(name));
            break;

          case SRC_ZIP:
            name = plainName + ".zip";
            content = zipFolder(basePathIn);
            break;

          default:
            throw new IllegalArgumentException("unknown TexResultType: " + type);
        }
        final String logFileName = plainName + ".log";
        final byte[] logContent = Files.readAllBytes(basePathOut.resolve(logFileName));
        final String logMessage = new String(logContent, StandardCharsets.UTF_8);

        final BasicTexResource output = BasicTexResource.builder().name(name)
            .content(ByteBuffer.wrap(content)).build();

        final BasicTexResult basicTexResult = BasicTexResult.builder().output(output)
            .logMessage(logMessage).build();

        // start remove
        final Path pathOutMain = basePath.resolve(name);
        Files.write(pathOutMain, content, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);

        Runtime.getRuntime().exec("evince " + pathOutMain.toAbsolutePath().toString());

        Runtime.getRuntime().exec("nautilus " + basePath.toAbsolutePath().toString());
        // stop remove
        return basicTexResult;
      } catch (UncheckedIOException | InterruptedException | BuildException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } catch (final IOException e) {
      e.printStackTrace();
    }
    return null;

  }

  private CommandResult runCommand(final String command, int time, TimeUnit timeUnit,
      File execDirectory) throws IOException, InterruptedException {
    final Runtime r = Runtime.getRuntime();

    logger.info(command);
    final Process p = r.exec(command, null, execDirectory);
    p.waitFor(time, timeUnit);
    final String errors = new String(p.getErrorStream().readAllBytes());
    final String outs = new String(p.getInputStream().readAllBytes());
    p.destroy();
    return new CommandResult(outs, errors);
  }

  private static byte[] zipFolder(Path basePathIn) {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (ZipOutputStream zos = new ZipOutputStream(baos)) {

      Files.walk(basePathIn).skip(0).filter(Files::isRegularFile).forEach(p -> {

        final ZipEntry entry = new ZipEntry(basePathIn.relativize(p).toString());

        try {
          zos.putNextEntry(entry);
          zos.write(Files.readAllBytes(p));
        } catch (final IOException e) {
          throw new UncheckedIOException(e);
        }

      });
      zos.closeEntry();

    } catch (UncheckedIOException | IOException e) {
      e.printStackTrace();
    }
    return baos.toByteArray();
  }

  private static void writeTexContent(Path basePath, TexContent texContent)
      throws IOException, UncheckedIOException {
    writeFile(basePath, texContent.getMainTexFile());

    if (texContent.getTexResources() != null) {
      texContent.getTexResources().stream().parallel().forEach(texRessource -> {
        try {
          writeFile(basePath, texRessource);
        } catch (final IOException e) {
          throw new UncheckedIOException(e);
        }
      });
    }
  }

  private static void writeFile(Path basePath, TexResource texRessource) throws IOException {
    final Path file = basePath.resolve(texRessource.getName());
    Files.write(file, texRessource.getContent().flip().array(), StandardOpenOption.CREATE_NEW,
        StandardOpenOption.WRITE);

  }

  @Override
  public String getTexVersion() {

    return version;
  }

}
