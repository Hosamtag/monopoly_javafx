package ooga.controller.readers.gameobjectsreaders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.Accessible;
import ooga.controller.propertiesaccessers.PropertyFileAccessor;

/**
 * this class allows the controller to access all the information needed to be accessed when loading
 * a saved game.
 */
public class SavedGameReader {

  private static final String PLAYER = "Player";
  private static final String NUMBER_OF_PLAYERS = "NumberOfPlayers";
  private static final String NAME = "name";
  private static final String TYPE = "type";
  private static final String ICON = "icon";
  private static final String CASH = "cash";
  private static final String CURRENT_TILE = "currentTile";
  private static final String OWNABLES = "ownables";
  private static final String TYPE_CAP = "Type";
  private static final String ICON_CAP = "Icon";
  private static final String CASH_CAP = "Cash";
  private static final String TILE_CAP = "Tile";
  private static final String OWNABLES_CAP = "Ownables";
  private static Accessible fileAccessor;

  /**
   * creates a object that is used to read values from a properties file
   */
  public SavedGameReader(){
    fileAccessor = new PropertyFileAccessor();
  }

  /**
   * creats a map of all the values that are needed to intialize a
   * saved game
   * @param initializationFile
   * @return a map of keys and values from the properties file used to
   * run a saved game
   * @throws IOException
   */
  public Map<String,String> gameObjectsViewReader(String initializationFile) throws IOException{
    List<Object> tileInfoKeys = fileAccessor
        .getKeySetOfPropertiesFile(initializationFile);
    return fileAccessor.createMap(tileInfoKeys, initializationFile);
  }

  /**
   * creates a map of all the players in the saved game
   * @param intialValues
   * @return a map of strings that has all the info needed for saved players
   */
  public List<Map<String,String>> createMapOfPlayers(Map<String, String> intialValues){
    List<Map<String,String>> playersInfo = new ArrayList<>();
    for(int i=1; i<= Integer.parseInt(intialValues.get(NUMBER_OF_PLAYERS)); i++){
      Map<String,String> thisPlayer = new HashMap<>();
      thisPlayer.put(NAME,intialValues.get(PLAYER+i));
      thisPlayer.put(TYPE,intialValues.get(PLAYER+i+TYPE_CAP));
      thisPlayer.put(ICON,intialValues.get(PLAYER+i+ICON_CAP));
      thisPlayer.put(CASH,intialValues.get(PLAYER+i+CASH_CAP));
      thisPlayer.put(CURRENT_TILE,intialValues.get(PLAYER+i+TILE_CAP));
      thisPlayer.put(OWNABLES,intialValues.get(PLAYER+i+OWNABLES_CAP));
      playersInfo.add(thisPlayer);
    }
    return playersInfo;
  }

}
