package ooga.view.gamedisplay;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.FlowPane;

/**
 * Creates the button panel with the buttons read in from the properties files for a specific agme
 *
 * @author Hosam Tageldin
 */
public class ButtonPanel {

  private final MenuBar myMenu;
  private final List<Button> gameButtons;

  public ButtonPanel(List<Button> gameButtons, MenuBar gameMenu) {
    this.gameButtons = gameButtons;
    myMenu = gameMenu;
  }

  /**
   * @return a Node with all of the buttons supplied from the constructor
   */
  public Node createButtonsBox() {
    FlowPane newOne = new FlowPane();
    newOne.setId("AllButtons");
    for (Button gameButton : gameButtons) {
      newOne.getChildren().add(gameButton);
    }
    newOne.getChildren().add(myMenu);
    return newOne;
  }
}
