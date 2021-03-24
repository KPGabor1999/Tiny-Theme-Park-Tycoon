package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import java.util.ArrayList;
import javafx.util.Pair;

public class TrashCan extends Infrastructure {
    private int capacity;
    private int filled;
    
    public TrashCan(int xLocation, int yLocation){
        this.maxLevel = 1;
        this.x = xLocation;
        this.y = yLocation;
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
    
    @Override
    public void level2Upgrade(){}       //They're not meant to be upgradeable but they could be.
    
    @Override
    public void level3Upgrade(){}
}
