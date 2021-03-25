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
import Idlethemeparkworld.model.buildable.infrastucture.LockedTile;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;

public enum BuildType {
    PAVEMENT(1,1,"Pavement","Use pavement tiles to connect buildings and help people get around.",1000,Assets.Texture.PAVEMENT),
    TRASHCAN(1,1,"Trash can","",1000,Assets.Texture.TRASHCAN),
    TOILET(1,1,"Toilet","",5000,Assets.Texture.TOILET),
    HOTDOGSTAND(2,1,"Hotdog stand","",10000,Assets.Texture.HOTDOGSTAND),
    ICECREAMPARLOR(2,1,"Ice cream parlor","",15000,Assets.Texture.ICECREAMPARLOR),
    BURGERJOINT(2,1,"Burger joint","",25000,Assets.Texture.BURGERJOINT),
    CAROUSEL(2,2,"Carousel","",15000,Assets.Texture.CAROUSEL),
    FERRISWHEEL(3,3,"Ferriswheel","",25000,Assets.Texture.FERRISWHEEL),
    SWINGINGSHIP(3,3,"Swinging ship","",20000,Assets.Texture.SWINGINGSHIP),
    ROLLERCOASTER(3,2,"Roller coaster","",35000,Assets.Texture.ROLLERCOASTER),
    HAUNTEDMANSION(3,3,"Haunted mansion","",20000,Assets.Texture.HAUNTEDMANSION),
    ENTRANCE(1,1,"Entrance","",0,Assets.Texture.GATE),
    LOCKEDTILE(1,1,"Locked","Unlock to use it as a building ground.",10000,Assets.Texture.LOCKED);
    
    private final int width, length;
    private final String name, description;
    private final int buildCost;
    private final Texture tex;
    
    BuildType(int w, int l, String name, String description, int bc, Texture tex){
        this.width = w;
        this.length = l;
        this.name = name;
        this.description = description;
        this.buildCost = bc;
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
            case LOCKEDTILE:
                return LockedTile.class;
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
    
    public String getDescription() {
        return description;
    }

    public int getBuildCost() {
        return buildCost;
    } 
    
    public Texture getTexture(){
        return tex;
    }
}
