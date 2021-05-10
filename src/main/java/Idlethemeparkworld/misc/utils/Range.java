package Idlethemeparkworld.misc.utils;

import java.util.Random;

/**
 * A range class to generate random numbers within a range
 */
public class Range {

    private final Random rand;
    private int low;
    private int high;

    /**
     * Creates a new range generator in between a(inclusive) and b(exclusive)
     * @param a The lower bound(inclusive)
     * @param b The upper bound(exclusive)
     */
    public Range(int a, int b) {
        this.rand = new Random();
        setRange(a, b);
    }

    /**
     * Sets the range min and max to a(inclusive) and b(exclusive)
     * @param a The lower bound(inclusive)
     * @param b The upper bound(exclusive)
     */
    public void setRange(int a, int b) {
        low = Math.min(a, b);
        high = Math.max(a, b);
    }

    /**
     * @param n The number to check
     * @return if the given number is withing the range
     */
    public boolean inRange(int n) {
        return low <= n && n <= high;
    }

    /**
     * @return a random number withing the range
     */
    public int getNextRandom() {
        return rand.nextInt(high - low + 1) + low;
    }

    /**
     * Increase the lower bound by a and upper bound by b.
     * @param a The lower bound change
     * @param b The upper bound change
     */
    public void add(int a, int b) {
        low += a;
        high += b;
    }
    
    public Range newRangeByMultiplier(double aMulti, double bMulti) {
        return new Range((int)Math.round(low*aMulti),(int)Math.round(high*bMulti));
    }
    
    public Range newRangeByMultiplier(double multiplier) {
        return new Range((int)Math.round(low*multiplier),(int)Math.round(high*multiplier));
    }

    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }
}
