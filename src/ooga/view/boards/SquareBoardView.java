package ooga.view.boards;

import java.util.List;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ooga.model.Player;
import ooga.view.BoardView;
import ooga.view.DiceView;
import ooga.view.TileView;

/**
 * Creates the regular monopoly board view that everyone knows with equal numbers of tiles on each
 * side.
 *
 * @author Hosam Tageldin
 */
public class SquareBoardView extends BoardView {

  private final static int BOTTOM_ROW_ROTATION = 0;
  private final static int TOP_ROW_ROTATION = 180;
  private final static int LEFT_COLUMN_ROTATION = 90;
  private final static int RIGHT_COLUMN_ROTATION = 270;
  private final static int NUMBER_OF_SIDES = 4;
  private final static int THIRD_SIDE = 3;
  private final static int SECOND_SIDE = 2;


  private final BorderPane myBoardHolder;
  private final DiceView myDie;
  private final List<TileView> tileViewList;

  public SquareBoardView(List<TileView> tilesForGame, DiceView myDie, List<Player> myPlayers,
      List<ImageView> icons) {
    super(tilesForGame, myPlayers, icons);
    tileViewList = tilesForGame;
    myBoardHolder = new BorderPane();
    myBoardHolder.getStyleClass().add("board");
    this.myDie = myDie;
  }

  /**
   * @return the Pane holding the board with all the players and the die in the appropriate location
   */
  protected Pane populateBoardView() {
    Pane topLevelPane = new Pane();
    createBoard();
    initializePlayers();
    topLevelPane.getChildren().add(myBoardHolder);
    return topLevelPane;
  }

  /**
   * Creates the Board by setting the top, right, left, and bottom portions of the board
   * appropriately
   */
  protected void createBoard() {
    myBoardHolder.setTop(createTopRow(tileViewList));
    myBoardHolder.setLeft(createLeftColumn(tileViewList));
    myBoardHolder.setRight(createRightColumn(tileViewList));
    myBoardHolder.setBottom(createBottomRow(tileViewList));
    myBoardHolder.setCenter(myDie.createDiceBox());
  }

  private HBox createBottomRow(List<TileView> tileViewList) {
    HBox row = new HBox();
    for (int i = (tileViewList.size() / NUMBER_OF_SIDES); i >= 0; i--) {
      TileView currentTile = tileViewList.get(i);
      currentTile.setRotate(BOTTOM_ROW_ROTATION);
      row.getChildren().add(currentTile);
    }
    return row;
  }

  private HBox createTopRow(List<TileView> tileViewList) {
    HBox row = new HBox();
    for (int i = (tileViewList.size() / NUMBER_OF_SIDES) * SECOND_SIDE;
        i <= (tileViewList.size() / NUMBER_OF_SIDES) * THIRD_SIDE; i++) {
      TileView currentTile = tileViewList.get(i);
      currentTile.setRotate(TOP_ROW_ROTATION);
      row.getChildren().add(currentTile);
    }
    return row;
  }

  private VBox createLeftColumn(List<TileView> tileViewList) {
    VBox column = new VBox();
    for (int i = ((tileViewList.size() / NUMBER_OF_SIDES) * SECOND_SIDE) - 1;
        i > tileViewList.size() / NUMBER_OF_SIDES; i--) {
      TileView currentTile = tileViewList.get(i);
      currentTile.setRotate(LEFT_COLUMN_ROTATION);
      column.getChildren().add(currentTile);
    }
    return column;
  }

  private VBox createRightColumn(List<TileView> tileViewList) {
    VBox column = new VBox();
    for (int i = ((tileViewList.size() / NUMBER_OF_SIDES) * THIRD_SIDE) + 1;
        i < tileViewList.size(); i++) {
      TileView currentTile = tileViewList.get(i);
      currentTile.setRotate(RIGHT_COLUMN_ROTATION);
      column.getChildren().add(currentTile);
    }
    return column;
  }
}
