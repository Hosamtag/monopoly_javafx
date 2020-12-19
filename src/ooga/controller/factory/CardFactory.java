package ooga.controller.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.controller.util.ArrayUtil;
import ooga.controller.util.ResourceUtil;
import ooga.model.CardEvent;
import ooga.exceptions.InvalidCardException;
import ooga.model.Card;
import ooga.view.GameAlert;
import ooga.view.alerts.MonopolyGameAlert;

public class CardFactory {

  private final List<Map<String, String>> cardPropertiesMap;
  private final List<Card> myCards;

  /**
   * Creates a Factory filled with cards from the specified map of properties.
   *
   * @param cardProperties map containing all of the properties as keys and their values as values
   */
  public CardFactory(List<Map<String, String>> cardProperties) {
    cardPropertiesMap = cardProperties;
    myCards = new ArrayList<>();
    createCards();
  }

  /**
   * Gets the list of cards created by this factory.
   *
   * @return the cards created
   */
  public List<Card> getMyCards() {
    return myCards;
  }

  private void createCards() {
    for (Map<String, String> cardProperties : cardPropertiesMap) {
      String prompt = cardProperties.get("Prompt");
      try {
        String cardClassName = "ooga.model.monopoly.MonopolyCard";
        Class<?> cardClass = Class.forName(cardClassName);
        CardEvent myEvent = eventCreator(cardProperties);
        Class<?>[] cardConstructorParams = {String.class, CardEvent.class};
        Constructor<?> constructor = cardClass.getConstructor(cardConstructorParams);
        Object[] cardConstructorObjects = {ResourceUtil.getResourceValue(prompt), myEvent};
        myCards.add((Card) constructor.newInstance(cardConstructorObjects));
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException | ClassNotFoundException e) {
        throw new InvalidCardException(
            String.format(ResourceUtil.getResourceValue("CardException"), prompt));
      }
    }
  }

  private CardEvent eventCreator(Map<String, String> cardProperties)
      throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchMethodException {
    String event = cardProperties.get("Event");
    String intParams = cardProperties.get("IntParams");
    String cardEventName = "ooga.model.cardevents." + event;
    Class<?> eventClass = Class.forName(cardEventName);
    try {
      String location = cardProperties.get("Location");
      Class<?>[] eventConstructorParams = {String.class, int[].class, GameAlert.class};
      Constructor<?> constructor = eventClass.getConstructor(eventConstructorParams);
      Object[] eventConstructorObjects = {location, ArrayUtil.stringToInt(intParams),
          new MonopolyGameAlert()};
      return (CardEvent) constructor.newInstance(eventConstructorObjects);
    } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
      Class<?>[] eventConstructorParams = {int[].class, GameAlert.class};
      Constructor<?> constructor = eventClass.getConstructor(eventConstructorParams);
      Object[] eventConstructorObjects = {ArrayUtil.stringToInt(intParams),
          new MonopolyGameAlert()};
      return (CardEvent) constructor.newInstance(eventConstructorObjects);
    }
  }

}
