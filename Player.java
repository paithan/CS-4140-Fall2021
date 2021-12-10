import java.util.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * Represents a player of the game Red7.
 *
 * @author The whole CS 4140 class of Fall 2021.
 */
public class Player {

    //fields
    
    //the hand of cards
    private ArrayList<Card> hand;
    
    //the cards on this player's board visible to everyone
    private ArrayList<Card> palette;
    
    //the player's name
    private String name;
    
    /**
     * Class constructor.
     */
    public Player(String name, ArrayList<Card> hand, Card paletteCard) {
        this.name = name;
        this.hand = copyCardList(hand);
        /*
        for (Card card : hand) {
            this.hand.add(card);
        }*/
        //this.hand = hand;
        this.palette = new ArrayList<Card>();
        this.palette.add(paletteCard);
    }
    
    
    /**
     * Class copy constructor.
     */
    public Player(Player player) {
        this.name = player.getName();
        this.hand = player.getHand();
        this.palette = player.getPalette();
    }
    
    
    /**
     * Moves a card to the palette.
     *
     * @param card  The card to move to the palette.
     * @return      A new Player with the card moved from the hand to the palette.
     * @throws IllegalArgumentException  When card is not in this Player's hand.
     */
    public Player moveToPalette(Card card) {
        if (!this.hand.contains(card)) {
            throw new IllegalArgumentException(card + " is not in " + this.hand + "!");
        } else {
            Player afterMove = new Player(this);
            afterMove.hand.remove(card);
            afterMove.palette.add(card);
            //this.palette.remove(card);
            //this.hand.add(card);
            return afterMove;
        }
    }
    
    
    /**
     * Moves a card to the canvas.
     */
    public Player moveToCanvas(Card card) {
        if (!this.hand.contains(card)) {
            throw new IllegalArgumentException(card + " is not in " + this.hand + "!");
        } else {
            Player afterMove = new Player(this);
            afterMove.hand.remove(card);
            return afterMove;
        }
    }
    
    /**
     * Moves a card to both the palette and canvas.
     */
    public Player moveToBoth(Card paletteCard, Card canvasCard) {
        Player afterPaletteMove = this.moveToPalette(paletteCard);
        return afterPaletteMove.moveToCanvas(canvasCard);
    }
    
    
    /**
     * Returns an FX version of the graphics for this.
     */
    public Node getFXGraphics(int cardHeight, boolean isInverted) {
        VBox playerGraphics = new VBox();
        //Observable<Node> graphicsChildren = playerGraphics.getChildren();
        playerGraphics.setSpacing(2);
        
        Text handText = new Text(this.name + "'s Hand");
        
        if (!isInverted) {
            //draw the hand
            playerGraphics.getChildren().add(handText);
            playerGraphics.getChildren().add(getCardRow(this.hand, cardHeight));
        }
        
        //draw the Palette
        playerGraphics.getChildren().add(new Text(this.name + "'s Palette:"));
        playerGraphics.getChildren().add(getCardRow(this.palette, cardHeight));
        
        if (isInverted) {
            //draw the hand
            playerGraphics.getChildren().add(handText);
            playerGraphics.getChildren().add(getCardRow(this.hand, cardHeight));
        }
        
        return playerGraphics;
    }
    
    //returns an FX graphical display for a row of cards.
    private static Node getCardRow(ArrayList<Card> cards, int cardHeight) {
        HBox cardsRow = new HBox();
        cardsRow.setSpacing(5);
        //for (int i = 0; i < cards.size(); i++) {
        for (Card card : cards) {
            Node cardGraphics = card.getFXGraphics(cardHeight);
            cardsRow.getChildren().add(cardGraphics);
        }
        return cardsRow;
    }
    
    
    //copies an ArrayList of Card objects.
    private static ArrayList<Card> copyCardList(ArrayList<Card> cards) {
        ArrayList<Card> clone = new ArrayList<Card>();
        clone.addAll(cards);
        return clone;
    }
    
    
    /**
     * Returns a string version of this.
     */
    public String toString() {
        return "A Red7 player named " + this.name + " with hand: " + this.hand + " and palette: " + this.palette;
    }
    
    /**
     * Returns the name of this player.
     */
    public String getName() {
        return this.name;
    }
    
    
    /**
     * Returns a deep copy of the hand of cards this player has.
     */
    public ArrayList<Card> getHand() {
        return copyCardList(this.hand);
    }
    
    /** 
     * Returns a deep copy of the palette for this player.
     */
    public ArrayList<Card> getPalette() {
        return copyCardList(this.palette);
    }
    
    
    /**
     * Returns whether this player equals an object.
     */
    public boolean equals(Object obj) {
        try {
            Player other = (Player)obj;
            return this.equals(other);
        } catch (ClassCastException e) {
            return false;
        }
    }
    
    
    /**
     * Returns whether this player equals another.
     */
    public boolean equals(Player other) {
        return (this.name.equals(other.name)) && (this.hand.equals(other.hand)) && (this.palette.equals(other.palette));
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    


} //end of class Player
