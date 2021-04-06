package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentActionType;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentThoughts;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodItem;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;
import java.awt.Color;
import java.util.ArrayList;

public class Visitor extends Agent {
    protected static final int AGENT_HUNGER_WARNING_THRESHOLD = 35;
    protected static final int AGENT_THIRST_WARNING_THRESHOLD = 35;
    protected static final int AGENT_TOILET_WARNING_THRESHOLD = 35;
    protected static final int AGENT_ENERGY_WARNING_THRESHOLD = 25;
    protected static final int AGENT_LOST_WARNING_THRESHOLD = 8;

    protected static final int AGENT_STATUS_MAXIMUM = 100;
    
    private int cash;
    private int cashSpent;
    private AgentAction currentAction;
    
    int patience;
    int energy;
    int happiness;
    int nausea;
    int hunger;
    int thirst;
    int toilet;
    int angriness;
    
    private Position lastEnter;
    private FoodItem item;
    
    private int statusMaxTimer;
    private int statusTimer;
    
    public Visitor(String name, int startingHappiness, Park park, AgentManager am){
        super(name, startingHappiness, park, am);
        this.cash = rand.nextInt(1000)+1000;
        this.cashSpent = 0;
        this.currentAction = new AgentAction(AgentActionType.ENTERPARK,null);
        
        this.patience = Time.convMinuteToTick(10);
        this.energy = 100;
        this.happiness = startingHappiness;
        this.nausea = 0;
        this.hunger = 100;
        this.thirst = 100;
        this.toilet = 100;
        this.angriness = 0;
        
        this.lastEnter = null;
        this.item = null;
        this.statusMaxTimer = 0;
        this.statusTimer = 0;
        setState(AgentState.ENTERINGPARK);
    }
    
    @Override
    public void update(long tickCount){
        super.update(tickCount);
        statusTimer++;
        if(tickCount % 24 == 0){
            checkFloating();
            if(state != AgentState.FLOATING){
                generateThoughts(tickCount);
                updateThought(tickCount);
                updateState();
                updateCurrentAction();
                performAction(tickCount);
                normalizeStatuses();
            } else {
                updateState();
            }
        }
    }
    
    private void checkFloating(){
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
    
    private void updateCurrentAction(){
         if(currentAction == null){
             if(!actionQueue.isEmpty()){
                 currentAction = actionQueue.poll();
             }
         }
    }
    
    private void updateThought(long tickCount){
        for (int i = 0; i < thoughts.size(); i++) {
            if(tickCount - thoughts.get(i).timeCreated > Time.convMinuteToTick(5)){
                thoughts.remove(i);
            }
        }
    }
    
    private void thoughtToHappiness(AgentThoughts thoughtType){
        switch(thoughtType){
            case CANTAFFORD:
            case BADVALUE:
            case LOST:
            case LONGQUEUE:
            case HUNGRY:
            case THIRSTY:
            case TIRED:
            case TOOMUCHLITTER:
            case CROWDED:
                happiness--;
                break;
            case WOW:
            case GOODVALUE:
            case FEELINGGREAT:
            case NOTHUNGRY:
            case NOTTHIRSTY:
            case CLEAN:
                happiness++;
                break;
            default: break;
        }
    }
    
    private void addAction(AgentAction action){
        if(!actionQueue.contains(action)){
            actionQueue.add(action);
        }
    }
    
    private void thoughtToAction(AgentThoughts thoughtType){
        switch(thoughtType){
            case NOMONEY:
                actionQueue.clear();
                addAction(new AgentAction(AgentActionType.LEAVEPARK,null));
                break;
            case WANTTHRILL:
                addAction(new AgentAction(AgentActionType.RIDE,null)); break;
            case GOHOME:
                actionQueue.clear();
                addAction(new AgentAction(AgentActionType.LEAVEPARK,null));
                break;
            case TIRED:
                addAction(new AgentAction(AgentActionType.SIT,null)); break;
            case HUNGRY:
                addAction(new AgentAction(AgentActionType.EAT,null)); break;
            case THIRSTY:
                addAction(new AgentAction(AgentActionType.EAT,null)); break;
            case TOILET:
                addAction(new AgentAction(AgentActionType.TOILET,null)); break;
            case CROWDED:
                addAction(new AgentAction(AgentActionType.WANDER,null)); break;
            default:
                break;
        }
    }
        
    private void insertThought(AgentThoughts thoughtType, Building subject,long tickCount){
        AgentThought thought = new AgentThought(thoughtType,subject,tickCount);
        if(!thoughts.contains(thought)){
            thoughts.add(thought);
            thoughtToHappiness(thoughtType);
            thoughtToAction(thoughtType);
        }
    }
    
    private boolean conditionToThought(int condition, int lowerThreshold, long tickCount, AgentThoughts positive, AgentThoughts negative, double leaveHappinessMultiplier){
        if(condition < lowerThreshold){
            if(condition <= 0){
                happiness *= leaveHappinessMultiplier;
                insertThought(AgentThoughts.GOHOME,null,tickCount);
                return true;
            } else {
                insertThought(negative,null,tickCount);
                return true;
            }
        } else if(95 < condition){
            insertThought(positive,null,tickCount);
        }
        return false;
    }
    
    private void generateThoughts(long tickCount){
        if(cash < 50){
            if(cash <= 0){
                insertThought(AgentThoughts.NOMONEY,null,tickCount);
            } else {
                insertThought(AgentThoughts.LOWMONEY,null,tickCount);
            }
        }
        boolean anyUrgent = false;
        anyUrgent = anyUrgent || conditionToThought(hunger,AGENT_HUNGER_WARNING_THRESHOLD,tickCount,AgentThoughts.NOTHUNGRY, AgentThoughts.HUNGRY, 0.5);
        anyUrgent = anyUrgent || conditionToThought(thirst,AGENT_THIRST_WARNING_THRESHOLD,tickCount,AgentThoughts.NOTTHIRSTY, AgentThoughts.THIRSTY, 0.6);
        anyUrgent = anyUrgent || conditionToThought(energy,AGENT_ENERGY_WARNING_THRESHOLD,tickCount,AgentThoughts.FEELINGGREAT, AgentThoughts.TIRED, 0.1);
        if(toilet < AGENT_TOILET_WARNING_THRESHOLD){
            insertThought(AgentThoughts.TOILET,null,tickCount);
        }
        if(happiness < 75 || !anyUrgent){
            insertThought(AgentThoughts.WANTTHRILL,null,tickCount);
        }
    }
    
    private void updateState(){
        switch(state){
            case IDLE:
                energy += 0.05;
                //nausea -= 0.1;
                break;
            case ENTERINGPARK:
            case LEAVINGPARK:
            case WANDERING:
            case WALKING:
                energy -= 0.03;
                hunger -= 0.01;
                thirst-=0.01;
                break;
            case QUEUING:
                if(statusTimer>patience){
                    happiness-=0.5;
                }
                break;
            case ONRIDE:
                energy -= 0.02;
                break;
            case SITTING:
                energy += 0.1;
                hunger -= 0.02;
                //nausea -= 0.5;
                break;
            case BUYING:
                break;
            case FLOATING:
                if(statusTimer>Time.convMinuteToTick(5)){
                    remove();
                }
            default:
                break;
        }
        hunger-=0.02;
        thirst-=0.01;
        toilet-=0.01;
    }
        
    private void performAction(long tickCount){
        if(currentAction != null){
            switch (currentAction.getAction()){
                case ENTERPARK:
                    enterCycle();
                    break;
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
                case LITTER:
                    litterCycle();
                    break;
                case NONE:
                    break;
                default:
                    break;
            }
        }
    }
    
    private void moveToRandomNeighbourPavement(){
        ArrayList<Building> paves = park.getPavementNeighbours(x, y);
        if(paves.size() > 0){
            int nextIndex = rand.nextInt(paves.size());
            moveTo(paves.get(nextIndex).getX(),paves.get(nextIndex).getY());
        }
    }
    
    private void normalizeStatuses(){
        happiness = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, happiness));
        energy = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, energy));
        hunger = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, hunger));
        thirst = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, thirst));
        toilet = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, toilet));
    }
    
    private void resetAction(){
        setState(AgentState.IDLE);
        currentAction = null;
    }
    
    private void leaveParkCycle(){
        switch(state){
            case IDLE:
                setState(AgentState.LEAVINGPARK);
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
    
    private void enterCycle(){
        Entrance entrance = (Entrance) currentBuilding;
        entrance.enterPark(this);
        resetAction();
    }
    
    private void attractionCycle(){
        Attraction attr;
        switch(state){
            case IDLE:
                setState(AgentState.WANDERING);
                break;
            case WANDERING:
                ArrayList<Building> bs = park.getNonPavementNeighbours(x, y);
                bs.removeIf(b -> !(b instanceof Attraction));
                if(bs.size() > 0){
                    for (int i = 0; i < bs.size(); i++) {
                        Attraction at = (Attraction)bs.get(i);
                        if(rand.nextBoolean() && at.getQueueLength() <= at.getCapacity()*1.5){
                            lastEnter = new Position(x, y);
                            moveTo(bs.get(i).getX(),bs.get(i).getY());
                            ((Attraction)currentBuilding).joinQueue(this);
                            setState(AgentState.QUEUING);
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
                    moveTo(lastEnter.x, lastEnter.y);
                    this.happiness -= 5;
                    setState(AgentState.IDLE);
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
                setState(AgentState.WANDERING);
                break;
            case WANDERING:
                ArrayList<Building> bs = park.getNonPavementNeighbours(x, y);
                bs.removeIf(b -> !(b instanceof Toilet));
                if(bs.size() > 0){
                    lastEnter = new Position(x, y);
                    moveTo(bs.get(0).getX(),bs.get(0).getY());
                    ((Toilet)currentBuilding).joinLine(this);
                    setState(AgentState.QUEUING);
                    break;
                } else {
                    moveToRandomNeighbourPavement();
                }
                break;
            case QUEUING:
                tlt = ((Toilet)currentBuilding);
                if(statusTimer>this.patience){
                    tlt.leaveLine(this);
                    moveTo(lastEnter.x, lastEnter.y);
                    this.happiness -= 5;
                    setState(AgentState.IDLE);
                } else {
                    if(tlt.isFirstInQueue(this)){
                        if(tlt.isThereEmptyStool()){
                            tlt.enter(this);
                            setState(AgentState.SHITTING);
                            statusMaxTimer = Time.convMinuteToTick(rand.nextInt(5)+2);
                            statusTimer = 0;
                        }
                    }
                }
                break;
            case SHITTING:
                tlt = ((Toilet)currentBuilding);
                if(statusTimer>statusMaxTimer){
                    toilet=100;
                    thirst+=30;
                    tlt.decreaseHygiene(rand.nextDouble()/2);
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
                setState(AgentState.WANDERING);
                break;
            case WANDERING:
                pave = (Pavement)currentBuilding;
                if(/*pave.hasFreeSeating()*/true){
                    //pave.sit();
                    setState(AgentState.SITTING);
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
                setState(AgentState.WANDERING);
                break;
            case WANDERING:
                ArrayList<Building> bs = park.getNonPavementNeighbours(x, y);
                bs.removeIf(b -> !(b instanceof FoodStall));
                if(bs.size() > 0){
                    for (int i = 0; i < bs.size(); i++) {
                        if(rand.nextBoolean()){
                            lastEnter = new Position(x, y);
                            moveTo(bs.get(i).getX(),bs.get(i).getY());
                            ((FoodStall)currentBuilding).joinQueue(this);
                            setState(AgentState.QUEUING);
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
                if(statusTimer>patience){
                    stall.leaveQueue(this);
                    moveTo(lastEnter.x, lastEnter.y);
                    this.happiness -= 5;
                    resetAction();
                } else {
                    if(stall.isFirstInQueue(this)){
                        setState(AgentState.BUYING);
                    }
                }
                break;
            case BUYING:
                stall = ((FoodStall)currentBuilding);
                if(stall.canService()){
                    item = stall.buyFood(this);
                    moveTo(lastEnter.x, lastEnter.y);
                    setState(AgentState.EATING);
                    statusTimer = 0;
                }
                break;
            case EATING:
                moveToRandomNeighbourPavement();
                if(statusTimer>Time.convMinuteToTick(item.consumeTime)){
                    hunger += item.hunger;
                    thirst += item.thirst;
                    resetAction();
                    currentAction = new AgentAction(AgentActionType.LITTER, null);
                }
                break;
            default:
                break;
        }
    }
    
    private void litterCycle(){
        TrashCan tc;
        switch(state){
            case IDLE:
                setState(AgentState.WANDERING);
                statusTimer = 0;
                break;
            case WANDERING:
                if(statusTimer>patience){
                    Infrastructure i = (Infrastructure)currentBuilding;
                    happiness-=5;
                    i.litter(item.consumeTime*0.01);
                    item = null;
                    resetAction();
                } else {
                    ArrayList<Building> bs = park.getNonPavementNeighbours(x, y);
                    bs.removeIf(b -> !(b instanceof TrashCan));
                    if(bs.size() > 0) {
                        for (int i = 0; i < bs.size(); i++) {
                            tc = ((TrashCan)bs.get(i));
                            if(!tc.isFull()){
                                tc.use(item.consumeTime*0.01);
                                item = null;
                                resetAction();
                                break;
                            }
                        }
                        if(state != AgentState.IDLE){
                            moveToRandomNeighbourPavement();
                        }
                    } else {
                        moveToRandomNeighbourPavement();
                    }
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
        setState(AgentState.ONRIDE);
    }
    
    public void sendRideEvent(int rideEvent){
        happiness += rideEvent;
        moveTo(lastEnter.x, lastEnter.y);
        this.resetAction();
    }
    
    public int getCashSpent(){
        return cashSpent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", cash=").append(cash);
        sb.append(", cashSpent=").append(cashSpent);
        sb.append(", currentAction=").append(currentAction);
        sb.append(", lastEnter=").append(lastEnter);
        sb.append(", item=").append(item);
        sb.append(", statusMaxTimer=").append(statusMaxTimer);
        sb.append(", statusTimer=").append(statusTimer);
        sb.append('}');
        return sb.toString();
    }
    
    public int getPatience() {
        return patience;
    }

    public int getEnergy() {
        return energy;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getNausea() {
        return nausea;
    }

    public int getHunger() {
        return hunger;
    }

    public int getThirst() {
        return thirst;
    }

    public int getToilet() {
        return toilet;
    }

    public int getAngriness() {
        return angriness;
    }
}
