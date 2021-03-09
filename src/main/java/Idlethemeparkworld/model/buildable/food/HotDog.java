package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.BuildType;

public class HotDog extends FoodStall {
    public HotDog(){
        this.buildingType = BuildType.HOTDOGSTAND;
        this.name = "Hot dog stand";
        this.capacity = 20;
        this.occupied = 0;
        this.foodPrice = 15;        //customers have a preferred pricepoint, if it's higher than that their happiness goes down a bit
        this.foodQuality = 1;
        this.buildingCost = 20000;
        this.upgradeCost = 40000;
        this.upkeepCost = 8;        //The total of its employees wages in dollars/hour.
    }
}
