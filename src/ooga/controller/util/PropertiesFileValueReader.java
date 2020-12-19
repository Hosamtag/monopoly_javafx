package ooga.controller.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Properties;
import ooga.exceptions.InvalidPropertiesFileException;

/**
 * the responsibility of this class is to just read a properties file
 */
public final class PropertiesFileValueReader {

  private PropertiesFileValueReader() {
  }

  /**
   * this method is static because reading a properties file is a generic function that should be
   * able to be implemented by any part of this program and also external programs, this reduces
   * duplicate code and also allows. This method also uses a custom exception that is thrown
   *
   * @param fileName
   * @return
   * @throws IOException
   */
  public static String readFile(String fileName, String val)
      throws InvalidPropertiesFileException, IOException, InvalidKeyException {
    FileInputStream input = null;
    Properties prop = null;
    try {
      input = new FileInputStream(fileName);
      prop = new Properties();
      prop.load(input);
    } catch (Exception InvalidPropertiesFileException) {
      throw new InvalidPropertiesFileException(ResourceUtil.getResourceValue("WrongGame"));
    } finally {
      if (input == null) {
        throw new InvalidPropertiesFileException(ResourceUtil.getResourceValue("WrongGame"));
      }
      input.close();
    }
    return getKeyValue(prop, val);
  }

  private static String getKeyValue(Properties prop, String value) throws InvalidKeyException {
    if (prop.getProperty(value) == null) {
      throw new InvalidKeyException(ResourceUtil.getResourceValue("WrongKey"));
    }
    return prop.getProperty(value);
  }

}
