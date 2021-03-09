package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;

public class FerrisWheel extends Attraction  {
    
    public FerrisWheel(){
        this.buildingType = BuildType.FERRISWHEEL;
        this.name = "Ferris Wheel";
        this.buildingCost = 15000;      //Balance these later.
        this.upgradeCost = 30000;
        this.upkeepCost = 8;
        this.fun = 30;
        this.capacity = 20;
        this.occupied = 0;
        this.runtime = 300;
        this.entryFee = 10;
        this.isRunning = false;
        this.condition = 100;
    }
}
