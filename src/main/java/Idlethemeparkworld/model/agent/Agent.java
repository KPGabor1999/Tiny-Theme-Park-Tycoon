package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentThought;
import Idlethemeparkworld.model.agent.AgentTypes.AgentType;
import Idlethemeparkworld.model.agent.AgentTypes.StaffType;
import Idlethemeparkworld.model.buildable.Building;

public abstract class Agent implements Updatable {
    private static final int AGENT_MAX_THOUGHTS = 5;
    private static final int AGENT_THOUGHT_ITEM_NONE = 255;

    private static final int AGENT_HUNGER_WARNING_THRESHOLD = 25;
    private static final int AGENT_THIRST_WARNING_THRESHOLD = 25;
    private static final int AGENT_TOILET_WARNING_THRESHOLD = 28;
    private static final int AGENT_LITTER_WARNING_THRESHOLD = 23;
    private static final int AGENT_DISGUST_WARNING_THRESHOLD = 22;
    private static final int AGENT_VANDALISM_WARNING_THRESHOLD = 15;
    private static final int AGENT_NOEXIT_WARNING_THRESHOLD = 8;
    private static final int AGENT_LOST_WARNING_THRESHOLD = 8;

    private static final int AGENT_MAX_HAPPINESS = 100;
    private static final int AGENT_MIN_ENERGY = 10;
    private static final int AGENT_MAX_ENERGY = 100;
    private static final int AGENT_MAX_HUNGER = 100;
    private static final int AGENT_MAX_THIRST = 100;
    private static final int AGENT_MAX_TOILET = 100;
    private static final int AGENT_MAX_NAUSEA = 100;
    
    private Park park;
    
    private String name;
    private int x,y;
    private boolean inPark;
    
    private AgentState state;
    private AgentType type;
    private StaffType staffType;
    
    private int destX, destY;
    private int patience;
    
    private int energy;
    private int happiness;
    private int nausea;
    private int hunger;
    private int thirst;
    private int toilet;
    private int angriness;
    private int weight;
    
    private BuildType[] visitHistory;
    
    private Building currentBuilding;
    
    public void setState(AgentState newState){
        this.state = newState;
    }
    
    public void stateReset(){
        
    }
    
    public void setPath(){
        
    }
    
    public void stepNext(){
        
    }
    
    public void joinQueue(){
        
    }
    
    public void exitQueue(){
        
    }
    
    public void addNewThought(AgentThought thought){
        
    }
    
    public void setDestination(int x, int y){
        
    }
    
    public abstract void update();
    protected abstract void performAction();
}
