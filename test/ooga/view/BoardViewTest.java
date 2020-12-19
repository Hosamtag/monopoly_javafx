package ooga.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.MouseInfo;
import java.awt.Point;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.controller.GameController;
import org.junit.jupiter.api.Test;
import util.DukeTest;
//import util.DukeApplicationTest;

public class BoardViewTest extends DukeTest {
  private static final String INITIAL_STYLESHEET = "monopoly.css";

  private Stage myStage;
  private Button rollButton;
  private Scene startScene;

  @Override
  public void start (Stage stage) {
    GameController c;
    stage.setResizable(false);
    Scene myScene = new Scene(new Group());
    myScene.getStylesheets().add(INITIAL_STYLESHEET);
    TabPane gamePane = new TabPane();
    Tab closeGameTab = new Tab("Close Game");
    closeGameTab.setOnSelectionChanged(e->stage.close());
    closeGameTab.setOnClosed(e->stage.close());
    Tab initialTab = new Tab();
    gamePane.getTabs().addAll(initialTab,closeGameTab);
    c = new GameController(myScene,initialTab);
    myScene.setRoot(gamePane);
    stage.setScene(myScene);
    stage.show();
    javafxRun(()-> press(KeyCode.T));
  }

  @Test
  void testPlayersShownOnGoTile(){
    TileView tile = lookup("#tile0").query();
    int expected = 4;
    assertEquals(expected, tile.numOfPlayersOnTile());
  }

  @Test
  void testNoPlayersShownOnOtherTiles(){
    TileView tile = lookup("#tile1").query();
    int expected = 0;
    assertEquals(expected, tile.numOfPlayersOnTile());
  }

  @Test
  void testRemovePlayerFromTile(){
    TileView tile = lookup("#tile0").query();
    javafxRun(()->tile.removePlayer(lookup("#playerView0").query()));
    assertEquals(3,tile.numOfPlayersOnTile());
  }

  @Test
  void testAddPlayerToTile(){
    TileView tile = lookup("#tile10").query();
    javafxRun(()->tile.addPlayer(lookup("#playerView0").query()));
    assertEquals(1,tile.numOfPlayersOnTile());
  }

  @Test
  void testCheatKey(){
    type(KeyCode.DIGIT5);
    TileView tile = lookup("#tile5").query();
    assertEquals(1,tile.numOfPlayersOnTile());
  }

  @Test
  void testCheatKeyEdgeCase(){
    TileView tile = lookup("#tile15").query();
    javafxRun(()->tile.addPlayer(lookup("#playerView0").query()));
    type(KeyCode.DIGIT5);
    TileView tile0 = lookup("#tile0").query();
    assertEquals(3,tile0.numOfPlayersOnTile());
  }

  @Test
  void testChangeToGridBoard(){
    TileView tile5 = lookup("#tile5").query();
    moveTo(tile5);
    Point p = MouseInfo.getPointerInfo().getLocation();
    clickOn("Menu");
    clickOn("Choose Board");
    moveTo("Square");
    clickOn("Grid");
    moveTo(tile5);
    Point p2 = MouseInfo.getPointerInfo().getLocation();
    assertNotEquals(p,p2);
  }

  @Test
  void testChangeShapeToCircle(){
    clickOn("Menu");
    clickOn("Choose Tile Shape");
    moveTo("rectangle");
    clickOn("circle");
  }

  @Test
  void testChangeShapeToHexagon(){
    clickOn("Menu");
    clickOn("Choose Tile Shape");
    moveTo("rectangle");
    clickOn("hexagon");
  }

  @Test
  void testChangeBackToSquareBoard() {
    TileView tile5 = lookup("#tile5").query();
    moveTo(tile5);
    Point p = MouseInfo.getPointerInfo().getLocation();
    clickOn("Menu");
    clickOn("Choose Board");
    moveTo("Square");
    clickOn("Grid");
    moveTo(tile5);
    Point p2 = MouseInfo.getPointerInfo().getLocation();
    clickOn("Menu");
    clickOn("Choose Board");
    clickOn("Square");
    moveTo(tile5);
    Point p3 = MouseInfo.getPointerInfo().getLocation();
    assertNotEquals(p, p2);
    assertEquals(p, p3);
  }

  @Test
  void testDieRolls(){
    TileView tile0 = lookup("#tile0").query();
    int start = tile0.numOfPlayersOnTile();
    javafxRun(()->clickOn("Roll"));
    int end = tile0.numOfPlayersOnTile();
    assertNotEquals(start,end);
  }

  @Test
  void testTileBorderColorPicker(){
    clickOn("Menu");
    clickOn("Choose Tile Color");
    moveTo("Background");
    clickOn("Border");
    writeInputsToDialog("");
    TileView tile0 = lookup("#tile0").query();
    assertEquals("-fx-border-color:#ffffffff;",tile0.getStyle());
  }
}
