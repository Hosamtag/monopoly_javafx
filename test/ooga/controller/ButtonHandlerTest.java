package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.model.Mortgageable;
import ooga.model.Upgradable;
import org.junit.jupiter.api.Test;
import util.DukeTest;

public class ButtonHandlerTest extends DukeTest {
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
  public void testSimpleUpgradeButton(){
    Upgradable tile = (Upgradable) c.getPlayers().get(0).getOwnables().get(1);
    assertEquals(0,tile.getCurrentBuilding().ordinal());
    clickOn("Upgrade");
    writeInputsToDialog("Oriental Avenue");
    assertEquals(1,tile.getCurrentBuilding().ordinal());
  }

  @Test
  public void testDoubleUpgrade(){
    Upgradable tile = (Upgradable) c.getPlayers().get(0).getOwnables().get(1);
    assertEquals(0,tile.getCurrentBuilding().ordinal());
    clickOn("Upgrade");
    writeInputsToDialog("Oriental Avenue");
    clickOn("Upgrade");
    writeInputsToDialog("Oriental Avenue");
    assertEquals(2,tile.getCurrentBuilding().ordinal());
  }

  @Test
  public void testInvalidUpgrade(){
    clickOn("Upgrade");
    writeInputsToDialog("Medijdsioaj");
    assertTrue(getDialogMessage().equals("No Player owns Medijdsioaj"));
  }

  @Test
  public void testTrade(){
    clickOn("Trade");
    writeInputsToDialog("Oriental Avenue");
    assertEquals("Oriental Avenue",c.getPlayers().get(0).getOwnables().get(2).getName());
  }

  @Test
  void testChangeToMortgage(){
    javafxRun(()->clickOn("Change Mortgage"));
    writeInputsToDialog("Vermont Avenue");
    Mortgageable property = (Mortgageable)c.getPlayers().get(0).getOwnables().get(0);
    assertTrue(property.checkIfMortgaged());
  }

  @Test
  void testChangeToUnMortgage(){
    javafxRun(()->clickOn("Change Mortgage"));
    writeInputsToDialog("Vermont Avenue");
    Mortgageable property = (Mortgageable)c.getPlayers().get(0).getOwnables().get(0);
    assertTrue(property.checkIfMortgaged());
    javafxRun(()->clickOn("Change Mortgage"));
    writeInputsToDialog("Vermont Avenue");
    assertFalse(property.checkIfMortgaged());
  }

  @Test
  void testChangeToMortgageErrorThrown(){
    javafxRun(()->clickOn("Change Mortgage"));
    writeInputsToDialog("Veon nue");
    assertTrue(getDialogMessage().equals("No Player owns Veon nue"));
  }

  @Test
  void testAddGame(){
    javafxRun(()->clickOn("Add Game"));
    javafxRun(()->clickOn("New Game"));

  }

  @Test
  void testSaveButton(){
    javafxRun(()->clickOn("Save Game"));

  }

}
