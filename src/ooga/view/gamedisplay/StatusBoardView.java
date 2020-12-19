package ooga.view.gamedisplay;

import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ooga.model.Player;

/**
 * Creates all status board for the game which holds the player profiles
 *
 * @author Hosam Tageldin, Isabella Knox
 */
public class StatusBoardView {

  private final List<Player> myPlayers;
  private final Map<Player, ImageView> myIcons;

  private final int activePlayer;

  /**
   * Constructor
   *
   * @param players
   * @param iconMaps
   * @param activePlayer
   */
  public StatusBoardView(List<Player> players, Map<Player, ImageView> iconMaps, int activePlayer) {
    myPlayers = players;
    this.myIcons = iconMaps;
    this.activePlayer = activePlayer;
    createStatusBoard();
  }


  /**
   * Returns a Vbox that holds all the player profiles
   *
   * @return box
   */
  public Node createStatusBoard() {
    VBox box = new VBox();
    PlayerProfile player = new PlayerProfile(myPlayers, myIcons, activePlayer);
    box.getChildren().addAll(player.createComboBox());
    box.setId("summary");
    return box;
  }

}
