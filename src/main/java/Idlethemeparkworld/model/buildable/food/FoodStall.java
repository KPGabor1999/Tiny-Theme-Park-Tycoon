package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.misc.utils.Range;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.administration.Finance;
import Idlethemeparkworld.model.agent.Maintainer;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import Idlethemeparkworld.model.buildable.Queueable;
import Idlethemeparkworld.model.buildable.Repairable;
import java.util.LinkedList;

public abstract class FoodStall extends Building implements Queueable, Repairable {

    protected LinkedList<Visitor> queue;
    protected int serviceTime;
    protected int serviceTimer;
    protected int foodPrice;
    protected Range foodQuality;
    protected Range drinkQuality;
    protected Range servingSize;
    protected double condition;

    protected FoodStall(GameManager gm) {
        super(gm);
        this.queue = new LinkedList<>();
        this.serviceTime = 0;
        this.serviceTimer = 0;
        this.foodPrice = 0;
        this.foodQuality = new Range(45, 55);
        this.drinkQuality = new Range(45, 90);
        this.servingSize = new Range(5, 15);
        this.condition = 100;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public int getQueueLength() {
        return queue.size();
    }

    @Override
    public int getRecommendedMax() {
        return (status == BuildingStatus.OPEN || status == BuildingStatus.OPEN) ? 10 / serviceTime : 0;
    }

    public void setFoodPrice(int number) {
        this.foodPrice = number;
    }
    
    @Override
    public double getCondition(){
        return condition;
    }   

    @Override
    public void setCondition(double condition) {
        this.condition = condition;
    }
    
    /**
     * Javításra szorul-e a büfé?
     * @return 
     */
    @Override
    public boolean shouldRepair(){
        return condition < 90;
    }

    /**
     * Büfé adatainak összegyûjtése (ezeket írjuk ki a párbeszédablakba).
     * @return 
     */
    @Override
    public ArrayList<Pair<String, String>> getAllData() {
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Food price: ", Integer.toString(foodPrice)));
        res.add(new Pair<>("Food quality: ", "(" + foodQuality.getLow() + "-" + foodQuality.getHigh() + ")"));
        res.add(new Pair<>("Upkeep cost: ", Integer.toString(upkeepCost)));
        res.add(new Pair<>("Condition: ", String.format("%.2f", condition)));
        res.add(new Pair<>("In queue: ", Integer.toString(queue.size())));
        return res;
    }

    //Methods for managing visitors:
    
    /**
     * Látogató betétele a sorba.
     * @param visitor 
     */
    @Override
    public void joinQueue(Visitor visitor) {
        queue.add(visitor);
    }

    /**
     * Az adott látogató a sor elején áll?
     * @param visitor
     * @return 
     */
    @Override
    public boolean isFirstInQueue(Visitor visitor) {
        return queue.peek().equals(visitor);
    }

    /**
     * Az adott látogató kiállítása a sorból.
     * @param visitor 
     */
    @Override
    public void leaveQueue(Visitor visitor) {
        queue.remove(visitor);
    }

    /**
     * Üzemel-e a büfé.
     * @return 
     */
    @Override
    public boolean canService() {
        return serviceTimer <= 0;
    }

    /**
     * Az adott látogató telt vesz.
     * @param visitor
     * @return 
     */
    public FoodItem buyFood(Visitor visitor) {
        if (canService()) {
            if (visitor.canPay(foodPrice)) {
                visitor.pay(foodPrice);
                gm.getFinance().earn(foodPrice, Finance.FinanceType.FOOD_SELL);
                leaveQueue(visitor);
                serviceTimer = serviceTime;
                changeCondition(-0.27);
                return new FoodItem(foodQuality.getNextRandom(), drinkQuality.getNextRandom(), servingSize.getNextRandom());
            } else {
                return new FoodItem();
            }
        } else {
            return new FoodItem();
        }
    }
    
    /**
     * Büfé állapotromlása.
     * @param amount 
     */
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

    /**
     * Büfé állapotának frissítése.
     */
    private void updateCondition() {
        switch (status) {
            case OPEN:
                changeCondition(-0.5);
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

    /**
     * Büfé állapotának átállítása manuálisan.
     * @param status 
     */
    @Override
    public void setStatus(BuildingStatus status) {
        if (this.status == BuildingStatus.FLOATING) {
            queue.clear();
            serviceTimer = 0;
        }
        super.setStatus(status);
    }

    /**
     * Büfé frissítése az updatecycle-ban.
     * @param tickCount 
     */
    @Override
    public void update(long tickCount) {
        super.update(tickCount);
        if (!canService()) {
            serviceTimer--;
        }
        if (tickCount % 24 == 0) {
            updateCondition();
        }
    }
}
