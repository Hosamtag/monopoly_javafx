package ooga.controller.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidTurnException;
import ooga.model.Player;
import ooga.model.Turn;

public class TurnFactory {

  /**
   * Creates a turn object that is used by players to determine how to take their turn.
   *
   * @param turnType the name of the type of turn to use.
   * @param player the player that this turn is assigned to.
   * @return the desired turn object.
   */
  public static Turn createTurn(String turnType, Player player) {
    try {
      turnType = turnType.substring(0, 1) + turnType.substring(1).toLowerCase();
      Class<?>[] turnConstructorParams = {Player.class};
      Class<?> turnClass = Class.forName("ooga.model.turns." + turnType + "Turn");
      Constructor<?> constructor = turnClass.getConstructor(turnConstructorParams);
      Object[] turnConstructorObjects = {player};
      return (Turn) constructor.newInstance(turnConstructorObjects);
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
      throw new InvalidTurnException(
          String.format(ResourceUtil.getResourceValue("TurnException"), turnType));
    }
  }

}
