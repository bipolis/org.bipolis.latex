package org.bipolis.latex.basic.model;

import java.nio.ByteBuffer;

import javax.annotation.processing.Generated;

import org.bipolis.latex.api.TexResource;

public class BasicTexResource implements TexResource {

  private final String name;
  private final ByteBuffer content;

  @Generated("SparkTools")
  private BasicTexResource(Builder builder) {
    this.name = builder.name;
    this.content = builder.content;
  }

  @Override
  public String getName() {

    return name;
  }

  @Override
  public ByteBuffer getContent() {
    return content;
  }

  /**
   * Creates builder to build {@link BasicTexResource}.
   * 
   * @return created builder
   */
  @Generated("SparkTools")
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder to build {@link BasicTexResource}.
   */
  @Generated("SparkTools")
  public static final class Builder {
    private String name;
    private ByteBuffer content;

    private Builder() {
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder content(ByteBuffer content) {
      this.content = content;
      return this;
    }

    public BasicTexResource build() {
      return new BasicTexResource(this);
    }
  }

}
