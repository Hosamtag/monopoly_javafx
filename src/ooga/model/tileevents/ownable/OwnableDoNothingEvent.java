package ooga.model.tileevents.ownable;

import ooga.model.GameModel;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.model.tileevents.OwnableTileEvent;
import ooga.view.GameAlert;

public class OwnableDoNothingEvent extends OwnableTileEvent {

  /**
   * Creates an OwnableDoNothingEvent object.
   * Used for passive events for tiles that don't have any passive event.
   *
   * @param alert the alert object used to display notifications.
   */
  public OwnableDoNothingEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Does nothing.
   *
   * @param owner the owner of the tile.
   * @param caller the player that called the event
   * @param currentTile this tile.
   * @param gameModel the model used for the game.
   */
  public void callEvent(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel) {
    //Do nothing.
  }
}
