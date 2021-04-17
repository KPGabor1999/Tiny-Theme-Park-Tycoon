package Idlethemeparkworld.misc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
        TRASHCAN,
        LOCKED,
        NOPATH,
        
        NPC1,
        NPC2,
        NPC3,
        NPC4,
        NPC5,
        NPC6,
        NPC7,
        NPC8,
        NPC9,
        NPC10,
        
        JANITOR,
        MAINTAINER,
        
        NONE;

        private static final String ASSETS_FOLDER_PATH = "resources/";
        private String filename;
        private BufferedImage asset;

        Texture(String path) {
            if (!this.name().equals("NONE")) {
                this.filename = path;
                load();
            } else {
                filename = null;
                asset = null;
            }
        }

        Texture() {
            if (!this.name().equals("NONE")) {
                this.filename = ASSETS_FOLDER_PATH + name().replace("_", "").toLowerCase() + ".png";
                load();
            } else {
                filename = null;
                asset = null;
            }
        }

        private void load() {
            try {
                asset = ResourceLoader.loadImage(filename);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public Image getAsset() {
            return asset;
        }
    }
}
