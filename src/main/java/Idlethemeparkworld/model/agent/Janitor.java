package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentActionType;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;
import java.util.ArrayList;

public class Janitor extends Agent implements Updatable {

    private final int salary;     //dollars per hour

    public Janitor(String name, Park park, AgentManager am) {
        super(name, park, am);
        this.type = AgentTypes.AgentType.STAFF;
        this.staffType = AgentTypes.StaffType.JANITOR;
        this.salary = 8;
    }

    public AgentState getState() {
        return state;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public void update(long tickCount) {
        //Randomra járkál fel alá, és ha infrastrukturális mezõre lép, kitakarítja.
        checkMove();
        statusTimer++;
        if (tickCount % 24 == 0) {
            checkFloating();
            if (state != AgentInnerLogic.AgentState.FLOATING) {
                updateState();
                performAction(tickCount);
            } else {
                updateState();
            }
        }
    }

    private void updateState() {
        switch (state) {
            case ENTERINGPARK:
                setState(AgentState.IDLE);
                break;
            case IDLE:
                setState(AgentState.WANDERING);
                currentAction = new AgentAction(AgentActionType.WANDER, null);
                break;
            case WANDERING:
                currentAction = new AgentAction(AgentActionType.WANDER, null);
                break;
            case CLEANING:
                currentAction = new AgentAction(AgentActionType.STAFFCLEAN, null);
                break;
            case FLOATING:
                if (statusTimer > Time.convMinuteToTick(5)) {
                    moveTo(0,0);
                }
                break;
            default:
                break;
        }
    }

    private void performAction(long tickCount) {
        if (currentAction != null) {
            switch (currentAction.getAction()) {
                case WANDER:
                    //Átlép egy környezõ mezõre, ami nem fû vagy lockedTile.
                    //Frissítjük a currentBuilding-et.
                    //Ha currentBuilding instanceof Infrastructure, STAFFCLEAN akció
                    moveToRandomNeighbourTile();
                    updateCurBuilding();
                    if (currentBuilding instanceof Infrastructure) {
                        setState(AgentState.CLEANING);
                    }
                    break;
                case STAFFCLEAN:
                    //Kitakarítjuk a currentBuilding-et.
                    //Visszaáll Wandering-be.
                    clean(currentBuilding);
                    setState(AgentState.WANDERING);
                    break;
                default:
                    break;
            }
        }
    }

    private void moveToRandomNeighbourTile() {
        ArrayList<Building> neighbours = park.getWalkableNeighbours(x, y);
        if (neighbours.size() > 0) {
            int nextIndex = rand.nextInt(neighbours.size());
            moveTo(neighbours.get(nextIndex).getX(), neighbours.get(nextIndex).getY());
        }
    }

    private void clean(Building currentBuilding) {
        //((Infrastructure) currentBuilding).sweep(rand.nextInt(5));
        ((Infrastructure) currentBuilding).sweep(100);
        if (currentBuilding instanceof Toilet) {
            //((Toilet) currentBuilding).clean(rand.nextInt(5));
            ((Toilet) currentBuilding).clean(100);
        } else if (currentBuilding instanceof TrashCan) {
            ((TrashCan) currentBuilding).empty();
        }
    }

}
