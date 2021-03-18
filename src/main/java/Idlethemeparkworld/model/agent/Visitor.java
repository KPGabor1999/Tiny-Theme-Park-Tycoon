package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.Park;

public class Visitor extends Agent {
    private int x,y;
    
    public Visitor(String name, int startingHappiness, Park park){
        super(name, startingHappiness, park);
    }
    
    @Override
    public void update(long tickCount){
        
    }
    
    @Override
    protected void performAction(){

    }

}
