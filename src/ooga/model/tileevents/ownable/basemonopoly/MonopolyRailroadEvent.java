package ooga.model.tileevents.ownable.basemonopoly;

import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.model.tileevents.ownable.MonopolyOwnableTileEvent;
import ooga.view.GameAlert;

public class MonopolyRailroadEvent extends MonopolyOwnableTileEvent {

  /**
   * Creates a new MonopolyRailroadEvent object.
   * Charges rent based on number of Railroads owned.
   *
   * @param alert the interface used to display notifications.
   */
  public MonopolyRailroadEvent(GameAlert alert) {
    super(alert);
  }

  @Override
  protected void landOnOwned(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel) {
    int rentOwed = calculateRailroadRent(owner, currentTile, currentTile.calculateBaseRent());
    callerPaysMoney(owner, caller, rentOwed, gameModel);
    displayGeneralInfoAlert(currentTile, rentOwed);
  }

  private int calculateRailroadRent(Player owner, OwnableTile currentTile, int rent) {
    int numRailroadsOwned = 0;
    for (Ownable ownedItem : owner.getOwnables()) {
      if (ownedItem instanceof OwnableTile) {
        String currentItemMonopoly = ((OwnableTile) ownedItem).getMonopoly();
        if (currentTile.getMonopoly().equals(currentItemMonopoly)) {
          numRailroadsOwned++;
        }
      }
    }
    return numRailroadsOwned * rent;
  }
}
