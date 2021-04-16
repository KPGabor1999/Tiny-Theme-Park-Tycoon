package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.agent.Visitor;
import java.util.LinkedList;

public class Toilet extends Infrastructure {
    protected LinkedList<Visitor> waitingLine;
    protected int occupied;
    protected int capacity;
    private double cleanliness;
    
    public Toilet(int x, int y, GameManager gm) {
        super(gm);
        this.maxLevel = 0;
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.TOILET;
        this.waitingLine = new LinkedList<>();
        this.occupied = 0;
        this.capacity = 10;
        this.cleanliness = 100;
        this.value = BuildType.TOILET.getBuildCost();
    }
    
    @Override
    public int getRecommendedMax(){
        return capacity;
    }

    public double getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(double cleanliness) {
        this.cleanliness = cleanliness;
    }
    
    @Override
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Capacity: ", occupied + "/" + capacity));
        res.add(new Pair<>("Cleanliness: ", String.format("%.2f", cleanliness)));
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
    
    public void decreaseHygiene(double amount){
        cleanliness-=amount;
    }
    
    public void clean(){
        this.cleanliness = 100;
    }
}
