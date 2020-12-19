package ooga.view.screen;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import ooga.controller.util.ResourceUtil;
import ooga.view.SplashScreen;

/**
 * Adds the Game Details for the user to select
 *
 * @author Hosam Tageldin, Isabella Knox
 */
public class GameDetailsScreen extends SplashScreen {

  private ComboBox<Integer> myPlayerSelect;
  private Spinner<Integer> myStartingPrice;
  private Spinner<Integer> numberOfSides;
  private ComboBox<Integer> numberOfDie;

  private static final int DICE_MIN_SPINNER = 1;
  private static final int DICE_MAX_SPINNER = 3000;
  private static final int DICE_INITIAL_VALUE_SPINNER = 6;
  private static final int DICE_STEP_SPINNER = 1;

  private static final int MONEY_MIN_SPINNER = 100;
  private static final int MONEY_MAX_SPINNER = 3000;
  private static final int MONEY_INITIAL_VALUE_SPINNER = 1000;
  private static final int MONEY_STEP_SPINNER = 100;
  private static final Integer[] NUM_PLAYER_OPTIONS = {2, 3, 4, 5, 6};
  private static final Integer[] NUM_DICE_OPTIONS = {2, 3, 4};

  public GameDetailsScreen(Tab pane) {
    super(pane);
  }

  /**
   * @return the number of players specified for the game
   */
  public int getNumberOfPlayers() {
    return myPlayerSelect.getSelectionModel().getSelectedItem();
  }

  /**
   * @return the starting cash amount for each player in this game
   */
  public int getStartingPrice() {
    return myStartingPrice.getValue();
  }

  /**
   * @return the number of sides specified for the die
   */
  public String getNumberOfSides() {
    return Integer.toString(numberOfSides.getValue());
  }

  /**
   * @return the number of die specified for the game
   */
  public String getNumberOfDie() {
    return Integer.toString(numberOfDie.getValue());
  }

  /**
   * Adds the header of the GameDetailsScreen
   */
  protected void addContent() {
    Text header = new Text(ResourceUtil.getResourceValue("GameFeatures"));
    header.setId("header");
    myRoot.getChildren().add(header);
  }

  /**
   * Adds the buttons supplied by the controller for this splash screen
   *
   * @param myButtons Buttons received from the SplashScreenButtonFactory to add onto the Splash
   */
  public void addButtons(List<Button> myButtons) {
    addPlayerOptions();
    addDieOptions();
    addMoneyOptions();
    myRoot.getChildren().addAll(pageHolder);
    for (Button button : myButtons) {
      button.getStyleClass().add("splash-screen-button");
      myRoot.getChildren().addAll(button);
    }
  }

  private void addPlayerOptions() {
    Text playerNumber = new Text(ResourceUtil.getResourceValue("PlayerSelect"));
    myPlayerSelect = new ComboBox<>(FXCollections.observableArrayList(NUM_PLAYER_OPTIONS));
    myPlayerSelect.getSelectionModel().selectFirst();
    pageHolder.getChildren().addAll(playerNumber, myPlayerSelect);
  }

  private void addDieOptions() {
    Text dieSides = new Text(ResourceUtil.getResourceValue("DieSelectSides"));
    numberOfSides = new Spinner<>();
    SpinnerValueFactory<Integer> numberFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(DICE_MIN_SPINNER, DICE_MAX_SPINNER,
            DICE_INITIAL_VALUE_SPINNER, DICE_STEP_SPINNER);
    numberOfSides.setValueFactory(numberFactory);
    Text dieNumber = new Text(ResourceUtil.getResourceValue("DieSelectNumber"));
    numberOfDie = new ComboBox<>(FXCollections.observableArrayList(NUM_DICE_OPTIONS));
    numberOfDie.getSelectionModel().selectFirst();
    pageHolder.getChildren().addAll(dieSides, numberOfSides, dieNumber, numberOfDie);
  }

  private void addMoneyOptions() {
    Text moneySelect = new Text(ResourceUtil.getResourceValue("MoneyStart"));
    myStartingPrice = new Spinner<>();
    SpinnerValueFactory<Integer> costFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(MONEY_MIN_SPINNER, MONEY_MAX_SPINNER,
            MONEY_INITIAL_VALUE_SPINNER, MONEY_STEP_SPINNER);
    myStartingPrice.setValueFactory(costFactory);
    pageHolder.getChildren().addAll(moneySelect, myStartingPrice);
  }
}