package ooga.controller.readers.dataversionreaders;

import java.io.IOException;
import ooga.controller.Accessible;
import ooga.controller.DataVersionReader;
import ooga.controller.propertiesaccessers.PropertyFileAccessor;

/**
 * this is a class that is used to read the information about game versions from
 * internal properties files
 */
public class DataVersionReaderInternal extends DataVersionReader {

  private static final String GAME_VERSION_KEY = "Version";
  private static final String CLICKHANDLER_KEY = "ClickHandler";
  private static final String CHANCE_DECK_KEY = "ChanceDeck";
  private static final String BUTTONS_KEY = "Buttons";
  private static final String TILES_KEY = "Tiles";
  private static final String RULES_KEY = "Rules";
  private static final String GAME_ORDER = "GameOrder";
  private static final String PROPERTIES_END = ".properties";
  private static final String GAME_VERSION_PATH = "data/gameversions/";
  private final Accessible thisGameAccessor = new PropertyFileAccessor();

  /**
   * gets the value of the game version
   * @param version
   * @return a string that is the desired game Version
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String getThisGameVersion(String version) throws IOException {
    return thisGameAccessor
        .readFile(GAME_VERSION_PATH + version + PROPERTIES_END, GAME_VERSION_KEY);
  }

  /**
   * gets the value of the click handler type
   * @param version
   * @return a string that is the desired click handler
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String getThisClickHandlerType(String version) throws IOException {
    return thisGameAccessor
        .readFile(GAME_VERSION_PATH + version + PROPERTIES_END, CLICKHANDLER_KEY);
  }

  /**
   * gets the value of the desired chance deck
   * @param version
   * @return a string that is the desired chance deck
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String getThisVersionChanceDeck(String version) throws IOException {
    return thisGameAccessor
        .readFile(GAME_VERSION_PATH + version + PROPERTIES_END, CHANCE_DECK_KEY);
  }

  /**
   * gets the value of the desired tiles
   * @param version
   * @return a string that is the desired tiles for this game
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String getThisVersionTiles(String version) throws IOException {
    return thisGameAccessor
        .readFile(GAME_VERSION_PATH + version + PROPERTIES_END, TILES_KEY);
  }

  /**
   * gets the value of the desired game buttons
   * @param version
   * @return a string that is the desired game buttons for this game
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String[] getThisGameButtons(String version) throws IOException {
    return thisGameAccessor.readFile(GAME_VERSION_PATH + version + PROPERTIES_END, BUTTONS_KEY)
        .split(",");
  }

  /**
   * gets the value of the desired game rules
   * @param version
   * @return a string that is the desired game rues for this game
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String[] getGameRules(String version) throws IOException {
    return thisGameAccessor.readFile(GAME_VERSION_PATH + version + PROPERTIES_END, RULES_KEY)
        .split(",");
  }

  /**
   * gets the value of the desired game order
   * @param version
   * @return a string that is the desired game order for this game
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String getGameOrder(String version) throws IOException {
    return thisGameAccessor
        .readFile(GAME_VERSION_PATH + version + PROPERTIES_END, GAME_ORDER);
  }


}
