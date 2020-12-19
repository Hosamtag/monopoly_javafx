package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.view.TileView;
import org.junit.jupiter.api.Test;
import util.DukeTest;

public class SwapClickHandlerTest extends DukeTest {
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
    javafxRun(()-> press(KeyCode.T));
  }

  @Test
  public void testBasicTileSwitch(){
    TileView tile5 = lookup("#tile5").query();
    javafxRun(()->clickOn(tile5));
    TileView tile6 = lookup("#tile6").query();
    javafxRun(()->clickOn(tile6));
    List<TileView> tileViewList = c.getMyGameView().getTileViews();
    assertEquals(tileViewList.get(5),tile6);
    assertEquals(tileViewList.get(6),tile5);
  }

  @Test
  public void testEdgeCaseSwitch(){
    TileView tile27 = lookup("#tile27").query();
    javafxRun(()->clickOn(tile27));
    TileView tile1 = lookup("#tile1").query();
    javafxRun(()->clickOn(tile1));
    List<TileView> tileViewList = c.getMyGameView().getTileViews();
    assertEquals(tileViewList.get(27),tile1);
    assertEquals(tileViewList.get(1),tile27);
  }

  @Test
  public void testSwitchWithPlayersOnTiles(){
    TileView tile25 = lookup("#tile25").query();
    javafxRun(()->clickOn(tile25));
    TileView tile0 = lookup("#tile0").query();
    javafxRun(()->clickOn(tile0));
    List<TileView> tileViewList = c.getMyGameView().getTileViews();
    assertEquals(tileViewList.get(25),tile0);
    assertEquals(tileViewList.get(0),tile25);
  }

}
