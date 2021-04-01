package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentAction;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodItem;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import java.util.ArrayList;

public class Visitor extends Agent {
    private int cash;
    private int cashSpent;
    private int entryTime;
    private AgentAction currentAction;
    
    private Position lastEnter;
    private FoodItem item;
    
    private int statusMaxTimer;
    private int statusTimer;
    
    public Visitor(String name, int startingHappiness, Park park, AgentManager am){
        super(name, startingHappiness, park, am);
    }
    
    @Override
    public void update(long tickCount){
        statusTimer++;
        if(tickCount % 24 == 0){
            performAction();
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
            case WALKING:
                energy -= 0.05;
                thirst-=0.01;
                break;
            case QUEUING:
                if(statusTimer>2500){
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
        
    protected void performAction(){
        switch (currentAction.getAction()){
            case EAT:
                eatCycle();
                break;
            case SIT:
                sitCycle();
                break;
            case WANDER:
                moveToRandomNeighbourPavement();
                break;
            case TOILET:
                toiletCycle();
                break;
            case LEAVEPARK:
                leaveParkCycle();
                break;
            case RIDE:
                attractionCycle();
                break;
            case THROWUP:
                //TODO
                break;
            case NONE:
                break;
            default:
                break;
        }
    }
    
    private void moveToRandomNeighbourPavement(){
        ArrayList<Building> paves = park.getPavementNeighbours(x, y);
        int nextIndex = rand.nextInt(paves.size());
        moveTo(paves.get(nextIndex).getX(),paves.get(nextIndex).getY());
    }
    
    private void resetAction(){
        state = AgentState.IDLE;
        item = null;
        currentAction = null;
    }
    
    private void leaveParkCycle(){
        switch(state){
            case IDLE:
                state = AgentState.LEAVINGPARK;
                break;
            case LEAVINGPARK:
                moveToRandomNeighbourPavement();
                if(currentBuilding instanceof Entrance){
                    //leaves a new review for the entire park based on final happiness
                    //enter data into history
                    am.removeAgent(this);
                }
                break;
            default:
                break;
        }
    }
    
    private void attractionCycle(){
        Attraction attr;
        switch(state){
            case IDLE:
                state = AgentState.WANDERING;
                break;
            case WANDERING:
                ArrayList<Building> bs = park.getNonPavementNeighbours(x, y);
                bs.removeIf(b -> !(b instanceof Attraction));
                if(bs.size() > 0){
                    for (int i = 0; i < bs.size(); i++) {
                        if(rand.nextBoolean()){
                            lastEnter = new Position(x, y);
                            setDestination(bs.get(i).getX(),bs.get(i).getY());
                            moveTo(bs.get(i).getX(),bs.get(i).getY());
                            ((Attraction)currentBuilding).joinQueue(this);
                            state = AgentState.QUEUING;
                            statusTimer = 0;
                            break;
                        }
                    }
                    if(state != AgentState.QUEUING){
                        moveToRandomNeighbourPavement();
                    }
                } else {
                    moveToRandomNeighbourPavement();
                }
                break;
            case QUEUING:
                attr = ((Attraction)currentBuilding);
                if(statusTimer>this.patience){
                    attr.leaveQueue(this);
                    this.happiness -= 5;
                    state = AgentState.IDLE;
                }
                break;
            case ONRIDE:
                break;
            default:
                break;
        }
    }
    
    private void toiletCycle(){
        Toilet tlt;
        
        switch(state){
            case IDLE:
                state = AgentState.WANDERING;
                break;
            case WANDERING:
                ArrayList<Building> bs = park.getNonPavementNeighbours(x, y);
                bs.removeIf(b -> !(b instanceof Toilet));
                if(bs.size() > 0){
                    lastEnter = new Position(x, y);
                    setDestination(bs.get(0).getX(),bs.get(0).getY());
                    moveTo(bs.get(0).getX(),bs.get(0).getY());
                    ((Toilet)currentBuilding).joinLine(this);
                    state = AgentState.QUEUING;
                    break;
                } else {
                    moveToRandomNeighbourPavement();
                }
                break;
            case QUEUING:
                tlt = ((Toilet)currentBuilding);
                if(statusTimer>this.patience){
                    tlt.leaveLine(this);
                    this.happiness -= 5;
                    state = AgentState.IDLE;
                } else {
                    if(tlt.isFirstInQueue(this)){
                        if(tlt.isThereEmptyStool()){
                            tlt.enter(this);
                            state = AgentState.SHITTING;
                            statusMaxTimer = Time.convMinuteToTick(rand.nextInt(5)+2);
                            statusTimer = 0;
                        }
                    }
                }
                break;
            case SHITTING:
                tlt = ((Toilet)currentBuilding);
                if(statusTimer>statusMaxTimer){
                    tlt.decreaseHygiene(rand.nextDouble());
                    tlt.exit();
                    moveTo(lastEnter.x, lastEnter.y);
                    resetAction();
                }
                break;
            default:
                break;
        }
    }
    
    private void sitCycle(){
        Pavement pave;
        switch(state){
            case IDLE:
                state = AgentState.WANDERING;
                break;
            case WANDERING:
                pave = (Pavement)currentBuilding;
                if(/*pave.hasFreeSeating()*/true){
                    //pave.sit();
                    state = AgentState.SITTING;
                } else {
                    moveToRandomNeighbourPavement();
                }
                break;
            case SITTING:
                pave = (Pavement)currentBuilding;
                if(energy >= 100){
                    //pave.leaveSeating();
                    resetAction();
                }
                break;
            default:
                break;
        }
    }
    
    private void eatCycle(){
        FoodStall stall;
        switch(state){
            case IDLE:
                state = AgentState.WANDERING;
                break;
            case WANDERING:
                ArrayList<Building> bs = park.getNonPavementNeighbours(x, y);
                bs.removeIf(b -> !(b instanceof FoodStall));
                if(bs.size() > 0){
                    for (int i = 0; i < bs.size(); i++) {
                        if(rand.nextBoolean()){
                            lastEnter = new Position(x, y);
                            setDestination(bs.get(i).getX(),bs.get(i).getY());
                            moveTo(bs.get(i).getX(),bs.get(i).getY());
                            ((FoodStall)currentBuilding).joinQueue(this);
                            state = AgentState.QUEUING;
                            statusTimer = 0;
                            break;
                        }
                    }
                    if(state != AgentState.QUEUING){
                        moveToRandomNeighbourPavement();
                    }
                } else {
                    moveToRandomNeighbourPavement();
                }
                break;
            case QUEUING:
                stall = ((FoodStall)currentBuilding);
                if(statusTimer>this.patience){
                    stall.leaveQueue(this);
                    this.happiness -= 5;
                    state = AgentState.IDLE;
                } else {
                    if(stall.isFirstInQueue(this)){
                        state = AgentState.BUYING;
                    }
                }
                break;
            case BUYING:
                stall = ((FoodStall)currentBuilding);
                if(stall.canService()){
                    item = stall.buyFood(this);
                    moveTo(lastEnter.x, lastEnter.y);
                    state = AgentState.EATING;
                    statusTimer = 0;
                }
                break;
            case EATING:
                moveToRandomNeighbourPavement();
                if(statusTimer>Time.convMinuteToTick(item.consumeTime)){
                    hunger += item.hunger;
                    thirst += item.thirst;
                    resetAction();
                }
                break;
            default:
                break;
        }
    }
    
    public boolean canPay(int amount){
        return amount <= cash;
    }
    
    public void pay(int amount){
        cash -= amount;
        cashSpent += amount;
    }
    
    public void setOnRide(){
        state = AgentState.ONRIDE;
    }
    
    public void sendRideEvent(int rideEvent){
        happiness += rideEvent;
        moveTo(lastEnter.x, lastEnter.y);
        state = AgentState.IDLE;
    }
}
