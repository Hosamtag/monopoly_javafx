package ooga.model;

public interface OwnableTile extends Tile, Ownable {

  /**
   * Determines how much rent a player owes when they land here.
   *
   * @return the rent amount
   */
  int calculateBaseRent();

  /**
   * Fetches the property's total value.
   * This is typically how much it's bought and sold for.
   */
  int getPropertyValue();

  /**
   * Determines which monopoly (color group) this property belongs to.
   *
   * @return the tile's monopoly
   */
  String getMonopoly();


}
