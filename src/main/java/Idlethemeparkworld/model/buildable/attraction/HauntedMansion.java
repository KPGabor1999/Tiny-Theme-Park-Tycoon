package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.misc.Assets;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;

public class HauntedMansion extends Attraction {

    public HauntedMansion(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.value = BuildType.HAUNTEDMANSION.getBuildCost();
        this.buildingType = BuildType.HAUNTEDMANSION;
        this.upkeepCost = 15;
        this.fun = 15;
        this.capacity = 20;
        this.runtime = 6;
        this.entryFee = 15;
        this.baseEntryFee = 15;
        this.upgradeCost = (int)(this.value * 0.75);
        this.sound = Assets.Sounds.BOO_LAUGH;
    }

    /**
     * Kísértetkastély frissítése.
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
