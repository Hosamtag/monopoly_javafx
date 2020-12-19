package ooga.model;

import java.util.List;

public interface BoardModel {

  /**
   * The list of Tiles in the board.
   *
   * @return all of the tiles in the game.
   */
  List<Tile> getTiles();

  /**
   * The tile that is diceRoll spaces away from playerCurrentTile.
   *
   * @param playerCurrentTile the tile the calculate distance from
   * @param diceRoll the distance to the desired tile.
   * @return the desired tile
   */
  Tile getNextTile(Tile playerCurrentTile, int diceRoll);

}
