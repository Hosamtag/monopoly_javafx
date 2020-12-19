package ooga.view.factory;

import java.lang.reflect.Constructor;
import java.util.List;
import javafx.scene.image.ImageView;
import ooga.model.Player;
import ooga.view.BoardView;
import ooga.view.DiceView;
import ooga.view.TileView;
import ooga.view.boards.SquareBoardView;

/**
 * Factory to create a BoardView based off a string representing a board name
 *
 * @author Hosam Tageldin
 */
public class BoardViewFactory {

  private BoardView myBoardView;

  public BoardViewFactory(String boardName, List<TileView> myTiles, DiceView myDie,
      List<Player> myPlayers, List<ImageView> myIcons) {
    createBoardView(boardName, myTiles, myDie, myPlayers, myIcons);
  }

  /**
   * @return the new BoardView created by the factory
   */
  public BoardView getNewBoardView() {
    return myBoardView;
  }

  private void createBoardView(String boardName, List<TileView> myTiles, DiceView myDie,
      List<Player> myPlayers, List<ImageView> myIcons) {
    try {
      String str = "ooga.view.boards." + boardName + "BoardView";
      Class<?> modelClass = Class.forName(str);
      Class<?>[] modelConstructorParams = {List.class, DiceView.class, List.class, List.class};
      Constructor<?> constructor = modelClass.getConstructor(modelConstructorParams);
      Object[] modelConstructorObjects = {myTiles, myDie, myPlayers, myIcons};
      myBoardView = (BoardView) constructor.newInstance(modelConstructorObjects);
    } catch (Exception e) {
      myBoardView = new SquareBoardView(myTiles, myDie, myPlayers, myIcons);
    }
  }
}
