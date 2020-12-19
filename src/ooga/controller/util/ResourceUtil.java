package ooga.controller.util;

import java.util.ResourceBundle;

/**
 * Util class to hold the language set for the game at the initial splash screen
 *
 * @author Hosam Tageldin
 */
public final class ResourceUtil {

  private static final String PROPERTIES = "properties/";

  private static ResourceBundle resourceBundle;

  private ResourceUtil() {

  }

  /**
   * Can be statically called to get the value from the data file
   *
   * @param key the key to retrieve the value for
   * @return the String representing the value in the data file
   */
  public static String getResourceValue(String key) {
    return resourceBundle.getString(key);
  }

  /**
   * Sets the language of the util class
   *
   * @param language Language chosen on the initial splash screen
   */
  public static void setLanguage(String language) {
    resourceBundle = ResourceBundle.getBundle(PROPERTIES + language);
  }
}
