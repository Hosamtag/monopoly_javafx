package ooga.model.tileevents.nonownable;

import ooga.controller.util.ResourceUtil;
import ooga.exceptions.OwnableNotFoundException;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.Tile;
import ooga.model.monopoly.MonopolyStatusEffect;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.view.GameAlert;

public class MonopolyGoToJailEvent extends NonOwnableTileEvent {

  private static final String GO_TO_JAIL = ResourceUtil.getResourceValue("GoToJailTile");
  private static final String JAIL = "Jail";

  /**
   * Creates a MonopolyGoToJailEvent object.
   * Used to send a player to jail.
   *
   * @param alert the interface used to display notifications.
   */
  public MonopolyGoToJailEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Sends a player to jail and updates their status accordingly.
   *
   * @param player the player that called the event.
   * @param value the value associated with this event.
   * @param gameModel the model used for this game.
   */
  @Override
  public void callEvent(Player player, int value, GameModel gameModel) {
    String header = String
        .format(TILE_EVENT, player.getCurrentTile().getName());
    gameAlert.createGeneralInfoAlert(GO_TO_JAIL, header, GO_TO_JAIL);
    gameModel.updatePlayerStatus(player, MonopolyStatusEffect.JAILED);
    try {
      Tile jailTile = gameModel.getTileFromName(JAIL);
      gameModel.jumpPlayerToTile(player, jailTile);
    } catch (OwnableNotFoundException e) {
      gameAlert.createErrorAlertPopUp(e.getMessage());
    }
  }
}
