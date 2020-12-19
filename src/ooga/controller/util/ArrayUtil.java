package ooga.controller.util;

import java.util.List;

public final class ArrayUtil {

  private ArrayUtil() {
  }

  /**
   * Converts a string to an array of integers.
   * Assumes the string is a CSV of integers without spaces.
   *
   * @param inputString the string to convert
   * @return the desired array of integers
   */
  public static int[] stringToInt(String inputString) {
    String[] splitInput = inputString.split(",");
    int[] newIntArray = new int[splitInput.length];
    for (int i = 0; i < newIntArray.length; i++) {
      newIntArray[i] = Integer.parseInt(splitInput[i]);
    }
    return newIntArray;
  }

  /**
   * Sums a list of integers.
   * Surprised Java didn't have this built in???
   *
   * @param values the list of values to sum
   * @return the total sum of all the values
   */
  public static int sum(List<Integer> values) {
    return values.stream().mapToInt(Integer::intValue).sum();
  }

  /**
   * Checks if a list contains all the same number.
   * Used for checking if a player rolled tuples on the die, but can be used elsewhere.
   *
   * @param values the values on each die.
   * @return whether or not the values are all the same.
   */
  public static boolean checkIfTuples(List<Integer> values) {
    for (Integer eachValue : values) {
      if (eachValue != (values.get(0)) || values.size() == 1) {
        return false;
      }
    }
    return values.get(0) != 0;
  }
}
