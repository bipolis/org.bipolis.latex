package org.bipolis.latex.api;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface TexFile extends TexResource {

	String FILE_EXTENSION = "tex";

}
