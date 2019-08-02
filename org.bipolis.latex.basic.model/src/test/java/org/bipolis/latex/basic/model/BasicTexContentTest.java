package org.bipolis.latex.basic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.bipolis.common.builder.BuildException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicTexContentTest {

	@Test
	public void testDouplicatResNames1() {

		Assertions.assertThrows(BuildException.class, () -> {
			final BasicTexFile basicTexFile = BasicTexFile.builder()
					.name(TestContent.TEX_FILE_NAME_VALID)
					.content(TestContent.CONTENT_TEX_VALID_SIMPLE)
					.build();
			final BasicTexResource basicTexResourceSameName = BasicTexResource.builder()
					.name(TestContent.TEX_FILE_NAME_VALID)
					.content(ByteBuffer.wrap("1".getBytes()))
					.build();
			BasicTexContent.builder()
			.mainTexFile(basicTexFile)
			.texRessources(Arrays.asList(basicTexResourceSameName))
			.build();
		});
	}

	@Test
	public void testDouplicatResNames2() {

		Assertions.assertThrows(BuildException.class, () -> {
			final BasicTexFile basicTexFile = BasicTexFile.builder()
					.name(TestContent.TEX_FILE_NAME_VALID)
					.content(TestContent.CONTENT_TEX_VALID_SIMPLE)
					.build();
			final BasicTexResource basicTexResourceSameName1 = BasicTexResource.builder()
					.name(TestContent.TEX_RESOURCE_NAME_VALID_SVG)
					.content(ByteBuffer.wrap("1".getBytes()))
					.build();
			final BasicTexResource basicTexResourceSameName2 = BasicTexResource.builder()
					.name(TestContent.TEX_RESOURCE_NAME_VALID_SVG)
					.content(ByteBuffer.wrap("2".getBytes()))
					.build();
			BasicTexContent.builder()
			.mainTexFile(basicTexFile)
			.texRessources(Arrays.asList(basicTexResourceSameName1, basicTexResourceSameName2))
			.build();
		});
	}

	@Test
	public void testMainTexFile() {

		final BasicTexFile basicTexFile = BasicTexFile.builder()
				.name(TestContent.TEX_FILE_NAME_VALID)
				.content(TestContent.CONTENT_TEX_VALID_SIMPLE)
				.build();
		final BasicTexContent basicTexContent = BasicTexContent.builder()
				.mainTexFile(basicTexFile)
				.build();
		// asserts
		assertNotNull(basicTexContent);
		assertEquals(basicTexFile, basicTexContent.getMainTexFile());
		assertNotNull(basicTexContent.getTexResources());
		assertEquals(0, basicTexContent.getTexResources().size());
	}

	@Test
	public void testMainTexFileNull() {

		Assertions.assertThrows(BuildException.class, () -> {
			BasicTexContent.builder().mainTexFile(null).build();
		});
	}

	@Test
	public void testMainTexFileWithRes() {

		final BasicTexFile basicTexFile = BasicTexFile.builder()
				.name(TestContent.TEX_FILE_NAME_VALID)
				.content(TestContent.CONTENT_TEX_VALID_SIMPLE)
				.build();
		final BasicTexResource basicTexResource = BasicTexResource.builder()
				.name(TestContent.TEX_RESOURCE_NAME_VALID_SVG)
				.content(TestContent.CONTENT_SVG_VALID_SIMPLE)
				.build();
		final BasicTexContent basicTexContent = BasicTexContent.builder()
				.mainTexFile(basicTexFile)
				.texRessources(Arrays.asList(basicTexResource))
				.build();
		// asserts
		assertNotNull(basicTexContent);
		assertEquals(basicTexFile, basicTexContent.getMainTexFile());
		assertNotNull(basicTexContent.getTexResources());
		assertEquals(1, basicTexContent.getTexResources().size());
		assertEquals(basicTexResource, basicTexContent.getTexResources().stream().findFirst().get());
	}
}
