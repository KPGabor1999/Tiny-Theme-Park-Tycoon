package Idlethemeparkworld.misc.utils;

import java.util.Random;

public class Range {

    private final Random rand;
    private int low;
    private int high;

    public Range(int a, int b) {
        this.rand = new Random();
        setRange(a, b);
    }

    public void setRange(int a, int b) {
        low = Math.min(a, b);
        high = Math.max(a, b);
    }

    public boolean inRange(int n) {
        return low <= n && n <= high;
    }

    public int getNextRandom() {
        return rand.nextInt(high - low + 1) + low;
    }

    public void add(Range r) {
        low += r.low;
        high += r.high;
    }

    public void add(int a, int b) {
        low += a;
        high += b;
    }

    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }
}
