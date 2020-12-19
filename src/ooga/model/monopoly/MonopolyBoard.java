package ooga.model.monopoly;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ooga.model.BoardModel;
import ooga.model.OwnableTile;
import ooga.model.Tile;

public class MonopolyBoard implements BoardModel {

  List<Tile> myTiles;
  Map<String, Set<OwnableTile>> monopolies;


  /**
   * Instantiates a MonopolyBoard object.
   *
   * @param tileInput The list of tiles used in the game.
   */
  public MonopolyBoard(List<Tile> tileInput) {
    myTiles = tileInput;
    processMonopolies();
  }

  /**
   * Determines the tile that a player will land on next.  Returns the tile as a Tile type.
   *
   * @param playerCurrentTile the Tile that the player is current on.
   * @param diceRoll          the amount of spaces that the player will move
   * @return the next tile that the player will land on
   */
  public Tile getNextTile(Tile playerCurrentTile, int diceRoll) {
    int currentTileIndex = myTiles.indexOf(playerCurrentTile);
    int nextTileIndex = (currentTileIndex + diceRoll) % myTiles.size();
    return myTiles.get(nextTileIndex);
  }

  protected void setTiles(List<Tile> newTiles) {
    myTiles = newTiles;
  }

  private void processMonopolies() {
    monopolies = new HashMap<>();
    for (Tile eachTile : myTiles) {
      if (eachTile instanceof OwnableTile) {
        OwnableTile castTile = (OwnableTile) eachTile;
        String tileName = castTile.getMonopoly();
        monopolies.putIfAbsent(tileName, new HashSet<>());
        monopolies.get(tileName).add(castTile);
      }
    }
  }

  protected Set<OwnableTile> getTilesInMonopoly(String monopoly) {
    return monopolies.get(monopoly);
  }

  /**
   * @return The list of tiles used in the game.
   */
  public List<Tile> getTiles() {
    return myTiles;
  }
}
