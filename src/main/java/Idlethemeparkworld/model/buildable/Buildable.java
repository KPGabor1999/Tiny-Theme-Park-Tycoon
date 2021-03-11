package Idlethemeparkworld.model.buildable;

import Idlethemeparkworld.model.BuildType;

public abstract class Buildable {
    protected BuildType buildingType; 
    protected int upkeepCost;
    protected boolean underConstruction;

    public BuildType getInfo() {
        return buildingType;
    }

    public int getUpkeepCost() {
        return upkeepCost;
    }

    public boolean isUnderConstruction() {
        return underConstruction;
    }
    
    public abstract void upgrade();
}
