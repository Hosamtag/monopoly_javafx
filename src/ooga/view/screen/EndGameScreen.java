package ooga.view.screen;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import ooga.controller.TopScoreKeeper;
import ooga.controller.scorekeeper.TopScoreSaver;
import ooga.controller.scorekeeper.TopScoresInternal;
import ooga.model.Player;
import ooga.controller.util.ResourceUtil;
import ooga.view.SplashScreen;

/**
 * The last splash screen displayed once a game is completed. Gives user option to start a new game,
 * load a game, and save the high scores locally or to firebase
 *
 * @author Hosam Tageldin
 */
public class EndGameScreen extends SplashScreen {

  private final TopScoreSaver myScoreSaver;
  private final List<Player> myPlayers;

  public EndGameScreen(Tab pane, List<Player> playerList) {
    super(pane);
    myScoreSaver = new TopScoreSaver();
    myPlayers = playerList;
  }

  /**
   * Adds the header of the splash screen and the Text displaying this games high score and the all
   * time high score
   */
  protected void addContent() {
    Text header = new Text(ResourceUtil.getResourceValue("EndGame"));
    header.setId("header");
    myRoot.getChildren().add(header);
    TopScoreKeeper scoreKeeper;
    scoreKeeper = new TopScoresInternal();
    Text topScorerText = new Text(String
        .format(ResourceUtil.getResourceValue("HighScore"), scoreKeeper.getTopPlayer(),
            scoreKeeper.getTopScore()));
    pageHolder.getChildren().add(topScorerText);
  }

  /**
   * Adds the buttons supplied by the controller
   *
   * @param myButtons Buttons received from the SplashScreenButtonFactory to add onto the Splash
   */
  public void addButtons(List<Button> myButtons) {
    for (Player winner : getWinners()) {
      Text thisGameHighScoreText = new Text(String
          .format(ResourceUtil.getResourceValue("YourScore"), winner.getName(), winner.getFunds()));
      pageHolder.getChildren().add(thisGameHighScoreText);
    }
    for (Button button : myButtons) {
      button.getStyleClass().add("splash-screen-button");
      pageHolder.getChildren().add(button);
    }
    addSaveButtons();
    myRoot.getChildren().add(pageHolder);
  }

  /**
   * Saves the top score to the internal file
   */
  public void saveInternal() {
    myScoreSaver.topScoreSaverInternally(myPlayers);
  }

  /**
   * Saves the top score to the external file
   */
  public void saveExternal() {
    myScoreSaver.topScoreSaverExternally(myPlayers);
  }

  private void addSaveButtons() {
    Button saveInternalButton = new Button(ResourceUtil.getResourceValue("SaveInternal"));
    saveInternalButton.getStyleClass().add("splash-screen-button");
    saveInternalButton.setOnAction(e -> saveInternal());
    Button saveExternalButton = new Button(ResourceUtil.getResourceValue("SaveExternal"));
    saveExternalButton.setOnAction(e -> saveExternal());
    saveExternalButton.getStyleClass().add("splash-screen-button");
    pageHolder.getChildren().addAll(saveInternalButton, saveExternalButton);
  }

  private List<Player> getWinners() {
    List<Player> winnerNames = new ArrayList<>();
    for (Player player : myPlayers) {
      if (player.checkIfWinner()) {
        winnerNames.add(player);
      }
    }
    return winnerNames;
  }
}
