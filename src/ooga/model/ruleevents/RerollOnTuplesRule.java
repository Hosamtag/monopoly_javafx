package ooga.model.ruleevents;

import ooga.controller.util.ArrayUtil;
import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.RuleEvent;
import ooga.view.GameAlert;

public class RerollOnTuplesRule extends RuleEvent {

  private static final String TUPLES = ResourceUtil.getResourceValue("Tuples");
  private static final String ROLL_AGAIN = ResourceUtil.getResourceValue("RollAgain");
  private static final String WHO_ROLLED = ResourceUtil.getResourceValue("WhoRolled");

  /**
   * Creates a RerollOnTuplesRule object.
   *
   * @param model The model used for the game.
   * @param alert The alert object used to display notifications.
   */
  public RerollOnTuplesRule(GameModel model, GameAlert alert) {
    super(model, alert);
  }

  /**
   * Checks the rules criteria. If the player has rolled tuples on their last turn their roll again.
   *
   * @param currentPlayer the player whose turn it currently is.
   */
  public void doRule(Player currentPlayer) {
    if (ArrayUtil.checkIfTuples(myModel.getRandomizable().getLastRoll())) {
      String message = String.format(WHO_ROLLED,currentPlayer.getName());
      myAlert.createGeneralInfoAlert(TUPLES, ROLL_AGAIN, message);
      myModel.doTurn(currentPlayer);
      myModel.callPlayerTileEvent(currentPlayer);
    }
  }
}