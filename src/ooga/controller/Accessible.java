package ooga.controller;

import java.util.List;
import java.util.Map;
/**
 * @author Malvika Jain
 */
public interface Accessible {

  List<Object> getKeySetOfPropertiesFile(String fileName);

  Map<String, String> createMap(List<Object> keys, String file);

  String readFile(String fileName, String val);
}
