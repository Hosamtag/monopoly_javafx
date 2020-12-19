package ooga.model;

public interface StatusEffect {

  /**
   * Fetches the list of status effects available.
   *
   * @return all of the status effects that are currently available.
   */
  @Deprecated
  StatusEffect[] effectTypes();

}
