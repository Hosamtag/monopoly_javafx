package ooga.model.tileevents.nonownable;

import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.monopoly.MonopolyStatusEffect;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.view.GameAlert;

public class ResetSpeedEvent extends NonOwnableTileEvent {

  private static final String YOU_ARE_SET_NORMAL = ResourceUtil.getResourceValue("SpeedSetNormal");


  /**
   * Creates a ResetSpeedEvent object.
   * Used to set a player's speed back to normal.
   *
   * @param alert the interface used for displaying notifications.
   */
  public ResetSpeedEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Sets the players statuseffect to NORMAL.
   *
   * @param player the player that called the event.
   * @param value the value associated with this event.
   * @param gameModel the model used for this game.
   */
  public void callEvent(Player player, int value, GameModel gameModel) {
    player.updateStatusEffect(MonopolyStatusEffect.NORMAL);
    String message = String
        .format(TILE_EVENT, player.getCurrentTile().getName());
    gameAlert.createGeneralInfoAlert(ACTION_CARD, YOU_ARE_SET_NORMAL, message);
  }
}
