package ooga.view.alerts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ooga.controller.util.ResourceUtil;
import ooga.model.Randomizable;
import ooga.view.GameAlert;
import ooga.view.DiceView;
import ooga.view.dice.NormalDiceView;

/**
 * Creates the various kind of dialogs and pop ups used throughout the game to give the user
 * feedback and get user responses
 *
 * @author Isabella Knox
 */
public class MonopolyGameAlert implements GameAlert {

  private static final String NO_RESPONSE = "No response";
  private static final int SPACING = 10;

  protected final TextField input;
  private final Spinner<Integer> inputSpinner;
  protected final TextField propertiesInputPlayer;
  protected final TextField propertiesInputSecondPlayer;
  protected final ComboBox<String> propertiesCombo;
  protected final ComboBox<String> propertiesComboSecondPlayer;
  protected final ComboBox<String> comboBox;
  protected final List<ComboBox<String>> choice;
  private final ErrorAlert errorAlert;
  private static final int MIN_SPINNER = 0;
  private static final int MAX_SPINNER = 9000;
  private static final int INITIAL_VALUE_SPINNER = 10;
  private static final int STEP_SPINNER = 10;


  /**
   * Constructor for class
   */
  public MonopolyGameAlert() {
    input = new TextField();
    inputSpinner = new Spinner<>();
    SpinnerValueFactory<Integer> valueFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_SPINNER, MAX_SPINNER,
            INITIAL_VALUE_SPINNER, STEP_SPINNER);
    inputSpinner.setValueFactory(valueFactory);
    comboBox = new ComboBox<>();
    choice = new ArrayList<>();
    propertiesInputPlayer = new TextField();
    propertiesInputSecondPlayer = new TextField();
    propertiesCombo = new ComboBox<>();
    propertiesComboSecondPlayer = new ComboBox<>();
    setIds();
    errorAlert = new ErrorAlert();
  }

  /**
   * Creates an error alert pop up
   *
   * @param message
   */
  public void createErrorAlertPopUp(String message) {
    errorAlert.createErrorAlert(message);
  }


  /**
   * Use for displaying general information alert for the user
   *
   * @param title
   * @param message
   */
  public void createGeneralInfoAlert(String title, String header, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Creates a dialog pop up with a drop down
   *
   * @param options
   * @param title
   * @param header
   * @param label
   * @return
   */
  public String getChoiceDialogInput(List<String> options, String title, String header,
      String label) {
    Dialog<String> dialog = createChoiceDialog(options, title, header, label);
    return (String) getInfoFromDialogResponse(dialog);
  }

  /**
   * Creates a dialog pop up with various buttons depending on the options given
   *
   * @param buttons
   * @param title
   * @param header
   * @param label
   * @return
   */
  public String getButtonsDialogChoice(List<String> buttons, String title, String header,
      String label) {
    Dialog<ButtonType> alert = createButtonOptionsDialog(buttons, title, header, label);
    ButtonType buttonChosen = (ButtonType) getInfoFromDialogResponse(alert);
    return buttonChosen.getText();
  }


  /**
   * creates the Auction dialog pop up used in the game
   *
   * @param options
   * @param inputPrompt
   * @param title
   * @param header
   * @return
   */
  public List<String> createAuctionDialog(List<String> options, String inputPrompt, String title,
      String header) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle(title);
    dialog.setHeaderText(header);
    VBox box = createVBoxForInput(inputSpinner, comboBox, options, inputPrompt);
    dialog.getDialogPane().setContent(box);
    Optional<String> result = dialog.showAndWait();
    List<String> responses = new ArrayList<>();
    responses.add(comboBox.getValue());
    responses.add(inputSpinner.getValue().toString());
    return responses;
  }

  /**
   * creates a pop up with a dice which can be rolled in the alert
   *
   * @param die
   * @param header
   * @param message
   */
  public void createDicePopUp(Randomizable die, String header, String message) {
    Dialog<String> dialog = createTextInputDialog(header, header);
    die.getNextRoll();
    DiceView gameAlerts = new NormalDiceView(die);
    Button buttonType = new Button(ResourceUtil.getResourceValue("Roll"));
    buttonType.setId("roll");
    VBox box = new VBox();
    box.getChildren().add(new Text(message));
    box.getChildren().add(gameAlerts.createDiceBox());
    box.getChildren().add(buttonType);
    buttonType.setOnAction(e -> {
      gameAlerts.start();
    });
    dialog.getDialogPane().setContent(box);
    Optional<String> result = dialog.showAndWait();
  }


  private VBox createVBoxForInput(Spinner<Integer> input, ComboBox<String> comboBox,
      List<String> options, String inputPrompt) {
    VBox box = new VBox();
    comboBox.getItems().clear();
    comboBox.getItems().addAll(options);
    comboBox.setValue(options.get(0));
    input.setPromptText(inputPrompt);
    box.getChildren().addAll(comboBox, input);
    box.setSpacing(SPACING);
    return box;
  }

  private void setIds() {
    inputSpinner.setId("inputSpinnerBox");
    input.setId("inputBox");
    comboBox.setId("comboBox");
    propertiesInputPlayer.setId("inputBox");
    propertiesInputSecondPlayer.setId("inputBox");
    propertiesCombo.setId("comboBox");
    propertiesComboSecondPlayer.setId("comboBox");
  }


  private Dialog<ButtonType> createButtonOptionsDialog(List<String> buttons, String title,
      String header, String label) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(label);

    List<ButtonType> buttonTypeList = new ArrayList<>();
    for (String button : buttons) {
      ButtonType buttonType = new ButtonType(button);
      buttonTypeList.add(buttonType);
    }
    alert.getButtonTypes().setAll(buttonTypeList);

    return alert;
  }


  private Dialog<String> createChoiceDialog(List<String> options, String title, String header,
      String label) {
    ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
    dialog.setTitle(title);
    dialog.setHeaderText(header);
    dialog.setContentText(label);
    return dialog;
  }

  /**
   * creates a text input field
   *
   * @param title
   * @param header
   * @return
   */
  protected Dialog<String> createTextInputDialog(String title, String header) {
    Dialog<String> dialog = new TextInputDialog();
    dialog.setTitle(title);
    dialog.setHeaderText(header);
    return dialog;
  }

  private Object getInfoFromDialogResponse(Dialog dialog) {
    Optional<Object> result = dialog.showAndWait();
    if (result.isPresent()) {
      return result.get();
    }
    return NO_RESPONSE;
  }

}
