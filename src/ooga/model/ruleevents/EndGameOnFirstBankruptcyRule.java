package ooga.model.ruleevents;

import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.RuleEvent;
import ooga.view.GameAlert;


public class EndGameOnFirstBankruptcyRule extends RuleEvent {

  int numPlayers;

  /**
   * Creates a EndGameOnFirstBankruptcyRule object.
   *
   * @param model The model used for the game.
   * @param alert The alert object used to display notifications.
   */
  public EndGameOnFirstBankruptcyRule(GameModel model, GameAlert alert) {
    super(model, alert);
    numPlayers = myModel.getPlayers().size();
  }

  /**
   * Checks the specified win conditions. If any players have gone bankrupts all other players win.
   *
   * @param currentPlayer the player whose turn it currently is.
   */
  public void doRule(Player currentPlayer) {
    if (myModel.getPlayers().size() < numPlayers) {
      Player winner = findPlayerWithMaxFunds(currentPlayer, myModel);
      myModel.declareGameOver();
      winner.declareWinner();
    }
  }

  private Player findPlayerWithMaxFunds(Player currentPlayer, GameModel myModel) {
    Player maxPlayer = currentPlayer;
    int maxFunds = currentPlayer.getFunds();
    for (Player eachPlayer : myModel.getPlayers()) {
      if (eachPlayer.getFunds() > maxFunds) {
        maxFunds = eachPlayer.getFunds();
        maxPlayer = eachPlayer;
      }
    }
    return maxPlayer;
  }


}
