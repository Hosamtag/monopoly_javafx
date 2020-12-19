package ooga.view.dice;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ooga.model.Randomizable;
import ooga.view.DiceView;

/**
 * Creates the DiceView used in the game for N dice with N sides Dice is filled based on images (if
 * less than 6 sides) or text and color if more than 6 sides in order to enable any number of sides
 * for the dice
 *
 * @author Isabella Knox
 */
public class NormalDiceView extends DiceView {

  private final ResourceBundle diceBundle;
  private static final String PATH = "properties/Dice";
  private static final int DICE_SIZE = 70;
  private static final String SLASH = "/";
  private static final String DICES_IMAGE_PATH = "dice/dice";
  private static final String PNG_EXTENSION = ".png";
  private static final int HIGHEST_DICE_IMAGE = 6;
  private static final int STYLESHEET_CSS_POSITION = 4;
  private static final int STYLESHEET_FIRST_INDEX_POSITION = 0;

  /**
   * constructor for dice with only randomizer
   *
   * @param randomizer
   */
  public NormalDiceView(Randomizable randomizer) {
    super(randomizer);
    diceBundle = ResourceBundle.getBundle(PATH);
  }

  /**
   * constructor for dice with stylesheet and randomizer to initialize dice with applied
   * style/color
   *
   * @param randomizer
   * @param styleSheet
   */
  public NormalDiceView(Randomizable randomizer, String styleSheet) {
    super(randomizer);
    diceBundle = ResourceBundle.getBundle(PATH);
    DICE_THEME = styleSheet
        .substring(STYLESHEET_FIRST_INDEX_POSITION, styleSheet.length() - STYLESHEET_CSS_POSITION);
  }

  /**
   * how to fill an individual die in the list of dice
   *
   * @param dice
   * @param roll
   */
  @Override
  public void setDieImage(StackPane dice, int roll) {
    Rectangle die = new Rectangle(DICE_SIZE, DICE_SIZE);
    if (MAX_SIDES <= HIGHEST_DICE_IMAGE) {
      dice.getChildren().clear();
      die.setFill(new ImagePattern(
          new Image(SLASH + DICE_THEME + DICES_IMAGE_PATH + roll + PNG_EXTENSION)));
      dice.getChildren().add(die);
    } else {
      stackTextOnDice(dice, die, roll);
    }
  }

  /**
   * sets all the dice images at the end of the roll with to appropriate sides
   *
   * @param nextRolls
   */
  @Override
  public void setAllDiceImages(List<Integer> nextRolls) {
    for (int i = 0; i < nextRolls.size(); i++) {
      Rectangle dice = new Rectangle(DICE_SIZE, DICE_SIZE);
      if (MAX_SIDES <= HIGHEST_DICE_IMAGE) {
        gameDice.get(i).getChildren().clear();
        dice.setFill(new ImagePattern(
            new Image(SLASH + DICE_THEME + DICES_IMAGE_PATH + nextRolls.get(i) + PNG_EXTENSION)));
        gameDice.get(i).getChildren().add(dice);
      } else {
        stackTextOnDice(gameDice.get(i), dice, nextRolls.get(i));
      }
    }
  }

  private void stackTextOnDice(StackPane pane, Rectangle dice, int roll) {
    Text text = new Text(Integer.toString(roll));
    text.setId("diceValue");
    dice.setId("diceRectangle");
    dice.setFill(Paint.valueOf(diceBundle.getString(DICE_THEME)));
    pane.getChildren().addAll(dice, text);
  }

  /**
   * changes the theme of the die (either color or images used depending on number of sides of the
   * dice)
   *
   * @param theme
   */
  @Override
  public void setDiceColor(String theme) {
    DICE_THEME = theme;
  }
}
