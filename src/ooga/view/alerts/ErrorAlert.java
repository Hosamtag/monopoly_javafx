package ooga.view.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import ooga.controller.util.ResourceUtil;

/**
 * Creates an Error Pop Up dialog alert window to display an error message
 *
 * @author Isabella Knox
 */
public class ErrorAlert {

  /**
   * Use for displaying error information alert for the user
   *
   * @param message the message to be displayed in the error alert
   */
  public void createErrorAlert(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(ResourceUtil.getResourceValue("ErrorTitle"));
    alert.setHeaderText(ResourceUtil.getResourceValue("ErrorHeader"));
    alert.setContentText(message);
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.showAndWait();
  }


}
