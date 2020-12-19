package ooga.controller;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import ooga.controller.factory.ButtonFactory;
import ooga.controller.loaders.GameLoader;
import ooga.controller.util.ResourceUtil;
import ooga.model.RuleEvent;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.Randomizable;
import ooga.model.Tile;
import ooga.view.GameViewable;
import ooga.view.alerts.TradeAlert;
import ooga.view.alerts.UpgradeAlert;
import ooga.view.screen.EndGameScreen;

/**
 * The controller that controls the game play of the current play and sets the actions for certain
 * buttons in the game. This class also determine what happens when a dice is rolled and checks for
 * win conditions to display the EndGame screen
 *
 * @author Hosam Tageldin
 */
public class GameController implements Observer {

  private static final String END_GAME_PROPERTIES = "properties/EndGameButtons";
  private static final ResourceBundle resourceBundle = ResourceBundle
      .getBundle(END_GAME_PROPERTIES);
  private static final String END_GAME_BUTTONS = "EndGame";
  private final Scene myScene;
  private final Tab myTab;
  private Player currentPlayer;
  private Order playerOrder;
  private List<RuleEvent> gameRules;
  private GameLoader myGameLoader;
  private GameModel myMonopolyModel;
  private GameViewable myGameView;
  private List<Tile> tileList;
  private List<Player> playerList;
  private final ButtonHandler myButtonHandler;


  private static final int PLAYER_FUND_CHANGE = 100;
  private static final int CODE_TO_INT_DIFFERENCE = 48;

  public GameController(Scene myScene, Tab newTab) {
    this.myScene = myScene;
    myTab = newTab;
    myButtonHandler = new ButtonHandler();
    createGameLoader();
  }

  private void createGameLoader() {
    myGameLoader = new GameLoader(myScene, myTab, this);
  }

  /**
   * Once the game loader has loaded in the game, this class is called to set all the appropriate
   * classes for the game controller to use
   */
  public void setObjects() {
    myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    myTab.setOnSelectionChanged(p -> myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode())));
    myMonopolyModel = myGameLoader.getMonopolyModel();
    playerList = myMonopolyModel.getPlayers();
    tileList = myMonopolyModel.getTiles();
    myGameView = myGameLoader.getGameView();
    currentPlayer = myGameLoader.getStartingPlayer();
    playerOrder = myGameLoader.getOrder();
    gameRules = myGameLoader.getRules();
    addObservables();
  }

  /**
   * Method called by the Roll Button. Will roll the die and then update the player turn
   */
  public void nextTurn() {
    myMonopolyModel.doTurn(currentPlayer);
    checkRules();
    checkGame();
    currentPlayer = getNextPlayer(currentPlayer);
    myGameView.updateView(currentPlayer);
  }

  private Player getNextPlayer(Player currentPlayer) {
    return playerOrder.getNextPlayer(currentPlayer, playerList);
  }

  private void checkGame() {
    if (myMonopolyModel.checkIfGameOver()) {
      EndGameScreen newEndScreen = new EndGameScreen(myTab, playerList);
      ButtonFactory newSplash = new ButtonFactory(this,
          resourceBundle.getString(END_GAME_BUTTONS).split(","));
      newEndScreen.addButtons(newSplash.getButtons());
    }
  }

  private void checkRules() {
    for (RuleEvent eachRule : gameRules) {
      eachRule.doRule(currentPlayer);
    }
  }

  private void addObservables() {
    for (Player pl : playerList) {
      pl.addObserver(this);
    }
    myMonopolyModel.addObserver(this);
    myMonopolyModel.getRandomizable().addObserver(myGameView.getDiceView());
  }

  /**
   * Calls the Game Loader to save the game once the Save Game button is selected
   */
  public void saveGame() {
    myGameLoader.saveGame();
  }

  /**
   * Calls the Game Loader to load a game once the Load Game button is selected
   */
  public void loadGame() {
    myGameLoader.loadGame();
  }

  /**
   * Calls the Game Loader to start a new game once the Start New Game button is selected
   */
  public void startGame() {
    myGameLoader.startNewGame();
  }

  /**
   * Calls the Game Loader to add a new game once the Add Game button is selected
   */
  public void addGame() {
    Tab newGame = new Tab();
    myTab.getTabPane().getTabs().add(0, newGame);
    new GameController(myScene, newGame);
    myTab.getTabPane().getSelectionModel().selectFirst();
  }

  /**
   * Calls the Button Handler class to handle the mortgage pop up and functionality
   */
  public void changeMortgage() {
    UpgradeAlert alert = new UpgradeAlert();
    String mortgageProperty = alert
        .upgradePropertyAlert(ResourceUtil.getResourceValue("MortgageProperty"),
            ResourceUtil.getResourceValue("MortgageWindow"),
            ResourceUtil.getResourceValue("MortgageHeader"));
    myButtonHandler.handleMortgage(myMonopolyModel, mortgageProperty);
    myGameView.updateView(currentPlayer);
  }

  /**
   * Calls the Button Handler class to handle the upgrade pop up and functionality
   */
  public void upgradeProperty() {
    UpgradeAlert alert = new UpgradeAlert();
    String propertyUpgrade = alert
        .upgradePropertyAlert(ResourceUtil.getResourceValue("UpgradeProperty"),
            ResourceUtil.getResourceValue("UpgradeWindow"),
            ResourceUtil.getResourceValue("UpgradeHeader"));
    myButtonHandler.handleUpgrade(myMonopolyModel, propertyUpgrade);
    myGameView.updateView(currentPlayer);
  }

  /**
   * Calls the Button Handler class to handle the trade pop up and functionality
   */
  public void tradeAction() {
    TradeAlert alert = new TradeAlert();
    List<String> responses = alert
        .createTradeAlertProperties(playerList, ResourceUtil.getResourceValue("PropertySelect"),
            ResourceUtil.getResourceValue("TradingWindow"),
            ResourceUtil.getResourceValue("TradingHeader"));
    myButtonHandler.handleTrade(myMonopolyModel, responses);
  }

  /**
   * Updates the view whenever the Observables change
   */
  @Override
  public void update() {
    myGameView.updateView(currentPlayer);
  }

  private void handleKeyInput(KeyCode code) {
    if (code.isDigitKey()) {
      moveCurrentPlayer(code.getCode() - CODE_TO_INT_DIFFERENCE);
    } else {
      handlePlayerCash(code);
    }
  }

  private void moveCurrentPlayer(int spaces) {
    if (spaces == 0) {
      currentPlayer = getNextPlayer(currentPlayer);
      myGameView.updateView(currentPlayer);
    } else {
      myMonopolyModel.jumpPlayerToTile(currentPlayer, getNewTile(spaces));
      myMonopolyModel.callPlayerTileEvent(currentPlayer);
      checkRules();
    }
  }

  private void handlePlayerCash(KeyCode code) {
    switch (code) {
      case A -> currentPlayer.updateFunds(currentPlayer.getFunds() + PLAYER_FUND_CHANGE);
      case D -> currentPlayer.updateFunds(currentPlayer.getFunds() - PLAYER_FUND_CHANGE);
      case E -> forceEndGame();
    }
  }

  private void forceEndGame() {
    currentPlayer.declareWinner();
    myMonopolyModel.declareGameOver();
    checkGame();
  }

  private Tile getNewTile(int spaces) {
    tileList = myMonopolyModel.getTiles();
    int currentIndex = tileList.indexOf(currentPlayer.getCurrentTile());
    int moveToIndex = (currentIndex + spaces) % tileList.size();
    return tileList.get(moveToIndex);
  }

  /**
   * For JUnit tests
   * @return integer representing current player index
   */
  public int getCurrentPlayer() {
    return playerList.indexOf(currentPlayer);
  }

  /**
   * For JUnit tests
   * @return the list of current players
   */
  public List<Player> getPlayers() {
    return playerList;
  }

  /**
   * For JUnit tests
   * @return the Randomizable for the game
   */
  public Randomizable getMyDie() {
    return myMonopolyModel.getRandomizable();
  }

  /**
   * For JUnit tests
   * @return the viewable interface of this game
   */
  public GameViewable getMyGameView() {
    return myGameView;
  }
}
