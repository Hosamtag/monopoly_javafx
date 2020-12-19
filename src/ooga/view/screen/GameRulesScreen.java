package ooga.view.screen;

import java.util.ArrayList;
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
 * Page for the users to select the specific game rules read in from a properties file
 *
 * @author Hosam Tageldin
 */
public class GameRulesScreen extends SplashScreen {

  private static final String SPLASH_SCREEN_PATH = "properties/SplashScreen";
  private final ResourceBundle resourceBundle;
  private final List<ComboBox<String>> myRuleOptions;

  public GameRulesScreen(Tab pane) {
    super(pane);
    myRuleOptions = new ArrayList<>();
    resourceBundle = ResourceBundle.getBundle(SPLASH_SCREEN_PATH);
  }

  /**
   * @return list of strings representing the selected rules for the game
   */
  public List<String> getSelectedRules() {
    List<String> selectedRules = new ArrayList<>();
    for (ComboBox<String> ruleOption : myRuleOptions) {
      if (!ruleOption.getValue().equals("None")) {
        selectedRules.add(ruleOption.getValue());
      }
    }
    return selectedRules;
  }

  /**
   * Adds the header for the splash screen
   */
  protected void addContent() {
    Text header = new Text(ResourceUtil.getResourceValue("SelectRules"));
    header.setId("header");
    myRoot.getChildren().add(header);
  }

  /**
   * Adds the buttons supplied by the controller for this splash screen
   *
   * @param myButtons Buttons received from the SplashScreenButtonFactory to add onto the Splash
   */
  public void addButtons(List<Button> myButtons) {
    addRuleOptions();
    myRoot.getChildren().add(pageHolder);
    for (Button button : myButtons) {
      button.getStyleClass().add("splash-screen-button");
      myRoot.getChildren().add(button);
    }
  }

  private void addRuleOptions() {
    String[] ruleTypes = resourceBundle.getString("AllRuleTypes").split(",");
    for (String ruleType : ruleTypes) {
      Text ruleHeader = new Text(ResourceUtil.getResourceValue(ruleType));
      String[] specificRules = resourceBundle.getString(ruleType).split(",");
      ComboBox<String> ruleSelect = new ComboBox<>(
          FXCollections.observableArrayList(specificRules));
      ruleSelect.getSelectionModel().selectFirst();
      myRuleOptions.add(ruleSelect);
      pageHolder.getChildren().addAll(ruleHeader, ruleSelect);
    }
  }
}
