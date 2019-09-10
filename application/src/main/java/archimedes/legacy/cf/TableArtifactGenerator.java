/*
 * TableArtifactGenerator.java
 *
 * 01.04.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf;

import archimedes.legacy.model.DataModel;
import archimedes.legacy.model.TableModel;
import archimedes.model.*;

/**
 * An artifact generator implementation for table classes.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 01.04.2018 - Added.
 */

public class TableArtifactGenerator extends AbstractArtifactGenerator<TableModel> {

    /**
     * Creates a new table artifact generator for the passed code builder.
     *
     * @param artifactBuilderFactory The artifact builder factory which the artifact generator
     *         is to build for.
     * @throws IllegalArgumentException Passing a null value.
     *
     * @changed OLI 01.04.2018 - Added.
     */
    public TableArtifactGenerator(ArtifactBuilderFactory<TableModel> artifactBuilderFactory) {
        super(artifactBuilderFactory);
    }

    /**
     * @changed OLI 01.04.2018 - Added.
     */
    @Override public TableModel[] getAllObjectsToGenerateCodeFor(DataModel dataModel) {
        return dataModel.getTables();
    }

}