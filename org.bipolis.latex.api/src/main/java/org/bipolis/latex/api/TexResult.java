package org.bipolis.latex.api;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface TexResult {

	String getLogMessage();

	TexResource getOutput();

}
