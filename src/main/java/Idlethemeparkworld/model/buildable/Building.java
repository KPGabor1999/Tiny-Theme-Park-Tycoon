package Idlethemeparkworld.model.buildable;

import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;

public abstract class Building extends Buildable{
    protected GameManager gm;           //All Building should know.
    protected BuildingStatus status;
    protected int x, y;
    protected int value;
    protected int currentLevel;
    protected int maxLevel = 3;
    protected int upgradeCost;

    //for debugging and prototyping
    public void setStatus(BuildingStatus status){
        this.status = status;
    }
    
    public BuildingStatus getStatus(){
        return status;
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

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }
    
    public boolean canUpgrade(){
        return currentLevel < maxLevel;
    }
    
    public void upgrade(){
        switch(currentLevel){
            case 1: level2Upgrade(); break;
            case 2: level3Upgrade(); break;
            default: break;
        }
    }

    //protected abstract void innerUpgrade();
    
    public abstract void level2Upgrade();   //these 3 should be abstract
    public abstract void level3Upgrade();
    
    public abstract ArrayList<Pair<String, String>> getAllData();

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
        return true;
    }
    
    
}
