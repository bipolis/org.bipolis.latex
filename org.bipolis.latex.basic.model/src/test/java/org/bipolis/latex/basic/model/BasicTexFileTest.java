package org.bipolis.latex.basic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bipolis.common.builder.BuildException;
import org.bipolis.latex.api.TexFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicTexFileTest {

	@Test
	public void testContentNull() {

		Assertions.assertThrows(BuildException.class, () -> {
			BasicTexFile.builder().name(TestContent.TEX_FILE_NAME_VALID).content(null).build();
		});
	}

	@Test
	public void testNameNull() {

		Assertions.assertThrows(BuildException.class, () -> {
			BasicTexFile.builder().name(null).content(TestContent.CONTENT_TEX_VALID_SIMPLE).build();
		});
	}

	@Test
	public void testNamePattern() {

		Assertions.assertThrows(BuildException.class, () -> {
			BasicTexFile.builder()
			.name(TestContent.TEX_FILE_NAME_INVALID)
			.content(TestContent.CONTENT_TEX_VALID_SIMPLE)
			.build();
		});
	}

	@Test
	public void testTexFile() {

		final BasicTexFile basicTexFile = BasicTexFile.builder()
				.name(TestContent.TEX_FILE_NAME_VALID)
				.content(TestContent.CONTENT_TEX_VALID_SIMPLE)
				.build();
		assertNotNull(basicTexFile);
		// TexFile
		final TexFile texFile = basicTexFile;
		assertEquals(TestContent.TEX_FILE_NAME_VALID, texFile.getName());
		assertEquals(TestContent.CONTENT_TEX_VALID_SIMPLE, texFile.getContent());
	}
}
