package ooga.model.tileevents.nonownable;

import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.view.GameAlert;

public class MonopolyGoEvent extends NonOwnableTileEvent {

  private static final String PASS_GO = ResourceUtil.getResourceValue("PassGoTileEvent");

  /**
   * Creates a MonopolyGoEvent object.
   * Used for passing go in most monopolies.
   *
   * @param alert the interface used for displaying notifications.
   */
  public MonopolyGoEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Pays the player a specified amount for passing go.
   *
   * @param player the player that called the event.
   * @param value the value associated with this event.
   * @param gameModel the model used for this game.
   */
  @Override
  public void callEvent(Player player, int value, GameModel gameModel) {
    String header = String
        .format(TILE_EVENT, player.getCurrentTile().getName());
    String message = String
        .format(PASS_GO, value);
    gameAlert.createGeneralInfoAlert(message, header, message);
    gameModel.payPlayer(player, gameModel.getBank(), value);
  }

}
