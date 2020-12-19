package ooga.controller.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import ooga.controller.util.ResourceUtil;
import ooga.model.RuleEvent;
import ooga.exceptions.InvalidRuleException;
import ooga.model.GameModel;
import ooga.view.GameAlert;
import ooga.view.alerts.MonopolyGameAlert;

public class RuleFactory {

  final List<RuleEvent> myRules;
  final GameModel myModel;

  /**
   * Creates a RuleFactory object that instantiates the Rules for a game.
   *
   * @param ruleTypes the list of names for the desired rules.
   * @param model the model used for this game.
   */
  public RuleFactory(List<String> ruleTypes, GameModel model) {
    myModel = model;
    myRules = new ArrayList<>();
    createRules(ruleTypes);
  }

  private void createRules(List<String> ruleStrings) {
    for (String eachRule : ruleStrings) {
      String path = "ooga.model.ruleevents." + eachRule + "Rule";
      try {
        Class<?> ruleClass = Class.forName(path);
        Class<?>[] paramTypes = {GameModel.class, GameAlert.class};
        Constructor<?> constructor = ruleClass.getConstructor(paramTypes);
        Object[] params = {myModel, new MonopolyGameAlert()};
        myRules.add((RuleEvent) constructor.newInstance(params));
      } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
        throw new InvalidRuleException(
            String.format(ResourceUtil.getResourceValue("RuleException"), eachRule));
      }
    }
  }

  /**
   * Gets the list of Rules used for a game.
   *
   * @return the desired list of rules.
   */
  public List<RuleEvent> getRules() {
    return myRules;
  }

}
