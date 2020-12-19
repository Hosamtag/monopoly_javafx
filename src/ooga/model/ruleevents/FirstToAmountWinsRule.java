package ooga.model.ruleevents;

import java.util.ResourceBundle;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.RuleEvent;
import ooga.view.GameAlert;

public class FirstToAmountWinsRule extends RuleEvent {

  private static final String PROPERTY_KEY = "MoneyNeededToWin";
  private static final String WIN_CONDITION_PROPERTIES = "properties/WinConditions";
  private final int WIN_AMOUNT;

  /**
   * Creates a FirstToAmountWinsRule object.
   *
   * @param model The model used for the game.
   * @param alert The alert object used to display notifications.
   */
  public FirstToAmountWinsRule(GameModel model, GameAlert alert) {
    super(model, alert);
    ResourceBundle resourceBundle;
    resourceBundle = ResourceBundle.getBundle(WIN_CONDITION_PROPERTIES);
    WIN_AMOUNT = Integer.parseInt(resourceBundle.getString(PROPERTY_KEY));
  }

  /**
   * Checks the specified win conditions. First player to the specified amount wins.
   *
   * @param currentPlayer the player whose turn it currently is.
   */
  public void doRule(Player currentPlayer) {
    for (Player eachPlayer : myModel.getPlayers()) {
      if (eachPlayer.getFunds() >= WIN_AMOUNT) {
        eachPlayer.declareWinner();
        myModel.declareGameOver();
      }
    }
  }
}
