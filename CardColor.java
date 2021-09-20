import java.lang.*;

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
        
        @Override
        public String toString() {
            return "Violet";
        }
    
    } //end of Violet

} //end of CardColor class
