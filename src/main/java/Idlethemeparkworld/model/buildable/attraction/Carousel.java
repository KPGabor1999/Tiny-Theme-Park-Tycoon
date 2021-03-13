package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;

public class Carousel extends Attraction {
    
    //Az �p�leteknek ismerni�k k�ne a saj�t hely�ket, hogy t�r�lni lehessen �ket a t�bl�r�l.
    public Carousel(int XLocation, int YLocation){
        this.XLocation    = XLocation;
        this.YLocation    = YLocation;
        System.out.println("K�rhinta koordin�t�i: (" + this.XLocation + ", " + this.YLocation + ")");
        this.currentLevel = 1;
        this.buildingType = BuildType.CAROUSEL;
        this.upkeepCost   = 1;
        this.fun          = 1;
        this.capacity     = 1;
        this.occupied     = 0;
        this.runtime      = 1;
        this.entryFee     = 1;
        this.isRunning    = false;
        this.condition    = 100;
        this.upgradeCost  = 1;
    }
    
    @Override
    public void level2Upgrade(){
        this.currentLevel += 1;
        this.upkeepCost   += 1;
        this.fun          += 1;
        this.capacity     += 1;
        this.occupied     = 0;
        this.runtime      = 1;
        this.entryFee     = 1;
        this.isRunning    = false;
        this.condition    = 100;
        this.upgradeCost  *= 100;
    }
    
    @Override
    public void level3Upgrade(){
        this.currentLevel += 1;
        this.upkeepCost   += 1;
        this.fun          += 1;
        this.capacity     += 1;
        this.occupied     = 0;
        this.runtime      = 1;
        this.entryFee     = 1;
        this.isRunning    = false;
        this.condition    = 100;
        this.upgradeCost  = 0;
    }
    
    protected void start(){
        //TODO
    }
}
