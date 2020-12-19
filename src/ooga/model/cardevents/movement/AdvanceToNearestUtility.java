package ooga.model.cardevents.movement;

import ooga.controller.util.ArrayUtil;
import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.model.Tile;
import ooga.model.cardevents.MovementCardEvent;
import ooga.view.GameAlert;

public class AdvanceToNearestUtility extends MovementCardEvent {

  private static final String TILE_PAY_OWNER = "TilePayOwner";
  private static final String PURCHASE_WINDOW = ResourceUtil.getResourceValue("PurchaseWindow");
  private static final String UTILITY_LAND = ResourceUtil.getResourceValue("UtilityLand");
  private static final String UTILITY_ROLL = ResourceUtil.getResourceValue("UtilityRoll");


  /**
   * Instantiates a new AdvanceToNearestUtility object.
   *
   * @param location The location where the player will be moved.
   * @param values Any other int values to be used. Not used in this card, leftover from the MovementCardEvent parent class.
   * @param alert The GameAlter object to be used to notify the player of the cards actions.
   */
  public AdvanceToNearestUtility(String location, int[] values, GameAlert alert) {
    super(location, values, alert);
  }

  /**
   * Moves the player to the nearest tile that has the monopoly name matching the location in the constructor.
   * If the tile is unkowned the player can buy, otherwise the rent is determined by the values in the constructor
   * and what the player rolls.
   *
   * @param model  the model used for this game.
   * @param player the player that drew the card.
   * @param prompt the prompt used for this card event.
   */
  @Override
  public void doCardAction(GameModel model, Player player, String prompt) {
    displayCardInfo(prompt);
    Tile currentTile = player.getCurrentTile();
    int currentTileIndex = model.getTiles().indexOf(currentTile);
    for (int tileIndex = 0; tileIndex < model.getTiles().size(); tileIndex++) {
      int modifiedIndex = (currentTileIndex + tileIndex) % model.getTiles().size();
      Tile tile = model.getTile(modifiedIndex);
      if (tile instanceof OwnableTile && ((OwnableTile) tile).getMonopoly().equals(getMyLocation())) {
        model.jumpPlayerToTile(player, model.getTile(modifiedIndex));
        handleTileInteraction(model, model.getTile(modifiedIndex), player, getValues());
        return;
      }
    }
  }

  private void handleTileInteraction(GameModel model, Tile tile, Player player, int[] values) {
    OwnableTile utilityTile = (OwnableTile) tile;
    Player owner = utilityTile.getOwner();
    if (owner == model.getBank()) {
      utilityTile.callMyEvent(player, model);
    } else if (owner != player) {
      getGameAlerts().createGeneralInfoAlert(UTILITY_LAND, UTILITY_ROLL, UTILITY_LAND);
      model.getRandomizable().getNextRoll();
      int rent = values[0] * utilityTile.calculateBaseRent() * ArrayUtil
          .sum(model.getRandomizable().getLastRoll());
      model.payPlayer(utilityTile.getOwner(), player, rent);
      String message = String
          .format(ResourceUtil.getResourceValue(TILE_PAY_OWNER), rent,
              tile.getName());
      getGameAlerts().createGeneralInfoAlert(PURCHASE_WINDOW, tile.getName(), message);
    }
  }


}
