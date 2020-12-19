package ooga.model.ruleevents;

import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.RuleEvent;
import ooga.view.GameAlert;

public class LastManStandingWinRule extends RuleEvent {

  private static final int NUMBER_OF_PLAYERS_AT_END_OF_GAME = 1;

  /**
   * Creates a LastManStandingWinRule object.
   *
   * @param model The model used for the game.
   * @param alert The alert object used to display notifications.
   */
  public LastManStandingWinRule(GameModel model, GameAlert alert) {
    super(model, alert);
  }

  /**
   * Checks the specified win criteria. Last person who is not bankrupt wins.
   *
   * @param currentPlayer the player whose turn it currently is.
   */
  public void doRule(Player currentPlayer) {
    if (myModel.getPlayers().size() == NUMBER_OF_PLAYERS_AT_END_OF_GAME) {
      myModel.declareGameOver();
      myModel.getPlayers().get(0).declareWinner();
    }
  }
}
