package ooga.model.tileevents;

import ooga.model.GameModel;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.model.TileEvent;
import ooga.view.GameAlert;

public abstract class OwnableTileEvent extends TileEvent {

  /**
   * Creates and OwnableTileEvent object.
   *
   * @param alert the alert object used to display info.
   */
  public OwnableTileEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Performs the event specified by this Object.
   *
   * @param owner the owner of the tile.
   * @param caller the player that called the event
   * @param currentTile this tile.
   * @param gameModel the model used for the game.
   */
  public abstract void callEvent(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel);

}
