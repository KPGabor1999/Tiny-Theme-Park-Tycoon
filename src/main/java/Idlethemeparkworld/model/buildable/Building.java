package Idlethemeparkworld.model.buildable;

public abstract class Building extends Buildable{
    protected int x, y;
    protected int currentLevel;
    public final int maxLevel = 3;
    protected int upgradeCost;
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
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
    
    public void level2Upgrade(){}   //these 3 should be abstract
    public void level3Upgrade(){}
    public void interact(){}        //arent't the visitors supposed to have this?
}
