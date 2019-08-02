package org.bipolis.latex.basic.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.processing.Generated;

import org.bipolis.common.builder.BuildException;
import org.bipolis.common.builder.BuilderValidator;
import org.bipolis.latex.api.TexContent;
import org.bipolis.latex.api.TexFile;
import org.bipolis.latex.api.TexResource;

public class BasicTexContent implements TexContent {

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
		public BasicTexContent build() {
			BuilderValidator.notNull("mainTexFile", mainTexFile);

			final List<String> list = new ArrayList<>();
			list.add(mainTexFile.getName().trim());

			if (texRessources != null) {
				for (final TexResource texResource : texRessources) {
					if (list.contains(texResource.getName().trim())) {
						throw new BuildException("Multiple Files with same name: " + texResource.getName());
					} else {
						list.add(texResource.getName());
					}

				}
			}

			return new BasicTexContent(this);
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
	}

	@Generated("SparkTools")
	public interface IBuildStage {
		BasicTexContent build();

		IBuildStage texRessources(Collection<TexResource> texRessources);
	}

	@Generated("SparkTools")
	public interface IMainTexFileStage {
		IBuildStage mainTexFile(TexFile mainTexFile);
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

}
