package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;

public class FishChip extends FoodStall {
    
    public FishChip(int x, int y, GameManager gm){
        super(gm);
        this.x    = x;
        this.y    = y;
        this.value        = BuildType.BURGERJOINT.getBuildCost();
        this.buildingType = BuildType.BURGERJOINT;
        this.serviceTime  = Time.convMinuteToTick(1);
        this.foodPrice    = 1;        //customers have a preferred pricepoint, if it's higher than that their happiness goes down a bit
        this.foodQuality.setRange(10, 15);
        this.upkeepCost   = 1;        //The total of its employees wages in dollars/hour.
        this.upgradeCost  = 1;
    }
    
    @Override
    public void level2Upgrade(){
        this.upkeepCost   += 1;
        this.serviceTime  += Time.convMinuteToTick(0.3);
        this.foodPrice    = 1;        //customers have a preferred pricepoint, if it's higher than that their happiness goes down a bit
        this.foodQuality.add(5, 5);
        this.upgradeCost  *= 10;
    }
    
    @Override
    public void level3Upgrade(){
        this.upkeepCost   += 1;
        this.serviceTime  += 1;
        this.foodPrice    = 1;        //customers have a preferred pricepoint, if it's higher than that their happiness goes down a bit
        this.foodQuality.add(5, 5);
    }
}
