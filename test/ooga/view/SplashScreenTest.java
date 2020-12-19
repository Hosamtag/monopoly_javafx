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


public class SplashScreenTest extends DukeTest {
  private static final String INITIAL_STYLESHEET = "monopoly.css";
  private GameController c;

  @Override
  public void start(Stage stage) {
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
  void testSplashScreenWalkThrough_1(){
    javafxRun(()->clickOn("New Game"));
    javafxRun(()->clickOn("Use Default Rules"));
    javafxRun(()->clickOn("Continue"));
    javafxRun(()->clickOn("Save"));
  }

  @Test
  void testSplashScreenWalkThroughToOwnRules(){
    javafxRun(()->clickOn("New Game"));
    javafxRun(()->clickOn("Choose your own Rules!"));
    javafxRun(()->clickOn("Continue"));
    javafxRun(()->clickOn("Continue"));
  }

  @Test
  void testNoDuplicatePlayers(){
    javafxRun(()->clickOn("New Game"));
    javafxRun(()->clickOn("Use Default Rules"));
    javafxRun(()->clickOn("Continue"));
    javafxRun(()->clickOn("Save"));
    javafxRun(()->clickOn("Save"));
    assertEquals("No duplicate player names, please!",getDialogMessage());
  }

  @Test
  void testSplashScreenWalkThroughToGame(){
    javafxRun(()->clickOn("New Game"));
    javafxRun(()->clickOn("Use Default Rules"));
    javafxRun(()->clickOn("Continue"));
    javafxRun(()->clickOn("Save"));
    write(lookup("#PlayerNameInput").queryTextInputControl(),"H");
    javafxRun(()->clickOn("Save"));
    TileView tile0 = lookup("#tile0").query();
    assertEquals(2,tile0.numOfPlayersOnTile());
  }

  @Test
  void endGameScreenSaveLocally(){
    javafxRun(()->press(KeyCode.S));
    javafxRun(()->press(KeyCode.E));
    javafxRun(()->clickOn("Save High Score Locally"));
  }

  @Test
  void endGameScreenStartNewGame(){
    javafxRun(()->press(KeyCode.S));
    javafxRun(()->press(KeyCode.E));
    javafxRun(()->clickOn("Start New Game"));
  }
}
