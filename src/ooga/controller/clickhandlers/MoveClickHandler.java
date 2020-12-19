package ooga.controller.clickhandlers;


import java.util.List;
import ooga.controller.ClickHandler;
import ooga.model.GameModel;
import ooga.model.Tile;
import ooga.view.GameViewable;
import ooga.view.TileView;

/**
 * Implements the Clickhandler to move the first player in the game to the tile that was clicked on
 *
 * @author Hosam Tageldin
 */
public class MoveClickHandler implements ClickHandler {

  private final static int FIRST_PLAYER = 0;
  private final GameModel myMonopolyModel;
  private final GameViewable myGameView;


  public MoveClickHandler(GameModel myMonopolyModel, GameViewable myGameView) {
    this.myMonopolyModel = myMonopolyModel;
    this.myGameView = myGameView;
  }

  /**
   * Moves the first player in the game to the tile that was clicked on
   */
  public void handleClick() {
    List<TileView> tileViewList = myGameView.getTileViews();
    List<Tile> tileList = myMonopolyModel.getTiles();
    for (int i = 0; i < myGameView.getTileViews().size(); i++) {
      if (tileViewList.get(i).isSelected()) {
        moveFirstPlayer(i, tileList, tileViewList);
        return;
      }
    }
  }

  private void moveFirstPlayer(int activeTile, List<Tile> tileList, List<TileView> tileViewList) {
    tileViewList.get(activeTile).resetSelection();
    myMonopolyModel
        .jumpPlayerToTile(myMonopolyModel.getPlayers().get(FIRST_PLAYER), tileList.get(activeTile));
    myMonopolyModel.callPlayerTileEvent(myMonopolyModel.getPlayers().get(FIRST_PLAYER));
  }

  /**
   * Update is called when the tile is clicked on using observer/observables
   */
  @Override
  public void update() {
    handleClick();
  }
}
