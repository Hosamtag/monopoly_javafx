package ooga.view;

import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * Abstract SplashScreen class to initialize the Id of the VBox and the FlowPane used by the
 * subclasses
 *
 * @author Hosam Tageldin
 */
public abstract class SplashScreen {

  final protected FlowPane pageHolder;
  final protected VBox myRoot = new VBox();

  public SplashScreen(Tab pane) {
    myRoot.setId("SplashScreenBackground");
    pageHolder = new FlowPane();
    pageHolder.setId("gameRules");
    addContent();
    pane.setContent(myRoot);
  }

  /**
   * Adds the content when called by the the constructor to add the content of the appropriate
   * subclass
   */
  protected abstract void addContent();

  /**
   * Adds the buttons to be displayed on the splash screen
   *
   * @param myButtons Buttons received from the SplashScreenButtonFactory to add onto the Splash
   *                  Screen
   */
  public abstract void addButtons(List<Button> myButtons);


}
