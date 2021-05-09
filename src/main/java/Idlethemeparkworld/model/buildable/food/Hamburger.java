package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;

public class Hamburger extends FoodStall {

    public Hamburger(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.value = BuildType.BURGERJOINT.getBuildCost();
        this.buildingType = BuildType.BURGERJOINT;
        this.serviceTime = Time.convMinuteToTick(1);
        this.foodPrice = 15;
        this.foodQuality.setRange(50, 90);
        this.upkeepCost = 15;
        this.upgradeCost = (int) (this.value * 0.75);
    }

    /**
     * Hamburgerezõ fejlesztése.
     */
    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.serviceTime += Time.convMinuteToTick(0.1);
                this.foodQuality.add(5, 5);
                this.upkeepCost += 10;
                this.upgradeCost *= 2;
                break;
            case 2:
                this.serviceTime += Time.convMinuteToTick(0.1);
                this.foodQuality.add(5, 5);
                this.upkeepCost += 10;
                break;
            default:
                break;
        }
    }
}
