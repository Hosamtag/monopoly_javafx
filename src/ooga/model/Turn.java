package ooga.model;

import ooga.view.GameAlert;

public abstract class Turn {

  final protected Player myPlayer;

  /**
   * Creates a Turn object.
   *
   * @param player the player whose turn this is
   */
  public Turn(Player player) {
    myPlayer = player;
  }

  /**
   * Performs the turn action.
   *
   * @param myDice the Randomizable used for this turn.
   * @param myBoard the board that the player is on.
   * @param model the model used for this game.
   * @param gameAlert the alert object used for displaying output
   */
  public abstract void doTurn(Randomizable myDice, BoardModel myBoard, GameModel model,
      GameAlert gameAlert);

  protected void tileByTileMovement(int numTilesToMoveBy, BoardModel myBoard, GameModel model) {
    int numTilesMoved = 0;
    while (numTilesMoved < numTilesToMoveBy - 1) {
      moveToNextTile(myBoard, model);
      numTilesMoved += 1;
    }
    myPlayer.updateCurrentTile(myBoard.getNextTile(myPlayer.getCurrentTile(), 1));
    model.callPlayerTileEvent(myPlayer);
  }

  private void moveToNextTile(BoardModel myBoard, GameModel model) {
    Tile nextTile = myBoard.getNextTile(myPlayer.getCurrentTile(), 1);
    myPlayer.updateCurrentTile(nextTile);
    nextTile.callMyPassiveEvent(myPlayer, model);
  }

}
