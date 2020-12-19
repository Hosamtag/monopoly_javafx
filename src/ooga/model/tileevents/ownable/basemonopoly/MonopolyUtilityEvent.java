package ooga.model.tileevents.ownable.basemonopoly;

import ooga.controller.util.ArrayUtil;
import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.model.tileevents.ownable.MonopolyOwnableTileEvent;
import ooga.view.GameAlert;

public class MonopolyUtilityEvent extends MonopolyOwnableTileEvent {

  private static final String HEADER = ResourceUtil.getResourceValue("UtilityLand");
  private static final String MESSAGE = ResourceUtil.getResourceValue("UtilityRoll");


  /**
   * Creates a MonpolyUtilityEvent object.
   * Charges rent by rolling the dice and multiplying outcome by base rent.
   *
   * @param alert the interface used for displaying notifications.
   */
  public MonopolyUtilityEvent(GameAlert alert) {
    super(alert);
  }

  @Override
  protected void landOnOwned(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel) {
    int rentOwed = calculateUtilityRent(gameModel, currentTile.calculateBaseRent());
    callerPaysMoney(owner, caller, rentOwed, gameModel);
    displayGeneralInfoAlert(currentTile, rentOwed);
  }

  private int calculateUtilityRent(GameModel myModel, int rent) {
    gameAlert.createGeneralInfoAlert(HEADER, MESSAGE, HEADER);
    myModel.getRandomizable().getNextRoll();
    return rent * ArrayUtil.sum(myModel.getRandomizable().getLastRoll());
  }

}
