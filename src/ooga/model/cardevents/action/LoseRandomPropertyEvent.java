package ooga.model.cardevents.action;

import java.util.List;
import java.util.Random;
import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.cardevents.ActionCardEvent;
import ooga.view.GameAlert;

public class LoseRandomPropertyEvent extends ActionCardEvent {

  private static final String CARD = ResourceUtil.getResourceValue("ActionCard");
  private static final String LOSE_RANDOM_PROPERTY = ResourceUtil
      .getResourceValue("LoseRandomProperty");
  private static final String YOU_LOST = ResourceUtil.getResourceValue("YouLost");

  /**
   * Instantiate a new LoseRandomPropertyEvent object.
   *
   * @param values Values to be used in the cards action. Leftover from parent ActionCardEvent.
   * @param alert GameAlert object to be used to notify players of the cards actions.
   */
  public LoseRandomPropertyEvent(int[] values, GameAlert alert) {
    super(values, alert);
  }

  /**
   * Takes a random ownable from the players list of ownables and returns it to the bank.
   *
   * @param model  the model used for this game.
   * @param player the player that drew the card.
   * @param prompt the prompt used for this card event.
   */
  @Override
  public void doCardAction(GameModel model, Player player, String prompt) {
    displayCardInfo(prompt, LOSE_RANDOM_PROPERTY);
    List<Ownable> playerOwnables = player.getOwnables();
    if (playerOwnables.size() > 0) {
      Random random = new Random();
      int ownableIndex = random.nextInt(playerOwnables.size());
      Ownable ownableToRemove = playerOwnables.get(ownableIndex);
      player.removeOwnable(ownableToRemove);
      model.getBank().addOwnable(ownableToRemove);
      ownableToRemove.changeOwner(model.getBank());
      String message = String.format(YOU_LOST, ownableToRemove.getName());
      displayCardInfo(LOSE_RANDOM_PROPERTY, message);
    }
  }

  /**
   * @param message The message to be displayed.
   * @param header Another message to be displayed.
   */
  public void displayCardInfo(String message, String header) {
    GameAlert myGameAlert = getGameAlerts();
    myGameAlert.createGeneralInfoAlert(CARD, message, header);
  }

}
