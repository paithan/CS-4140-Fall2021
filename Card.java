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

} // end of Card.java
