package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.BuildType;

public class Hamburger extends FoodStall {
    public Hamburger(){
        this.buildingType = BuildType.BURGERJOINT;
        this.name = "Burger joint";
        this.capacity = 0;
        this.occupied = 0;
        this.foodPrice = 15;        //customers have a preferred pricepoint, if it's higher than that their happiness goes down a bit
        this.foodQuality = 1;
        this.buildingCost = 20000;
        this.upgradeCost = 40000;
        this.upKeepCost = 8;        //The total of its employees wages in dollars/hour.
        this.condition = 100;
    }
}
