import java.util.Random;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * An implementation of the game Red7, by Asmadi Games.  Only includes two card colors: violet and indigo.  Thanks to Asmadi Games for granting me permission to use their game rules for educational purposes.  This implementation is intended to be terrible, and will be updated during the semester in a Software Engineering course.
 *
 * @author Kyle Burke <kwburke@plymouth.edu>
 */
public class Red7 extends Application {
    
    /**
     * Main method to run the game.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Removes a random card from the deck and puts it in the appropriate collection.
     */
    public static void dealCard(boolean[] redsInDeck, boolean[] yellowsInDeck, boolean[] violetsInDeck, ArrayList<String> targetColors, ArrayList<Integer> targetNumbers) {
        
        boolean workingCard = false;
        
        //keep trying to generate a working card
        while (!workingCard) {
        
            //generate a random index
            Random randGen = new Random();
            int cardIndex = randGen.nextInt(21); //21 because we only have three colors so far
            int colorIndex = cardIndex / 7;
            int numberIndex = cardIndex % 7;
            
            boolean[] cardArray = redsInDeck;
            if (colorIndex == 1) {
                cardArray = yellowsInDeck;
            } else if (colorIndex == 2) {
                cardArray = violetsInDeck;
            }
            
            workingCard = cardArray[numberIndex];
            if (workingCard) {
                //add the card to the output lists
                if (colorIndex == 0) {
                    targetColors.add("Red");
                } else if (colorIndex == 1) {
                    targetColors.add("Yellow");
                } else {
                    targetColors.add("Violet");
                }
                targetNumbers.add(numberIndex + 1);
                
                //remove the card from the deck
                cardArray[numberIndex] = false;
            }
        }
    }
    
    /**
     * Displays the whole game state.
     */
    public static void displayAll(Stage stage, String canvasColor, ArrayList<String> playerAPaletteColors, ArrayList<Integer> playerAPaletteNumbers, ArrayList<String> playerBPaletteColors, ArrayList<Integer> playerBPaletteNumbers, ArrayList<String> playerAHandColors, ArrayList<Integer> playerAHandNumbers, ArrayList<String> playerBHandColors, ArrayList<Integer> playerBHandNumbers) {
        VBox board = new VBox();
        board.setSpacing(2);
        
        //player A Hand
        Text handText = new Text("Player A's Hand");
        board.getChildren().add(handText);
        board.getChildren().add(getCardRow(playerAHandColors, playerAHandNumbers));
        
        //player A Palette
        board.getChildren().add(new Text("Player A's Palette:"));
        board.getChildren().add(getCardRow(playerAPaletteColors, playerAPaletteNumbers));
        
        //the canvas
        board.getChildren().add(new Text("Canvas:"));
        Rectangle canvasRectangle;
        if (canvasColor.equals("Red")) {
            canvasRectangle = new Rectangle(175, 125, Color.RED);
        } else if (canvasColor.equals("Yellow")) {
            canvasRectangle = new Rectangle(175, 125, Color.YELLOW);
        } else {
            canvasRectangle = new Rectangle(175, 125, Color.VIOLET);
        }
        board.getChildren().add(canvasRectangle);
        
        //player B Palette
        board.getChildren().add(new Text("Player B's Palette:"));
        board.getChildren().add(getCardRow(playerBPaletteColors, playerBPaletteNumbers));
        
        //player B Hand
        board.getChildren().add(new Text("Player B's Hand"));
        board.getChildren().add(getCardRow(playerBHandColors, playerBHandNumbers));
        
        //set up the scene and make sure it's visible
        stage.setScene(new Scene(board));
        stage.show();
        stage.sizeToScene();
    }
    
    /**
     * Gets a HBox for a single row of cards.
     */
    public static HBox getCardRow(ArrayList<String> colors, ArrayList<Integer> numbers) {
        HBox cardsRow = new HBox();
        for (int i = 0; i < colors.size(); i++) {
            StackPane cardGraphics = getCardGraphics(colors.get(i), numbers.get(i));
            cardsRow.getChildren().add(cardGraphics);
        }
        return cardsRow;
    }
    
    
    /**
     * Gets a StackPane with the graphics for a single card.
     */
    public static StackPane getCardGraphics(String color, int number) {
        StackPane cardPane = new StackPane();
        
        //add the color as a background rectangle
        Rectangle cardBase = new Rectangle(125, 175, Color.VIOLET);
        if (color.equals("Red")) {
            cardBase = new Rectangle(125, 175, Color.RED);
        } else if (color.equals("Yellow")) {
            cardBase = new Rectangle(125, 175, Color.YELLOW);
        }
        cardPane.getChildren().add(cardBase);
        
        //now add the text above that
        Text cardNumberText = new Text("" + number);
        cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
        if (color.equals("Yellow")) {
            cardNumberText.setFill(Color.BLACK);
        } else {
            cardNumberText.setFill(Color.WHITE);
        }
        cardPane.getChildren().add(cardNumberText);
        
        return cardPane;
    }
    
    /**
     * Determines whether the current player is winning.
     */
    public static boolean playerWinning(String canvasColor, ArrayList<String> playerPaletteColors, ArrayList<Integer> playerPaletteNumbers, ArrayList<String> opponentPaletteColors, ArrayList<Integer> opponentPaletteNumbers) {
        if (canvasColor.equals("Red")) {
            String maxPlayerColor = "Nothing";
            int maxPlayerNumber = 0;
            for (int i = 0; i < playerPaletteColors.size(); i++) {
                String color = playerPaletteColors.get(i);
                int number = playerPaletteNumbers.get(i);
                if (number > maxPlayerNumber) {
                    maxPlayerColor = color;
                    maxPlayerNumber = number;
                } else if (number == maxPlayerNumber) {
                    if (color.equals("Red") || (color.equals("Yellow") && maxPlayerColor.equals("Violet"))) {
                        maxPlayerColor = color;
                        maxPlayerNumber = number;
                    }
                }
            }
            String maxOpponentColor = "Nothing";
            int maxOpponentNumber = 0;
            for (int i = 0; i < opponentPaletteColors.size(); i++) {
                String color = opponentPaletteColors.get(i);
                int number = opponentPaletteNumbers.get(i);
                if (number > maxOpponentNumber) {
                    maxOpponentColor = color;
                    maxOpponentNumber = number;
                } else if (number == maxOpponentNumber) {
                    if (color.equals("Red") || (color.equals("Yellow") && maxPlayerColor.equals("Violet"))) {
                        maxOpponentColor = color;
                        maxOpponentNumber = number;
                    }
                }
            }
            
            System.out.println("Player's max card: " + maxPlayerColor + " " + maxPlayerNumber);
            System.out.println("Opponent's max card: " + maxOpponentColor + " " + maxOpponentNumber);
            
            //check whether player is winning
            if (maxPlayerNumber > maxOpponentNumber) {
                return true;
            } else if (maxPlayerNumber == maxOpponentNumber) {
                if (maxPlayerColor.equals("Red") || (maxPlayerColor.equals("Yellow") && maxOpponentColor.equals("Violet"))) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (canvasColor.equals("Yellow")) {
            //Yellow is "Most of one color"
            System.out.println("Yellow color!");
            
            //find player's biggest color
            ArrayList<Integer> playerReds = new ArrayList<Integer>();
            ArrayList<Integer> playerYellows = new ArrayList<Integer>();
            ArrayList<Integer> playerViolets = new ArrayList<Integer>();
            for (int i = 0; i < playerPaletteColors.size(); i++) {
                String color = playerPaletteColors.get(i);
                int number = playerPaletteNumbers.get(i);
                if (color.equals("Red")) {
                    playerReds.add(number);
                } else if (color.equals("Yellow")) {
                    playerYellows.add(number);
                } else if (color.equals("Violet")) {
                    playerViolets.add(number);
                }
            }
            //start with the reds
            ArrayList<Integer> maxPlayerList = playerReds;
            String playerMaxColor = "Red";
            //check whether the yellows are bigger
            if (playerYellows.size() > maxPlayerList.size()) {
                maxPlayerList = playerYellows;
                playerMaxColor = "Yellow";
            } else if (playerYellows.size() == maxPlayerList.size()) {
                //now check the biggest element in each
                int maxHighest = 0;
                for (int i = 0; i < maxPlayerList.size(); i++) {
                    maxHighest = Math.max(maxHighest, maxPlayerList.get(i));
                }
                int yellowHighest = 0;
                for (int i = 0; i < playerYellows.size(); i++) {
                    yellowHighest = Math.max(yellowHighest, playerYellows.get(i));
                }
                if (yellowHighest > maxHighest) {
                    maxPlayerList = playerYellows;
                    playerMaxColor = "Yellow";
                }
            }
            //now check whether the violets are bigger
            if (playerViolets.size() > maxPlayerList.size()) {
                maxPlayerList = playerViolets;
                playerMaxColor = "Violet";
            } else if (playerViolets.size() == maxPlayerList.size()) {
                //now check the biggest element in each
                int maxHighest = 0;
                for (int i = 0; i < maxPlayerList.size(); i++) {
                    maxHighest = Math.max(maxHighest, maxPlayerList.get(i));
                }
                int violetHighest = 0;
                for (int i = 0; i < playerViolets.size(); i++) {
                    violetHighest = Math.max(violetHighest, playerViolets.get(i));
                }
                if (violetHighest > maxHighest) {
                    maxPlayerList = playerViolets;
                    playerMaxColor = "Violet";
                }
            }
            
            //find opponent's biggest color
            ArrayList<Integer> opponentReds = new ArrayList<Integer>();
            ArrayList<Integer> opponentYellows = new ArrayList<Integer>();
            ArrayList<Integer> opponentViolets = new ArrayList<Integer>();
            for (int i = 0; i < opponentPaletteColors.size(); i++) {
                String color = opponentPaletteColors.get(i);
                int number = opponentPaletteNumbers.get(i);
                if (color.equals("Red")) {
                    opponentReds.add(number);
                } else if (color.equals("Yellow")) {
                    opponentYellows.add(number);
                } else if (color.equals("Violet")) {
                    opponentViolets.add(number);
                }
            }
            //start with the reds
            ArrayList<Integer> maxOpponentList = opponentReds;
            String opponentMaxColor = "Red";
            //check whether the yellows are bigger
            if (opponentYellows.size() > maxOpponentList.size()) {
                maxOpponentList = opponentYellows;
                opponentMaxColor = "Yellow";
            } else if (opponentYellows.size() == maxOpponentList.size()) {
                //now check the biggest element in each
                int maxHighest = 0;
                for (int i = 0; i < maxOpponentList.size(); i++) {
                    maxHighest = Math.max(maxHighest, maxOpponentList.get(i));
                }
                int yellowHighest = 0;
                for (int i = 0; i < opponentYellows.size(); i++) {
                    yellowHighest = Math.max(yellowHighest, opponentYellows.get(i));
                }
                if (yellowHighest > maxHighest) {
                    maxOpponentList = opponentYellows;
                    opponentMaxColor = "Yellow";
                }
            }
            //now check whether the violets are bigger
            if (opponentViolets.size() > maxOpponentList.size()) {
                maxOpponentList = opponentViolets;
                opponentMaxColor = "Violet";
            } else if (opponentViolets.size() == maxOpponentList.size()) {
                //now check the biggest element in each
                int maxHighest = 0;
                for (int i = 0; i < maxOpponentList.size(); i++) {
                    maxHighest = Math.max(maxHighest, maxOpponentList.get(i));
                }
                int violetHighest = 0;
                for (int i = 0; i < opponentViolets.size(); i++) {
                    violetHighest = Math.max(violetHighest, opponentViolets.get(i));
                }
                if (violetHighest > maxHighest) {
                    maxOpponentList = opponentViolets;
                    opponentMaxColor = "Violet";
                }
            }
            
            System.out.println("In yellow rules...");
            System.out.println("maxPlayerList: " + maxPlayerList.toString());
            System.out.println("maxOpponentList: " + maxOpponentList.toString());
            
            //now compare the two biggest lists
            if (maxPlayerList.size() > maxOpponentList.size()) {
                return true;
            } else if (maxPlayerList.size() == maxOpponentList.size()) {
                int playerMaxNumber = 0;
                for (int i = 0; i < maxPlayerList.size(); i++) {
                    playerMaxNumber = Math.max(playerMaxNumber, maxPlayerList.get(i));
                }
                int opponentMaxNumber = 0;
                for (int i = 0; i < maxOpponentList.size(); i++) {
                    opponentMaxNumber = Math.max(opponentMaxNumber, maxOpponentList.get(i));
                }
                System.out.println("Same size lists, checking the sizes...");
                System.out.println("list sizes: " + maxPlayerList.size());
                System.out.println("playerMaxNumber: " + playerMaxNumber + ",  playerMaxColor: " + playerMaxColor);
                System.out.println("opponentMaxNumber: " + opponentMaxNumber + ",  opponentMaxColor: " + opponentMaxColor);
                
                if (playerMaxNumber > opponentMaxNumber) {
                    return true;
                } else if (playerMaxNumber == opponentMaxNumber) {
                    if (playerMaxColor.equals("Red") || (playerMaxColor.equals("Yellow") && opponentMaxColor.equals("Violet"))) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            //the remaining color is Violet: Most Cards Below 4
            
            //set up the player's cards that count
            ArrayList<String> playerColorsBelow4 = new ArrayList<String>();
            ArrayList<Integer> playerNumbersBelow4 = new ArrayList<Integer>();
            String playerMaxColorBelow4 = "Nothing";
            int playerMaxNumberBelow4 = 0;
            for (int i = 0; i < playerPaletteColors.size(); i++) {
                String color = playerPaletteColors.get(i);
                int number = playerPaletteNumbers.get(i);
                if (number < 4) {
                    playerColorsBelow4.add(color);
                    playerNumbersBelow4.add(number);
                    if (number > playerMaxNumberBelow4) {
                        playerMaxColorBelow4 = color;
                        playerMaxNumberBelow4 = number;
                    } else if (number == playerMaxNumberBelow4) {
                        if (color.equals("Red") || (color.equals("Yellow") && playerMaxColorBelow4.equals("Violet"))) {
                            playerMaxColorBelow4 = color;
                            playerMaxNumberBelow4 = number;
                        }
                    }
                }
            }
            
            //set up the opponent's cards that count
            ArrayList<String> opponentColorsBelow4 = new ArrayList<String>();
            ArrayList<Integer> opponentNumbersBelow4 = new ArrayList<Integer>();
            String opponentMaxColorBelow4 = "Nothing";
            int opponentMaxNumberBelow4 = 0;
            for (int i = 0; i < opponentPaletteColors.size(); i++) {
                String color = opponentPaletteColors.get(i);
                int number = opponentPaletteNumbers.get(i);
                if (number < 4) {
                    opponentColorsBelow4.add(color);
                    opponentNumbersBelow4.add(number);
                    if (number > opponentMaxNumberBelow4) {
                        opponentMaxColorBelow4 = color;
                        opponentMaxNumberBelow4 = number;
                    } else if (number == opponentMaxNumberBelow4) {
                        if (color.equals("Red") || (color.equals("Yellow") && opponentMaxColorBelow4.equals("Violet"))) {
                            opponentMaxColorBelow4 = color;
                            opponentMaxNumberBelow4 = number;
                        }
                    }
                }
            }
            
            //compare the two sets to see who wins!
            if (playerColorsBelow4.size() > opponentColorsBelow4.size()) {
                return true;
            } else if (playerColorsBelow4.size() == opponentColorsBelow4.size()) {
                if (playerMaxNumberBelow4 > opponentMaxNumberBelow4) {
                    return true;
                } else if (playerMaxNumberBelow4 == opponentMaxNumberBelow4) {
                    return (playerMaxColorBelow4.equals("Red") || (playerMaxColorBelow4.equals("Yellow") && opponentMaxColorBelow4.equals("Violet")));
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }  
    }
    
    /**
     * Clones an ArrayList.
     */
    public static <T> ArrayList<T>  cloneAL(ArrayList<T> toClone) {
        ArrayList<T> clone = new ArrayList<T>();
        for (T item : toClone) {
            clone.add(item);
        }
        return clone;
    }
    
    /**
     * Replaces an array list's contents with another.
     */
    public static <T> void replaceContentsWithAnother(ArrayList<T> toReplace, ArrayList<T> newContents) {
        toReplace.clear();
        for (T item : newContents) {
            toReplace.add(item);
        }
    }

    /**
     * Start the game.
     */
    public void start(Stage primaryStage) {
    
        primaryStage.setTitle("Red 7");
        
        //cards that are still in the deck
        boolean[] violetsInDeck = new boolean[] {true, true, true, true, true, true, true};
        boolean[] yellowsInDeck = new boolean[] {true, true, true, true, true, true, true};
        boolean[] redsInDeck = new boolean[] {true, true, true, true, true, true, true};
        
        //canvas
        String canvasColor = "Red";
        
        //playerA setup
        ArrayList<String> playerAHandColors = new ArrayList<String>();
        ArrayList<Integer> playerAHandNumbers = new ArrayList<Integer>();
        ArrayList<String> playerAPaletteColors = new ArrayList<String>();
        ArrayList<Integer> playerAPaletteNumbers = new ArrayList<Integer>();
        
        //deal cards to playerA
        //1 to the palette
        dealCard(redsInDeck, yellowsInDeck, violetsInDeck, playerAPaletteColors, playerAPaletteNumbers);
        //4 to the hand
        for (int i = 0; i < 4; i++) {
            dealCard(redsInDeck, yellowsInDeck, violetsInDeck, playerAHandColors, playerAHandNumbers);
        }
        
        //playerB setup
        ArrayList<String> playerBHandColors = new ArrayList<String>();
        ArrayList<Integer> playerBHandNumbers = new ArrayList<Integer>();
        ArrayList<String> playerBPaletteColors = new ArrayList<String>();
        ArrayList<Integer> playerBPaletteNumbers = new ArrayList<Integer>();
        
        //deal cards to playerB
        //1 to the palette
        dealCard(redsInDeck, yellowsInDeck, violetsInDeck, playerBPaletteColors, playerBPaletteNumbers);
        //4 to the hand
        for (int i = 0; i < 4; i++) {
            dealCard(redsInDeck, yellowsInDeck, violetsInDeck, playerBHandColors, playerBHandNumbers);
        }
        
        //set the first player based on who is winning.
        int currentPlayer;
        if (playerWinning(canvasColor, playerAPaletteColors, playerAPaletteNumbers, playerBPaletteColors, playerBPaletteNumbers)) {
            currentPlayer = 1;
        } else {
            currentPlayer = 0;
        }
        
        String[] players = new String[] {"A", "B"};
        String player = players[currentPlayer];
        
        System.out.println("Player " + player + " goes first!");
        
        /* */
        while (true) {
        
            displayAll(primaryStage, canvasColor, playerAPaletteColors, playerAPaletteNumbers, playerBPaletteColors, playerBPaletteNumbers, playerAHandColors, playerAHandNumbers, playerBHandColors, playerBHandNumbers);
            
            ArrayList<String> currentPlayerHandColors;
            ArrayList<Integer> currentPlayerHandNumbers;
            ArrayList<String> currentPlayerPaletteColors;
            ArrayList<Integer> currentPlayerPaletteNumbers;
            ArrayList<String> opponentHandColors;
            ArrayList<Integer> opponentHandNumbers;
            ArrayList<String> opponentPaletteColors;
            ArrayList<Integer> opponentPaletteNumbers;
            if (currentPlayer == 0) {
                currentPlayerHandColors = playerAHandColors;
                currentPlayerHandNumbers = playerAHandNumbers;
                currentPlayerPaletteColors = playerAPaletteColors;
                currentPlayerPaletteNumbers = playerAPaletteNumbers;
                opponentHandColors = playerBHandColors;
                opponentHandNumbers = playerBHandNumbers;
                opponentPaletteColors = playerBPaletteColors;
                opponentPaletteNumbers = playerBPaletteNumbers;
            } else {
                currentPlayerHandColors = playerBHandColors;
                currentPlayerHandNumbers = playerBHandNumbers;
                currentPlayerPaletteColors = playerBPaletteColors;
                currentPlayerPaletteNumbers = playerBPaletteNumbers;
                opponentHandColors = playerAHandColors;
                opponentHandNumbers = playerAHandNumbers;
                opponentPaletteColors = playerAPaletteColors;
                opponentPaletteNumbers = playerAPaletteNumbers;
            }
            
            if (currentPlayerHandColors.size() == 0) {
                break;
            }
            
            ArrayList<String> playChoices = new ArrayList<String>();
            playChoices.add("Play only to Palette");
            playChoices.add("Play only to Canvas");
            if (currentPlayerHandColors.size() > 1) {
                playChoices.add("Play to Palette and Canvas");
            }
            playChoices.add("Concede");
            ArrayList<String> choiceChosen = new ArrayList<String>();
            ChoiceDialog<String> dialog = new ChoiceDialog<String>(playChoices.get(0), playChoices);
            dialog.setTitle("Player " + player + "'s turn.");
            dialog.setHeaderText("Player " + player + ", Choose your move.");
            dialog.setContentText("Options:");
        
            System.out.println("Player " + player + "'s turn...");
            while (true) {
                dialog.showAndWait().ifPresent( (response) -> {
                    choiceChosen.add(response);
                });
                if (choiceChosen.size() == 1) {
                    break;
                }
            }
            String choice = choiceChosen.get(0);
            
            ArrayList<String> newPlayerPaletteColors = cloneAL(currentPlayerPaletteColors);
            ArrayList<Integer> newPlayerPaletteNumbers = cloneAL(currentPlayerPaletteNumbers);
            ArrayList<String> newPlayerHandColors = cloneAL(currentPlayerHandColors);
            ArrayList<Integer> newPlayerHandNumbers = cloneAL(currentPlayerHandNumbers);
            String newCanvasColor = canvasColor;
            boolean playToPalette = false;
            boolean playToCanvas = false;
            if (choice.equals("Play only to Palette")) {
                playToPalette = true;
            } else if (choice.equals("Play only to Canvas")) {
                playToCanvas = true;
            } else if (choice.equals("Play to Palette and Canvas")) {
                playToPalette = true;
                playToCanvas = true;
            } else {
                //concede
                break;
            }
            
            if (playToPalette) {
                playChoices.clear();
                choiceChosen.clear();
                for (int i = 0; i < currentPlayerHandColors.size(); i++) {
                    String color = currentPlayerHandColors.get(i);
                    int number = currentPlayerHandNumbers.get(i);
                    playChoices.add(i + ": Play " + color + " " + number + " to the palette.");
                }
                dialog = new ChoiceDialog<String>(playChoices.get(0), playChoices);
                dialog.setTitle("Palette card");
                dialog.setHeaderText("Player " + player + ", pick your card");
                dialog.setContentText("Options:");
                while (true) {
                    dialog.showAndWait().ifPresent( (response) -> {
                        choiceChosen.add(response);
                    });
                    if (choiceChosen.size() == 1) {
                        break;
                    }
                }
                choice = choiceChosen.get(0);
                int cardIndex = 0; 
                try {
                    cardIndex = Integer.parseInt(choice.substring(0, 1));
                } catch (Exception e) {
                    System.err.print("This shouldn't happen!");
                }
                String color = currentPlayerHandColors.get(cardIndex);
                int number = currentPlayerHandNumbers.get(cardIndex);
                newPlayerHandColors.remove(cardIndex);
                newPlayerHandNumbers.remove(cardIndex);
                newPlayerPaletteColors.add(color);
                newPlayerPaletteNumbers.add(number);
            }
            
            if (playToCanvas) {
                playChoices.clear();
                choiceChosen.clear();
                for (int i = 0; i < newPlayerHandColors.size(); i++) {
                    String color = newPlayerHandColors.get(i);
                    int number = newPlayerHandNumbers.get(i);
                    playChoices.add(i + ": Play " + color + " " + number + " to the canvas.");
                }
                dialog = new ChoiceDialog<String>(playChoices.get(0), playChoices);
                dialog.setTitle("Canvas card");
                dialog.setHeaderText("Player " + player + ", pick your card");
                dialog.setContentText("Options:");
                while (true) {
                    dialog.showAndWait().ifPresent( (response) -> {
                        choiceChosen.add(response);
                    });
                    if (choiceChosen.size() == 1) {
                        break;
                    }
                }
                choice = choiceChosen.get(0);
                int cardIndex = 0; 
                try {
                    cardIndex = Integer.parseInt(choice.substring(0, 1));
                } catch (Exception e) {
                    System.err.print("This shouldn't happen!");
                }
                String color = newPlayerHandColors.get(cardIndex);
                int number = newPlayerHandNumbers.get(cardIndex);
                newPlayerHandColors.remove(cardIndex);
                newPlayerHandNumbers.remove(cardIndex);
                newCanvasColor = color;
            }
                
            if (playerWinning(newCanvasColor, newPlayerPaletteColors, newPlayerPaletteNumbers, opponentPaletteColors, opponentPaletteNumbers)) {
                //great!  Apply the move!
                System.out.println("That move works!");
                replaceContentsWithAnother(currentPlayerHandColors, newPlayerHandColors);
                replaceContentsWithAnother(currentPlayerHandNumbers, newPlayerHandNumbers);
                replaceContentsWithAnother(currentPlayerPaletteColors, newPlayerPaletteColors);
                replaceContentsWithAnother(currentPlayerPaletteNumbers, newPlayerPaletteNumbers);
                canvasColor = newCanvasColor;
                
                //switch players
                currentPlayer = (currentPlayer + 1) % 2; //move to the next player
                player = players[currentPlayer];
            } else {
                System.out.println("That move doesn't work!");
            }
            
            
            
        }
        /* */
        
        System.out.println("Player " + player + " loses!");
        
        
    }
        
        /*
        
        boolean[] dealtViolets = new boolean[7];
        for (int i = 0; i < 7; i++) {
            dealtViolets[i] = false;
        }
        
        boolean[] indigosDealt = new boolean[] {false, false, false, false, false, false, false};
        
        int[] playerAHandNums = new int[2];
        int[] BHandNums = new int[2];
        int[] APaletteNums = new int[1];
        int[] playerBPaletteNumbers = new int[1];
        
        String[] AHandColors = new String[2];
        String[] playerBHandColors = new String[2];
        String[] BPaletteColors = new String[1];
        String[] playerAPaletteColors = new String[1];
        
        String canvasColor = "Red";
        
        Random randGen = new Random();
        
        boolean x = false;
        int number = -5;
        String color = "Nothing";
        while (!x) {
            number = randGen.nextInt(7) + 1;
            int cardColor = randGen.nextInt(2) + 1;
            color = "Indigo";
            if (cardColor == 1) {
                color = "Violet";
            }
            if (color.equals("Violet")) {
                x = !dealtViolets[number - 1];
            } else {
                x = !indigosDealt[number - 1];
            }
        }
        if (color.equals("Violet")) {
            dealtViolets[number - 1] = true;
        } else {
            indigosDealt[number - 1] = true;
        }
        APaletteNums[0] = number;
        playerAPaletteColors[0] = color;
        
        x=false;
        while (!x) {
            number = randGen.nextInt(7) + 1;
            int cardColor = randGen.nextInt(2) + 1;
            color = "Indigo";
            if (cardColor == 1) {
                color = "Violet";
            }
            if (color.equals("Violet")) {
                x = !dealtViolets[number - 1];
            } else {
                x = !indigosDealt[number - 1];
            }
        }
        if (color.equals("Violet")) {
            dealtViolets[number - 1] = true;
        } else {
            indigosDealt[number - 1] = true;
        }
        playerBPaletteNumbers[0] = number;
        BPaletteColors[0] = color;
        
        x=false;
        while (!x) {
            number = randGen.nextInt(7) + 1;
            int cardColor = randGen.nextInt(2) + 1;
            color = "Indigo";
            if (cardColor == 1) {
                color = "Violet";
            }
            if (color.equals("Violet")) {
                x = !dealtViolets[number - 1];
            } else {
                x = !indigosDealt[number - 1];
            }
        }
        if (color.equals("Violet")) {
            dealtViolets[number - 1] = true;
        } else {
            indigosDealt[number - 1] = true;
        }
        playerAHandNums[0] = number;
        AHandColors[0] = color;
        
        x=false;
        while (!x) {
            number = randGen.nextInt(7) + 1;
            int cardColor = randGen.nextInt(2) + 1;
            color = "Indigo";
            if (cardColor == 1) {
                color = "Violet";
            }
            if (color.equals("Violet")) {
                x = !dealtViolets[number - 1];
            } else {
                x = !indigosDealt[number - 1];
            }
        }
        if (color.equals("Violet")) {
            dealtViolets[number - 1] = true;
        } else {
            indigosDealt[number - 1] = true;
        }
        playerAHandNums[1] = number;
        AHandColors[1] = color;
        
        x=false;
        while (!x) {
            number = randGen.nextInt(7) + 1;
            int cardColor = randGen.nextInt(2) + 1;
            color = "Indigo";
            if (cardColor == 1) {
                color = "Violet";
            }
            if (color.equals("Violet")) {
                x = !dealtViolets[number - 1];
            } else {
                x = !indigosDealt[number - 1];
            }
        }
        if (color.equals("Violet")) {
            dealtViolets[number - 1] = true;
        } else {
            indigosDealt[number - 1] = true;
        }
        BHandNums[0] = number;
        playerBHandColors[0] = color;
        
        x=false;
        while (!x) {
            number = randGen.nextInt(7) + 1;
            int cardColor = randGen.nextInt(2) + 1;
            color = "Indigo";
            if (cardColor == 1) {
                color = "Violet";
            }
            if (color.equals("Violet")) {
                x = !dealtViolets[number - 1];
            } else {
                x = !indigosDealt[number - 1];
            }
        }
        if (color.equals("Violet")) {
            dealtViolets[number - 1] = true;
        } else {
            indigosDealt[number - 1] = true;
        }
        BHandNums[1] = number;
        playerBHandColors[1] = color;
        
        VBox board = new VBox();
        board.setSpacing(2);
        Text handText = new Text("Player A's Hand");
        board.getChildren().add(handText);
        Pane aHandCards = new HBox();
        board.getChildren().add(aHandCards);
        StackPane aHandCard0Pane = new StackPane();
        Rectangle aHandCard0 = new Rectangle(125, 175, Color.VIOLET);
        if (AHandColors[0].equals("Indigo")) {
            aHandCard0.setFill(Color.INDIGO);
        }
        aHandCard0Pane.getChildren().add(aHandCard0);
        Text cardNumberText = new Text("" +  playerAHandNums[0]);
        cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
        cardNumberText.setFill(Color.WHITE);
        aHandCard0Pane.getChildren().add(cardNumberText);
        aHandCards.getChildren().add(aHandCard0Pane);
        
        
        StackPane aHandCard1Pane = new StackPane();
        aHandCard0 = new Rectangle(125, 175, Color.VIOLET);
        if (AHandColors[1].equals("Indigo")) {
            aHandCard0.setFill(Color.INDIGO);
        }
        aHandCard1Pane.getChildren().add(aHandCard0);
        cardNumberText = new Text("" + playerAHandNums[1]);
        cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
        cardNumberText.setFill(Color.WHITE);
        aHandCard1Pane.getChildren().add(cardNumberText);
        aHandCards.getChildren().add(aHandCard1Pane);
        
        board.getChildren().add(new Separator());
        
        board.getChildren().add(new Text("Player A's Palette"));
        StackPane aPalette = new StackPane();
        Rectangle aPaletteR = new Rectangle(125, 175, Color.VIOLET);
        if (playerAPaletteColors[0].equals("Indigo")) {
            aPaletteR.setFill(Color.INDIGO);
        }
        aPalette.getChildren().add(aPaletteR);
        cardNumberText = new Text("" + APaletteNums[0]);
        cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
        cardNumberText.setFill(Color.WHITE);
        aPalette.getChildren().add(cardNumberText);
        board.getChildren().add(aPalette);
        
        board.getChildren().add(new Separator());
        
        board.getChildren().add(new Text("Canvas"));
        
        board.getChildren().add(new Rectangle(175, 125, Color.RED));
        
        board.getChildren().add(new Separator());
        
        board.getChildren().add(new Text("Player B's Palette"));
        StackPane bPalette = new StackPane();
        Rectangle bPaletteR = new Rectangle(125, 175, Color.VIOLET);
        if (BPaletteColors[0].equals("Indigo")) {
            bPaletteR.setFill(Color.INDIGO);
        }
        bPalette.getChildren().add(bPaletteR);
        cardNumberText = new Text("" + playerBPaletteNumbers[0]);
        cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
        cardNumberText.setFill(Color.WHITE);
        bPalette.getChildren().add(cardNumberText);
        board.getChildren().add(bPalette);
        
        board.getChildren().add(new Separator());
        
        board.getChildren().add(new Text("Player B's Hand"));
        HBox bHandCards = new HBox();
        board.getChildren().add(bHandCards);
        
        StackPane bHandCard0Pane = new StackPane();
        Rectangle bHandCard0R = new Rectangle(125, 175, Color.VIOLET);
        if (playerBHandColors[0].equals("Indigo")) {
            bHandCard0R.setFill(Color.INDIGO);
        }
        bHandCard0Pane.getChildren().add(bHandCard0R);
        cardNumberText = new Text("" + BHandNums[0]);
        cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
        cardNumberText.setFill(Color.WHITE);
        bHandCard0Pane.getChildren().add(cardNumberText);
        bHandCards.getChildren().add(bHandCard0Pane);
        
        StackPane bHandCard1Pane = new StackPane();
        Rectangle bHandCard1R = new Rectangle(125, 175, Color.VIOLET);
        if (playerBHandColors[1].equals("Indigo")) {
            bHandCard1R.setFill(Color.INDIGO);
        }
        bHandCard1Pane.getChildren().add(bHandCard1R);
        cardNumberText = new Text("" + BHandNums[1]);
        cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
        cardNumberText.setFill(Color.WHITE);
        bHandCard1Pane.getChildren().add(cardNumberText);
        bHandCards.getChildren().add(bHandCard1Pane);
        
        
        primaryStage.setScene(new Scene(board));
        primaryStage.show();
        primaryStage.sizeToScene();
        
        
        boolean firstFirst = APaletteNums[0] < playerBPaletteNumbers[0] || (APaletteNums[0] == playerBPaletteNumbers[0] && playerAPaletteColors[0].equals("Violet"));
        
        stillPlaying: while(true) {
            ArrayList<String> playChoices = new ArrayList<String>();
            ArrayList<String> choiceChosen = new ArrayList<String>();
            if (firstFirst) {
                System.out.println("Player A's turn...");
                while (true) {
                    playChoices.clear();
                    if (playerAHandNums.length > 0) {
                        playChoices.add("Play only to Palette");
                        playChoices.add("Play only to Canvas");
                    }
                    if (playerAHandNums.length > 1) {
                        playChoices.add("Play to Palette and Canvas");
                    }
                    playChoices.add("Concede");
                    choiceChosen.clear();
                    ChoiceDialog<String> dialog = new ChoiceDialog<String>(playChoices.get(0), playChoices);
                    dialog.setTitle("Player A's turn.");
                    dialog.setHeaderText("Player A, Choose your move.");
                    dialog.setContentText("Options:");
                    dialog.showAndWait().ifPresent( (response) -> {
                        choiceChosen.add(response);
                    });
                    /*
                    while (choiceChosen.size() < 1) {
                        System.out.println("Waiting...");
                    }
                    */ /*
                    String chosen = choiceChosen.get(0);
                    System.out.println("chosen: " + chosen);
                    if (chosen.equals("Concede")) {
                        System.out.println("Player A Loses!");
                        break stillPlaying;
                    } else if (chosen.equals("Play only to Palette")) {
                        ArrayList<String> handChoices = new ArrayList<String>();
                        handChoices.add("Go back");
                        for (int i = 0; i < playerAHandNums.length; i++) {
                            handChoices.add("Play card " + i + ": " + AHandColors[i] + " " + playerAHandNums[i] + " to palette");
                        }
                        
                        dialog = new ChoiceDialog<String>("Go back", handChoices);
                        dialog.setHeaderText("Which card will you move to the Palette?");
                        dialog.setContentText("Options:");
                        dialog.showAndWait().ifPresent( (response) -> {
                            choiceChosen.add(response);
                            System.out.println("response: " + response);
                        });
                        chosen = choiceChosen.get(1);
                        if (chosen.equals("Go back")) {
                            System.out.println("Backing up...");
                            continue;
                        } else if (chosen.charAt(10) == '0') {
                            String colorPick = AHandColors[0];
                            int numberPick = playerAHandNums[0];
                            int handIndex = 0;
                            boolean playOkay = false;
                            if (canvasColor.equals("Red")) {
                                String bHighestColor = BPaletteColors[0];
                                int bHighestNumber = playerBPaletteNumbers[0];
                                for (int i = 1; i < BPaletteColors.length; i++) {
                                    if (playerBPaletteNumbers[i] > bHighestNumber || (playerBPaletteNumbers[i] == bHighestNumber && BPaletteColors[i].equals("Indigo"))) {
                                        bHighestColor = BPaletteColors[i];
                                        bHighestNumber = playerBPaletteNumbers[i];
                                    }
                                }
                                String aHighestColor = colorPick;
                                int aHighestNumber = numberPick;
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    if (APaletteNums[i] > aHighestNumber || (APaletteNums[i] == aHighestNumber && playerAPaletteColors[i].equals("Indigo"))) {
                                        aHighestNumber = APaletteNums[i];
                                        aHighestColor = playerAPaletteColors[i];
                                    }
                                }
                                if (aHighestNumber > bHighestNumber || (aHighestNumber == bHighestNumber && aHighestColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                            } else if (canvasColor.equals("Indigo")) {
                                boolean[] numsB = new boolean[] {false, false, false, false, false, false, false, false};
                                boolean[] numsA = new boolean[] {false, false, false, false, false, false, false, false};
                                String[] colorsA = new String[] {"", "", "", "", "", "", "", ""};
                                int bMaxStreak = 0;
                                int bStreakTop = 0;
                                int currentStreak = 0;
                                int aMaxStreak = 0;
                                int aStreakTop = 0;
                                String aStreakTopColor = "";
                                for (int i = 0; i < playerBPaletteNumbers.length; i++) {
                                    numsB[playerBPaletteNumbers[i]] = true;
                                }
                                for (int i = 1; i < numsB.length; i++) {
                                    if (numsB[i]) {
                                        currentStreak += 1;
                                        if (currentStreak >=bMaxStreak) {
                                            bMaxStreak = currentStreak;
                                            bStreakTop = i;
                                        }
                                    } else {
                                        currentStreak = 0;
                                    }
                                }
                                for (int i = 0; i < APaletteNums.length; i++) {
                                    numsA[APaletteNums[i]] = true;
                                    if (colorsA[APaletteNums[i]].equals("") || playerAPaletteColors[i].equals("Indigo")) {
                                        colorsA[i] = playerAPaletteColors[i];
                                    }
                                }
                                numsA[numberPick] = true;
                                if (colorsA[numberPick].equals("") || colorPick.equals("Indigo")) {
                                    colorsA[numberPick] = colorPick;
                                }
                                for (int i = 1; i < numsA.length; i++) {
                                    if (numsA[i]) {
                                        currentStreak += 1;
                                        if (currentStreak >=aMaxStreak) {
                                            aMaxStreak = currentStreak;
                                            aStreakTop = i;
                                        }
                                    } else {
                                        currentStreak = 0;
                                    }
                                }
                                aStreakTopColor = colorsA[aStreakTop];
                                if (aMaxStreak > bMaxStreak || (aMaxStreak == bMaxStreak && aStreakTop > bStreakTop) || (aMaxStreak == bMaxStreak && aStreakTop == bStreakTop && aStreakTopColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                                
                            } else if (canvasColor.equals("Violet")) {
                                int numBSmall = 0;
                                int highSmallB = 0;
                                for (int i = 0; i < BPaletteColors.length; i++) {
                                    if (playerBPaletteNumbers[i] < 4) {
                                        numBSmall ++;
                                        if (playerBPaletteNumbers[i] > highSmallB) {
                                            highSmallB = playerBPaletteNumbers[i];
                                        }
                                    }
                                }
                                int numASmall = 0;
                                int highSmallA = 0;
                                String highSmallAColor = "";
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    if (APaletteNums[i] < 4) {
                                        numASmall ++;
                                        if (APaletteNums[i] > highSmallA) {
                                            highSmallA = APaletteNums[i];
                                            highSmallAColor = playerAPaletteColors[i];
                                        }
                                    }
                                }
                                if (numberPick < 4) {
                                    numASmall ++;
                                    if (numberPick > highSmallA) {
                                        highSmallA = numberPick;
                                        highSmallAColor = colorPick;
                                    }
                                }
                                if (numASmall > numBSmall || (numASmall == numBSmall && highSmallA > highSmallB) || (numASmall == numBSmall && highSmallA == highSmallB && highSmallAColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                                
                            }
                            
                            
                            if (playOkay) {
                                System.out.println("Made a legal play to the Palette!");
                                
                                String[] newAPaletteColors = new String[playerAPaletteColors.length + 1];
                                int[] newAPaletteNums = new int[APaletteNums.length + 1];
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    newAPaletteColors[i] = playerAPaletteColors[i];
                                    newAPaletteNums[i] = APaletteNums[i];
                                }
                                newAPaletteColors[newAPaletteColors.length-1] = colorPick;
                                newAPaletteNums[newAPaletteNums.length - 1] = numberPick;
                                APaletteNums = new int[newAPaletteNums.length];
                                playerAPaletteColors = new String[newAPaletteColors.length];
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    APaletteNums[i] = newAPaletteNums[i];
                                    playerAPaletteColors[i] = newAPaletteColors[i];
                                }  
                                
                                String[] newAHandColors = new String[playerAHandNums.length - 1];
                                int[] newAHandNums = new int[playerAHandNums.length - 1];
                                int j = 0;
                                for (int i = 0; i < playerAHandNums.length; i ++) {
                                    if (i != handIndex) {
                                        newAHandColors[j] = AHandColors[i];
                                        newAHandNums[j] = playerAHandNums[i];
                                        j++;
                                    }
                                }
                                AHandColors = new String[newAHandColors.length];
                                playerAHandNums = new int[newAHandNums.length];
                                for (int i = 0; i < AHandColors.length; i++) {
                                    AHandColors[i] = newAHandColors[i];
                                    playerAHandNums[i] = newAHandNums[i];
                                }
                                
                                break;
                                
                            }
                        } else if (chosen.charAt(10) == '1') {
                            String colorPick = AHandColors[1];
                            int numberPick = playerAHandNums[1];
                            int handIndex = 1;
                            boolean playOkay = false;
                            if (canvasColor.equals("Red")) {
                                String bHighestColor = BPaletteColors[0];
                                int bHighestNumber = playerBPaletteNumbers[0];
                                for (int i = 1; i < BPaletteColors.length; i++) {
                                    if (playerBPaletteNumbers[i] > bHighestNumber || (playerBPaletteNumbers[i] == bHighestNumber && BPaletteColors[i].equals("Indigo"))) {
                                        bHighestColor = BPaletteColors[i];
                                        bHighestNumber = playerBPaletteNumbers[i];
                                    }
                                }
                                String aHighestColor = colorPick;
                                int aHighestNumber = numberPick;
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    if (APaletteNums[i] > aHighestNumber || (APaletteNums[i] == aHighestNumber && playerAPaletteColors[i].equals("Indigo"))) {
                                        aHighestNumber = APaletteNums[i];
                                        aHighestColor = playerAPaletteColors[i];
                                    }
                                }
                                if (aHighestNumber > bHighestNumber || (aHighestNumber == bHighestNumber && aHighestColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                            } else if (canvasColor.equals("Indigo")) {
                                boolean[] numsB = new boolean[] {false, false, false, false, false, false, false, false};
                                boolean[] numsA = new boolean[] {false, false, false, false, false, false, false, false};
                                String[] colorsA = new String[] {"", "", "", "", "", "", "", ""};
                                int bMaxStreak = 0;
                                int bStreakTop = 0;
                                int currentStreak = 0;
                                int aMaxStreak = 0;
                                int aStreakTop = 0;
                                String aStreakTopColor = "";
                                for (int i = 0; i < playerBPaletteNumbers.length; i++) {
                                    numsB[playerBPaletteNumbers[i]] = true;
                                }
                                for (int i = 1; i < numsB.length; i++) {
                                    if (numsB[i]) {
                                        currentStreak += 1;
                                        if (currentStreak >=bMaxStreak) {
                                            bMaxStreak = currentStreak;
                                            bStreakTop = i;
                                        }
                                    } else {
                                        currentStreak = 0;
                                    }
                                }
                                for (int i = 0; i < APaletteNums.length; i++) {
                                    numsA[APaletteNums[i]] = true;
                                    if (colorsA[APaletteNums[i]].equals("") || playerAPaletteColors[i].equals("Indigo")) {
                                        colorsA[i] = playerAPaletteColors[i];
                                    }
                                }
                                numsA[numberPick] = true;
                                if (colorsA[numberPick].equals("") || colorPick.equals("Indigo")) {
                                    colorsA[numberPick] = colorPick;
                                }
                                for (int i = 1; i < numsA.length; i++) {
                                    if (numsA[i]) {
                                        currentStreak += 1;
                                        if (currentStreak >=aMaxStreak) {
                                            aMaxStreak = currentStreak;
                                            aStreakTop = i;
                                        }
                                    } else {
                                        currentStreak = 0;
                                    }
                                }
                                aStreakTopColor = colorsA[aStreakTop];
                                if (aMaxStreak > bMaxStreak || (aMaxStreak == bMaxStreak && aStreakTop > bStreakTop) || (aMaxStreak == bMaxStreak && aStreakTop == bStreakTop && aStreakTopColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                                
                            } else if (canvasColor.equals("Violet")) {
                                int numBSmall = 0;
                                int highSmallB = 0;
                                for (int i = 0; i < BPaletteColors.length; i++) {
                                    if (playerBPaletteNumbers[i] < 4) {
                                        numBSmall ++;
                                        if (playerBPaletteNumbers[i] > highSmallB) {
                                            highSmallB = playerBPaletteNumbers[i];
                                        }
                                    }
                                }
                                int numASmall = 0;
                                int highSmallA = 0;
                                String highSmallAColor = "";
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    if (APaletteNums[i] < 4) {
                                        numASmall ++;
                                        if (APaletteNums[i] > highSmallA) {
                                            highSmallA = APaletteNums[i];
                                            highSmallAColor = playerAPaletteColors[i];
                                        }
                                    }
                                }
                                if (numberPick < 4) {
                                    numASmall ++;
                                    if (numberPick > highSmallA) {
                                        highSmallA = numberPick;
                                        highSmallAColor = colorPick;
                                    }
                                }
                                if (numASmall > numBSmall || (numASmall == numBSmall && highSmallA > highSmallB) || (numASmall == numBSmall && highSmallA == highSmallB && highSmallAColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                            }
                            
                            
                            if (playOkay) {
                                System.out.println("Made a legal play to the Palette!");
                                
                                String[] newAPaletteColors = new String[playerAPaletteColors.length + 1];
                                int[] newAPaletteNums = new int[APaletteNums.length + 1];
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    newAPaletteColors[i] = playerAPaletteColors[i];
                                    newAPaletteNums[i] = APaletteNums[i];
                                }
                                newAPaletteColors[newAPaletteColors.length-1] = colorPick;
                                newAPaletteNums[newAPaletteNums.length - 1] = numberPick;
                                APaletteNums = new int[newAPaletteNums.length];
                                playerAPaletteColors = new String[newAPaletteColors.length];
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    APaletteNums[i] = newAPaletteNums[i];
                                    playerAPaletteColors[i] = newAPaletteColors[i];
                                }  
                                
                                String[] newAHandColors = new String[playerAHandNums.length - 1];
                                int[] newAHandNums = new int[playerAHandNums.length - 1];
                                int j = 0;
                                for (int i = 0; i < playerAHandNums.length; i ++) {
                                    if (i != handIndex) {
                                        newAHandColors[j] = AHandColors[i];
                                        newAHandNums[j] = playerAHandNums[i];
                                        j++;
                                    }
                                }
                                AHandColors = new String[newAHandColors.length];
                                playerAHandNums = new int[newAHandNums.length];
                                for (int i = 0; i < AHandColors.length; i++) {
                                    AHandColors[i] = newAHandColors[i];
                                    playerAHandNums[i] = newAHandNums[i];
                                }
                                
                                break;
                            }
                        }
                        
                        System.out.println("That move did not help you to win.  Let's try that again...  (canvasColor: " + canvasColor + ")");
                        
                        
                        
                    } else if (chosen.equals("Play only to Canvas")) {
                        ArrayList<String> handChoices = new ArrayList<String>();
                        handChoices.add("Go back");
                        for (int i = 0; i < playerAHandNums.length; i++) {
                            handChoices.add("Play card " + i + ": " + AHandColors[i] + " " + playerAHandNums[i] + " to canvas");
                        }
                        
                        dialog = new ChoiceDialog<String>("Go back", handChoices);
                        dialog.setHeaderText("Which card will you move to the Canvas?");
                        dialog.setContentText("Options:");
                        dialog.showAndWait().ifPresent( (response) -> {
                            choiceChosen.add(response);
                            System.out.println("response: " + response);
                        });
                        chosen = choiceChosen.get(1);
                        if (chosen.equals("Go back")) {
                            System.out.println("Backing up...");
                            continue;
                        } else if (chosen.charAt(10) == '0') {
                            String colorPick = AHandColors[0];
                            int numberPick = playerAHandNums[0];
                            int handIndex = 0;
                            boolean playOkay = false;
                            if (colorPick.equals("Red")) {
                                String bHighestColor = BPaletteColors[0];
                                int bHighestNumber = playerBPaletteNumbers[0];
                                for (int i = 1; i < BPaletteColors.length; i++) {
                                    if (playerBPaletteNumbers[i] > bHighestNumber || (playerBPaletteNumbers[i] == bHighestNumber && BPaletteColors[i].equals("Indigo"))) {
                                        bHighestColor = BPaletteColors[i];
                                        bHighestNumber = playerBPaletteNumbers[i];
                                    }
                                }
                                String aHighestColor = "";
                                int aHighestNumber = 0;
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    if (APaletteNums[i] > aHighestNumber || (APaletteNums[i] == aHighestNumber && playerAPaletteColors[i].equals("Indigo"))) {
                                        aHighestNumber = APaletteNums[i];
                                        aHighestColor = playerAPaletteColors[i];
                                    }
                                }
                                if (aHighestNumber > bHighestNumber || (aHighestNumber == bHighestNumber && aHighestColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                            } else if (colorPick.equals("Indigo")) {
                                boolean[] numsB = new boolean[] {false, false, false, false, false, false, false, false};
                                boolean[] numsA = new boolean[] {false, false, false, false, false, false, false, false};
                                String[] colorsA = new String[] {"", "", "", "", "", "", "", ""};
                                int bMaxStreak = 0;
                                int bStreakTop = 0;
                                int currentStreak = 0;
                                int aMaxStreak = 0;
                                int aStreakTop = 0;
                                String aStreakTopColor = "";
                                for (int i = 0; i < playerBPaletteNumbers.length; i++) {
                                    numsB[playerBPaletteNumbers[i]] = true;
                                }
                                for (int i = 1; i < numsB.length; i++) {
                                    if (numsB[i]) {
                                        currentStreak += 1;
                                        if (currentStreak >=bMaxStreak) {
                                            bMaxStreak = currentStreak;
                                            bStreakTop = i;
                                        }
                                    } else {
                                        currentStreak = 0;
                                    }
                                }
                                for (int i = 0; i < APaletteNums.length; i++) {
                                    numsA[APaletteNums[i]] = true;
                                    if (colorsA[APaletteNums[i]].equals("") || playerAPaletteColors[i].equals("Indigo")) {
                                        colorsA[i] = playerAPaletteColors[i];
                                    }
                                }
                                for (int i = 1; i < numsA.length; i++) {
                                    if (numsA[i]) {
                                        currentStreak += 1;
                                        if (currentStreak >=aMaxStreak) {
                                            aMaxStreak = currentStreak;
                                            aStreakTop = i;
                                        }
                                    } else {
                                        currentStreak = 0;
                                    }
                                }
                                aStreakTopColor = colorsA[aStreakTop];
                                if (aMaxStreak > bMaxStreak || (aMaxStreak == bMaxStreak && aStreakTop > bStreakTop) || (aMaxStreak == bMaxStreak && aStreakTop == bStreakTop && aStreakTopColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                                
                            } else if (colorPick.equals("Violet")) {
                                int numBSmall = 0;
                                int highSmallB = 0;
                                for (int i = 0; i < BPaletteColors.length; i++) {
                                    if (playerBPaletteNumbers[i] < 4) {
                                        numBSmall ++;
                                        if (playerBPaletteNumbers[i] > highSmallB) {
                                            highSmallB = playerBPaletteNumbers[i];
                                        }
                                    }
                                }
                                int numASmall = 0;
                                int highSmallA = 0;
                                String highSmallAColor = "";
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    if (APaletteNums[i] < 4) {
                                        numASmall ++;
                                        if (APaletteNums[i] > highSmallA) {
                                            highSmallA = APaletteNums[i];
                                            highSmallAColor = playerAPaletteColors[i];
                                        }
                                    }
                                }
                                if (numASmall > numBSmall || (numASmall == numBSmall && highSmallA > highSmallB) || (numASmall == numBSmall && highSmallA == highSmallB && highSmallAColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                                
                            }
                            
                            
                            if (playOkay) {
                                System.out.println("Made a legal play to the canvas!");
                                
                                canvasColor = colorPick;
                                
                                /*
                                String[] newAPaletteColors = new String[playerAPaletteColors.length + 1];
                                int[] newAPaletteNums = new int[APaletteNums.length + 1];
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    newAPaletteColors[i] = playerAPaletteColors[i];
                                    newAPaletteNums[i] = APaletteNums[i];
                                }
                                newAPaletteColors[newAPaletteColors.length-1] = colorPick;
                                newAPaletteNums[newAPaletteNums.length - 1] = numberPick;
                                APaletteNums = new int[newAPaletteNums.length];
                                playerAPaletteColors = new String[newAPaletteColors.length];
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    APaletteNums[i] = newAPaletteNums[i];
                                    playerAPaletteColors[i] = newAPaletteColors[i];
                                } */ 
                                /*
                                
                                String[] newAHandColors = new String[playerAHandNums.length - 1];
                                int[] newAHandNums = new int[playerAHandNums.length - 1];
                                int j = 0;
                                for (int i = 0; i < playerAHandNums.length; i ++) {
                                    if (i != handIndex) {
                                        newAHandColors[j] = AHandColors[i];
                                        newAHandNums[j] = playerAHandNums[i];
                                        j++;
                                    }
                                }
                                AHandColors = new String[newAHandColors.length];
                                playerAHandNums = new int[newAHandNums.length];
                                for (int i = 0; i < AHandColors.length; i++) {
                                    AHandColors[i] = newAHandColors[i];
                                    playerAHandNums[i] = newAHandNums[i];
                                }
                                
                                break;
                                
                            }
                        } else if (chosen.charAt(10) == '1') {
                            String colorPick = AHandColors[1];
                            int numberPick = playerAHandNums[1];
                            int handIndex = 1;
                            boolean playOkay = false;
                            if (canvasColor.equals("Red")) {
                                String bHighestColor = BPaletteColors[0];
                                int bHighestNumber = playerBPaletteNumbers[0];
                                for (int i = 1; i < BPaletteColors.length; i++) {
                                    if (playerBPaletteNumbers[i] > bHighestNumber || (playerBPaletteNumbers[i] == bHighestNumber && BPaletteColors[i].equals("Indigo"))) {
                                        bHighestColor = BPaletteColors[i];
                                        bHighestNumber = playerBPaletteNumbers[i];
                                    }
                                }
                                String aHighestColor = colorPick;
                                int aHighestNumber = numberPick;
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    if (APaletteNums[i] > aHighestNumber || (APaletteNums[i] == aHighestNumber && playerAPaletteColors[i].equals("Indigo"))) {
                                        aHighestNumber = APaletteNums[i];
                                        aHighestColor = playerAPaletteColors[i];
                                    }
                                }
                                if (aHighestNumber > bHighestNumber || (aHighestNumber == bHighestNumber && aHighestColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                            } else if (canvasColor.equals("Indigo")) {
                                boolean[] numsB = new boolean[] {false, false, false, false, false, false, false, false};
                                boolean[] numsA = new boolean[] {false, false, false, false, false, false, false, false};
                                String[] colorsA = new String[] {"", "", "", "", "", "", "", ""};
                                int bMaxStreak = 0;
                                int bStreakTop = 0;
                                int currentStreak = 0;
                                int aMaxStreak = 0;
                                int aStreakTop = 0;
                                String aStreakTopColor = "";
                                for (int i = 0; i < playerBPaletteNumbers.length; i++) {
                                    numsB[playerBPaletteNumbers[i]] = true;
                                }
                                for (int i = 1; i < numsB.length; i++) {
                                    if (numsB[i]) {
                                        currentStreak += 1;
                                        if (currentStreak >=bMaxStreak) {
                                            bMaxStreak = currentStreak;
                                            bStreakTop = i;
                                        }
                                    } else {
                                        currentStreak = 0;
                                    }
                                }
                                for (int i = 0; i < APaletteNums.length; i++) {
                                    numsA[APaletteNums[i]] = true;
                                    if (colorsA[APaletteNums[i]].equals("") || playerAPaletteColors[i].equals("Indigo")) {
                                        colorsA[i] = playerAPaletteColors[i];
                                    }
                                }
                                for (int i = 1; i < numsA.length; i++) {
                                    if (numsA[i]) {
                                        currentStreak += 1;
                                        if (currentStreak >=aMaxStreak) {
                                            aMaxStreak = currentStreak;
                                            aStreakTop = i;
                                        }
                                    } else {
                                        currentStreak = 0;
                                    }
                                }
                                aStreakTopColor = colorsA[aStreakTop];
                                if (aMaxStreak > bMaxStreak || (aMaxStreak == bMaxStreak && aStreakTop > bStreakTop) || (aMaxStreak == bMaxStreak && aStreakTop == bStreakTop && aStreakTopColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                                
                            } else if (canvasColor.equals("Violet")) {
                                int numBSmall = 0;
                                int highSmallB = 0;
                                for (int i = 0; i < BPaletteColors.length; i++) {
                                    if (playerBPaletteNumbers[i] < 4) {
                                        numBSmall ++;
                                        if (playerBPaletteNumbers[i] > highSmallB) {
                                            highSmallB = playerBPaletteNumbers[i];
                                        }
                                    }
                                }
                                int numASmall = 0;
                                int highSmallA = 0;
                                String highSmallAColor = "";
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    if (APaletteNums[i] < 4) {
                                        numASmall ++;
                                        if (APaletteNums[i] > highSmallA) {
                                            highSmallA = APaletteNums[i];
                                            highSmallAColor = playerAPaletteColors[i];
                                        }
                                    }
                                }
                                if (numberPick < 4) {
                                    numASmall ++;
                                    if (numberPick > highSmallA) {
                                        highSmallA = numberPick;
                                        highSmallAColor = colorPick;
                                    }
                                }
                                if (numASmall > numBSmall || (numASmall == numBSmall && highSmallA > highSmallB) || (numASmall == numBSmall && highSmallA == highSmallB && highSmallAColor.equals("Indigo"))) {
                                    playOkay = true;
                                }
                            }
                            
                            
                            if (playOkay) {
                                System.out.println("Made a legal play to the canvas!");
                                
                                canvasColor = colorPick;
                                /*
                                
                                String[] newAPaletteColors = new String[playerAPaletteColors.length + 1];
                                int[] newAPaletteNums = new int[APaletteNums.length + 1];
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    newAPaletteColors[i] = playerAPaletteColors[i];
                                    newAPaletteNums[i] = APaletteNums[i];
                                }
                                newAPaletteColors[newAPaletteColors.length-1] = colorPick;
                                newAPaletteNums[newAPaletteNums.length - 1] = numberPick;
                                APaletteNums = new int[newAPaletteNums.length];
                                playerAPaletteColors = new String[newAPaletteColors.length];
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    APaletteNums[i] = newAPaletteNums[i];
                                    playerAPaletteColors[i] = newAPaletteColors[i];
                                }  */
                                /*
                                String[] newAHandColors = new String[playerAHandNums.length - 1];
                                int[] newAHandNums = new int[playerAHandNums.length - 1];
                                int j = 0;
                                for (int i = 0; i < playerAHandNums.length; i ++) {
                                    if (i != handIndex) {
                                        newAHandColors[j] = AHandColors[i];
                                        newAHandNums[j] = playerAHandNums[i];
                                        j++;
                                    }
                                }
                                AHandColors = new String[newAHandColors.length];
                                playerAHandNums = new int[newAHandNums.length];
                                for (int i = 0; i < AHandColors.length; i++) {
                                    AHandColors[i] = newAHandColors[i];
                                    playerAHandNums[i] = newAHandNums[i];
                                }
                                
                                break;
                            }
                        }
                        
                        System.out.println("That move did not help you to win.  Let's try that again...  (canvasColor: " + canvasColor + ")");
                        
                        
                        
                    } else if (chosen.equals("Play to Palette and Canvas")) {
                        ArrayList<String> handChoices = new ArrayList<String>();
                        handChoices.add("Go back");
                        for (int i = 0; i < playerAHandNums.length; i++) {
                            handChoices.add("Play card " + i + ": " + AHandColors[i] + " " + playerAHandNums[i] + " to palette first...");
                        }
                        
                        dialog = new ChoiceDialog<String>("Go back", handChoices);
                        dialog.setHeaderText("First choose for the Palette.");
                        dialog.setContentText("Options:");
                        dialog.showAndWait().ifPresent( (response) -> {
                            choiceChosen.add(response);
                            System.out.println("response: " + response);
                        });
                        chosen = choiceChosen.get(1);
                        if (chosen.equals("Go back")) {
                            System.out.println("Backing up...");
                            continue;
                        } else {
                            boolean playOkay = false;
                            int hand2PaletteIndex = 0;
                            try {
                                hand2PaletteIndex = Integer.parseInt(chosen.charAt(10) + "");
                            } catch (Exception e) {
                                System.err.println("Ooops!  Couldn't get a character!");
                            }
                            String colorPick = AHandColors[hand2PaletteIndex];
                            int numberPick = playerAHandNums[hand2PaletteIndex];
                            handChoices.clear();
                            handChoices.add("Go back");
                            for (int i = 0; i < playerAHandNums.length; i++) {
                                if (i != hand2PaletteIndex) {
                                    handChoices.add("Play card " + i + ": " + AHandColors[i] + " " + playerAHandNums[i] + " to the canvas second.");
                                }
                            }
                        
                            dialog = new ChoiceDialog<String>("Go back", handChoices);
                            dialog.setHeaderText("Next, choose for the canvas.");
                            dialog.setContentText("Options:");
                            dialog.showAndWait().ifPresent( (response) -> {
                                choiceChosen.add(response);
                                System.out.println("response: " + response);
                            });
                            chosen = choiceChosen.get(2);
                            if (chosen.equals("Go back")) {
                                System.out.println("Backing up...");
                                continue;
                            } else {
                                int hand2CanvasIndex = 0;
                                try {
                                    hand2CanvasIndex = Integer.parseInt(chosen.charAt(10) + "");
                                } catch (Exception e) {
                                    System.err.println("Couldn't get a character when looking for the canvas card!");
                                }
                                String canvasColorPick = AHandColors[hand2CanvasIndex];
                                
                                if (canvasColorPick.equals("Red")) {
                                    String bHighestColor = BPaletteColors[0];
                                    int bHighestNumber = playerBPaletteNumbers[0];
                                    for (int i = 1; i < BPaletteColors.length; i++) {
                                        if (playerBPaletteNumbers[i] > bHighestNumber || (playerBPaletteNumbers[i] == bHighestNumber && BPaletteColors[i].equals("Indigo"))) {
                                            bHighestColor = BPaletteColors[i];
                                            bHighestNumber = playerBPaletteNumbers[i];
                                        }
                                    }
                                    String aHighestColor = colorPick;
                                    int aHighestNumber = numberPick;
                                    for (int i = 0; i < playerAPaletteColors.length; i++) {
                                        if (APaletteNums[i] > aHighestNumber || (APaletteNums[i] == aHighestNumber && playerAPaletteColors[i].equals("Indigo"))) {
                                            aHighestNumber = APaletteNums[i];
                                            aHighestColor = playerAPaletteColors[i];
                                        }
                                    }
                                    if (aHighestNumber > bHighestNumber || (aHighestNumber == bHighestNumber && aHighestColor.equals("Indigo"))) {
                                        playOkay = true;
                                    }
                                } else if (canvasColorPick.equals("Indigo")) {
                                    boolean[] numsB = new boolean[] {false, false, false, false, false, false, false, false};
                                    boolean[] numsA = new boolean[] {false, false, false, false, false, false, false, false};
                                    String[] colorsA = new String[] {"", "", "", "", "", "", "", ""};
                                    int bMaxStreak = 0;
                                    int bStreakTop = 0;
                                    int currentStreak = 0;
                                    int aMaxStreak = 0;
                                    int aStreakTop = 0;
                                    String aStreakTopColor = "";
                                    for (int i = 0; i < playerBPaletteNumbers.length; i++) {
                                        numsB[playerBPaletteNumbers[i]] = true;
                                    }
                                    for (int i = 1; i < numsB.length; i++) {
                                        if (numsB[i]) {
                                            currentStreak += 1;
                                            if (currentStreak >=bMaxStreak) {
                                                bMaxStreak = currentStreak;
                                                bStreakTop = i;
                                            }
                                        } else {
                                            currentStreak = 0;
                                        }
                                    }
                                    numsA[numberPick] = true;
                                    colorsA[numberPick] = colorPick;
                                    for (int i = 0; i < APaletteNums.length; i++) {
                                        numsA[APaletteNums[i]] = true;
                                        if (colorsA[APaletteNums[i]].equals("") || playerAPaletteColors[i].equals("Indigo")) {
                                            colorsA[i] = playerAPaletteColors[i];
                                        }
                                    }
                                    for (int i = 1; i < numsA.length; i++) {
                                        if (numsA[i]) {
                                            currentStreak += 1;
                                            if (currentStreak >=aMaxStreak) {
                                                aMaxStreak = currentStreak;
                                                aStreakTop = i;
                                            }
                                        } else {
                                            currentStreak = 0;
                                        }
                                    }
                                    aStreakTopColor = colorsA[aStreakTop];
                                    if (aMaxStreak > bMaxStreak || (aMaxStreak == bMaxStreak && aStreakTop > bStreakTop) || (aMaxStreak == bMaxStreak && aStreakTop == bStreakTop && aStreakTopColor.equals("Indigo"))) {
                                        playOkay = true;
                                    }
                                    
                                } else if (canvasColorPick.equals("Violet")) {
                                    int numBSmall = 0;
                                    int highSmallB = 0;
                                    for (int i = 0; i < BPaletteColors.length; i++) {
                                        if (playerBPaletteNumbers[i] < 4) {
                                            numBSmall ++;
                                            if (playerBPaletteNumbers[i] > highSmallB) {
                                                highSmallB = playerBPaletteNumbers[i];
                                            }
                                        }
                                    }
                                    int numASmall = 0;
                                    int highSmallA = 0;
                                    String highSmallAColor = "";
                                    if (numberPick < 4) {
                                        numASmall ++;
                                        highSmallA = numberPick;
                                        highSmallAColor = colorPick;
                                    }
                                    for (int i = 0; i < playerAPaletteColors.length; i++) {
                                        if (APaletteNums[i] < 4) {
                                            numASmall ++;
                                            if (APaletteNums[i] > highSmallA) {
                                                highSmallA = APaletteNums[i];
                                                highSmallAColor = playerAPaletteColors[i];
                                            }
                                        }
                                    }
                                    if (numASmall > numBSmall || (numASmall == numBSmall && highSmallA > highSmallB) || (numASmall == numBSmall && highSmallA == highSmallB && highSmallAColor.equals("Indigo"))) {
                                        playOkay = true;
                                    }
                                    
                                }
                            
                            
                                if (playOkay) {
                                    System.out.println("Made a legal play to both the palette and canvas!");
                                    
                                    canvasColor = canvasColorPick;
                                    
                                    String[] newAPaletteColors = new String[playerAPaletteColors.length + 1];
                                    int[] newAPaletteNums = new int[APaletteNums.length + 1];
                                    for (int i = 0; i < playerAPaletteColors.length; i++) {
                                        newAPaletteColors[i] = playerAPaletteColors[i];
                                        newAPaletteNums[i] = APaletteNums[i];
                                    }
                                    newAPaletteColors[newAPaletteColors.length-1] = colorPick;
                                    newAPaletteNums[newAPaletteNums.length - 1] = numberPick;
                                    APaletteNums = new int[newAPaletteNums.length];
                                    playerAPaletteColors = new String[newAPaletteColors.length];
                                    for (int i = 0; i < playerAPaletteColors.length; i++) {
                                        APaletteNums[i] = newAPaletteNums[i];
                                        playerAPaletteColors[i] = newAPaletteColors[i];
                                    } 
                                    
                                    AHandColors = new String[0];
                                    playerAHandNums = new int[0];
                                    
                                    break;
                                    
                                }
                            }
                        }
                        
                        System.out.println("That move did not help you to win.  Let's try that again...  (canvasColor: " + canvasColor + ")");
                    }
                }
                
            }
            
            board = new VBox();
            board.setSpacing(2);
            handText = new Text("Player A's Hand");
            board.getChildren().add(handText);
            aHandCards = new HBox();
            board.getChildren().add(aHandCards);
            for (int i = 0; i < AHandColors.length; i++) {
                StackPane aHandCardPane = new StackPane();
                Rectangle aHandCardR = new Rectangle(125, 175, Color.VIOLET);
                if (AHandColors[i].equals("Indigo")) {
                    aHandCardR.setFill(Color.INDIGO);
                }
                aHandCardPane.getChildren().add(aHandCardR);
                cardNumberText = new Text("" +  playerAHandNums[i]);
                cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
                cardNumberText.setFill(Color.WHITE);
                aHandCardPane.getChildren().add(cardNumberText);
                aHandCards.getChildren().add(aHandCardPane);
            }
            
            board.getChildren().add(new Separator());
            
            board.getChildren().add(new Text("Player A's Palette"));
            
            HBox aPaletteCards = new HBox();
            board.getChildren().add(aPaletteCards);
            for (int i = 0; i < playerAPaletteColors.length; i++) {
                aPalette = new StackPane();
                aPaletteR = new Rectangle(125, 175, Color.VIOLET);
                if (playerAPaletteColors[i].equals("Indigo")) {
                    aPaletteR.setFill(Color.INDIGO);
                }
                aPalette.getChildren().add(aPaletteR);
                cardNumberText = new Text("" + APaletteNums[i]);
                cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
                cardNumberText.setFill(Color.WHITE);
                aPalette.getChildren().add(cardNumberText);
                aPaletteCards.getChildren().add(aPalette);
            }
            
            board.getChildren().add(new Separator());
            
            board.getChildren().add(new Text("Canvas"));
            
            if (canvasColor.equals("Red")) {
                board.getChildren().add(new Rectangle(175, 125, Color.RED));
            } else if (canvasColor.equals("Indigo")) {
                board.getChildren().add(new Rectangle(175, 125, Color.INDIGO));
            } else  if (canvasColor.equals("Violet")) {
                board.getChildren().add(new Rectangle(175, 125, Color.VIOLET));
            }
            
            board.getChildren().add(new Separator());
            
            board.getChildren().add(new Text("Player B's Palette"));
            
            HBox bPaletteCards = new HBox();
            board.getChildren().add(bPaletteCards);
            for (int i = 0; i < BPaletteColors.length; i++) {
                bPalette = new StackPane();
                bPaletteR = new Rectangle(125, 175, Color.VIOLET);
                if (BPaletteColors[i].equals("Indigo")) {
                    bPaletteR.setFill(Color.INDIGO);
                }
                bPalette.getChildren().add(bPaletteR);
                cardNumberText = new Text("" + playerBPaletteNumbers[i]);
                cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
                cardNumberText.setFill(Color.WHITE);
                bPalette.getChildren().add(cardNumberText);
                bPaletteCards.getChildren().add(bPalette);
            }
            
            board.getChildren().add(new Separator());
            
            board.getChildren().add(new Text("Player B's Hand"));
            bHandCards = new HBox();
            board.getChildren().add(bHandCards);
            
            for (int i = 0; i < playerBHandColors.length; i++) {
                StackPane bHandCardPane = new StackPane();
                Rectangle bHandCardR = new Rectangle(125, 175, Color.VIOLET);
                if (playerBHandColors[i].equals("Indigo")) {
                    bHandCardR.setFill(Color.INDIGO);
                }
                bHandCardPane.getChildren().add(bHandCardR);
                cardNumberText = new Text("" + BHandNums[i]);
                cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
                cardNumberText.setFill(Color.WHITE);
                bHandCardPane.getChildren().add(cardNumberText);
                bHandCards.getChildren().add(bHandCardPane);
            }
            
            
            primaryStage.setScene(new Scene(board));
            primaryStage.show();
            primaryStage.sizeToScene();
            
            firstFirst = true;
            
            System.out.println("Player B's turn...");
            
            while (true) {
                playChoices.clear();
                if (BHandNums.length > 0) {
                    playChoices.add("Play only to Palette");
                    playChoices.add("Play only to Canvas");
                }
                if (BHandNums.length > 1) {
                    playChoices.add("Play to Palette and Canvas");
                }
                playChoices.add("Concede");
                choiceChosen.clear();
                ChoiceDialog<String> dialog = new ChoiceDialog<String>(playChoices.get(0), playChoices);
                dialog.setTitle("Player B's turn.");
                dialog.setHeaderText("Player B, Choose your move.");
                dialog.setContentText("Options:");
                dialog.showAndWait().ifPresent( (response) -> {
                    choiceChosen.add(response);
                });
                /*
                while (choiceChosen.size() < 1) {
                    System.out.println("Waiting...");
                }
                */
                /*
                String chosen = choiceChosen.get(0);
                System.out.println("chosen: " + chosen);
                if (chosen.equals("Concede")) {
                    System.out.println("Player B Loses!");
                    break stillPlaying;
                } else if (chosen.equals("Play only to Palette")) {
                    ArrayList<String> handChoices = new ArrayList<String>();
                    handChoices.add("Go back");
                    for (int i = 0; i < BHandNums.length; i++) {
                        handChoices.add("Play card " + i + ": " + playerBHandColors[i] + " " + BHandNums[i] + " to palette");
                    }
                    
                    dialog = new ChoiceDialog<String>("Go back", handChoices);
                    dialog.setHeaderText("Which card will you move to the Palette?");
                    dialog.setContentText("Options:");
                    dialog.showAndWait().ifPresent( (response) -> {
                        choiceChosen.add(response);
                        System.out.println("response: " + response);
                    });
                    chosen = choiceChosen.get(1);
                    if (chosen.equals("Go back")) {
                        System.out.println("Backing up...");
                        continue;
                    } else {
                        int handIndex = 0;
                        try {
                            handIndex = Integer.parseInt(chosen.charAt(10) + "");
                        } catch (Exception e) {
                            System.err.println("Couldn't parse the integer for player B choosing a hand card!");
                        }
                        String colorPick = playerBHandColors[handIndex];
                        int numberPick = BHandNums[handIndex];
                        boolean playOkay = false;
                        if (canvasColor.equals("Red")) {
                            String bHighestColor = colorPick;
                            int bHighestNumber = numberPick;
                            for (int i = 0; i < BPaletteColors.length; i++) {
                                if (playerBPaletteNumbers[i] > bHighestNumber || (playerBPaletteNumbers[i] == bHighestNumber && BPaletteColors[i].equals("Indigo"))) {
                                    bHighestColor = BPaletteColors[i];
                                    bHighestNumber = playerBPaletteNumbers[i];
                                }
                            }
                            String aHighestColor = playerAPaletteColors[0];
                            int aHighestNumber = APaletteNums[0];
                            for (int i = 0; i < playerAPaletteColors.length; i++) {
                                if (APaletteNums[i] > aHighestNumber || (APaletteNums[i] == aHighestNumber && playerAPaletteColors[i].equals("Indigo"))) {
                                    aHighestNumber = APaletteNums[i];
                                    aHighestColor = playerAPaletteColors[i];
                                }
                            }
                            if (aHighestNumber < bHighestNumber || (aHighestNumber == bHighestNumber && aHighestColor.equals("Violet"))) {
                                playOkay = true;
                            }
                        } else if (canvasColor.equals("Indigo")) {
                            boolean[] numsB = new boolean[] {false, false, false, false, false, false, false, false};
                            boolean[] numsA = new boolean[] {false, false, false, false, false, false, false, false};
                            String[] colorsA = new String[] {"", "", "", "", "", "", "", ""};
                            int bMaxStreak = 0;
                            int bStreakTop = 0;
                            int currentStreak = 0;
                            int aMaxStreak = 0;
                            int aStreakTop = 0;
                            String aStreakTopColor = "";
                            numsB[numberPick] = true;
                            for (int i = 0; i < playerBPaletteNumbers.length; i++) {
                                numsB[playerBPaletteNumbers[i]] = true;
                            }
                            for (int i = 1; i < numsB.length; i++) {
                                if (numsB[i]) {
                                    currentStreak += 1;
                                    if (currentStreak >= bMaxStreak) {
                                        bMaxStreak = currentStreak;
                                        bStreakTop = i;
                                    }
                                } else {
                                    currentStreak = 0;
                                }
                            }
                            for (int i = 0; i < APaletteNums.length; i++) {
                                numsA[APaletteNums[i]] = true;
                                if (colorsA[APaletteNums[i]].equals("") || playerAPaletteColors[i].equals("Indigo")) {
                                    colorsA[i] = playerAPaletteColors[i];
                                }
                            }
                            for (int i = 1; i < numsA.length; i++) {
                                if (numsA[i]) {
                                    currentStreak += 1;
                                    if (currentStreak >= aMaxStreak) {
                                        aMaxStreak = currentStreak;
                                        aStreakTop = i;
                                    }
                                } else {
                                    currentStreak = 0;
                                }
                            }
                            aStreakTopColor = colorsA[aStreakTop];
                            if (aMaxStreak < bMaxStreak || (aMaxStreak == bMaxStreak && aStreakTop < bStreakTop) || (aMaxStreak == bMaxStreak && aStreakTop == bStreakTop && aStreakTopColor.equals("Violet"))) {
                                playOkay = true;
                            }
                            
                        } else if (canvasColor.equals("Violet")) {
                            int numBSmall = 0;
                            int highSmallB = 0;
                            if (numberPick < 4) {
                                numBSmall ++;
                                highSmallB = numberPick;
                            }
                            for (int i = 0; i < BPaletteColors.length; i++) {
                                if (playerBPaletteNumbers[i] < 4) {
                                    numBSmall ++;
                                    if (playerBPaletteNumbers[i] > highSmallB) {
                                        highSmallB = playerBPaletteNumbers[i];
                                    }
                                }
                            }
                            int numASmall = 0;
                            int highSmallA = 0;
                            String highSmallAColor = "";
                            for (int i = 0; i < playerAPaletteColors.length; i++) {
                                if (APaletteNums[i] < 4) {
                                    numASmall ++;
                                    if (APaletteNums[i] > highSmallA) {
                                        highSmallA = APaletteNums[i];
                                        highSmallAColor = playerAPaletteColors[i];
                                    }
                                }
                            }
                            if (numASmall < numBSmall || (numASmall == numBSmall && highSmallA < highSmallB) || (numASmall == numBSmall && highSmallA == highSmallB && highSmallAColor.equals("Violet"))) {
                                playOkay = true;
                            }
                            
                        }
                        
                        
                        if (playOkay) {
                            System.out.println("Made a legal play to the Palette!");
                            
                            String[] newBPaletteColors = new String[BPaletteColors.length + 1];
                            int[] newBPaletteNums = new int[playerBPaletteNumbers.length + 1];
                            for (int i = 0; i < BPaletteColors.length; i++) {
                                newBPaletteColors[i] = BPaletteColors[i];
                                newBPaletteNums[i] = playerBPaletteNumbers[i];
                            }
                            newBPaletteColors[newBPaletteColors.length-1] = colorPick;
                            newBPaletteNums[newBPaletteNums.length - 1] = numberPick;
                            playerBPaletteNumbers = new int[newBPaletteNums.length];
                            BPaletteColors = new String[newBPaletteColors.length];
                            for (int i = 0; i < BPaletteColors.length; i++) {
                                playerBPaletteNumbers[i] = newBPaletteNums[i];
                                BPaletteColors[i] = newBPaletteColors[i];
                            }  
                            
                            String[] newBHandColors = new String[BHandNums.length - 1];
                            int[] newBHandNums = new int[BHandNums.length - 1];
                            int j = 0;
                            for (int i = 0; i < BHandNums.length; i ++) {
                                if (i != handIndex) {
                                    newBHandColors[j] = playerBHandColors[i];
                                    newBHandNums[j] = BHandNums[i];
                                    j++;
                                }
                            }
                            playerBHandColors = new String[newBHandColors.length];
                            BHandNums = new int[newBHandNums.length];
                            for (int i = 0; i < playerBHandColors.length; i++) {
                                playerBHandColors[i] = newBHandColors[i];
                                BHandNums[i] = newBHandNums[i];
                            }
                            
                            break;
                            
                        }
                    } 
                    
                    System.out.println("That move did not help you to win.  Let's try that again...  (canvasColor: " + canvasColor + ")");
                    
                    
                    
                } else if (chosen.equals("Play only to Canvas")) {
                    ArrayList<String> handChoices = new ArrayList<String>();
                    handChoices.add("Go back");
                    for (int i = 0; i < BHandNums.length; i++) {
                        handChoices.add("Play card " + i + ": " + playerBHandColors[i] + " " + BHandNums[i] + " to canvas");
                    }
                    
                    dialog = new ChoiceDialog<String>("Go back", handChoices);
                    dialog.setHeaderText("Which card will you move to the Canvas?");
                    dialog.setContentText("Options:");
                    dialog.showAndWait().ifPresent( (response) -> {
                        choiceChosen.add(response);
                        System.out.println("response: " + response);
                    });
                    chosen = choiceChosen.get(1);
                    if (chosen.equals("Go back")) {
                        System.out.println("Backing up...");
                        continue;
                    } else if (chosen.charAt(10) == '0') {
                        int handIndex = 0;
                        try {
                            handIndex = Integer.parseInt(chosen.charAt(10) + "");
                        } catch (Exception e) {
                            System.out.println("Couldn't parse the int.");
                        }
                        String colorPick = playerBHandColors[handIndex];
                        int numberPick = BHandNums[handIndex];
                        boolean playOkay = false;
                        if (colorPick.equals("Red")) {
                            String bHighestColor = BPaletteColors[0];
                            int bHighestNumber = playerBPaletteNumbers[0];
                            for (int i = 1; i < BPaletteColors.length; i++) {
                                if (playerBPaletteNumbers[i] > bHighestNumber || (playerBPaletteNumbers[i] == bHighestNumber && BPaletteColors[i].equals("Indigo"))) {
                                    bHighestColor = BPaletteColors[i];
                                    bHighestNumber = playerBPaletteNumbers[i];
                                }
                            }
                            String aHighestColor = "";
                            int aHighestNumber = 0;
                            for (int i = 0; i < playerAPaletteColors.length; i++) {
                                if (APaletteNums[i] > aHighestNumber || (APaletteNums[i] == aHighestNumber && playerAPaletteColors[i].equals("Indigo"))) {
                                    aHighestNumber = APaletteNums[i];
                                    aHighestColor = playerAPaletteColors[i];
                                }
                            }
                            if (aHighestNumber < bHighestNumber || (aHighestNumber == bHighestNumber && aHighestColor.equals("Violet"))) {
                                playOkay = true;
                            }
                        } else if (colorPick.equals("Indigo")) {
                            boolean[] numsB = new boolean[] {false, false, false, false, false, false, false, false};
                            boolean[] numsA = new boolean[] {false, false, false, false, false, false, false, false};
                            String[] colorsA = new String[] {"", "", "", "", "", "", "", ""};
                            int bMaxStreak = 0;
                            int bStreakTop = 0;
                            int currentStreak = 0;
                            int aMaxStreak = 0;
                            int aStreakTop = 0;
                            String aStreakTopColor = "";
                            for (int i = 0; i < playerBPaletteNumbers.length; i++) {
                                numsB[playerBPaletteNumbers[i]] = true;
                            }
                            for (int i = 1; i < numsB.length; i++) {
                                if (numsB[i]) {
                                    currentStreak += 1;
                                    if (currentStreak >=bMaxStreak) {
                                        bMaxStreak = currentStreak;
                                        bStreakTop = i;
                                    }
                                } else {
                                    currentStreak = 0;
                                }
                            }
                            for (int i = 0; i < APaletteNums.length; i++) {
                                numsA[APaletteNums[i]] = true;
                                if (colorsA[APaletteNums[i]].equals("") || playerAPaletteColors[i].equals("Indigo")) {
                                    colorsA[i] = playerAPaletteColors[i];
                                }
                            }
                            for (int i = 1; i < numsA.length; i++) {
                                if (numsA[i]) {
                                    currentStreak += 1;
                                    if (currentStreak >=aMaxStreak) {
                                        aMaxStreak = currentStreak;
                                        aStreakTop = i;
                                    }
                                } else {
                                    currentStreak = 0;
                                }
                            }
                            aStreakTopColor = colorsA[aStreakTop];
                            if (aMaxStreak < bMaxStreak || (aMaxStreak == bMaxStreak && aStreakTop < bStreakTop) || (aMaxStreak == bMaxStreak && aStreakTop == bStreakTop && aStreakTopColor.equals("Violet"))) {
                                playOkay = true;
                            }
                            
                        } else if (colorPick.equals("Violet")) {
                            int numBSmall = 0;
                            int highSmallB = 0;
                            for (int i = 0; i < BPaletteColors.length; i++) {
                                if (playerBPaletteNumbers[i] < 4) {
                                    numBSmall ++;
                                    if (playerBPaletteNumbers[i] > highSmallB) {
                                        highSmallB = playerBPaletteNumbers[i];
                                    }
                                }
                            }
                            int numASmall = 0;
                            int highSmallA = 0;
                            String highSmallAColor = "";
                            for (int i = 0; i < playerAPaletteColors.length; i++) {
                                if (APaletteNums[i] < 4) {
                                    numASmall ++;
                                    if (APaletteNums[i] > highSmallA) {
                                        highSmallA = APaletteNums[i];
                                        highSmallAColor = playerAPaletteColors[i];
                                    }
                                }
                            }
                            if (numASmall < numBSmall || (numASmall == numBSmall && highSmallA < highSmallB) || (numASmall == numBSmall && highSmallA == highSmallB && highSmallAColor.equals("Violet"))) {
                                playOkay = true;
                            }
                            
                        }
                        
                        
                        if (playOkay) {
                            System.out.println("Made a legal play to the canvas!");
                            
                            canvasColor = colorPick;
                            
                            /*
                            String[] newAPaletteColors = new String[playerAPaletteColors.length + 1];
                            int[] newAPaletteNums = new int[APaletteNums.length + 1];
                            for (int i = 0; i < playerAPaletteColors.length; i++) {
                                newAPaletteColors[i] = playerAPaletteColors[i];
                                newAPaletteNums[i] = APaletteNums[i];
                            }
                            newAPaletteColors[newAPaletteColors.length-1] = colorPick;
                            newAPaletteNums[newAPaletteNums.length - 1] = numberPick;
                            APaletteNums = new int[newAPaletteNums.length];
                            playerAPaletteColors = new String[newAPaletteColors.length];
                            for (int i = 0; i < playerAPaletteColors.length; i++) {
                                APaletteNums[i] = newAPaletteNums[i];
                                playerAPaletteColors[i] = newAPaletteColors[i];
                            } */ 
                            /*
                            String[] newBHandColors = new String[BHandNums.length - 1];
                            int[] newBHandNums = new int[BHandNums.length - 1];
                            int j = 0;
                            for (int i = 0; i < BHandNums.length; i ++) {
                                if (i != handIndex) {
                                    newBHandColors[j] = playerBHandColors[i];
                                    newBHandNums[j] = BHandNums[i];
                                    j++;
                                }
                            }
                            playerBHandColors = new String[newBHandColors.length];
                            BHandNums = new int[newBHandNums.length];
                            for (int i = 0; i < playerBHandColors.length; i++) {
                                playerBHandColors[i] = newBHandColors[i];
                                BHandNums[i] = newBHandNums[i];
                            }
                            
                            break;
                            
                        }
                    }
                    
                    System.out.println("That move did not help you to win.  Let's try that again...  (canvasColor: " + canvasColor + ")");
                    
                    
                    
                } else if (chosen.equals("Play to Palette and Canvas")) {
                    ArrayList<String> handChoices = new ArrayList<String>();
                    handChoices.add("Go back");
                    for (int i = 0; i < BHandNums.length; i++) {
                        handChoices.add("Play card " + i + ": " + playerBHandColors[i] + " " + BHandNums[i] + " to palette first...");
                    }
                    
                    dialog = new ChoiceDialog<String>("Go back", handChoices);
                    dialog.setHeaderText("First choose for the Palette.");
                    dialog.setContentText("Options:");
                    dialog.showAndWait().ifPresent( (response) -> {
                        choiceChosen.add(response);
                        System.out.println("response: " + response);
                    });
                    chosen = choiceChosen.get(1);
                    if (chosen.equals("Go back")) {
                        System.out.println("Backing up...");
                        continue;
                    } else {
                        boolean playOkay = false;
                        int hand2PaletteIndex = 0;
                        try {
                            hand2PaletteIndex = Integer.parseInt(chosen.charAt(10) + "");
                        } catch (Exception e) {
                            System.err.println("Ooops!  Couldn't get a character!");
                        }
                        String colorPick = playerBHandColors[hand2PaletteIndex];
                        int numberPick = BHandNums[hand2PaletteIndex];
                        handChoices.clear();
                        handChoices.add("Go back");
                        for (int i = 0; i < BHandNums.length; i++) {
                            if (i != hand2PaletteIndex) {
                                handChoices.add("Play card " + i + ": " + playerBHandColors[i] + " " + BHandNums[i] + " to the canvas second.");
                            }
                        }
                    
                        dialog = new ChoiceDialog<String>("Go back", handChoices);
                        dialog.setHeaderText("Next, choose for the canvas.");
                        dialog.setContentText("Options:");
                        dialog.showAndWait().ifPresent( (response) -> {
                            choiceChosen.add(response);
                            System.out.println("response: " + response);
                        });
                        chosen = choiceChosen.get(2);
                        if (chosen.equals("Go back")) {
                            System.out.println("Backing up...");
                            continue;
                        } else {
                            int hand2CanvasIndex = 0;
                            try {
                                hand2CanvasIndex = Integer.parseInt(chosen.charAt(10) + "");
                            } catch (Exception e) {
                                System.err.println("Couldn't get a character when looking for the canvas card!");
                            }
                            String canvasColorPick = AHandColors[hand2CanvasIndex];
                            
                            if (canvasColorPick.equals("Red")) {
                                String bHighestColor = colorPick;
                                int bHighestNumber = numberPick;
                                for (int i = 0; i < BPaletteColors.length; i++) {
                                    if (playerBPaletteNumbers[i] > bHighestNumber || (playerBPaletteNumbers[i] == bHighestNumber && BPaletteColors[i].equals("Indigo"))) {
                                        bHighestColor = BPaletteColors[i];
                                        bHighestNumber = playerBPaletteNumbers[i];
                                    }
                                }
                                String aHighestColor = playerAPaletteColors[0];
                                int aHighestNumber = APaletteNums[0];
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    if (APaletteNums[i] > aHighestNumber || (APaletteNums[i] == aHighestNumber && playerAPaletteColors[i].equals("Indigo"))) {
                                        aHighestNumber = APaletteNums[i];
                                        aHighestColor = playerAPaletteColors[i];
                                    }
                                }
                                if (aHighestNumber < bHighestNumber || (aHighestNumber == bHighestNumber && aHighestColor.equals("Violet"))) {
                                    playOkay = true;
                                }
                            } else if (canvasColorPick.equals("Indigo")) {
                                boolean[] numsB = new boolean[] {false, false, false, false, false, false, false, false};
                                boolean[] numsA = new boolean[] {false, false, false, false, false, false, false, false};
                                String[] colorsA = new String[] {"", "", "", "", "", "", "", ""};
                                int bMaxStreak = 0;
                                int bStreakTop = 0;
                                int currentStreak = 0;
                                int aMaxStreak = 0;
                                int aStreakTop = 0;
                                String aStreakTopColor = "";
                                numsB[numberPick] = true;
                                for (int i = 0; i < playerBPaletteNumbers.length; i++) {
                                    numsB[playerBPaletteNumbers[i]] = true;
                                }
                                for (int i = 1; i < numsB.length; i++) {
                                    if (numsB[i]) {
                                        currentStreak += 1;
                                        if (currentStreak >=bMaxStreak) {
                                            bMaxStreak = currentStreak;
                                            bStreakTop = i;
                                        }
                                    } else {
                                        currentStreak = 0;
                                    }
                                }
                                for (int i = 0; i < APaletteNums.length; i++) {
                                    numsA[APaletteNums[i]] = true;
                                    if (colorsA[APaletteNums[i]].equals("") || playerAPaletteColors[i].equals("Indigo")) {
                                        colorsA[i] = playerAPaletteColors[i];
                                    }
                                }
                                for (int i = 1; i < numsA.length; i++) {
                                    if (numsA[i]) {
                                        currentStreak += 1;
                                        if (currentStreak >=aMaxStreak) {
                                            aMaxStreak = currentStreak;
                                            aStreakTop = i;
                                        }
                                    } else {
                                        currentStreak = 0;
                                    }
                                }
                                aStreakTopColor = colorsA[aStreakTop];
                                if (aMaxStreak < bMaxStreak || (aMaxStreak == bMaxStreak && aStreakTop < bStreakTop) || (aMaxStreak == bMaxStreak && aStreakTop == bStreakTop && aStreakTopColor.equals("Violet"))) {
                                    playOkay = true;
                                }
                                
                            } else if (canvasColorPick.equals("Violet")) {
                                int numBSmall = 0;
                                int highSmallB = 0;
                                if (numberPick < 4) {
                                    numBSmall++;
                                    highSmallB = numberPick;
                                }
                                for (int i = 0; i < BPaletteColors.length; i++) {
                                    if (playerBPaletteNumbers[i] < 4) {
                                        numBSmall ++;
                                        if (playerBPaletteNumbers[i] > highSmallB) {
                                            highSmallB = playerBPaletteNumbers[i];
                                        }
                                    }
                                }
                                int numASmall = 0;
                                int highSmallA = 0;
                                String highSmallAColor = "";
                                for (int i = 0; i < playerAPaletteColors.length; i++) {
                                    if (APaletteNums[i] < 4) {
                                        numASmall ++;
                                        if (APaletteNums[i] > highSmallA) {
                                            highSmallA = APaletteNums[i];
                                            highSmallAColor = playerAPaletteColors[i];
                                        }
                                    }
                                }
                                if (numASmall < numBSmall || (numASmall == numBSmall && highSmallA < highSmallB) || (numASmall == numBSmall && highSmallA == highSmallB && highSmallAColor.equals("Violet"))) {
                                    playOkay = true;
                                }
                                
                            }
                        
                        
                            if (playOkay) {
                                System.out.println("Made a legal play to both the palette and canvas!");
                                
                                canvasColor = canvasColorPick;
                                
                                String[] newBPaletteColors = new String[BPaletteColors.length + 1];
                                int[] newBPaletteNums = new int[BPaletteColors.length + 1];
                                for (int i = 0; i < BPaletteColors.length; i++) {
                                    newBPaletteColors[i] = BPaletteColors[i];
                                    newBPaletteNums[i] = playerBPaletteNumbers[i];
                                }
                                newBPaletteColors[newBPaletteColors.length-1] = colorPick;
                                newBPaletteNums[newBPaletteNums.length - 1] = numberPick;
                                playerBPaletteNumbers = new int[newBPaletteNums.length];
                                BPaletteColors = new String[newBPaletteColors.length];
                                for (int i = 0; i < BPaletteColors.length; i++) {
                                    playerBPaletteNumbers[i] = newBPaletteNums[i];
                                    BPaletteColors[i] = newBPaletteColors[i];
                                } 
                                
                                playerBHandColors = new String[0];
                                BHandNums = new int[0];
                                
                                break;
                                
                            }
                        }
                    }
                    
                    System.out.println("That move did not help you to win.  Let's try that again...  (canvasColor: " + canvasColor + ")");
                }
            }
            
            
            
            
            
            board = new VBox();
            board.setSpacing(2);
            handText = new Text("Player A's Hand");
            board.getChildren().add(handText);
            aHandCards = new HBox();
            board.getChildren().add(aHandCards);
            for (int i = 0; i < AHandColors.length; i++) {
                StackPane aHandCardPane = new StackPane();
                Rectangle aHandCardR = new Rectangle(125, 175, Color.VIOLET);
                if (AHandColors[i].equals("Indigo")) {
                    aHandCardR.setFill(Color.INDIGO);
                }
                aHandCardPane.getChildren().add(aHandCardR);
                cardNumberText = new Text("" +  playerAHandNums[i]);
                cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
                cardNumberText.setFill(Color.WHITE);
                aHandCardPane.getChildren().add(cardNumberText);
                aHandCards.getChildren().add(aHandCardPane);
            }
            
            board.getChildren().add(new Separator());
            
            board.getChildren().add(new Text("Player A's Palette"));
            
            aPaletteCards = new HBox();
            board.getChildren().add(aPaletteCards);
            for (int i = 0; i < playerAPaletteColors.length; i++) {
                aPalette = new StackPane();
                aPaletteR = new Rectangle(125, 175, Color.VIOLET);
                if (playerAPaletteColors[i].equals("Indigo")) {
                    aPaletteR.setFill(Color.INDIGO);
                }
                aPalette.getChildren().add(aPaletteR);
                cardNumberText = new Text("" + APaletteNums[i]);
                cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
                cardNumberText.setFill(Color.WHITE);
                aPalette.getChildren().add(cardNumberText);
                aPaletteCards.getChildren().add(aPalette);
            }
            
            board.getChildren().add(new Separator());
            
            board.getChildren().add(new Text("Canvas"));
            
            if (canvasColor.equals("Red")) {
                board.getChildren().add(new Rectangle(175, 125, Color.RED));
            } else if (canvasColor.equals("Indigo")) {
                board.getChildren().add(new Rectangle(175, 125, Color.INDIGO));
            } else  if (canvasColor.equals("Violet")) {
                board.getChildren().add(new Rectangle(175, 125, Color.VIOLET));
            }
            
            board.getChildren().add(new Separator());
            
            board.getChildren().add(new Text("Player B's Palette"));
            
            bPaletteCards = new HBox();
            board.getChildren().add(bPaletteCards);
            for (int i = 0; i < BPaletteColors.length; i++) {
                bPalette = new StackPane();
                bPaletteR = new Rectangle(125, 175, Color.VIOLET);
                if (BPaletteColors[i].equals("Indigo")) {
                    bPaletteR.setFill(Color.INDIGO);
                }
                bPalette.getChildren().add(bPaletteR);
                cardNumberText = new Text("" + playerBPaletteNumbers[i]);
                cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
                cardNumberText.setFill(Color.WHITE);
                bPalette.getChildren().add(cardNumberText);
                bPaletteCards.getChildren().add(bPalette);
            }
            
            board.getChildren().add(new Separator());
            
            board.getChildren().add(new Text("Player B's Hand"));
            bHandCards = new HBox();
            board.getChildren().add(bHandCards);
            
            for (int i = 0; i < playerBHandColors.length; i++) {
                StackPane bHandCardPane = new StackPane();
                Rectangle bHandCardR = new Rectangle(125, 175, Color.VIOLET);
                if (playerBHandColors[i].equals("Indigo")) {
                    bHandCardR.setFill(Color.INDIGO);
                }
                bHandCardPane.getChildren().add(bHandCardR);
                cardNumberText = new Text("" + BHandNums[i]);
                cardNumberText.setFont(Font.font("System", FontWeight.BOLD, 50.0));
                cardNumberText.setFill(Color.WHITE);
                bHandCardPane.getChildren().add(cardNumberText);
                bHandCards.getChildren().add(bHandCardPane);
            }
            
            
            primaryStage.setScene(new Scene(board));
            primaryStage.show();
            primaryStage.sizeToScene();
        }
        
        System.out.println("Game over!");
        
        
        
    } */


} //end of Red7
