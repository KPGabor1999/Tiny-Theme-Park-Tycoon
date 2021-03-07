package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;

public class Toilet extends Infrastructure {
    private int cleanliness;
    
    public Toilet(){
        this.buildingType = BuildType.TOILET;
        this.name = "Toilet";
        this.cleanliness = 100;
    }
}
