package Idlethemeparkworld.misc;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Assets {
    
    public static enum Sounds {
        BGM,
        BOO_LAUGH,
        CAROUSEL,
        CASH_REGISTER,
        COG_SPINNING,
        CONSTRUCTION,
        CROWD_AMBIANCE,
        EXPLOSION,
        NATURE,
        NOM_NOM_NOM,
        PAPER_CRUMBLING,
        PEOPLE_SCREAMS,
        THEME,
        TOILET_FLUSH,
        UGH,
        WRONG_ANSWER,
        YOU_ARE_A_PIRATE,
        
        NONE;

        private static final String ASSETS_FOLDER_PATH = "resources/sounds/";
        
        public URL getSoundFile(){
            return ResourceLoader.loadResource(ASSETS_FOLDER_PATH + name().toLowerCase() + ".wav");
        }
    }

    public static enum Texture {
        BURGERJOINT(3),
        CAROUSEL(3),
        FERRISWHEEL(3),
        GATE,
        GRASS,
        HAUNTEDMANSION(3),
        HOTDOGSTAND(3),
        ICECREAMPARLOR(3),
        PAVEMENT,
        ROLLERCOASTER(3),
        SWINGINGSHIP(3),
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
        POPUP,
        
        JANITOR,
        MAINTAINER,
        
        NONE;

        private static final String ASSETS_FOLDER_PATH = "resources/";
        private BufferedImage asset;
        private ArrayList<BufferedImage> assets;
        
        Texture(String path) {
            if (!this.name().equals("NONE")) {
                load(path);
            } else {
                asset = null;
            }
        }
        
        Texture(int versionCount) {
            asset = null;
            assets = new ArrayList<>();
            for (int i = 1; i <= versionCount; i++) {
                String file = ASSETS_FOLDER_PATH + name().replace("_", "").toLowerCase() + i + ".png";
                try {
                    BufferedImage assetData = ResourceLoader.loadImage(file);
                    assets.add(assetData);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        Texture() {
            if (!this.name().equals("NONE")) {
                load(ASSETS_FOLDER_PATH + name().replace("_", "").toLowerCase() + ".png");
            } else {
                asset = null;
            }
        }

        private void load(String path) {
            try {
                asset = ResourceLoader.loadImage(path);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        /**
         * @return an image containing the corresponding visuals
         */
        public BufferedImage getAsset() {
            return asset;
        }
        
        public ArrayList<BufferedImage> getAssets() {
            return assets;
        }
    }
}
