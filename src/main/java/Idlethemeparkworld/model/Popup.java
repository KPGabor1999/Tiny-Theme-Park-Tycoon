package Idlethemeparkworld.model;

import Idlethemeparkworld.misc.utils.Circle;

/**
 * Popup system
 */
public class Popup implements Updatable{
    private Circle circle;
    private int durationSeconds;

    /**
     * Create a new popup with a given hitbox and duration
     * @param circle circle hitbox
     */
    public Popup(Circle circle) {
        this.circle = circle;
        this.durationSeconds = 12;
    }
    
    @Override
    public void update(long tickCount) {
        durationSeconds--;
    }

    public Circle getCircle() {
        return circle;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }
}
