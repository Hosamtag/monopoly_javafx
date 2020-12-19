package ooga.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.Map;
import ooga.controller.util.PropertiesFileMapMaker;
import ooga.controller.propertiesaccessers.PropertyFileAccessor;

/**
 * This class creates a way for the program to be able to read the game objects used to make the
 * monopoly game (tiles and cards). This class can be extended by creating more game objects that
 * variations of monopoly could use (such as a mystery money deck)
 */
public abstract class GameObjectsReader {

  private static final String PROPERTIES_EXTENSION = ".properties";

  public Accessible thisGameAccessor;
  public String filePath;

  /**
   * Creates an instance of a property file accessor that allows the program to parse and get
   * information from properties files
   */
  public GameObjectsReader() {
    thisGameAccessor = new PropertyFileAccessor();
    setPath();
  }

  /**
   * sets the path of where the properties files are for a specific game object
   */
  protected abstract void setPath();

  /**
   * gets all the info in a properties file for a certain game object
   *
   * @param tile
   * @return a string of all the info about a game object in the properties file
   * @throws IOException
   */
  public String getInfoForSpecificGameObject(String tile) throws IOException {
    List<Object> tileInfoKeys = thisGameAccessor
        .getKeySetOfPropertiesFile(filePath + tile + PROPERTIES_EXTENSION);
    return addAllInfoOfTile(tile, tileInfoKeys);
  }

  /**
   * @param tile
   * @return
   * @throws IOException
   */
  public Map<String, String> gameInfoMapper(String tile) throws IOException, InvalidKeyException {
    List<Object> tileInfoKeys = thisGameAccessor
        .getKeySetOfPropertiesFile(filePath + tile + PROPERTIES_EXTENSION);
    return PropertiesFileMapMaker.createMap(tileInfoKeys, filePath + tile + PROPERTIES_EXTENSION);
  }

  /**
   * the path to the files can be hard coded because the data reader abstract classes are specific
   * to this certain game. this design can be made modular by creating more abstract classes that
   * extend the game objects reader class that have different functionalities
   *
   * @param tile
   * @param keys
   * @return
   * @throws IOException
   */
  private String addAllInfoOfTile(String tile, List<Object> keys) throws IOException {
    String fileInfo = "";
    for (Object keyval : keys) {
      fileInfo = fileInfo + thisGameAccessor
          .readFile(filePath + tile + PROPERTIES_EXTENSION, keyval.toString()) + ",";
    }
    return fileInfo;

  }

}
