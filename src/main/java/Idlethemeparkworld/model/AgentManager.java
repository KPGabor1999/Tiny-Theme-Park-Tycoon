package Idlethemeparkworld.model;

import Idlethemeparkworld.model.agent.Visitor;
import java.util.ArrayList;
import java.util.Random;

public class AgentManager implements Updatable {
    private static final int AGENT_UPDATE_TICK = 12;
    
    private int visitorProbability;
    private Park park;
    
    private Random rand;
    
    private ArrayList<Visitor> visitors;
    
    public AgentManager(Park park){
        this.park = park;
        this.visitorProbability = 0;
        this.rand = new Random();
        this.visitors = new ArrayList<>();
    }
    
    public void spawnVisitor(){
        //
    }
    
    private void getNewRandomVisitor(){
        
    }
    
    //Might need to make these penalties more gradual rather than the current threshold based one
    public void updateVisitorProbability(){
        int prob = 50;
        
        int rating = park.getRating();
        prob += rating > 100 ? 100 : rating;
        
        int visitorCount = 0;
        if(visitorCount > park.getMaxGuest()){
            prob *= 0.2;
        }
        
        int entFee = park.getEntranceFee();
        if(entFee > park.getActiveValue()){
            prob *= 0.4;
        }
        
        visitorProbability = prob;
    }
    
    private int getRandomHappiness(){
        return rand.nextInt(50)+35;
    }
    
    public void spawnUpdate(){
        if(rand.nextInt(250)<visitorProbability){
            spawnVisitor();
        }
    }
    
    public int getStaffCount(){
        return 0;
    }
    
    public int getVisitorCount(){
        return visitors.size();
    }
    
    public void update(long tickCount){
        for (int i = 0; i < visitors.size(); i++) {
            visitors.get(i).update(tickCount);
        }
    }
}
