package ooga.model;

import ooga.view.GameAlert;

public abstract class RuleEvent {

  protected final GameModel myModel;
  protected final GameAlert myAlert;

  /**
   * Creates a new Rule Event object.
   *
   * @param model the model used in this game
   * @param alert an alert object used to display notifications.
   */
  public RuleEvent(GameModel model, GameAlert alert) {
    myModel = model;
    myAlert = alert;
  }

  /**
   * Performs the check and actions specified by this rule.
   *
   * @param currentPlayer the player whose turn it currently is.
   */
  public abstract void doRule(Player currentPlayer);

}
