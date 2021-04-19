package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;

public class RollerCoaster extends Attraction {

    public RollerCoaster(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.value = BuildType.ROLLERCOASTER.getBuildCost();
        this.buildingType = BuildType.ROLLERCOASTER;
        this.upkeepCost = 13;
        this.fun = 16;
        this.capacity = 10;
        this.runtime = 2;
        this.entryFee = 24;
        this.upgradeCost = this.value * 2;
    }

    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.fun *= 1.5;
                this.capacity += 4;
                this.runtime -= 1;
                this.upgradeCost *= 2;
                break;
            case 2:
                this.fun *= 1.5;
                this.capacity += 4;
                break;
            default:
                break;
        }
    }
}
