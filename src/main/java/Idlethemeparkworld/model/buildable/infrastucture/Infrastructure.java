package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import javafx.util.Pair;

public abstract class Infrastructure extends Building {
    protected int capacity;
    protected int occupied;
    protected int duration;
    //protected int rubbish;

    public int getCapacity() {
        return capacity;
    }

    public int getOccupied() {
        return occupied;
    }

    public int getDuration() {
        return duration;
    }
    
    public void clean(){
        
    }
    
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        return res;
    }
}
