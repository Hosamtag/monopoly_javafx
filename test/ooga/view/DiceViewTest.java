package ooga.view;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.GameController;
import org.junit.jupiter.api.Test;
import util.DukeTest;
//import util.DukeApplicationTest;

class DiceViewTest extends DukeTest {
  private static final String INITIAL_STYLESHEET = "monopoly.css";

  private Stage myStage;
  private DiceView myDiceView;
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
  void testHandleMethod_RollDisplaysImageView() {
    javafxRun(()-> press(KeyCode.G));
    myDiceView = c.getMyGameView().getDiceView();
    javafxRun(()->myDiceView.createDiceBox());
    assertTrue(!myDiceView.getGameDie().get(0).getChildren().isEmpty());
    assertTrue((myDiceView.getGameDie().get(0).getChildren().get(0).toString().contains("ImagePattern")));
  }

  @Test
  void test_numberOfDice(){
    javafxRun(()-> press(KeyCode.G));
    myDiceView = c.getMyGameView().getDiceView();
    assertEquals(1, myDiceView.getGameDie().size());
  }

  @Test
  void test_NSidedDice_NumberOfDice(){
    javafxRun(()-> press(KeyCode.T));
    myDiceView = c.getMyGameView().getDiceView();
    assertEquals(2, myDiceView.getGameDie().size());
  }


  @Test
  void test_NSidedDice_FilledCorrectText(){
    javafxRun(()-> press(KeyCode.T));
    myDiceView = c.getMyGameView().getDiceView();
    javafxRun(()->myDiceView.createDiceBox());
    Rectangle die = (Rectangle) myDiceView.getGameDie().get(0).getChildren().get(0);
    assertTrue(!myDiceView.getGameDie().get(0).getChildren().isEmpty());
    assertFalse((myDiceView.getGameDie().get(0).getChildren().get(0).toString().contains("ImagePattern")));
    HBox dice = lookup("#diceBox").query();
    StackPane dice1 = (StackPane) dice.getChildren().get(0);
    Text value = (Text) dice1.getChildren().get(1);
    assertTrue(value.getText().equals("1"));
  }

  @Test
  void test_NSidedDice_Color_initial(){
    javafxRun(()-> press(KeyCode.T));
    myDiceView = c.getMyGameView().getDiceView();
    javafxRun(()->myDiceView.createDiceBox());
    Rectangle die = (Rectangle) myDiceView.getGameDie().get(0).getChildren().get(0);
    assertTrue(!myDiceView.getGameDie().get(0).getChildren().isEmpty());
    assertFalse((myDiceView.getGameDie().get(0).getChildren().get(0).toString().contains("ImagePattern")));
    HBox dice = lookup("#diceBox").query();
    StackPane dice1 = (StackPane) dice.getChildren().get(0);
    Rectangle value = (Rectangle) dice1.getChildren().get(0);
    assertEquals(Paint.valueOf("RED"), value.getFill());
  }

  @Test
  void test_NSidedDice_ThemeChange_Duke(){
    javafxRun(()-> press(KeyCode.T));
    clickOn("Menu");
    clickOn("Choose Theme");
    moveTo("monopoly");
    clickOn("duke");
    HBox dice = lookup("#diceBox").query();
    StackPane dice1 = (StackPane) dice.getChildren().get(0);
    Rectangle value = (Rectangle) dice1.getChildren().get(0);
    assertEquals(Paint.valueOf("BLUE"), value.getFill());
  }

  @Test
  void test_NSidedDice_ThemeChange_Dark(){
    javafxRun(()-> press(KeyCode.T));
    clickOn("Menu");
    clickOn("Choose Theme");
    moveTo("monopoly");
    clickOn("dark");
    HBox dice = lookup("#diceBox").query();
    StackPane dice1 = (StackPane) dice.getChildren().get(0);
    Rectangle value = (Rectangle) dice1.getChildren().get(0);
    assertEquals(Paint.valueOf("BLACK"), value.getFill());
  }

  @Test
  void test_NSidedDice_Roll_ColorStaysTheSame(){
    javafxRun(()-> press(KeyCode.T));
    myDiceView = c.getMyGameView().getDiceView();
    javafxRun(()->myDiceView.createDiceBox());
    myDiceView.start();
    HBox dice = lookup("#diceBox").query();
    StackPane dice1 = (StackPane) dice.getChildren().get(0);
    Rectangle value = (Rectangle) dice1.getChildren().get(0);
    assertEquals(Paint.valueOf("RED"), value.getFill());
  }

}