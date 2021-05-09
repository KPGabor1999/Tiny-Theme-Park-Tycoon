package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.misc.Assets;
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
        this.sound = Assets.Sounds.PAPER_CRUMBLING;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getFilled() {
        return filled;
    }
    
    /**
     * Ideje kiüríteni a szemetest?
     * @return 
     */
    @Override
    public boolean shouldClean() {
        return littering > 3 || filled/capacity > 0.2;
    }

    /**
     * Szemetes adatainak lekérése (kiíratáshoz).
     * @return 
     */
    @Override
    public ArrayList<Pair<String, String>> getAllData() {
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Capacity: ", String.format("%.2f", capacity)));
        res.add(new Pair<>("Filled: ", String.format("%.2f", filled)));
        res.add(new Pair<>("Littering: ", String.format("%.2f", littering)));
        return res;
    }

    /**
     * Szemetes használata.
     * @param amount 
     */
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

    /**
     * Tele van-e a szemetes?
     * @return 
     */
    public boolean isFull() {
        return filled >= capacity;
    }

    /**
     * Szemetes kiürítése.
     */
    public void empty() {
        filled = 0;
    }

    /**
     * Egy szemetes lerakása +5 ember tartózkodását engedélyezi a parkban.
     * @return 
     */
    @Override
    public int getRecommendedMax() {
        return 4;
    }
}
