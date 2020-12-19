package ooga.model.tileevents.ownable;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import ooga.controller.util.ArrayUtil;
import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.view.GameAlert;

public class MoswapolyPropertyTileEvent extends MonopolyOwnableTileEvent {

  private static final String ROLL_MESSAGE = ResourceUtil.getResourceValue("UtilityRollPurchase");
  private static final String RANDOM_PROPERTY_ASSIGNMENT = ResourceUtil
      .getResourceValue("RandomPropertyAssignment");
  private static final String RANDOMLY_ASSIGNED = ResourceUtil
      .getResourceValue("RandomlyAssignedTo");


  /**
   * Creates a MoswapolyPropertyTileEvent object.
   * Used in Moswapoly.
   * Prompts the player to buy the property and otherwise randomly assigns it to another player. Rent
   * and price determined by the property values scaled by what the user rolls on their dice.
   *
   * @param alert the interface used for displaying notifications.
   */
  public MoswapolyPropertyTileEvent(GameAlert alert) {
    super(alert);
  }

  @Override
  protected void landOnUnowned(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel) {
    if (caller.getFunds() >= currentTile.getPropertyValue()) {
      gameAlert.createGeneralInfoAlert(PURCHASE_WINDOW, ROLL_MESSAGE, PURCHASE_WINDOW);
      int playerPropertyCost = calculatePropertyCost(gameModel, currentTile.getPropertyValue());
      String userDecision = promptUserPurchaseChoice(currentTile.getName(), String
          .format(TILE_PURCHASE, currentTile.getName(), playerPropertyCost)
      );
      if (userDecision.equals(YES)) {
        callerPaysMoney(owner, caller, playerPropertyCost, gameModel);
        gameModel.exchangeOwnables(caller, owner, Collections.singletonList(currentTile));
        return;
      }
    }
    propertyToRandomPlayer(owner, caller, currentTile, gameModel);
  }

  @Override
  protected void landOnOwned(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel) {
    int rentOwed = calculateRentOwed(owner, currentTile, currentTile.calculateBaseRent());
    callerPaysMoney(owner, caller, rentOwed, gameModel);
    displayGeneralInfoAlert(currentTile, rentOwed);
  }

  private int calculateRentOwed(Player owner, OwnableTile currentTile, int rent) {
    int propertiesOwned = 0;
    for (Ownable ownedItem : owner.getOwnables()) {
      if (ownedItem instanceof OwnableTile) {
        propertiesOwned++;
      }
    }
    return (int) Math.sqrt(propertiesOwned) * rent;
  }

  private int calculatePropertyCost(GameModel myModel, int rent) {
    myModel.getRandomizable().getNextRoll();
    return (int) (rent * Math.sqrt(ArrayUtil.sum(myModel.getRandomizable().getLastRoll())));
  }

  private void propertyToRandomPlayer(Player owner, Player caller, OwnableTile currentTile,
      GameModel gameModel) {
    Player newOwner = caller;
    List<Player> players = gameModel.getPlayers();
    Random random = new Random();
    while (newOwner == caller) {
      int randomIndex = random.nextInt(players.size());
      newOwner = players.get(randomIndex);
    }
    gameModel.exchangeOwnables(newOwner, owner, Collections.singletonList(currentTile));
    String randomlyAssignedTo = String
        .format(RANDOMLY_ASSIGNED, newOwner.getName(), currentTile.getName());
    gameAlert
        .createGeneralInfoAlert(PURCHASE_WINDOW, RANDOM_PROPERTY_ASSIGNMENT, randomlyAssignedTo);

  }

}
