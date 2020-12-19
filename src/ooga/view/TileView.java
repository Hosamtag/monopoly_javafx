package ooga.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import ooga.controller.Observable;
import ooga.controller.Observer;

/**
 * The visual representation of a Monopoly Tile. Extends a borderpane to aid in placement different
 * aspects of the tile. Implements Observable to notify Observers when it gets clicked on.
 *
 * @author Hosam Tageldin
 */

public abstract class TileView extends BorderPane implements Observable {

  final protected StackPane playerBox;
  final List<Observer> observers;
  final protected String fileName;
  final protected String tileName;
  private boolean isSelected;

  private static final double GLOW_LEVEL = 0.8;
  private static final int IMG_HEIGHT = 30;

  public TileView(String name, String fileName) {
    observers = new ArrayList<>();
    this.tileName = name;
    this.fileName = fileName;
    playerBox = new StackPane();
    initializeSpace();
    this.setOnMouseClicked(e -> glow());
    this.getStyleClass().add("rectangle");
  }

  /**
   * Initializes the TileView space with an image if the fileName is not null. Allows for
   * flexibility where tiles can either be populated with text or an image.
   */
  protected void initializeSpace() {
    if (fileName != null) {
      ImageView img = new ImageView(
          new Image(this.getClass().getClassLoader().getResourceAsStream(fileName)));
      img.setFitHeight(IMG_HEIGHT);
      img.setPreserveRatio(true);
      playerBox.getChildren().add(img);
    }
  }


  /**
   * Adds a player to this TileView
   *
   * @param img the player icon to be added to the TileView
   */
  public void addPlayer(ImageView img) {
    playerBox.getChildren().add(img);
  }

  /**
   * Removes the player from this TileView
   *
   * @param img the player icon to be removed to the TileView
   */
  public void removePlayer(ImageView img) {
    playerBox.getChildren().remove(img);
  }

  /**
   * @return a boolean representing whether this tile has been selected
   */
  public boolean isSelected() {
    return this.isSelected;
  }

  /**
   * Resets the selection of the tile so that the effect disappears
   */
  public void resetSelection() {
    this.isSelected = false;
    this.setEffect(null);
  }

  /**
   * @param name of the Tile
   * @return boolean representing if this TileView has the same tile name
   */
  public boolean equalsName(String name) {
    return tileName.equals(name);
  }

  /**
   * Used by JUnit testing to make sure there is an appropriate number of icons on the tile
   *
   * @return the number of icons on a tile
   */
  public int numOfPlayersOnTile() {
    return playerBox.getChildren().size() - 1;
  }

  /**
   * Notifies observers that a change to the TileView state has been made. In our case, it notifies
   * observers when it gets clicked on
   */
  @Override
  public void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }

  /**
   * Adds an Observer to the list of Observers
   *
   * @param o the Observer to add to this observable
   */
  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }


  private void glow() {
    isSelected = !isSelected;
    if (isSelected) {
      Glow glow = new Glow(GLOW_LEVEL);
      this.setEffect(glow);
    } else {
      this.setEffect(null);
    }
    notifyObservers();
  }

}
