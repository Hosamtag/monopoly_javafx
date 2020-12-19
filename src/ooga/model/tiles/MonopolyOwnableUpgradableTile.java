package ooga.model.tiles;

import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidOperationException;
import ooga.model.Buildings;
import ooga.model.GameModel;
import ooga.model.Upgradable;
import ooga.model.tileevents.OwnableTileEvent;

public class MonopolyOwnableUpgradableTile extends MonopolyOwnableTile implements Upgradable {

  private static final int NOT_UPGRADABLE = -1;

  final boolean isMortgaged;
  final int[] myRentStructure;
  final int[] myUpgradeCost;

  /**
   * Creates a MonopolyOwnableUpgradableTile.
   * This would traditionally be used for a generic (non utility/railroad) monopoly ownable tile.
   * Can be used or extended for custom tiles.
   *
   * @param propertyName the name of this property.
   * @param event the event called when players land here.
   * @param passiveEvent the event called when players pass here.
   * @param monopoly the monopoly/color group of this tile.
   * @param propertyValue the value of this property.
   * @param rentStructure rent tiers for each upgrade level
   * @param upgradeCost the cost for upgrading this tile.
   */
  public MonopolyOwnableUpgradableTile(String propertyName, OwnableTileEvent event,
      OwnableTileEvent passiveEvent, String monopoly,
      int propertyValue, int[] rentStructure, int[] upgradeCost) {
    super(propertyName, event, passiveEvent, monopoly, propertyValue, rentStructure[0]);
    isMortgaged = false;
    myRentStructure = rentStructure;
    myUpgradeCost = upgradeCost;
  }

  /**
   * Determines the rent for this property based on the upgrade level.
   *
   * @return the current rent.
   */
  @Override
  public int calculateBaseRent() {
    return myRentStructure[myBuildings.ordinal()];
  }

  /**
   * The value of this property.
   * Most often used for buying/selling.
   *
   * @return the value of this tile.
   */
  public int getPropertyValue() {
    return myValue;
  }

  /**
   * Upgrades this tile to the next tier.
   * Does nothing if this tile is owned by the bank or the owner doesn't have the monopoly.
   * Does not allow for upgrading past max tier.
   * Does not charge the owner, this should be handled elsewhere.
   *
   * @param myModel the model used for this game.
   */
  public void upgrade(GameModel myModel) {
    if (myOwner.equals(myModel.getBank()) || !myModel.checkMonopoly(myOwner, this)) {
      throw new InvalidOperationException(
          ResourceUtil.getResourceValue("CantUpgrade"));
    }
    if (myBuildings.ordinal() + 1 < Buildings.values().length) {
      myBuildings = Buildings.values()[myBuildings.ordinal() + 1];
    }
  }

  /**
   * Determines the current upgrade on this tile.
   *
   * @return the current upgrade level.
   */
  public Buildings getCurrentBuilding() {
    return myBuildings;
  }

  /**
   * Determines how much it would cost to upgrade this tile.
   *
   * @return the cost to upgrade this tile.
   */
  public int getUpgradeCost() {
    if (myBuildings.ordinal() < myUpgradeCost.length) {
      return myUpgradeCost[myBuildings.ordinal()];
    }
    return NOT_UPGRADABLE;
  }

}
