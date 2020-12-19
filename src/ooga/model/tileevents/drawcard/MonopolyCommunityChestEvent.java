package ooga.model.tileevents.drawcard;

import ooga.controller.util.ResourceUtil;
import ooga.model.tileevents.DrawCardEvent;
import ooga.view.GameAlert;

public class MonopolyCommunityChestEvent extends DrawCardEvent {

  private static final String COMMUNITY_CHEST = "CommunityChest";

  /**
   * Creates a MonopolyCommunityChestEvent object.
   * Used for community chest tiles in monopoly.
   *
   * @param alert
   */
  public MonopolyCommunityChestEvent(GameAlert alert) {
    super(alert);
  }

  @Override
  protected String getMyMessage() {
    return ResourceUtil.getResourceValue(COMMUNITY_CHEST);
  }
}
