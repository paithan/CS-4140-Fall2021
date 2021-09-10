import java.util.*;

/**
 * Models a single Red 7 card.
 *
 * @author CS 4140 Fall 2021.
 */
public class Card {

    //the color of the card
    private String color;
    
    //the number of the card
    private int number;
    
    //legal colors
    private String[] colors = new String[]{"Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet"};
    
    /**
     * Creates an instance of a Card.
     *
     * @param color The color of the card.
     * @param number The number of the card.
     */
    public Card(String color, int number) {
        if ((number > 7) || (number < 1)) {
            //an illegal number
            throw new IllegalArgumentException("number needs to be between 1 and 7.  Tried: " + number);
        }
        this.number = number;
        List<String> legalColors = Arrays.asList(this.colors);
        if (legalColors.contains(color)) {
            this.color = color;
        } else {
            throw new IllegalArgumentException("Illegal color: " + color);
        }
    }
    
    
    @Override
    public String toString() {
        String cardString = this.color + " " + number;
        return cardString;
    }
    
    
    /**
     * Gets the color.
     *
     * @return  The color of this card.
     */
    public String getColor() {
        return this.color;
    }
    
    /**
     * Gets the number.
     *
     * @return The number value of this card.
     */
    public int getNumber() {
        return this.number;
    }
    
    /**
     * Tests equality.
     *
     * @param otherCard  The card to compare with this.
     * @return  true if they are the same card, false otherwise.
     */
    public boolean equals(Card otherCard) {
        return this.color.equals(otherCard.color) && this.number == otherCard.number;
    }
    
    /**
     * Main method for testing.
     *
     * @param args  Strings from the command line.
     */
    public static void main(String[] args) {
        Card testCard = new Card("Red", 7);
        System.out.println(testCard);
        
        try {
            Card mauve = new Card("Mauve", 8);
            System.out.println(mauve);
        } catch (IllegalArgumentException e) {
            System.out.println("Mauve broke things, good!");
        }
    }

} // end of Card.java











