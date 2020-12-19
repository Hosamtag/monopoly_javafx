package ooga.controller.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import ooga.view.alerts.ErrorAlert;
import ooga.view.SplashScreen;

/**
 * Creates the buttons found in each splash screen
 *
 * @author Hosam Tageldin
 */
public class SplashScreenButtonFactory {

  private static final String BUTTONS_PATH = "properties/SplashScreen";
  private static final String LABEL = "Label";
  private final List<Button> myButtons;
  private final Object eventHandler;
  private final String[] myButtonLabels;
  private final ResourceBundle resourceBundle;
  private final SplashScreen eventCaller;

  /**
   * Constructor to create the splash screen buttons based on the parameters
   *
   * @param eventHandler the class with the associated method of the button's setOnAction
   * @param eventCaller  the SplashScreen class to call this method on
   */
  public SplashScreenButtonFactory(Object eventHandler, SplashScreen eventCaller) {
    this.eventHandler = eventHandler;
    this.eventCaller = eventCaller;
    myButtons = new ArrayList<>();
    resourceBundle = ResourceBundle.getBundle(BUTTONS_PATH);
    myButtonLabels = resourceBundle.getString(eventCaller.getClass().getSimpleName()).split(",");
    createButtons();
  }

  private void createButtons() {
    for (String buttonLabel : myButtonLabels) {
      Button newGameButton = new Button(resourceBundle.getString(buttonLabel + LABEL));
      newGameButton.setOnAction(event -> {
        try {
          Method m = this.eventHandler.getClass()
              .getDeclaredMethod(resourceBundle.getString(buttonLabel), eventCaller.getClass());
          m.invoke(this.eventHandler, this.eventCaller);
        } catch (Exception e) {
          ErrorAlert alert = new ErrorAlert();
          alert.createErrorAlert(e.getMessage());
        }
      });
      myButtons.add(newGameButton);
    }
  }

  /**
   * @return the list of buttons associated with a specific splash screen
   */
  public List<Button> getButtons() {
    return myButtons;
  }
}
