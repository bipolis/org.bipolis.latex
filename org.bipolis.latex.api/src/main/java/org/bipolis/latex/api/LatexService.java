package org.bipolis.latex.api;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface LatexService {

  TexResult createDocument(TexContent content, TexResultType type);

  String getTexVersion();

}
