package ooga.model.tileevents.nonownable;

import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.view.GameAlert;

public class MonopolyTaxEvent extends NonOwnableTileEvent {

  private static final String TAX_EVENT = ResourceUtil.getResourceValue("TaxEvent");
  private static final String TAX_TILE = ResourceUtil.getResourceValue("TaxTile");

  /**
   * Creates a MonopolyTaxEvent object.
   *
   * @param alert the interface used to display notifications.
   */
  public MonopolyTaxEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Makes a the calling player pay a specified tax determined by the value parameter.
   *
   * @param player the player that called the event.
   * @param value the value associated with this event.
   * @param gameModel the model used for this game.
   */
  @Override
  public void callEvent(Player player, int value, GameModel gameModel) {
    gameModel.payPlayer(gameModel.getBank(), player, value);
    String message = String
        .format(TAX_EVENT, value);
    gameAlert.createGeneralInfoAlert(TAX_TILE, TAX_TILE, message);
  }
}
