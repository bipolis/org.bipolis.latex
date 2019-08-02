package org.bipolis.latex.basic.service;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.bipolis.common.builder.BuildException;
import org.bipolis.common.runtime.CommandResult;
import org.bipolis.common.runtime.RuntimeUtil;
import org.bipolis.latex.api.LatexService;
import org.bipolis.latex.api.TexContent;
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

	private Logger logger;

	final String latexProg = "pdflatex";
	// "/usr/local/texlive/2018/bin/x86_64-linux/pdflatex";
	// depends on type

	private String version = null;

	@Activate
	public void activate() {

		final String paramVersion = " -v";
		final String latexGetVersionCommand = latexProg + paramVersion;
		CommandResult result;
		try {
			result = RuntimeUtil.runCommand(latexGetVersionCommand, 1, TimeUnit.SECONDS);
		} catch (final IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Bad or no Latex Version", e);
		} catch (final InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException("Bad or no Latex Version", e);
		}
		if (result.hasErrors()) {
			throw new RuntimeException("Bad or no Latex Version " + result.getErrors());
		}
		if (!result.hasOuts()) {
			throw new RuntimeException("Bad or no Latex Version " + "Tex Output is missing");
		}
		version = new String(result.getOuts());
	}

	@Reference(service = LoggerFactory.class)
	public void bindLogger(Logger logger) {

		this.logger = logger;
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
				Util.writeTexContent(basePathIn, texContent);
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
				while ((commandResult = RuntimeUtil.runCommand(command, execDirectory, 10,
						TimeUnit.SECONDS)).getOutsAsString().contains(RERUN_TO_GET)) {
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
						content = Util.zipFolder(basePathIn);
						break;
					default:
						throw new IllegalArgumentException("unknown TexResultType: " + type);
				}
				final String logFileName = plainName + ".log";
				final byte[] logContent = Files.readAllBytes(basePathOut.resolve(logFileName));
				final String logMessage = new String(logContent, StandardCharsets.UTF_8);
				final BasicTexResource output = BasicTexResource.builder()
						.name(name)
						.content(ByteBuffer.wrap(content))
						.build();
				final BasicTexResult basicTexResult = BasicTexResult.builder()
						.output(output)
						.logMessage(logMessage)
						.build();
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

	@Override
	public String getTexVersion() {

		return version;
	}
}
