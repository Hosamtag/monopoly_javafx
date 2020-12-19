package ooga.view;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ooga.controller.GameController;
import ooga.view.gamedisplay.PlayerProfile;
import org.junit.jupiter.api.Test;
//import util.DukeApplicationTest;
import util.DukeTest;

class PlayerProfileTest extends DukeTest {
  private static final String INITIAL_STYLESHEET = "monopoly.css";

  private PlayerProfile myPlayerProfiles;
  private Stage myStage;
  private DiceView myDiceView;
  private Scene startScene;
  private VBox summary;

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
  void test_numberOfProfilesCreated(){
    summary = lookup("#summary").query();
    assertEquals(4, summary.getChildren().size());
  }

  @Test
  void test_testNameDisplay(){
    summary = lookup("#summary").query();
    ComboBox<BorderPane> combo = (ComboBox<BorderPane>)summary.getChildren().get(0);
    String name = combo.getValue().getLeft().toString();
    String expected = "Hosam";
    assertTrue(name.contains(expected));
  }

  @Test
  void test_nameIncorrect(){
    summary = lookup("#summary").query();
    assertFalse(summary.getChildren().size() == 1);
    ComboBox<BorderPane> combo = (ComboBox<BorderPane>)summary.getChildren().get(0);
    String name = combo.getValue().getLeft().toString();
    String expected = "LOSER";
    assertFalse(name.contains(expected));
  }

  @Test
  void test_PlayerInitialFunds(){
    summary = lookup("#summary").query();
    ComboBox<BorderPane> combo = (ComboBox<BorderPane>)summary.getChildren().get(0);
    String money = combo.getValue().getLeft().toString();
    String expected = "$2000";
    assertTrue(money.contains(expected));
  }


  @Test
  void test_PlayerWithProperties_initial(){
    summary = lookup("#summary").query();
    ComboBox combo = (ComboBox<BorderPane>)summary.getChildren().get(2);
    String expected = "Mediterranean Avenue";
    select(combo, expected);
    assertEquals(expected, combo.getValue().toString());
  }

  @Test
  void test_PlayerWithNoProperties_initial(){
    summary = lookup("#summary").query();
    ComboBox combo = (ComboBox<BorderPane>)summary.getChildren().get(3);
    String expected = "";
    select(combo, expected);
    assertEquals(expected, combo.getValue().toString());
  }

  @Test
  void test_PlayerWithHouses_initial(){
    summary = lookup("#summary").query();
    ComboBox<BorderPane> combo = (ComboBox<BorderPane>)summary.getChildren().get(2);
    HBox house = (HBox) combo.getItems().get(1).getRight();
    assertEquals(1, house.getChildren().size());
    assertTrue(house.getChildren().get(0).toString().contains("ImageView"));
  }


  @Test
  void test_PlayerWithHouses_AfterUpgrade(){
    Button button = lookup("Upgrade").queryButton();
    clickOn(button);
    String expected = "Baltic Avenue";
    writeInputsToDialog(expected);

    summary = lookup("#summary").query();
    ComboBox<BorderPane> combo = (ComboBox<BorderPane>)summary.getChildren().get(2);
    HBox house = (HBox) combo.getItems().get(1).getRight();
    assertEquals(2, house.getChildren().size());
    assertTrue(house.getChildren().get(0).toString().contains("ImageView"));
    assertTrue(house.getChildren().get(1).toString().contains("ImageView"));
  }

  @Test
  void test_PlayerWithHotelAfterHavingFourHouses_ThreeUpgrades(){
    Button button = lookup("Upgrade").queryButton();
    for(int i = 1; i<4; i++){
      clickOn(button);
      String expected = "Baltic Avenue";
      writeInputsToDialog(expected);
      VBox summary = lookup("#summary").query();
      ComboBox<BorderPane> combo = (ComboBox<BorderPane>)summary.getChildren().get(2);
      HBox house = (HBox) combo.getItems().get(1).getRight();
      assertEquals((i+1), house.getChildren().size());
      assertTrue(house.getChildren().get(0).toString().contains("ImageView"));
    }

    clickOn(button);
    String expected = "Baltic Avenue";
    writeInputsToDialog(expected);
    summary = lookup("#summary").query();
    ComboBox<BorderPane> combo = (ComboBox<BorderPane>)summary.getChildren().get(2);
    HBox hotel = (HBox) combo.getItems().get(1).getRight();
    assertEquals(1, hotel.getChildren().size());
    assertTrue(hotel.getChildren().get(0).toString().contains("ImageView"));
  }


}

