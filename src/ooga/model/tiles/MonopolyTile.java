package ooga.model.tiles;

import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.Tile;
import ooga.model.tileevents.NonOwnableTileEvent;

public class MonopolyTile implements Tile {

  final String myName;
  final int myInt;
  final NonOwnableTileEvent myEvent;
  final NonOwnableTileEvent myPassiveEvent;

  /**
   * Creates a MonopolyTile.  Used as a generic Non-Ownable Tile.
   *
   * @param propertyName the name of this tile.
   * @param event the event that this tile calls when a player lands on it.
   * @param passiveEvent the event that this tile calls when a player passes it.
   * @param integerParams the values associated with this tile's event.
   */
  public MonopolyTile(String propertyName, NonOwnableTileEvent event,
      NonOwnableTileEvent passiveEvent, int integerParams) {
    myName = propertyName;
    myEvent = event;
    myPassiveEvent = passiveEvent;
    myInt = integerParams;
  }

  /**
   * Calls the event for this tile.
   *
   * @param player The player that is calling this tile event.
   * @param model The model associated with this game.
   */
  public void callMyEvent(Player player, GameModel model) {
    myEvent.callEvent(player, myInt, model);
  }

  /**
   * The passive event associated with this tile.
   * Passive events are called when a player passes the tile.
   *
   * @param player The player that is calling this passive event.
   * @param model The model associated with this game.
   */
  public void callMyPassiveEvent(Player player, GameModel model) {
    myPassiveEvent.callEvent(player, myInt, model);
  }

  /**
   * Gets the name of this tile.
   *
   * @return this tile's name.
   */
  public String getName() {
    return myName;
  }
}
