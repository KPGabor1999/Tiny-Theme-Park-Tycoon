package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;

public class Carousel extends Attraction {
    public Carousel(){
        this.buildingType = BuildType.CAROUSEL;
        this.name = "Carousel";
    }
}
