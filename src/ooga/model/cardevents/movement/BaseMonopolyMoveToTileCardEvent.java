package ooga.model.cardevents.movement;

import ooga.exceptions.OwnableNotFoundException;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.cardevents.MovementCardEvent;
import ooga.view.GameAlert;

public class BaseMonopolyMoveToTileCardEvent extends MovementCardEvent {

  /**
   * @param location The location where the player will be moved.
   * @param values Any other int values to be used. Not used in this card, leftover from the MovementCardEvent parent class.
   * @param alert The GameAlter object to be used to notify the player of the cards actions.
   */
  public BaseMonopolyMoveToTileCardEvent(String location, int[] values, GameAlert alert) {
    super(location, values, alert);
  }

  /**
   * Jumps the specified player to the tile whose name matched the location parameter of the constructor.
   *
   * @param model  the model used for this game.
   * @param player the player that drew the card.
   * @param prompt the prompt used for this card event.
   */
  @Override
  public void doCardAction(GameModel model, Player player, String prompt) {
    displayCardInfo(prompt);
    String location = getMyLocation();
    try{
      model.jumpPlayerToTile(player, model.getTileFromName(location));
      model.getTileFromName(location).callMyEvent(player, model);
    }catch(OwnableNotFoundException e){
      getGameAlerts().createErrorAlertPopUp(e.getMessage());
    }
  }

}
