import java.util.*;
import java.lang.*;

/**
 * Models a single Red 7 card.
 *
 * @author CS 4140 Fall 2021.
 */
public class Card implements Comparable<Card> {

    //the color of the card
    private CardColor color;
    
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
    public Card(CardColor color, int number) {
        if ((number > 7) || (number < 1)) {
            //an illegal number
            throw new IllegalArgumentException("number needs to be between 1 and 7.  Tried: " + number);
        }
        this.number = number;
        this.color = color;
    }
    
    
    @Override
    public String toString() {
        String cardString = "(" + this.color.toString() + ", " + number + ")";
        return cardString;
    }
    
    
    /**
     * Gets the color.
     *
     * @return  The color of this card.
     */
    public CardColor getColor() {
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
        return (this.color.compareTo(otherCard.color) == 0) && this.number == otherCard.number;
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
        if (this.number != otherCard.number) {
            return this.number - otherCard.number;
        } else {
            return this.color.compareTo(otherCard.color);
        }
    }
    
    
    /**
     * Main method for testing.
     *
     * @param args  Strings from the command line.
     */
    public static void main(String[] args) {
        CardColor red = new CardColor.Red();
        CardColor yellow = new CardColor.Yellow();
        CardColor violet = new CardColor.Violet();
        Card testCard = new Card(red, 7);
        System.out.println(testCard);
        
        /*
        try {
            Card mauve = new Card("Mauve", 8);
            System.out.println(mauve);
        } catch (IllegalArgumentException e) {
            System.out.println("Mauve broke things, good!");
        }*/
        
        Card red7 = new Card(red, 7);
        Card yellow3 = new Card(yellow, 3);
        Card violet3 = new Card(violet, 3);
        
        System.out.println("Does Red 7 equal Yellow 3?  " + red7.equals(yellow3));
        System.out.println("Does Red 7 equal Red 7?  " + red7.equals(testCard));
        
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(red7);
        cards.add(violet3);
        cards.add(yellow3);
        cards.add(new Card(red, 6));
        cards.add(new Card(yellow, 7));
        cards.add(new Card(violet, 5));
        
        System.out.println("Is Red 7 in the list?  (should be true)  " + cards.contains(testCard));
        
        System.out.println("How does Red 7 compare to Red 7?  (should be 0) " + red7.compareTo(testCard));
        System.out.println("How does Red 7 compare to Red 7?  (should be 0) " + red7.compareTo(red7));
        System.out.println("How does Red 7 compare to Yellow 3?  (should be positive) " + red7.compareTo(yellow3));
        System.out.println("How does Yellow 3 compare to Red 7?  (should be negative) " + yellow3.compareTo(red7));
        System.out.println("How does Yellow 3 compare to Violet 3?  (should be positive) " + yellow3.compareTo(violet3));
        
        
        System.out.println("List of cards: " + cards);
        Set<Card> cardSet = new TreeSet<Card>();
        cardSet.addAll(cards);
        System.out.println(cardSet);
        
        /*
        Card[] cardArray = cards.toArray(new Card[0]);
        Arrays.sort(cardArray);
        for (int i = 0; i < cardArray.length; i++) {
            System.out.print(
        }*/
        
        
    }

} // end of Card.java











