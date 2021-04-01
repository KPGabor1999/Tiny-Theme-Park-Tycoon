package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.misc.utils.Direction;
import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.agent.AgentTypes.AgentType;
import Idlethemeparkworld.model.agent.AgentTypes.StaffType;
import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

public abstract class Agent implements Updatable {
    private static final int AGENT_HISTORY_LENGTH = 10;

    protected static final int AGENT_HUNGER_WARNING_THRESHOLD = 35;
    protected static final int AGENT_THIRST_WARNING_THRESHOLD = 35;
    protected static final int AGENT_TOILET_WARNING_THRESHOLD = 35;
    protected static final int AGENT_ENERGY_WARNING_THRESHOLD = 25;
    protected static final int AGENT_LOST_WARNING_THRESHOLD = 8;

    protected static final int AGENT_STATUS_MAXIMUM = 100;
    
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
    LinkedList<AgentAction> actionQueue;
    
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
        this.actionQueue = new LinkedList<>();
        
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
    
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
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
    
    public void moveTo(Position p){
        moveTo(p.x, p.y);
    }
    
    public void moveTo(int x, int y){
        this.x=x;
        this.y=y;
        currentBuilding = park.getTile(x, y).getBuilding();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Agent{name=").append(name);
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", destX=").append(destX);
        sb.append(", destY=").append(destY);
        sb.append(", energy=").append(energy);
        sb.append(", happiness=").append(happiness);
        sb.append(", hunger=").append(hunger);
        sb.append(", thirst=").append(thirst);
        sb.append(", toilet=").append(toilet);
        sb.append(", thoughts=").append(thoughts);
        sb.append(", state=").append(state);
        sb.append(", actionQueue=").append(actionQueue);
        return sb.toString();
    }
}
