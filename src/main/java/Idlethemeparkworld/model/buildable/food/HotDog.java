package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;

public class HotDog extends FoodStall {

    public HotDog(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.value = BuildType.HOTDOGSTAND.getBuildCost();
        this.buildingType = BuildType.HOTDOGSTAND;
        this.serviceTime = Time.convRealLifeSecondToTick(1);
        this.foodPrice = 10;
        this.foodQuality.setRange(40, 75);
        this.upkeepCost = 13;
        this.upgradeCost = (int) (this.value * 0.75);
    }

    /**
     * Hotdogstand fejlesztése.
     */
    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.serviceTime += Time.convRealLifeSecondToTick(0.3);
                this.foodQuality.add(5, 5);
                this.upkeepCost += 5;
                this.upgradeCost *= 2;
                break;
            case 2:
                this.serviceTime += Time.convRealLifeSecondToTick(0.3);
                this.foodQuality.add(5, 5);
                this.upkeepCost += 5;
                break;
            default:
                break;
        }
    }
}
