package ooga.model.cardevents.action;

import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.cardevents.ActionCardEvent;
import ooga.view.GameAlert;

public class BaseMonopolyTaxCardEvent extends ActionCardEvent {

  private static final String CARD = ResourceUtil.getResourceValue("ActionCard");
  private static final String PLAYER_TAXED = ResourceUtil.getResourceValue("PlayerTaxed");
  private static final String PAY_MONEY = ResourceUtil.getResourceValue("PayMoney");
  private static final String NEW_LINE = "\n";

  /**
   * Instantiate a new BaseMonopolyTaxCardEvent object.
   *
   * @param values Values to be used in the cards action. Leftover from parent ActionCardEvent.
   * @param alert GameAlert object to be used to notify players of the cards actions.
   */
  public BaseMonopolyTaxCardEvent(int[] values, GameAlert alert) {
    super(values, alert);
  }

  /**
   * Takes the amount of money passed into values of the constructor away from the player.
   *
   * @param model  the model used for this game.
   * @param player the player that drew the card.
   * @param prompt the prompt used for this card event.
   */
  @Override
  public void doCardAction(GameModel model, Player player, String prompt) {
    displayCardInfo(prompt);
    model.payPlayer(model.getBank(), player, this.getValues()[0]);
  }

  /**
   * @param prompt The prompt to be displayed.
   */
  public void displayCardInfo(String prompt) {
    GameAlert myGameAlters = getGameAlerts();
    String receiveMoneyString = String
        .format(PAY_MONEY, this.getValues()[0]);
    String message = prompt + NEW_LINE + receiveMoneyString;
    myGameAlters.createGeneralInfoAlert(CARD, PLAYER_TAXED, message);
  }


}
