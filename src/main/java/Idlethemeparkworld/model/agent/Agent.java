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
import Idlethemeparkworld.model.buildable.BuildingStatus;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
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
    
    int destX, destY;
    
    Random rand;
    
    AgentState state;
    protected int statusMaxTimer;
    protected int statusTimer;
    protected AgentAction currentAction;
    Building currentBuilding;
    
    protected Color color;
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
        
        this.destX = 0;
        this.destY = this.destX;
        this.rand = new Random();
        
        this.state = AgentState.ENTERINGPARK;
        
        this.currentBuilding = park.getTile(x, y).getBuilding();
        
        this.color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255),255);
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
    
    public Color getColor(){
        return color;
    }
    
    public Position calculateExactPosition(int cellSize){
        Position res = prevPos.lerp(newPos, lerpTimer/24.0);
        return res;
    }
    
    protected void setState(AgentState newState){
        this.state = newState;
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
    
    protected void moveTo(Position p){
        moveTo(p.x, p.y);
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
    public void update(long tickCount){
        checkMove();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Agent{name=").append(name);
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", destX=").append(destX);
        sb.append(", destY=").append(destY);
        sb.append(", state=").append(state);
        return sb.toString();
    }
}
