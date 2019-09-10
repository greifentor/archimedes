/*
 * ArtifactBuilderFactory.java
 *
 * 01.04.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf;

import archimedes.legacy.model.DataModel;
import archimedes.model.*;

import gengen.*;

/**
 * A factory for artifact builders.
 *
 * @param <T> The type of objects which the artifact builder factory is to create for.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 01.04.2018 - Added.
 */

public interface ArtifactBuilderFactory<T> {

    /**
     * Returns a new artifact builder for the passed parameters.
     *
     * @param o The object which the code is to create for.
     * @param dataModel The data model which the code is to create for.
     * @param ip The individual preferences of the code generation process.
     *
     * @changed OLI 01.04.2018 - Added.
     */
    abstract public ArtifactBuilder createBuilder(T o, DataModel dm, IndividualPreferences ip);

    /**
     * Returns a name for the artifact builder.
     *
     * @return A name for the artifact builder.
     *
     * @changed OLI 03.04.2018 - Added.
     */
    abstract public String getName();

}