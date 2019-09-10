/*
 * ObjectsToGenerateCollection.java
 *
 * 19.04.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf;

import java.util.*;

/**
 * A container which manages the objects which are selected for code generation.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 19.04.2018 - Added.
 */

public class ObjectsToGenerateCollection {

    private Map<Object, Object> objects = new HashMap<Object, Object>();

    /**
     * Add the passed object to the collection of objects which is the code to generate for.
     *
     * @param o The object to add to the collection.
     */
    public void add(Object o) {
       if (o != null) {
           this.objects.put(o, o);
       }
    }

    /**
     * Checks if the passed object is contained by the collection which is the code to generate
     * for.
     *
     * @param o The object which is to check for to be contained by the collection.
     * @return "true" if the passed object is contained by the collection of object which the
     *         code is to generate for.
     */
    public boolean contains(Object o) {
        return this.objects.get(o) != null;
    }

}