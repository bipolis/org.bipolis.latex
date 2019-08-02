package org.bipolis.latex;

import java.nio.ByteBuffer;

public interface TestContent {

	String TEX_FILE_NAME_VALID = "validTexFileName.tex";
	String TEX_FILE_NAME_INVALID = "inValidTexFileName.xet";

	String TEX_RESOURCE_NAME_VALID = "TexResourceName";
	String TEX_RESOURCE_NAME_INVALID = " ";
	String TEX_RESOURCE_NAME_VALID_TEX = "TexResourceName.tex";
	String TEX_RESOURCE_NAME_VALID_SVG = "TexResourceName.svg";

	ByteBuffer CONTENT_TEX_VALID_SIMPLE = ByteBuffer.wrap(("\\documentclass{article} \n" + //
			"\\begin{document} \n" + //
			"test \n" + //
			"\\end{document}").getBytes());

	ByteBuffer CONTENT_SVG_VALID_SIMPLE = ByteBuffer
			.wrap(("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n" + //
					"<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 20001102//EN\"\n" + //
					" \"http://www.w3.org/TR/2000/CR-SVG-20001102/DTD/svg-20001102.dtd\">\n" + //
					"\n" + //
					"<svg width=\"100%\" height=\"100%\">\n" + //
					"  <g transform=\"translate(50,50)\">\n" + //
					"    <rect x=\"0\" y=\"0\" width=\"150\" height=\"50\" style=\"fill:red;\" />\n" + //
					"  </g>\n" + //
					"\n" + //
					"</svg>").getBytes());

}
