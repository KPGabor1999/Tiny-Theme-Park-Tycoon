package Idlethemeparkworld.model.buildable.attraction;

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
        this.capacity = 30;
        this.runtime = 9;
        this.entryFee = 15;
        this.upgradeCost = this.value * 2;
    }

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
