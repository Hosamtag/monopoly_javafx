package ooga.view.alerts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.model.Player;


/**
 * Creates the Trade alerts used in the game
 *
 * @author Hosam Tageldin
 */
public class TradeAlert extends MonopolyGameAlert {

  private static final int MIN_SPINNER = 0;
  private static final int MAX_SPINNER = 9000;
  private static final int INITIAL_VALUE_SPINNER = 500;
  private static final int STEP_SPINNER = 10;
  private static final int SPACING = 10;
  private final Spinner<Integer> moneyInputPlayer;
  private final Spinner<Integer> moneyInputSecondPlayer;


  /**
   * Constructor for the class
   */
  public TradeAlert() {
    super();
    moneyInputPlayer = new Spinner<>();
    moneyInputSecondPlayer = new Spinner<>();
    SpinnerValueFactory<Integer> valueFactory1 =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_SPINNER, MAX_SPINNER,
            INITIAL_VALUE_SPINNER, STEP_SPINNER);
    SpinnerValueFactory<Integer> valueFactory2 =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_SPINNER, MAX_SPINNER,
            INITIAL_VALUE_SPINNER, STEP_SPINNER);
    moneyInputPlayer.setValueFactory(valueFactory1);
    moneyInputSecondPlayer.setValueFactory(valueFactory2);
  }

  /**
   * Creates the trade alert
   *
   * @param players     the list of players currently in the game
   * @param inputPrompt the prompt of the alert
   * @param title       the title of the alert
   * @param header      the header of the alert
   * @return List of strings of the user input
   */
  public List<String> createTradeAlertProperties(List<Player> players, String inputPrompt,
      String title, String header) {
    Dialog<String> dialog = createTextInputDialog(title, header);
    dialog.getDialogPane()
        .setContent(createPropertiesAndMoneyBox(playerToStrings(players), inputPrompt));
    Optional<String> result = dialog.showAndWait();
    List<String> responses = new ArrayList<>();
    if (result.isPresent()) {
      responses.add(propertiesCombo.getValue());
      responses.add(propertiesInputPlayer.getText());
      responses.add(moneyInputPlayer.getValue().toString());
      responses.add(propertiesComboSecondPlayer.getValue());
      responses.add(propertiesInputSecondPlayer.getText());
      responses.add(moneyInputSecondPlayer.getValue().toString());
    }
    return responses;
  }

  private HBox createPropertiesAndMoneyBox(List<String> players, String inputPrompt) {
    VBox firstPlayer = createVBoxForInputs(propertiesInputPlayer, moneyInputPlayer, propertiesCombo,
        players, inputPrompt);
    VBox secondPlayer = createVBoxForInputs(propertiesInputSecondPlayer, moneyInputSecondPlayer,
        propertiesComboSecondPlayer, players, inputPrompt);
    HBox box = new HBox();
    box.setSpacing(SPACING);
    box.getChildren().addAll(firstPlayer, secondPlayer);
    return box;
  }

  /**
   * Creates a VBox that holds the user inputs
   *
   * @param propertiesInput
   * @param moneyInput
   * @param comboBox
   * @param options
   * @param inputPrompt
   * @return
   */
  protected VBox createVBoxForInputs(
      TextField propertiesInput, Spinner<Integer> moneyInput, ComboBox<String> comboBox,
      List<String> options, String inputPrompt) {
    VBox box = new VBox();
    comboBox.getItems().clear();
    comboBox.getItems().addAll(options);
    comboBox.setValue(options.get(0));
    propertiesInput.setPromptText(inputPrompt);
    box.getChildren().addAll(comboBox, propertiesInput, moneyInput);
    box.setSpacing(SPACING);
    return box;
  }

  private List<String> playerToStrings(List<Player> players) {
    List<String> playerNames = new ArrayList<>();
    for (Player player : players) {
      playerNames.add(player.getName());
    }
    return playerNames;
  }

}
