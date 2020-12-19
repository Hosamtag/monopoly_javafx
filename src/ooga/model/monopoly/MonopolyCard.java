package ooga.model.monopoly;

import ooga.model.Card;
import ooga.model.CardEvent;
import ooga.model.GameModel;
import ooga.model.Player;

public class MonopolyCard implements Card {

  private final String myPrompt;
  private final CardEvent myAction;

  /**
   * Instantiates a new MonopolyCard object.
   *
   * @param cardPrompt The prompt to be displayed when the card is used.
   * @param action The CardEvent object to occur when the card is used.
   */
  public MonopolyCard(String cardPrompt, CardEvent action) {
    myPrompt = cardPrompt;
    myAction = action;
  }

  /**
   * Activates the stored CardEvent.
   *
   * @param player The player that drew the card
   * @param model  the model used for this game.
   */
  public void callCardAction(Player player, GameModel model) {
    myAction.doCardAction(model, player, myPrompt);
  }

  /**
   * @return The prompt stored for the card.
   */
  public String getCardDescription() {
    return myPrompt;
  }
}
