package ooga.view;

import java.util.List;
import ooga.model.Player;

/**
 * The external facing interface for the view of the game. Any game can use this api to create
 * the viewable using these methods
 *
 * @author Hosam Tageldin
 */
public interface GameViewable {

  /**
   * Used to indicate there was a change in the model based on player position, funds, or owned
   * properties and updates the view accordingly
   *
   * @param activePlayer the player whose turn is currently theirs
   */
  void updateView(Player activePlayer);

  /**
   * @return the DiceView being used by the view
   */
  DiceView getDiceView();

  /**
   * @return the list of TileViews being used by the view
   */
  List<TileView> getTileViews();

  /**
   * Sets the tile views to be used by the view
   *
   * @param newTiles the new tiles to be used for the view
   */
  void setTileViews(List<TileView> newTiles);

}
