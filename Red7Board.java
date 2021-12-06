import java.util.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;

/**
 * Models a Red7 position.
 *
 * @author CS4140 Fall 2021.
 */
public class Red7Board {
    
    //The players of this game.
    private Player[] players;
    
    //the number of cards in each player's hand at the start
    private static final int STARTING_HAND_SIZE = 7;
    
    //The deck
    private ArrayList<Card> deck;
    
    //the canvas color
    private CardColor canvas;
    
    
    
    /**
     * Class constructor.
     */
    public Red7Board(Red7Board previousBoard, CardColor canvas, Player justMoved) {
        this.deck = previousBoard.deck; //TODO: write a method that copies an arraylist of cards to use here.
        this.canvas = canvas;
        
        this.players = new Player[2];
        this.players[0] = previousBoard.players[0];
        this.players[1] = previousBoard.players[1];
        //set the players correctly
        //check which player became justMoved by seeing which hand is a superset of justMoved's hand.
        Set<Card> previousPlayer0Hand = new TreeSet<Card>();
        previousPlayer0Hand.addAll(previousBoard.players[0].getHand());
        Set<Card> justMovedHand = new TreeSet<Card>();
        justMovedHand.addAll(justMoved.getHand());
        if (previousPlayer0Hand.containsAll(justMovedHand)) {
            this.players[0] = justMoved;
        } else {
            this.players[1] = justMoved;
        }
        
        /*
        //set the players correctly
        if (justMoved.getName().equals(previousBoard.players[0].getName()) {
            this.players[0] = justMoved;
            this.players[1] = previousBoard.players[1];
        } else {
            this.players[0] = previousBoard.players[0];
            this.players[1] = justMoved;
        }*/
    }
    
    /**
     * Class constructor.
     */
    public Red7Board() {
        
        //build the deck of cards
        this.deck = new ArrayList<Card>();
        
        
        //add the cards to the deck
        for (int cardNumber = 1; cardNumber <= 7; cardNumber++) {
            this.deck.add(Card.createCard(new CardColor.Red(), cardNumber));
            this.deck.add(Card.createCard(new CardColor.Yellow(), cardNumber));
            this.deck.add(Card.createCard(new CardColor.Green(), cardNumber));
            this.deck.add(Card.createCard(new CardColor.Violet(), cardNumber));
        }
        Collections.shuffle(this.deck);
        
        this.canvas = new CardColor.Red();
        
        ArrayList<Card> playerAHand = new ArrayList<Card>();
        ArrayList<Card> playerAPalette = new ArrayList<Card>();
        
        
        //deal cards to playerA
        //4 to the hand
        for (int i = 0; i < STARTING_HAND_SIZE; i++) {
            playerAHand.add(dealCard());
        }
        
        Player playerA = new Player("Player A", playerAHand, this.dealCard());
        
        //playerB setup
        ArrayList<Card> playerBHand = new ArrayList<Card>();
        ArrayList<Card> playerBPalette = new ArrayList<Card>();
        
        //deal cards to playerB
        //4 to the hand
        for (int i = 0; i < STARTING_HAND_SIZE; i++) {
            playerBHand.add(dealCard());
        }
        
        Player playerB = new Player("Player B", playerBHand, this.dealCard());
        
        this.players = new Player[] {playerA, playerB};
        
    }

    /**
     * Returns the current player.
     */
    public Player getCurrentPlayer() {
        /*
        int winningPlayerIndex = this.canvas.whoIsWinning(this.players[0].getPalette(), this.players[1].getPalette());*/
        //int currentPlayerIndex = 1 - winningPlayerIndex;
        int currentPlayerIndex = (this.getWinningPlayerIndex() + 1) % this.players.length;
        return this.players[currentPlayerIndex];
        
    }
    
    /**
     * Returns the previous player.
     */
    public Player getPreviousPlayer() {
        /*
        int winningPlayerIndex = this.canvas.whoIsWinning(this.players[0].getPalette(), this.players[1].getPalette());
        return this.players[winningPlayerIndex];
        */
        return this.players[this.getWinningPlayerIndex()];
    }
    
    //gets the index of the winning player.
    private int getWinningPlayerIndex() {
        return this.canvas.whoIsWinning(this.players[0].getPalette(), this.players[1].getPalette());
    }
    
    
    //returns the next card in the deck
    private Card dealCard() {
        return this.deck.remove(0);
    }
    
    
    /**
     * Returns the canvas color.
     */
    public CardColor getCanvas() {
        return this.canvas;
    }
    
    
    /**
     * Returns a JavaFX Node that displays this.
     */
    public Node getFXGraphics() {
        int cardHeight = 100;
        VBox node = new VBox();
        node.setSpacing(2);
        
        node.getChildren().add(this.players[0].getFXGraphics(cardHeight, false));
        
        //the canvas
        node.getChildren().add(new Text("Canvas:"));
        Rectangle canvasRectangle = new Rectangle(175, 125, this.canvas.getFXColor());
        node.getChildren().add(canvasRectangle);
        
        
        node.getChildren().add(this.players[1].getFXGraphics(cardHeight, true));
    
        return node;
    }



} //end of Red7Board.java
































