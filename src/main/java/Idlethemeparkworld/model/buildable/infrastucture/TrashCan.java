package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;

public class TrashCan extends Infrastructure {
    private int capacity;
    private int filled;
    
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

    public int getCapacity() {
        return capacity;
    }

    public int getFilled() {
        return filled;
    }
    
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Capacity: ", Integer.toString(capacity)));
        res.add(new Pair<>("Filled: ", Integer.toString(filled)));
        return res;
    }    
    
    //Methods for managing visitors:
    
    public void use(){
        if(!this.isFull()){
            filled++;
        }
    }
    
    public boolean isFull(){
        return filled < capacity;
    }
        
    public void empty(){
        filled = 0;
    }
}
