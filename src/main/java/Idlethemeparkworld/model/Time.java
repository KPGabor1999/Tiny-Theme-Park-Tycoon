package Idlethemeparkworld.model;

public class Time implements Updatable {

    private static final int MINUTE_DURATION = 24;

    private int ticks;

    public Time() {
        this.ticks = 0;
    }

    @Override
    public void update(long tickCount) {
        ticks++;
    }

    public int getTotalMinutes() {
        return ticks / MINUTE_DURATION;
    }

    public int getMinutes() {
        return getTotalMinutes() % 60;
    }

    public int getTotalHours() {
        return getTotalMinutes() / 60;
    }

    public int getHours() {
        return getTotalHours() % 24;
    }

    public int getDays() {
        return getTotalHours() / 24;
    }

    public void reset() {
        ticks = 0;
    }

    public static int convMinuteToTick(double min) {
        return (int) Math.round(min * MINUTE_DURATION);
    }
    
    public static String minutesToString(int mins) {
        int days = (mins/60) / 24;
        int hours = (mins/60) % 24;
        int minutes = mins % 60;
        return String.format("Day %d - %02d:%02d", days, hours, minutes);
    }

    public String toStringShort() {
        return String.format("%02d:%02d", getHours(), getMinutes());
    }

    @Override
    public String toString() {
        return String.format("Day %d - %02d:%02d", getDays(), getHours(), getMinutes());
    }
}
