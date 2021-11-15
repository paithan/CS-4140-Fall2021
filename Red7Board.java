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
    public Red7Board() {
        
        //build the deck of cards
        this.deck = new ArrayList<Card>();
        
        
        //add the cards to the deck
        for (int cardNumber = 1; cardNumber <= 7; cardNumber++) {
            this.deck.add(Card.createCard(new CardColor.Red(), cardNumber));
            this.deck.add(Card.createCard(new CardColor.Yellow(), cardNumber));
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
     * Returns the index of the current player.
     */
    public int getCurrentPlayerIndex() {
        
    
    }
    
    
    //returns the next card in the deck
    private Card dealCard() {
        return this.deck.remove(0);
    }



} //end of Red7Board.java
































