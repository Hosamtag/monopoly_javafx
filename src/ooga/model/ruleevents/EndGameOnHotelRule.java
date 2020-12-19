package ooga.model.ruleevents;

import ooga.model.Buildings;
import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.RuleEvent;
import ooga.model.Tile;
import ooga.model.Upgradable;
import ooga.view.GameAlert;

public class EndGameOnHotelRule extends RuleEvent {

  /**
   * Creates a EndGameOnHotelRule object.
   *
   * @param model The model used for the game.
   * @param alert The alert object used to display notifications.
   */
  public EndGameOnHotelRule(GameModel model, GameAlert alert) {
    super(model, alert);
  }

  /**
   * Checks the specified win conditions. First player to build a hotel wins.
   *
   * @param currentPlayer the player whose turn it currently is.
   */
  public void doRule(Player currentPlayer) {
    for (Tile eachTile : myModel.getTiles()) {
      if (eachTile instanceof Upgradable) {
        endGameIfHasHotel((Upgradable) eachTile);
      }
    }
  }

  private void endGameIfHasHotel(Upgradable property) {
    if (property.getCurrentBuilding() == Buildings.HOTEL) {
      myModel.declareGameOver();
      ((Ownable) property).getOwner().declareWinner();
    }
  }

}
