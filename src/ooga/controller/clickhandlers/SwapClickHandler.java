package ooga.controller.clickhandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ooga.controller.ClickHandler;
import ooga.model.GameModel;
import ooga.model.Tile;
import ooga.view.GameViewable;
import ooga.view.TileView;

/**
 * Implements the Clickhandler to swap two tiles once there are two active tiles (tiles that have
 * been clicked on)
 *
 * @author Hosam Tageldin
 */
public class SwapClickHandler implements ClickHandler {

  private final static int ACTIVE_NUMBER_TO_SWAP = 2;
  private final static int FIRST_TILE = 0;
  private final static int SECOND_TILE = 1;
  private final GameModel myMonopolyModel;
  private final GameViewable myGameView;

  public SwapClickHandler(GameModel myMonopolyModel, GameViewable myGameView) {
    this.myMonopolyModel = myMonopolyModel;
    this.myGameView = myGameView;
  }

  /**
   * Checks if two active tiles exist, and if so, will call the private methods to swap the tiles
   */
  public void handleClick() {
    List<TileView> tileViewList = myGameView.getTileViews();
    List<Tile> tileList = createList(myMonopolyModel.getTiles());
    List<Integer> activeTileIndices = new ArrayList<>();
    for (int i = 0; i < tileViewList.size(); i++) {
      if (tileViewList.get(i).isSelected()) {
        activeTileIndices.add(i);
      }
    }
    if (activeTileIndices.size() == ACTIVE_NUMBER_TO_SWAP) {
      swapTiles(activeTileIndices, tileList, tileViewList);
    }
  }

  private void swapTiles(List<Integer> activeTiles, List<Tile> tileList,
      List<TileView> tileViewList) {
    int firstIndex = activeTiles.get(FIRST_TILE);
    int secondIndex = activeTiles.get(SECOND_TILE);
    Collections.swap(tileList, firstIndex, secondIndex);
    myMonopolyModel.setTiles(tileList);
    Collections.swap(tileViewList, firstIndex, secondIndex);
    myGameView.setTileViews(tileViewList);
  }

  /**
   * Update is called when the tile is clicked on using observer/observables
   */
  @Override
  public void update() {
    handleClick();
  }

  private List<Tile> createList(List<Tile> tileList) {
    List<Tile> newList = new ArrayList<>();
    for (Tile tile : tileList) {
      newList.add(tile);
    }
    return newList;
  }
}
