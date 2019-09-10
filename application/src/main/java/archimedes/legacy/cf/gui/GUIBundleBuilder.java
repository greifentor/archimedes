/*
 * GUIBundleBuilder.java
 *
 * 29.11.2013
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf.gui;


import java.io.*;

import baccara.files.*;
import baccara.gui.*;


/**
 * This class is provides specific GUI bundle which uses the ACF resources.
 *
 * To get it singleton code fragment below my be helpful:
 * <PRE>
 * public class XXXGUIBundleBuilder {
 *
 *     private static GUIBundle guiBundle = null;
 *
 *     public static GUIBundle getGUIBundle() {
 *         return guiBundle;
 *     }
 *
 *     static {
 *         guiBundle = GUIBundleBuilder.getBundle("XXX");
 *     }
 * 
 * }
 *
 * @author O.Lieshoff
 *
 * @changed OLI 29.11.2013 - Added.
 * @changed OLI 16.09.2015 - Moved to "Archimedes".
 */

public class GUIBundleBuilder {

    /**
     * Returns a specific GUI bundle with the ACF code factory resources.
     *
     * @param guiBundle A bundle with GUI information.
     * @param applicationNames The names of the application as prefix for the resource files.
     * @return A specific GUI bundle with the ACF code factory resources.
     *
     * @changed OLI 29.11.2013 - Added.
     */
    public static GUIBundle getGUIBundle(GUIBundle guiBundle, String... applicationNames) {
        GUIBundle agb = guiBundle;
        String lang = System.getProperty("archimedes.user.language", "en");
        ResourceManager rm = null;
        for (int i = 0; i < applicationNames.length; i++) {
            String fn = "./conf/" + applicationNames[i] + "_resource_" + lang + ".properties";
            if (new File(fn).exists()) {
                if (rm == null) {
                    rm = new PropertyResourceManager(new PropertyFileManager().open(fn));
                } else {
                    ((PropertyResourceManager) rm).addResources(new PropertyFileManager().open(
                            fn));
                }
            }
        }
        return new GUIBundle(agb.getInifile(), rm, agb.getImageProvider(),
                agb.getHGap(), agb.getVGap());
    }

}