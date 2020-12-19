package ooga.controller.loaders;

import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import ooga.controller.ClickHandler;
import ooga.controller.DataVersionReader;
import ooga.controller.GameController;
import ooga.controller.savers.GameSaver;
import ooga.controller.readers.dataversionreaders.DataVersionReaderExternal;
import ooga.controller.readers.dataversionreaders.DataVersionReaderInternal;
import ooga.controller.factory.FactoryCreator;
import ooga.controller.readers.gameobjectsreaders.SavedGameReader;
import ooga.controller.Order;
import ooga.controller.util.ResourceUtil;
import ooga.model.RuleEvent;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.view.GameViewable;
import ooga.view.MonopolyView;
import ooga.view.TileView;
import ooga.view.alerts.ErrorAlert;

/**
 * Loads a game either through a loaded file or the information retrieved from the splash screen
 * handler
 *
 * @author Hosam Tageldin
 */
public class GameLoader {

  private final static String SAVED_GAMES_PATH = "data/gameversions/initialization";
  private final static String SKIP_TO_GAME_FILE = "/skipToGame.properties";
  private final static String SKIP_TO_SWAP_FILE = "/skipToSwap";
  private final static String TEST_GAME_FILE = "/testFile.properties";
  private final static String GAME_VERSION = "GameVersion";
  private final static String DICE = "Dice";
  private final static String SKIP_LANGUAGE = "English";
  private final static String CURRENT_PLAYER = "CurrentPlayer";
  private final Tab myTab;
  private final Scene myScene;
  private DataVersionReader newReader;
  private final FactoryCreator myFactoryCreator;
  private String[] randomizableDetails;
  private String myGameVersion;
  private GameViewable myGameView;
  private GameModel myMonopolyModel;
  private List<String> playerIcons;
  private List<Map<String, String>> myPlayerMaps;
  private Player currentPlayer;
  private final GameController myGameController;
  private SplashScreenHandler mySplashScreenHandler;
  private boolean userSelected;

  public GameLoader(Scene myScene, Tab myTab, GameController myGameController) {
    this.myScene = myScene;
    this.myTab = myTab;
    this.myGameController = myGameController;
    this.myFactoryCreator = new FactoryCreator();
    setCheatKeys();
    createSplashScreen();
  }

  private void createSplashScreen() {
    mySplashScreenHandler = new SplashScreenHandler(myTab, this);
  }

  /**
   * Starts a new game based off these parameters
   *
   * @param randomizableDetails the details for the randomizable
   * @param playerProperties    the List of maps representing players
   * @param userSelected        boolean representing whether there are user selected rules
   */
  public void startGame(String[] randomizableDetails, List<Map<String, String>> playerProperties,
      boolean userSelected) {
    this.userSelected = userSelected;
    this.myGameVersion = myTab.getText();
    this.myPlayerMaps = playerProperties;
    this.randomizableDetails = randomizableDetails;
    newReader = new DataVersionReaderInternal();
    setGameType(0);
  }

  /**
   * Loads a game based off the file name chosen in the FileChooser
   *
   * @param fileName the String representing the file selected by the user
   */
  public void getLoadedGame(String fileName) {
    try {
      userSelected = false;
      SavedGameReader dataVersionReader = new SavedGameReader();
      Map<String, String> gameProps = dataVersionReader.gameObjectsViewReader(fileName);
      this.myGameVersion = gameProps.get(GAME_VERSION);
      myTab.setText(myGameVersion);
      this.myPlayerMaps = dataVersionReader.createMapOfPlayers(gameProps);
      randomizableDetails = gameProps.get(DICE).split(",");
      setGameType(Integer.parseInt(gameProps.get(CURRENT_PLAYER)));
      updateTileOwners(dataVersionReader.createMapOfPlayers(gameProps));
      myGameView.updateView(currentPlayer);
    } catch (Exception e) {
      ErrorAlert newAlert = new ErrorAlert();
      newAlert.createErrorAlert("Invalid properties file");
    }
  }

  /**
   * Loads a game internally
   */
  public void loadGame() {
    newReader = new DataVersionReaderInternal();
    showFileChooser();
  }

  /**
   * Loads a game from Firebase
   */
  public void externalLoadGame() {
    newReader = new DataVersionReaderExternal();
    showFileChooser();
  }

  private void showFileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(SAVED_GAMES_PATH));
    File chosen = fileChooser.showOpenDialog(null);
    if (chosen != null) {
      getLoadedGame(chosen.getPath());
    }
  }

  private void setGameType(int activePlayerIndex) {
    try {
      newReader.createNewGameVerison(myGameVersion);
      String clickHandlerType = newReader.getClickHandlerType();
      ModelLoader myModelLoader = new ModelLoader(newReader, myPlayerMaps, randomizableDetails);
      myMonopolyModel = myModelLoader.getGameModel();
      List<Button> myButtons = myFactoryCreator.createButtons(myGameController,
          newReader.getGameButtons());
      List<TileView> tileViewList = myFactoryCreator.getTileViews(newReader.getTileMaps());
      playerIcons = myModelLoader.getPlayerIcons();
      myGameView = new MonopolyView(myScene, myTab, tileViewList,
          myMonopolyModel.getPlayers(), playerIcons,
          myMonopolyModel.getRandomizable(), myButtons);
      ClickHandler clickHandler = myFactoryCreator
          .createClickHandler(clickHandlerType, myMonopolyModel, myGameView);
      for (TileView tile : tileViewList) {
        tile.addObserver(clickHandler);
      }
      currentPlayer = myMonopolyModel.getPlayers().get(activePlayerIndex);
      myGameController.setObjects();
      myGameView.updateView(currentPlayer);
    } catch (Exception e) {
      new ErrorAlert().createErrorAlert("Invalid Property Files. Return to Main Screen");
      mySplashScreenHandler.setScene();
    }
  }

  private void updateTileOwners(List<Map<String, String>> gameProps) {
    OwnableAssigner newOwnableAssigner = new OwnableAssigner();
    newOwnableAssigner.assignOwnables(myMonopolyModel, gameProps);
  }

  /**
   * Shows the FileChooser for saving the game and calls the game saver to save the game based off
   * the current state
   */
  public void saveGame() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(SAVED_GAMES_PATH));
    File chosen = fileChooser.showSaveDialog(null);
    if (chosen != null) {
      new GameSaver(chosen.getName(), myGameVersion, myMonopolyModel.getPlayers(), playerIcons,
          myGameController.getCurrentPlayer(), myMonopolyModel.getRandomizable());
    }
  }

  /**
   * Starts a new game for the curretn screen
   */
  public void startNewGame() {
    mySplashScreenHandler.startNewGame();
  }

  /**
   * @return the current GameModel API
   */
  public GameModel getMonopolyModel() {
    return myMonopolyModel;
  }

  /**
   * @return the current GameViewable API
   */
  public GameViewable getGameView() {
    return myGameView;
  }

  /**
   * Defaults to the first player in the list for a new game, but can be any player based off how
   * the game is loaded in if the current player is the 3rd player in the list
   *
   * @return the starting player for the game
   */
  public Player getStartingPlayer() {
    return currentPlayer;
  }

  /**
   * @return the List of rule events for this GameModel to follow
   */
  public List<RuleEvent> getRules() {
    return myFactoryCreator.getRules(getGameRuleStrings(), myMonopolyModel);
  }

  /**
   * @return the Order that determines who goes next in the list of players
   */
  public Order getOrder() {
    return myFactoryCreator.getOrder(newReader.getOrder());
  }

  private List<String> getGameRuleStrings() {
    if (userSelected) {
      return mySplashScreenHandler.getSelectedRules();
    } else {
      return newReader.getRules();
    }
  }

  private void setCheatKeys() {
    myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
  }

  private void handleKeyInput(KeyCode code) {
    switch (code) {
      case S -> skipToSwap();
      case G -> skipToGame();
      case T -> skipToTest();
    }
  }

  private void skipToGame() {
    ResourceUtil.setLanguage(SKIP_LANGUAGE);
    newReader = new DataVersionReaderInternal();
    getLoadedGame(SAVED_GAMES_PATH + SKIP_TO_GAME_FILE);
  }

  private void skipToSwap() {
    ResourceUtil.setLanguage(SKIP_LANGUAGE);
    newReader = new DataVersionReaderInternal();
    getLoadedGame(SAVED_GAMES_PATH + SKIP_TO_SWAP_FILE);
  }

  private void skipToTest() {
    ResourceUtil.setLanguage(SKIP_LANGUAGE);
    newReader = new DataVersionReaderInternal();
    getLoadedGame(SAVED_GAMES_PATH + TEST_GAME_FILE);
  }
}
