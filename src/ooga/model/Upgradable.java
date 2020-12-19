package ooga.model;

public interface Upgradable {

  /**
   * Upgrades to the next level of upgrade.
   *
   * @param model the model used for this game
   */
  void upgrade(GameModel model);

  /**
   * Returns the current upgrade level.
   *
   * @return the current level of upgrade
   */
  Buildings getCurrentBuilding();

  /**
   * Determines how much it costs to upgrade this.
   *
   * @return the upgrade cost
   */
  int getUpgradeCost();

}
