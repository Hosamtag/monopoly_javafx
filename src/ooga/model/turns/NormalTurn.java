package ooga.model.turns;

import ooga.controller.util.ArrayUtil;
import ooga.model.BoardModel;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.Randomizable;
import ooga.model.Turn;
import ooga.view.GameAlert;

public class NormalTurn extends Turn {

  /**
   * Creates a NormalTurn object
   *
   * @param player the player whose turn it is
   */
  public NormalTurn(Player player) {
    super(player);
  }

  /**
   * Performs a regular turn where the player moves at normal speed.
   *
   * @param myDie the randomizable used for this turn
   * @param myBoard the board that the player is on.
   * @param model the model used for this game.
   * @param gameAlert the alert object used for displaying output
   */
  public void doTurn(Randomizable myDie, BoardModel myBoard, GameModel model, GameAlert gameAlert) {
    int nextRollValues = ArrayUtil.sum(myDie.getNextRoll());
    tileByTileMovement(nextRollValues, myBoard, model);
  }

}
