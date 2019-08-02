package org.bipolis.latex;

import java.util.Arrays;

import org.bipolis.latex.api.TexResultType;
import org.bipolis.latex.basic.model.BasicTexContent;
import org.bipolis.latex.basic.model.BasicTexFile;
import org.bipolis.latex.basic.model.BasicTexResource;
import org.bipolis.latex.basic.service.BasicLatextService;
import org.junit.jupiter.api.Test;

public class LatextServiceTest {

	@Test
	public void testName() {

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
		final BasicLatextService basicLatextService = new BasicLatextService();
		basicLatextService.bindLogger(new MockLogger());
		basicLatextService.activate();
		// asserts
		basicLatextService.createDocument(basicTexContent, TexResultType.DVI);
		basicLatextService.createDocument(basicTexContent, TexResultType.PDF);
		basicLatextService.createDocument(basicTexContent, TexResultType.SRC_ZIP);
	}
}
