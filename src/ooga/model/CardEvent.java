package ooga.model;

import ooga.view.GameAlert;

public abstract class CardEvent {

  private final GameAlert gameAlert;
  private final int[] values;

  /**
   * Creates a CardEvent object.
   *
   * @param values the values used by this card event
   * @param alert an alert object used to display notifications
   */
  public CardEvent(int[] values, GameAlert alert) {
    this.values = values;
    this.gameAlert = alert;
  }

  /**
   * Performs the action specified.
   *
   * @param model the model used for this game.
   * @param player the player that drew the card.
   * @param prompt the prompt used for this card event.
   */
  public abstract void doCardAction(GameModel model, Player player, String prompt);

  protected int[] getValues() {
    return this.values;
  }

  protected GameAlert getGameAlerts() {
    return this.gameAlert;
  }
}
