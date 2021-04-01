package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.misc.utils.Direction;
import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentAction;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentThought;
import Idlethemeparkworld.model.agent.AgentTypes.AgentType;
import Idlethemeparkworld.model.agent.AgentTypes.StaffType;
import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public abstract class Agent implements Updatable {
    private static final int AGENT_MAX_THOUGHTS = 5;
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
    Direction dir;
    boolean inPark;
    
    boolean manualMovable;
    AgentType type;
    StaffType staffType;
    
    int destX, destY;
    int patience;
    
    int energy;
    int happiness;
    int nausea;
    int hunger;
    int thirst;
    int toilet;
    int angriness;
    Random rand;
    
    ArrayList<AgentThought> thoughts;
    AgentState state;
    AgentAction current;
    PriorityQueue<AgentAction> actionQueue;
    
    BuildType[] visitHistory;

    Building currentBuilding;

    public Agent(String name, int startingHappiness, Park park, AgentManager am) {
        this.am = am;
        this.park = park;
        
        this.name = name;
        this.x = 0;
        this.y = this.x;
        this.inPark = true;  //In case we want to do pooling
        
        this.destX = 0;
        this.destY = this.destX;
        this.patience = Time.convMinuteToTick(20);
        
        this.energy = 100;
        this.happiness = startingHappiness;
        this.nausea = 0;
        this.hunger = 100;
        this.thirst = 100;
        this.toilet = 100;
        this.angriness = 0;
        this.rand = new Random();
        
        this.thoughts = new ArrayList<>();
        this.state = AgentState.ENTERINGPARK;
        this.actionQueue = new PriorityQueue<>();
        
        this.visitHistory = new BuildType[AGENT_HISTORY_LENGTH];
        this.currentBuilding = park.getTile(x, y).getBuilding();
    }
    
    public void chooseAttraction(ArrayList<Building> buildings){
        if (!buildings.isEmpty()) {
            int chosenAttractionID = rand.nextInt(buildings.size());
        }
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
    
    public void setState(AgentState newState){
        this.state = newState;
    }
    
    public void stateReset(){
        state = AgentState.IDLE;
    }
    
    //For now no pathfinding needed
    public void setPath(){
        
    }
    
    public void stepNext(){
        
    }
    
    public void moveForward(){
        moveTo(x+dir.x, y+dir.y);
    }
    
    public void moveTo(Position p){
        moveTo(p.x, p.y);
    }
    
    public void moveTo(int x, int y){
        x=dir.x;
        y=dir.y;
        currentBuilding = park.getTile(x, y).getBuilding();
    }
    
    
    public void addNewThought(AgentThought thought){
        if(thoughts.size() == AGENT_MAX_THOUGHTS){
            thoughts.remove(0);
        }
        thoughts.add(thought);
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
}
