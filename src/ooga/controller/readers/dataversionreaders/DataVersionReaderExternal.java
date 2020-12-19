package ooga.controller.readers.dataversionreaders;

import java.io.IOException;
import ooga.controller.DataVersionReader;
import ooga.controller.scorekeeper.FirebaseAccessor;

/**
 * gets the needed values for loading a new game from an external firebase database
 */
public class DataVersionReaderExternal extends DataVersionReader {

  private final FirebaseAccessor thisFirebaseLink;

  /**
   * sets up the connection with the firebase database
   */
  private static final String GAME_VERSION_KEY = "Version";
  private static final String CLICKHANDLER_KEY = "ClickHandler";
  private static final String CHANCE_DECK_KEY = "ChanceDeck";
  private static final String BUTTONS_KEY = "Buttons";
  private static final String TILES_KEY = "Tiles";
  private static final String RULES_KEY = "Rules";
  private static final String GAME_ORDER = "GameOrder";

  private final static int THREAD_LEVEL_LENGTH = 5000;

  public DataVersionReaderExternal() {
    thisFirebaseLink = new FirebaseAccessor();
  }

  /**
   * gets the value of the game version
   *
   * @param version
   * @return a string that is the desired game Version
   * @throws IOException
   * @throws InterruptedException
   */

  @Override
  protected String getThisGameVersion(String version) throws IOException, InterruptedException {
    return getGameInfo(version, GAME_VERSION_KEY);
  }

  /**
   * gets the value of the click handler type
   *
   * @param version
   * @return a string that is the desired click handler
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String getThisClickHandlerType(String version)
      throws IOException, InterruptedException {
    return getGameInfo(version, CLICKHANDLER_KEY);
  }

  /**
   * gets the value of the desired chance deck
   *
   * @param version
   * @return a string that is the desired chance deck
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String getThisVersionChanceDeck(String version)
      throws IOException, InterruptedException {
    return getGameInfo(version, CHANCE_DECK_KEY);
  }

  /**
   * gets the value of the desired tiles
   *
   * @param version
   * @return a string that is the desired tiles for this game
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String getThisVersionTiles(String version) throws IOException, InterruptedException {
    return getGameInfo(version, TILES_KEY);
  }

  /**
   * gets the value of the desired game buttons
   *
   * @param version
   * @return a string that is the desired game buttons for this game
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String[] getThisGameButtons(String version) throws IOException, InterruptedException {
    return getGameInfo(version, BUTTONS_KEY).split(",");
  }

  /**
   * gets the value of the desired game rules
   *
   * @param version
   * @return a string that is the desired game rues for this game
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String[] getGameRules(String version) throws IOException, InterruptedException {
    return getGameInfo(version, RULES_KEY).split(",");
  }

  /**
   * gets the value of the desired game order
   * @param version
   * @return a string that is the desired game order for this game
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected String getGameOrder(String version) throws InterruptedException {
    return getGameInfo(version, GAME_ORDER);

  }

  /**
   * gets the value of the desired game info
   * @param version
   * @return a string that is the desired game info for this game
   * @throws IOException
   * @throws InterruptedException
   */
  private String getGameInfo(String version, String key) throws InterruptedException {
    thisFirebaseLink.updateRefToChild(version);
    final String[] gameVersion = new String[1];
    thisFirebaseLink.readData(value -> gameVersion[0] = value, key);
    Thread.sleep(THREAD_LEVEL_LENGTH);
    return gameVersion[0];
  }


}
