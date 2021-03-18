package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;

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

}
