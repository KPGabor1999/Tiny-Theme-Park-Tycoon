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
import java.awt.Rectangle;

public enum BuildType {
    PAVEMENT(1, 1, "Pavement", "Use pavement tiles to connect buildings and help people get around.", 1000, Assets.Texture.PAVEMENT, new Rectangle(0,10,64,64)),
    TRASHCAN(1, 1, "Trash can", "", 1000, Assets.Texture.TRASHCAN, new Rectangle(0,10,64,64)),
    TOILET(1, 1, "Toilet", "", 5000, Assets.Texture.TOILET, new Rectangle(0,40,64,24)),
    ICECREAMPARLOR(2, 1, "Ice Cream parlor", "", 10000, Assets.Texture.ICECREAMPARLOR, new Rectangle(0,0,75,64)),
    HOTDOGSTAND(2, 1, "Hot Dog stand", "", 15000, Assets.Texture.HOTDOGSTAND, new Rectangle(54,5,74,59)),
    BURGERJOINT(2, 1, "Burger joint", "", 25000, Assets.Texture.BURGERJOINT, new Rectangle(34,0,94,64)),
    CAROUSEL(2, 2, "Carousel", "", 16000, Assets.Texture.CAROUSEL, new Rectangle(0,84,128,44)),
    SWINGINGSHIP(3, 2, "Swinging ship", "", 32000, Assets.Texture.SWINGINGSHIP, new Rectangle(0,96,128,32)),
    HAUNTEDMANSION(3, 2, "Haunted mansion", "", 32000, Assets.Texture.HAUNTEDMANSION, new Rectangle(0,86,128,42)),
    FERRISWHEEL(3, 3, "Ferriswheel", "", 48000, Assets.Texture.FERRISWHEEL, new Rectangle(0,138,192,54)),
    ROLLERCOASTER(3, 3, "Roller coaster", "", 60000, Assets.Texture.ROLLERCOASTER, new Rectangle(0,0,64,128)),
    ENTRANCE(1, 1, "Entrance", "", 0, Assets.Texture.GATE, new Rectangle(0,10,64,64)),
    LOCKEDTILE(1, 1, "Locked", "Unlock to use it as a building ground.", 5000, Assets.Texture.LOCKED, new Rectangle(0,10,64,64));

    private final int width, length;
    private final String name, description;
    private final int buildCost;
    private final Texture tex;
    private final Rectangle lobbyArea;

    BuildType(int w, int l, String name, String description, int bc, Texture tex, Rectangle lobbyArea) {
        this.width = w;
        this.length = l;
        this.name = name;
        this.description = description;
        this.buildCost = bc;
        this.tex = tex;
        this.lobbyArea = lobbyArea;
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
    
    public Rectangle getLobbyArea() {
        return lobbyArea;
    }
}
