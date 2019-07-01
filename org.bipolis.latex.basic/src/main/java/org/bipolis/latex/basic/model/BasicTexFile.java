package org.bipolis.latex.basic.model;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.processing.Generated;

import org.bipolis.latex.api.TexFile;
import org.bipolis.latex.api.TexResource;

public class BasicTexFile implements TexFile {

  private final String name;
  private final ByteBuffer content;

  @Generated("SparkTools")
  private BasicTexFile(Builder builder) {
    name = builder.name;
    content = builder.content;
  }

  @Override
  public String getName() {

    return name;
  }

  @Override
  public ByteBuffer getContent() {

    return content;
  }

  @Override
  public TexFile getMainTexFile() {

    return this;
  }

  @Override
  public Collection<TexResource> getTexResources() {

    return Collections.emptyList();
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

  /**
   * Builder to build {@link BasicTexFile}.
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

    public BasicTexFile build() {
      return new BasicTexFile(this);
    }
  }
}
