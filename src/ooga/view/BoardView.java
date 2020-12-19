package ooga.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ooga.model.Player;

/**
 * Abstract class representing the view of the monopoly board. The board is constructed using a list
 * of TileViews. Holds the shared methods of adding and removing players on the boards tile views.
 *
 * @author Hosam Tageldin
 */
public abstract class BoardView {

  private List<TileView> tileViewList;
  private final List<Player> playerList;
  private final List<ImageView> iconList;
  private final Map<Player, ImageView> playerIconMapping;

  public BoardView(List<TileView> tilesForGame, List<Player> myPlayers, List<ImageView> icons) {
    this.playerList = myPlayers;
    this.iconList = icons;
    tileViewList = tilesForGame;
    setIds();
    playerIconMapping = new HashMap<>();
    setMappings();
  }

  /**
   * Populates the board view with the list of tile views and DiceView in the appropriate manner
   *
   * @return a Pane with the tile views spread out in the manner determined by the subclass
   */
  protected abstract Pane populateBoardView();

  /**
   * Creates the Board in the manner specified by the subclass
   */
  protected abstract void createBoard();

  protected void setTileViewList(List<TileView> newTiles) {
    this.tileViewList = newTiles;
    resetEffects();
  }

  /**
   * Adds the players on the appropriate tile views of the Board
   */
  protected void initializePlayers() {
    for (Player player : playerList) {
      getLocation(player.getCurrentTile().getName()).addPlayer(playerIconMapping.get(player));
    }
  }

  /**
   * Removes all the player icons from the TileViews so that players aren't duplicated on the board
   */
  protected void removePlayers() {
    for (TileView tile : tileViewList) {
      for (ImageView icon : iconList) {
        tile.removePlayer(icon);
      }
    }
  }

  private void setMappings() {
    for (int i = 0; i < playerList.size(); i++) {
      playerIconMapping.put(playerList.get(i), iconList.get(i));
    }
  }

  private void resetEffects() {
    for (TileView tile : tileViewList) {
      tile.resetSelection();
    }
  }

  private TileView getLocation(String tileName) {
    for (int i = 0; i < tileViewList.size(); i++) {
      if (tileViewList.get(i).equalsName(tileName)) {
        return tileViewList.get(i);
      }
    }
    return null;
  }

  private void setIds() {
    for (int i = 0; i < tileViewList.size(); i++) {
      tileViewList.get(i).setId("tile" + i);
    }
  }

}
