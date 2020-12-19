package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.controller.DataVersionReader;
import ooga.controller.readers.dataversionreaders.DataVersionReaderInternal;
import ooga.controller.util.ArrayUtil;
import ooga.controller.util.ResourceUtil;
import ooga.model.cardevents.action.LoseRandomPropertyEvent;
import ooga.model.cardevents.movement.AdvanceToNearestUtility;
import ooga.model.cardevents.movement.BaseMonopolyMoveToTileCardEvent;
import ooga.model.cardevents.action.BaseMonopolyPaymentEvent;
import ooga.model.cardevents.action.BaseMonopolyTaxCardEvent;
import ooga.controller.factory.TileFactory;
import ooga.model.cardevents.action.GetOutOfJailFreeCardEvent;
import ooga.model.monopoly.MonopolyModel;
import ooga.model.monopoly.MonopolyPlayer;
import ooga.model.randomizables.NormalDice;
import ooga.view.alerts.MonopolyGameAlert;
import org.junit.jupiter.api.Test;
import util.DukeTest;

public class CardEventTest extends DukeTest {

  private MonopolyModel myModel;
  private Player myPlayer;
  private Button buttonTest;

  @Override
  public void start(final Stage stage) {
    ResourceUtil.setLanguage("English");
    DataVersionReader newReader = new DataVersionReaderInternal();
    TileFactory myFactory;
    try {
      newReader.createNewGameVerison("original");
      myFactory = new TileFactory(newReader.getTileMaps());
    } catch (Exception e) {
      myFactory = new TileFactory(new ArrayList<>());
      System.out.println("If you're reading this it's too late");
    }
    List<String> playerNames = Arrays.asList("P1", "P2", "P3", "P4");
    NormalDice myDie = new NormalDice(6, 1);
    List<Player> inputtedPlayerList = new ArrayList<>();
    for(String player : playerNames){
      inputtedPlayerList.add(new MonopolyPlayer(player, myFactory.getMyTiles().get(0), 250));
    }
    myModel = new MonopolyModel(myFactory.getMyTiles(), inputtedPlayerList, myDie, new ArrayList<Card>(),new MonopolyGameAlert());
    myPlayer = myModel.getPlayer("P1");
  }

  @Test
  public void testBaseMonopolyTaxCardEventBasic(){
    CardEvent testingEvent = new BaseMonopolyTaxCardEvent(new int[]{200}, new MonopolyGameAlert());
    javafxRun(() -> testingEvent.doCardAction(myModel, myPlayer,"test"));
    clickOn(lookup("OK").queryButton());
    assertEquals(50, myPlayer.getFunds());
  }

  @Test
  public void testBaseMonopolyPaymentCardEventBasic(){
    CardEvent testingEvent = new BaseMonopolyPaymentEvent(new int[]{200}, new MonopolyGameAlert());
    javafxRun(() -> testingEvent.doCardAction(myModel, myPlayer,"test"));
    clickOn(lookup("OK").queryButton());
    assertEquals(450, myPlayer.getFunds());
  }

  @Test
  public void testBaseMonopolyMoveToTileBasic(){
    CardEvent testingEvent = new BaseMonopolyMoveToTileCardEvent("Boardwalk", new int[]{}, new MonopolyGameAlert());
    javafxRun(() -> testingEvent.doCardAction(myModel, myPlayer,"test"));
    clickOn(lookup("OK").queryButton());
    assertEquals(myModel.getTile(39), myPlayer.getCurrentTile());
  }

  @Test
  public void testAdvanceToNearestUtility(){
    CardEvent testingEvent = new AdvanceToNearestUtility("Utility", new int[]{10}, new MonopolyGameAlert());
    javafxRun(() -> testingEvent.doCardAction(myModel, myPlayer,"test"));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("Yes").queryButton());
    assertEquals(myModel.getTile(12), myPlayer.getCurrentTile());
  }

  @Test
  public void testAdvanceToNearestUtilityDifferentStartingLocation(){
    myModel.payPlayer(myPlayer, myModel.getBank(), 1000);
    CardEvent testingEvent = new AdvanceToNearestUtility("Utility", new int[]{10}, new MonopolyGameAlert());
    myModel.jumpPlayerToTile(myPlayer,myModel.getTile(27));
    javafxRun(() -> testingEvent.doCardAction(myModel, myPlayer,"test"));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("Yes").queryButton());
    assertEquals(myModel.getTile(28), myPlayer.getCurrentTile());
  }

  @Test
  public void testAdvanceToNearestUtilityPayBasedOffRoll(){
    myModel.payPlayer(myPlayer, myModel.getBank(), 1000);
    myModel.payPlayer(myModel.getPlayer("P2"), myModel.getBank(), 1000);
    myModel.jumpPlayerToTile(myModel.getPlayer("P2"),myModel.getTile(12));
    javafxRun(() -> myModel.getTile(12).callMyEvent(myModel.getPlayer("P2"),myModel));
    clickOn(lookup("Yes").queryButton());
    CardEvent testingEvent = new AdvanceToNearestUtility("Utility", new int[]{10}, new MonopolyGameAlert());
    javafxRun(() -> testingEvent.doCardAction(myModel, myPlayer,"test"));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("OK").queryButton());
    int lastRoll = ArrayUtil.sum(myModel.getRandomizable().getLastRoll());
    assertEquals(1250 - lastRoll*4*10,myPlayer.getFunds());
  }

  @Test
  public void testGetOutOfJailFreeCard(){
    CardEvent testingEvent = new GetOutOfJailFreeCardEvent( new int[]{}, new MonopolyGameAlert());
    assertTrue(!myPlayer.getOwnables().contains(testingEvent));
    javafxRun(() -> testingEvent.doCardAction(myModel, myPlayer,"test"));
    clickOn(lookup("OK").queryButton());
    assertTrue(myPlayer.getOwnables().contains(testingEvent));
  }

  @Test
  public void testLoseRandomPropertyCard(){
    CardEvent testingEvent = new LoseRandomPropertyEvent( new int[]{}, new MonopolyGameAlert());
    myModel.exchangeOwnables(myPlayer,myModel.getBank(),Arrays.asList((Ownable)myModel.getTile(1)));
    assertTrue(myPlayer.getOwnables().contains(myModel.getTile(1)));
    javafxRun(() -> testingEvent.doCardAction(myModel, myPlayer,"test"));
    clickOn(lookup("OK").queryButton());
    assertTrue(!myPlayer.getOwnables().contains(myModel.getTile(1)));
  }

  @Test
  public void testLoseRandomPropertyCardNothingToLose(){
    CardEvent testingEvent = new LoseRandomPropertyEvent( new int[]{}, new MonopolyGameAlert());
    myModel.exchangeOwnables(myPlayer,myModel.getBank(),Arrays.asList((Ownable)myModel.getTile(1)));
    assertTrue(myPlayer.getOwnables().contains(myModel.getTile(1)));
    javafxRun(() -> testingEvent.doCardAction(myModel, myPlayer,"test"));
    clickOn(lookup("OK").queryButton());
    assertTrue(!myPlayer.getOwnables().contains(myModel.getTile(1)));
  }

  @Test
  public void testLoseRandomPropertyCardLoseGetOutOfJailCard(){
    CardEvent getOutOfJail = new GetOutOfJailFreeCardEvent( new int[]{}, new MonopolyGameAlert());
    javafxRun(() -> getOutOfJail.doCardAction(myModel, myPlayer,"test"));
    clickOn(lookup("OK").queryButton());
    CardEvent testingEvent = new LoseRandomPropertyEvent( new int[]{}, new MonopolyGameAlert());
    javafxRun(() -> testingEvent.doCardAction(myModel, myPlayer,"test"));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("OK").queryButton());
    assertTrue(!myPlayer.getOwnables().contains(getOutOfJail));
  }

}
