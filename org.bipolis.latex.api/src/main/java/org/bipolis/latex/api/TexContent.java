package org.bipolis.latex.api;

import java.util.Collection;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface TexContent {

	TexFile getMainTexFile();

	Collection<TexResource> getTexResources();
}
