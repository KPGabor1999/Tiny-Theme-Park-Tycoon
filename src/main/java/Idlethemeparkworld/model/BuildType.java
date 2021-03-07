package Idlethemeparkworld.model;

public enum BuildType {
    PAVEMENT(1,1),
    TRASHCAN(1,1),
    TOILET(1,1),
    HOTDOGSTAND(1,1),
    ICECREAMPARLOR(1,1),
    BURGERJOINT(1,1),
    CAROUSEL(1,1),
    FERRISWHEEL(1,1),
    SWINGINGSHIP(1,1),
    TOLLERCOASTER(1,1),
    HAUNTEDMANSION(1,1);
    
    private final int width, length;
    
    BuildType(int w, int l){
        this.width = w;
        this.length = l;
    }
    
    
}
