package ooga.controller.loaders;

import java.util.List;
import java.util.Map;
import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.Upgradable;

/**
 * Assigns the ownables to the appropriate players once a game is being loaded in
 *
 * @author Hosam Tageldin
 */
public class OwnableAssigner {

  private GameModel myMonopolyModel;

  public OwnableAssigner() {
    myMonopolyModel = null;
  }

  /**
   * Assigns the ownables based off the list of maps which includes what the player owns and how
   * many upgrades they have for each property
   *
   * @param myMonopolyModel the current model interface for the game
   * @param gameProps       represents what each player owns
   */
  public void assignOwnables(GameModel myMonopolyModel, List<Map<String, String>> gameProps) {
    this.myMonopolyModel = myMonopolyModel;
    List<Player> playerList = myMonopolyModel.getPlayers();
    for (int i = 0; i < gameProps.size(); i++) {
      String ownables = gameProps.get(i).get("ownables");
      disperseOwnables(ownables, playerList.get(i));
    }
  }

  private void disperseOwnables(String ownables, Player player) {
    if (!ownables.equals("")) {
      String[] ownedTiles = ownables.split(",");
      for (String eachTileString : ownedTiles) {
        String[] tileProp = eachTileString.split(":");
        Ownable eachTile = (Ownable) myMonopolyModel.getTileFromName(tileProp[0]);
        eachTile.changeOwner(player);
        player.addOwnable(eachTile);
        myMonopolyModel.getBank().getOwnables().remove(eachTile);
        handleUpgrades(eachTile, Integer.parseInt(tileProp[1]));
      }
    }
  }

  private void handleUpgrades(Ownable eachTile, int numberOfUpgrades) {
    for (int j = 0; j < numberOfUpgrades; j++) {
      ((Upgradable) eachTile).upgrade(myMonopolyModel);
    }
  }
}
