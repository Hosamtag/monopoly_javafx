package ooga.model.monopoly;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.Observer;
import ooga.controller.factory.TurnFactory;
import ooga.exceptions.InvalidOperationException;
import ooga.model.BoardModel;
import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.Randomizable;
import ooga.model.StatusEffect;
import ooga.model.Tile;
import ooga.model.Turn;
import ooga.model.turns.NormalTurn;
import ooga.view.GameAlert;

public class MonopolyPlayer implements Player {

  private final List<Ownable> myOwnables;
  final List<Observer> observers;
  Tile currentTile;
  int myBalance;
  final String myName;
  StatusEffect myStatus;
  Turn myTurn;
  private boolean isWinner = false;


  /**
   * Instantiates a MonopolyPlayer object.
   *
   * @param name            the name of the player.  This will be used as an ID and should not be
   *                        the same as other players.
   * @param startingTile    the tile that this player will start on.  For new games this will
   *                        usually be the first tile, but loaded games will have players all around
   *                        the board..
   * @param startingBalance the amount of money that the player has at the start of the game.
   */
  public MonopolyPlayer(String name, Tile startingTile, int startingBalance) {
    myOwnables = new ArrayList<>();
    observers = new ArrayList<>();
    currentTile = startingTile;
    myBalance = startingBalance;
    myStatus = MonopolyStatusEffect.NORMAL;
    myName = name;
    myTurn = new NormalTurn(this);
  }

  /**
   * Removes the specified ownable object from this players list of owned ownables.
   *
   * @param toRemove the ownable to be removed.
   */
  public void removeOwnable(Ownable toRemove) {
    if (!myOwnables.contains(toRemove)) {
      throw new InvalidOperationException(myName + " does not own " + toRemove.getName());
    }
    myOwnables.remove(toRemove);
    notifyObservers();

  }

  /**
   * Adds the specified ownable object from this players list of owned ownables.
   *
   * @param toAdd the ownable that should be added.
   */
  public void addOwnable(Ownable toAdd) {
    myOwnables.add(toAdd);
    notifyObservers();
  }

  /**
   * @return A copy of the list of the players ownables.
   */
  public List<Ownable> getOwnables() {
    return new ArrayList<>(myOwnables);
  }

  /**
   * @return The name of the player which should be unique.
   */
  public String getName() {
    return myName;
  }

  /**
   * Updates the current tile of the player which indicates their location.
   *
   * @param newCurrentTile The new tile that is the players location.
   */
  public void updateCurrentTile(Tile newCurrentTile) {
    currentTile = newCurrentTile;
    notifyObservers();
  }

  /**
   * Notifies observers of the player that something has changed.
   */
  @Override
  public void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }

  /**
   * Adds a new observer to the players list of observers.
   *
   * @param o A new observer object that is observing the player.
   */
  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  /**
   * @return The current tile that indicates the players location.
   */
  public Tile getCurrentTile() {
    return currentTile;
  }

  /**
   * @return The current status effect of the player
   */
  public StatusEffect getStatusEffects() {
    return myStatus;
  }

  /**
   * @param status The new status effect of the player
   */
  public void updateStatusEffect(StatusEffect status) {
    myStatus = status;
    myTurn = TurnFactory.createTurn(status.toString(), this);
  }


  /**
   * @return The integer value of the players funds.
   */
  public int getFunds() {
    return myBalance;
  }

  /**
   * Adjusts the Player's funds to a new value.
   *
   * @param newBalance the new balance of the Player's account
   */
  public void updateFunds(int newBalance) {
    myBalance = newBalance;
    notifyObservers();
  }

  /**
   * Takes a turn for the player.
   *
   * @param myDie     the current Randomizable that is being used.
   * @param myBoard   the current board that the player is on.
   * @param model     the model for this game.
   * @param gameAlert an object used to send alerts to the view.
   */
  public void doTurn(Randomizable myDie, BoardModel myBoard, GameModel model, GameAlert gameAlert) {
    myTurn.doTurn(myDie, myBoard, model, gameAlert);
  }

  /**
   * Declares the player as the winnder of the game.
   */
  public void declareWinner() {
    isWinner = true;
  }

  /**
   * @return A boolean indicating if the player has been declared winner.
   */
  public boolean checkIfWinner() {
    return isWinner;
  }
}
