import java.util.*;
import java.lang.*;

/**
 * Models a single Red 7 card.
 *
 * @author CS 4140 Fall 2021.
 */
public class Card implements Comparable<Card> {

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
        String cardString = "(" + this.color + ", " + number + ")";
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
     * Tests equality.
     *
     * @param obj  The other object to compare with this.
     * @return  true if they are the same card, false otherwise.
     */
    public boolean equals(Object obj) {
        try {
            Card otherCard = (Card) obj;
            return this.equals(otherCard);
        } catch (ClassCastException e) {
            return false;
        }
    }
    
    @Override
    public int compareTo(Card otherCard) {
        if (this.number > otherCard.number) {
            return 1;
        } else if (this.number < otherCard.number) {
            return -1;
        } else {
            //the two numbers are equal
            if (this.color.equals(otherCard.color)) {
                return 0;
            } else if (this.color.equals("Red") || (this.color.equals("Yellow") && otherCard.color.equals("Violet"))) {
                //our card is better
                return 1;
            } else {
                return -1;
            }
        }
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
        
        Card red7 = new Card("Red", 7);
        Card yellow3 = new Card("Yellow", 3);
        Card violet3 = new Card("Violet", 3);
        
        System.out.println("Does Red 7 equal Yellow 3?  " + red7.equals(yellow3));
        System.out.println("Does Red 7 equal Red 7?  " + red7.equals(testCard));
        
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(red7);
        
        System.out.println("Is Red 7 in the list?  (should be true)  " + cards.contains(testCard));
        
        System.out.println("How does Red 7 compare to Red 7?  (should be 0) " + red7.compareTo(testCard));
        System.out.println("How does Red 7 compare to Red 7?  (should be 0) " + red7.compareTo(red7));
        System.out.println("How does Red 7 compare to Yellow 3?  (should be 1) " + red7.compareTo(yellow3));
        System.out.println("How does Yellow 3 compare to Red 7?  (should be -1) " + yellow3.compareTo(red7));
        System.out.println("How does Yellow 3 compare to Violet 3?  (should be 1) " + yellow3.compareTo(violet3));
        
        
        
    }

} // end of Card.java











