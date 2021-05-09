package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.misc.Assets;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;

public class FerrisWheel extends Attraction {

    public FerrisWheel(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.value = BuildType.FERRISWHEEL.getBuildCost();
        this.buildingType = BuildType.FERRISWHEEL;
        this.upkeepCost = 18;
        this.fun = 25;
        this.capacity = 25;
        this.runtime = 7;
        this.entryFee = 20;
        this.baseEntryFee = 20;        
        this.upgradeCost = (int)(this.value * 0.75);
        this.sound = Assets.Sounds.COG_SPINNING;
    }

    /**
     * Óriáskerék fejlesztése.
     */
    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.fun *= 1.5;
                this.runtime -= 1;                
                this.upgradeCost *= 2;
                break;
            case 2:
                this.fun *= 1.5;
                this.runtime -= 1;    
                this.upgradeCost *= 2;                
                break;
            case 3:
                this.fun *= 1.5;
                this.runtime -= 1;                    
                break;
            default:
                break;
        }
    }
}
