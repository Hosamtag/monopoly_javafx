package ooga.model.ruleevents;

import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.RuleEvent;
import ooga.model.monopoly.MonopolyStatusEffect;
import ooga.view.GameAlert;

public class SpeedBasedOnPositionRule extends RuleEvent {

  /**
   * Creates a SpeedBasedOnPositionRule object.
   *
   * @param model The model used for the game.
   * @param alert The alert object used to display notifications.
   */
  public SpeedBasedOnPositionRule(GameModel model, GameAlert alert) {
    super(model, alert);
  }

  /**
   * Enacts the specified rule. If the player tile is an odd index, the roll of the next die is doubled (FAST status.
   * If the player tile is an even index, the roll of the next die is halved (SLOW status).
   *
   * @param currentPlayer the player whose turn it currently is.
   */
  public void doRule(Player currentPlayer) {
    if (myModel.getTiles().indexOf(currentPlayer.getCurrentTile()) % 2 == 0) {
      currentPlayer.updateStatusEffect(MonopolyStatusEffect.FAST);
    } else {
      currentPlayer.updateStatusEffect(MonopolyStatusEffect.SLOW);
    }
  }

}
