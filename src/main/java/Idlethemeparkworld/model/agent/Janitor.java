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
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author KrazyXL
 */
public class Janitor extends Agent implements Updatable{
    private int salary;     //dollars per hour
    
    public Janitor(String name, Park park, AgentManager am){
        super(name, park, am);
        this.type      = AgentTypes.AgentType.STAFF;
        this.staffType = AgentTypes.StaffType.JANITOR;
        this.color     = Color.WHITE;
        this.salary    = 8;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public void update(long tickCount) {
        //Randomra járkál fel alá, és ha infrastrukturális mezõre lép, kitakarítja.
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
        
        //Minden egész órakor levonjuk az órabérét. (AgentManager-ben)
    }
    
    private void updateState(){
        switch(state){
            case ENTERINGPARK:
                setState(AgentState.IDLE);
                currentAction = new AgentAction(AgentActionType.ENTERPARK, null);
                //System.out.println("Beleptem a parkba.");
                break;
            case IDLE:
                setState(AgentState.WANDERING);
                currentAction = new AgentAction(AgentActionType.WANDER, null);
                //System.out.println("Setalni megyek.");
                break;
            //-------------------------------
            case WANDERING:
                //Minden körben randomra rálép egy új mezõre és kitakarítja azt.
                currentAction = new AgentAction(AgentActionType.WANDER, null);
                //System.out.println("Továbbsetalok.");
                break;
            case QUEUING:
                //System.err.println("Én nem kéne, hogy sorban álljak.");
                //A mosdók takarításakor állhat sorban.
                //De õ akár a végtelenségig is vár.
                break;
            case CLEANING:
                //Kitakarítja az adott mezõt, majd visszaáll Wandering-re.
                currentAction = new AgentAction(AgentActionType.STAFFCLEAN, null);
                //System.out.println("Ez a hely takaritast igenyel.");
                break;
            case FLOATING:
                //System.err.println("Én nem kéne, hogy lebegjek.");
                if(statusTimer>Time.convMinuteToTick(5)){
                    remove();
                }
                break;
            default:
                //System.err.println("Miért vagyok " + getState() + " állapotban?");
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
                        setState(AgentState.CLEANING);
                    }
                    break;
                case STAFFCLEAN:
                    //Kitakarítjuk a currentBuilding-et.
                    //Visszaáll Wandering-be.
                    clean(currentBuilding);
                    setState(AgentState.WANDERING);
                    break;
                case TOILET:
                    //System.err.println("Nekem nem is kéne a vécén ülnöm.");
                    //toiletCycle();
                    break;
                default:
                    //System.err.println("Miért csinálom ezt: " + getCurrentAction() + "?");
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
        if(currentBuilding instanceof Entrance){
            ((Entrance)currentBuilding).setLittering(0);
            System.out.println("Felszedtem a szemetet a bejáratnál.");
        } else if(currentBuilding instanceof Pavement){
            ((Pavement)currentBuilding).setLittering(0);
            System.out.println("Felszedtem a szemetet egy járdáról.");
        } else if(currentBuilding instanceof Toilet){
            ((Toilet)currentBuilding).setLittering(0);
            ((Toilet)currentBuilding).setCleanliness(100);
            System.out.println("Kitakarítottam egy mosdót.");
        } else if(currentBuilding instanceof TrashCan){
            ((TrashCan)currentBuilding).setLittering(0);
            ((TrashCan)currentBuilding).setFilled(0);
            System.out.println("Kiürítettem egy szemetest.");
        }
    }
    
}
