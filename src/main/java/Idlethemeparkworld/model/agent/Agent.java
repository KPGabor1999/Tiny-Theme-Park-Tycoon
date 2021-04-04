package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.agent.AgentTypes.AgentType;
import Idlethemeparkworld.model.agent.AgentTypes.StaffType;
import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public abstract class Agent implements Updatable {
    private static final int AGENT_HISTORY_LENGTH = 10;
    
    AgentManager am;
    Park park;
    
    String name;
    int x,y;
    boolean inPark;
    
    AgentType type;
    StaffType staffType;
    
    int destX, destY;
    
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
        
        this.destX = 0;
        this.destY = this.destX;
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
    
    public void setState(AgentState newState){
        this.state = newState;
    }
    
    protected void moveTo(Position p){
        moveTo(p.x, p.y);
    }
    
    protected void moveTo(int x, int y){
        this.x=x;
        this.y=y;
        updateCurBuilding();
    }
    
    protected void setDestination(int x, int y){
        this.destX = x;
        this.destY = y;
    }
    
    protected void remove(){
        am.removeAgent(this);
    }
    
    protected void updateCurBuilding(){
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
        sb.append(", thoughts=").append(thoughts);
        sb.append(", state=").append(state);
        sb.append(", actionQueue=").append(actionQueue);
        return sb.toString();
    }
}
