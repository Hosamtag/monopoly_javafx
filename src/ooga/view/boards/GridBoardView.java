package ooga.view.boards;

import java.util.List;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import ooga.model.Player;
import ooga.view.BoardView;
import ooga.view.DiceView;
import ooga.view.TileView;

/**
 * Creates a grid view of all of the game tiles
 *
 * @author Hosam Tageldin
 */
public class GridBoardView extends BoardView {

  private final FlowPane myBoardHolder;
  private final DiceView myDie;
  private final List<TileView> tileViewList;


  public GridBoardView(List<TileView> tilesForGame, DiceView myDie, List<Player> myPlayers,
      List<ImageView> icons) {
    super(tilesForGame, myPlayers, icons);
    tileViewList = tilesForGame;
    myBoardHolder = new FlowPane();
    myBoardHolder.setId("InOrderBoard");
    this.myDie = myDie;
  }

  /**
   * @return the Pane holding the board with all the players and the die in the appropriate location
   */
  protected Pane populateBoardView() {
    myBoardHolder.getChildren().clear();
    Pane topLevelPane = new Pane();
    createBoard();
    initializePlayers();
    myBoardHolder.getChildren().add(myDie.createDiceBox());
    topLevelPane.getChildren().add(myBoardHolder);
    return topLevelPane;
  }

  /**
   * Creates the Board by setting the top, right, left, and bottom portions of the board
   * appropriately
   */
  protected void createBoard() {
    for (int i = tileViewList.size() - 1; i >= 0; i--) {
      TileView currentTile = tileViewList.get(i);
      currentTile.setRotate(0);
      myBoardHolder.getChildren().add(currentTile);
    }
  }

}
