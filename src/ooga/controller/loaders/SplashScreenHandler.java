package ooga.controller.loaders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Tab;
import ooga.controller.factory.SplashScreenButtonFactory;
import ooga.controller.util.ResourceUtil;
import ooga.view.alerts.ErrorAlert;
import ooga.view.screen.GameDetailsScreen;
import ooga.view.screen.GameRulesScreen;
import ooga.view.screen.GameSelectScreen;
import ooga.view.screen.LandingPageScreen;
import ooga.view.screen.PlayerSelectScreen;

/**
 * Handles loading up a game through the splash screen. Puts all the inputted players and
 * randomizables in the appropriate structure to be used by the factories in creating the players
 * and the die.
 *
 * @author Hosam Tageldin
 */
public class SplashScreenHandler {

  private static final String NORMAL = "Normal";

  private String myGameVersion;
  private String[] chosenDieInformation;
  private int playerCount;
  private int startingPrice;
  private final List<Map<String, String>> playerProperties;
  private final List<String> chosenIcons;
  private final List<String> inputtedNames;
  private final GameLoader myGameLoader;
  private final Tab myTab;
  private boolean userSelected;
  private List<String> selectedRules;

  public SplashScreenHandler(Tab myTab, GameLoader myGameLoader) {
    this.myGameLoader = myGameLoader;
    this.myTab = myTab;
    playerProperties = new ArrayList<>();
    chosenIcons = new ArrayList<>();
    inputtedNames = new ArrayList<>();
    setScene();
  }

  /**
   * Sets the initial landing screen of the Game
   */
  public void setScene() {
    LandingPageScreen startScreen = new LandingPageScreen(myTab);
    SplashScreenButtonFactory myFactory = new SplashScreenButtonFactory(this, startScreen);
    startScreen.addButtons(myFactory.getButtons());
  }

  /**
   * Handles the functionality if user elects to load the game on first splash screen
   *
   * @param startScreen used to get the game language
   */
  public void loadGame(LandingPageScreen startScreen) {
    ResourceUtil.setLanguage(startScreen.getLanguage());
    myGameLoader.loadGame();
  }

  /**
   * Handles the functionality if user elects to load the game from Firebase on first splash screen
   *
   * @param startScreen used to get the game language
   */
  public void externalLoadGame(LandingPageScreen startScreen) {
    ResourceUtil.setLanguage(startScreen.getLanguage());
    myGameLoader.externalLoadGame();
  }

  /**
   * Handles the functionality if user elects to start a new game on first splash screen
   *
   * @param startScreen used to get the game language
   */
  public void startGame(LandingPageScreen startScreen) {
    ResourceUtil.setLanguage(startScreen.getLanguage());
    startNewGame();
  }

  /**
   * Starts a new game, can also be called by the GameLoader whenever a user wants to start a new
   * game midway through another game
   */
  public void startNewGame() {
    userSelected = false;
    GameSelectScreen gameSelectScreen = new GameSelectScreen(myTab);
    SplashScreenButtonFactory myFactory = new SplashScreenButtonFactory(this, gameSelectScreen);
    gameSelectScreen.addButtons(myFactory.getButtons());
  }

  /**
   * Takes the users to the Rule select screen for them to choose their own custom game rules
   *
   * @param gameSelectScreen used to get the selected game version
   */
  public void selectRules(GameSelectScreen gameSelectScreen) {
    myGameVersion = gameSelectScreen.getChosenVersion();
    myTab.setText(myGameVersion);
    userSelected = true;
    GameRulesScreen gameRulesScreen = new GameRulesScreen(myTab);
    SplashScreenButtonFactory myFactory = new SplashScreenButtonFactory(this, gameRulesScreen);
    gameRulesScreen.addButtons(myFactory.getButtons());
  }

  /**
   * Takes the users to the game details select screen for them to choose how many players, and the
   * type of die to play with
   *
   * @param gameRulesScreen used to get the selected rules from the previous screen
   */
  public void startCustomMonopoly(GameRulesScreen gameRulesScreen) {
    selectedRules = gameRulesScreen.getSelectedRules();
    showDetails();
  }

  /**
   * Shows the details game screen if user elects to use default rules for the game
   *
   * @param gameSelectScreen used to get the selected game version
   */
  public void startMonopoly(GameSelectScreen gameSelectScreen) {
    myGameVersion = gameSelectScreen.getChosenVersion();
    myTab.setText(myGameVersion);
    showDetails();
  }

  private void showDetails() {
    GameDetailsScreen gameDetailsScreen = new GameDetailsScreen(myTab);
    SplashScreenButtonFactory myFactory = new SplashScreenButtonFactory(this, gameDetailsScreen);
    gameDetailsScreen.addButtons(myFactory.getButtons());
  }

  /**
   * Starts cycle of adding new players to the game until the list is equal to the size of the
   * number of players selected in the details screen
   *
   * @param gameRulesScreen to get the number of players for the game
   */
  public void startCycle(GameDetailsScreen gameRulesScreen) {
    playerCount = gameRulesScreen.getNumberOfPlayers();
    String numberOfSides = gameRulesScreen.getNumberOfSides();
    String numberOfDie = gameRulesScreen.getNumberOfDie();
    createDiceArray(numberOfSides, numberOfDie);
    startingPrice = gameRulesScreen.getStartingPrice();
    chosenIcons.clear();
    playerProperties.clear();
    playerCycle();
  }

  private void playerCycle() {
    PlayerSelectScreen newPlayerScreen = new PlayerSelectScreen(myTab, chosenIcons, myGameVersion);
    SplashScreenButtonFactory myFactory = new SplashScreenButtonFactory(this, newPlayerScreen);
    newPlayerScreen.addButtons(myFactory.getButtons());
  }

  /**
   * Saves an inputted players name and icon to the lists and the list of maps
   *
   * @param newPlayerScreen to get the chosen player name and icon
   */
  public void savePlayer(PlayerSelectScreen newPlayerScreen) {
    String name = newPlayerScreen.getPlayerName();
    if (inputtedNames.contains(name)) {
      ErrorAlert noDuplicatePlayers = new ErrorAlert();
      noDuplicatePlayers.createErrorAlert(ResourceUtil.getResourceValue("DuplicatePlayers"));
    } else {
      String icon = newPlayerScreen.getPlayerIcon();
      addPlayerMap(name, icon);
      chosenIcons.add(icon);
      inputtedNames.add(name);
      if (playerProperties.size() == playerCount) {
        myGameLoader.startGame(chosenDieInformation, playerProperties, userSelected);
      } else {
        playerCycle();
      }
    }
  }

  private void addPlayerMap(String name, String icon) {
    Map<String, String> playerMap = new HashMap<>();
    playerMap.put("name", name);
    playerMap.put("type", "Monopoly");
    playerMap.put("cash", Integer.toString(startingPrice));
    playerMap.put("icon", icon);
    playerProperties.add(playerMap);
  }

  private void createDiceArray(String diceSides, String dieNumber) {
    chosenDieInformation = new String[]{NORMAL, diceSides, dieNumber};
  }

  /**
   * @return the Strings representing the game rules for the game to be used by the factory
   */
  public List<String> getSelectedRules() {
    return Collections.unmodifiableList(selectedRules);
  }

}
