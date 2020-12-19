package ooga.controller.propertiesaccessers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import ooga.controller.Accessible;
import ooga.controller.util.KeyValuesReader;
import ooga.controller.util.PropertiesFileMapMaker;
import ooga.controller.util.PropertiesFileValueReader;
import ooga.exceptions.InvalidGameVersionException;
import ooga.view.alerts.ErrorAlert;

/**
 * This class is used to access various values and elements in the
 * properties files used in this project
 */
public class PropertyFileAccessor implements Accessible {

  private final ErrorAlert thisError = new ErrorAlert();

  /**
   * gets all the key values of a properties file in order
   * @param fileName
   * @return a list of objects that are all the keys in the properties file
   * @throws IOException
   */
  @Override
  public List<Object> getKeySetOfPropertiesFile(String fileName) {
    try {
      return KeyValuesReader.getKeySetOfPropertiesFile(fileName);
    } catch (Exception InvalidPropertiesFileException) {
      thisError.createErrorAlert(InvalidPropertiesFileException.getMessage());
    }
    return null;
  }

  /**
   * creates a map of all the keys and values in a properties File
   * @param keys
   * @param file
   * @return a map of all the keys and values in a properties file
   * @throws InvalidGameVersionException
   */
  @Override
  public Map<String, String> createMap(List<Object> keys, String file)
      throws InvalidGameVersionException {
    try {
      return PropertiesFileMapMaker.createMap(keys, file);
    } catch (Exception InvalidGameVersionException) {
      thisError.createErrorAlert(InvalidGameVersionException.getMessage());
    }
    return null;
  }

  /**
   * gets the value associated with a certain key
   * @param fileName
   * @param val
   * @return the string of the value that is associated with a key
   * @throws InvalidGameVersionException
   */
  @Override
  public String readFile(String fileName, String val) throws InvalidGameVersionException {
    try {
      return PropertiesFileValueReader.readFile(fileName, val);
    } catch (Exception InvalidGameVersionException) {
      thisError.createErrorAlert(InvalidGameVersionException.getMessage());
    }
    return null;
  }
}
