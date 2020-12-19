package ooga.model;

public interface Tile {

  /**
   * Calls the event associated with this tile.
   *
   * @param player The player that is calling this tile event.
   * @param model The model associated with this game.
   */
  void callMyEvent(Player player, GameModel model);

  /**
   * Calls the passive event associated with this tile.
   *
   * @param player The player that is calling this passive event.
   * @param model The model associated with this game.
   */
  void callMyPassiveEvent(Player player, GameModel model);

  /**
   * Gets the name of this tile.
   *
   * @return the name of this tile.
   */
  String getName();

}
