package ooga.model.tileevents.ownable;

import java.util.Arrays;
import java.util.Collections;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidPropertiesFileException;
import ooga.model.GameModel;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.model.tileevents.OwnableTileEvent;
import ooga.view.GameAlert;

public abstract class MonopolyOwnableTileEvent extends OwnableTileEvent {

  protected static final String YES = ResourceUtil.getResourceValue("Yes");
  protected static final String NO = ResourceUtil.getResourceValue("No");
  protected static final String TILE_PAY_OWNER = "TilePayOwner";
  protected static final String PURCHASE_WINDOW = ResourceUtil.getResourceValue("PurchaseWindow");
  protected static final String TILE_PURCHASE = ResourceUtil.getResourceValue("TilePurchase");


  /**
   * Creates a new MonopolyOwnableTileEvent object.
   *
   * @param alert the interface used for displaying notifications.
   */
  public MonopolyOwnableTileEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * Allows users to buy or auction unowned tiles.  Charges rent for owned tiles.
   *
   * @param owner the owner of the tile.
   * @param caller the player that called the event
   * @param currentTile this tile.
   * @param gameModel the model used for the game.
   */
  public void callEvent(Player owner, Player caller, OwnableTile currentTile, GameModel gameModel) {
    if (owner == gameModel.getBank()) {
      landOnUnowned(owner, caller, currentTile, gameModel);
    } else if (!owner.getName().equals(caller.getName())) {
      landOnOwned(owner, caller, currentTile, gameModel);
    }
  }

  protected abstract void landOnOwned(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel);

  protected void landOnUnowned(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel) {
    if (caller.getFunds() >= currentTile.getPropertyValue()) {
      String userDecision = promptUserPurchaseChoice(currentTile.getName(), String
          .format(TILE_PURCHASE, currentTile.getName(), currentTile.getPropertyValue())
      );
      if (userDecision.equals(YES)) {
        callerPaysMoney(owner, caller, currentTile.getPropertyValue(), gameModel);
        gameModel.exchangeOwnables(caller, owner, Collections.singletonList(currentTile));
        return;
      }
    }
    try {
      holdPropertyAuction(owner, caller, currentTile, gameModel);
    } catch (InvalidPropertiesFileException e) {
      gameAlert.createErrorAlertPopUp(e.getMessage());
    }
  }

  protected void holdPropertyAuction(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel) {
    gameModel.holdAuction(owner, owner, caller, currentTile);
  }


  protected String promptUserPurchaseChoice(String title, String header) {
    return gameAlert.getButtonsDialogChoice(
        Arrays.asList(YES, NO), title, header, MonopolyOwnableTileEvent.PURCHASE_WINDOW);
  }

  protected void displayGeneralInfoAlert(OwnableTile currentTile, int rentOwed) {
    String message = String
        .format(ResourceUtil.getResourceValue(TILE_PAY_OWNER), rentOwed,
            currentTile.getName());
    gameAlert.createGeneralInfoAlert(PURCHASE_WINDOW, currentTile.getName(), message);
  }

  protected void callerPaysMoney(Player owner, Player caller, int amountToPay,
      GameModel gameModel) {
    gameModel.payPlayer(owner, caller, amountToPay);
  }

}
