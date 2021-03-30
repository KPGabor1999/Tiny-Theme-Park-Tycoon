package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import java.util.ArrayList;

public class IceCream extends FoodStall {
    
    public IceCream(int x, int y, GameManager gm){
        super(gm);
        this.x    = x;
        this.y    = y;
        this.value        = BuildType.ICECREAMPARLOR.getBuildCost();
        this.buildingType = BuildType.ICECREAMPARLOR;
        this.serviceTime  = Time.convMinuteToTick(1);
        this.foodPrice    = 5;
        this.foodQuality.setRange(10, 15);
        this.upkeepCost   = 2 * 10;
        this.upgradeCost  = this.value*2;
    }
    
    @Override
    public void level2Upgrade(){
        this.serviceTime  += Time.convMinuteToTick(0.3);
        this.foodQuality.add(5, 5);
        this.upkeepCost   += 10;
        this.upgradeCost  *= 2;
    }
    
    @Override
    public void level3Upgrade(){
        this.serviceTime  += Time.convMinuteToTick(0.3);
        this.foodQuality.add(5, 5);
        this.upkeepCost   += 10;
        this.upgradeCost  = 0;
    }
}
