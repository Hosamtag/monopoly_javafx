package ooga.model.turns;

import ooga.controller.util.ArrayUtil;
import ooga.model.BoardModel;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.Randomizable;
import ooga.model.Turn;
import ooga.view.GameAlert;

public class SlowTurn extends Turn {

  /**
   * Creates a SlowTurn object.
   * Used to make the player goes half the speed.
   *
   * @param player the player whose turn this is.
   */
  public SlowTurn(Player player) {
    super(player);
  }

  /**
   * Performs a slow turn where the player goes half as far as normal.
   *
   * @param myDie the randomizable used for this turn.
   * @param myBoard the board that the player is on.
   * @param model the model used for this game.
   * @param gameAlert the alert object used for displaying output
   */
  public void doTurn(Randomizable myDie, BoardModel myBoard, GameModel model, GameAlert gameAlert) {
    int nextRollValues = ArrayUtil.sum(myDie.getNextRoll()) / 2;
    tileByTileMovement(nextRollValues, myBoard, model);
  }
}
