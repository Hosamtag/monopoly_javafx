package ooga.model.cardevents;

import ooga.controller.util.ResourceUtil;
import ooga.model.CardEvent;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.view.GameAlert;

public abstract class MovementCardEvent extends CardEvent {

  private static final String CARD = ResourceUtil.getResourceValue("ActionCard");
  private static final String MOVEMENT_EVENT = ResourceUtil.getResourceValue("MovementEvent");
  private static final String MESSAGE = ResourceUtil.getResourceValue("MoveTo");
  private static final String NEW_LINE = "\n";

  private final String myLocation;

  /**
   * Instantiates a new movement card object that is used to move a player somewhere.
   *
   * @param location The name of the tile that the player will move to.
   * @param values Values to be used for any other card actions.
   * @param alert GameAlert object to be used to notify of the cards actions.
   */
  public MovementCardEvent(String location, int[] values, GameAlert alert) {
    super(values, alert);
    this.myLocation = location;
  }

  /**
   * Activates the action of the card.
   *
   * @param model  the model used for this game.
   * @param player the player that drew the card.
   * @param prompt the prompt used for this card event.
   */
  public abstract void doCardAction(GameModel model, Player player, String prompt);

  protected String getMyLocation() {
    return this.myLocation;
  }

  protected void displayCardInfo(String prompt) {
    GameAlert myGameAlters = getGameAlerts();
    String locationString = String
        .format(MESSAGE, getMyLocation());
    String message = prompt + NEW_LINE + locationString;
    myGameAlters.createGeneralInfoAlert(CARD, MOVEMENT_EVENT, message);
  }
}
