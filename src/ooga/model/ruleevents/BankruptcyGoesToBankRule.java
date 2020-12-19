package ooga.model.ruleevents;

import java.util.List;
import ooga.controller.Observer;
import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.RuleEvent;
import ooga.view.GameAlert;

public class BankruptcyGoesToBankRule extends RuleEvent implements Observer {

  /**
   * Creates a BankruptcyGoesToBankRule object.
   *
   * @param model The model used for the game.
   * @param alert The alert object used to display notifications.
   */
  public BankruptcyGoesToBankRule(GameModel model, GameAlert alert) {
    super(model, alert);
  }

  /**
   * Causes for the rule conditions to be checked and the rule carried out if applicable.
   *
   * @param currentPlayer the player whose turn it currently is.
   */
  public void doRule(Player currentPlayer) {
    update();
  }

  /**
   * Iterates over all players stored in the model and bankrupts them if they are in dept. When a player is
   * bankrupt their assets are all given back to the bank.
   */
  public void update() {
    List<Player> copyOfPlayersList = List.copyOf(myModel.getPlayers());
    for (Player eachPlayer : copyOfPlayersList) {
      if (eachPlayer.getFunds() <= 0) {
        removeOwnables(eachPlayer);
        myModel.removePlayer(eachPlayer);
      }
    }
  }

  private void removeOwnables(Player bankruptPlayer) {
    for (Ownable eachOwnable : bankruptPlayer.getOwnables()) {
      bankruptPlayer.removeOwnable(eachOwnable);
      myModel.getBank().addOwnable(eachOwnable);
      eachOwnable.changeOwner(myModel.getBank());
    }
  }
}
