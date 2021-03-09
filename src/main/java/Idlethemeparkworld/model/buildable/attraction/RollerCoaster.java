package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;

public class RollerCoaster extends Attraction {
    public RollerCoaster(){
        this.buildingType = BuildType.ROLLERCOASTER;
        this.name = "Roller coaster";
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
