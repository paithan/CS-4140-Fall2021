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
    public abstract int whoIsWinning(ArrayList<Card> playerAPalette, ArrayList<Card> playerBPalette);
    
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
        public int whoIsWinning(ArrayList<Card> playerAPalette, ArrayList<Card> playerBPalette) {
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
        public int whoIsWinning(ArrayList<Card> playerAPalette, ArrayList<Card> playerBPalette) {
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
                return 0; //TODO: this is terrible!  We're not actually doing the thing here!!!!
                
            }
        }
    
    } //end of Yellow
    
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
        public int whoIsWinning(ArrayList<Card> playerAPalette, ArrayList<Card> playerBPalette) {
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








