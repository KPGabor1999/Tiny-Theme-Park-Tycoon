package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.misc.utils.Position;
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
import java.util.LinkedList;

public class Janitor extends Agent implements Updatable {

    private final int salary;
    private final static LinkedList<AgentAction> actionQueue = new LinkedList<>();

    public Janitor(String name, Park park, AgentManager am) {
        super(name, park, am);
        this.type = AgentTypes.AgentType.STAFF;
        this.staffType = AgentTypes.StaffType.JANITOR;
        this.salary = 8;
    }

    public int getSalary() {
        return salary;
    }

    /**
     * Takarító frissítése az updatecycle-ben.
     * @param tickCount 
     */
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
    
    /**
     * Új teendõ kijelölése a takarítónak.
     * @param action 
     */
    private static void addAction(AgentAction action) {
        if (!actionQueue.contains(action)) {
            actionQueue.add(action);
        }
    }
    
    /**
     * Felhívjuk egy takarító figyelmét arra, hogy egy épület sürgõsen takarításra szorul.
     * @param building 
     */
    public static void alertOfCriticalBuilding(Building building){
        addAction(new AgentAction(AgentActionType.STAFFCLEAN, building));
    }
    
    /**
     * Cselekvés a teendõlista szerint.
     */
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

    /**
     * Takarító állapotának frissítése.
     */
    private void updateState() {
        switch (state) {
            case ENTERINGPARK:
                setState(AgentState.IDLE);
                break;
            case IDLE:
                checkActionQueue();
                break;
            case WANDERING:
                checkActionQueue();
                if(this.state != AgentState.WALKING){
                    moveToRandomNeighbourTile();
                    if (currentBuilding instanceof Infrastructure && ((Infrastructure)currentBuilding).shouldClean()) {
                        statusMaxTimer = Time.convRealLifeSecondToTick(rand.nextInt(4)+1);
                        setState(AgentState.CLEANING);
                    }
                }
                break;
            case WALKING:
                moveOnPath();
                if (path.isEmpty() && currentBuilding instanceof Infrastructure) {
                    statusMaxTimer = Time.convRealLifeSecondToTick(rand.nextInt(4)+1);
                    setState(AgentState.CLEANING);
                }
                break;
            case CLEANING:
                if(statusTimer > statusMaxTimer){
                    clean(currentBuilding);
                    resetAction();
                }
                break;
            case FLOATING:
                if (statusTimer > Time.convRealLifeSecondToTick(5)) {
                    moveTo(0,0);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Átlépés egy véletlen szomszédos mezõre, ami nem fû vagy lezárt mezõ.
     */
    private void moveToRandomNeighbourTile() {
        ArrayList<Building> neighbours = park.getInfrastructureNeighbours(x, y);
        if (neighbours.size() > 0) {
            int nextIndex = rand.nextInt(neighbours.size());
            moveTo(neighbours.get(nextIndex).getX(), neighbours.get(nextIndex).getY());
        }
        updateCurBuilding();
    }

    /**
     * Infrastrukturális elem kitakarítása.
     * @param currentBuilding 
     */
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
