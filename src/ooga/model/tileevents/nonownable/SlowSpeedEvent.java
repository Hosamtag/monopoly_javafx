package ooga.model.tileevents.nonownable;

import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.monopoly.MonopolyStatusEffect;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.view.GameAlert;

public class SlowSpeedEvent extends NonOwnableTileEvent {

  private static final String YOU_ARE_SLOWED = ResourceUtil.getResourceValue("YouAreSlowed");

  /**
   * Creates a SlowSpeedEvent object.
   * Sets the players speed to slow, so that they move half as fast.
   *
   * @param alert the interface used for displaying notifications.
   */
  public SlowSpeedEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Updates the players statuseffect to SLOW.
   *
   * @param player the player that called the event.
   * @param value the value associated with this event.
   * @param gameModel the model used for this game.
   */
  public void callEvent(Player player, int value, GameModel gameModel) {
    player.updateStatusEffect(MonopolyStatusEffect.SLOW);
    String message = String
        .format(TILE_EVENT, player.getCurrentTile().getName());
    gameAlert.createGeneralInfoAlert(ACTION_CARD, YOU_ARE_SLOWED, message);
  }
}
