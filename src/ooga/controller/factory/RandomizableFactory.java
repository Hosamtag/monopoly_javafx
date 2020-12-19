package ooga.controller.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidTileDataException;
import ooga.model.Randomizable;

/**
 * Instantiates the randomizable for the game based off what is read from a properties file for a
 * loaded game or what is selected on the third splash screen for the die properties
 *
 * @author Hosam Tageldin
 */
public class RandomizableFactory {

  private Randomizable myDice;

  /**
   * Creates the RandomizableFactory object that instantiates the Randomizable used for this game.
   *
   * @param dieType       The name of the type of die to use.
   * @param numberOfSides the number of sides the die will have.
   * @param numberOfDie   the number of dice to use.
   */
  public RandomizableFactory(String dieType, int numberOfSides, int numberOfDie) {
    createDie(dieType, numberOfSides, numberOfDie);
  }

  private void createDie(String dieType, int numberOfSides, int numberOfDie) {
    try {
      String str = "ooga.model.randomizables." + dieType + "Dice";
      Class<?> modelClass = Class.forName(str);
      Class<?>[] modelConstructorParams = {int.class, int.class};
      Constructor<?> constructor = modelClass.getConstructor(modelConstructorParams);
      Object[] modelConstructorObjects = {numberOfSides, numberOfDie};
      myDice = (Randomizable) constructor.newInstance(modelConstructorObjects);
    } catch (IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchMethodException e) {
      throw new InvalidTileDataException(
          String.format(ResourceUtil.getResourceValue("RandomizableException"), dieType));
    }
  }

  /**
   * Gets the Randomizable instantiated by this factory.
   *
   * @return the desired randomizable.
   */
  public Randomizable getDice() {
    return myDice;
  }


}
