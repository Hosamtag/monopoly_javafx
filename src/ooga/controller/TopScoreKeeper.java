package ooga.controller;

import java.io.IOException;

public abstract class TopScoreKeeper {

  /**
   * updates the top scorer in the appropriate data place
   * @param name
   * @throws IOException
   */
  public abstract void updateTopPlayer(String name) throws IOException;

  /**
   * gets the top player in the certain database
   * @return the string that is the top player
   */
  public abstract String getTopPlayer();
  /**
   * updates the score in the appropriate data place
   * @param score
   * @throws IOException
   */
  public abstract void updateTopScore(int score) throws IOException;
  /**
   * gets the top score in the certain database
   * @return the int that is the top score
   */
  public abstract int getTopScore();

}
