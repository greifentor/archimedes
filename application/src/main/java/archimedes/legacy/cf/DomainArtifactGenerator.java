/*
 * DomainArtifactGenerator.java
 *
 * 31.03.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf;

import archimedes.legacy.model.DataModel;
import archimedes.legacy.model.DomainModel;
import archimedes.model.*;

/**
 * An artifact generator implementation for domain classes.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 31.03.2018 - Added.
 */

public class DomainArtifactGenerator extends AbstractArtifactGenerator<DomainModel> {

    /**
     * Creates a new domain artifact generator for the passed code builder.
     *
     * @param artifactBuilderFactory The artifact builder factory which the artifact generator
     *         is to build for.
     * @throws IllegalArgumentException Passing a null value.
     *
     * @changed OLI 31.03.2018 - Added.
     */
    public DomainArtifactGenerator(ArtifactBuilderFactory<DomainModel> artifactBuilderFactory) {
        super(artifactBuilderFactory);
    }

    /**
     * @changed OLI 01.04.2018 - Added.
     */
    @Override public DomainModel[] getAllObjectsToGenerateCodeFor(DataModel dataModel) {
        return dataModel.getAllDomains();
    }

}