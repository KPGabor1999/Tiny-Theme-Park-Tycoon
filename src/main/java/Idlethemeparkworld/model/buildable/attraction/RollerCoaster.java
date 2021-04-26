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
        this.upkeepCost = 20;
        this.fun = 30;
        this.capacity = 15;
        this.runtime = 2;
        this.entryFee = 25;
        this.baseEntryFee = 25;        
        this.upgradeCost = (int)(this.value * 0.75);
        this.soundFileName = "people_screams.wav";
    }

    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.fun *= 1.5;
                this.capacity += 4;
                this.runtime -= 0.5;
                this.upgradeCost *= 2;
                break;
            case 2:
                this.fun *= 1.5;
                this.capacity += 4;
                this.runtime -= 0.5;
                break;
            default:
                break;
        }
    }
}
