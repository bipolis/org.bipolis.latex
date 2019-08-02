package org.bipolis.latex.basic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bipolis.common.builder.BuildException;
import org.bipolis.latex.api.TexResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicTexRessourceTest {

	@Test
	public void testContentNull() {

		Assertions.assertThrows(BuildException.class, () -> {
			BasicTexResource.builder().name(TestContent.TEX_RESOURCE_NAME_VALID).content(null).build();
		});
	}

	@Test
	public void testNameNull() {

		Assertions.assertThrows(BuildException.class, () -> {
			BasicTexResource.builder().name(null).content(TestContent.CONTENT_TEX_VALID_SIMPLE).build();
		});
	}

	@Test
	public void testNamePattern() {

		Assertions.assertThrows(BuildException.class, () -> {
			BasicTexResource.builder()
			.name(TestContent.TEX_RESOURCE_NAME_INVALID)
			.content(TestContent.CONTENT_TEX_VALID_SIMPLE)
			.build();
		});
	}

	@Test
	public void testTexResource() {

		final BasicTexResource basicTexRessource = BasicTexResource.builder()
				.name(TestContent.TEX_RESOURCE_NAME_VALID_TEX)
				.content(TestContent.CONTENT_TEX_VALID_SIMPLE)
				.build();
		assertNotNull(basicTexRessource);
		// TexResource
		final TexResource texRes = basicTexRessource;
		assertEquals(TestContent.TEX_RESOURCE_NAME_VALID_TEX, texRes.getName());
		assertEquals(TestContent.CONTENT_TEX_VALID_SIMPLE, texRes.getContent());
	}
}
