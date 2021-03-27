package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.BuildType;
import java.util.ArrayList;

public class IceCream extends FoodStall {
    
    public IceCream(int xLocation, int yLocation){
        this.x    = xLocation;
        this.y    = yLocation;
        this.value        = BuildType.ICECREAMPARLOR.getBuildCost();
        this.currentLevel = 1;
        this.buildingType = BuildType.ICECREAMPARLOR;
        this.waitingLine  = new ArrayList<>();
        this.capacity     = 2;
        this.foodPrice    = 5;        //customers have a preferred pricepoint, if it's higher than that their happiness goes down a bit
        this.foodQuality  = 5;
        this.upkeepCost   = 2 * 10;        //The total of its employees wages in dollars/hour.
        this.upgradeCost  = this.value*2;
    }
    
    @Override
    public void level2Upgrade(){
        this.currentLevel += 1;
        this.capacity++;
        this.foodQuality  *= 1.5;
        this.upkeepCost   += 10;
        this.upgradeCost  *= 2;
    }
    
    @Override
    public void level3Upgrade(){
        this.currentLevel += 1;
        this.capacity++;
        this.foodQuality  *= 1.5;
        this.upkeepCost   += 10;
        this.upgradeCost  = 0;
    }
}
