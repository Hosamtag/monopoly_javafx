package ooga.model.tileevents.drawcard;

import ooga.controller.util.ResourceUtil;
import ooga.model.tileevents.DrawCardEvent;
import ooga.view.GameAlert;

public class MonopolyChanceEvent extends DrawCardEvent {

  private static final String CHANCE = "Chance";

  /**
   * Creates a MonopolyChanceEvent object.
   * Used for chance tiles in monopoly.
   *
   * @param alert the interface used to display notifications
   */
  public MonopolyChanceEvent(GameAlert alert) {
    super(alert);
  }

  @Override
  protected String getMyMessage() {
    return ResourceUtil.getResourceValue(CHANCE);
  }
}
