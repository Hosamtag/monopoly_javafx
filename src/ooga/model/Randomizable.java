package ooga.model;

import java.util.List;
import ooga.controller.Observable;

public interface Randomizable extends Observable {

  /**
   * Determines the next random state for this Randomizable.
   *
   * @return the next random state
   */
  List<Integer> getNextRoll();

  /**
   * Finds the current (most recent) random state for this Randomizable.
   *
   * @return the current random state
   */
  List<Integer> getLastRoll();

  /**
   * Gets how many sides the randomizable has.
   *
   * @return the number of sides.
   */
  int getNumberOfSides();

  /**
   * Gets how many Dice are contained in this Randomizable.
   *
   * @return the number of contained dice.
   */
  int getNumberOfDie();
}
