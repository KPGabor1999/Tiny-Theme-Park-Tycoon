package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;

public class TrashCan extends Infrastructure {
    private double capacity;
    private double filled;
    
    public TrashCan(int x, int y, GameManager gm) {
        super(gm);
        this.maxLevel = 0;
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.TRASHCAN;
        this.capacity = 30;
        this.filled = 0;
        this.value = BuildType.TRASHCAN.getBuildCost();
    }

    public double getCapacity() {
        return capacity;
    }

    public double getFilled() {
        return filled;
    }
    
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Capacity: ", Double.toString(capacity)));
        res.add(new Pair<>("Filled: ", Double.toString(filled)));
        return res;
    }    
    
    //Methods for managing visitors:
    
    public void use(double amount){
        if(!this.isFull()){
            filled+=amount;
        }
    }
    
    public boolean isFull(){
        return filled < capacity;
    }
        
    public void empty(){
        filled = 0;
    }
    
    @Override
    public int getRecommendedMax(){
        return 5;
    }
}
