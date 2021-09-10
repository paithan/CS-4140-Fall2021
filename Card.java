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











