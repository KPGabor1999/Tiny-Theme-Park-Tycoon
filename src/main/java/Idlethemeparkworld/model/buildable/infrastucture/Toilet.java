package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.agent.Janitor;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import Idlethemeparkworld.model.buildable.Queueable;
import java.util.LinkedList;

public class Toilet extends Infrastructure implements Queueable {

    protected LinkedList<Visitor> queue;
    protected int occupied;
    protected int capacity;
    private double cleanliness;

    public Toilet(int x, int y, GameManager gm) {
        super(gm);
        this.maxLevel = 0;
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.TOILET;
        this.queue = new LinkedList<>();
        this.occupied = 0;
        this.capacity = 10;
        this.cleanliness = 100;
        this.value = BuildType.TOILET.getBuildCost();
    }

    @Override
    public int getRecommendedMax() {
        return capacity;
    }

    public double getCleanliness() {
        return cleanliness;
    }
    
    public int getQueueLength(){
        return queue.size();
    }

    @Override
    public ArrayList<Pair<String, String>> getAllData() {
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Capacity: ", occupied + "/" + capacity));
        res.add(new Pair<>("Cleanliness: ", String.format("%.2f", cleanliness)));
        res.add(new Pair<>("Littering: ", String.format("%.2f", littering)));
        return res;
    }

    @Override
    public void joinQueue(Visitor visitor) {
        queue.add(visitor);
    }

    @Override
    public boolean isFirstInQueue(Visitor visitor) {
        return queue.peek().equals(visitor);
    }

    @Override
    public void leaveQueue(Visitor visitor) {
        queue.remove(visitor);
    }
    
    @Override
    public boolean canService() {
        return occupied < capacity;
    }

    public void enter(Visitor visitor) {
        queue.poll();
        occupied++;
    }

    public void exit() {
        occupied--;
    }

    public void decreaseHygiene(double amount) {
        cleanliness -= amount;
        if(cleanliness < 35) {
            Janitor.alertOfCriticalBuilding(this);
        }
        if(cleanliness <= 0){
            cleanliness = 0;
            this.setStatus(BuildingStatus.DECAYED);
            gm.getBoard().drawParkRender();
        }
    }

    @Override
    public boolean shouldClean() {
        return littering > 3 || cleanliness < 85;
    }
    
    public void clean(int amount) {
        cleanliness += amount;
        cleanliness = Math.min(cleanliness, 100);
    }
}
