package Idlethemeparkworld.model.buildable;

public abstract class Buildable {
    protected int buildingCost;
    protected int upgradeCost;
    protected int upKeepCost;
    protected boolean underConstruction;
    protected int condition;
    
    public abstract void build();
    public abstract void upgrade();
}
