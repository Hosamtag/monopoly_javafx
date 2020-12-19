package ooga.controller.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import ooga.controller.ClickHandler;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidObjectErrorException;
import ooga.model.GameModel;
import ooga.view.GameViewable;

/**
 * Factory to create the ClickHandler associated with the game
 *
 * @author Hosam Tageldin
 */
public class ClickHandlerFactory {

  private ClickHandler clickHandler;

  /**
   * Constructor to create the click handler
   * @param clickHandlerType the type of click handler
   * @param myMonopolyModel the model interface for the current game
   * @param myGameView the view inteface of the current game
   */
  public ClickHandlerFactory(String clickHandlerType, GameModel myMonopolyModel,
      GameViewable myGameView) {
    createClickHandler(clickHandlerType, myMonopolyModel, myGameView);
  }

  private void createClickHandler(String clickHandlerType, GameModel myMonopolyModel,
      GameViewable myGameView) {
    try {
      Class<?>[] turnConstructorParams = {GameModel.class, GameViewable.class};
      Class<?> turnClass = Class
          .forName("ooga.controller.clickhandlers." + clickHandlerType + "ClickHandler");
      Constructor<?> constructor = turnClass.getConstructor(turnConstructorParams);
      Object[] turnConstructorObjects = {myMonopolyModel, myGameView};
      clickHandler = (ClickHandler) constructor.newInstance(turnConstructorObjects);
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
      throw new InvalidObjectErrorException(
          String.format(ResourceUtil.getResourceValue("ClickHandlerException"), clickHandlerType));
    }
  }

  /**
   * @return the ClickHandler created by the factory
   */
  public ClickHandler getClickHandler() {
    return clickHandler;
  }
}
