package ooga.model.tileevents.nonownable;

import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.monopoly.MonopolyStatusEffect;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.view.GameAlert;

public class FastSpeedEvent extends NonOwnableTileEvent {

  private static final String YOU_ARE_SPED_UP = ResourceUtil.getResourceValue("YouAreSpedUp");

  /**
   * Creates a FastSpeedEvent object.
   * Used to speed up the player.
   *
   * @param alert the interface used for displaying notifications
   */
  public FastSpeedEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Updates the player's statuseffect to FAST.
   *
   * @param player the player that called the event.
   * @param value the value associated with this event.
   * @param gameModel the model used for this game.
   */
  public void callEvent(Player player, int value, GameModel gameModel) {
    player.updateStatusEffect(MonopolyStatusEffect.FAST);
    String message = String
        .format(TILE_EVENT, player.getCurrentTile().getName());
    gameAlert.createGeneralInfoAlert(ACTION_CARD, YOU_ARE_SPED_UP, message);
  }
}
