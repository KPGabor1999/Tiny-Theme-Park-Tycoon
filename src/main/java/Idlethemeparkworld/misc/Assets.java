package Idlethemeparkworld.misc;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Assets {
    
    public static enum Sounds {
        BGM("theme"),
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
        private final String filename;
        
        private Sounds(){
            this.filename = name().toLowerCase();
        }
        
        private Sounds(String filename){
            this.filename = filename;
        }
        
        public URL getSoundFile(){
            return ResourceLoader.loadResource(ASSETS_FOLDER_PATH + filename + ".wav");
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
        ROLLERCOASTER,
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
        
        NIGHT,
        SUNNY,
        RAINY,
        SNOWY,
        CLEAR,
        CLOUDY,
        
        JANITOR,
        MAINTAINER,
        
        NONE;

        private static final String ASSETS_FOLDER_PATH = "resources/";
        private BufferedImage asset;
        private BufferedImage nightAsset;
        private ArrayList<BufferedImage> assets;
        private ArrayList<BufferedImage> nightAssets;
        
        Texture(int versionCount) {
            asset = null;
            nightAsset = null;
            assets = new ArrayList<>();
            nightAssets = new ArrayList<>();
            for (int i = 1; i <= versionCount; i++) {
                String file = ASSETS_FOLDER_PATH + name().replace("_", "").toLowerCase() + i + ".png";
                String nightFile = ASSETS_FOLDER_PATH + name().replace("_", "").toLowerCase() + i + "night.png";
                try {
                    BufferedImage assetData = ResourceLoader.loadImage(nightFile);
                    nightAssets.add(assetData);
                } catch (Exception ex) {
                    
                }
                try {
                    BufferedImage assetData = ResourceLoader.loadImage(file);
                    assets.add(assetData);
                } catch (Exception ex) {
                }
            }
        }

        Texture() {
            if (!this.name().equals("NONE")) {
                asset = null;
                nightAsset = null;
                try {
                asset = ResourceLoader.loadImage(ASSETS_FOLDER_PATH + name().replace("_", "").toLowerCase() + ".png");
                } catch (Exception ex) {
                }
                try {
                    nightAsset = ResourceLoader.loadImage(ASSETS_FOLDER_PATH + name().replace("_", "").toLowerCase() + "night.png");
                } catch (Exception ex) {
                }
            } else {
                asset = null;
                nightAsset = null;
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
        
        public BufferedImage getNightAsset() {
            return nightAsset;
        }
        
        public ArrayList<BufferedImage> getNightAssets() {
            return nightAssets;
        }
    }
}
