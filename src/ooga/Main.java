package ooga;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ooga.controller.GameController;

/**
 * Main class launches the program through the use of a TabPane so that multiple games can be played
 * on the same screen
 *
 * @author Hosam Tageldin
 */
public class Main extends Application {

  private static final String INITIAL_STYLESHEET = "monopoly.css";

  /**
   * Sets the initial Stylesheet and Calls the GameController to populate the scene of the
   * controller
   *
   * @param stage the stage to be populated with the game
   */
  @Override
  public void start(Stage stage) {
    stage.setResizable(false);
    stage.initStyle(StageStyle.UNDECORATED);
    Scene myScene = new Scene(new Group());
    myScene.getStylesheets().add(INITIAL_STYLESHEET);
    TabPane gamePane = new TabPane();
    Tab closeGameTab = new Tab("Close Game");
    closeGameTab.setOnSelectionChanged(e -> stage.close());
    closeGameTab.setOnClosed(e -> stage.close());
    Tab initialTab = new Tab();
    gamePane.getTabs().addAll(initialTab, closeGameTab);
    new GameController(myScene, initialTab);
    myScene.setRoot(gamePane);
    stage.setScene(myScene);
    stage.show();
  }

  /**
   * Start of the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
