package ooga.controller.propertiesaccessers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * This class extends properties and allows the keys to be recieved in an ordered way by redefinidng
 * how the keys are added source code inspired from https://stackoverflow.com/questions/5610822/convert-enumeration-to-a-set-list
 */
public class SequencedProperties extends Properties {


  private final List keyList = new ArrayList();

  /**
   * creates an enums for the keys in the properties file
   * so that they can be kept in a certain order
   * @return the values in the keylist as an emums
   */
  @Override
  public Enumeration keys() {
    return Collections.enumeration(keyList);
  }


  /**
   * redefines the put method in the properties class
   * to make the values be put in a certain order
   * @param key
   * @param value
   * @return
   */
  @Override
  public Object put(Object key, Object value) {
    if (!containsKey(key)) {
      keyList.add(key);
    }
    return super.put(key, value);
  }

}