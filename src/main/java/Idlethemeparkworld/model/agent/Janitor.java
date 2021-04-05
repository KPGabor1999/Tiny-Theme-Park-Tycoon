package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Tile;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentActionType;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author KrazyXL
 */
public class Janitor extends Agent implements Updatable{
    
    public Janitor(String name, Park park, AgentManager am){
        super(name, park, am);
        this.type = AgentTypes.AgentType.STAFF;
        this.staffType = AgentTypes.StaffType.JANITOR;
        this.color = Color.WHITE;
    }

    @Override
    public void update(long tickCount) {
        //Randomra járkál fel alá, és ha infrastrukturális mezõre lép, kitakarítja.
        //Minden egész órakor levonjuk az órabérét.
        checkMove();
        statusTimer++;
        if(tickCount % 24 == 0){
            checkFloating();
            if(state != AgentInnerLogic.AgentState.FLOATING){
                updateState();
                performAction(tickCount);
            } else {
                updateState();
            }
        }
    }
    
    private void updateState(){
        switch(state){
            case ENTERINGPARK:
                setState(AgentState.IDLE);
                currentAction = new AgentAction(AgentActionType.ENTERPARK, null);
                break;
            case IDLE:
                setState(AgentState.WANDERING);
                currentAction = new AgentAction(AgentActionType.WANDER, null);
                break;
            //-------------------------------
            case WANDERING:
                //Minden körben randomra rálép egy új mezõre és kitakarítja azt.
            case QUEUING:
                //A mosdók takarításakor állhat sorban.
                //De õ akár a végtelenségig is vár.
                break;
            case CLEANING:
                //Kitakarítja az adott mezõt, majd visszaáll Wandering-re.
            case FLOATING:
                if(statusTimer>Time.convMinuteToTick(5)){
                    remove();
                }
            default:
                break;
        }
    }
    
    private void performAction(long tickCount){
        if(currentAction != null){
            switch (currentAction.getAction()){
                case ENTERPARK:
                    enterCycle();
                    break;
                case WANDER:
                    //Átlép egy környezõ mezõre, ami nem fû vagy lockedTile.
                    //Frissítjük a currentBuilding-et.
                    //Ha currentBuilding instanceof Infrastructure, STAFFCLEAN akció
                    moveToRandomNeighbourTile();
                    updateCurBuilding();
                    if(currentBuilding instanceof Infrastructure){
                        clean(currentBuilding);
                    }
                    break;
                case STAFFCLEAN:
                    //Kitakarítjuk a currentBuilding-et.
                    //Visszaáll Wandering-be.
                    break;
                case TOILET:
                    //toiletCycle();
                    break;
                default:
                    break;
            }
        }
    }
    
    private void enterCycle(){
        Entrance entrance = (Entrance) currentBuilding;
    }
    
    private void moveToRandomNeighbourTile(){
        ArrayList<Building> neighbours = park.getWalkableNeighbours(x, y);
        if(neighbours.size() > 0){
            int nextIndex = rand.nextInt(neighbours.size());
            moveTo(neighbours.get(nextIndex).getX(),neighbours.get(nextIndex).getY());
        }
    }
    
    private void clean(Building currentBuilding){
        
    }
    
}
