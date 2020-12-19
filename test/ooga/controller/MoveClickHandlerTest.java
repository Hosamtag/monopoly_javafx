package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.view.TileView;
import org.junit.jupiter.api.Test;
import util.DukeTest;

public class MoveClickHandlerTest extends DukeTest {
  private static final String INITIAL_STYLESHEET = "monopoly.css";

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
    javafxRun(()-> press(KeyCode.G));
  }

  @Test
  public void testMoveClickHandlerToRailRoad(){
    TileView tile5 = lookup("#tile5").query();
    javafxRun(()->clickOn(tile5));
    javafxRun(()->clickOn("Yes"));
    assertEquals(1,tile5.numOfPlayersOnTile());
  }


  @Test
  public void testMoveClickHandlerToCornerTile(){
    TileView tile10 = lookup("#tile10").query();
    javafxRun(()->clickOn(tile10));
    //clickOn(lookup("OK").queryButton());
    //Right when this test starts to fail, uncomment the above line to clear the popup
    assertEquals(1,tile10.numOfPlayersOnTile());
  }

  @Test
  public void testMoveClickHandlerToCurrentTile(){
    TileView tile0 = lookup("#tile0").query();
    assertEquals(4,tile0.numOfPlayersOnTile());
    javafxRun(()->clickOn(tile0));
    assertEquals(4,tile0.numOfPlayersOnTile());
  }


}
