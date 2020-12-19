package ooga.controller.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import ooga.controller.Order;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidTileDataException;

/**
 * Creates the Order class from the String read in from the data file with a specific game
 *
 * @author Hosam Tageldin
 */
public class OrderFactory {

  private Order playerOrder;

  /**
   * Creates an OrderFactory object that determines the order that players take their turns in.
   *
   * @param orderString string containing the type of order to use
   */
  public OrderFactory(String orderString) {
    createOrder(orderString);
  }

  private void createOrder(String orderString) {
    try {
      String str = "ooga.controller.order." + orderString + "Order";
      Class<?> modelClass = Class.forName(str);
      Constructor<?> constructor = modelClass.getConstructor();
      playerOrder = (Order) constructor.newInstance();
    } catch (IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException | NoSuchMethodException e) {
      throw new InvalidTileDataException(
          String.format(ResourceUtil.getResourceValue("OrderException"), orderString));
    }
  }


  /**
   * Gets the order object created by this factory.
   *
   * @return the order object
   */
  public Order getOrder() {
    return playerOrder;
  }

}
