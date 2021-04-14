package Idlethemeparkworld.model.buildable;

import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;

public abstract class Building extends Buildable{
    protected BuildingStatus status;
    protected int x, y;
    protected int value;
    protected int currentLevel;
    protected int maxLevel;
    protected int upgradeCost;
    protected double condition;

    public Building(GameManager gm) {
        super(gm);
        this.status = BuildingStatus.OPEN;
        this.maxLevel     = 3;
        this.currentLevel = 1;
        this.condition = 100;
    }
    
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
    
    public boolean canUpgrade(){
        return currentLevel < maxLevel;
    }
    
    public void upgrade(){
        if(canUpgrade()){
            innerUpgrade();
            currentLevel++;
            value += upgradeCost;
        }
    }
    
    protected void innerUpgrade(){}
    
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
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
}
