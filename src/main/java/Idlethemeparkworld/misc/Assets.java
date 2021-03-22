package Idlethemeparkworld.misc;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        private static final String ASSETS_FOLDER_PATH = "assets/";
        private String filename;     
        private Image asset;

        Texture(String path) {
            this.filename = ASSETS_FOLDER_PATH + path;
            load();
        }

        Texture() {
            this.filename = ASSETS_FOLDER_PATH + name().replace("_", "").toLowerCase();
            load();
        }
        
        private void load(){
            try {
                asset = ResourceLoader.loadImage(filename);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        private Image getAsset(){
            return asset;
        } 
    }
}
