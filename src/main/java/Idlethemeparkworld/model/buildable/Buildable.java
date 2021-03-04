package Idlethemeparkworld.model.buildable;

public abstract class Buildable {
    protected int buildingCost;
    protected int upgradeCost;
    protected int upKeepCost;
    protected boolean underConstruction;
    protected int condition;
    
    public abstract void build(); //i'm not actually sure if we actually need this
    public abstract void upgrade();
}
