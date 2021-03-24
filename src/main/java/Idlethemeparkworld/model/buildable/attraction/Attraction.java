package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.misc.utils.Range;
import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import javafx.util.Pair;

public abstract class Attraction extends Building {
    public enum AttractionType{
        GENTLE,
        THRILL
    } 
    
    public class AttractionStats{
        public Range excitement;
        public Range intensity;
        public Range fear;
        public Range nausea;
        
        public int maxCapacity;
        public int runtime;
        

        
        public AttractionStats(){
            this(new int[] {0,0,0,0,0,0,0,0,0,0});
        }
        
        public AttractionStats(int[] data){
            excitement = new Range(data[0], data[1]);
            intensity = new Range(data[2], data[3]);
            fear = new Range(data[4], data[5]);
            nausea = new Range(data[6], data[7]);
            
            maxCapacity = data[8];
            runtime = data[9];
        }
        
        public void upgrade(AttractionStats upgrade){
            excitement.add(upgrade.excitement);
            intensity.add(upgrade.intensity);
            fear.add(upgrade.fear);
            nausea.add(upgrade.nausea);
            
            maxCapacity += upgrade.maxCapacity;
            runtime += upgrade.runtime;
        }
    }
    
    protected static ArrayList<AttractionStats> upgrades;
    protected AttractionStats stats;
    
    protected int fun;
    protected int capacity;
    protected int occupied;
    protected int runtime;
    protected int entryFee;
    protected int condition;

    public int getOccupied() {
        return occupied;
    }

    public int getCondition() {
        return condition;
    }
    
    public int getEntryFee(){
        return entryFee;
    }
    
    public void increaseOccupied(int num){
        this.occupied += num;
    }
    
    public boolean canUpgrade(){
        return currentLevel < upgrades.size();
    }
    
    protected void innerUpgrade(){
        stats.upgrade(upgrades.get(currentLevel-1));
        currentLevel++;
    }
    
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Fun: ", Integer.toString(fun)));
        res.add(new Pair<>("Capacity: ", occupied + "/" + capacity));
        res.add(new Pair<>("Runtime: ", Integer.toString(runtime)));
        res.add(new Pair<>("Entry fee: ", Integer.toString(entryFee)));
        res.add(new Pair<>("Upkeep cost: ", Integer.toString(upkeepCost)));
        res.add(new Pair<>("Condition: ", Integer.toString(condition)));
        return res;
    }
    
    //consider using an observer/event listener
    protected abstract void start();
}
