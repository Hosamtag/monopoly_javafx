package ooga.view.alerts;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import ooga.controller.util.ResourceUtil;

/**
 * Creates a color picker dialog alert pop up for the border of the tiles and the background to
 * change
 *
 * @author Hosam Tageldin, Isabella Knox
 */
public class ColorPickerAlert {

  /**
   * Shows the Alert with the ColorPicker, default value is set to white
   *
   * @return String representing the hex value that the player selected
   */
  public String showColorPicker() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(ResourceUtil.getResourceValue("PickColor"));
    alert.setHeaderText(ResourceUtil.getResourceValue("Pick"));
    ColorPicker newPicker = new ColorPicker();
    alert.setGraphic(newPicker);
    alert.showAndWait();
    return newPicker.getValue().toString();
  }
}
