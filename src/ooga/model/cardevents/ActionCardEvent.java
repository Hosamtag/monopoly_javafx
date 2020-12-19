package ooga.model.cardevents;

import ooga.model.CardEvent;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.view.GameAlert;

public abstract class ActionCardEvent extends CardEvent {


  /**
   * @param values Values that will be used in the cards event.
   * @param alert Game alert object to be used to notify the users of the cards actions.
   */
  public ActionCardEvent(int[] values, GameAlert alert) {
    super(values, alert);
  }

  /**
   * Activates the cards event.
   *
   * @param model  the model used for this game.
   * @param player the player that drew the card.
   * @param prompt the prompt used for this card event.
   */
  public abstract void doCardAction(GameModel model, Player player, String prompt);
}
