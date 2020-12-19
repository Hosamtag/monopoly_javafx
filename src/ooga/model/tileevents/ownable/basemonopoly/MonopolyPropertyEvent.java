package ooga.model.tileevents.ownable.basemonopoly;

import ooga.model.GameModel;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.model.tileevents.ownable.MonopolyOwnableTileEvent;
import ooga.view.GameAlert;

public class MonopolyPropertyEvent extends MonopolyOwnableTileEvent {

  /**
   * Creates a MonopolyPropertyEvent object.
   * Used for regular property tiles in base monopoly.
   *
   * @param alert the interface used for displaying notifications.
   */
  public MonopolyPropertyEvent(GameAlert alert) {
    super(alert);
  }

  @Override
  protected void landOnOwned(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel) {
    int rentOwed = currentTile.calculateBaseRent();
    callerPaysMoney(owner, caller, rentOwed, gameModel);
    displayGeneralInfoAlert(currentTile, rentOwed);
  }

}
