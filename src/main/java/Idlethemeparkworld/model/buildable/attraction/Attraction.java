package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.misc.utils.Range;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.administration.Finance.FinanceType;
import Idlethemeparkworld.model.agent.Maintainer;
import Idlethemeparkworld.model.buildable.Queueable;
import Idlethemeparkworld.model.buildable.Repairable;
import java.util.ArrayList;
import java.util.Random;

public abstract class Attraction extends Building implements Queueable, Repairable {

    protected int fun;
    protected int capacity;
    protected int runtime;
    protected int entryFee;
    protected int baseEntryFee;

    protected int statusTimer;
    protected double condition;

    protected ArrayList<Visitor> queue;
    protected ArrayList<Visitor> onRide;

    protected Random rand;

    public Attraction(GameManager gm) {
        super(gm);
        this.queue = new ArrayList<>();
        this.onRide = new ArrayList<>();
        this.rand = new Random();
        this.condition = 100;
        playConstructionSound();
    }

    public int getQueueLength() {
        return queue.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBaseEntryFee() {
        return baseEntryFee;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(int number) {
        this.entryFee = number;
    }

    @Override
    public boolean shouldRepair(){
        return condition < 90;
    }
    
    @Override
    public double getCondition(){
        return condition;
    }   

    @Override
    public void setCondition(double condition) {
        this.condition = condition;
    }

    @Override
    public int getRecommendedMax() {
        return (status == BuildingStatus.OPEN || status == BuildingStatus.OPEN) ? (int) Math.floor(capacity * 1.5) : 0;
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
        gm.getFinance().earn(profit, FinanceType.RIDE_SELL);
    }

    private void finish() {
        Range r = new Range((int) Math.floor(fun * condition / 100), fun);
        int rideEvent = 0;
        if (rand.nextInt(100) < condition * 1.5) {
            rideEvent = r.getNextRandom();
        } else {
            rideEvent = (rand.nextInt(15) + 10) * (-1);
        }
        for (int i = 0; i < onRide.size(); i++) {
            onRide.get(i).sendRideEvent(rideEvent);
        }
        onRide.clear();
        changeCondition(-0.47);
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
    
    private void changeCondition(double amount) {
        condition += amount;
        if(condition < 35) {
            Maintainer.alertOfCriticalBuilding(this);
        }
        if (condition <= 0) {
            condition = 0;
            status = BuildingStatus.DECAYED;
            gm.getBoard().drawParkRender();
        }
    }

    private void updateCondition() {
        switch (status) {
            case OPEN:
                changeCondition(-0.33);
                break;
            case CLOSED:
                changeCondition(-1);
                break;
            case INACTIVE:
                changeCondition(-2);
                break;
            case FLOATING:
                changeCondition(-4);
                break;
            default:
                break;
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
