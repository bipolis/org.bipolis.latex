/**
 *
 */

package org.bipolis.latex.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.bipolis.latex.api.LatexService;
import org.bipolis.latex.api.TexContent;
import org.bipolis.latex.api.TexFile;
import org.bipolis.latex.api.TexResource;
import org.bipolis.latex.api.TexResultType;
import org.bipolis.latex.basic.model.BasicTexContent;
import org.bipolis.latex.basic.model.BasicTexFile;
import org.bipolis.latex.basic.model.BasicTexResource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletMultipart;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

// TODO: Auto-generated Javadoc
/**
 * The Class LatexServlet.
 *
 * @author stbischof
 */
@Component(service = Servlet.class)
@HttpWhiteboardServletPattern("/latex")
@HttpWhiteboardServletMultipart(maxFileSize = 500000)
public class LatexServlet extends HttpServlet {

	/** The Constant QUERY_PARAM_RESULT_TYPE. */
	private static final String QUERY_PARAM_RESULT_TYPE = "resultType";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The latex service. */
	@Reference()
	private LatexService latexService;

	/**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final Map<String, String[]> paramMap = request.getParameterMap();

		final TexResultType type = paramMap.containsKey(QUERY_PARAM_RESULT_TYPE)
				? TexResultType.valueOf(QUERY_PARAM_RESULT_TYPE)
				: TexResultType.PDF;

		final Collection<Part> parts = request.getParts();

		TexFile mainTexFile = null;
		final List<TexResource> texRessources = new ArrayList<>();
		for (final Part part : parts) {
			System.out.printf("File %s, %s, %d%n", part.getName(), part.getContentType(), part.getSize());

			try (InputStream is = part.getInputStream()) {

				if (mainTexFile == null) {
					mainTexFile = BasicTexFile.builder().name(part.getName())
							.content(ByteBuffer.wrap(part.getInputStream().readAllBytes())).build();
				} else {
					texRessources.add(BasicTexResource.builder().name(part.getName())
							.content(ByteBuffer.wrap(part.getInputStream().readAllBytes())).build());
				}

			}
		}
		final TexContent texContent = BasicTexContent.builder().mainTexFile(mainTexFile)
				.texRessources(texRessources).build();

		latexService.createDocument(texContent, type);
	}

}
