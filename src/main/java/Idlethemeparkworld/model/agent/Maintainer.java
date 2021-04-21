package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.Repairable;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import java.util.ArrayList;
import java.util.LinkedList;

public class Maintainer extends Agent {

    private final int salary;
    private final static LinkedList<AgentAction> actionQueue = new LinkedList<>();

    public Maintainer(String name, Park park, AgentManager am) {
        super(name, park, am);
        this.type = AgentTypes.AgentType.STAFF;
        this.staffType = AgentTypes.StaffType.MAINTAINER;
        this.salary = 20;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public void update(long tickCount) {
        checkMove();
        statusTimer++;
        if (tickCount % 24 == 0) {
            checkFloating();
            if (state != AgentInnerLogic.AgentState.FLOATING) {
                updateState();
            }
        }
    }
    
    public static void alertOfCriticalBuilding(Building building){
        addAction(new AgentAction(AgentInnerLogic.AgentActionType.STAFFREPAIR, building));
    }
    
    private static void addAction(AgentAction action) {
        if (!actionQueue.contains(action)) {
            actionQueue.add(action);
        }
    }
    
    private void checkActionQueue(){
        if(actionQueue.isEmpty()){
            setState(AgentInnerLogic.AgentState.WANDERING);
        } else {
            currentAction = actionQueue.pop();
            Building building = currentAction.getSubject();
            if(building == null){
                currentAction = null;
            } else {
                path = park.getPathfinding().getPath(new Position(x,y), building);
                setState(AgentInnerLogic.AgentState.WALKING);
            }
        }
    }

    private void updateState() {
        switch (state) {
            case ENTERINGPARK:
                setState(AgentInnerLogic.AgentState.IDLE);
                break;
            case IDLE:
                checkActionQueue();
                break;
            case WANDERING:
                checkActionQueue();
                if(this.state != AgentState.WALKING){
                    ArrayList<Building> buildings = park.getNonPavementOrEntranceNeighbours(x, y);
                    buildings.removeIf(b -> !(b instanceof Repairable));
                    boolean found = false;
                    for (int i = 0; i < buildings.size() && !found; i++) {
                        if (((Repairable)buildings.get(i)).shouldRepair()) {
                            moveTo(buildings.get(i).getX(), buildings.get(i).getY());
                            statusMaxTimer = Time.convMinuteToTick(rand.nextInt(4)+1);
                            setState(AgentInnerLogic.AgentState.FIXING);
                            found = true;
                        }
                    }
                    if(!found){
                        moveToRandomNeighbourPavementTile();
                    }
                }
                break;
            case WALKING:
                moveOnPath();
                if (path.isEmpty() && currentBuilding instanceof Attraction || currentBuilding instanceof FoodStall) {
                    statusMaxTimer = Time.convMinuteToTick(rand.nextInt(4)+1);
                    setState(AgentInnerLogic.AgentState.FIXING);
                }
                break;
            case FIXING:
                if(statusTimer > statusMaxTimer){
                    repair(currentBuilding);
                    moveToRandomNeighbourPavementTile();
                    resetAction();
                }
                break;
            case FLOATING:
                if (statusTimer > Time.convMinuteToTick(5)) {
                    moveTo(0,0);
                    resetAction();
                }
                break;
            default:
                break;
        }
    }

    private void moveToRandomNeighbourPavementTile() {
        ArrayList<Building> neighbours = park.getPavementNeighbours(x, y);
        if (neighbours.size() > 0) {
            int nextIndex = rand.nextInt(neighbours.size());
            moveTo(neighbours.get(nextIndex).getX(), neighbours.get(nextIndex).getY());
        }
        updateCurBuilding();
    }

    private void repair(Building currentBuilding) {
        if (currentBuilding instanceof Attraction) {
            ((Attraction) currentBuilding).setCondition(100);
        } else if (currentBuilding instanceof FoodStall) {
            ((FoodStall) currentBuilding).setCondition(100);
        }
    }

}
