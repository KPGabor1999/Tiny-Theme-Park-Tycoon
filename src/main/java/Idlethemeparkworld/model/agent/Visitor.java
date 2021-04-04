package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentActionType;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentState;
import Idlethemeparkworld.model.agent.AgentInnerLogic.AgentThoughts;
import Idlethemeparkworld.model.agent.AgentTypes.AgentType;
import Idlethemeparkworld.model.agent.AgentTypes.StaffType;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodItem;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import java.awt.Color;
import java.util.ArrayList;

public class Visitor extends Agent {
    private int cash;
    private int cashSpent;
    private AgentAction currentAction;
    
    private Position lastEnter;
    private FoodItem item;
    
    private int statusMaxTimer;
    private int statusTimer;
    
    private Color color;
    private int xOffset;
    private int yOffset;
    
    public Visitor(String name, int startingHappiness, Park park, AgentManager am){
        super(name, startingHappiness, park, am);
        this.cash = rand.nextInt(1000)+1000;
        this.cashSpent = 0;
        this.currentAction = new AgentAction(AgentActionType.ENTERPARK,null);
        this.lastEnter = null;
        this.item = null;
        this.statusMaxTimer = 0;
        this.statusTimer = 0;
        this.color = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255),255);
        this.xOffset = rand.nextInt(64);
        this.yOffset = rand.nextInt(64);
    }
    
    @Override
    public void update(long tickCount){
        statusTimer++;
        if(tickCount % 24 == 0){
            checkFloating();
            if(state != AgentState.FLOATING){
                generateThoughts(tickCount);
                updateThought(tickCount);
                updateState();
                updateCurrentAction();
                performAction();
                normalizeStatuses();
            } else {
                updateState();
            }
        }
    }
    
    @Override
    public void moveTo(int x, int y){
        this.x=x;
        this.y=y;
        currentBuilding = park.getTile(x, y).getBuilding();
        xOffset = rand.nextInt(currentBuilding.getInfo().getWidth()*64);
        yOffset = rand.nextInt(currentBuilding.getInfo().getLength()*64);
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
        anyUrgent = anyUrgent || conditionToThought(energy,AGENT_ENERGY_WARNING_THRESHOLD,tickCount,AgentThoughts.FEELINGGREAT, AgentThoughts.TIRED, 0.5);
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
                if(statusTimer>Time.convMinuteToTick(5)){
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
                    kill();
                }
            default:
                break;
        }
        hunger-=0.02;
        thirst-=0.01;
        toilet-=0.01;
    }
        
    private void performAction(){
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
        state = AgentState.IDLE;
        item = null;
        currentAction = null;
    }
    
    private void kill(){
        am.removeAgent(this);
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
    
    private void enterCycle(){
        Entrance entrance = (Entrance) currentBuilding;
        entrance.enterPark(this);
        resetAction();
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
                        Attraction at = (Attraction)bs.get(i);
                        if(rand.nextBoolean() && at.getQueueLength() <= at.getCapacity()*1.5){
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
                    moveTo(lastEnter.x, lastEnter.y);
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
                    moveTo(lastEnter.x, lastEnter.y);
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
                    thirst+=30;
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
                    moveTo(lastEnter.x, lastEnter.y);
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
    
    public Color getColor(){
        return color;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }
}
