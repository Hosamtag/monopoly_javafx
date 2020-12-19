package ooga.controller.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Button;
import ooga.controller.ClickHandler;
import ooga.controller.GameController;
import ooga.exceptions.InvalidCardException;
import ooga.exceptions.InvalidTileDataException;
import ooga.view.GameViewable;
import ooga.model.Randomizable;
import ooga.controller.Order;
import ooga.model.RuleEvent;
import ooga.model.Card;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.Tile;
import ooga.view.TileView;
import ooga.view.alerts.ErrorAlert;

/**
 * Handles all the factories involved with loading a game to reduce dependencies on the other
 * controller classes
 *
 * @author Hosam Tageldin
 */
public class FactoryCreator {

  private PlayerFactory myPlayerFactory;
  private final ErrorAlert myErrorAlert;

  public FactoryCreator() {
    myErrorAlert = new ErrorAlert();
  }

  /**
   * Uses the button factory to create the list of buttons for the game
   *
   * @param myGameController the class where all the methods for a button are
   * @param buttonLabels     the labels for the buttons
   * @return the list of buttons involved with a specific game
   */
  public List<Button> createButtons(GameController myGameController, String[] buttonLabels) {
    return new ButtonFactory(myGameController, buttonLabels).getButtons();
  }

  /**
   * Uses the Tile Factory to create the list of tiles for the monopoly model
   *
   * @param tileMaps the list of maps where each map represents the properties of a tile
   * @return the list of tiles
   */
  public List<Tile> createTiles(List<Map<String, String>> tileMaps) {
    try {
      return new TileFactory(tileMaps).getMyTiles();
    } catch (InvalidTileDataException ex) {
      myErrorAlert.createErrorAlert(ex.getMessage());
      return new ArrayList<>();
    }
  }

  /**
   * Uses the Tile Factory to create the list of tile view for the monopoly view
   *
   * @param tileMaps the list of maps where each map represents the properties of a tile
   * @return the list of tile views
   */
  public List<TileView> getTileViews(List<Map<String, String>> tileMaps) {
    try {
      return new TileFactory(tileMaps).getMyTileViews();
    } catch (InvalidTileDataException ex) {
      myErrorAlert.createErrorAlert(ex.getMessage());
      return new ArrayList<>();
    }
  }

  /**
   * Uses the CardFactory to create the list of cards for the monopoly model
   *
   * @param cardProperties the list of maps where each map represents the properties of a game card
   * @return the list of cards
   */
  public List<Card> createCards(List<Map<String, String>> cardProperties) {
    try {
      return new CardFactory(cardProperties).getMyCards();
    } catch (InvalidCardException ex) {
      myErrorAlert.createErrorAlert(ex.getMessage());
      return new ArrayList<>();
    }
  }

  /**
   * Uses the Player Factory to create the list of players for the game, this list coming either
   * through splash screens or loading a game
   *
   * @param playerProperties the list of maps where each map represents the properties of a player
   * @return the list of players
   */
  public List<Player> createPlayers(List<Map<String, String>> playerProperties,
      List<Tile> myTiles) {
    try {
      myPlayerFactory = new PlayerFactory(playerProperties, myTiles);
      return myPlayerFactory.getPlayers();
    } catch (InvalidTileDataException ex) {
      myErrorAlert.createErrorAlert(ex.getMessage());
      return new ArrayList<>();
    }
  }

  /**
   * Uses the Player Factory to create the list of player icons for the game, this list coming
   * either through splash screens or loading a game
   *
   * @return the list of player icons
   */
  public List<String> getPlayerIcons() {
    try {
      return myPlayerFactory.getPlayerIcons();
    } catch (InvalidCardException ex) {
      myErrorAlert.createErrorAlert(ex.getMessage());
      return new ArrayList<>();
    }
  }

  /**
   * Uses the Rule Factory to create the list of rules for the game
   *
   * @param myRuleList the list of string representing the rules for the game
   * @param model      the current game model
   * @return the list of RuleEvents
   */
  public List<RuleEvent> getRules(List<String> myRuleList, GameModel model) {
    try {
      RuleFactory myRuleFactory = new RuleFactory(myRuleList, model);
      return myRuleFactory.getRules();
    } catch (InvalidCardException ex) {
      myErrorAlert.createErrorAlert(ex.getMessage());
      return new ArrayList<>();
    }
  }

  /**
   * Uses the Randomizable Factory to create the randomizable for the game
   *
   * @param diceDetails the array representing the dice details
   * @return the Randomizable for the game
   */
  public Randomizable createRandomizables(String[] diceDetails) {
    String typeOfDie = diceDetails[0];
    int numberOfSides = Integer.parseInt(diceDetails[1]);
    int numberOfDie = Integer.parseInt(diceDetails[2]);
    return new RandomizableFactory(typeOfDie, numberOfSides, numberOfDie).getDice();
  }

  /**
   * Uses the ClickHandler factory to create the click handler with the current game
   *
   * @param clickHandlerType the String representing the click handler for the game
   * @param myMonopolyModel  the current monopoly game model
   * @param myGameView       the current monopoly view interface
   * @return the ClickHandler for the current game
   */
  public ClickHandler createClickHandler(String clickHandlerType,
      GameModel myMonopolyModel, GameViewable myGameView) {
    return new ClickHandlerFactory(clickHandlerType, myMonopolyModel, myGameView).getClickHandler();
  }

  /**
   * Uses the Order Factory to create the order associated with the current game version
   *
   * @param orderString String representing the order for the game
   * @return the Order class for the game
   */
  public Order getOrder(String orderString) {
    return new OrderFactory(orderString).getOrder();
  }
}
