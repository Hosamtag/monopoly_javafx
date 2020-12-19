package ooga.view.screen;

import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import ooga.view.SplashScreen;

/**
 * The initial page that is shown when the program is run. Shows the possible options and a language
 * select box.
 *
 * @author Hosam Tageldin
 */
public class LandingPageScreen extends SplashScreen {

  private static final String START_SCREEN_PROPERTIES = "properties/HomePage";
  private ComboBox<String> languageSelect;
  private ResourceBundle resourceBundle;

  public LandingPageScreen(Tab pane) {
    super(pane);
  }

  /**
   * Adds the header of the landing page and sets the Resource bundle of the start screen
   */
  protected void addContent() {
    resourceBundle = ResourceBundle.getBundle(START_SCREEN_PROPERTIES);
    Text header = new Text(resourceBundle.getString("GameName"));
    header.setId("header");
    myRoot.getChildren().add(header);
  }

  /**
   * Allows the ResourceUtil to be set based on user selection on the start screen
   *
   * @return String representing the language selected by the player
   */
  public String getLanguage() {
    return languageSelect.getSelectionModel().getSelectedItem();
  }

  /**
   * Adds the buttons supplied by the Controller to be displayed on this screen
   *
   * @param myButtons Buttons received from the SplashScreenButtonFactory to add onto the Splash
   */
  public void addButtons(List<Button> myButtons) {
    for (Button button : myButtons) {
      pageHolder.getChildren().addAll(button);
      button.getStyleClass().add("splash-screen-button");
    }
    addLanguageOptions();
    myRoot.getChildren().add(pageHolder);
  }

  private void addLanguageOptions() {
    Text selectLanguage = new Text(resourceBundle.getString("ChooseLanguage"));
    String[] languageOptions = resourceBundle.getString("LanguageOptions").split(",");
    languageSelect = new ComboBox<>(FXCollections.observableArrayList(languageOptions));
    languageSelect.getSelectionModel().selectFirst();
    pageHolder.getChildren().addAll(selectLanguage, languageSelect);
  }
}
