package org.bipolis.latex.basic.model;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.processing.Generated;

import org.bipolis.latex.api.TexContent;
import org.bipolis.latex.api.TexFile;
import org.bipolis.latex.api.TexResource;

public class BasicTexContent implements TexContent {

  private final TexFile mainTexFile;
  private final Collection<TexResource> texRessources;

  @Generated("SparkTools")
  private BasicTexContent(Builder builder) {
    mainTexFile = builder.mainTexFile;
    texRessources = builder.texRessources;
  }

  @Override
  public TexFile getMainTexFile() {
    return mainTexFile;
  }

  @Override
  public Collection<TexResource> getTexResources() {

    return texRessources;
  }

  /**
   * Creates builder to build {@link BasicTexContent}.
   * 
   * @return created builder
   */
  @Generated("SparkTools")
  public static IMainTexFileStage builder() {
    return new Builder();
  }

  @Generated("SparkTools")
  public interface IMainTexFileStage {
    public IBuildStage mainTexFile(TexFile mainTexFile);
  }

  @Generated("SparkTools")
  public interface IBuildStage {
    public IBuildStage texRessources(Collection<TexResource> texRessources);

    public BasicTexContent build();
  }

  /**
   * Builder to build {@link BasicTexContent}.
   */
  @Generated("SparkTools")
  public static final class Builder implements IMainTexFileStage, IBuildStage {
    private TexFile mainTexFile;
    private Collection<TexResource> texRessources = Collections.emptyList();

    private Builder() {
    }

    @Override
    public IBuildStage mainTexFile(TexFile mainTexFile) {
      this.mainTexFile = mainTexFile;
      return this;
    }

    @Override
    public IBuildStage texRessources(Collection<TexResource> texRessources) {
      this.texRessources = texRessources;
      return this;
    }

    @Override
    public BasicTexContent build() {
      return new BasicTexContent(this);
    }
  }

}
