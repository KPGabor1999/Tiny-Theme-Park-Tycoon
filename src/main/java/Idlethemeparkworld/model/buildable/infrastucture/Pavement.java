package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;

public class Pavement extends Infrastructure {
    public Pavement(){
        this.buildingType = BuildType.PAVEMENT;
        this.name = "Pavement";
        this.buildingCost = 1000;
    }
}