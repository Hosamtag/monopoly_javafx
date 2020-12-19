package ooga.view.tile;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import ooga.view.TileView;

/**
 * Extends the TileView for any tile that only includes an image, name and price. Any of these three
 * parameters can be empty. NonPropertyTileView is used by any tile that does not have a "monopoly"
 * represented by the bar on the top
 *
 * @author Hosam Tageldin
 */
public class NonPropertyTileView extends TileView {

  private final String myPrice;

  public NonPropertyTileView(String name, String fileName, String price) {
    super(name, fileName);
    myPrice = price;
    setTile();
  }

  private void setTile() {
    TextFlow name = new TextFlow(new Text(simplify(tileName)));
    name.setTextAlignment(TextAlignment.CENTER);
    Text price = new Text(myPrice);
    this.setTop(name);
    if (Integer.parseInt(myPrice) != 0) {
      this.setBottom(price);
    }
    this.setCenter(playerBox);
    BorderPane.setAlignment(name, Pos.CENTER);
    BorderPane.setAlignment(price, Pos.CENTER);
  }

  private String simplify(String name) {
    return name.replaceAll("[0-9]", "");
  }
}
