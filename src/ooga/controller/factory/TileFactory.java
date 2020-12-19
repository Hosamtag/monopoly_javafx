package ooga.controller.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import ooga.controller.util.ArrayUtil;
import ooga.controller.util.ResourceUtil;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.model.tileevents.OwnableTileEvent;
import ooga.exceptions.InvalidTileDataException;
import ooga.model.Tile;
import ooga.view.GameAlert;
import ooga.view.TileView;
import ooga.view.alerts.ErrorAlert;
import ooga.view.alerts.MonopolyGameAlert;

/**
 * Creates the Tiles for the model and the TileViews for the interface based off the properties
 * files
 *
 * @author James Arnold, Hosam Tageldin
 */
public class TileFactory {

  private final List<Map<String, String>> tilePropertiesMap;
  private final List<Tile> myTiles;
  private final List<TileView> myTileViews;

  /**
   * Creates a TileFactory object that instantiates the Tiles for a game. Creates back-end Tile
   * objects and corresponding front-end TileView objects. Instantiates TileEvents within the
   * Tiles.
   *
   * @param tileProperties the list of tile properties maps with the key being the property and
   *                       value is the value assigned to the property
   */
  public TileFactory(List<Map<String, String>> tileProperties) {
    this.tilePropertiesMap = tileProperties;
    myTiles = new ArrayList<>();
    myTileViews = new ArrayList<>();
    createTiles();
    createViewTiles();
  }

  /**
   * Gets the list of Tiles used by the back-end.
   *
   * @return the list of Tile objects.
   */
  public List<Tile> getMyTiles() {
    return myTiles;
  }

  /**
   * Gets the list of TileViews used by the front-end.
   *
   * @return the list of TileView objects.
   */
  public List<TileView> getMyTileViews() {
    return myTileViews;
  }

  private void createTiles() {
    for (Map<String, String> tileProperties : tilePropertiesMap) {
      String name = tileProperties.get("name");
      String type = tileProperties.get("type");
      String monopoly = tileProperties.get("monopoly");
      int price = Integer.parseInt(tileProperties.get("price"));
      try {
        try {
          instantiatePropertyTiles(type, name, price, tileProperties);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException | ClassCastException e) {
          instantiateNonPropertyTile(name, type, monopoly, tileProperties);
        }
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchMethodException e) {
        throw new InvalidTileDataException(
            String.format(ResourceUtil.getResourceValue("TileException"), name));
      }
    }
  }

  private void instantiatePropertyTiles(String type, String name, int price,
      Map<String, String> tileProperties)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    String str = "ooga.model." + type;
    Class<?> modelClass = Class.forName(str);
    NonOwnableTileEvent myEvent = (NonOwnableTileEvent) eventCreator(
        tileProperties.get("event"));
    NonOwnableTileEvent myPassive = (NonOwnableTileEvent) eventCreator(
        tileProperties.get("passiveEvent"));
    Class<?>[] modelConstructorParams = {String.class, NonOwnableTileEvent.class,
        NonOwnableTileEvent.class, int.class};
    Constructor<?> constructor = modelClass.getConstructor(modelConstructorParams);
    Object[] modelConstructorObjects = {name, myEvent, myPassive, price};
    myTiles.add((Tile) constructor.newInstance(modelConstructorObjects));
  }

  private void instantiateNonPropertyTile(String name, String type, String monopoly,
      Map<String, String> tileProperties)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    String str = "ooga.model." + type;
    Class<?> modelClass = Class.forName(str);
    OwnableTileEvent myEvent = (OwnableTileEvent) eventCreator(tileProperties.get("event"));
    OwnableTileEvent myPassive = (OwnableTileEvent) eventCreator(
        tileProperties.get("passiveEvent"));
    Constructor<?> constructor = modelClass.getDeclaredConstructors()[0];
    List<Object> myObjects = new ArrayList<>(
        Arrays.asList(name, myEvent, myPassive, monopoly));
    myObjects.addAll(fetchIntObjects(tileProperties));
    myTiles.add((Tile) constructor.newInstance(myObjects.toArray()));
  }


  private List<Object> fetchIntObjects(Map<String, String> tileProperties) {
    List<Object> myIntObjects = new ArrayList<>();
    List<String> keySet = new ArrayList<>(tileProperties.keySet());
    Collections.sort(keySet);
    for (String key : keySet) {
      try {
        myIntObjects.add(Integer.parseInt(tileProperties.get(key)));
      } catch (NumberFormatException e) {
        try {
          myIntObjects.add(ArrayUtil.stringToInt(tileProperties.get(key)));
        } catch (NumberFormatException ex) {
          //DO NOTHING
        }
      }
    }
    return myIntObjects;
  }

  private Object eventCreator(String event)
      throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
    Class<?>[] constructorParams = {GameAlert.class};
    Constructor<?> constructor = Class.forName("ooga.model.tileevents." + event)
        .getConstructor(constructorParams);
    Object[] params = {new MonopolyGameAlert()};
    return constructor.newInstance(params);
  }


  private void createViewTiles() {
    for (Map<String, String> tileProperties : tilePropertiesMap) {
      String name = tileProperties.get("name");
      String viewType = tileProperties.get("viewType");
      String fileName = tileProperties.get("fileName");
      String price = tileProperties.get("price");
      String color = tileProperties.get("monopoly");
      try {
        try {
          instantiatePropertyViewTiles(name, fileName, color, price, viewType, tileProperties);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
          instantiateNonPropertyViewTiles(name, fileName, price, viewType);
        }
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchMethodException e) {
        ErrorAlert alert = new ErrorAlert();
        alert.createErrorAlert(e.getMessage());
        throw new InvalidTileDataException(
            String.format(ResourceUtil.getResourceValue("TileException"), name));
      }
    }
  }

  private void instantiatePropertyViewTiles(String name, String fileName, String color,
      String price, String viewType, Map<String, String> tileProperties)
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Class<?> modelClass = Class.forName("ooga.view.tile." + viewType + "TileView");
    Class<?>[] modelConstructorParams = {String.class, String.class, String.class,
        String.class, String[].class, String[].class};
    Constructor<?> constructor = modelClass.getConstructor(modelConstructorParams);
    Object[] modelConstructorObjects = {name, fileName, color, price,
        tileProperties.get("rentStructure").split(","),
        tileProperties.get("upgradeCosts").split(",")};
    myTileViews.add((TileView) constructor.newInstance(modelConstructorObjects));
  }

  private void instantiateNonPropertyViewTiles(String name, String fileName, String price,
      String viewType)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
    Class<?> modelClass = Class.forName("ooga.view.tile." + viewType + "TileView");
    Class<?>[] modelConstructorParams = {String.class, String.class, String.class};
    Constructor<?> constructor = modelClass.getConstructor(modelConstructorParams);
    Object[] modelConstructorObjects = {name, fileName, price};
    myTileViews.add((TileView) constructor.newInstance(modelConstructorObjects));
  }


}
