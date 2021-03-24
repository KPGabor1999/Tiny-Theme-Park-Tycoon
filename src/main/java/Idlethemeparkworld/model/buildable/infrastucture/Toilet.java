package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import java.util.ArrayList;
import javafx.util.Pair;

public class Toilet extends Infrastructure {
    private int cleanliness;
    
    public Toilet(int xLocation, int yLocation){
        this.maxLevel = 1;
        this.x = xLocation;
        this.y = yLocation;
        this.buildingType = BuildType.TOILET;
        this.capacity = 10;
        this.occupied = 0;
        this.cleanliness = 100;
        this.value = BuildType.TOILET.getBuildCost();
    }

    public int getCleanliness() {
        return cleanliness;
    }
    
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Capacity: ", occupied + "/" + capacity));
        res.add(new Pair<>("Cleanliness: ", Integer.toString(cleanliness)));
        return res;
    }   
        
    @Override
    public void level2Upgrade(){}       //They're not meant to be upgradeable but they could be.
    
    @Override
    public void level3Upgrade(){}

}
