package Idlethemeparkworld.model;

/**
 * All instances of this should be updated by the tick based update cycle
 */
public interface Updatable {

    /**
     * Updates the current component
     * @param tickCount The current tickCount of the update cycle
     */
    public void update(long tickCount);
}
