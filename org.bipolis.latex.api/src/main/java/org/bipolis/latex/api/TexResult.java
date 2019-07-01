package org.bipolis.latex.api;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface TexResult {

  TexResource getOutput();

  String getLogMessage();
}
