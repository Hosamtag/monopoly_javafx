package ooga.view.tile;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.view.TileView;

/**
 * Subclass of the TileView parent class to represent any tile that only contains an Image that is
 * usually found on the corner tiles in monopoly
 *
 * @author Hosam Tageldin
 */
public class CornerTileView extends TileView {

  public CornerTileView(String name, String fileName, String price) {
    super(name, fileName);
  }

  /**
   * Initializes the space by placing an enlarged image view in the center of the TileView
   */
  @Override
  protected void initializeSpace() {
    ImageView img = new ImageView(
        new Image(this.getClass().getClassLoader().getResourceAsStream(fileName)));
    img.fitWidthProperty().bind(this.widthProperty());
    img.fitHeightProperty().bind(this.heightProperty());
    playerBox.getChildren().add(img);
    this.setCenter(playerBox);
  }
}
