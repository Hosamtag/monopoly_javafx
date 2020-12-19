package ooga.controller.scorekeeper;

import java.io.IOException;
import java.util.List;
import ooga.controller.TopScoreKeeper;
import ooga.controller.util.ResourceUtil;
import ooga.model.Player;
import ooga.view.alerts.ErrorAlert;

/**
 * this class determines the top scorer given a list of players
 * and saves it based on how the methods are called
 */
public class TopScoreSaver {
  private Player topPlayer;
  private ErrorAlert thisError;

  public TopScoreSaver(){
    thisError = new ErrorAlert();
  }

  /**
   * stores the top scorer internally
   * @param playerList
   * @throws IOException
   * @throws InterruptedException
   */
  public void topScoreSaverInternally(List<Player> playerList){
    TopScoreKeeper thisInternalKeeper = new TopScoresInternal();
    getTopPlayer(playerList);
    updateScore(thisInternalKeeper);
  }

  /**
   * stores the top scorer externally
   * @param playerList
   * @throws IOException
   * @throws InterruptedException
   */
  public void topScoreSaverExternally(List<Player> playerList){
    TopScoreKeeper thisExternalKeeper = new TopScorersExternal();
     updateScore(thisExternalKeeper);
  }

  /**
   * updates who the top player and scorer is in the game
   * based on the type of scorekeeper is passed in
   * @param thisKeeper
   * @throws IOException
   */
  private void updateScore(TopScoreKeeper thisKeeper){
    try {
      if (thisKeeper.getTopScore() < topPlayer.getFunds()) {
        thisKeeper.updateTopPlayer(topPlayer.getName());
        thisKeeper.updateTopScore(topPlayer.getFunds());
      }
    } catch (Exception e){

    }
  }

  /**
   * gets the top player in the list of players
   * @param playerList
   */
  private void getTopPlayer(List<Player> playerList){
    int i = -1;
    int highScore = 0;
    for(int j = 0; j<playerList.size(); j++){
      int playerScore = playerList.get(j).getFunds();
      if(playerScore>highScore){
        highScore = playerScore;
        topPlayer = playerList.get(j);
      }
    }
  }


}
