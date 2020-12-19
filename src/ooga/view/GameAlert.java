package ooga.view;

import java.util.List;
import ooga.model.Randomizable;


/**
 * The external facing interface for the game alert pop ups
 *
 * @author Isabella Knox, Alex Jimenez, Hosam Tageldin
 */
public interface GameAlert {

  /**
   * creates a general information alert pop up
   *
   * @param title
   * @param header
   * @param message
   */
  void createGeneralInfoAlert(String title, String header, String message);

  /**
   * creates a choice dialog pop up with a drop down
   *
   * @param options
   * @param title
   * @param header
   * @param label
   * @return
   */
  String getChoiceDialogInput(List<String> options, String title, String header, String label);

  /**
   * creates a dialog pop up with various buttons depending on the options given
   *
   * @param buttons
   * @param title
   * @param header
   * @param label
   * @return
   */
  String getButtonsDialogChoice(List<String> buttons, String title, String header, String label);

  /**
   * creates the Auction dialog used in the game
   *
   * @param options
   * @param inputPrompt
   * @param title
   * @param header
   * @return
   */
  List<String> createAuctionDialog(List<String> options, String inputPrompt, String title,
      String header);

  /**
   * creates a dialog alert that contains game dice to allow user to roll die for special game
   * options
   *
   * @param die
   * @param header
   * @param message
   */
  void createDicePopUp(Randomizable die, String header, String message);

  /**
   * creates a error dialog pop up
   *
   * @param message
   */
  void createErrorAlertPopUp(String message);
}
