package ooga.view.screen;

import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import ooga.controller.util.ResourceUtil;
import ooga.view.SplashScreen;

/**
 * Represents the SplashScreen for users to select the game version to be played. The game versions
 * are all read in through a properties file. Buttons for the splash screen are supplied by the
 * controller.
 *
 * @author Hosam Tageldin
 */
public class GameSelectScreen extends SplashScreen {

  private static final String SPLASH_SCREEN_PATH = "properties/SplashScreen";
  private final ResourceBundle resourceBundle;
  private ComboBox<String> myGameSelect;

  public GameSelectScreen(Tab pane) {
    super(pane);
    resourceBundle = ResourceBundle.getBundle(SPLASH_SCREEN_PATH);
  }

  /**
   * @return String representing the game chosen by the user
   */
  public String getChosenVersion() {
    return myGameSelect.getValue().toLowerCase();
  }

  /**
   * Adds the Header of the GameSelect screen
   */
  protected void addContent() {
    Text header = new Text(ResourceUtil.getResourceValue("GameVersion"));
    header.setId("header");
    myRoot.getChildren().add(header);
  }

  /**
   * Adds the buttons supplied by the controller to add to this screen
   *
   * @param myButtons Buttons received from the SplashScreenButtonFactory to add onto the Splash
   */
  public void addButtons(List<Button> myButtons) {
    String[] gameOptions = resourceBundle.getString("PossibleGames").split(",");
    myGameSelect = new ComboBox<>(FXCollections.observableArrayList(gameOptions));
    myGameSelect.getSelectionModel().selectFirst();
    myRoot.getChildren().add(myGameSelect);
    for (Button button : myButtons) {
      button.getStyleClass().add("splash-screen-button");
      myRoot.getChildren().add(button);
    }
  }
}
