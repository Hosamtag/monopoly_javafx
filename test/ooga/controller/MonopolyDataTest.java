package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.stage.Stage;
import ooga.controller.readers.dataversionreaders.DataVersionReaderExternal;
import ooga.controller.readers.dataversionreaders.DataVersionReaderInternal;
import ooga.controller.factory.TileFactory;
import ooga.controller.readers.gameobjectsreaders.CardReader;
import ooga.controller.readers.gameobjectsreaders.SavedGameReader;
import ooga.controller.readers.gameobjectsreaders.TileReader;
import ooga.controller.propertiesaccessers.PropertyFileMaker;
import ooga.controller.savers.GameSaver;
import ooga.controller.util.KeyValuesReader;
import ooga.controller.util.PropertiesFileValueReader;
import ooga.controller.propertiesaccessers.PropertyFileAccessor;
import ooga.controller.scorekeeper.TopScorersExternal;
import ooga.controller.scorekeeper.TopScoresInternal;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidPropertiesFileException;
import ooga.model.Card;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.Tile;
import ooga.model.monopoly.MonopolyModel;
import ooga.model.monopoly.MonopolyPlayer;
import ooga.model.randomizables.NormalDice;
import ooga.view.alerts.MonopolyGameAlert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeTest;


public class MonopolyDataTest extends DukeTest {

  String[] fireBaseRulesTest = {"RerollOnTuples", "StrictBankruptcy", "EndGameOnFirstBankruptcy"};
  String[] fireBaseButtonsTest = {"Roll","Trade","Upgrade","Load","StartNew","Save","ChangeMortgage"};


  List<String> testStringInput = new ArrayList<>() {{
    add("GoTile,Go,200,Corner,go.png,");
    add("RegularPropertyTile,Mediterranean Avenue,200,brown,Property,200,brown,");
  }};


  List<Object> testKeyValues = new ArrayList<>() {{
    add("Version");
    add("Title");
    add("ChanceDeck");
    add("CommunityChestDeck");
    add("Tiles");
    add("Rules");
  }};

  Map<String,String> testSavedGameReader = new HashMap<>(){{
    put("CurrentPlayer","1");
    put("NumberOfPlayers","0");
    put("Dice","Normal,6,2");
    put("TileUpgrades",",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
    put("GameVersion","original");
  }};

  Map<String,String> testSavedGameReader2 = new HashMap<>(){{
    put("GameVersion","original");
    put("CurrentPlayer","2");
    put("Dice","Normal,6,2");
    put("NumberOfPlayers","4");
    put("Player1","Hosam");
    put("Player1Type","Monopoly");
    put("Player1Icon", "playericons/car.png");
    put("Player1Cash","2000");
    put("Player1Tile","Go");
    put("Player1Ownables","");
    put("Player2","Hosam2");
    put("Player2Type","Monopoly");
    put("Player2Icon", "playericons/boat.png");
    put("Player2Cash","2000");
    put("Player2Tile","Go");
    put("Player2Ownables","");
  }};



  Map<String,String> testMapValues = new HashMap<>(){{
    put("Tiles","testTiles");
    put("Version","test");
    put("CommunityChestDeck","communityChestDeck1");
    put("Title","test Monopoly");
    put("ChanceDeck","chanceDeck1");
    put("Rules", "RerollOnTuples,StrictBankruptcy,EndGameOnFirstBankruptcy");
  }};

  Map<String,String> testBordwalkValues = new HashMap<>(){{
    put("rentStructure","50,200,600,1400,1700,2000");
    put("passiveEvent","ownable.OwnableDoNothingEvent");
    put("price","400");
    put("monopoly","blue");
    put("name","Boardwalk");
    put("viewType","Property");
    put("upgradeCosts", "200,200,200,200,200");
    put("type", "tiles.MonopolyOwnableUpgradableTile");
    put("event", "ownable.basemonopoly.MonopolyPropertyEvent");
  }};

  Map<String,String> getTestSavedGameReader = new HashMap<>(){{
    put("Player4Icon","dog");
    put("Player3Tile","Go");
    put("Player3Type","Monopoly");
    put("Player2Tile","Go");
    put("Player2Ownables","");
    put("Player3Ownables","");
    put("Player4Ownables", "");
    put("Player4Cash", "250");
    put("GameVersion", "original");
    put("Player1Ownables","Reading Railroad:0");
    put("Player4Tile", "Go");
    put("Player1Tile", "Go");
    put("Player2", "P2");
    put("Player1", "P1");
    put("Player4", "P4");
    put("Player3","P3");
    put("Player4Type", "Monopoly");
    put("Player1Type", "Monopoly");
    put("Player1Cash", "250");
    put("CurrentPlayer", "1");
    put("NumberOfPlayers", "4");
    put("Dice", "Normal,2,5");
    put("Player2Cash", "250");
    put("Player1Icon", "car");
    put("Player3Icon", "boat");
    put("Player3Cash", "250");
    put("Player2Icon", "hat");
    put("Player2Type", "Monopoly");
  }};

  Map<String,String> testCard1Values = new HashMap<>(){{
    put("Event","action.BaseMonopolyTaxCardEvent");
    put("Prompt","PayTax");
    put("IntParams","300");
  }};

  Map<String,String> testPlayer1 = new HashMap<>(){{
    put("currentTile","Go");
    put("ownables","null");
    put("name","P1");
    put("icon","car");
    put("type","Monopoly");
    put("cash","250");
  }};

  Map<String,String> testPlayer2 = new HashMap<>(){{
    put("currentTile","Go");
    put("ownables","null");
    put("name","P2");
    put("icon","hat");
    put("type","Monopoly");
    put("cash","250");
  }};
  Map<String,String> testPlayer3 = new HashMap<>(){{
    put("currentTile","Go");
    put("ownables","null");
    put("name","P3");
    put("icon","boat");
    put("type","Monopoly");
    put("cash","250");
  }};
  Map<String,String> testPlayer4 = new HashMap<>(){{
    put("currentTile","Go");
    put("ownables","null");
    put("name","P4");
    put("icon","dog");
    put("type","Monopoly");
    put("cash","250");
  }};

  List<Map<String,String>> testValuesSavedGameReader = new ArrayList<>(){{
    add(testPlayer1);
    add(testPlayer2);
    add(testPlayer3);
    add(testPlayer4);
  }};
  Map<String,String> card1 = new HashMap<>(){{
    put("IntParams","300");
    put("Event","action.BaseMonopolyTaxCardEvent");
    put("Prompt","PayTax");
  }};
  Map<String,String> card2 = new HashMap<>(){{
    put("IntParams","0");
    put("Event","movement.BaseMonopolyMoveToTileCardEvent");
    put("Prompt","Move");
    put("Location","Boardwalk");
  }};
  Map<String,String> card3 = new HashMap<>(){{
    put("IntParams","0");
    put("Event","action.GetOutOfJailFreeCardEvent");
    put("Prompt","OutOfJail");
  }};
  Map<String,String> card4 = new HashMap<>(){{
    put("IntParams","10");
    put("Event","movement.AdvanceToNearestUtility");
    put("Prompt","Move");
    put("Location","Utility");
  }};
  List<Map<String,String>> testCardMapValues = new ArrayList<>(){{
    add(card1);
    add(card2);
    add(card3);
    add(card4);
  }};

  String testTileInput = "tiles.MonopolyOwnableUpgradableTile,Boardwalk,Property,400,blue,50,200,600,1400,1700,2000,ownable.basemonopoly.MonopolyPropertyEvent,ownable.OwnableDoNothingEvent,200,200,200,200,200,";
  String testTileInput2 = "tiles.MonopolyOwnableUpgradableTile,Atlantic Avenue,Property,260,yellow,22,110,330,800,975,1150,ownable.basemonopoly.MonopolyPropertyEvent,ownable.OwnableDoNothingEvent,150,150,150,150,150,";
  String testCardInput = "action.BaseMonopolyTaxCardEvent,PayTax,300,";


  List<Player> inputtedPlayerList;
  List<String> icons;
  List<Tile> tileList;



  @Override
  public void start(Stage stage) {
    MonopolyModel myModel;
    ResourceUtil.setLanguage("English");
    DataVersionReader newReader = new DataVersionReaderInternal();
    TileFactory myFactory;
    try {
      newReader.createNewGameVerison("original");
      myFactory = new TileFactory(newReader.getTileMaps());
      tileList=myFactory.getMyTiles();
    } catch (Exception e) {
      myFactory = new TileFactory(new ArrayList<>());
      System.out.println("If you're reading this it's too late");
    }
    // myFactory=new TileFactory(new ArrayList<>(), myHandler);
    List<String> playerNames = Arrays.asList("P1", "P2", "P3", "P4");
    icons =Arrays.asList("car","hat","boat","dog");
    NormalDice myDie = new NormalDice(1, 6);
     inputtedPlayerList = new ArrayList<>();
    for (String player : playerNames) {
      inputtedPlayerList.add(new MonopolyPlayer(player, myFactory.getMyTiles().get(0), 250));

    }
    myModel = new MonopolyModel(myFactory.getMyTiles(), inputtedPlayerList, myDie, new ArrayList<Card>(), new MonopolyGameAlert());
    MonopolyPlayer myBank = (MonopolyPlayer) myModel.getBank();
    Ownable tradedTile = myBank.getOwnables().get(2);
    List<Ownable> tilesToTrade = new ArrayList<>();
    tilesToTrade.add(tradedTile);
    myModel.exchangeOwnables(inputtedPlayerList.get(0), myBank, tilesToTrade);

  }


  @Test
  void readIndividualTile() throws IOException {
    GameObjectsReader thisTileReader = new TileReader();
    String test = thisTileReader.getInfoForSpecificGameObject("original/boardwalk");
    assertEquals(testTileInput,test);
  }

  @Test
  void readIndividualCard() throws IOException {
    GameObjectsReader thisCardReader = new CardReader();
    String test = thisCardReader.getInfoForSpecificGameObject("monopoly/card1");
    assertEquals(testCardInput,test);
  }


  @Test
  void readIndividualTile2() throws IOException {
    GameObjectsReader thisTileReader = new TileReader();
    String test = thisTileReader.getInfoForSpecificGameObject("original/atlanticAvenue");
    assertEquals(testTileInput2,test);
  }

  @Test
  void testgameInfoMapperTile() throws IOException, InvalidKeyException {
    GameObjectsReader thisTileReader = new TileReader();
    assertEquals(testBordwalkValues,thisTileReader.gameInfoMapper("original/boardwalk"));
  }
  @Test
  void wrongKeyValuesTest() throws IOException {
    Assertions.assertThrows(InvalidPropertiesFileException.class, () -> {
      KeyValuesReader.getKeySetOfPropertiesFile("original/wrong");
    });
  }

  @Test
  void emptyPropertiesFileTest(){
    GameObjectsReader thisTileReader = new TileReader();
    Assertions.assertThrows(InvalidPropertiesFileException.class, () -> {
      KeyValuesReader.getKeySetOfPropertiesFile("");
    });
  }

  @Test
  void readFileWrongFileTest() {
    Assertions.assertThrows(InvalidPropertiesFileException.class, () -> {
      PropertiesFileValueReader.readFile("wrong", "key");
    });
  }

  @Test
  void readFileWrongKeyTest() throws IOException, InvalidKeyException {
    Assertions.assertThrows(InvalidKeyException.class, () -> {
      PropertiesFileValueReader.readFile("data/gameVersions/test.properties", "");
    });
  }

  @Test
  void testPropertyFileGameAlertErrors() throws IOException {
    Accessible thisAccessor = new PropertyFileAccessor();
    javafxRun(()->thisAccessor.getKeySetOfPropertiesFile("ashdfh"));
    assertTrue(getDialogMessage().equals("This Game Object Does Not Exist"));
    javafxRun(()->thisAccessor.readFile("data/gameVersions/test.properties", ""));
    assertTrue(getDialogMessage().equals("Wrong Key Given"));
    javafxRun(()->thisAccessor.readFile("wrong.properties", ""));
    assertTrue(getDialogMessage().equals("Wrong Game Version"));
    javafxRun(()->thisAccessor.createMap(thisAccessor.getKeySetOfPropertiesFile("data/gameVersions/test.properties"), ""));
    assertTrue(getDialogMessage().equals("Wrong Game Version"));
    javafxRun(()->thisAccessor.createMap(thisAccessor.getKeySetOfPropertiesFile("wrong.properties"), ""));
    assertTrue(getDialogMessage().equals("This Game Object Does Not Exist"));
  }

  @Test
  void testgameInfoMapperCard() throws IOException, InvalidKeyException {
    GameObjectsReader thisCardReader = new CardReader();
    assertEquals(testCard1Values,thisCardReader.gameInfoMapper("monopoly/card1"));
  }


  @Test
  void testKeyValueReader() throws IOException, InvalidKeyException {
   assertEquals("test", PropertiesFileValueReader
       .readFile("data/gameVersions/test.properties","Version"));
   assertEquals("test Monopoly", PropertiesFileValueReader.readFile("data/gameVersions/test.properties","Title"));
   assertEquals("chanceDeck1", PropertiesFileValueReader.readFile("data/gameVersions/test.properties","ChanceDeck"));
   assertEquals("communityChestDeck1", PropertiesFileValueReader.readFile("data/gameVersions/test.properties","CommunityChestDeck"));
   assertEquals("testTiles", PropertiesFileValueReader.readFile("data/gameVersions/test.properties","Tiles"));
  }

  /**
   * tests that the Accessible interface is able to get the values needed
   */

  @Test
  void testAccessibleInterfaceMethods() throws IOException {
    Accessible thisAcessor = new PropertyFileAccessor();
    assertEquals(testKeyValues, thisAcessor.getKeySetOfPropertiesFile("data/gameVersions/test.properties"));
    assertEquals(testMapValues,thisAcessor.createMap(thisAcessor.getKeySetOfPropertiesFile("data/gameVersions/test.properties"),"data/gameVersions/test.properties"));
  }





  @Test
  void readSavedPlayer() throws IOException {
    SavedGameReader thisdataVersionReader = new SavedGameReader();
    assertEquals(testSavedGameReader,thisdataVersionReader.gameObjectsViewReader("data/gameVersions/initialization/tester.properties"));
  }

  @Test
  void propertyFileMakerTest() throws IOException, InvalidKeyException {
    PropertyFileMaker thisGaveSaver = new PropertyFileMaker();
    thisGaveSaver.createFile("data/gameVersions/initialization/test");
    thisGaveSaver.writeVals("test=hello");
    thisGaveSaver.closeFile();
    assertEquals("hello",PropertiesFileValueReader.readFile("data/gameVersions/initialization/test.properties","test"));
    thisGaveSaver.createFile("data/gameVersions/initialization/test");
    thisGaveSaver.writeVals("test=changed");
    thisGaveSaver.closeFile();
    assertEquals("changed",PropertiesFileValueReader.readFile("data/gameVersions/initialization/test.properties","test"));
    thisGaveSaver.closeFile();
  }

  @Test
  void TestDataVersionReader() throws IOException, InterruptedException, InvalidKeyException {
    DataVersionReader thisDataVersionReader = new DataVersionReaderInternal();
    thisDataVersionReader.createNewGameVerison("original");
    assertEquals(testCardMapValues,thisDataVersionReader.getCardMaps());
  }

  @Test
  void TestGameSaver() throws IOException, InvalidKeyException {
   GameSaver thisGameSaver = new GameSaver("tester2","original",inputtedPlayerList,icons,1, new NormalDice(2,5));
    assertEquals("original",PropertiesFileValueReader.readFile("data/gameversions/initialization/skipToGame.properties","GameVersion"));
  }

  @Test
  void TestGameSaver2() throws IOException, InvalidKeyException {
    GameSaver thisGameSaver = new GameSaver("tester5","original",inputtedPlayerList,icons,1, new NormalDice(2,5));
    assertEquals("original",PropertiesFileValueReader.readFile("data/gameversions/initialization/skipToGame.properties","GameVersion"));
  }


  @Test
  void TestSavedGameReader() throws IOException {
    SavedGameReader thisGameReader = new SavedGameReader();
    assertEquals(getTestSavedGameReader,thisGameReader.gameObjectsViewReader("data/gameversions/initialization/tester5.properties"));
    assertEquals(testValuesSavedGameReader.get(1).get("name"),thisGameReader.createMapOfPlayers(thisGameReader.gameObjectsViewReader("data/gameversions/initialization/tester5.properties")).get(1).get("name"));
  }


  @Test
  void topScoreInternalTest() throws IOException, InterruptedException {
    Accessible thisAccessor = new PropertyFileAccessor();
    TopScoreKeeper thisKeeper= new TopScoresInternal();
    thisKeeper.updateTopPlayer("test");
    assertEquals("test",thisAccessor.readFile("data/gameVersions/topScores/topScore.properties","firstPlace"));
    thisKeeper.updateTopScore(10);
    assertEquals("10",thisAccessor.readFile("data/gameVersions/topScores/topScore.properties","firstPlaceScore"));
    assertEquals("test",thisKeeper.getTopPlayer());
    thisKeeper.updateTopScore(10);
    assertEquals("10",thisKeeper.getTopScore()+"");

  }

  @Test
  void topScoreExternalTest() throws IOException, InterruptedException {
    TopScorersExternal thisKeeper= new TopScorersExternal();
    thisKeeper.updateTopPlayer("hosam");
    thisKeeper.updateTopScore(0);
    assertEquals("hosam",thisKeeper.getTopPlayer());
    assertEquals("0",thisKeeper.getTopScore()+"");

  }

  @Test
  void readGameVersionFromFirebaser() throws IOException, InterruptedException {
    DataVersionReader thisReader = new DataVersionReaderExternal();
    thisReader.createNewGameVerison("original");
    assertEquals("original",thisReader.getGameVersion());
    assertEquals("Move",thisReader.getClickHandlerType());
    assertEquals(Arrays.asList(fireBaseRulesTest),thisReader.getRules());
    assertEquals(Arrays.asList(fireBaseRulesTest),thisReader.getRules());
    assertEquals(Arrays.asList(fireBaseButtonsTest),Arrays.asList(thisReader.getGameButtons()));

  }

}
