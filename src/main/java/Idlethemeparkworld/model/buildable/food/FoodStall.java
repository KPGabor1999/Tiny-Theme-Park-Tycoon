package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.misc.Assets;
import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.misc.utils.Range;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.News;
import Idlethemeparkworld.model.administration.Finance;
import Idlethemeparkworld.model.agent.AgentInnerLogic;
import Idlethemeparkworld.model.agent.Maintainer;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import Idlethemeparkworld.model.buildable.Queueable;
import Idlethemeparkworld.model.buildable.RandomSkin;
import Idlethemeparkworld.model.buildable.Repairable;
import Idlethemeparkworld.model.buildable.Reviewable;
import java.util.LinkedList;
import java.util.Random;

public abstract class FoodStall extends Building implements Queueable, Repairable, RandomSkin, Reviewable {

    protected LinkedList<Visitor> queue;
    protected int serviceTime;
    protected int serviceTimer;
    protected int foodPrice;
    protected Range foodQuality;
    protected Range drinkQuality;
    protected Range servingSize;
    protected double condition;
    
    protected Random rand;
    protected final int skinID;
    
    protected ArrayList<String> reviews;

    protected FoodStall(GameManager gm) {
        super(gm);
        this.queue = new LinkedList<>();
        this.serviceTime = 0;
        this.serviceTimer = 0;
        this.foodPrice = 0;
        this.foodQuality = new Range(45, 55);
        this.drinkQuality = new Range(30, 50);
        this.servingSize = new Range(5, 15);
        this.condition = 100;
        this.rand = new Random();
        this.skinID = rand.nextInt(3);
        this.sound = Assets.Sounds.NOM_NOM_NOM;
        this.reviews = new ArrayList<>();
    }

    public int getSkinID(){
        return skinID;
    }
    
    public int getFoodPrice() {
        return foodPrice;
    }

    public int getQueueLength() {
        return queue.size();
    }
    
    @Override
    public void addReview(String name, AgentInnerLogic.Reviews review){
        reviews.add(name+": "+review.getReviewText());
        if(reviews.size() > 6){
            reviews.remove(0);
        }
    }

    @Override
    public ArrayList<String> getReviews() {
        return reviews;
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
        res.add(new Pair<>("Food quality: ", "(" + getFoodQuality().getLow() + "-" + getFoodQuality().getHigh() + ")"));
        res.add(new Pair<>("Drink quality: ", "(" + getDrinkQuality().getLow() + "-" + getDrinkQuality().getHigh() + ")"));
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
    
    protected Pair<Double,Double> getWeatherMultiplier(){
        return new Pair(1,1);
    }
    
    public Range getFoodQuality(){
        return foodQuality.newRangeByMultiplier(getWeatherMultiplier().getKey());
    }
    
    public Range getDrinkQuality(){
        return drinkQuality.newRangeByMultiplier(getWeatherMultiplier().getValue());
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
                return new FoodItem(getFoodQuality().getNextRandom(), getDrinkQuality().getNextRandom(), servingSize.getNextRandom(), foodPrice);
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
        double original = condition;
        condition += amount;
        if(condition < 35) {
            if(original >= 35){
                News.getInstance().addNews("Condition of food stall(" + x + "," + y + ") is below critical threshold!");
            }
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
