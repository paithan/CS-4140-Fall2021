import java.util.*;
import java.lang.*;

/**
 * Models a single Red 7 card.
 *
 * @author CS 4140 Fall 2021.
 */
public abstract class Card implements Comparable<Card> {


    //the number of the card
    private int number;
    
    //the color of the card
    private CardColor color;
    
    //legal colors
    //private String[] colors = new String[]{"Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet"};
    
    /**
     * Creates an instance of a Card.
     *
     * @param color The color of the card.
     * @param number The number of the card.
     */
    protected Card(CardColor color, int number) {
        this.number = number;
        this.color = color;
        /*
        if ((number > 7) || (number < 1)) {
            //an illegal number
            throw new IllegalArgumentException("number needs to be between 1 and 7.  Tried: " + number);
        }
        */
    }
    
    
    /**
     * Creates a Card object.
     */
    public static Card createCard(CardColor color, int number) {
        return new RealCard(color, number);
    }
    
    /**
     * Creates a card that acts as an empty card?  a null card?
     */
    public static Card createNullCard() {
        return new NullCard();
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
        //return (this.getColor().compareTo(otherCard.getColor()) == 0) && this.getNumber() == otherCard.getNumber();
        return (this.compareTo(otherCard) == 0);
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
        if (this.getNumber() != otherCard.getNumber()) {
            return this.getNumber() - otherCard.getNumber();
        } else {
            return this.getColor().compareTo(otherCard.getColor());
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
        Card testCard = new RealCard(red, 7);
        System.out.println(testCard);
        
        /*
        try {
            Card mauve = new RealCard("Mauve", 8);
            System.out.println(mauve);
        } catch (IllegalArgumentException e) {
            System.out.println("Mauve broke things, good!");
        }*/
        
        Card red7 = new RealCard(red, 7);
        Card yellow3 = new RealCard(yellow, 3);
        Card violet3 = new RealCard(violet, 3);
        
        System.out.println("Does Red 7 equal Yellow 3?  " + red7.equals(yellow3));
        System.out.println("Does Red 7 equal Red 7?  " + red7.equals(testCard));
        
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(red7);
        cards.add(violet3);
        cards.add(yellow3);
        cards.add(new RealCard(red, 6));
        cards.add(new RealCard(yellow, 7));
        cards.add(new RealCard(violet, 5));
        
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
        Card[] cardArray = cards.toArray(new RealCard[0]);
        Arrays.sort(cardArray);
        for (int i = 0; i < cardArray.length; i++) {
            System.out.print(
        }*/
        
    }
    
    /**
     * Implements a card representing no card at all.
     */
    private static class NullCard extends Card {
        
        /**
         * Creates a non-existent card.
         */
        public NullCard() {
            super(new CardColor.Violet(), 0);
            /*
            this.number = 0;
            this.color = new CardColor.Violet();
            */
        }
        
        @Override
        public String toString() {
            return "";
        }
        
        /*
        @Override
        public int getNumber() {
            return 0;
        }*/
    
    } //end of NullCard
    
    /**
     * Implements a real Red7 card.
     */
    private static class RealCard extends Card {

        /**
         * Creates a new Red7 card.
         */
        public RealCard(CardColor color, int number) {
            super(color, number);
            if ((number > 7) || (number < 1)) {
                //an illegal number
                throw new IllegalArgumentException("number needs to be between 1 and 7.  Tried: " + number);
            }
            /*
            this.number = number;
            this.color = color;
            */
        }
    
        @Override
        public String toString() {
            String cardString = "(" + this.getColor().toString() + ", " + this.getNumber() + ")";
            return cardString;
        }
        
        
    
    } //end of RealCard
    

} // end of Card.java











