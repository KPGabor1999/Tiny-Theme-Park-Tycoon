package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.buildable.Building;

public class Visitor extends Agent {
    private int x,y;
    
    public Visitor(String name, int startingHappiness, Park park, AgentManager am){
        super(name, startingHappiness, park, am);
    }
    
    @Override
    public void update(long tickCount){
        
    }
    
    @Override
    protected void performAction(){
       
    }
    
    //Agent joins the chosen attraction's queue
    public void joinQueue(Building attraction){
        
    }
}
