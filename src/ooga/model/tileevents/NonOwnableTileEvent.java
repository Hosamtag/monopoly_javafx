package ooga.model.tileevents;

import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.TileEvent;
import ooga.view.GameAlert;

public abstract class NonOwnableTileEvent extends TileEvent {

  /**
   * Creates a NonOwnableTileEvent object.
   *
   * @param alert the alert object used for notifications.
   */
  public NonOwnableTileEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Performs the event specified by this object.
   *
   * @param player the player that called the event.
   * @param value the value associated with this event.
   * @param gameModel the model used for this game.
   */
  public abstract void callEvent(Player player, int value, GameModel gameModel);

}
