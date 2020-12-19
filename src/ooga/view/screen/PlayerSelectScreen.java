package ooga.view.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import ooga.controller.util.ResourceUtil;
import ooga.view.SplashScreen;

/**
 * SplashScreen for the user to select the icon and names for a game player
 *
 * @author Hosam Tageldin
 */
public class PlayerSelectScreen extends SplashScreen {

  private static final int ICON_SIZE = 40;
  private static final String ICON_PROPERTIES = "properties/Icon";
  private TextField playerName;
  private final List<String> updatedIcons;
  private List<ImageView> iconOptions;
  private ComboBox<ImageView> iconSelect;


  public PlayerSelectScreen(Tab pane, List<String> iconsToRemove, String gameVersion) {
    super(pane);
    ResourceBundle resourceBundle = ResourceBundle.getBundle(ICON_PROPERTIES);
    updatedIcons = new ArrayList<>(List.of(resourceBundle.getString(gameVersion).split(",")));
    updatedIcons.removeAll(iconsToRemove);
    initializeList();
  }

  /**
   * @return String representing the chosen name for this player
   */
  public String getPlayerName() {
    return playerName.getText();
  }

  /**
   * @return String representing the chosen icon for this player
   */
  public String getPlayerIcon() {
    return updatedIcons.get(iconSelect.getSelectionModel().getSelectedIndex());
  }

  /**
   * Adds the header for the PlayerSelectScreen
   */
  protected void addContent() {
    iconOptions = new ArrayList<>();
    Text playerText = new Text(ResourceUtil.getResourceValue("CreatePlayer"));
    playerText.setId("header");
    myRoot.getChildren().add(playerText);
  }

  /**
   * Adds the buttons supplied by the controller to be displayed on this splash screen
   *
   * @param myButtons Buttons received from the SplashScreenButtonFactory to add onto the Splash
   */
  public void addButtons(List<Button> myButtons) {
    Text selectIconPrompt = new Text(ResourceUtil.getResourceValue("PlayerIcon"));
    iconSelect = new ComboBox<>(FXCollections.observableArrayList(iconOptions));
    iconSelect.getSelectionModel().selectFirst();
    Text selectPlayerPrompt = new Text(ResourceUtil.getResourceValue("PlayerName"));
    playerName = new TextField();
    playerName.setId("PlayerNameInput");
    pageHolder.getChildren().addAll(selectIconPrompt, iconSelect, selectPlayerPrompt, playerName);
    for (Button button : myButtons) {
      button.getStyleClass().add("splash-screen-button");
      pageHolder.getChildren().add(button);
    }
    myRoot.getChildren().addAll(pageHolder);
  }

  private void initializeList() {
    for (String icon : updatedIcons) {
      ImageView img = new ImageView(
          new Image(this.getClass().getClassLoader().getResourceAsStream(icon)));
      img.setFitHeight(ICON_SIZE);
      img.setPreserveRatio(true);
      iconOptions.add(img);
    }
  }
}
