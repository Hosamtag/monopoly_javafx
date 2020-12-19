package ooga.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import ooga.controller.DataVersionReader;
import ooga.controller.readers.dataversionreaders.DataVersionReaderInternal;
import ooga.controller.util.ArrayUtil;
import ooga.controller.util.ResourceUtil;
import ooga.model.cardevents.action.GetOutOfJailFreeCardEvent;
import ooga.model.monopoly.MonopolyCard;
import ooga.model.tileevents.NonOwnableTileEvent;
import ooga.model.tileevents.drawcard.MonopolyChanceEvent;
import ooga.model.tileevents.drawcard.MonopolyCommunityChestEvent;
import ooga.model.tileevents.nonownable.BankruptcyTileEvent;
import ooga.model.tileevents.nonownable.MonopolyGoToJailEvent;
import ooga.model.tileevents.nonownable.WinTileEvent;
import ooga.model.tileevents.ownable.MoswapolyPropertyTileEvent;
import ooga.model.tileevents.ownable.basemonopoly.MonopolyPropertyEvent;
import ooga.model.tileevents.ownable.basemonopoly.MonopolyRailroadEvent;
import ooga.model.tileevents.nonownable.MonopolyTaxEvent;
import ooga.model.tileevents.ownable.basemonopoly.MonopolyUtilityEvent;
import ooga.model.tileevents.OwnableTileEvent;
import ooga.controller.factory.TileFactory;
import ooga.model.tileevents.ownable.JuniorMonopolyPropertyTileEvent;
import ooga.model.monopoly.MonopolyModel;
import ooga.model.monopoly.MonopolyPlayer;
import ooga.model.monopoly.MonopolyStatusEffect;
import ooga.model.randomizables.NormalDice;
import ooga.view.alerts.MonopolyGameAlert;
import org.junit.jupiter.api.Test;
//import util.DukeApplicationTest;
import util.DukeTest;

class TileEventTest extends DukeTest {

  private MonopolyModel myModel;
  private Player myPlayer;
  private Button buttonTest;

  //SEED 5538: 4 2 2 4 1 3 1 4 1 6 5
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
    NormalDice myDie = new NormalDice(6, 1, 5538);
    List<Player> inputtedPlayerList = new ArrayList<>();
    for(String player : playerNames){
      inputtedPlayerList.add(new MonopolyPlayer(player, myFactory.getMyTiles().get(0), 250));
    }
    List<Card> cardList = new ArrayList<Card>();
    cardList.add((new MonopolyCard("Test", new GetOutOfJailFreeCardEvent(new int[]{}, new MonopolyGameAlert()))));
    myModel = new MonopolyModel(myFactory.getMyTiles(), inputtedPlayerList, myDie, cardList, new MonopolyGameAlert());
    myPlayer = myModel.getPlayer("P1");
  }

  @Test
  public void testLandOnUnownedPurchaseFunds(){
    OwnableTileEvent testingEvent = new MonopolyPropertyEvent(new MonopolyGameAlert());
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(1),myModel));
    buttonTest = lookup("Yes").queryButton();
    clickOn(buttonTest);
    assertEquals(190, myPlayer.getFunds());
  }


  @Test
  public void testLandOnUnownedPurchaseTileOwnerTransfer(){
    OwnableTileEvent testingEvent = new MonopolyPropertyEvent(new MonopolyGameAlert());
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(1),myModel));
    buttonTest = lookup("Yes").queryButton();
    clickOn(buttonTest);
    assertTrue(myPlayer.getOwnables().contains(myModel.getTile(1)));
  }

  @Test
  public void testLandOnUnownedDontPurchase(){
    OwnableTileEvent testingEvent = new MonopolyPropertyEvent(new MonopolyGameAlert());
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(1),myModel));
    buttonTest = lookup("No").queryButton();
    clickOn(buttonTest);
    assertEquals(250, myPlayer.getFunds());
  }

  @Test
  public void testLandOnUnownedAuction(){
    myModel.payPlayer(myModel.getPlayer("P2"), myModel.getBank(), 1000);
    OwnableTileEvent testingEvent = new MonopolyPropertyEvent(new MonopolyGameAlert());
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(1),myModel));
    buttonTest = lookup("No").queryButton();
    clickOn(buttonTest);
    assertEquals(250, myPlayer.getFunds());
    ComboBox<String> comboBox = (ComboBox) lookup("#comboBox").queryComboBox();
    select(comboBox, "P2");
    clickOn(lookup("OK").queryButton());
    assertTrue(myModel.getPlayer("P2").getOwnables().contains(myModel.getTile(1)));
  }


  @Test
  public void testLandOnOwnedPayOwner(){
    OwnableTileEvent testingEvent = new MonopolyPropertyEvent(new MonopolyGameAlert());
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(1),myModel));
    buttonTest = lookup("Yes").queryButton();
    clickOn(buttonTest);
    assertEquals(190, myPlayer.getFunds());
    javafxRun(() -> testingEvent.callEvent(myPlayer, myModel.getPlayer("P2"), (OwnableTile) myModel.getTile(1),myModel));
    assertEquals(248, myModel.getPlayer("P2").getFunds());
  }

  @Test
  public void landOnRailroadTileUnowned(){
    OwnableTileEvent testingEvent = new MonopolyRailroadEvent(new MonopolyGameAlert());
    myModel.payPlayer(myPlayer, myModel.getBank(), 1000);
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(5),myModel));
    buttonTest = lookup("Yes").queryButton();
    clickOn(buttonTest);
    assertTrue(myPlayer.getOwnables().contains(myModel.getTile(5)));
  }

  @Test
  public void landOnRailroadTileOneOwned(){
    OwnableTileEvent testingEvent = new MonopolyRailroadEvent(new MonopolyGameAlert());
    myModel.payPlayer(myPlayer, myModel.getBank(), 1000);
    javafxRun(() ->testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(5),myModel));
    buttonTest = lookup("Yes").queryButton();
    clickOn(buttonTest);
    javafxRun(() -> testingEvent.callEvent(myPlayer, myModel.getPlayer("P2"), (OwnableTile) myModel.getTile(5),myModel));
    assertEquals(200, myModel.getPlayer("P2").getFunds());
  }

  @Test
  public void landOnRailroadTileTwoOwned(){
    OwnableTileEvent testingEvent = new MonopolyRailroadEvent(new MonopolyGameAlert());
    myModel.payPlayer(myPlayer, myModel.getBank(), 1000);
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(5),myModel));
    buttonTest = lookup("Yes").queryButton();
    clickOn(buttonTest);
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(15),myModel));
    buttonTest = lookup("Yes").queryButton();
    clickOn(buttonTest);
    javafxRun(() -> testingEvent.callEvent(myPlayer, myModel.getPlayer("P2"), (OwnableTile) myModel.getTile(15),myModel));
    assertEquals(150, myModel.getPlayer("P2").getFunds());
  }

  @Test
  public void landOnUtilityTileUnowned(){
    OwnableTileEvent testingEvent = new MonopolyUtilityEvent(new MonopolyGameAlert());
    myModel.payPlayer(myPlayer, myModel.getBank(), 1000);
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(12),myModel));
    buttonTest = lookup("Yes").queryButton();
    clickOn(buttonTest);
    assertTrue(myPlayer.getOwnables().contains(myModel.getTile(12)));
  }

  @Test
  public void landOnUtilityTileOwned(){
    OwnableTileEvent testingEvent = new MonopolyUtilityEvent(new MonopolyGameAlert());
    int playerStartingMoney = ((MonopolyPlayer )myModel.getPlayer("P2")).getFunds();
    myModel.payPlayer(myPlayer, myModel.getBank(), 1000);
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(12),myModel));
    clickOn(lookup("Yes").queryButton());
    javafxRun(() -> testingEvent.callEvent(myPlayer, myModel.getPlayer("P2"), (OwnableTile) myModel.getTile(12),myModel));
    clickOn(lookup("OK").queryButton());
    assertTrue((playerStartingMoney - (((MonopolyPlayer )myModel.getPlayer("P2")).getFunds())) % 4 == 0);
  }

  @Test
  public void landOnTaxTile(){
    NonOwnableTileEvent testingEvent = new MonopolyTaxEvent(new MonopolyGameAlert());
    javafxRun(() -> testingEvent.callEvent(myPlayer, 200, myModel));
    assertEquals(50, myPlayer.getFunds());
  }

  @Test
  public void landOnGoToJailTilePlayerStatus(){
    NonOwnableTileEvent testingEvent = new MonopolyGoToJailEvent(new MonopolyGameAlert());
    assertEquals(MonopolyStatusEffect.NORMAL, myPlayer.getStatusEffects());
    javafxRun(() -> testingEvent.callEvent(myPlayer, 0, myModel));
    clickOn(lookup("OK").queryButton());
    assertEquals(MonopolyStatusEffect.JAILED, myPlayer.getStatusEffects());
  }

  @Test
  public void landOnGoToJailTilePlayerLocation(){
    NonOwnableTileEvent testingEvent = new MonopolyGoToJailEvent(new MonopolyGameAlert());
    javafxRun(() -> testingEvent.callEvent(myPlayer, 0, myModel));
    clickOn(lookup("OK").queryButton());
    assertEquals(myModel.getTile(10), myPlayer.getCurrentTile());
  }


  @Test
  public void testLandOnUnownedPropertyJuniorMonopoly(){
    OwnableTileEvent testingEvent = new JuniorMonopolyPropertyTileEvent(new MonopolyGameAlert());
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(1),myModel));
    buttonTest = lookup("OK").queryButton();
    clickOn(buttonTest);
    assertEquals(190, myPlayer.getFunds());
    }

  @Test
  public void testLandOnOwnedPropertyJuniorMonopoly(){
    OwnableTileEvent testingEvent = new JuniorMonopolyPropertyTileEvent(new MonopolyGameAlert());
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(1),myModel));
    clickOn(lookup("OK").queryButton());
    javafxRun(() -> testingEvent.callEvent(myPlayer, myModel.getPlayer("P2"), (OwnableTile) myModel.getTile(1),myModel));
    clickOn(lookup("OK").queryButton());
    assertEquals(248, myModel.getPlayer("P2").getFunds());
  }

  @Test
  public void testLandOnOwnedPropertyInMonopolyJuniorMonopoly(){
    OwnableTileEvent testingEvent = new JuniorMonopolyPropertyTileEvent(new MonopolyGameAlert());
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(1),myModel));
    clickOn(lookup("OK").queryButton());
    javafxRun(() -> testingEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile) myModel.getTile(3),myModel));
    clickOn(lookup("OK").queryButton());
    javafxRun(() -> testingEvent.callEvent(myPlayer, myModel.getPlayer("P2"), (OwnableTile) myModel.getTile(1),myModel));
    clickOn(lookup("OK").queryButton());
    assertEquals(246, myModel.getPlayer("P2").getFunds());
  }

  @Test
  void testPlayerMovesAfterThreeFullTurnsJailed() {
    myModel.payPlayer(myPlayer,myModel.getBank(),1000);
    javafxRun(() -> myModel.jumpPlayerToTile(myPlayer, myModel.getTile(30)));
    javafxRun(() -> myModel.callPlayerTileEvent(myPlayer));
    clickOn(lookup("OK").queryButton());
    javafxRun(() ->myModel.doTurn(myPlayer));
    clickOn(lookup("No").queryButton());
    javafxRun(() ->myModel.doTurn(myPlayer));
    clickOn(lookup("No").queryButton());
    javafxRun(() ->myModel.doTurn(myPlayer));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("Yes").queryButton());
    assertNotEquals(myModel.getTile(10), myPlayer.getCurrentTile());
  }

  @Test
  void testPlayerCantMoveInJail() {
    javafxRun(() -> myModel.jumpPlayerToTile(myPlayer, myModel.getTile(30)));
    javafxRun(() -> myModel.callPlayerTileEvent(myPlayer));
    clickOn(lookup("OK").queryButton());
    javafxRun(() -> myModel.doTurn(myPlayer));
    clickOn(lookup("No").queryButton());
    assertEquals(myModel.getTile(10), myPlayer.getCurrentTile());
  }

  @Test
  void testPassGo() {
    javafxRun(() -> myModel.jumpPlayerToTile(myPlayer, myModel.getTile(0)));
    javafxRun(() -> myModel.callPlayerTileEvent(myPlayer));
    clickOn(lookup("OK").queryButton());
    assertEquals(450, myPlayer.getFunds());
  }

  @Test
  void testBankruptcyTileEventEvenRoll(){
    NonOwnableTileEvent bankruptcyTileEvent = new BankruptcyTileEvent(new MonopolyGameAlert());
    javafxRun(() -> bankruptcyTileEvent.callEvent(myPlayer,0,myModel));
    clickOn(lookup("OK").queryButton());
    assertEquals(MonopolyStatusEffect.NORMAL, myPlayer.getStatusEffects());
  }

  @Test
  void testBankruptcyTileEventOddRoll(){
    NonOwnableTileEvent bankruptcyTileEvent = new BankruptcyTileEvent(new MonopolyGameAlert());
    myModel.getRandomizable().getNextRoll();
    myModel.getRandomizable().getNextRoll();
    myModel.getRandomizable().getNextRoll();
    myModel.getRandomizable().getNextRoll();
    javafxRun(() -> bankruptcyTileEvent.callEvent(myPlayer,0,myModel));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("OK").queryButton());
    assertTrue(!myModel.getPlayers().contains(myPlayer));
  }

  @Test
  void testDrawCardTileEvents(){
    NonOwnableTileEvent drawCardTileEvent = new MonopolyChanceEvent(new MonopolyGameAlert());
    javafxRun(() -> drawCardTileEvent.callEvent(myPlayer,0,myModel));
    clickOn(lookup("OK").queryButton());
    NonOwnableTileEvent drawCardTileEventCommunityChest = new MonopolyCommunityChestEvent(new MonopolyGameAlert());
    javafxRun(() -> drawCardTileEventCommunityChest.callEvent(myPlayer,0,myModel));
    clickOn(lookup("OK").queryButton());
  }

  @Test
  void testWinTileEvent(){
    NonOwnableTileEvent winGameEvent = new WinTileEvent(new MonopolyGameAlert());
    assertEquals(false, myModel.checkIfGameOver());
    assertEquals(false, myPlayer.checkIfWinner());
    javafxRun(() -> winGameEvent.callEvent(myPlayer,0,myModel));
    clickOn(lookup("OK").queryButton());
    assertEquals(true, myModel.checkIfGameOver());
    assertEquals(true, myPlayer.checkIfWinner());
  }

  @Test
  void moswapolyPropertyTileUnknowned(){
    myModel.payPlayer(myPlayer,myModel.getBank(),10000);
    OwnableTileEvent tileEvent = new MoswapolyPropertyTileEvent(new MonopolyGameAlert());
    javafxRun(() -> tileEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile)myModel.getTile(1),myModel ));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("Yes").queryButton());
    int cost = (int) (((OwnableTile)myModel.getTile(1)).getPropertyValue() * Math.sqrt(ArrayUtil.sum(myModel.getRandomizable().getLastRoll())));
    assertEquals(10250-cost, myPlayer.getFunds());
  }

  @Test
  void moswapolyPropertyTileUnknownedNoBuy(){
    myModel.payPlayer(myPlayer,myModel.getBank(),10000);
    OwnableTileEvent tileEvent = new MoswapolyPropertyTileEvent(new MonopolyGameAlert());
    javafxRun(() -> tileEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile)myModel.getTile(1),myModel ));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("No").queryButton());
    clickOn(lookup("OK").queryButton());
    assertTrue(!((OwnableTile)myModel.getTile(1)).getOwner().equals(myModel.getBank()));
  }

  @Test
  void moswapolyPropertyTileOwned(){
    myModel.payPlayer(myPlayer,myModel.getBank(),10000);
    OwnableTileEvent tileEvent = new MoswapolyPropertyTileEvent(new MonopolyGameAlert());
    javafxRun(() -> tileEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile)myModel.getTile(1),myModel ));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("Yes").queryButton());
    javafxRun(() -> tileEvent.callEvent(myPlayer, myModel.getPlayer("P2"), (OwnableTile)myModel.getTile(1),myModel ));
    clickOn(lookup("OK").queryButton());
    assertEquals(248, myModel.getPlayer("P2").getFunds());
  }

  @Test
  void moswapolyPropertyTileOwnedIncreasedRent(){
    myModel.payPlayer(myPlayer,myModel.getBank(),10000);
    myPlayer.addOwnable((Ownable)myModel.getTile(3));
    myPlayer.addOwnable((Ownable)myModel.getTile(6));
    myPlayer.addOwnable((Ownable)myModel.getTile(8));
    OwnableTileEvent tileEvent = new MoswapolyPropertyTileEvent(new MonopolyGameAlert());
    javafxRun(() -> tileEvent.callEvent(myModel.getBank(), myPlayer, (OwnableTile)myModel.getTile(1),myModel ));
    clickOn(lookup("OK").queryButton());
    clickOn(lookup("Yes").queryButton());
    javafxRun(() -> tileEvent.callEvent(myPlayer, myModel.getPlayer("P2"), (OwnableTile)myModel.getTile(1),myModel ));
    clickOn(lookup("OK").queryButton());
    assertEquals(246, myModel.getPlayer("P2").getFunds());
  }

}