package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.misc.utils.Direction;
import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentAction;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.buildable.Building;

public class Visitor extends Agent {
    private int cash;
    private int cashSpent;
    private int entryTime;
    
    public Visitor(String name, int startingHappiness, Park park, AgentManager am){
        super(name, startingHappiness, park, am);
    }
    
    @Override
    public void update(long tickCount){
        if(tickCount % 24 == 0){
            performAction(actionQueue.poll());
        }
    }
    
    
    
    protected void performAction(AgentAction action){
        switch (action.getAction()){
            case EAT:
                break;
            case SIT:
                break;
            case WANDER:
                do{
                    dir = Direction.randomDirection();
                } while(park.getTile(x+dir.x, y+dir.y).getBuilding().getInfo() != BuildType.PAVEMENT);
                moveForward();
                
                break;
            case ENTER:
                this.setState(AgentState.ENTERINGBUILDING);
                moveTo(destX, destY);
                break;
            case EXIT:
                this.setState(AgentState.LEAVINGBUILDING);
                moveTo(destX, destY);
                break;
            case RIDE:
                break;
            case WOW:
                break;
            case THROWUP:
                this.setState(AgentState.IDLE);
                break;
            case READMAP:
                break;
            case TAKEPHOTO:
                break;
            case CLAP:
                break;
            case NONE:
                break;
            default:
                this.setState(AgentState.IDLE);
        }
    }
    
    //Agent joins the chosen attraction's queue
    public void joinQueue(Building attraction){
        
    }
}
