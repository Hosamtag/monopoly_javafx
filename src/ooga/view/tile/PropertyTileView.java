package ooga.view.tile;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import ooga.view.TileView;
import ooga.view.CardView;
import ooga.view.cards.PropertyCardView;

/**
 * Extends the TileView for any tile that is included in a monopoly. The monopoly is represented by
 * bar on the top. Set to display the property card on the top right of the screen on hover of the
 * computer mouse.
 *
 * @author Hosam Tageldin
 */
public class PropertyTileView extends TileView {

  private final String myColor;
  private final String myPrice;
  private CardView display;

  public PropertyTileView(String name, String fileName, String color, String price,
      String[] rentStructure, String[] upgradeCosts) {
    super(name, fileName);
    myColor = color;
    myPrice = price;
    setTile();
    this.setOnMouseEntered(e -> showProperties(name, color, rentStructure, upgradeCosts));
    this.setOnMouseExited(e -> hideProperties());
  }

  private void setTile() {
    HBox newRec = new HBox();
    newRec.setId("PropertyColor");
    newRec.setStyle("-fx-background-color: " + myColor);
    this.setTop(newRec);
    if (fileName == null) {
      TextFlow name = new TextFlow(new Text(tileName));
      name.setTextAlignment(TextAlignment.CENTER);
      playerBox.getChildren().add(name);
    }
    this.setCenter(playerBox);
    Text money = new Text(myPrice);
    this.setBottom(money);
    BorderPane.setAlignment(money, Pos.CENTER);
  }

  private void showProperties(String name, String color, String[] rentStructure,
      String[] upgradeCosts) {
    display = new PropertyCardView(name, color, rentStructure, upgradeCosts);
    display.show();
  }

  private void hideProperties() {
    display.hide();
  }


}
