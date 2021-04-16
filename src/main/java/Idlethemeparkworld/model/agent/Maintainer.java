package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import java.awt.Color;
import java.util.ArrayList;

public class Maintainer extends Agent {

    private int salary;     //dollars per hour

    public Maintainer(String name, Park park, AgentManager am) {
        super(name, park, am);
        this.type = AgentTypes.AgentType.STAFF;
        this.staffType = AgentTypes.StaffType.MAINTAINER;
        this.color = Color.BLACK;
        this.salary = 20;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public void update(long tickCount) {
        //Randomra járkál fel alá, és ha attrakció vagy büfé mezõre lép, kitakarítja.
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
                setState(AgentInnerLogic.AgentState.IDLE);
                break;
            case IDLE:
                setState(AgentInnerLogic.AgentState.WANDERING);
                currentAction = new AgentAction(AgentInnerLogic.AgentActionType.WANDER, null);
                break;
            case WANDERING:
                currentAction = new AgentAction(AgentInnerLogic.AgentActionType.WANDER, null);
                break;
            case FIXING:
                currentAction = new AgentAction(AgentInnerLogic.AgentActionType.STAFFREPAIR, null);
                break;
            case FLOATING:
                if (statusTimer > Time.convMinuteToTick(5)) {
                    remove();
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
                    if (currentBuilding instanceof Attraction || currentBuilding instanceof FoodStall) {
                        setState(AgentInnerLogic.AgentState.FIXING);
                    }
                    break;
                case STAFFREPAIR:
                    //Kitakarítjuk a currentBuilding-et.
                    //Visszaáll Wandering-be.
                    repair(currentBuilding);
                    setState(AgentInnerLogic.AgentState.WANDERING);
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

    private void repair(Building currentBuilding) {
        if (currentBuilding instanceof Attraction) {
            ((Attraction) currentBuilding).setCondition(100);
            //System.out.println("Megjavítottam egy attrakciot.");
        } else if (currentBuilding instanceof FoodStall) {
            ((FoodStall) currentBuilding).setCondition(100);
            //System.out.println("Megjavítottam egy büfet.");
        }
    }

}
