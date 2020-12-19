package ooga.model.monopoly;

import ooga.model.StatusEffect;

public enum MonopolyStatusEffect implements StatusEffect {
  JAILED,
  NORMAL,
  SLOW,
  FAST;

  /**
   * @return all of the status effects that are currently available for the Monopoly game.
   */
  @Deprecated
  public StatusEffect[] effectTypes() {
    return values();
  }
}
