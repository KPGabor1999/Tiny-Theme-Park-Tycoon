package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.misc.utils.Range;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import java.util.LinkedList;
import java.util.PriorityQueue;

public abstract class FoodStall extends Building {
    protected LinkedList<Visitor> queue;
    protected int serviceTime;
    protected int serviceTimer;
    protected int foodPrice;
    protected Range foodQuality;
    protected Range drinkQuality;
    protected Range servingSize;

    protected FoodStall(GameManager gm) {
        super(gm);
        this.queue = new LinkedList<>();
        this.serviceTime = 0;
        this.serviceTimer = 0;
        this.foodPrice = 0;
        this.foodQuality = new Range(45,55);
        this.drinkQuality = new Range(45,55);
        this.servingSize = new Range(2,5);
    }
    
    @Override
    public int getRecommendedMax(){
        return 10/serviceTime;
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
    
    public FoodItem buyFood(Visitor visitor){
        if(canService()){
            if(visitor.canPay(foodPrice)){
                visitor.pay(foodPrice);
                gm.getFinance().earn(foodPrice);

                leaveQueue(visitor);
                serviceTimer = serviceTime;
                System.out.println("Food bought");
                return new FoodItem(foodQuality.getNextRandom(),drinkQuality.getNextRandom(),servingSize.getNextRandom());
            } else {
                return new FoodItem();
            }
        } else {
            return new FoodItem();
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
    
    @Override
    public void update(long tickCount){
        super.update(tickCount);
        if(!canService()){
            serviceTimer--;
        }
        if(tickCount%24==0){
            updateCondition();
        }
    }
}
