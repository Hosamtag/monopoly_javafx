package ooga.view;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import ooga.controller.Observer;
import ooga.model.Randomizable;

/**
 * Creates a view of the game dice and creates an animation of rolling die. Dice uses an observer in
 * order to be rolled and set in various ways and events throughout the game (such as tile events
 * that need users to roll again).
 *
 * @author Isabella Knox, Hosam Tageldin
 */
public abstract class DiceView extends AnimationTimer implements Observer {


  protected String DICE_THEME = "monopoly";
  private static final int STARTING_DICE_SIDE = 1;
  private static final long FRAMES_PER_SEC = 50L;
  private static final long INTERVAL = 1000000000L / FRAMES_PER_SEC;

  private long last = 0;
  private static final int MAX_ROLLS = 20;
  private int count = 0;

  protected List<StackPane> gameDice = new ArrayList<>();
  private HBox dieHolder;
  protected Randomizable myRandomizer;
  protected int MAX_SIDES;
  private static final int STYLESHEET_CSS_POSITION = 4;
  private static final int STYLESHEET_FIRST_INDEX_POSITION = 0;


  /**
   * constructor for DiceView
   *
   * @param randomizer
   */
  public DiceView(Randomizable randomizer) {
    this.myRandomizer = randomizer;
    MAX_SIDES = this.myRandomizer.getNumberOfSides();
  }

  /**
   * constructor for DiceView using a stylesheet in order to initialize the Diceview with the
   * correct color
   *
   * @param randomizer
   * @param styleSheet
   */
  public DiceView(Randomizable randomizer, String styleSheet) {
    this.myRandomizer = randomizer;
    MAX_SIDES = this.myRandomizer.getNumberOfSides();
    DICE_THEME = styleSheet
        .substring(STYLESHEET_FIRST_INDEX_POSITION, styleSheet.length() - STYLESHEET_CSS_POSITION);
  }


  /**
   * This method handles the action that occurs when when the method start() is called. Random dice
   * are displayed for 20 rolls. The final image is the actual roll of the player.
   *
   * @param now
   */
  @Override
  public void handle(long now) {
    if (now - last > INTERVAL) {
      int r = 1 + (int) (Math.random() * MAX_SIDES);
      for (int i = 0; i < myRandomizer.getLastRoll().size(); i++) {
        setDieImage(gameDice.get(i), r);
      }
      last = now;
      count++;
      if (count > MAX_ROLLS) {
        stop();
        count = 0;
        setAllDiceImages(myRandomizer.getLastRoll());
      }
    }
  }

  /**
   * This creates the HBox which will hold all the dice.
   *
   * @return
   */
  public HBox createDiceBox() {
    dieHolder = new HBox();
    dieHolder.setId("diceBox");
    resetDiceView();
    return dieHolder;
  }

  /**
   * resets the DiceView to starting dice side
   */
  public void resetDiceView() {
    dieHolder.getChildren().clear();
    gameDice.clear();
    for (int i = 0; i < myRandomizer.getNumberOfDie(); i++) {
      gameDice.add(new StackPane());
      setDieImage(gameDice.get(i), STARTING_DICE_SIDE);
      dieHolder.getChildren().add(gameDice.get(i));
    }
  }

  /**
   * Will set the theme/color of the game die
   *
   * @param theme
   */
  public abstract void setDiceColor(String theme);

  /**
   * Will fill an individual die depending on how this is defined
   *
   * @param dice
   * @param roll
   */
  public abstract void setDieImage(StackPane dice, int roll);

  /**
   * Will fill each die depending on how this method is defined
   *
   * @param nextRolls
   */
  public abstract void setAllDiceImages(List<Integer> nextRolls);

  /**
   * returns the game's dice
   *
   * @return gameDice
   */
  public List<StackPane> getGameDie() {
    return gameDice;
  }

  /**
   * updates the DiceView by starting the animation timer
   */
  @Override
  public void update() {
    this.start();
  }

}
