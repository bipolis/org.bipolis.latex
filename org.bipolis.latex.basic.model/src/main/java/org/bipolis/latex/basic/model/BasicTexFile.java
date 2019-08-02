package org.bipolis.latex.basic.model;

import java.nio.ByteBuffer;

import javax.annotation.processing.Generated;

import org.bipolis.common.builder.BuilderValidator;
import org.bipolis.latex.api.TexFile;

public class BasicTexFile implements TexFile {

	/**
	 * Builder to build {@link BasicTexFile}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String name;
		private ByteBuffer content;

		private Builder() {
		}

		/**
		 * Builds the.
		 *
		 * @return the basic tex file
		 */
		public BasicTexFile build() {
			BuilderValidator.notNull("name", name);
			BuilderValidator.notEmpty("name", name, true);
			BuilderValidator.matchPattern("name", name, ".*." + TexFile.FILE_EXTENSION);
			BuilderValidator.notNull("content", content);
			return new BasicTexFile(this);
		}

		public Builder content(ByteBuffer content) {
			this.content = content;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}
	}

	/**
	 * Creates builder to build {@link BasicTexFile}.
	 *
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	private final String name;

	private final ByteBuffer content;

	@Generated("SparkTools")
	private BasicTexFile(Builder builder) {
		name = builder.name;
		content = builder.content;
	}

	@Override
	public ByteBuffer getContent() {

		return content;
	}

	@Override
	public String getName() {

		return name;
	}

}
