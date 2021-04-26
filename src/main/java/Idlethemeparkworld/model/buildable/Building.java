package Idlethemeparkworld.model.buildable;

import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.GameManager;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class Building extends Buildable {

    protected BuildingStatus status;
    protected int x, y;
    protected int value;
    protected int currentLevel;
    protected int maxLevel;
    protected int upgradeCost;
    
    protected boolean visited;
    
    protected String soundFileName;
 
    public Building(GameManager gm) {
        super(gm);
        this.status = BuildingStatus.OPEN;
        this.maxLevel = 3;
        this.currentLevel = 1;
        this.soundFileName = "";
    }

    public void setStatus(BuildingStatus status) {
        this.status = status;
    }

    public BuildingStatus getStatus() {
        return status;
    }

    public Position getPos(){
        return new Position(x, y);
    }
    
    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public abstract int getRecommendedMax();

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }
    
    public void playConstructionSound(){
        File file = new File("C:\\Users\\KrazyXL\\idle-theme-park-world\\src\\main\\resources\\resources\\sounds\\construction.wav");
        
        AudioInputStream audioIn;
        try {
            audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip;
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            System.err.println("A megadott hangfájl nem támogatott!");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("IOException");
        } catch (LineUnavailableException ex) {
            System.err.println("LineUnavailableException");
        }
    }

    public boolean canUpgrade() {
        return currentLevel < maxLevel;
    }

    public void upgrade() {
        if (canUpgrade()) {
            innerUpgrade();
            currentLevel++;
            value += upgradeCost;
            gm.checkWin();
        }
    }

    protected void innerUpgrade() {}

    public abstract ArrayList<Pair<String, String>> getAllData();
    
    public void playSound(){
        File file = new File("C:\\Users\\KrazyXL\\idle-theme-park-world\\src\\main\\resources\\resources\\sounds\\" + soundFileName);
        
        AudioInputStream audioIn;
        try {
            audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip;
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            System.err.println("A megadott hangfájl nem támogatott!");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("IOException");
        } catch (LineUnavailableException ex) {
            System.err.println("LineUnavailableException");
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Building other = (Building) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }
}
