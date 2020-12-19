package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import ooga.controller.DataVersionReader;
import ooga.controller.readers.dataversionreaders.DataVersionReaderInternal;
import ooga.controller.util.ArrayUtil;
import ooga.controller.util.ResourceUtil;
import ooga.controller.factory.RuleFactory;
import ooga.controller.factory.TileFactory;
import ooga.model.cardevents.action.GetOutOfJailFreeCardEvent;
import ooga.model.monopoly.MonopolyStatusEffect;
import ooga.exceptions.InvalidOperationException;
import ooga.model.monopoly.MonopolyModel;
import ooga.model.monopoly.MonopolyPlayer;
import ooga.model.randomizables.NormalDice;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.model.tileevents.nonownable.FastSpeedEvent;
import ooga.model.tileevents.nonownable.SlowSpeedEvent;
import ooga.model.tileevents.ownable.JuniorMonopolyPropertyTileEvent;
import ooga.model.tileevents.ownable.OwnableDoNothingEvent;
import ooga.model.tiles.JuniorOwnableTile;
import ooga.view.GameAlert;
import ooga.view.alerts.MonopolyGameAlert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import util.DukeTest;

public class MonopolyModelTest extends DukeTest {

  MonopolyModel myModel;
  MonopolyPlayer myPlayer;

  //SEED 5538: 4 2 2 4 1 3 1 4 1 6 5
  @Override
  public void start(Stage stage){
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
    NormalDice myDie = new NormalDice(6, 1, 5538);
    List<Player> inputtedPlayerList = new ArrayList<>();
    for (String player : playerNames) {
      inputtedPlayerList.add(new MonopolyPlayer(player, myFactory.getMyTiles().get(0), 250));
    }
    myModel = new MonopolyModel(myFactory.getMyTiles(), inputtedPlayerList, myDie, new ArrayList<Card>(), new MonopolyGameAlert());
    myPlayer = (MonopolyPlayer) myModel.getPlayer("P1");
  }


  @Test
  void testInstantiatePlayer() {
    assertEquals(250, myPlayer.getFunds());
  }

  @Test
  void testPlayersStartingTiles() {
    assertEquals(myModel.getTile(0), myPlayer.getCurrentTile());
  }

  @Test
  void testPlayerUpdateTile() {
    myPlayer.updateCurrentTile(myModel.getTile(4));
    assertEquals(myModel.getTile(4), myPlayer.getCurrentTile());
  }


  @Test
  void testInstantiateBank() {
    assertEquals(Integer.MAX_VALUE, (myModel.getBank()).getFunds());
  }

  @Test
  void testPayBankPlayerBalanceUpdate() {
    myModel.payPlayer(myModel.getBank(), myPlayer, 100);
    assertEquals(150, myPlayer.getFunds());
  }

  @Test
  void testPayBankBankBalanceStaysSame() {
    myModel.payPlayer(myModel.getBank(), myPlayer, 100);
    assertEquals(Integer.MAX_VALUE, (myModel.getBank()).getFunds());
  }

  @Test
  void testPayNegativeAmount() {
    myModel.payPlayer(myModel.getBank(), myPlayer, -100);
    assertEquals(350, myPlayer.getFunds());
  }

  @Test
  void testDoTurnMovePlayer() {
    myModel.payPlayer(myPlayer,myModel.getBank(),1000);
    myModel.jumpPlayerToTile(myPlayer,myModel.getTile(10));
    assertEquals(myModel.getTile(10), myPlayer.getCurrentTile());
    javafxRun(() ->myModel.doTurn(myPlayer));
    clickOn(lookup("Yes").queryButton());
    List<Integer> lastTurnRoll = myModel.getDie().getLastRoll();
    assertEquals(myModel.getTile(lastTurnRoll.get(0) + 10), myPlayer.getCurrentTile());
  }

  @Test
  void testPlayerReceivesOneOwnableInExchange() {
    MonopolyPlayer myBank = (MonopolyPlayer) myModel.getBank();
    Ownable tradedTile = myBank.getOwnables().get(2);
    List<Ownable> tilesToTrade = new ArrayList<>();
    tilesToTrade.add(tradedTile);
    myModel.exchangeOwnables(myPlayer, myBank, tilesToTrade);
    assertEquals(tradedTile.getName(), myPlayer.getOwnables().get(0).getName());
  }

  @Test
  void testSenderLosesOneOwnableInExchange() {
    MonopolyPlayer myBank = (MonopolyPlayer) myModel.getBank();
    Ownable tradedTile = myBank.getOwnables().get(2);
    List<Ownable> tilesToTrade = new ArrayList<>();
    tilesToTrade.add(tradedTile);
    myModel.exchangeOwnables(myPlayer, myBank, tilesToTrade);
    assertNotEquals(tradedTile.getName(), myBank.getOwnables().get(2));
  }

  @Test
  void testMultipleOwnablesExchanged() {
    MonopolyPlayer myBank = (MonopolyPlayer) myModel.getBank();
    Ownable tradedTile = myBank.getOwnables().get(2);
    Ownable tradedTile2 = myBank.getOwnables().get(3);
    List<Ownable> tilesToTrade = new ArrayList<>();
    tilesToTrade.add(tradedTile);
    tilesToTrade.add(tradedTile2);
    myModel.exchangeOwnables(myPlayer, myBank, tilesToTrade);
    assertEquals(myPlayer.getName(), tradedTile.getOwner().getName());
    assertEquals(tradedTile2.getName(), myPlayer.getOwnables().get(1).getName());
  }

  @Test
  void testNoOwnablesExchanged() {
    myModel.exchangeOwnables(myPlayer, myModel.getBank(), new ArrayList<>());
    assertEquals(0, myPlayer.getOwnables().size());
  }

  @Test
  void testCheckMonopolyBank() {
    assertEquals(true, myModel.checkMonopoly(myModel.getBank(), (OwnableTile) myModel.getTile(1)));
  }

  @Test
  void testCheckMonopolyPlayer() {
    List<Ownable> tilesToSwap = new ArrayList<>();
    tilesToSwap.add((Ownable) myModel.getTile(1));
    tilesToSwap.add((Ownable) myModel.getTile(3));
    myModel.exchangeOwnables(myPlayer, myModel.getBank(), tilesToSwap);
    assertEquals(true, myModel.checkMonopoly(myPlayer, (OwnableTile) myModel.getTile(1)));
  }

  @Test
  void testCheckMonopolyNoneOwned() {
    assertEquals(false, myModel.checkMonopoly(myPlayer, (OwnableTile) myModel.getTile(1)));
  }

  @Test
  void testCheckMonopolySomeOwned() {
    List<Ownable> tilesToSwap = new ArrayList<>();
    tilesToSwap.add((Ownable) myModel.getTile(1));
    myModel.exchangeOwnables(myPlayer, myModel.getBank(), tilesToSwap);
    assertEquals(false, myModel.checkMonopoly(myPlayer, (OwnableTile) myModel.getTile(1)));
    assertEquals(false, myModel.checkMonopoly(myModel.getBank(), (OwnableTile) myModel.getTile(1)));
  }

  @Test
  void testGetUpgradeCost() {
    assertEquals(50,((Upgradable)myModel.getTile(1)).getUpgradeCost());
  }

  @Test
  void testUpgradingTileNoMonopoly() {
    Upgradable myUpgradableTile = (Upgradable)myModel.getTile(1);
    myModel.exchangeOwnables(myPlayer,myModel.getBank(), Arrays.asList((Ownable)myUpgradableTile));
    assertThrows(InvalidOperationException.class, ()->myUpgradableTile.upgrade(myModel));
  }

  @Test
  void testUpgradingTileMonopoly() {
    Upgradable myUpgradableTile = (Upgradable)myModel.getTile(1);
    myModel.exchangeOwnables(myPlayer,myModel.getBank(), Arrays.asList((Ownable)myUpgradableTile));
    myModel.exchangeOwnables(myPlayer,myModel.getBank(), Arrays.asList((Ownable)myModel.getTile(3)));
    myUpgradableTile.upgrade(myModel);
    assertEquals(Buildings.ONE_HOUSE, myUpgradableTile.getCurrentBuilding());
  }

  @Test
  void testTooManyUpgrades() {
    Upgradable myUpgradableTile = (Upgradable)myModel.getTile(1);
    myModel.exchangeOwnables(myPlayer,myModel.getBank(), Arrays.asList((Ownable)myUpgradableTile));
    myModel.exchangeOwnables(myPlayer,myModel.getBank(), Arrays.asList((Ownable)myModel.getTile(3)));
    myUpgradableTile.upgrade(myModel);
    myUpgradableTile.upgrade(myModel);
    myUpgradableTile.upgrade(myModel);
    myUpgradableTile.upgrade(myModel);
    myUpgradableTile.upgrade(myModel);
    myUpgradableTile.upgrade(myModel);
    myUpgradableTile.upgrade(myModel);
    assertEquals(Buildings.HOTEL, myUpgradableTile.getCurrentBuilding());
  }

  @Test
  void testMonopolyJuniorTile(){
    GameAlert testAlert = new MonopolyGameAlert();
    OwnableTile MonopolyJuniorTile = new JuniorOwnableTile("test", new JuniorMonopolyPropertyTileEvent(testAlert),
        new OwnableDoNothingEvent(testAlert), "monopoly", 150,100);
    MonopolyJuniorTile.changeOwner(myModel.getBank());
    myModel.getBank().addOwnable(MonopolyJuniorTile);
    javafxRun(()->MonopolyJuniorTile.callMyEvent(myPlayer,myModel));
    javafxRun(()->MonopolyJuniorTile.callMyPassiveEvent(myPlayer,myModel));
    clickOn(lookup("OK").queryButton());
    assertEquals(100, myPlayer.getFunds());
    assertEquals( myPlayer, MonopolyJuniorTile.getOwner());
    assertEquals( 100, MonopolyJuniorTile.calculateBaseRent());
    assertEquals( 150, MonopolyJuniorTile.getPropertyValue());
    assertEquals( "monopoly", MonopolyJuniorTile.getMonopoly());
  }

  @Test
  void testUpgradeIncreasesRent() {
    int startingRent = ((OwnableTile)myModel.getTile(1)).calculateBaseRent();
    Upgradable myUpgradableTile = (Upgradable)myModel.getTile(1);
    myModel.exchangeOwnables(myPlayer,myModel.getBank(), Arrays.asList((Ownable)myUpgradableTile));
    myModel.exchangeOwnables(myPlayer,myModel.getBank(), Arrays.asList((Ownable)myModel.getTile(3)));
    ((Upgradable)myModel.getTile(1)).upgrade(myModel);
    assertNotEquals(startingRent, ((OwnableTile) myModel.getTile(1)).calculateBaseRent());
  }

  @Test
  void testNextUpgradeCostAfterMaxUpgrade() {
    Upgradable myOwnableTile = (Upgradable)myModel.getTile(1);
    myModel.exchangeOwnables(myPlayer,myModel.getBank(), Arrays.asList((Ownable)myOwnableTile));
    myModel.exchangeOwnables(myPlayer,myModel.getBank(), Arrays.asList((Ownable)myModel.getTile(3)));
    myOwnableTile.upgrade(myModel);
    myOwnableTile.upgrade(myModel);
    myOwnableTile.upgrade(myModel);
    myOwnableTile.upgrade(myModel);
    myOwnableTile.upgrade(myModel);
    myOwnableTile.upgrade(myModel);
    myOwnableTile.upgrade(myModel);
    assertEquals(-1,((Upgradable)myModel.getTile(1)).getUpgradeCost());
  }

  @Test
  void testSetTiles() {
    List<Tile> myTiles = new ArrayList<>();
    for (int i = 0; i<40; i++) {
      myTiles.add(myModel.getTile(i));
    }
    Collections.swap(myTiles, 30, 5);
    myModel.setTiles(myTiles);
    assertEquals("Go to Jail", myModel.getTile(5).getName());
    assertEquals("Reading Railroad", myModel.getTile(30).getName());
  }

  @Test
  void testSetTilesWithPlayer() {
    List<Tile> myTiles = new ArrayList<>();
    for (int i = 0; i<40; i++) {
      myTiles.add(myModel.getTile(i));
    }
    Collections.swap(myTiles, 0, 5);
    myModel.setTiles(myTiles);
    assertEquals(myModel.getTile(5), myPlayer.getCurrentTile());
  }

  @Test
  void testRuleBankruptcy() {
    RuleFactory myRuleFactory = new RuleFactory(Arrays.asList("BankruptcyGoesToBank"), myModel);
    List<RuleEvent> myRules = myRuleFactory.getRules();
    myPlayer.updateFunds(myPlayer.getFunds() - 10000000);
    myRules.get(0).doRule(myPlayer);
    assertEquals(3, myModel.getPlayers().size());
  }

  @Test
  void testBankruptcyGoesToBank() {
    RuleFactory myRuleFactory = new RuleFactory(Arrays.asList("BankruptcyGoesToBank"), myModel);
    List<RuleEvent> myRules = myRuleFactory.getRules();
    myModel.exchangeOwnables(myPlayer, myModel.getBank(), Arrays.asList((Ownable)myModel.getTile(1)));
    assertEquals(myModel.getTiles().get(1), myPlayer.getOwnables().get(0));
    myPlayer.updateFunds(myPlayer.getFunds() - 10000000);
    myRules.get(0).doRule(myPlayer);
    assertEquals(myModel.getBank(), ((Ownable) myModel.getTile(1)).getOwner());
  }

  @Test
  void testRuleBankruptcyGoesToAuction() {
    RuleFactory myRuleFactory = new RuleFactory(Arrays.asList("BankruptcyGoesToAuction"), myModel);
    List<RuleEvent> myRules = myRuleFactory.getRules();
    myModel.exchangeOwnables(myPlayer, myModel.getBank(), Arrays.asList((Ownable)myModel.getTile(1)));
    myPlayer.updateFunds(myPlayer.getFunds() - 10000000);
    myModel.getPlayer("P2").updateFunds(1000000000);
    javafxRun(() -> myRules.get(0).doRule(myPlayer));
    clickOn(lookup("OK").queryButton());
    ComboBox<String> comboBox = lookup("#comboBox").queryComboBox();
    select(comboBox, "P2");
    clickOn(lookup("OK").queryButton());
    assertTrue(myModel.getPlayer("P2").getOwnables().contains(myModel.getTile(1)));
  }

  @Test
  void testJailedTurnGetOutOfJailFreeCard(){
    myModel.payPlayer(myPlayer,myModel.getBank(),1000);
    myPlayer.updateStatusEffect(MonopolyStatusEffect.JAILED);
    myModel.jumpPlayerToTile(myPlayer,myModel.getTile(10));
    javafxRun(() -> myModel.doTurn(myPlayer));
    clickOn(lookup("No").queryButton());
    assertEquals(MonopolyStatusEffect.JAILED, myPlayer.getStatusEffects());
    Ownable getOutOfJailCard = new GetOutOfJailFreeCardEvent(new int[]{}, new MonopolyGameAlert());
    myPlayer.addOwnable(getOutOfJailCard);
    javafxRun(() -> myModel.doTurn(myPlayer));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("Yes").queryButton());
    assertEquals(MonopolyStatusEffect.NORMAL, myPlayer.getStatusEffects());
    assertTrue(!myPlayer.getOwnables().contains(getOutOfJailCard));
  }

  @Test
  void testSlowTurn() {
    myModel.getDie().getNextRoll();
    myPlayer.updateStatusEffect(MonopolyStatusEffect.SLOW);
    javafxRun(() ->myModel.doTurn(myPlayer));
    clickOn(lookup("Yes").queryButton());
    assertEquals(myModel.getTile(1), myPlayer.getCurrentTile());
  }

  @Test
  void testFastTurn() {
    myPlayer.updateStatusEffect(MonopolyStatusEffect.FAST);
    javafxRun(() ->myModel.doTurn(myPlayer));
    clickOn(lookup("Yes").queryButton());
    assertEquals(myModel.getTile(8), myPlayer.getCurrentTile());
  }

  @Test
  void testFastTurnPassGo() {
    myModel.getDie().getNextRoll();
    myPlayer.updateStatusEffect(MonopolyStatusEffect.FAST);
    myModel.jumpPlayerToTile(myPlayer, myModel.getTile(39));
    javafxRun(()->myModel.doTurn(myPlayer));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("Yes").queryButton());
    assertEquals(450-60, myPlayer.getFunds());
  }

  @Test
  void testSlowTurnPassGo() {
    myPlayer.updateStatusEffect(MonopolyStatusEffect.SLOW);
    myModel.jumpPlayerToTile(myPlayer, myModel.getTile(39));
    javafxRun(()->myModel.doTurn(myPlayer));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("Yes").queryButton());
    assertEquals(450-60, myPlayer.getFunds());
  }

  @Test
  void testSlowTurnWhenRoll1() {
    myModel.getDie().getNextRoll();
    myModel.getDie().getNextRoll();
    myModel.getDie().getNextRoll();
    myModel.getDie().getNextRoll();
    myPlayer.updateStatusEffect(MonopolyStatusEffect.SLOW);
    javafxRun(()->myModel.doTurn(myPlayer));
    clickOn(lookup("Yes").queryButton());
    assertEquals(myModel.getTile(1), myPlayer.getCurrentTile());
  }

  @Test
  void testPayBailReleasesPlayer() {
    myModel.jumpPlayerToTile(myPlayer, myModel.getTile(30));
    javafxRun(()->myModel.callPlayerTileEvent(myPlayer));
    javafxRun(()->clickOn(lookup("OK").queryButton()));
    javafxRun(()->myModel.doTurn(myPlayer));
    javafxRun(()->clickOn(lookup("Yes").queryButton()));
    javafxRun(()->clickOn(lookup("Yes").queryButton()));
    assertEquals(MonopolyStatusEffect.NORMAL, myPlayer.getStatusEffects());
    assertEquals(250-50-160, myPlayer.getFunds());
  }

  @Test
  void testEndGameOnFirstBankruptcy() {
    RuleFactory myRuleFactory = new RuleFactory(Arrays.asList("BankruptcyGoesToBank", "EndGameOnFirstBankruptcy"), myModel);
    List<RuleEvent> myRules = myRuleFactory.getRules();
    myPlayer.updateFunds(myPlayer.getFunds() - 10000000);
    myRules.get(0).doRule(myPlayer);
    myRules.get(1).doRule(myPlayer);
    assertTrue(myModel.getPlayer("P2").checkIfWinner());
  }

  @Test
  void testLastManStandingWins() {
    RuleFactory myRuleFactory = new RuleFactory(Arrays.asList("BankruptcyGoesToBank", "LastManStandingWin"), myModel);
    List<RuleEvent> myRules = myRuleFactory.getRules();
    myPlayer.updateFunds(-1000);
    myModel.getPlayer("P2").updateFunds(-1000);
    myModel.getPlayer("P3").updateFunds(-1000);
    myRules.get(0).doRule(myPlayer);
    myRules.get(1).doRule(myPlayer);
    assertTrue(myModel.getPlayer("P4").checkIfWinner());
  }

  @Test
  void testSpeedBasedOnPositionFastOnEven() {
    RuleFactory myRuleFactory = new RuleFactory(Arrays.asList("SpeedBasedOnPosition"), myModel);
    List<RuleEvent> myRules = myRuleFactory.getRules();
    myRules.get(0).doRule(myPlayer);
    javafxRun(() -> myModel.doTurn(myPlayer));
    clickOn(lookup("Yes").queryButton());
    assertEquals(myModel.getTile(8), myPlayer.getCurrentTile());
  }

  @Test
  void testSpeedBasedOnPositionSlowOnOdd() {
    myModel.jumpPlayerToTile(myPlayer, myModel.getTile(1));
    RuleFactory myRuleFactory = new RuleFactory(Arrays.asList("SpeedBasedOnPosition"), myModel);
    List<RuleEvent> myRules = myRuleFactory.getRules();
    myRules.get(0).doRule(myPlayer);
    javafxRun(() -> myModel.doTurn(myPlayer));
    clickOn(lookup("Yes").queryButton());
    assertEquals(myModel.getTile(3), myPlayer.getCurrentTile());
  }

  @Test
  void testEndGameOnHotel() {
    RuleFactory myRuleFactory = new RuleFactory(Arrays.asList("EndGameOnHotel"), myModel);
    List<RuleEvent> myRules = myRuleFactory.getRules();
    myModel.exchangeOwnables(myPlayer, myModel.getBank(), Arrays.asList((Ownable)myModel.getTile(1)));
    myModel.exchangeOwnables(myPlayer, myModel.getBank(), Arrays.asList((Ownable)myModel.getTile(3)));
    Upgradable property = (Upgradable)(myPlayer.getOwnables().get(0));
    property.upgrade(myModel);
    property.upgrade(myModel);
    property.upgrade(myModel);
    property.upgrade(myModel);
    property.upgrade(myModel);
    myRules.get(0).doRule(myPlayer);
    assertTrue(myPlayer.checkIfWinner());
  }

  @Test
  void testFirstToAmountWins() {
    RuleFactory myRuleFactory = new RuleFactory(Arrays.asList("FirstToAmountWins"), myModel);
    List<RuleEvent> myRules = myRuleFactory.getRules();
    myPlayer.updateFunds(6000);
    myRules.get(0).doRule(myPlayer);
    assertTrue(myPlayer.checkIfWinner());
  }

  @Test
  void testFirstToMonopolyWins() {
    RuleFactory myRuleFactory = new RuleFactory(Arrays.asList("EndGameOnMonopoly"), myModel);
    List<RuleEvent> myRules = myRuleFactory.getRules();
    myRules.get(0).doRule(myPlayer);
    assertTrue(!myPlayer.checkIfWinner());
    myModel.exchangeOwnables(myPlayer,myModel.getBank(),Arrays.asList((Ownable)myModel.getTile(1)));
    myModel.exchangeOwnables(myPlayer,myModel.getBank(),Arrays.asList((Ownable)myModel.getTile(3)));
    myRules.get(0).doRule(myPlayer);
    assertTrue(myPlayer.checkIfWinner());
  }


  @Test
  void testFastTile(){
    NonOwnableTileEvent fastTile = new FastSpeedEvent(new MonopolyGameAlert());
    javafxRun(()->fastTile.callEvent(myPlayer, 0, myModel));
    assertEquals(MonopolyStatusEffect.FAST, myPlayer.getStatusEffects());
  }

  @Test
  void testSlowTile() {
    NonOwnableTileEvent slowTile = new SlowSpeedEvent(new MonopolyGameAlert());
    javafxRun(()->slowTile.callEvent(myPlayer, 0, myModel));
    assertEquals(MonopolyStatusEffect.SLOW, myPlayer.getStatusEffects());
  }

  //TODO: MAKE MULTIPLE DIE FOR THIS TEST

  /*
  @Test
  void testRerollOnTuples() {
    RuleFactory myRuleFactory = new RuleFactory(Arrays.asList("RerollOnTuples"), myModel);
    List<RuleEvent>myRules = myRuleFactory.getRules();
    while(!ArrayUtil.checkIfTuples(myModel.getRandomizable().getLastRoll())) {
      myModel.getRandomizable().getNextRoll();
    }
    Tile currentPosition = myPlayer.getCurrentTile();
    myRules.get(0).doRule(myPlayer);
    assertNotEquals(currentPosition, myPlayer.getCurrentTile());
  }
  */
}