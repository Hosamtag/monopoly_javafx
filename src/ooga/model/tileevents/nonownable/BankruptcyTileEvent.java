package ooga.model.tileevents.nonownable;

import ooga.controller.util.ArrayUtil;
import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.view.GameAlert;

public class BankruptcyTileEvent extends NonOwnableTileEvent {

  private static final String ROLL_EVENS = ResourceUtil.getResourceValue("RollEvens");
  private static final String BANKRUPTCY_SAFE = ResourceUtil.getResourceValue("BankruptcySafe");
  private static final String BANKRUPTCY_OUT = ResourceUtil.getResourceValue("BankruptcyOut");

  private static final int EVEN = 2;

  /**
   * Creates a BankruptyTileEvent object.
   * Used for instantly bankrupting a player.
   *
   * @param alert the interface used for displaying notifications.
   */
  public BankruptcyTileEvent(GameAlert alert) {
    super(alert);
  }

  /**
   * If the player rolls an odd number they are instantly bankrupted.
   * If they roll an even number they're safe.
   *
   * @param player the player that called the event.
   * @param value the value associated with this event.
   * @param gameModel the model used for this game.
   */
  @Override
  public void callEvent(Player player, int value, GameModel gameModel) {
    String header = String
        .format(TILE_EVENT, player.getCurrentTile().getName());
    gameAlert.createGeneralInfoAlert(ACTION_CARD, header, ROLL_EVENS);
    gameModel.getRandomizable().getNextRoll();
    int rollSum = ArrayUtil.sum(gameModel.getRandomizable().getLastRoll());
    if (rollSum % EVEN == 0) {
      gameAlert.createGeneralInfoAlert(ACTION_CARD, header, BANKRUPTCY_SAFE);
    } else {
      gameAlert.createGeneralInfoAlert(ACTION_CARD, header, BANKRUPTCY_OUT);
      removeOwnables(player, gameModel);
      gameModel.removePlayer(player);
    }
  }

  private void removeOwnables(Player bankruptPlayer, GameModel model) {
    for (Ownable eachOwnable : bankruptPlayer.getOwnables()) {
      bankruptPlayer.removeOwnable(eachOwnable);
      model.getBank().addOwnable(eachOwnable);
      eachOwnable.changeOwner(model.getBank());
    }
  }


}
