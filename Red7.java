import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
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
 * An implementation of the game Red7, by Asmadi Games.  Thanks to Asmadi Games for granting me permission to use their game rules for educational purposes.  
 *
 * @author Kyle Burke <kwburke@plymouth.edu>
 */
public class Red7 extends Application {


    //the deck of cards in this game
    private ArrayList<Card> deck;
    
    /**
     * Main method to run the game.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Deals a card from the deck.
     */
    public Card dealCard() {
        return this.deck.remove(0);
    }
    
    
    /**
     * Displays all part of the current game state.
     */
    public void displayAll(Stage stage, CardColor canvas, Player playerA, Player playerB) {
        int cardHeight = 100;
        VBox board = new VBox();
        board.setSpacing(2);
        
        board.getChildren().add(playerA.getFXGraphics(cardHeight, false));
        
        //the canvas
        board.getChildren().add(new Text("Canvas:"));
        Rectangle canvasRectangle = new Rectangle(175, 125, canvas.getFXColor());
        board.getChildren().add(canvasRectangle);
        
        
        board.getChildren().add(playerB.getFXGraphics(cardHeight, true));
        
        
        //set up the scene and make sure it's visible
        stage.setScene(new Scene(board));
        stage.show();
        stage.sizeToScene();
    }
    
    /**
     * Determines which player is winning.
     */
    public static int whoIsWinning(CardColor canvasColor, ArrayList<Card> playerAPalette, ArrayList<Card> playerBPalette) {
        return canvasColor.whoIsWinning(playerAPalette, playerBPalette);
    
    }
    
    /**
     * Determines whether the current player is winning.
     */
     /*
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
    }/* */
    
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
        //primaryStage.setFullScreen(true);
        
        Red7Board board = new Red7Board();
        
        
        
        //set the first player based on who is winning.
        int currentPlayerIndex;
        
        int winning = canvas.whoIsWinning(playerA.getPalette(), playerB.getPalette());
        System.out.println("I think player " + winning + " is winning.");
        
        currentPlayerIndex = 1 - winning;
        System.out.println("That means player " + currentPlayerIndex + " goes first!");
        
        Player[] players = new Player[] {playerA, playerB};
        Player currentPlayer = players[currentPlayerIndex];
        
        System.out.println("Player " + currentPlayer.getName() + " goes first!");
        
        /* */
        while (true) {
            
            displayAll(primaryStage, canvas, players[0], players[1]);
            
            
            currentPlayer = players[currentPlayerIndex];
            Player opponentPlayer = players[1-currentPlayerIndex];
            
            if (currentPlayer.getHand().size() == 0) {
                break;
            }
            
            ArrayList<String> playChoices = new ArrayList<String>();
            playChoices.add("Play only to Palette");
            playChoices.add("Play only to Canvas");
            if (currentPlayer.getHand().size() > 1) {
                playChoices.add("Play to Palette and Canvas");
            }
            playChoices.add("Concede");
            ArrayList<String> choiceChosen = new ArrayList<String>();
            ChoiceDialog<String> dialog = new ChoiceDialog<String>(playChoices.get(0), playChoices);
            dialog.setTitle("Player " + currentPlayer.getName() + "'s turn.");
            dialog.setHeaderText("Player " + currentPlayer.getName() + ", Choose your move.");
            dialog.setContentText("Options:");
        
            System.out.println("Player " + currentPlayer.getName() + "'s turn...");
            while (true) {
                dialog.showAndWait().ifPresent( (response) -> {
                    choiceChosen.add(response);
                });
                if (choiceChosen.size() == 1) {
                    break;
                }
            }
            String choice = choiceChosen.get(0);
            
            Player playerAfterMove = currentPlayer;
            CardColor newCanvasColor = canvas;
            
            
            //String newCanvasColor = canvasColor;
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
                for (int i = 0; i < currentPlayer.getHand().size(); i++) {
                    Card card = currentPlayer.getHand().get(i);
                    String cardString = card.toString();
                    playChoices.add(i + ": Play " + cardString + " to the palette.");
                    /*
                    Color cardColor = card.getColor();
                    String color = currentPlayerHandColors.get(i);
                    int number = currentPlayerHandNumbers.get(i);
                    //int number = currentPlayerHandNumbers.get(i);
                    playChoices.add(i + ": Play " + color + " " + number + " to the palette.");
                    */
                }
                dialog = new ChoiceDialog<String>(playChoices.get(0), playChoices);
                dialog.setTitle("Palette card");
                dialog.setHeaderText("Player " + currentPlayer.getName() + ", pick your card");
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
                Card cardToPalette = currentPlayer.getHand().get(cardIndex);
                playerAfterMove = playerAfterMove.moveToPalette(cardToPalette);
                
            }
            
            if (playToCanvas) {
                playChoices.clear();
                choiceChosen.clear();
                for (int i = 0; i < playerAfterMove.getHand().size(); i++) {
                    Card card = playerAfterMove.getHand().get(i);
                    playChoices.add(i + ": Play " + card + " to the canvas.");
                    
                }
                dialog = new ChoiceDialog<String>(playChoices.get(0), playChoices);
                dialog.setTitle("Canvas card");
                dialog.setHeaderText("Player " + playerAfterMove.getName() + ", pick your card");
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
                Card cardToCanvas = playerAfterMove.getHand().get(cardIndex);
                playerAfterMove = playerAfterMove.moveToCanvas(cardToCanvas);
                newCanvasColor = cardToCanvas.getColor();
                
            }
            if (newCanvasColor.whoIsWinning(playerAfterMove.getPalette(), opponentPlayer.getPalette()) == 0) {
                System.out.println("That move works!");
                canvas = newCanvasColor;
                players[currentPlayerIndex] = playerAfterMove;
                currentPlayerIndex = (currentPlayerIndex + 1) % 2;
            } else {
                System.out.println("That move doesn't work!");
            }
            
            
            
        }
        
        System.out.println("Player " + currentPlayer.getName() + " loses!");
        
        
    }
        


} //end of Red7
