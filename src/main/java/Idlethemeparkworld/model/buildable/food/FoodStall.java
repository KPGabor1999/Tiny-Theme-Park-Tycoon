package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.agent.Visitor;

public abstract class FoodStall extends Building {
    protected ArrayList<Visitor> waitingLine;     //customers are waiting to be served
    protected int capacity;         //How many customers can be served at a time
    protected int foodPrice;        //This is what you adjust in the Administration menu.
    protected int foodQuality;      //food quality = happiness bonus

    public int getFoodQuality() {
        return foodQuality;
    }
    
    public void setFoodPrice(int number){
        this.foodPrice = number;
    }
    
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Food price: ", Integer.toString(foodPrice)));
        res.add(new Pair<>("Food quality: ", Integer.toString(foodQuality)));
        res.add(new Pair<>("Upkeep cost: ", Integer.toString(upkeepCost)));
        return res;
    }
    
    //Methods for managing visitors:
    
    public void joinLine(Visitor visitor){
        waitingLine.add(visitor);
        //Visitor starts waiting.
        //If they don't get served in time, they leave the line disappointed.
    }
    
    public void serveCustomers(){   //Serve multiple customers at once.
        if(waitingLine.size() >= capacity){
            for(int nextInLine = 1; nextInLine <= capacity; nextInLine++){
                serveCustomer();
            }
        } else {
            for(int nextInLine = 1; nextInLine <= waitingLine.size(); nextInLine++){
                serveCustomer();
            }
        }
    }
    
    public void serveCustomer(){
        //Serves the first customer in line
        gm.getFinance().earn(foodPrice);   //sells them food 
        waitingLine.remove(0);             //removes them from the line
        //Visitor eats food, becomes happier and less hungry.
        //Then the visitor looks for a nearby trashcan. If the don't find one, they just drop it on the floor.
    }
    
    public void leaveLine(Visitor visitor){
        waitingLine.remove(visitor);
        //Visitor gets disappointed and their happiness drops.
    }
}
