package ooga.controller.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.exceptions.InvalidGameVersionException;

public final class PropertiesFileMapMaker {

  private PropertiesFileMapMaker() {
  }

  /**
   * creates a map of all the keys and values in a properties file
   * @param keys
   * @param file
   * @return
   * @throws InvalidGameVersionException
   * @throws IOException
   * @throws InvalidKeyException
   */
  public static Map<String,String> createMap(List<Object> keys, String file)
      throws InvalidGameVersionException, IOException, InvalidKeyException {
    Map<String, String> retMap = new HashMap<>();
    for (Object keyVal : keys) {
      retMap.put(keyVal.toString(), PropertiesFileValueReader
          .readFile(file, keyVal.toString()));
    }
    return retMap;
  }

}
