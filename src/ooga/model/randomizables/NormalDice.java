package ooga.model.randomizables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import ooga.controller.Observer;
import ooga.model.Randomizable;

public class NormalDice implements Randomizable {

  private Random randomIntGenerator;
  private final int MAX_DIE_VALUE;
  private final List<Observer> observers;
  private List<Integer> lastRoll;
  private final int NUMBER_OF_DICE;
  private static final int MIN_DIE_VALUE = 1;

  /**
   * Instantiates a set of n-sided die.  Die have values ranging from min to max-1 (EXCLUSIVE MAX)
   *
   * @param numberOfSides the number of sides of the created die
   * @param numberOfDice the number of dice to include in this group
   */
  public NormalDice(int numberOfSides, int numberOfDice) {
    MAX_DIE_VALUE = numberOfSides;
    observers = new ArrayList<>();
    NUMBER_OF_DICE = numberOfDice;
    randomIntGenerator = new Random();
  }

  /**
   * Instantiates a set of n-sided as above but uses a specified Random seed for testing.
   *
   * @param numberOfSides the number of sides of the created die
   * @param numberOfDice the number of dice to include in this group
   * @param seed the seed of the Random object used. Mostly for testing purposes
   */
  public NormalDice(int numberOfSides, int numberOfDice, int seed) {
    this(numberOfSides, numberOfDice);
    randomIntGenerator = new Random(seed);
  }

  /**
   * Gets the value of the next roll of the dice.
   *
   * @return A list of integers indicating the roll values of each individual die in the group.
   */
  public List<Integer> getNextRoll() {
    List<Integer> listOfNextRolls = new ArrayList<>();
    for (int dieIndex = 0; dieIndex < NUMBER_OF_DICE; dieIndex++) {
      listOfNextRolls
          .add(randomIntGenerator.nextInt(MAX_DIE_VALUE) + MIN_DIE_VALUE);
    }
    lastRoll = listOfNextRolls;
    notifyObservers();
    return listOfNextRolls;
  }

  /**
   * Gets the value of the previous roll of the dice.
   *
   * @return A list of integers indicating the last roll values of each individual die in the group
   */
  public List<Integer> getLastRoll() {
    if (lastRoll != null) {
      return lastRoll;
    }
    return new ArrayList<>(Collections.nCopies(NUMBER_OF_DICE, 0));
  }

  /**
   * @return The number of sides on each dice in the group.
   */
  public int getNumberOfSides() {
    return MAX_DIE_VALUE;
  }

  /**
   * @return The number of dice in the group.
   */
  public int getNumberOfDie() {
    return NUMBER_OF_DICE;
  }

  /**
   * Notifies observers of the dice. Used to tell when the dice has been rolled.
   */
  @Override
  public void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }

  /**
   * Adds an observer to the list of objects listening to the NormalDice object.
   *
   * @param o A new observer object.
   */
  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }
}