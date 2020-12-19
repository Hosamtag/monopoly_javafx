package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.model.Tile;
import ooga.view.TileView;
import org.junit.jupiter.api.Test;
import util.DukeTest;

public class CheatKeyTest extends DukeTest {
  private static final String INITIAL_STYLESHEET = "monopoly.css";

  private Scene startScene;
  private GameController c;

  @Override
  public void start (Stage stage) {
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
  void testCheatKeySkipGame(){
    javafxRun(()-> press(KeyCode.T));
    assertTrue(c.getPlayers().get(0).getName().equals("Hosam"));
    assertTrue(c.getPlayers().get(0).getOwnables().get(0).getName().equals("Vermont Avenue"));
  }

  @Test
  void testCheatKeyDigit0_NoMovePlayer(){
    javafxRun(()-> press(KeyCode.T));
    Tile currentTile = c.getPlayers().get(0).getCurrentTile();
    javafxRun(()->press(KeyCode.DIGIT0));
    Tile newTile = c.getPlayers().get(0).getCurrentTile();
    assertEquals(currentTile,newTile);
  }

  @Test
  void testCheatKeyDigit4_MovePlayer(){
    javafxRun(()-> press(KeyCode.T));
    Tile currentTile = c.getPlayers().get(0).getCurrentTile();
    javafxRun(()->press(KeyCode.DIGIT4));
    javafxRun(()-> clickOn("OK"));
    Tile newTile = c.getPlayers().get(0).getCurrentTile();
    assertEquals(currentTile,newTile);
  }

  @Test
  void testCheatKeyDigit4_MovesPlayer4(){
    javafxRun(()-> press(KeyCode.T));
    TileView tile4 = lookup("#tile4").query();
    int initial = tile4.numOfPlayersOnTile();
    assertEquals(0,initial);
    javafxRun(()->press(KeyCode.DIGIT4));
    int postJump = tile4.numOfPlayersOnTile();
    assertEquals(1,postJump);
  }

  @Test
  void increasePlayerMoneyIncreasesBy100(){
    javafxRun(()-> press(KeyCode.T));
    int playerCurrentFunds = c.getPlayers().get(2).getFunds();
    javafxRun(()->press(KeyCode.A));
    javafxRun(()->press(KeyCode.A));
    javafxRun(()->press(KeyCode.A));
    int playerNewFunds = c.getPlayers().get(2).getFunds();
    assertTrue(playerCurrentFunds < playerNewFunds);
  }

  @Test
  void decreasePlayerMoneyDecreasesBy100(){
    javafxRun(()-> press(KeyCode.T));
    int playerCurrentFunds = c.getPlayers().get(2).getFunds();
    javafxRun(()->press(KeyCode.D));
    javafxRun(()->press(KeyCode.D));
    javafxRun(()->press(KeyCode.D));
    int playerNewFunds = c.getPlayers().get(2).getFunds();
    assertTrue(playerCurrentFunds > playerNewFunds);
  }


}
