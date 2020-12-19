package ooga.controller.scorekeeper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import ooga.controller.Accessible;
import ooga.controller.TopScoreKeeper;
import ooga.controller.propertiesaccessers.PropertyFileAccessor;
import ooga.controller.util.ResourceUtil;
import ooga.view.alerts.ErrorAlert;

/**
 * keeps track of the top score in an internal properties file
 */
public class TopScoresInternal extends TopScoreKeeper {

  private final Properties prop;
  private final Accessible thisAccessor;
  private static final String FIRSTPLACEKEY = "firstPlace";
  private static final String FIRSTPLACESCOREKEY = "firstPlaceScore";
  private static final String TOPSCOREPATH = "gameversions/topscores/topScore.properties";
  private static final String FULLTOPSCOREPATH = "data/gameversions/topscores/topScore.properties";
  private ErrorAlert thisError;

  /**
   * sets up the properties file accessor that is used to write to the desired properties file
   */
  public TopScoresInternal() {
    thisAccessor = new PropertyFileAccessor();
    prop = new Properties();
    thisError = new ErrorAlert();
  }

  /**
   * updates the top scorer in the appropriate data place
   *
   * @param name
   */
  @Override
  public void updateTopPlayer(String name) {
    try {
      FileOutputStream out = new FileOutputStream(FULLTOPSCOREPATH);
      prop.remove(FIRSTPLACEKEY);
      prop.setProperty(FIRSTPLACEKEY, name);
      prop.store(out, null);
      out.close();
    } catch (Exception IOException) {
      thisError.createErrorAlert(ResourceUtil.getResourceValue("CantAccess"));
    }
  }


  /**
   * gets the top player in the certain database
   *
   * @return the string that is the top player
   */
  @Override
  public String getTopPlayer() {
    try {
      InputStream in = getClass().getClassLoader().getResourceAsStream(TOPSCOREPATH);
      prop.load(in);
      in.close();
      return (thisAccessor.readFile(FULLTOPSCOREPATH, FIRSTPLACEKEY));
    } catch (Exception IOException) {
      thisError.createErrorAlert(ResourceUtil.getResourceValue("CantAccess"));
    }
    return null;
  }

  /**
   * updates the score in the appropriate data place
   *
   * @param score
   */
  @Override
  public void updateTopScore(int score) {
    try {
      FileOutputStream out = new FileOutputStream(FULLTOPSCOREPATH);
      prop.remove(FIRSTPLACESCOREKEY);
      prop.setProperty(FIRSTPLACESCOREKEY, score + "");
      prop.store(out, null);
      out.close();
    } catch (Exception IOException) {
      thisError.createErrorAlert(ResourceUtil.getResourceValue("CantAccess"));
    }
  }


  /**
   * gets the top score in the certain database
   *
   * @return the int that is the top score
   * @throws InterruptedException
   */
  @Override
  public int getTopScore() {
    try {
      InputStream in = getClass().getClassLoader().getResourceAsStream(TOPSCOREPATH);
      prop.load(in);
      in.close();
      return Integer.parseInt(thisAccessor.readFile(FULLTOPSCOREPATH, FIRSTPLACESCOREKEY));
    } catch (Exception IOException) {
      thisError.createErrorAlert(ResourceUtil.getResourceValue("CantAccess"));
    }
    return 0;
  }


}
