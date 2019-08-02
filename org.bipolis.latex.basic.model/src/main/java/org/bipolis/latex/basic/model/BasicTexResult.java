package org.bipolis.latex.basic.model;

import javax.annotation.processing.Generated;

import org.bipolis.common.builder.BuilderValidator;
import org.bipolis.latex.api.TexResource;
import org.bipolis.latex.api.TexResult;

public class BasicTexResult implements TexResult {

	/**
	 * Builder to build {@link BasicTexResult}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private TexResource output;
		private String logMessage;

		private Builder() {
		}

		public BasicTexResult build() {
			BuilderValidator.notNull("output", output);
			return new BasicTexResult(this);
		}

		public Builder logMessage(String logMessage) {
			this.logMessage = logMessage;
			return this;
		}

		public Builder output(TexResource output) {
			this.output = output;
			return this;
		}
	}

	/**
	 * Creates builder to build {@link BasicTexResult}.
	 *
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	TexResource output;

	String logMessage;

	@Generated("SparkTools")
	private BasicTexResult(Builder builder) {
		output = builder.output;
		logMessage = builder.logMessage;
	}

	@Override
	public String getLogMessage() {
		return logMessage;
	}

	@Override
	public TexResource getOutput() {
		return output;
	}

}
