package ooga.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import ooga.model.Player;
import ooga.model.Randomizable;
import ooga.view.alerts.ColorPickerAlert;
import ooga.view.boards.SquareBoardView;
import ooga.view.dice.NormalDiceView;
import ooga.view.factory.BoardViewFactory;
import ooga.view.factory.MenuFactory;
import ooga.view.gamedisplay.ButtonPanel;
import ooga.view.gamedisplay.IconView;
import ooga.view.gamedisplay.StatusBoardView;

/**
 * Implements the GameViewable interface to create the view of a monopoly game. Holds all the views
 * associated with a monopoly game from the board, the player scoreboards and the button panel
 *
 * @author Hosam Tageldin, Isabella Knox
 */
public class MonopolyView implements GameViewable {

  private static final String HEX_CODE = "#";
  private static final String CSS = ".css";
  private final BorderPane myGameScreen;
  private BorderPane myScoreBoard;
  private BoardView myBoard;
  private final ButtonPanel myButtons;
  private DiceView myDie;
  private final Randomizable myRandomizer;
  private final List<Player> myPlayers;
  private final List<TileView> myTiles;
  private final List<String> myIcons;
  private final Scene myScene;
  private final Map<Player, ImageView> playerIconMapping;

  public MonopolyView(Scene myScene, Tab myTab, List<TileView> myTiles, List<Player> players,
      List<String> icons, Randomizable gameDie, List<Button> buttons) {
    this.myScene = myScene;
    myPlayers = players;
    this.myTiles = myTiles;
    myRandomizer = gameDie;
    myGameScreen = new BorderPane();
    myIcons = icons;
    createDiceView();
    playerIconMapping = new HashMap<>();
    setMappings();
    myBoard = new SquareBoardView(myTiles, myDie, players, new IconView(icons).getIcons());
    MenuFactory myMenuFactory = new MenuFactory(this);
    myButtons = new ButtonPanel(buttons, myMenuFactory.getGameMenuBar());
    makeScene();
    myGameScreen.setId(myTab.getText() + "GameScreen");
    myTab.setContent(myGameScreen);
  }

  /**
   * updates the View of the monopoly game by removing all the players from their old locations and
   * add them to their new tile locations. Creates a StatusBoardView with the new player list.
   *
   * @param activePlayer the Player whose turn is currently theirs
   */
  public void updateView(Player activePlayer) {
    removeAllPlayers();
    addAllPlayers();
    StatusBoardView newBoard = new StatusBoardView(myPlayers, playerIconMapping,
        myPlayers.indexOf(activePlayer));
    myScoreBoard.setTop(newBoard.createStatusBoard());
  }

  /**
   * Sets the tile views for the MonopolyView class
   *
   * @param newTiles the new TileViews that correspond to the new list being used by the model
   */
  public void setTileViews(List<TileView> newTiles) {
    myBoard.setTileViewList(newTiles);
    removeAllPlayers();
    myGameScreen.setLeft(myBoard.populateBoardView());
  }

  /**
   * A getter for the TileViews
   *
   * @return the list of TileViews currently in use by the MonopolyView
   */
  public List<TileView> getTileViews() {
    return myTiles;
  }

  /**
   * A getter for the DiceView
   *
   * @return the DiceView being used by the MonopolyView
   */
  public DiceView getDiceView() {
    return myDie;
  }

  /**
   * Used by the menu to change the board being used by monopoly view
   *
   * @param board String representing the new type of board
   */
  public void changeBoard(String board) {
    removeAllPlayers();
    myBoard = new BoardViewFactory(board, myTiles, myDie, myPlayers,
        new IconView(myIcons).getIcons()).getNewBoardView();
    myGameScreen.setLeft(myBoard.populateBoardView());
  }

  /**
   * Used by the menu to change the shape of each tiles in monopoly view
   *
   * @param shape String representing the new shape for the tiles
   */
  public void changeTileShape(String shape) {
    for (TileView tile : myTiles) {
      tile.getStyleClass().clear();
      tile.getStyleClass().add(shape);
    }
  }

  /**
   * Uses a ColorPickerAlert to get input regarding the new Tile Background color and changes the
   * style to match the input
   *
   * @param empty a useless string to match the parameters for the other methods used by the
   *              factory
   */
  public void changeTileBackgroundColor(String empty) {
    ColorPickerAlert newColorPicker = new ColorPickerAlert();
    String color = turnToHex(newColorPicker.showColorPicker());
    for (TileView tile : myTiles) {
      tile.setStyle(tile.getStyle() + "-fx-background-color:" + color + ";");
    }
  }

  /**
   * Uses a ColorPickerAlert to get input regarding the new Tile Border color and changes the style
   * to match the input
   *
   * @param empty a useless string to match the parameters for the other methods used by the
   *              factory
   */
  public void changeTileBorderColor(String empty) {
    ColorPickerAlert newColorPicker = new ColorPickerAlert();
    String color = turnToHex(newColorPicker.showColorPicker());
    for (TileView tile : myTiles) {
      tile.setStyle(tile.getStyle() + "-fx-border-color:" + color + ";");
    }
  }

  private void setMappings() {
    for (int i = 0; i < myPlayers.size(); i++) {
      playerIconMapping.put(myPlayers.get(i), new IconView(myIcons).getIcons().get(i));
    }
  }

  private void makeScene() {
    myGameScreen.setLeft(myBoard.populateBoardView());
    myScoreBoard = new BorderPane();
    myScoreBoard.setId("PlayerBoard");
    myScoreBoard.setBottom(myButtons.createButtonsBox());
    myGameScreen.setRight(myScoreBoard);
  }


  private void createDiceView() {
    myDie = new NormalDiceView(myRandomizer, myScene.getStylesheets().get(0));
  }

  private void addAllPlayers() {
    myBoard.initializePlayers();
  }

  private void removeAllPlayers() {
    myBoard.removePlayers();
  }

  /**
   * Changes the theme of the scene and resets the dice image to the correct colors
   *
   * @param sheet
   */
  public void changeTheme(String sheet) {
    myScene.getStylesheets().clear();
    myScene.getStylesheets().add(sheet + CSS);
    myDie.setDiceColor(sheet);
    myDie.resetDiceView();
  }

  private String turnToHex(String colorValue) {
    return HEX_CODE + colorValue.substring(2);
  }
}
