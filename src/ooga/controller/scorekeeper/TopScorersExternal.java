package ooga.controller.scorekeeper;

import ooga.controller.TopScoreKeeper;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidFirebaseException;
import ooga.view.alerts.ErrorAlert;

/**
 * gets and stores the values associated with a top scorer
 * in an external database
 */
public class TopScorersExternal extends TopScoreKeeper {

  private static final String UPDATE_INDICTOR_KEY = "test";
  private static final String UPDATE_INDICTOR_VAL= "update";
  private static final String FIRST_PLACE_KEY= "firstPlace";
  private static final String FIRST_PLACE_SCORE_KEY = "firstPlaceScore";
  private final FirebaseAccessor firebase;

  private static final int THREAD_SLEEP_TIME = 5000;
  private ErrorAlert thisError;

  /**
   * sets up access to the firebase database
   */
  public TopScorersExternal(){
    firebase = new FirebaseAccessor();
    firebase.update(UPDATE_INDICTOR_KEY,UPDATE_INDICTOR_VAL);
    thisError = new ErrorAlert();
  }

  /**
   * updates the top scorer in the appropriate data place
   */
  @Override
  public void updateTopPlayer(String name) {
  firebase.update(name,FIRST_PLACE_KEY);
  }


  /**
   * gets the top player in the certain database
   * @return the string that is the top player
   */
  public String getTopPlayer() {
      return readPlayerInfo(FIRST_PLACE_KEY);

  }

  /**
   * updates the score in the appropriate data place
   * @param score
   */
  @Override
  public void updateTopScore(int score) {
    firebase.update(score, FIRST_PLACE_SCORE_KEY);
  }

  /**
   * gets the top score in the certain database
   * @return the int that is the top score
   */
  @Override
  public int getTopScore(){
    return Integer.parseInt(readPlayerInfo(FIRST_PLACE_SCORE_KEY));
  }

  /**
   * reads the info about the topscorer
   * @param key
   * @return the string associated with the top scorer for the name
   * or score
   */
  private String readPlayerInfo(String key)  {
    try {
      final String[] val = new String[1];
      firebase.readData(value -> val[0] = value, key);
      Thread.sleep(THREAD_SLEEP_TIME);
      return val[0];
    } catch (InvalidFirebaseException | InterruptedException e) {
      thisError.createErrorAlert(ResourceUtil.getResourceValue("CantAccess"));
    }
    return "";
  }

}


