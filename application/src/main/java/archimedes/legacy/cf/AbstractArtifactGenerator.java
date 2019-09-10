/*
 * AbstractArtifactGenerator.java
 *
 * 01.04.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf;

import archimedes.cf.gui.*;
import archimedes.legacy.cf.gui.ProgressFrame;
import archimedes.legacy.model.DataModel;
import archimedes.legacy.model.DomainModel;
import archimedes.legacy.model.TableModel;
import archimedes.model.*;

import static corentx.util.Checks.*;

import gengen.*;

import java.util.*;

/**
 * A super class for artifact generators.
 * 
 * <T> The type of objects which the artifact generator is working for.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 01.04.2018 - Added.
 */

abstract public class AbstractArtifactGenerator<T> implements ArtifactGenerator<T> {

    private ArtifactBuilderFactory<T> artifactBuilderFactory = null;
    private boolean temporarilySuspended = false;

    /**
     * Creates a new artifact generator for the passed code builder.
     *
     * @param artifactBuilderFactory The artifact builder factory which the artifact generator
     *         is to build for.
     * @param ip The individual preferences for the code generation process.
     * @throws IllegalArgumentException Passing a null value.
     */
    public AbstractArtifactGenerator(ArtifactBuilderFactory<T> artifactBuilderFactory) {
        super();
        ensure(artifactBuilderFactory != null, "artifact builder factory cannot be null.");
        this.artifactBuilderFactory = artifactBuilderFactory;
    }

    @Override public void generate(DataModel dm, List<Artifact> artifacts,
            ProgressFrame progressFrame, IndividualPreferences ip,
            ObjectsToGenerateCollection otgc) {
        T[] os = getAllObjectsToGenerateCodeFor(dm);
        progressFrame.resetObjects(os.length);
        for (T o : os) {
            if (otgc.contains(o)) {
                progressFrame.setCommentObjects(getObjectsName(o));
                ArtifactBuilder<T> ab = this.artifactBuilderFactory.createBuilder(o, dm, ip); 
                if (!ab.isSuppressCodeGeneration()) {
                    Artifact a = ab.build();
                    artifacts.add(a);
                }
            }
            progressFrame.incrementObjects();
        }
    }

    private String getObjectsName(Object o) {
        if (o instanceof ArtifactGenerator) {
            return ((ArtifactGenerator<?>) o).getName();
        } else if (o instanceof DomainModel) {
            return ((DomainModel) o).getName();
        } else if (o instanceof TableModel) {
            return ((TableModel) o).getName();
        }
        return o.toString();
    }

    /**
     * Returns an array with the object to generate artifacts for.
     *
     * @param dataModel The data model which the objects are to take for.
     * @return An array with the object to generate artifacts for.
     */
    abstract public T[] getAllObjectsToGenerateCodeFor(DataModel dm);

    @Override public String getName() {
        return this.artifactBuilderFactory.getName();
    }

    @Override public boolean isDeprecated() {
        return false;
    }

    @Override public boolean isTemporarilySuspended() {
        return this.temporarilySuspended;
    }

    @Override public void setTemporarilySuspended(boolean temporarilySuspended) {
        this.temporarilySuspended = temporarilySuspended;
    }

}