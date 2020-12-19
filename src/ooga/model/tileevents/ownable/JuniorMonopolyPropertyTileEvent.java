package ooga.model.tileevents.ownable;

import java.util.Collections;
import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.view.GameAlert;

public class JuniorMonopolyPropertyTileEvent extends MonopolyOwnableTileEvent {

  protected static final String TILE_PURCHASED = ResourceUtil.getResourceValue("TilePurchased");

  /**
   * Creates a JuniorMonopolyPropertyTileEvent object. Differs from the parent MonopolyOwnableTileEvent
   * in that players are required to buy unowned tiles so there are no auctions.
   *
   * @param alert the interface used to display notifications.
   */
  public JuniorMonopolyPropertyTileEvent(GameAlert alert) {
    super(alert);
  }

  @Override
  protected void landOnUnowned(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel) {
    if (caller.getFunds() >= currentTile.getPropertyValue()) {
      callerPaysMoney(owner, caller, currentTile.getPropertyValue(), gameModel);
      gameModel.exchangeOwnables(caller, owner, Collections.singletonList(currentTile));
      String message = String
          .format(TILE_PURCHASED, currentTile.getName(),
              currentTile.getPropertyValue());
      gameAlert.createGeneralInfoAlert(PURCHASE_WINDOW, currentTile.getName(), message);
    }
  }

  @Override
  protected void landOnOwned(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel) {
    int rentOwed = currentTile.calculateBaseRent();
    if (gameModel.checkMonopoly(owner, currentTile)) {
      rentOwed = rentOwed + rentOwed;
    }
    callerPaysMoney(owner, caller, rentOwed, gameModel);
    displayGeneralInfoAlert(currentTile, rentOwed);
  }

}
