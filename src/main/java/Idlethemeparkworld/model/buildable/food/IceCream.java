package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.BuildType;

public class IceCream extends FoodStall {
    
    public IceCream(int xLocation, int yLocation){
        this.x    = xLocation;
        this.y    = yLocation;
        this.value        = BuildType.ICECREAMPARLOR.getBuildCost();
        this.currentLevel = 1;
        this.buildingType = BuildType.ICECREAMPARLOR;
        this.capacity     = 1;
        this.occupied     = 0;
        this.foodPrice    = 1;        //customers have a preferred pricepoint, if it's higher than that their happiness goes down a bit
        this.foodQuality  = 1;
        this.upkeepCost   = 1;        //The total of its employees wages in dollars/hour.
        this.upgradeCost  = 1;
    }
    
    @Override
    public void level2Upgrade(){
        this.currentLevel += 1;
        this.upkeepCost   += 1;
        this.capacity     += 1;
        this.occupied     = 0;
        this.foodPrice    = 1;        //customers have a preferred pricepoint, if it's higher than that their happiness goes down a bit
        this.foodQuality  += 1;
        this.value        += upgradeCost;
        this.upgradeCost  *= 1;
        System.out.println(currentLevel);
    }
    
    @Override
    public void level3Upgrade(){
        this.currentLevel += 1;
        this.upkeepCost   += 1;
        this.capacity     += 1;
        this.occupied     = 0;
        this.foodPrice    = 1;        //customers have a preferred pricepoint, if it's higher than that their happiness goes down a bit
        this.foodQuality  += 1;
        this.value        += upgradeCost;
        this.upgradeCost  = 1;
    }
}
