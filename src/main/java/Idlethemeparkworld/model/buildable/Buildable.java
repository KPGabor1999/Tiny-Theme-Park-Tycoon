package Idlethemeparkworld.model.buildable;

import Idlethemeparkworld.model.BuildType;

public abstract class Buildable {
    protected BuildType buildingType; 
    protected int buildingCost;
    protected int upgradeCost;
    protected int upkeepCost;
    protected boolean underConstruction;

    public BuildType getBuildingType() {
        return buildingType;
    }

    public int getBuildingCost() {
        return buildingCost;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public int getUpkeepCost() {
        return upkeepCost;
    }

    public boolean isUnderConstruction() {
        return underConstruction;
    }
    
    
    
    public abstract void build(); //i'm not actually sure if we actually need this
    public abstract void upgrade();
}
