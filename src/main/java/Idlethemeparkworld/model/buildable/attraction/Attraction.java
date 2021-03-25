package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.misc.utils.Range;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import Idlethemeparkworld.misc.utils.Pair;
import java.util.ArrayList;

public abstract class Attraction extends Building implements Updatable {
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
    
    protected GameManager gm;
    
    protected int fun;
    protected int capacity;
    protected int occupied;
    protected int runtime;
    protected int entryFee;
    protected double condition;
    
    protected int statusTimer;

    protected ArrayList<Visitor> queue;
    
    public int getOccupied() {
        return occupied;
    }

    public double getCondition() {
        return condition;
    }
    
    public int getEntryFee(){
        return entryFee;
    }
    
    public void increaseOccupied(int num){
        this.occupied += num;
    }
    
    //public boolean canUpgrade(){
    //    return currentLevel < upgrades.size();
    //}
    
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
        res.add(new Pair<>("Condition: ", String.format("%.2f", condition)));
        return res;
    }
    
    //consider using an observer/event listener
    protected void start(){
        status=BuildingStatus.RUNNING;
        statusTimer = 0;
        int profit = 0;
        for (int i = 0; i < queue.size(); i++) {
            if(true/*queue.get(i).hasMoney(entryFee)*/){
                /*queue.get(i).pay(entryFee)*/
                profit += entryFee;
            }
        }
        gm.getFinance().earn(profit);
    }
    
    protected void finish(){
        Range r = new Range((int)Math.floor(fun*condition/100),fun);
        int rideEvent = r.getNextRandom();
        for (int i = 0; i < queue.size(); i++) {
            //queue.get(i).sendRideEvent(rideEvent);
        }
        resetQueue();
        status=BuildingStatus.OPEN;
    }
    
    public void joinQueue(Visitor v){
        if(status == BuildingStatus.OPEN){
            queue.add(v);
            occupied++;
            if(occupied == capacity){
                start();
            }
        }
    }
    
    private void resetQueue(){
        queue.clear();
        occupied=0;
    }
    
    public void leaveQueue(Visitor v){
        queue.remove(v);
        occupied--;
    }
    
    private void updateCondition(){
        switch(status){
            case RUNNING:
                condition-=0.04; break;
            case OPEN:
                condition-=0.02; break;
            case CLOSED:
                condition-=0.04; break;
            case INACTIVE:
                condition-=0.1; break;
            case FLOATING:
                condition-=0.25; break;
            default:
                break;
        }
    }
    
    public void update(long tickCount){
        statusTimer++;
        switch(status){
            case RUNNING:
                if(statusTimer >= Time.convMinuteToTick(fun)){
                    finish();
                }
                break;
            case OPEN:
                break;
            case CLOSED:
                break;
            case INACTIVE:
                break;
            case FLOATING:
                if(condition<=0){
                    status = BuildingStatus.DECAYED;
                }
                break;
            default:
                break;
        }
        if(tickCount%24==0){
            updateCondition();
        }
    }
}
