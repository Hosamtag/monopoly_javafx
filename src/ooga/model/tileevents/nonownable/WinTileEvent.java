package ooga.model.tileevents.nonownable;

import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.view.GameAlert;

public class WinTileEvent extends NonOwnableTileEvent {

  private static final String YOU_WIN = ResourceUtil.getResourceValue("YouWin");

  /**
   * Creates a WinTileEvent object.
   * Automatically makes the calling player a winner.
   *
   * @param alert the interface used for displaying notifications.
   */
  public WinTileEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Declares the calling player as a winner.
   *
   * @param player the player that called the event.
   * @param value the value associated with this event.
   * @param gameModel the model used for this game.
   */
  @Override
  public void callEvent(Player player, int value, GameModel gameModel) {
    player.declareWinner();
    gameModel.declareGameOver();
    String message = String
        .format(TILE_EVENT, player.getCurrentTile().getName());
    gameAlert.createGeneralInfoAlert(ACTION_CARD, YOU_WIN, message);
  }

}
