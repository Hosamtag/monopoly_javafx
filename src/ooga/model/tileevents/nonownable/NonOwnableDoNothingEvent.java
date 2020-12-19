package ooga.model.tileevents.nonownable;

import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.view.GameAlert;

public class NonOwnableDoNothingEvent extends NonOwnableTileEvent {

  /**
   * Creates a NonOwnableDoNothingEvent object.
   * Used for tiles that have no effect.
   *
   * @param alert the interface used for displaying notifications.
   */
  public NonOwnableDoNothingEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Does nothing.
   *
   * @param player the player that called the event.
   * @param value the value associated with this event.
   * @param gameModel the model used for this game.
   */
  @Override
  public void callEvent(Player player, int value, GameModel gameModel) {
    //Do nothing.
  }

}
