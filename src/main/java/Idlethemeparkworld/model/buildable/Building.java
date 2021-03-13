package Idlethemeparkworld.model.buildable;

public abstract class Building extends Buildable{
    protected int XLocation, YLocation;
    protected int currentLevel;
    public final int maxLevel = 3;
    protected int upgradeCost;

    public int getXLocation() {
        return XLocation;
    }

    public int getYLocation() {
        return YLocation;
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
    
    
    
    public void upgrade(){
        switch(currentLevel){
            case 1: level2Upgrade(); break;
            case 2: level3Upgrade(); break;
            default: //Upgrade button disabled
        }
    }
    
    public abstract void level2Upgrade();   //these 3 should be abstract
    public abstract void level3Upgrade();
    //public abstract void interact();                 //arent't the visitors supposed to have this?
}
