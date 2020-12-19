package ooga.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The card view to display details of specific tiles used in the game. Sets the card location to be
 * in the top right of the screen to not interfere with the game play.
 *
 * @author Hosam Tageldin
 */
public abstract class CardView extends Stage {

  private final static String INITIAL_STYLESHEET = "monopoly.css";
  private final static int CARD_WIDTH = 400;
  protected VBox myDescription;
  protected BorderPane myCard;

  public CardView(String name) {
    initStyle(StageStyle.UNDECORATED);
    myCard = new BorderPane();
    myDescription = new VBox();
    myDescription.setId("PropertyDescription");
    HBox titleBox = new HBox(new Text(name));
    titleBox.getStyleClass().add("cardTitle");
    myDescription.getChildren().add(titleBox);
    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    this.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - CARD_WIDTH);
    this.setY(primaryScreenBounds.getMinY());
    this.setWidth(CARD_WIDTH);
    myCard.setBottom(myDescription);
    Scene newScene = new Scene(myCard);
    newScene.getStylesheets().add(INITIAL_STYLESHEET);
    setScene(newScene);
  }

  /**
   * Adds an HBox with a line of text into the CardView
   *
   * @param descriptionLine line to add as Text into an HBox in the CardView
   */
  protected void createHBox(String descriptionLine) {
    HBox newBox = new HBox(new Text(descriptionLine));
    newBox.getStyleClass().add("descriptionLine");
    myDescription.getChildren().add(newBox);
  }
}
