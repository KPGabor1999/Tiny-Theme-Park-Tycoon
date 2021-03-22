package Idlethemeparkworld.misc;

public class Assets {
    public static enum Texture {
        BURGERJOINT,
        CAROUSEL,
        FERRISWHEEL,
        GATE,
        GRASS,
        HAUNTEDMANSION,
        HOTDOGSTAND,
        ICECREAMPARLOR,
        PAVEMENT,
        ROLLERCOASTER,
        SWINGINGSHIP,
        TOILET,
        TRASHCAN
        ;

        private String filename;        

        Texture(String path) {
            this.filename = path;
        }

        Texture() {
            this.filename = name().replace("_", "").toLowerCase();
        }
    }
}
