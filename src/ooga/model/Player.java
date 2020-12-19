package ooga.model;

import java.util.List;
import ooga.controller.Observable;
import ooga.view.GameAlert;

public interface Player extends Observable {

  /**
   * Gets the name of this player.
   *
   * @return the player's name
   */
  String getName();

  /**
   * Gets the tile that the player is currently on.
   *
   * @return the tile the player's on
   */
  Tile getCurrentTile();

  /**
   * Gets all of the ownables currently held by this player.
   *
   * @return the player's ownables
   */
  List<Ownable> getOwnables();

  /**
   * Gets how much money the player has.
   *
   * @return the amount of money this player has
   */
  int getFunds();

  /**
   * Updates the player's funds to a new value.
   *
   * @param newFunds the new amount of funds the player should have
   */
  void updateFunds(int newFunds);

  /**
   * Gets the players current status effect.
   *
   * @return the current status effect
   */
  StatusEffect getStatusEffects();

  /**
   * Updates the player's status effect to a new one.
   *
   * @param newEffect the new status effect that this player should have
   */
  void updateStatusEffect(StatusEffect newEffect);

  /**
   * Performs the turn for this player.
   *
   * @param myDie the current Randomizable that is being used.
   * @param myBoard the current board that the player is on.
   * @param model the model for this game.
   * @param gameAlert an object used to send alerts to the view.
   */
  void doTurn(Randomizable myDie, BoardModel myBoard, GameModel model, GameAlert gameAlert);

  /**
   * Adds an ownable to the players inventory.
   *
   * @param toAdd the ownable that should be added.
   */
  void addOwnable(Ownable toAdd);

  /**
   * Removes an ownable from a player's inventory.
   *
   * @param toRemove the ownable to be removed.
   */
  void removeOwnable(Ownable toRemove);

  /**
   * Updates the player's internal location to the correct location.
   *
   * @param newLocation the new tile that the player is on.
   */
  void updateCurrentTile(Tile newLocation);

  /**
   * Makes this player a winner of the game.  Can not be undone.
   * There can be multiple winners.
   */
  void declareWinner();

  /**
   * Checks if this player is a winner of the game.
   *
   * @return whether the player is a winner.
   */
  boolean checkIfWinner();

}
