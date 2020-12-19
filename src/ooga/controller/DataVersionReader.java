package ooga.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ooga.controller.readers.gameobjectsreaders.CardReader;
import ooga.controller.readers.gameobjectsreaders.TileReader;
import ooga.controller.propertiesaccessers.PropertyFileAccessor;

/**
 * the purpose of this class is that if a new version of monopoly is loaded this class will be able
 * to parse the version properties file and create a way to gather all the info needed for a game of
 * monopoly sources used: https://www.tutorialspoint.com/how-to-read-the-data-from-a-properties-file-in-java
 * https://www.java2novice.com/java-collections-and-util/properties/all-keys/
 */
public abstract class DataVersionReader {

  private String gameVersion;
  private String gameOrder;
  private String clickHandlerType;
  private String versionChanceDeck;
  private String versionTiles;
  private String[] gameButtons;
  private final Accessible thisGameAccessor;
  private String[] gameRules;
  private static final String PROPERTIESEND = ".properties";
  private static final String CHANCEDECKPATH = "data/gameversions/decks/chancedecks/";
  private static final String TILECONFIGURATIONPATH = "data/gameversions/tileconfigurations/";

  /**
   * creates an object used to access information about properties files
   */
  public DataVersionReader() {
    thisGameAccessor = new PropertyFileAccessor();

  }
  /**
   * gets the value of the game version
   * @param version
   * @return a string that is the desired game Version
   * @throws IOException
   * @throws InterruptedException
   */

  protected abstract String getThisGameVersion(String version)
      throws IOException, InterruptedException;
  /**
   * gets the value of the click handler type
   * @param version
   * @return a string that is the desired click handler
   * @throws IOException
   * @throws InterruptedException
   */
  protected abstract String getThisClickHandlerType(String version)
      throws IOException, InterruptedException;
  /**
   * gets the value of the desired chance deck
   * @param version
   * @return a string that is the desired chance deck
   * @throws IOException
   * @throws InterruptedException
   */
  protected abstract String getThisVersionChanceDeck(String version)
      throws IOException, InterruptedException;
  /**
   * gets the value of the desired tiles
   * @param version
   * @return a string that is the desired tiles for this game
   * @throws IOException
   * @throws InterruptedException
   */
  protected abstract String getThisVersionTiles(String version)
      throws IOException, InterruptedException;
  /**
   * gets the value of the desired game buttons
   * @param version
   * @return a string that is the desired game buttons for this game
   * @throws IOException
   * @throws InterruptedException
   */
  protected abstract String[] getThisGameButtons(String version)
      throws IOException, InterruptedException;

  /**
   * gets the value of the desired game rules
   * @param version
   * @return a string that is the desired game rues for this game
   * @throws IOException
   * @throws InterruptedException
   */
  protected abstract String[] getGameRules(String version) throws IOException, InterruptedException;
  /**
   * gets the value of the desired game order
   * @param version
   * @return a string that is the desired game order for this game
   * @throws IOException
   * @throws InterruptedException
   */
  protected abstract String getGameOrder(String version) throws IOException, InterruptedException;

  /**
   * sets all the values needed to create a new game
   * @param version
   * @throws IOException
   * @throws InterruptedException
   */
  public void createNewGameVerison(String version) throws IOException, InterruptedException{
    gameVersion = this.getThisGameVersion(version);
    clickHandlerType = this.getThisClickHandlerType(version);
    versionChanceDeck = this.getThisVersionChanceDeck(version);
    versionTiles = this.getThisVersionTiles(version);
    gameButtons = this.getThisGameButtons(version);
    gameRules = this.getGameRules(version);
    gameOrder = this.getGameOrder(version);
  }

  /**
   * gets the click handler type specified in this game version
   * @return the string of the click handler type
   */
  public String getClickHandlerType(){
    return clickHandlerType;
  }

  /**
   * gets the click game version type specified in this game version
   * @return the string of the game version type
   */
  public String getGameVersion() {return gameVersion;}

  /**
   * gets the click game order type specified in this game version
   * @return the string of the game order type
   */
  public String getOrder() { return gameOrder;}

  /**
   * gets a list of maps that has all the information for
   * the tiles used in the game
   * @return a list of map of key and value pairs of the all the
   * data associated with a tile
   * @throws IOException
   * @throws InvalidKeyException
   */
  public List<Map<String, String>> getTileMaps() throws IOException, InvalidKeyException {
    List<Object> keyVales = thisGameAccessor
        .getKeySetOfPropertiesFile(
            TILECONFIGURATIONPATH + versionTiles + PROPERTIESEND);
    GameObjectsReader tileReader = new TileReader();
    List<Map<String, String>> allTileViews = new ArrayList<>();
    for (Object key : keyVales) {
      allTileViews.add(tileReader.gameInfoMapper(
          thisGameAccessor
              .readFile(TILECONFIGURATIONPATH + versionTiles + PROPERTIESEND,
                  key.toString())));
    }
    return allTileViews;
  }

  /**
   * gets a list of maps that has all the information for
   * the cards used in the game
   * @return a list of map of key and value pairs of the all the
   * data associated with a card
   * @throws IOException
   * @throws InvalidKeyException
   */
  public List<Map<String, String>> getCardMaps() throws IOException, InvalidKeyException {
    List<Object> keyVales = thisGameAccessor
        .getKeySetOfPropertiesFile(
            CHANCEDECKPATH + versionChanceDeck + PROPERTIESEND);
    GameObjectsReader cardReader = new CardReader();
    List<Map<String, String>> allTileViews = new ArrayList<>();
    for (Object key : keyVales) {
      allTileViews.add(cardReader.gameInfoMapper(
          thisGameAccessor
              .readFile(CHANCEDECKPATH + versionChanceDeck + PROPERTIESEND,
                  key.toString())));
    }
    return allTileViews;
  }

  /**
   * gets all the game buttons for a certain game version
   * @return an array of game values that are needed to keep buttons
   * that are used in this game versino
   */
  public String[] getGameButtons() {
    return gameButtons;
  }

  /**
   * gets a list of all the rules associated with this game
   * @return list of all the rules associated with a certain game
   */
  public List<String> getRules() {
    return new ArrayList<>(Arrays.asList(gameRules));
  }


}

