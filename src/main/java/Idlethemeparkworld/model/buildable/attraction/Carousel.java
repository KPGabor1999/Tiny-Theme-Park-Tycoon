package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.misc.Assets.Sounds;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;

public class Carousel extends Attraction {

    public Carousel(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.CAROUSEL;
        this.value = buildingType.getBuildCost();
        this.upgradeCost = (int)(this.value * 0.75);

        this.upkeepCost = 6;
        this.fun = 12;
        this.capacity = 10;
        this.runtime = 3;
        this.entryFee = 10;
        this.baseEntryFee = 10;
        this.sound = Sounds.CAROUSEL;
    }

    /**
     * K�rhinta fejleszt�se.
     */
    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.fun *= 1.5;
                this.capacity += 5;
                this.upgradeCost *= 2;
                break;
            case 2:
                this.fun *= 1.5;
                this.capacity += 5;
                break;
            default:
                break;
        }
    }
}
