package ooga.model.cardevents.action;

import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.cardevents.ActionCardEvent;
import ooga.view.GameAlert;

public class GetOutOfJailFreeCardEvent extends ActionCardEvent implements Ownable {

  private static final String CARD = ResourceUtil.getResourceValue("ActionCard");
  private static final String GET_OUT_OF_JAIL_FREE_CARD = ResourceUtil
      .getResourceValue("GetOutOfJailFreeCard");

  private Player myOwner;

  /**
   * Instantiate a new GetOutOfJailFreeCardEvent object.
   *
   * @param values Values to be used in the cards action. Leftover from parent ActionCardEvent.
   * @param alert GameAlert object to be used to notify players of the cards actions.
   */
  public GetOutOfJailFreeCardEvent(int[] values, GameAlert alert) {
    super(new int[]{}, alert);
  }

  /**
   * @return The owner of the card.
   */
  @Override
  public Player getOwner() {
    return this.myOwner;
  }

  /**
   * @param newOwner the new owner of this object.
   */
  @Override
  public void changeOwner(Player newOwner) {
    this.myOwner = newOwner;
  }

  /**
   * @return The name of the card.
   */
  @Override
  public String getName() {
    return GET_OUT_OF_JAIL_FREE_CARD;
  }

  /**
   * Gives the object to the player to be used as a get out of jail free card.
   *
   * @param model  the model used for this game.
   * @param player the player that drew the card.
   * @param prompt the prompt used for this card event.
   */
  @Override
  public void doCardAction(GameModel model, Player player, String prompt) {
    displayCardInfo(prompt);
    player.addOwnable(this);
  }

  private void displayCardInfo(String prompt) {
    GameAlert myGameAlters = getGameAlerts();
    myGameAlters.createGeneralInfoAlert(CARD, GET_OUT_OF_JAIL_FREE_CARD, prompt);
  }
}
