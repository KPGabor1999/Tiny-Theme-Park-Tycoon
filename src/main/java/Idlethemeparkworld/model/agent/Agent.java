package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.agent.AgentTypes.AgentType;
import Idlethemeparkworld.model.agent.AgentTypes.StaffType;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import java.util.Random;

public abstract class Agent implements Updatable {
    protected static final int AGENT_HISTORY_LENGTH = 10;
    
    AgentManager am;
    Park park;
    
    String name;
    int x,y;
    boolean inPark;
    
    AgentType type;
    StaffType staffType;
    
    Random rand;
    
    AgentState state;
    protected int statusMaxTimer;
    protected int statusTimer;
    protected AgentAction currentAction;
    Building currentBuilding;
    
    protected Position prevPos;
    protected Position newPos;
    protected int xOffset;
    protected int yOffset;
    protected int lerpTimer;
    protected boolean isMoving;

    public Agent(String name, Park park, AgentManager am) {
        this.am = am;
        this.park = park;
        
        this.name = name;
        this.x = 0;
        this.y = this.x;
        
        this.rand = new Random();
        
        this.state = AgentState.ENTERINGPARK;
        
        this.currentBuilding = park.getTile(x, y).getBuilding();
        
        this.xOffset = rand.nextInt(64);
        this.yOffset = rand.nextInt(64);
        this.prevPos = new Position(xOffset,yOffset);
        this.newPos = new Position(xOffset,yOffset);
        this.lerpTimer = 0;
        this.isMoving = false;
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
    
    public AgentState getState(){
        return state;
    }
    
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
    
    public Position calculateExactPosition(int cellSize){
        Position res = prevPos.lerp(newPos, lerpTimer/24.0);
        return res;
    }
    
    protected void setState(AgentState newState){
        statusTimer = 0;
        state = newState;
    }
    
    protected void checkFloating(){
        if(park.getTile(x, y).isEmpty() || park.getTile(x, y).getBuilding().getStatus() == BuildingStatus.FLOATING){
            if(state != AgentState.FLOATING){
                resetAction();
                statusTimer = 0;
                state = AgentState.FLOATING;
            }
        } else {
            if(state == AgentState.FLOATING){
                state = AgentState.IDLE;
            }
        }
    }
    
    protected void resetAction(){
        setState(AgentState.IDLE);
        currentAction = null;
    }
    
    protected void moveTo(int x, int y){
        prevPos = new Position(this.x*64+xOffset,this.y*64+yOffset);
        lerpTimer = 0;
        isMoving = true;
        this.x=x;
        this.y=y;
        updateCurBuilding();
        xOffset = rand.nextInt(currentBuilding.getInfo().getWidth()*64);
        yOffset = rand.nextInt(currentBuilding.getInfo().getLength()*64);
        newPos = new Position(this.x*64+xOffset,this.y*64+yOffset);
    }
    
    protected void checkMove(){
        if(isMoving){
            lerpTimer++;
            isMoving=lerpTimer<24;
        }
    }
    
    protected void remove(){
        am.removeAgent(this);
    }
    
    protected void updateCurBuilding(){
        currentBuilding = park.getTile(x, y).getBuilding();
    }
    
    @Override
    public void update(long tickCount){
        checkMove();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name=").append(name).append("\n");
        sb.append("x=").append(x).append("\n");
        sb.append("y=").append(y).append("\n");
        sb.append("state=").append(state).append("\n");
        return sb.toString();
    }
}
