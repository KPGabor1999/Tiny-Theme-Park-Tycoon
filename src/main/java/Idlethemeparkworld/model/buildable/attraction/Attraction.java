package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.misc.utils.Range;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.buildable.Queueable;
import java.util.ArrayList;
import java.util.Random;

public abstract class Attraction extends Building implements Updatable, Queueable {

    protected int fun;
    protected int capacity;
    protected int runtime;
    protected int entryFee;

    protected int statusTimer;

    protected ArrayList<Visitor> queue;
    protected ArrayList<Visitor> onRide;

    protected Random rand;
    
    public Attraction(GameManager gm) {
        super(gm);
        this.queue = new ArrayList<>();
        this.onRide = new ArrayList<>();
        this.rand = new Random();
    }

    public int getQueueLength() {
        return queue.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public double getCondition() {
        return condition;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public void setCondition(double condition) {
        this.condition = condition;
    }

    @Override
    public int getRecommendedMax() {
        return (status == BuildingStatus.OPEN || status == BuildingStatus.OPEN) ? (int)Math.floor(capacity * 1.5) : 0;
    }

    public ArrayList<Pair<String, String>> getAllData() {
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Fun: ", Integer.toString(fun)));
        res.add(new Pair<>("In queue: ", Integer.toString(queue.size())));
        res.add(new Pair<>("On ride/capacity: ", onRide.size() + "/" + capacity));
        res.add(new Pair<>("Runtime: ", Integer.toString(runtime)));
        res.add(new Pair<>("Entry fee: ", Integer.toString(entryFee)));
        res.add(new Pair<>("Upkeep cost: ", Integer.toString(upkeepCost)));
        res.add(new Pair<>("Condition: ", String.format("%.2f", condition)));
        return res;
    }

    private void start() {
        status = BuildingStatus.RUNNING;
        statusTimer = 0;
        int profit = 0;
        for (int i = 0; i < onRide.size(); i++) {
            if (onRide.get(i).canPay(entryFee)) {
                onRide.get(i).pay(entryFee);
                profit += entryFee;
            } else {
                onRide.get(i).sendRideEvent(0);
            }
        }
        gm.getFinance().earn(profit);
    }

    private void finish() {
        Range r = new Range((int) Math.floor(fun * condition / 100), fun);
        int rideEvent = 0;
        if(rand.nextInt(100) < condition*1.5){
            rideEvent = r.getNextRandom();
        } else {
            rideEvent = (rand.nextInt(15)+10)*(-1);
        }
        for (int i = 0; i < onRide.size(); i++) {
            onRide.get(i).sendRideEvent(rideEvent);
        }
        onRide.clear();
        status = BuildingStatus.OPEN;
    }

    private void loadVisitors() {
        while (!queue.isEmpty() && onRide.size() < capacity) {
            Visitor v = queue.remove(0);
            v.setOnRide();
            onRide.add(v);
        }
    }

    @Override
    public void joinQueue(Visitor v) {
        queue.add(v);
    }

    @Override
    public void leaveQueue(Visitor v) {
        queue.remove(v);
    }
    
    @Override
    public boolean isFirstInQueue(Visitor v) {
        return false;
    }
    
    @Override
    public boolean canService() {
        return true;
    }

    private void updateCondition() {
        switch (status) {
            case RUNNING:
                condition -= 0.02;
                break;
            case OPEN:
                condition -= 0.01;
                break;
            case CLOSED:
                condition -= 0.02;
                break;
            case INACTIVE:
                condition -= 0.04;
                break;
            case FLOATING:
                condition -= 2.5;
                break;
            default:
                break;
        }

        if (condition <= 0) {
            condition = 0;
            status = BuildingStatus.DECAYED;
        }
    }

    @Override
    public void setStatus(BuildingStatus status) {
        if (this.status == BuildingStatus.FLOATING) {
            queue.clear();
            onRide.clear();
            statusTimer = 0;
        }
        super.setStatus(status);
    }

    @Override
    public void update(long tickCount) {
        statusTimer++;
        switch (status) {
            case RUNNING:
                if (statusTimer >= Time.convMinuteToTick(runtime)) {
                    finish();
                }
                break;
            case OPEN:
                if (queue.size() >= capacity) {
                    loadVisitors();
                    start();
                }
                break;
            default:
                break;
        }
        if (tickCount % 24 == 0) {
            updateCondition();
        }
    }
}
