package ooga.controller.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidTileDataException;
import ooga.model.Player;
import ooga.model.Tile;

/**
 * Creates the List of players and associated icons either based off the file read in from a loaded
 * game, or the saved list of maps from the splash screens
 *
 * @author Hosam Tageldin
 */
public class PlayerFactory {

  private final List<Map<String, String>> playerPropertiesMap;
  private final List<Player> myPlayers;
  private final List<String> myPlayerIcons;
  private final List<Tile> myTiles;

  /**
   * Creates a PlayerFactory object that instantiates the players of the game. Creates Players,
   * PlayerIcons: used for both front-end and back-end instantiation.
   *
   * @param playerProperties map of properties for each player containing the property as the key
   *                         and the value as the value.
   * @param myTiles          the tiles used for this game
   */
  public PlayerFactory(List<Map<String, String>> playerProperties, List<Tile> myTiles) {
    this.playerPropertiesMap = playerProperties;
    this.myTiles = myTiles;
    myPlayers = new ArrayList<>();
    myPlayerIcons = new ArrayList<>();
    createPlayers();
  }

  /**
   * Gets the Player objects used for this game.
   *
   * @return the list of players.
   */
  public List<Player> getPlayers() {
    return myPlayers;
  }

  /**
   * Gets the icons used for players in this game.
   *
   * @return the list of icon names.
   */
  public List<String> getPlayerIcons() {
    return myPlayerIcons;
  }

  private void createPlayers() {
    for (Map<String, String> tileProperties : playerPropertiesMap) {
      String name = tileProperties.get("name");
      String type = tileProperties.get("type");
      int cash = Integer.parseInt(tileProperties.get("cash"));
      String startingTile = tileProperties.get("currentTile");
      myPlayerIcons.add(tileProperties.get("icon"));
      try {
        String str = "ooga.model.monopoly." + type + "Player";
        Class<?> modelClass = Class.forName(str);
        Class<?>[] modelConstructorParams = {String.class, Tile.class, int.class};
        Constructor<?> constructor = modelClass.getConstructor(modelConstructorParams);
        Object[] modelConstructorObjects = {name, getTile(startingTile), cash};
        myPlayers.add((Player) constructor.newInstance(modelConstructorObjects));
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchMethodException e) {
        throw new InvalidTileDataException(
            String.format(ResourceUtil.getResourceValue("PlayerException"), name));
      }
    }
  }

  private Tile getTile(String tileName) {
    for (Tile tile : myTiles) {
      if (tile.getName().equals(tileName)) {
        return tile;
      }
    }
    return myTiles.get(0);
  }

}