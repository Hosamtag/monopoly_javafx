package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import ooga.model.randomizables.NormalDice;
import org.junit.jupiter.api.Test;

public class RandomizableTest {

  @Test
  void testSingleDieWithinBounds() {
    Randomizable myDie = new NormalDice(1,1);
    assertEquals(1, myDie.getNextRoll().get(0));
  }

  @Test
  void testMultipleDie() {
    Randomizable myDice = new NormalDice(2,10);
    assertEquals(10, myDice.getNextRoll().size());
  }

  @Test
  void testCorrectLastRoll() {
    Randomizable myDie = new NormalDice(1,1);
    myDie.getNextRoll();
    assertEquals(1, myDie.getLastRoll().get(0));
  }

  @Test
  void testLastRollIfNotRolledYet() {
    Randomizable myDie = new NormalDice(2,1);
    assertEquals(0, myDie.getLastRoll().get(0));
  }

  @Test
  void testInvalidDataNumberOfDice() {
    Randomizable myDie = new NormalDice(1,0);
    assertEquals(new ArrayList<Integer>(), myDie.getNextRoll());
  }

  @Test
  void testSeedWorks() {
    NormalDice myDie = new NormalDice(6, 1, 5538);
    assertEquals(4, myDie.getNextRoll().get(0));
  }
}
