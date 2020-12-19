package ooga.view.alerts;

import java.util.Optional;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

/**
 * Creates the Upgrade Alert
 *
 * @author Hosam Tageldin
 */
public class UpgradeAlert extends MonopolyGameAlert {

  /**
   * Constructor
   */
  public UpgradeAlert() {
    super();
  }

  /**
   * Creates the upgrade pop up in the game
   *
   * @param inputPrompt the prompt of the alert
   * @param title       the title of the alert
   * @param header      the header of the alert
   * @return the String representing the property to upgrade
   */
  public String upgradePropertyAlert(String inputPrompt, String title, String header) {
    Dialog<String> dialog = createTextInputDialog(title, header);
    dialog.getDialogPane().setContent(createTextField(inputPrompt));
    Optional<String> result = dialog.showAndWait();
    String property = "";
    if (result.isPresent()) {
      property = input.getText();
    }
    return property;
  }

  private VBox createTextField(String inputPrompt) {
    VBox box = new VBox();
    input.setPromptText(inputPrompt);
    box.getChildren().addAll(input);
    return box;
  }
}
