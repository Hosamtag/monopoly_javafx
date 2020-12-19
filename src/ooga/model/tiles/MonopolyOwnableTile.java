package ooga.model.tiles;

import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidOperationException;
import ooga.model.Buildings;
import ooga.model.GameModel;
import ooga.model.Mortgageable;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.model.monopoly.MonopolyModel;
import ooga.model.tileevents.OwnableTileEvent;

public class MonopolyOwnableTile implements OwnableTile, Mortgageable {

  private static final String MORTGAGE_EXCEPTION = ResourceUtil
      .getResourceValue("MortgageException");
  private static final double BAD_HARDCODE_INTEREST_VALUE = 1.1;

  Player myOwner;
  boolean isMortgaged;
  final String myMonopoly;
  final int myValue;
  final int myRent;
  final String myName;
  Buildings myBuildings;
  final OwnableTileEvent myEvent;
  final OwnableTileEvent myPassiveEvent;

  /**
   * Creates a MonopolyOwnableTile object.
   * Most often used for non-upgradable ownable tiles such as railroads and utilities.
   *
   * @param propertyName the name of this property.
   * @param event the event that occurs when players land here.
   * @param passiveEvent the event that occurs when players pass here.
   * @param monopoly the monopoly/color group this tile belongs to.
   * @param propertyValue the value of this property.
   * @param rent the base rent for this tile.
   */
  public MonopolyOwnableTile(String propertyName, OwnableTileEvent event,
      OwnableTileEvent passiveEvent, String monopoly, int propertyValue, int rent) {
    myName = propertyName;
    myValue = propertyValue;
    myMonopoly = monopoly;
    isMortgaged = false;
    myRent = rent;
    myBuildings = Buildings.NONE;
    myEvent = event;
    myPassiveEvent = passiveEvent;
  }

  /**
   * Finds the base rent for this property.
   *
   * @return the base rent
   */
  public int calculateBaseRent() {
    return myRent;
  }

  /**
   * Gets the current property value.
   *
   * @return the value of this property.
   */
  public int getPropertyValue() {
    return myValue;
  }

  /**
   * Finds which monopoly/color group this tile belongs to.
   *
   * @return the monopoly of this tile.
   */
  public String getMonopoly() {
    return myMonopoly;
  }

  /**
   * Finds which player owns this tile.
   *
   * @return the owner of this tile.
   */
  public Player getOwner() {
    return myOwner;
  }

  /**
   * The owner of this tile.
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
    if (!isMortgaged) {
      myEvent.callEvent(myOwner, player, this, model);
    }
  }

  /**
   * Calls the event that is associated with passing this tile.
   *
   * @param player The player that is calling this passive event.
   * @param model The model associated with this game.
   */
  public void callMyPassiveEvent(Player player, GameModel model) {
    if (!isMortgaged) {
      myPassiveEvent.callEvent(myOwner, player, this, model);
    }
  }

  /**
   * Returns the name associated with this tile.
   *
   * @return this tile's name.
   */
  public String getName() {
    return myName;
  }

  /**
   * Updates the mortgage status of this tile. and charges the player.
   * Switches between mortgaged and unmortgaged.
   * Does not allow for changes if the property is owned by the bank.
   */
  public void changeMortgageStatus() {
    if (myOwner.getName().contains(MonopolyModel.BANK_NAME)) {
      throw new InvalidOperationException(MORTGAGE_EXCEPTION);
    }
    if (isMortgaged) {
      myOwner.updateFunds(myOwner.getFunds() - amountToRemoveMortgage());
    } else {
      myOwner.updateFunds(myOwner.getFunds() + myValue);
    }
    isMortgaged = !isMortgaged;
  }

  /**
   * Determines the cost to remove the mortgage on this tile.
   * For this kind of tile, it charges the value plus 10% interest.
   *
   * @return the cost to remove the mortgage.
   */
  public int amountToRemoveMortgage() {
    return (int) (BAD_HARDCODE_INTEREST_VALUE * myValue);

  }

  /**
   * Checks if this tile is mortgaged.
   *
   * @return whether the property is mortgaged or not.
   */
  public boolean checkIfMortgaged() {
    return isMortgaged;
  }

}
