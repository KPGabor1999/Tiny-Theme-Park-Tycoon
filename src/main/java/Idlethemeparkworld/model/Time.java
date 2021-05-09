package Idlethemeparkworld.model;

/**
 * A time class that holds all utility functions connected to in game time and converters between
 * real life time, in game time and in game ticks.
 */
public class Time implements Updatable {

    private static final int MINUTE_DURATION = 6;

    private int ticks;

    /**
     * Creates a new time tracker
     */
    public Time() {
        this.ticks = 0;
    }

    @Override
    public void update(long tickCount) {
        ticks++;
    }

    /**
     * @return Get the total amount of minutes elapsed
     */
    public int getTotalMinutes() {
        return ticks / MINUTE_DURATION;
    }

    /**
     * @return the minutes in a hour:minute form
     */
    public int getMinutes() {
        return getTotalMinutes() % 60;
    }

    /**
     * @return Get the total amount of hours elapsed
     */
    public int getTotalHours() {
        return getTotalMinutes() / 60;
    }

    /**
     * @return the hours in a hour:minute form
     */
    public int getHours() {
        return getTotalHours() % 24;
    }

    /**
     * @return the days in a hour:minute form
     */
    public int getDays() {
        return getTotalHours() / 24;
    }

    /**
     * Resets the time tracking to 0. Makes it day 0 - 0:00.
     */
    public void reset() {
        ticks = 0;
    }

    /**
     * Converts in game minutes to tick counts
     * @param min The number of in game minutes
     * @return the converted tick counts
     */
    public static int convMinuteToTick(double min) {
        return (int) Math.round(min * MINUTE_DURATION);
    }
    
    public static int convRealLifeSecondToTick(double seconds) {
        return (int) Math.round(seconds * 24);
    }
    
    /**
     * Converts in game minutes to a stylized Day x - HH:MM format.
     * @param mins The total number of minutes elapsed
     * @return formatted time.
     */
    public static String minutesToString(int mins) {
        int days = (mins/60) / 24;
        int hours = (mins/60) % 24;
        int minutes = mins % 60;
        return String.format("Day %d - %02d:%02d", days, hours, minutes);
    }

    /**
     * @return shortened formatted time.
     */
    public String toStringShort() {
        return String.format("%02d:%02d", getHours(), getMinutes());
    }

    @Override
    public String toString() {
        return String.format("Day %d - %02d:%02d", getDays(), getHours(), getMinutes());
    }
}
