package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.agent.Janitor;

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
    
    @Override
    public boolean shouldClean() {
        return littering > 3 || filled/capacity > 0.2;
    }

    @Override
    public ArrayList<Pair<String, String>> getAllData() {
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Capacity: ", String.format("%.2f", capacity)));
        res.add(new Pair<>("Filled: ", String.format("%.2f", filled)));
        res.add(new Pair<>("Littering: ", String.format("%.2f", littering)));
        return res;
    }

    public void use(double amount) {
        if (!isFull()) {
            filled += amount;
            if(filled/capacity > 0.7) {
                Janitor.alertOfCriticalBuilding(this);
            }
            if (isFull()) {
                filled = capacity;
            }
        }
    }

    public boolean isFull() {
        return filled >= capacity;
    }

    public void empty() {
        filled = 0;
    }

    @Override
    public int getRecommendedMax() {
        return 5;
    }
}
