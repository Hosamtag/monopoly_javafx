package ooga.controller.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidObjectErrorException;

/**
 * Factory to create the buttons in the data file associated with a game
 *
 * @author Hosam Tageldin
 */
public class ButtonFactory {

  private static final String BUTTONS_PATH = "gameversions/buttonMethods";
  private final List<Button> myButtons;
  private final Object eventHandler;
  private final String[] myButtonLabels;
  private final ResourceBundle resourceBundle;

  /**
   * Creates the buttons from the parameters
   *
   * @param eventHandler holds the methods associated with the button setOnAction
   * @param buttonLabels the label to give each button
   */
  public ButtonFactory(Object eventHandler, String[] buttonLabels) {
    this.eventHandler = eventHandler;
    myButtons = new ArrayList<>();
    myButtonLabels = buttonLabels;
    resourceBundle = ResourceBundle.getBundle(BUTTONS_PATH);
    createButtons();
  }

  private void createButtons() {
    for (String buttonLabel : myButtonLabels) {
      Button newGameButton = new Button(ResourceUtil.getResourceValue(buttonLabel));
      newGameButton.setId(ResourceUtil.getResourceValue(buttonLabel).toLowerCase());
      newGameButton.setOnAction(event -> {
        try {
          Method m = this.eventHandler.getClass()
              .getDeclaredMethod(resourceBundle.getString(buttonLabel));
          m.invoke(this.eventHandler);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          throw new InvalidObjectErrorException(
              String.format(ResourceUtil.getResourceValue("ButtonException"), buttonLabel));
        }
      });

      myButtons.add(newGameButton);
    }
  }

  /**
   * @return List of buttons created by the factory
   */
  public List<Button> getButtons() {
    return myButtons;
  }
}
