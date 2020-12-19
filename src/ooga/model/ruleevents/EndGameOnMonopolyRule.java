package ooga.model.ruleevents;

import ooga.model.GameModel;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.model.RuleEvent;
import ooga.model.Tile;
import ooga.view.GameAlert;

public class EndGameOnMonopolyRule extends RuleEvent {

  /**
   * Creates a EndGameOnMonopolyRule object.
   *
   * @param model The model used for the game.
   * @param alert The alert object used to display notifications.
   */
  public EndGameOnMonopolyRule(GameModel model, GameAlert alert) {
    super(model, alert);
  }


  /**
   * Checks the specified win conditions. First player to get a monopoly of properties wins.
   *
   * @param currentPlayer the player whose turn it currently is.
   */
  public void doRule(Player currentPlayer) {
    for (Tile eachTile : myModel.getTiles()) {
      if (eachTile instanceof OwnableTile) {
        endGameIfMonopoly((OwnableTile) eachTile);
      }
    }
  }

  private void endGameIfMonopoly(OwnableTile property) {
    if (myModel.checkMonopoly(property.getOwner(), property)) {
      myModel.declareGameOver();
      property.getOwner().declareWinner();
    }
  }
}
