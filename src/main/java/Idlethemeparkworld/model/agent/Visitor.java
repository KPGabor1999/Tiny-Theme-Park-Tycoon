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
    
    private int timeInQueue;
    
    public Visitor(String name, int startingHappiness, Park park, AgentManager am){
        super(name, startingHappiness, park, am);
    }
    
    @Override
    public void update(long tickCount){
        if(tickCount % 24 == 0){
            performAction(actionQueue.poll());
        }
    }
    
    protected void updateState(){
        switch(state){
            case IDLE:
                energy += 0.05;
                nausea -= 0.1;
                break;
            case ENTERINGPARK:
            case LEAVINGPARK:
                break;
            case WALKING:
                energy -= 0.05;
                thirst-=0.01;
                break;
            case QUEUING:
                if(timeInQueue>2500){
                    happiness-=0.01;
                }
                break;
            case ONRIDE:
                energy -= 0.02;
                break;
            case SITTING:
                energy += 0.1;
                hunger -= 0.02;
                nausea -= 0.5;
                break;
            case BUYING:
                break;
            default:
                throw new AssertionError(state.name());
        }
        hunger-=0.02;
        thirst-=0.02;
        toilet-=0.01;
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
            case THROWUP:
                this.setState(AgentState.IDLE);
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
