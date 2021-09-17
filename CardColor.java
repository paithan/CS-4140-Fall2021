import java.lang.*;

/**
 * Represents a single color from the game Red7.
 *
 * @author CS4140 Fall 2021 class.
 */
public abstract class CardColor implements Comparable<CardColor> {

    @Override
    public abstract int compareTo(CardColor other);
    
    //returns the rank of the color
    private abstract int getColorRank();

    /**
     * Returns a string representation of this color.
     *
     * @return String represetnation of this.
     */
    public abstract String toString();
    
    /**
     * Main method for testing.
     */
    public static void main(String[] args) {
        CardColor red = new Red();
        System.out.println(red.toString());
    }
    
    /**
     * The color Red.
     */
    public static class Red extends CardColor {
        
        public Red() {
        
        }
        
        private int getColorRank() {
            return 7;
        }
        
        @Override
        public int compareTo(CardColor other) {
            return this.getColorRank() - other.getColorRank();
        }
        
        @Override
        public String toString() {
            return "Red";
        }
    
    } //end of Red 
    
    /**
     * The color Yellow.
     */
    public static class Yellow extends CardColor {
        
        public Yellow() {
        
        }
        
        @Override
        public int compareTo(CardColor other) {
            return 1;
        }
        
        private int getColorRank() {
            return 5;
        }
        
        @Override
        public String toString() {
            return "Yellow";
        }
    
    } //end of Yellow
    
    /**
     * The color Violet.
     */
    public static class Violet extends CardColor {
        
        public Violet() {
        
        }
        
        @Override
        public int compareTo(CardColor other) {
            return 1;
        }
        
        private int getColorRank() {
            return 1;
        }
        
        @Override
        public String toString() {
            return "Violet";
        }
    
    } //end of Violet

} //end of CardColor class
