package ooga.controller.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import ooga.controller.propertiesaccessers.SequencedProperties;
import ooga.exceptions.InvalidPropertiesFileException;

public final class KeyValuesReader {

  private KeyValuesReader() {
  }

  /**
   * gets all the key values of a properties file in order
   *
   * @param fileName
   * @return a list of objects that are all the keys in the properties file
   * @throws IOException
   */
  public static List<Object> getKeySetOfPropertiesFile(String fileName)
      throws InvalidPropertiesFileException {
    try {
      InputStream reader = new FileInputStream(fileName);
      SequencedProperties prop = new SequencedProperties();
      prop.load(reader);
      return Collections.list(prop.keys());
    } catch (Exception InvalidPropertiesFileException) {
      throw new InvalidPropertiesFileException(ResourceUtil.getResourceValue("ObjectDoesntExist"));
    }
  }

}
