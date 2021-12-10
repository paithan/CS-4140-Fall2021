import java.lang.*;
import java.util.*;
import javafx.scene.paint.Color;

/**
 * Represents a single color from the game Red7.
 *
 * @author CS4140 Fall 2021 class.
 */
public abstract class CardColor implements Comparable<CardColor> {

    @Override
    public int compareTo(CardColor other) {
        return this.getColorRank() - other.getColorRank();
    }
    
    //returns the rank of the color
    protected abstract int getColorRank();
    
    /**
     * Returns a javafx Color for this.
     * @return  A GUI color for this object.
     */
    public abstract Color getFXColor();
    
    /**
     * Returns a javafx Color for text on main color.
     * @return  A GUI color for text for this object.
     */
    public abstract Color getFXTextColor();

    /**
     * Returns a string representation of this color.
     *
     * @return String represetnation of this.
     */
    public abstract String toString();
    
    /**
     * Returns which player is winning.
     */
    public abstract int whoIsWinning(List<Card> playerAPalette, List<Card> playerBPalette);
    
    /**
     * Main method for testing.
     */
    public static void main(String[] args) {
        CardColor red = new Red();
        CardColor yellow = new Yellow();
        CardColor violet = new Violet();
        System.out.println(red.toString());
        System.out.println("How does red compare to yellow?  (Should be positive integer) " + red.compareTo(yellow));
        System.out.println("How does violet compare to red?  (Should be a negative integer) " + violet.compareTo(red));
        System.out.println("How does yellow compare to itself?  (Should be zero)  " + yellow.compareTo(yellow));
    }
    
    /**
     * The color Red.
     */
    public static class Red extends CardColor {
        
        public Red() {
        
        }
        
        protected int getColorRank() {
            return 7;
        }
        
        @Override
        public String toString() {
            return "Red";
        }
        
        @Override
        public Color getFXColor(){
            return Color.RED;
        }
        
        @Override
        public Color getFXTextColor(){
            return Color.WHITE;
        }
        
        /**
         * Returns the index of the palette with the highest card.
         */
        public int whoIsWinning(List<Card> playerAPalette, List<Card> playerBPalette) {
            //Card maxACard = Collections.max(playerAPalette);
            /*
            playerAPalette.sort((aCard, bCard) -> {
                return aCard.compareTo(bCard);
            });
            Card maxACard = playerAPalette.get(playerAPalette.size() - 1);
            */
            System.out.println("Red's whoIsWinning... comparing palettes!");
            System.out.println("playerAPalette: " + playerAPalette);
            System.out.println("playerBPalette: " + playerBPalette);
            Card maxACard = playerAPalette.get(0);
            for (Card card : playerAPalette) {
                if (card.compareTo(maxACard) > 0) {
                    maxACard = card;
                }
            }
            Card maxBCard = playerBPalette.get(0);
            for (Card card: playerBPalette) {
                if (card.compareTo(maxBCard) > 0) {
                    maxBCard = card;
                }
            }
            if (maxACard.compareTo(maxBCard) > 0) {
                return 0;
            } else {
                return 1;
            }
        }
    
    } //end of Red 
    
    /**
     * The color Orange.
     */
    public static class Orange extends CardColor {
        
        public Orange() {
        
        }
        
        protected int getColorRank() {
            return 6;
        }
        
        @Override
        public String toString() {
            return "Orange";
        }
        
        @Override
        public Color getFXColor(){
            return Color.ORANGE;
        }
        
        @Override
        public Color getFXTextColor(){
            return Color.BLACK;
        }
        
        /**
         * Returns the index of the palette with more of one color.
         */
        public int whoIsWinning(List<Card> playerAPalette, List<Card> playerBPalette) {
            System.out.println("Inside orange's version of whoIsWinning.");
            //quantities of each number in the palettes
            int[] playerAPaletteNumberQuantities = new int[] {0, 0, 0, 0, 0, 0, 0, 0}; //the zeroeth entry should stay zero
            int[] playerBPaletteNumberQuantities = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
            for (Card card : playerAPalette) {
                playerAPaletteNumberQuantities[card.getNumber()] += 1;
            }
            for (Card card : playerBPalette) {
                playerBPaletteNumberQuantities[card.getNumber()] += 1;
            }
            int maxAIndex = 0;
            int maxAQuantity = 0;
            int maxBIndex = 0;
            int maxBQuantity = 0;
            for (int i = 0; i < playerAPaletteNumberQuantities.length; i++) {
                if (playerAPaletteNumberQuantities[i] >= maxAQuantity) {
                    maxAQuantity = playerAPaletteNumberQuantities[i];
                    maxAIndex = i;
                }
                if (playerBPaletteNumberQuantities[i] >= maxBQuantity) {
                    maxBQuantity = playerBPaletteNumberQuantities[i];
                    maxBIndex = i;
                }
            }
            if (maxAQuantity > maxBQuantity) {
                return 0;
            } else if (maxBQuantity > maxAQuantity) {
                return 1;
            } else {
                //they have the same amount of their most frequent numbers
                if (maxAIndex > maxBIndex) {
                    return 0;
                } else if (maxAIndex < maxBIndex) {
                    return 1;
                } else {
                    //they have the same amount of their most frequent numbers, and those are the same number
                    
                    //find A's best color among the matching numbers
                    int maxAColorRank = 0;
                    for (Card card : playerAPalette) {
                        int aColorRank = card.getColor().getColorRank();
                        if (card.getNumber() == maxAIndex && aColorRank > maxAColorRank) {
                            maxAColorRank = aColorRank;
                        }
                    }
                    
                    //find B's best color among the matching numbers
                    int maxBColorRank = 0;
                    for (Card card : playerBPalette) {
                        int bColorRank = card.getColor().getColorRank();
                        if (card.getNumber() == maxBIndex && bColorRank > maxBColorRank) {
                            maxBColorRank = bColorRank;
                        }
                    }
                    
                    if (maxAColorRank > maxBColorRank) {
                        return 0;
                    } else {
                        return 1;
                    }
                    
                }
                
            }
        }
    
    } //end of Orange
    
    /**
     * The color Yellow.
     */
    public static class Yellow extends CardColor {
        
        public Yellow() {
        
        }
        
        protected int getColorRank() {
            return 5;
        }
        
        @Override
        public String toString() {
            return "Yellow";
        }
        
        @Override
        public Color getFXColor(){
            return Color.YELLOW;
        }
        
        @Override
        public Color getFXTextColor(){
            return Color.BLACK;
        }
        
        /**
         * Returns the index of the palette with more of one color.
         */
        public int whoIsWinning(List<Card> playerAPalette, List<Card> playerBPalette) {
            System.out.println("Inside yellow's version of whoIsWinning.");
            //quantities of each color in the palettes
            //From Red (index 0) to Violet (index 6)
            int[] playerAPaletteColorQuantities = new int[] {0, 0, 0, 0, 0, 0, 0};
            int[] playerBPaletteColorQuantities = new int[] {0, 0, 0, 0, 0, 0, 0};
            for (Card card : playerAPalette) {
                playerAPaletteColorQuantities[7-card.getColor().getColorRank()] += 1;
            }
            for (Card card : playerBPalette) {
                playerBPaletteColorQuantities[7-card.getColor().getColorRank()] += 1;
            }
            int maxAIndex = 0;
            int maxANumber = 0;
            int maxBIndex = 0;
            int maxBNumber = 0;
            for (int i = 0; i < playerAPaletteColorQuantities.length; i++) {
                if (playerAPaletteColorQuantities[i] > maxANumber) {
                    maxANumber = playerAPaletteColorQuantities[i];
                    maxAIndex = i;
                }
                if (playerBPaletteColorQuantities[i] > maxBNumber) {
                    maxBNumber = playerBPaletteColorQuantities[i];
                    maxBIndex = i;
                }
            }
            if (maxANumber > maxBNumber) {
                return 0;
            } else if (maxBNumber > maxANumber) {
                return 1;
            } else {
                //they have the same number of their best colors
                //return 0; //TODO: this is terrible!  We're not actually doing the thing here!!!!
                
                //this is our attempt to fix it! :)
                //first find player A's best color
                int maxAColorRank = 0;
                int maxAColorHighestNumber = 0;
                for (int i = 0; i < playerAPaletteColorQuantities.length; i++) {
                    int numAtColor = playerAPaletteColorQuantities[i];
                    if (numAtColor == maxANumber) {
                        int colorRank = 7-i; //because the indices are opposite the color ranks.  duh.
                        int colorHighestNumber = 0; //this will be the highest number of a card of that color in Player A's palette
                        for (Card card : playerAPalette) {
                            if (card.getColor().getColorRank() == colorRank) {
                                if (card.getNumber() > colorHighestNumber) {
                                    colorHighestNumber = card.getNumber();
                                }
                            }
                        }
                        if (colorHighestNumber > maxAColorHighestNumber || (colorHighestNumber == maxAColorHighestNumber && colorRank > maxAColorRank)) {
                            //we found a new best card in the matching colors, so use that instead
                            maxAColorRank = colorRank;
                            maxAColorHighestNumber = colorHighestNumber;
                        }
                    }
                }
                //now find player B's best color
                int maxBColorRank = 0;
                int maxBColorHighestNumber = 0;
                for (int i = 0; i < playerBPaletteColorQuantities.length; i++) {
                    int numAtColor = playerBPaletteColorQuantities[i];
                    if (numAtColor == maxBNumber) {
                        int colorRank = 7-i; //because the indices are opposite the color ranks.  duh.
                        int colorHighestNumber = 0; //this will be the highest number of a card of that color in Player A's palette
                        for (Card card : playerBPalette) {
                            if (card.getColor().getColorRank() == colorRank) {
                                if (card.getNumber() > colorHighestNumber) {
                                    colorHighestNumber = card.getNumber();
                                }
                            }
                        }
                        if (colorHighestNumber > maxBColorHighestNumber || (colorHighestNumber == maxBColorHighestNumber && colorRank > maxBColorRank)) {
                            //we found a new best card in the matching colors, so use that instead
                            maxBColorRank = colorRank;
                            maxBColorHighestNumber = colorHighestNumber;
                        }
                    }
                }
                
                if (maxAColorHighestNumber > maxBColorHighestNumber || (maxAColorHighestNumber == maxBColorHighestNumber && maxAColorRank > maxBColorRank)) {
                    return 0;
                } else {
                    return 1;
                }
                
            }
        }
    
    } //end of Yellow
    
    
    /**
     * The color Green.
     */
    public static class Green extends CardColor {
        
        public Green() {
        
        }
        
        protected int getColorRank() {
            return 4;
        }
        
        @Override
        public String toString() {
            return "Green";
        }
        
        @Override
        public Color getFXColor(){
            return Color.GREEN;
        }
        
        @Override
        public Color getFXTextColor(){
            return Color.BLACK;
        }
        
        /**
         * Returns the index of the palette with more evens.
         */
        public int whoIsWinning(List<Card> playerAPalette, List<Card> playerBPalette) {
            System.out.println("Inside Green's version of whoIsWinning.");
            
            List<Card> evensA = new ArrayList<Card>();
            for (Card card : playerAPalette) {
                if (card.getNumber() % 2 == 0) {
                    evensA.add(card);
                }
            }
            List<Card> evensB = new ArrayList<Card>();
            for (Card card : playerBPalette) {
                if (card.getNumber() % 2 == 0) {
                    evensB.add(card);
                }
            }
            if (evensA.size() > evensB.size()) {
                return 0;
            } else if (evensA.size() < evensB.size()) {
                return 1;
            } else {
                //the sizes are the same!
                return (new Red()).whoIsWinning(evensA, evensB);
            }
            
        }
    
    } //end of Green
    
    
    /**
     * The color Blue.
     */
    public static class Blue extends CardColor {
        
        public Blue() {
        
        }
        
        protected int getColorRank() {
            return 3;
        }
        
        @Override
        public String toString() {
            return "Blue";
        }
        
        @Override
        public Color getFXColor(){
            return Color.BLUE;
        }
        
        @Override
        public Color getFXTextColor(){
            return Color.WHITE;
        }
        
        /**
         * Returns the index of the palette with more evens.
         */
        public int whoIsWinning(List<Card> playerAPalette, List<Card> playerBPalette) {
            System.out.println("Inside Blue's version of whoIsWinning.");
            
            Set<CardColor> aColors = new TreeSet<CardColor>();
            for (Card card : playerAPalette) {
                aColors.add(card.getColor());
            }
            Set<CardColor> bColors = new TreeSet<CardColor>();
            for (Card card : playerBPalette) {
                bColors.add(card.getColor());
            }
            
            if (aColors.size() > bColors.size()) {
                return 0;
            } else if (aColors.size() < bColors.size()) {
                return 1;
            } else {
                //both players have the same number of colors
                return (new Red()).whoIsWinning(playerAPalette, playerBPalette);
            }
            
        }
    
    } //end of Blue
    
    /**
     * The color Violet.
     */
    public static class Violet extends CardColor {
        
        public Violet() {
        
        }
        
        protected int getColorRank() {
            return 1;
        }
        
        /**
         * Returns the index of the palette with more cards below 4.
         */
        public int whoIsWinning(List<Card> playerAPalette, List<Card> playerBPalette) {
            int numPlayerABelow4 = 0;
            Card bestCardForABelow4 = Card.createNullCard();
            for (Card card : playerAPalette) {
                if (card.getNumber() < 4) {
                    numPlayerABelow4 ++;
                    if (bestCardForABelow4.compareTo(card) < 0) {//bestCardForABelow4.getNumber() < card.getNumber() || bestCardForABelow4.getNumber() == card.getNumber() &&
                        bestCardForABelow4 = card;
                    }
                }
            }
            int numPlayerBBelow4 = 0;
            Card bestCardForBBelow4 = Card.createNullCard();
            for (Card card : playerBPalette) {
                if (card.getNumber() < 4) {
                    numPlayerBBelow4 ++;
                    if (bestCardForBBelow4.compareTo(card) < 0) {
                        bestCardForBBelow4 = card;
                    }
                }
            }
            if (numPlayerABelow4 == numPlayerBBelow4) {
                if (bestCardForABelow4.compareTo(bestCardForBBelow4) < 0) {
                    return 1;
                } else if (bestCardForABelow4.compareTo(bestCardForBBelow4) > 0) {
                    //no other else, because they cannot tie.
                    return 0;
                } else {
                    throw new RuntimeException("Ohno, we compared the cards of two players and they match!!!  CardA: " + bestCardForABelow4 + "  CardB: " + bestCardForBBelow4);
                }
            } else if (numPlayerABelow4 < numPlayerBBelow4) {
                return 1;
            } else {
                return 0;
            }
        }
        
        @Override
        public String toString() {
            return "Violet";
        }
        
        @Override
        public Color getFXColor(){
            return Color.VIOLET;
        }
        
        @Override
        public Color getFXTextColor(){
            return Color.WHITE;
        }
    
    } //end of Violet

} //end of CardColor class








