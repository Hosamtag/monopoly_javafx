package ooga.controller.savers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ooga.controller.propertiesaccessers.PropertyFileMaker;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.Randomizable;
import ooga.model.Upgradable;

/**
 * this class saves a game that is being run
 * @author Malvika Jain
 */
public class GameSaver {

  PropertyFileMaker thisMaker;
  private static final String PLAYER = "Player";
  private static final String CURRENT_PLAYER = "CurrentPlayer";
  private static final String GAME_VERSION = "GameVersion";
  private static final String NUMBER_OF_PLAYERS = "NumberOfPlayers";
  private static final String MONOPOLY = "Monopoly";
  private static final String TYPE = "Type";
  private static final String ICON = "Icon";
  private static final String CASH = "Cash";
  private static final String DICE = "Dice";
  private static final String TILE = "Tile";
  private static final String NORMAL = "=Normal,";
  private static final String OWNABLE = "Ownables";
  private static final String INITAILIZATION_PATH = "data/gameversions/initialization/";

  /**
   * creates and organized the properties file that is used
   * to keep track of a saved game in a properties file
   * @param FileName
   * @param gameType
   * @param players
   * @param icons
   * @param playerTurn
   * @param myRandomizable
   */
  public GameSaver(String FileName,String gameType, List<Player> players, List<String> icons,int playerTurn, Randomizable myRandomizable){
    try {
      thisMaker = new PropertyFileMaker();
      thisMaker.createFile(INITAILIZATION_PATH + FileName);
      thisMaker.writeVals(GAME_VERSION + "=" + gameType);
      thisMaker.writeVals(CURRENT_PLAYER + "=" + playerTurn);
      thisMaker.writeVals(
          DICE + NORMAL + myRandomizable.getNumberOfSides() + "," + myRandomizable.getLastRoll()
              .size());
      thisMaker.writeVals(NUMBER_OF_PLAYERS + "=" + players.size());
      addPlayerInfo(players, icons);
      thisMaker.closeFile();
    } catch (IOException e) {

    }

  }

  /**
   * adds all the info needed for the players that are in the
   * game being saved to the properties file
   * @param players
   * @param icons
   * @throws IOException
   */
  private void addPlayerInfo(List<Player> players, List<String> icons) throws IOException {
    for (int i = 0; i < players.size(); i++) {
      int playerNum = i + 1;
      Player currentPlayer = players.get(i);
      thisMaker.writeVals(PLAYER + playerNum + "=" + currentPlayer.getName());
      thisMaker.writeVals(PLAYER + playerNum + TYPE + "=" + MONOPOLY);
      thisMaker.writeVals(PLAYER + playerNum + ICON + "=" + icons.get(i));
      thisMaker.writeVals(PLAYER + playerNum + CASH + "=" + currentPlayer.getFunds());
      thisMaker
          .writeVals(PLAYER + playerNum + TILE + "=" + currentPlayer.getCurrentTile().getName());
      List<String> ownedValList = new ArrayList<>();
      for (int j = 0; j < currentPlayer.getOwnables().size(); j++) {
        Ownable ownable = currentPlayer.getOwnables().get(j);
        String ownnableProperty = ownable.getName();
        try {
          ownnableProperty +=
              ":" + ((Upgradable) ownable).getCurrentBuilding().ordinal();
        } catch (ClassCastException e) {
          ownnableProperty += ":0";
        }
        ownedValList.add(ownnableProperty);
      }
      String ownedVals = String.join(",", ownedValList);
      thisMaker.writeVals(PLAYER + playerNum + OWNABLE + "=" + ownedVals);
    }

  }
}
