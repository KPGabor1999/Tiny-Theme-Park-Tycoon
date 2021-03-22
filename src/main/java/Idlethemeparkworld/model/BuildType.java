package Idlethemeparkworld.model;

import Idlethemeparkworld.misc.Assets;
import Idlethemeparkworld.misc.Assets.Texture;
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
    PAVEMENT(1,1,"Pavement",1000,0,Assets.Texture.PAVEMENT),
    TRASHCAN(1,1,"Trash can",1000,0,Assets.Texture.TRASHCAN),
    TOILET(1,1,"Toilet",5000,0,Assets.Texture.TOILET),
    HOTDOGSTAND(1,1,"Hotdog stand",10000,0,Assets.Texture.HOTDOGSTAND),
    ICECREAMPARLOR(1,1,"Ice cream parlor",15000,0,Assets.Texture.ICECREAMPARLOR),
    BURGERJOINT(1,1,"Burger joint",25000,0,Assets.Texture.BURGERJOINT),
    CAROUSEL(1,1,"Carousel",15000,0,Assets.Texture.CAROUSEL),
    FERRISWHEEL(1,1,"Ferriswheel",25000,0,Assets.Texture.FERRISWHEEL),
    SWINGINGSHIP(1,1,"Swinging ship",20000,0,Assets.Texture.SWINGINGSHIP),
    ROLLERCOASTER(1,1,"Roller coaster",35000,0,Assets.Texture.ROLLERCOASTER),
    HAUNTEDMANSION(1,1,"Haunted mansion",20000,0,Assets.Texture.HAUNTEDMANSION),
    ENTRANCE(1,1,"Entrance",0,0,Assets.Texture.GATE),
    LOCKEDTILE(1,1,"Locked",10000,0,Assets.Texture.NONE);
    
    private final int width, length;
    private final String name;
    private final int buildCost, upgradeCost;
    private final Texture tex;
    
    BuildType(int w, int l, String name, int bc, int uc, Texture tex){
        this.width = w;
        this.length = l;
        this.name = name;
        this.buildCost = bc;
        this.upgradeCost = uc;
        this.tex = tex;
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

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public int getBuildCost() {
        return buildCost;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }
    
    public Texture getTexture(){
        return tex;
    }
}
