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
import java.util.ArrayList;
import java.util.Random;

/**
 * Agent class, base class for all agents and people in the game.
 * 
 * Has basic movement/pathfinding functionality, render and state/action system.
 */
public abstract class Agent implements Updatable {
    protected static final int AGENT_HISTORY_LENGTH = 10;
    
    AgentManager am;
    Park park;
    
    String name;
    int x,y;
    boolean inPark;
    protected ArrayList<Position> path;
    
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
        this.path = new ArrayList<>();
    }

    /**
     * @return name of the agent
     */
    public String getName() {
        return name;
    }

    /**
     * @return type of the agent
     */
    public AgentType getType() {
        return type;
    }

    /**
     * @return staff type of the agent in case the agent is a staff
     */
    public StaffType getStaffType() {
        return staffType;
    }
    
    /**
     * @return state of the agent
     */
    public AgentState getState(){
        return state;
    }
    
    /**
     * @return x coordinate of the agent
     */
    public int getX(){
        return x;
    }

    /**
     * @return y coordinate of the agent
     */
    public int getY(){
        return y;
    }
    
    /**
     * Hol van most a karakter a képernyõn?
     * @param cellSize
     * @return 
     */
    public Position calculateExactPosition(int cellSize){
        Position res = prevPos.lerp(newPos, lerpTimer/24.0);
        return res;
    }
    
    /**
     * Karakter állapotának beállítása manuálisan.
     * @param newState 
     */
    protected void setState(AgentState newState){
        statusTimer = 0;
        state = newState;
    }
    
    /**
     * Karakter ellenõrzi, hogy lebegõ státuszban van-e?
     */
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
    
    /**
     * Karakter jelenlegi akciójának kinullázása.
     */
    protected void resetAction(){
        setState(AgentState.IDLE);
        currentAction = null;
    }
    
    /**
     * Karakter léptetése a kiválasztott útvonalon.
     */
    protected void moveOnPath(){
        if(path.size() > 0){
            Position nextPos = path.remove(0);
            if(park.getTile(nextPos.x, nextPos.y).isEmpty()
                    || park.getTile(nextPos.x, nextPos.y).getBuilding().getStatus() == BuildingStatus.DECAYED){
                resetAction();
            } else {
                moveTo(nextPos.x, nextPos.y);
            }
        }
    }
    
    /**
     * Karakter áthelyezése ide.
     * @param x
     * @param y 
     */
    protected void moveTo(int x, int y){
        prevPos = new Position(this.x*64+xOffset,this.y*64+yOffset);
        lerpTimer = 0;
        isMoving = true;
        this.x=x;
        this.y=y;
        updateCurBuilding();
        if(currentBuilding != null) {
            xOffset = rand.nextInt(currentBuilding.getInfo().getLobbyArea().width)+currentBuilding.getInfo().getLobbyArea().x;
            yOffset = rand.nextInt(currentBuilding.getInfo().getLobbyArea().height)+currentBuilding.getInfo().getLobbyArea().y;
            newPos = new Position(this.x*64+xOffset,this.y*64+yOffset);
        } else {
            setState(AgentState.FLOATING);
        }
    }
    
    /**
     * Épp mozog-e a karakter.
     */
    protected void checkMove(){
        if(isMoving){
            lerpTimer++;
            isMoving=lerpTimer<24;
        }
    }
    
    /**
     * Karakter törlése.
     */
    protected void remove(){
        am.removeAgent(this);
    }
    
    /**
     * Frissítjük, melyik épületben van a karakter.
     */
    protected void updateCurBuilding(){
        currentBuilding = park.getTile(x, y).getBuilding();
    }
    
    /**
     * Karakter frissítése az updatecycle-ben.
     * @param tickCount 
     */
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
