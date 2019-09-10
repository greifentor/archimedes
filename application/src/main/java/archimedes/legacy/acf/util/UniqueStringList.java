/*
 * UniqueStringList.java
 *
 * 07.11.2013
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.acf.util;


import corentx.util.*;


/**
 * This class manages a list of strings which is alphabetically sorted and contains any string
 * only one times.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 07.11.2013 - Added.
 * @changed OLI 16.09.2015 - Moved to "Archimedes".
 */

public class UniqueStringList {

    private SortedVector<String> list = new SortedVector<String>();

    /**
     * Adds the passed string if not already contained in the list
     *
     * @param s The string to add.
     *
     * @changed OLI 07.11.2013 - Added.
     */ 
    public void add(String s) {
        if (!this.list.contains(s)) {
            this.list.add(s);
        }
    }

    /**
     * Returns the list content ready to be appended to a string.
     *
     * @return The list content ready to be appended to a string
     *
     * @changed OLI 07.11.2013 - Added.
     */
    public String getContentToAppend() {
        String r = "";
        for (String s : this.list) {
            r += s;
        }
        return r;
    }

    /**
     * Removes all elements from the list.
     *
     * @changed OLI 07.11.2013 - Added.
     */
    public void clear() {
        this.list.clear();
    }

    /**
     * Returns the size of the list.
     *
     * @return The size of the list.
     *
     * @changed OLI 07.11.2013 - Added.
     */
    public int size() {
        return this.list.size();
    }

    /**
     * Returns the list content as a string array.
     *
     * @return The list content as a string array.
     *
     * @changed OLI 07.11.2013 - Added.
     */
    public String[] toArray() {
        return this.list.toArray(new String[0]);
    }

}