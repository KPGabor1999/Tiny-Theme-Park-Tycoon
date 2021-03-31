package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.agent.Visitor;
import java.util.PriorityQueue;

public class Toilet extends Infrastructure {
    protected PriorityQueue<Visitor> waitingLine;
    protected int occupied;
    protected int capacity;
    private int cleanliness;
    
    public Toilet(int x, int y, GameManager gm) {
        super(gm);
        this.maxLevel = 0;
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.TOILET;
        this.waitingLine = new PriorityQueue<>();
        this.occupied = 0;
        this.capacity = 10;
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
    
    //Methods for managing visitors:
        
    public void joinLine(Visitor visitor){
        waitingLine.add(visitor);
    }
    
    public boolean isFirstInQueue(Visitor visitor){
        return waitingLine.peek().equals(visitor);
    }
    
    public void leaveLine(Visitor visitor){
        waitingLine.remove(visitor);
    }
    
    public boolean isThereEmptyStool(){
        return occupied<capacity;
    }
    
    public void enter(Visitor visitor){
        waitingLine.poll();
        occupied++;
    }
    
    public void exit(){
        occupied--;
    }
    
    public void decreaseHygiene(int amount){
        cleanliness-=amount;
    }
    
    public void clean(){
        this.cleanliness = 100;
    }
}
