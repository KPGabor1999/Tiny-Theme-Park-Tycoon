package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentThought;
import Idlethemeparkworld.model.agent.AgentTypes.AgentType;
import Idlethemeparkworld.model.agent.AgentTypes.StaffType;
import Idlethemeparkworld.model.buildable.Building;

public abstract class Agent implements Updatable {
    private static final int AGENT_MAX_THOUGHTS = 5;
    private static final int AGENT_THOUGHT_ITEM_NONE = 255;
    private static final int AGENT_HISTORY_LENGTH = 10;

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
    
    AgentManager am;
    Park park;
    
    String name;
    int x,y;
    boolean inPark;
    
    boolean manualMovable;
    AgentState state;
    AgentType type;
    StaffType staffType;
    
    int destX, destY;
    int patience;
    int weight;
    
    int energy;
    int happiness;
    int nausea;
    int hunger;
    int thirst;
    int toilet;
    int angriness;

    AgentThought[] thoughts;
    
    BuildType[] visitHistory;

    Building currentBuilding;

    public Agent(String name, int startingHappiness, Park park, AgentManager am) {
        this.am = am;
        this.park = park;
        
        this.name = name;
        this.x = 0;
        this.y = this.x;
        this.inPark = true;
        
        this.destX = 0;
        this.destY = this.destX;
        this.patience = 50; //DNA
        this.weight = 60; //DNA
        
        this.energy = 100;
        this.happiness = startingHappiness;
        this.nausea = 0;
        this.hunger = 100;
        this.thirst = 100;
        this.toilet = 100;
        this.angriness = 0;
        
        this.thoughts = new AgentThought[AGENT_MAX_THOUGHTS];
        for (int i = 0; i < thoughts.length; i++) {
            thoughts[i] = new AgentThought();
        }
        this.visitHistory = new BuildType[AGENT_HISTORY_LENGTH];
        this.currentBuilding = park.getTile(x, y).getBuilding();
    }

    public String getName() {
        return name;
    }

    public boolean isInPark() {
        return inPark;
    }

    public AgentType getType() {
        return type;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    public int getPatience() {
        return patience;
    }

    public int getEnergy() {
        return energy;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getNausea() {
        return nausea;
    }

    public int getHunger() {
        return hunger;
    }

    public int getThirst() {
        return thirst;
    }

    public int getToilet() {
        return toilet;
    }

    public int getAngriness() {
        return angriness;
    }

    public int getWeight() {
        return weight;
    }
    
    public void setState(AgentState newState){
        this.state = newState;
    }
    
    public void stateReset(){
        state = AgentState.IDLE;
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
        if(thoughts.length < AGENT_MAX_THOUGHTS){
            
        }
    }
    
    public void setDestination(int x, int y){
        this.destX = x;
        this.destY = y;
    }
    
    public void forceLocation(int x, int y){
        if(manualMovable){
            this.x = x;
            this.y = y;
            updateCurBuilding();
        }
    }
    
    private void remove(){
        am.removeAgent(this);
    }
    
    public void updateCurBuilding(){
        currentBuilding = park.getTile(x, y).getBuilding();
    }
    
    
    
    @Override
    public abstract void update(long tickCount);
    protected abstract void performAction();
}
