/**
 *
 */
package org.bipolis.latex.basic.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bipolis.latex.api.TexContent;
import org.bipolis.latex.api.TexResource;

/**
 * @author stbischof
 *
 */
public class Util {

	public static void writeFile(Path basePath, TexResource texRessource) throws IOException {

		final Path file = basePath.resolve(texRessource.getName());
		Files.write(file, texRessource.getContent().flip().array(), StandardOpenOption.CREATE_NEW,
				StandardOpenOption.WRITE);
	}

	public static void writeTexContent(Path basePath, TexContent texContent)
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

	public static byte[] zipFolder(Path basePathIn) {

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
}
