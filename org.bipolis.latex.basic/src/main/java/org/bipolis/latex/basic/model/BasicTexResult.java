package org.bipolis.latex.basic.model;

import javax.annotation.processing.Generated;

import org.bipolis.latex.api.TexResource;
import org.bipolis.latex.api.TexResult;

public class BasicTexResult implements TexResult {

  TexResource output;
  String logMessage;

  @Generated("SparkTools")
  private BasicTexResult(Builder builder) {
    output = builder.output;
    logMessage = builder.logMessage;
  }

  @Override
  public TexResource getOutput() {
    return output;
  }

  @Override
  public String getLogMessage() {
    return logMessage;
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

  /**
   * Builder to build {@link BasicTexResult}.
   */
  @Generated("SparkTools")
  public static final class Builder {
    private TexResource output;
    private String logMessage;

    private Builder() {
    }

    public Builder output(TexResource output) {
      this.output = output;
      return this;
    }

    public Builder logMessage(String logMessage) {
      this.logMessage = logMessage;
      return this;
    }

    public BasicTexResult build() {
      return new BasicTexResult(this);
    }
  }

}
