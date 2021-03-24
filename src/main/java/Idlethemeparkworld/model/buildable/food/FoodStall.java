package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import javafx.util.Pair;

public abstract class FoodStall extends Building {
    protected int capacity;
    protected int occupied;
    protected int foodPrice;        //This is what you adjust in the Administration menu.
    protected int foodQuality;      //food quality lvl 5 = 1.5x happiness

    public int getCapacity() {
        return capacity;
    }

    public int getOccupied() {
        return occupied;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public int getFoodQuality() {
        return foodQuality;
    }
    
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        return res;
    }
}
