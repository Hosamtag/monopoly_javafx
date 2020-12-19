final
====

This project implements a player for multiple related games.

Names: Alex Jimenez (ajj29), Hosam Tagel-Din (ht120), Isabella Knox (ik63), Malvika Jain (msj24), James Arnold (jra55)


### Timeline

Start Date: Oct 24, 2020

Finish Date: Nov 18, 2020

Hours Spent: 500+ hrs

### Primary Roles

Alex Jimenez (ajj29) - Model and Model interaction with controller and player through pop ups.

Hosam Tagel-Din (ht120) - view and controller, the factories related to the view in the controller and view packages, click handling,splash screens, monopoly board, tiles, cards on hover

Isabella Knox (ik63) - view and how game alerts occur, game theme styling, dice, player profiles.

Malvika Jain (msj24)- data reading and how it should be formatted and passed to work in the controller, model, and view 

James Arnold (jra55)  - model and how model relates to factories/controller

### Resources Used

1. Professor Duvall
2. Class Discussion Page ([Piazza](https://piazza.com/class/kdz40c8ei8t6zw?cid=107))
3. [JavaFx Documentation](https://docs.oracle.com/javase/8/javafx/api/toc.htm)
4. [Java Documentation](https://docs.oracle.com/en/java/)
5. Code examples from CS 307 lectures/labs ([lab_javafx](https://coursework.cs.duke.edu/compsci307_2020fall/lab_javafx), [lab_browser](https://coursework.cs.duke.edu/compsci307_2020fall/lab_browser), [example_simplification](https://coursework.cs.duke.edu/compsci307_2020fall/example_simplification), [lab_calculator](https://coursework.cs.duke.edu/compsci307_2020fall/lab_calculator), [example_encapsulation](https://coursework.cs.duke.edu/compsci307_2020fall/example_encapsulation), [example_testui](https://coursework.cs.duke.edu/compsci307_2020fall/example_testui) etc.)
6. CS 307 TAs (office hours TAs and each member's personal Undergraduate TA)
7. [How to do Dialogs](https://code.makery.ch/blog/javafx-dialogs-official/)
8. [Styling with CSS](https://docs.oracle.com/javafx/2/layout/style_css.htm)
9. [CSS Reference Guide](https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html#node)
10. [CSS Guide](https://www.tutorialspoint.com/javafx/javafx_css.htm)
11. [enumeration to a set list](https://stackoverflow.com/questions/5610822/convert-enumeration-to-a-set-list)
12. [Google Firebase Documentation](https://firebase.google.com/docs/admin/setup#prerequisites) 
13. [Datasnapshot](https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method)
14. [StackOverflow] finding the paths for shapes to be used in css files

### Running the Program

Main class:

In order to run the program, run Main.main().   Make sure the doc folder is excluded from the compiler.  Also mark the resources and data folders as resources. Since Firebase is used, must import in library (in Project Structures) com.google.firebase:firebase-admin:7.0.1

 

Data files needed: 

The datafiles needed to run a game are done in a hierarchical order that include a base file (that defines the game version). This file defines the game version, the click handler, the game order, the title, the specific chance deck that is used, the specific community check deck that is used, the tiles on the board that are wanted, the buttons, and the rules. This specification can either be loaded from the properties file or an external firebase database. Everything in this initial properties file can be switched out to have different specifications -- making almost infinite possibilities the game can be extended. This properties file defines the various game objects that are stated in the “version” properties file. This information can also be found in a firebase database. 

The next file that is needed is a Tile Configuration property file that states the desired tiles, the game you want them from (for example you have to specify if you want the boardwalk tile from the original monopoly game or the junior game), and the original order you want them to appear on the board. For each tile mentioned in the Tile Configuration property file the type, name, viewType, price,monopoly color, rent structure, associated event, the passive event, and the upgrade cost need to be specified in a separate properties file. The properties files for individual tiles are separated into folders for organization. In the decks package the various cards for different games are defined. The properties files for the individual cards have anywhere from 1 to 5 parameters to define the prompt and action that should be triggered when the card is picked up. These are the basic files needed to load a new game. 

The top score properties file is there to keep an internal record of the player that has ended a game with the most money. 
The initialization properties files are game files we created that can be used to load a pre-played/saved game. However, newly saved games are saved in the saved games folder. Additionally, in a firebase database top scores can be saved.

Cheat keys: 

    * Digit 1-9: Moves current player ahead 1-9 spaces
    * Digit 0:  Changes the current player 
    * G: Skip splash screens to go to Original Game
    * S: Skip splash screens to go to Moswapoly
    * D: Decrease the current player’s funds by $100 (If a player’s funds goes into the negatives using this cheat key, the bankruptcy rule goes into effect after the dice is rolled). 
    * Click on a tile: jumps player to a tile (except in Moswapoly)
    

Features implemented:

 During a New Game initialization, you are able to choose between game versions, languages, and between default game rules and customizing game rules.
    
  Customizable Rules
  
    1. Bankruptcy Rules - This defines what occurs to the properties one player goes bankrupt and is removed from the game
       a. BankruptcyGoesToBank - All property tiles go to the bank. 
       b. BankruptcyGoesToAuction - All property tiles are auctioned and given to players who place the bid. 
    2. ReRoll Rules - This defines what occurs 
       a. ReRollOnTuples - If doubles or triples are rolled, a player is able to roll again. 
    3. How To Win - defines when the game ends and how the winner is declared
       a. EndGameOnFirstBankruptcy - Once one player has gone bankrupt, the game ends and the winning player is decided on which player has the most current funds (this does not include their properties, houses, hotels, etc.).
       b. LastManStandingWin  - Once every other player has gone bankrupt and left the game, the last player wins the game.
    
    
Four variations of Monopoly have been implemented. 

   1. Original Monopoly 
       
       Game board is 40 tiles. 
       Default rules: 
       
           1. If a player lands on an unowned property, the current player is given the option to buy the property. 
           2. If a player declines the option to buy, an auction for the tile is held. The property will go to the player who places the bid (and the cost will be subtracted from their funds).
           3. Rolling doubles (or any non-1 Tuple)  lets you roll again
           4. Bankruptcies result in auctions
           5. Last player remaining is the winner
	       6. Three turns, doubles, or bail will get you out of jail
 
                
   2. Junior Monopoly 
   
       Game board is 24 tiles. 
       
       Default rules: 
    
           1. If a player lands on an unowned property, the player must buy the property. 
           2. There is no auction for properties because players must buy all tiles they land on.
           3. There is no upgrading of properties.
           4. There is no trading. 
           5. Rolling doubles (or any non-1 Tuple) lets you roll again
	       6. Bankruptcies return all properties to the bank
           7.The game ends when the first bankruptcy occurs and the winner is whoever has the most money
      
    
   3. Moswapoly
          Game board is 28 tiles. 

       Default rules: 
       
           1. Clicking on two tiles swaps the tiles.
           2. Rolling tuples lets you go again
           3. Bankruptcy sells all tiles to the bank
	       4. First player with a hotel wins or first player to monopoly
	       5. Speed tiles change your speed until you pass the next one
	       6. If a player lands on an unowned property, the current player is given the option to buy. The price is determined by the base price value times the square root of that the player roles 
           7. If a player decides to decline buying a property they landed on, the property tile is randomly assigned to another player. 	
           8. If a player lands on an owned property the cost is the base rent times the square root of the number of total properties the owner owns
           9. New Action card that removes a players property at random
           10. Tile that bankrupts you if you land on it, tile that makes you win automatically if you land on it
          
   4. Dukeopoly 
       Game board is 40 tiles. 
       
       Default rules:
           1. Same as Original
            
 
   Customization of Game Screen: Using the Menu, options are given to the user to edit the view. 
        
        Choose Board: changes the tiles layout on the screen
            Square - Default setting. This places game tiles in a square formation to give the classic Monopoly game board layout. 
            Grid - This places the tiles in a grid formation. 
        Choose Theme: styling for splash screens, buttons, player profiles, and         
            monopoly - This is the default theme. Green/White theme with unique backgrounds for each of the four versions. Red Dice for dice with less than 6 sides (images). Dice with greater than 6 sides, red dice with white text is used.
            duke - Blue/Black/White theme with unique backgrounds for each of the four versions. White Dice for dice with less than 6 sides (images). Dice with greater than 6 sides, blue dice with white text is used.
            dark - Black/Gray/White theme with unique backgrounds for each of the four versions. Gray Dice for dice with less than 6 sides (images). Dice with greater than 6 sides, black dice with white text is used.
        Choose Tile Shape: Changes the tile shapes (excluding the color bars)
            Rectangle 
            Circle 
            Hexagon
        Choose Tile Color: gives a color picker pop up for styling tiles
            Tile: color chosen changes the color background of the tiles 
            Border: color choses the outline of the shape of the tiles

Game Buttons in All Versions:

        Add A Game: Allows a user to have an additional game running in parallel with the current game. Different games can be accessed using the tabs.
        Start A New Game: Takes user to splash screen to restart the current game
        Load A Game: Allows a user to select a new properties game file 
        Save Game: saves the current game statuses into a new properties file.


Tiles Events

    * MonopolyChanceEvent: Gives the player a random card specified from the deck
    * MonopolyCommunityChestEvent: Gives the player a random card specified from the deck
    * Bankruptcy Tile Event: Makes the player bankrupt and removes them from the game
    * FastSpeedEvent: Makes the player move double their role
    * SlowSpeedEvent: Makes the player move half their role
    * MonopolyGoEvent: Give the player an amount of money passed into the card
    * MonopolyGoToJailEvent: Sends the player to jail
    * MonopolyTaxEvent: Taxes the player the amount of money specified in the property file
    * NonOwnableDoNothingEvent: Does nothing, used for passive events
    * ResetSpeedEvent: Sets player status back to normal
    * WinTileEvent: Makes player win the game
    * JuniorMonopolyPropertyTileEvent: Requires that the player buys the tile. Charges base rent
    * MoswapolyPropertyTileEvent:Player has option to buy tile. Price is based on base property value scaled by what player rolls. If not bought assigned to a random player. Rent based on base rent scaled by the number of properties the owner owns. 
    * OwnableDoNothingEvent: Does nothing, used for passive events. 
    * MonopolyPropertyEvent:Player has option to buy tile. Price is based on base property value. If no buy property is auctioned. Rent based off base rent and houses/hotels.
    * MonopolyRailroadEvent: Bought the same as MonopolyPropertyEvent. Rent based off base rent times  number of railroads the player owns.
    * MonopolyUtilityEvent: Bought the same as MonopolyPropertyEvent. Rent based off base rent times what the player rolls. 

Game Cards

    * PaymentEvent: Pays the player the amount specified in the property file.
    * TaxEvent: Taxes the player the amount specified in the property file. 
    * GetOutOfJailFreeCard: Used to automatically get out of jail
    * LoseRandomPropertyEvent: Causes player to randomly lose a property
    * AdvanceToNearestUtility: Advances to nearest utility. If unowned can buy if owned, pay utility price times roll times value specified in property file.
    * BaseMonopolyMoveToTileCardEvent: Moves the player to the tile specified.

Turn Types

    * Fast: Moves the player double the roll
    * Normal: Moves the player the number of rolls specified
    * Slow: Moves the player half the roll
    * Jail: Player escapes if they have get out of jail free card, they pay bail, they roll doubles, or the have stayed their max sentence. No movement otherwise.

### Notes/Assumptions

Assumptions or Simplifications:

1. User doesn't use cheat keys after game ends
2. Game play paths on the game board are linear.  (no split paths)
3. Auction pop up assumes all players are together and able to debate bids. The auction box input is of the highest bid and the bid winner.
4. The name of the jail tile is ‘jail’
5. Integer parameters for new tiles must be in alphabetical order based on their .properties keys (for making custom types of tiles with unspecified numbers of int params)
6. The information about tiles and cards is already entered correctly 
7. The data read from firebase takes a while to load because of asynchronous reading issues 


##### For Running Tests:
1. In order to get 'ClickOn' to work properly in IntelliJ, the Security & Privacy on your local computer must be set to allow for IntelliJ to control your computer.
For MAC Users, this is under Security & Privacy, under Privacy and under Accessibility. IntelliJ must be an app that is allowed to control your computer.

2. Issues may arise when running the entire test package together due to computer speed differences and clickOn/window pop up timing. All tests pass if a class is re-run by itself.

3. It may take a while to run some of the data tests that relate to reading in from Firebase because of asynchronous reading issues. 

##### Interesting data files:

1. The test files are interesting in the sense they have varying information (maybe not present in a traditional monopoly game)
2. The moswapoly.properties and the dukeopoly.properties are interesting in the sense they allow for fun and new variations of monopoly


##### Known Bugs:
1. If one of the properties are clicked on in the player profiles during the game, the combo box shows the property value and the name is no longer able to be clicked and rechosen. To update the view and retrieve the names, the dice must be rolled (next player must take their turn).
2. During splash screens, icons listed when assigning icons to players disappear from view on the drop down after being clicked (icons can still be clicked and selected, just not seen on the dropdown).


##### Extra credit:
   
1. Highscores: The Top score is saved to a Google Firebase or to a local properties file.
2. Save Game Data in the Web: The Top score is saved to a Google Firebase
3. Preferences: Each player is able to be customized through selecting their own names and icons (icons are unique to each game version).
4. Hovering over tiles with your mouse shows the tile description in a pop up that displays in the upper right hand side of the game screen.
5. Game Area Editor: Users can design the game's initial configuration and its content dynamically using a GUI
6. Load Games: Users can load a new game during a game session
7. Save Games. Users can save the current state of an active game



### Impressions

Overall, this assignment was able to teach us the usefulness and need for encapsulation. This project also taught the importance and usefulness of using API and interfaces. A program which utilizes a model, view, and controller is critical to this assignment. This showed us the vast flexibility and use for the separation between back-end and front-end code. How to use CSS styling was learned alongside how to use reflection. Ensuring various areas are read from properties files, using reflection, etc. allowed for a wide range of rule implementations, tile events, game layouts, etc.
