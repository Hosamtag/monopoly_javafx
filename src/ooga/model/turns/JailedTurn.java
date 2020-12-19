package ooga.model.turns;

import java.util.Arrays;
import ooga.controller.util.ArrayUtil;
import ooga.controller.util.ResourceUtil;
import ooga.model.BoardModel;
import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.Randomizable;
import ooga.model.Turn;
import ooga.model.cardevents.action.GetOutOfJailFreeCardEvent;
import ooga.model.monopoly.MonopolyStatusEffect;
import ooga.view.GameAlert;

public class JailedTurn extends Turn {

  private static final String YES = ResourceUtil.getResourceValue("Yes");
  private static final String NO = ResourceUtil.getResourceValue("No");
  private static final String ROLL_TUPLES = ResourceUtil.getResourceValue("RollTuples");
  private static final String USE_GET_OUT_OF_JAIL_FREE = ResourceUtil
      .getResourceValue("UseGetOutOfJail");
  private static final String ASK_PAY_BAIL = ResourceUtil.getResourceValue("AskToPayBail");
  private static final String JAIL_WINDOW = ResourceUtil.getResourceValue("JailWindow");
  private static final String FINISHED_MAX_SENTENCE = ResourceUtil
      .getResourceValue("FinishJailSentence");
  private static final String LEAVE_JAIL = ResourceUtil.getResourceValue("YouMayLeaveJail");

  private static final int BAIL_PRICE = 50;
  private static final int MAX_SENTENCE = 3;

  private int myTurnsInJail;
  private GameAlert myGameAlert;
  private GameModel myGameModel;
  private Randomizable myDice;

  /**
   * Creates a JailedTurn object.
   * This version allows for bail, roll doubles, waiting 3 turns, or get out of jail free cards.
   *
   * @param player the player whose turn it is.
   */
  public JailedTurn(Player player) {
    super(player);
    myTurnsInJail = 1;
  }

  /**
   * Performs a jailed turn.
   * In this version the player cannot move unless they pay bail, roll doubles, wait three turns,
   * or have a get out of jail free card.
   *
   * @param dice the Randomizable used for this turn
   * @param board the board used for this game
   * @param gameModel the model used for this game.
   * @param gameAlert the alert object used for displaying output
   */
  public void doTurn(Randomizable dice, BoardModel board, GameModel gameModel,
      GameAlert gameAlert) {
    this.myGameAlert = gameAlert;
    this.myGameModel = gameModel;
    this.myDice = dice;
    if (checkIfGetOutOfJailFree() || checkIfMaxSentence() || askToPayBail() || checkIfTuples()) {
      myPlayer.updateStatusEffect(MonopolyStatusEffect.NORMAL);
      myPlayer.doTurn(myDice, board, myGameModel, myGameAlert);
    } else {
      myTurnsInJail += 1;
    }
  }

  private boolean checkIfMaxSentence() {
    if (myTurnsInJail >= MAX_SENTENCE) {
      myGameAlert.createGeneralInfoAlert(JAIL_WINDOW, FINISHED_MAX_SENTENCE, LEAVE_JAIL);
      myGameModel.payPlayer(myGameModel.getBank(), myPlayer, BAIL_PRICE);
      return true;
    }
    return false;
  }

  private boolean askToPayBail() {
    String message = String.format(ASK_PAY_BAIL, BAIL_PRICE);
    String userDecision = promptUserPurchaseChoice(JAIL_WINDOW, message, JAIL_WINDOW);
    if (userDecision.equals(YES)) {
      myGameModel.payPlayer(myGameModel.getBank(), myPlayer, BAIL_PRICE);
      return true;
    }
    return false;
  }

  private boolean checkIfTuples() {
    myDice.getNextRoll();
    if (ArrayUtil.checkIfTuples(myDice.getLastRoll())) {
      myGameAlert.createGeneralInfoAlert(JAIL_WINDOW, ROLL_TUPLES, LEAVE_JAIL);
      return true;
    }
    return false;
  }

  private boolean checkIfGetOutOfJailFree() {
    for (Ownable ownable : myPlayer.getOwnables()) {
      if (ownable instanceof GetOutOfJailFreeCardEvent) {
        myPlayer.removeOwnable(ownable);
        myGameAlert.createGeneralInfoAlert(JAIL_WINDOW, USE_GET_OUT_OF_JAIL_FREE, LEAVE_JAIL);
        return true;
      }
    }
    return false;
  }

  private String promptUserPurchaseChoice(String title, String header, String label) {
    return myGameAlert.getButtonsDialogChoice(
        Arrays.asList(YES, NO), title, header, label);
  }

}
