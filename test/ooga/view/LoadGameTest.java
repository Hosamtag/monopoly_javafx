package ooga.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.controller.GameController;
import org.junit.jupiter.api.Test;
import util.DukeTest;

public class LoadGameTest extends DukeTest {
  private static final String INITIAL_STYLESHEET = "monopoly.css";

  private Stage myStage;
  private DiceView myDiceView;
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
  }

  @Test
  void testLoadedGame(){
    javafxRun(()-> press(KeyCode.T));
    TileView tile = lookup("#tile2").query();
    int expected = 0;
    int actual = tile.numOfPlayersOnTile();
    assertEquals(expected,actual);
  }

  @Test
  void testLoadGameButton(){
    javafxRun(()-> press(KeyCode.T));
    TileView tile = lookup("#tile2").query();
    int expected = 0;
    int actual = tile.numOfPlayersOnTile();
    assertEquals(expected,actual);
  }

}
