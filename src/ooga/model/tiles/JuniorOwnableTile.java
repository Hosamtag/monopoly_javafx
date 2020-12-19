package ooga.model.tiles;

import ooga.model.GameModel;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.model.tileevents.OwnableTileEvent;

public class JuniorOwnableTile implements OwnableTile {

  String myName;
  int myValue;
  String myMonopoly;
  int myRent;
  OwnableTileEvent myEvent;
  OwnableTileEvent myPassiveEvent;
  Player myOwner;

  /**
   * Creates a JuniorOwnableTile object.
   * This object is used for the Monopoly Junior game, but could be used elsewhere.
   *
   * @param propertyName the name of this tile.
   * @param event the event associated with landing on this tile.
   * @param passiveEvent the event associated with passing this tile.
   * @param monopoly the monopoly/color group of this tile.
   * @param propertyValue The value of this property.
   * @param rent the rent to pay when someone lands here.
   */
  public JuniorOwnableTile(String propertyName, OwnableTileEvent event,
      OwnableTileEvent passiveEvent, String monopoly,
      int propertyValue, int rent) {
    myName = propertyName;
    myValue = propertyValue;
    myMonopoly = monopoly;
    myRent = rent;
    myEvent = event;
    myPassiveEvent = passiveEvent;
  }

  /**
   * Finds the rent for this property.
   *
   * @return the rent for this property.
   */
  public int calculateBaseRent() {
    return myRent;
  }

  /**
   * Gets the value of this property.
   *
   * @return this tile's value.
   */
  public int getPropertyValue() {
    return myValue;
  }

  /**
   * Gets the monopoly/color group of this tile.
   *
   * @return this tile's monopoly/color group.
   */
  public String getMonopoly() {
    return myMonopoly;
  }

  /**
   * Gets the owner of this monopoly.
   *
   * @return this tile's owner.
   */
  public Player getOwner() {
    return myOwner;
  }

  /**
   * Changes the owner of this tile.
   *
   * @param newOwner the new owner of this object.
   */
  public void changeOwner(Player newOwner) {
    myOwner = newOwner;
  }

  /**
   * Calls the event associated with landing on this tile.
   *
   * @param player The player that is calling this tile event.
   * @param model The model associated with this game.
   */
  public void callMyEvent(Player player, GameModel model) {
    myEvent.callEvent(myOwner, player, this, model);
  }

  /**
   * Calls the event associated with passing this tile.
   *
   * @param player The player that is calling this passive event.
   * @param model The model associated with this game.
   */
  public void callMyPassiveEvent(Player player, GameModel model) {
    myPassiveEvent.callEvent(myOwner, player, this, model);
  }

  /**
   * Gets the name of this tile.
   *
   * @return this tile's name
   */
  public String getName() {
    return myName;
  }
}
