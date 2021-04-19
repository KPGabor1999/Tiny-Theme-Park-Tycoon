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
    PAVEMENT(1, 1, "Pavement", "Use pavement tiles to connect buildings and help people get around.", 1000, Assets.Texture.PAVEMENT, 0, 0),
    TRASHCAN(1, 1, "Trash can", "", 1000, Assets.Texture.TRASHCAN, 0, 0),
    TOILET(1, 1, "Toilet", "", 5000, Assets.Texture.TOILET, 0, 0),
    ICECREAMPARLOR(2, 1, "Ice Cream parlor", "", 10000, Assets.Texture.ICECREAMPARLOR, 25, 10),
    HOTDOGSTAND(2, 1, "Hot Dog stand", "", 15000, Assets.Texture.HOTDOGSTAND, 50, 20),
    BURGERJOINT(2, 1, "Burger joint", "", 25000, Assets.Texture.BURGERJOINT, 50, 20),
    CAROUSEL(2, 2, "Carousel", "", 16000, Assets.Texture.CAROUSEL, 20, 20),
    SWINGINGSHIP(3, 2, "Swinging ship", "", 32000, Assets.Texture.SWINGINGSHIP, 10, 20),
    HAUNTEDMANSION(3, 2, "Haunted mansion", "", 32000, Assets.Texture.HAUNTEDMANSION, 80, 80),
    FERRISWHEEL(3, 3, "Ferriswheel", "", 48000, Assets.Texture.FERRISWHEEL, 78, 78),
    ROLLERCOASTER(3, 3, "Roller coaster", "", 60000, Assets.Texture.ROLLERCOASTER, 90, 90),
    ENTRANCE(1, 1, "Entrance", "", 0, Assets.Texture.GATE, 0, 0),
    LOCKEDTILE(1, 1, "Locked", "Unlock to use it as a building ground.", 5000, Assets.Texture.LOCKED, 0, 0);

    private final int width, length;
    private final String name, description;
    private final int buildCost;
    private final Texture tex;
    private final int queueX, queueY;

    BuildType(int w, int l, String name, String description, int bc, Texture tex, int qX, int qY) {
        this.width = w;
        this.length = l;
        this.name = name;
        this.description = description;
        this.buildCost = bc;
        this.tex = tex;
        this.queueX = qX;
        this.queueY = qY;
    }

    public static Class GetClass(BuildType type) {
        switch (type) {
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

    public Texture getTexture() {
        return tex;
    }
    
    public int getQueueX(){
        return queueX;
    }
    
    public int getQueueY(){
        return queueY;
    }
}
