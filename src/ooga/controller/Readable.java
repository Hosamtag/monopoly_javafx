package ooga.controller;

import java.io.IOException;

/**
 * small scale inheritance hierarchy allows readbale to be a feature of the reading class and then
 * the meaning of the object can be changed and be dynamic
 */
public interface Readable {

  /**
   * creates the parameter
   *
   * @param version
   */
  void createNewGameVersion(String version) throws IOException;


}
