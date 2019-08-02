package org.bipolis.latex.api;

import java.nio.ByteBuffer;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface TexResource {

	String getName();

	ByteBuffer getContent();
}
