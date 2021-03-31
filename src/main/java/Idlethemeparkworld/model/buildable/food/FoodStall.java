package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.misc.utils.Range;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import java.util.PriorityQueue;

public abstract class FoodStall extends Building {
    protected PriorityQueue<Visitor> queue;
    protected int serviceTime;
    protected int serviceTimer;
    protected int foodPrice;
    protected Range foodQuality;
    protected int upkeepTimer;

    protected FoodStall(GameManager gm) {
        super(gm);
        this.queue = new PriorityQueue<>();
        this.serviceTime = 0;
        this.serviceTimer = 0;
        this.foodPrice = 0;
        this.foodQuality = new Range(45,55);
        this.upkeepTimer = 0;
    }
    
    public void setFoodPrice(int number){
        this.foodPrice = number;
    }
    
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Food price: ", Integer.toString(foodPrice)));
        res.add(new Pair<>("Food quality: ", "("+foodQuality.getLow()+"-"+foodQuality.getHigh()+")"));
        res.add(new Pair<>("Upkeep cost: ", Integer.toString(upkeepCost)));
        return res;
    }
    
    //Methods for managing visitors:
    
    public void joinQueue(Visitor visitor){
        queue.add(visitor);
    }
    
    public boolean isFirstInQueue(Visitor visitor){
        return queue.peek().equals(visitor);
    }
    
    public void leaveQueue(Visitor visitor){
        queue.remove(visitor);
    }
    
    /*
    public List<Food> getMenu() {
        return Collections.unmodifiableList(menu);
    }
    */
    
    public boolean canService(){
        return serviceTimer <= 0;
    }
    
    public int buyFood(Visitor visitor){
        if(canService()){
            //if(visitor.canPay(foodprice){
            //visitor.pay(foodPrice);
            gm.getFinance().earn(foodPrice);

            leaveQueue(visitor);
            serviceTimer = serviceTime;
            return foodQuality.getNextRandom(); 
        } else {
            return 0;
        }
    }
    
    public void repair(int amount){
        condition+=amount;
    }
    
    private void updateCondition(){
        switch(status){
            case OPEN:
                condition-=0.02; break;
            case CLOSED:
                condition-=0.04; break;
            case INACTIVE:
                condition-=0.1; break;
            case FLOATING:
                condition-=0.25; 
                if(condition<=0){
                    status = BuildingStatus.DECAYED;
                }
                break;
            default:
                break;
        }
    }
    
    public void update(long tickCount){
        if(!canService()){
            serviceTimer--;
        }
        if(tickCount%24==0){
            updateCondition();
            upkeepTimer++;
            if(upkeepTimer >= Time.convMinuteToTick(60)/24){
                gm.getFinance().pay(upkeepCost);
                upkeepTimer = 0;
            }
        }
    }
}
