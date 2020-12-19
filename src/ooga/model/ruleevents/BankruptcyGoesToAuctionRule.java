package ooga.model.ruleevents;

import java.util.List;
import ooga.controller.Observer;
import ooga.controller.util.ResourceUtil;
import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.RuleEvent;
import ooga.view.GameAlert;

public class BankruptcyGoesToAuctionRule extends RuleEvent implements Observer {

  private static final String BANKRUPTCY = ResourceUtil.getResourceValue("Bankruptcy");
  private static final String BANKRUPTCY_AUCTION = ResourceUtil.getResourceValue("BankruptcyAuction");
  private static final String WHO_IS_BANKRUPT = ResourceUtil.getResourceValue("WhoIsBankrupt");

  /**
   * Creates a BankruptcyGoesToAuctionRule object.
   *
   * @param model The model used for the game.
   * @param alert The alert object used to display notifications.
   */
  public BankruptcyGoesToAuctionRule(GameModel model, GameAlert alert) {
    super(model, alert);
  }

  /** Causes for the rule conditions to be checked and the rule carried out if applicable.
   *
   * @param currentPlayer the player whose turn it currently is.
   */
  public void doRule(Player currentPlayer) {
    update();
  }


  /**
   * Iterates over all players stored in the model and bankrupts them if they are in dept. When a player is
   * bankrupt their assets are auctioned to the remaining players.
   */
  public void update() {
    List<Player> copyOfPlayersList = List.copyOf(myModel.getPlayers());
    for (Player eachPlayer : copyOfPlayersList) {
      if (eachPlayer.getFunds() <= 0) {
        auctionOwnables(eachPlayer);
        myModel.removePlayer(eachPlayer);
      }
    }
  }

  private void auctionOwnables(Player bankruptPlayer) {
    String message = String.format(WHO_IS_BANKRUPT,bankruptPlayer.getName());
    myAlert.createGeneralInfoAlert(BANKRUPTCY, BANKRUPTCY_AUCTION, message);
    for (Ownable eachOwnable : bankruptPlayer.getOwnables()) {
      myModel.holdAuction(bankruptPlayer, myModel.getBank(), bankruptPlayer, eachOwnable);
    }
  }
}
