/*
 * ClassNameGenerator.java
 *
 * 08.04.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf;


/**
 * An interface which should be implemented by class which are able to generate class names.
 *
 * @param <T> The type which the class is to generate for.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 08.04.2018 - Added.
 */

public interface ClassNameGenerator<T> {

    /**
     * Returns a class name for the passed object.
     *
     * @param o The object which the class name is to generate for.
     * @return A class name for the passed object.
     * @throws IllegalArgumentException Passing a null value.
     */
    abstract public String getClassName(T o) throws IllegalArgumentException;

}