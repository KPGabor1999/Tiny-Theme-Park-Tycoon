package Idlethemeparkworld.model;

import Idlethemeparkworld.model.buildable.attraction.Carousel;
import Idlethemeparkworld.model.buildable.attraction.FerrisWheel;
import Idlethemeparkworld.model.buildable.attraction.HauntedMansion;
import Idlethemeparkworld.model.buildable.attraction.RollerCoaster;
import Idlethemeparkworld.model.buildable.attraction.SwingingShip;
import Idlethemeparkworld.model.buildable.food.Hamburger;
import Idlethemeparkworld.model.buildable.food.HotDog;
import Idlethemeparkworld.model.buildable.food.IceCream;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;

public enum BuildType {
    PAVEMENT(1,1),
    TRASHCAN(1,1),
    TOILET(1,1),
    HOTDOGSTAND(1,1),
    ICECREAMPARLOR(1,1),
    BURGERJOINT(1,1),
    CAROUSEL(1,1),
    FERRISWHEEL(1,1),
    SWINGINGSHIP(1,1),
    ROLLERCOASTER(1,1),
    HAUNTEDMANSION(1,1),
    ENTRANCE(1,1);
    
    private final int width, length;
    
    BuildType(int w, int l){
        this.width = w;
        this.length = l;
    }
    
    public static Class GetClass(BuildType type) {
        switch (type){
            case PAVEMENT:
                return Pavement.class;
            case TRASHCAN:
                return TrashCan.class;
            case TOILET:
                return Toilet.class;
            case HOTDOGSTAND:
                return HotDog.class;
            case ICECREAMPARLOR:
                return IceCream.class;
            case BURGERJOINT:
                return Hamburger.class;
            case CAROUSEL:
                return Carousel.class;
            case FERRISWHEEL:
                return FerrisWheel.class;
            case SWINGINGSHIP:
                return SwingingShip.class;
            case ROLLERCOASTER:
                return RollerCoaster.class;
            case HAUNTEDMANSION:
                return HauntedMansion.class;
            case ENTRANCE:
                return Entrance.class;
            default:
                return null;
        }
    }
}
