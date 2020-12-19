package ooga.model.tileevents;

import java.util.List;
import java.util.Random;
import ooga.controller.util.ResourceUtil;
import ooga.model.Card;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.view.GameAlert;

public abstract class DrawCardEvent extends NonOwnableTileEvent {

  protected static final String DRAW_CARD = ResourceUtil.getResourceValue("DrawCard");

  /**
   * Created a DrawCardEvent object
   *
   * @param alert the alert object used to display notifications.
   */
  public DrawCardEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Performs the event specified by this object.
   *
   * @param player the player that called the event.
   * @param value the value associated with this event.
   * @param gameModel the model used for this game.
   */
  @Override
  public void callEvent(Player player, int value, GameModel gameModel) {
    gameAlert.createGeneralInfoAlert(ACTION_CARD, getMyMessage(), DRAW_CARD);
    List<Card> allCards = gameModel.getCards();
    Random rand = new Random();
    int randomIndex = rand.nextInt(allCards.size());
    Card randomChosenCard = allCards.get(randomIndex);
    randomChosenCard.callCardAction(player, gameModel);
  }

  protected abstract String getMyMessage();

}
