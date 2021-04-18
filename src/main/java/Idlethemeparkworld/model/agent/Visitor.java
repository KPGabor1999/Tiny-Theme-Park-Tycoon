package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.AgentManager;
import Idlethemeparkworld.model.BuildType;
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
import java.util.LinkedList;

public class Visitor extends Agent {

    protected static final int AGENT_HUNGER_WARNING_THRESHOLD = 45;
    protected static final int AGENT_THIRST_WARNING_THRESHOLD = 45;
    protected static final int AGENT_TOILET_WARNING_THRESHOLD = 45;
    protected static final int AGENT_ENERGY_WARNING_THRESHOLD = 45;

    protected static final int AGENT_STATUS_MAXIMUM = 100;

    private int cash;
    private int cashSpent;

    int patience;
    int energy;
    int happiness;
    int hunger;
    int thirst;
    int toilet;

    ArrayList<AgentThought> thoughts;
    LinkedList<AgentAction> actionQueue;

    BuildType[] visitHistory;
    private Position lastEnter;
    private FoodItem item;
    
    private final int skinID;
    
    private long tickCount;

    public Visitor(String name, int startingHappiness, Park park, AgentManager am) {
        super(name, park, am);
        this.skinID = rand.nextInt(10)+1;
        this.cash = rand.nextInt(1000) + 500;
        this.cashSpent = 0;
        this.currentAction = new AgentAction(AgentActionType.ENTERPARK, null);

        this.patience = Time.convMinuteToTick(rand.nextInt(7)+6);
        this.energy = 100;
        this.happiness = startingHappiness;
        this.hunger = rand.nextInt(25)+75;
        this.thirst = rand.nextInt(25)+75;
        this.toilet = rand.nextInt(25)+75;
        this.lastEnter = null;
        this.item = null;
        this.statusMaxTimer = 0;
        setState(AgentState.ENTERINGPARK);

        this.thoughts = new ArrayList<>();
        this.actionQueue = new LinkedList<>();
    }

    @Override
    public void update(long tickCount) {
        super.update(tickCount);
        statusTimer++;
        if (tickCount % 24 == 0) {
            this.tickCount = tickCount;
            checkFloating();
            if (state != AgentState.FLOATING) {
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

    @Override
    protected void checkFloating() {
        if (park.getTile(x, y).isEmpty() || park.getTile(x, y).getBuilding().getStatus() == BuildingStatus.FLOATING) {
            if (state != AgentState.FLOATING) {
                resetAction();
                setState(AgentState.FLOATING);
            }
        } else {
            if (state == AgentState.FLOATING) {
                if (park.getTile(x, y).getBuilding().getInfo() != BuildType.PAVEMENT) {
                    moveTo(lastEnter.x, lastEnter.y);
                    this.resetAction();
                }
                setState(AgentState.IDLE);
            }
        }
    }

    private void updateCurrentAction() {
        if (currentAction == null) {
            if (!actionQueue.isEmpty()) {
                if(this.lastEnter != null){
                    resetAction();
                } else {
                    state = AgentState.IDLE;
                }
                currentAction = actionQueue.poll();
            }
        }
    }

    private void updateThought(long tickCount) {
        for (int i = 0; i < thoughts.size(); i++) {
            if (tickCount - thoughts.get(i).timeCreated > Time.convMinuteToTick(5)) {
                thoughts.remove(i);
            }
        }
    }

    private void thoughtToHappiness(AgentThoughts thoughtType) {
        switch (thoughtType) {
            case CANTAFFORD:
            case BADVALUE:
            case LOST:
            case TOOMUCHLITTER:
            case CROWDED:
                happiness-=5;
                break;
            case LONGQUEUE:
            case HUNGRY:
            case THIRSTY:
            case TIRED:
                happiness--;
                break;
            case NOTHUNGRY:
            case NOTTHIRSTY:
            case FEELINGGREAT:
                happiness++;
                break;
            case WOW:
            case GOODVALUE:
            case CLEAN:
                happiness+=5;
                break;
            default:
                break;
        }
    }

    private void addAction(AgentAction action) {
        if (!actionQueue.contains(action)) {
            actionQueue.add(action);
        }
    }

    private void thoughtToAction(AgentThoughts thoughtType) {
        switch (thoughtType) {
            case NOMONEY:
                actionQueue.clear();
                addAction(new AgentAction(AgentActionType.LEAVEPARK, null));
                break;
            case WANTTHRILL:
                addAction(new AgentAction(AgentActionType.RIDE, null));
                break;
            case GOHOME:
                actionQueue.clear();
                addAction(new AgentAction(AgentActionType.LEAVEPARK, null));
                break;
            case TIRED:
                addAction(new AgentAction(AgentActionType.SIT, null));
                break;
            case HUNGRY:
                addAction(new AgentAction(AgentActionType.EAT, null));
                break;
            case THIRSTY:
                if(hunger<toilet){
                    addAction(new AgentAction(AgentActionType.TOILET, null));
                } else {
                    addAction(new AgentAction(AgentActionType.EAT, null));
                }
                break;
            case TOILET:
                addAction(new AgentAction(AgentActionType.TOILET, null));
                break;
            case CROWDED:
                addAction(new AgentAction(AgentActionType.WANDER, null));
                break;
            default:
                break;
        }
    }
    
    private void insertThought(AgentThoughts thoughtType, Building subject) {
        insertThought(thoughtType, subject, tickCount);
    }

    private void insertThought(AgentThoughts thoughtType, Building subject, long tickCount) {
        AgentThought thought = new AgentThought(thoughtType, subject, tickCount);
        if (!thoughts.contains(thought)) {
            thoughts.add(thought);
            thoughtToHappiness(thoughtType);
            thoughtToAction(thoughtType);
        }
    }

    private boolean conditionToThought(int condition, int lowerThreshold, long tickCount, AgentThoughts positive, AgentThoughts negative, double leaveHappinessMultiplier) {
        if (condition < lowerThreshold) {
            if (condition <= 0) {
                happiness *= leaveHappinessMultiplier;
                insertThought(AgentThoughts.GOHOME, null, tickCount);
                return true;
            } else {
                insertThought(negative, null, tickCount);
                return true;
            }
        } else if (95 < condition) {
            insertThought(positive, null, tickCount);
        }
        return false;
    }

    private void generateThoughts(long tickCount) {
        if (cash < 100) {
            if (cash <= 0) {
                insertThought(AgentThoughts.NOMONEY, null, tickCount);
            } else {
                insertThought(AgentThoughts.LOWMONEY, null, tickCount);
            }
        }
        
        boolean hungerUrgent = conditionToThought(hunger, AGENT_HUNGER_WARNING_THRESHOLD, tickCount, AgentThoughts.NOTHUNGRY, AgentThoughts.HUNGRY, 0.6);
        boolean thirstUrgent = conditionToThought(thirst, AGENT_THIRST_WARNING_THRESHOLD, tickCount, AgentThoughts.NOTTHIRSTY, AgentThoughts.THIRSTY, 0.75);
        boolean energyUrgent = conditionToThought(energy, AGENT_ENERGY_WARNING_THRESHOLD, tickCount, AgentThoughts.FEELINGGREAT, AgentThoughts.TIRED, 0.9);
        boolean anyUrgent = hungerUrgent || thirstUrgent || energyUrgent;
        
        if (toilet < AGENT_TOILET_WARNING_THRESHOLD) {
            insertThought(AgentThoughts.TOILET, null, tickCount);
        }
        if (happiness < 75 || !anyUrgent) {
            insertThought(AgentThoughts.WANTTHRILL, null, tickCount);
        }
    }

    private void updateState() {
        switch (state) {
            case IDLE:
                energy += 0.05;
                break;
            case ENTERINGPARK:
            case LEAVINGPARK:
            case WANDERING:
            case WALKING:
                energy -= 0.03;
                hunger -= 0.01;
                thirst -= 0.01;
                break;
            case QUEUING:
                if (statusTimer > patience) {
                    happiness -= 0.5;
                }
                break;
            case ONRIDE:
                energy -= 0.02;
                break;
            case SITTING:
                energy += 0.1;
                hunger -= 0.02;
                break;
            case BUYING:
                break;
            case FLOATING:
                if (statusTimer > Time.convMinuteToTick(5)) {
                    remove();
                }
            default:
                break;
        }
        hunger -= 0.02;
        thirst -= 0.01;
        toilet -= 0.01;
    }

    private void performAction(long tickCount) {
        if (currentAction != null) {
            switch (currentAction.getAction()) {
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

    private void normalizeStatuses() {
        happiness = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, happiness));
        energy = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, energy));
        hunger = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, hunger));
        thirst = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, thirst));
        toilet = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, toilet));
    }

    private void leaveParkCycle() {
        switch (state) {
            case IDLE:
                setState(AgentState.LEAVINGPARK);
                break;
            case LEAVINGPARK:
                moveToRandomNeighbourPavement();
                if (currentBuilding instanceof Entrance) {
                    am.removeAgent(this);
                }
                break;
            default:
                break;
        }
    }

    private void enterCycle() {
        Entrance entrance = (Entrance) currentBuilding;
        entrance.enterPark(this);
        resetAction();
    }

    private void attractionCycle() {
        Attraction attr;
        switch (state) {
            case IDLE:
                setState(AgentState.WANDERING); break;
            case WANDERING:
                wandering(Attraction.class); break;
            case QUEUING:
                attr = ((Attraction) currentBuilding);
                if (statusTimer > this.patience) {
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

    private void toiletCycle() {
        Toilet tlt;
        switch (state) {
            case IDLE:
                setState(AgentState.WANDERING); break;
            case WANDERING:
                wandering(Toilet.class); break;
            case QUEUING:
                tlt = ((Toilet) currentBuilding);
                if (statusTimer > this.patience) {
                    insertThought(AgentThoughts.LONGQUEUE, currentBuilding);
                    tlt.leaveQueue(this);
                    moveTo(lastEnter.x, lastEnter.y);
                    this.happiness -= 5;
                    setState(AgentState.IDLE);
                } else {
                    if (tlt.isFirstInQueue(this)) {
                        if (tlt.canService()) {
                            tlt.enter(this);
                            setState(AgentState.SHITTING);
                            statusMaxTimer = Time.convMinuteToTick(rand.nextInt(5) + 2);
                        }
                    }
                }
                break;
            case SHITTING:
                tlt = ((Toilet) currentBuilding);
                if (statusTimer > statusMaxTimer) {
                    checkLittering();
                    if(tlt.getCleanliness() < 1){
                        if(rand.nextInt(10) < 3){
                            insertThought(AgentThoughts.CLEAN, null);
                        }
                    } else if(tlt.getCleanliness() > 10){
                        insertThought(AgentThoughts.TOOMUCHLITTER, null);
                    }
                    toilet = 100;
                    thirst += rand.nextInt(40)+50;
                    tlt.decreaseHygiene(rand.nextDouble() / 2);
                    tlt.exit();
                    moveTo(lastEnter.x, lastEnter.y);
                    resetAction();
                }
                break;
            default:
                break;
        }
    }

    private void sitCycle() {
        switch (state) {
            case IDLE:
                setState(AgentState.WANDERING);
                break;
            case WANDERING:
                if (statusTimer > this.patience) {
                    this.happiness -= 3;
                    resetAction();
                }
                if (rand.nextBoolean()) {
                    setState(AgentState.SITTING);
                } else {
                    moveToRandomNeighbourPavement();
                }
                break;
            case SITTING:
                if (energy >= 100) {
                    resetAction();
                }
                break;
            default:
                break;
        }
    }

    private void eatCycle() {
        FoodStall stall;
        switch (state) {
            case IDLE:
                setState(AgentState.WANDERING); break;
            case WANDERING:
                wandering(FoodStall.class); break;
            case QUEUING:
                stall = ((FoodStall) currentBuilding);
                if (statusTimer > patience) {
                    stall.leaveQueue(this);
                    moveTo(lastEnter.x, lastEnter.y);
                    this.happiness -= 5;
                    resetAction();
                } else {
                    if (stall.isFirstInQueue(this)) {
                        setState(AgentState.BUYING);
                    }
                }
                break;
            case BUYING:
                stall = ((FoodStall) currentBuilding);
                if (stall.canService()) {
                    item = stall.buyFood(this);
                    if(item.empty){
                        insertThought(AgentThoughts.CANTAFFORD, null);
                        moveTo(lastEnter.x, lastEnter.y);
                        resetAction();
                    } else {
                        if(rand.nextBoolean()){
                            moveTo(lastEnter.x, lastEnter.y);
                        }
                        setState(AgentState.EATING);
                    }
                }
                break;
            case EATING:
                if(currentBuilding instanceof Pavement){
                    moveToRandomNeighbourPavement();
                }
                if (statusTimer > Time.convMinuteToTick(item.consumeTime)) {
                    hunger += item.hunger;
                    thirst += item.thirst;
                    if(currentBuilding instanceof FoodStall){
                        moveTo(lastEnter.x, lastEnter.y);
                    }
                    resetAction();
                    currentAction = new AgentAction(AgentActionType.LITTER, null);
                }
                break;
            default:
                break;
        }
    }

    private void litterCycle() {
        switch (state) {
            case IDLE:
                setState(AgentState.WANDERING); break;
            case WANDERING:
                wandering(TrashCan.class); break;
            default:
                break;
        }
    }
    
    private void wandering(Class buildingClass){
        if (statusTimer > patience) {
            happiness -= 5;
            if(this.currentAction.getAction() == AgentActionType.LITTER){
                ((Infrastructure) currentBuilding).litter(item.consumeTime * 0.07);
            }
            item = null;
            resetAction();
        } else {
            ArrayList<Building> bs = park.getNonPavementNeighbours(x, y);
            bs.removeIf(b -> !(buildingClass.isInstance(b)));
            if (bs.size() > 0) {
                boolean foundBuilding = false;
                for (int i = 0; i < bs.size() && !foundBuilding; i++) {
                    switch(this.currentAction.getAction()){
                        case LITTER:
                            TrashCan tc = ((TrashCan) bs.get(i));
                            if (!tc.isFull()) {
                                checkLittering();
                                tc.use(item.consumeTime * 0.05);
                                item = null;
                                resetAction();
                                foundBuilding = true;
                            }
                            break;
                        case EAT:
                            if (rand.nextDouble() < ((double)statusTimer)/patience) {
                                lastEnter = new Position(x, y);
                                moveTo(bs.get(i).getX(), bs.get(i).getY());
                                ((FoodStall) currentBuilding).joinQueue(this);
                                setState(AgentState.QUEUING);
                                foundBuilding = true;
                            }
                            break;
                        case TOILET:
                            lastEnter = new Position(x, y);
                            moveTo(bs.get(0).getX(), bs.get(0).getY());
                            ((Toilet) currentBuilding).joinQueue(this);
                            setState(AgentState.QUEUING);
                            foundBuilding = true;
                            break;
                        case RIDE:
                            Attraction attraction = (Attraction) bs.get(i);
                            if (rand.nextDouble() < ((double)statusTimer)/patience && attraction.getQueueLength() <= attraction.getCapacity() * 1.5 && canPay(attraction.getEntryFee())) {
                                lastEnter = new Position(x, y);
                                moveTo(bs.get(i).getX(), bs.get(i).getY());
                                ((Attraction) currentBuilding).joinQueue(this);
                                setState(AgentState.QUEUING);
                                foundBuilding = true;
                            } else if(canPay(attraction.getEntryFee())){
                                insertThought(AgentThoughts.CANTAFFORD, null);
                            }
                            break;

                        default:
                            break;
                    }
                    
                }
                if (state != AgentState.QUEUING && state != AgentState.IDLE) {
                    moveToRandomNeighbourPavement();
                }
            } else {
                moveToRandomNeighbourPavement();
            }
        }
    }

    private void moveToRandomNeighbourPavement() {
        ArrayList<Building> paves = park.getPavementNeighbours(x, y);
        if (paves.size() > 0) {
            int nextIndex = rand.nextInt(paves.size());
            moveTo(paves.get(nextIndex).getX(), paves.get(nextIndex).getY());
            checkLittering();
        }
    }
    
    private void checkLittering(){
        if(((Infrastructure)currentBuilding).getLittering() < 1){
            if(rand.nextInt(10) < 3){
                insertThought(AgentThoughts.CLEAN, null);
            }
        } else if(((Infrastructure)currentBuilding).getLittering() > 10){
            insertThought(AgentThoughts.TOOMUCHLITTER, null);
        }
    }

    public boolean canPay(int amount) {
        return amount <= cash;
    }

    public void pay(int amount) {
        cash -= amount;
        cashSpent += amount;
    }

    public void setOnRide() {
        setState(AgentState.ONRIDE);
    }

    public void sendRideEvent(int rideEvent) {
        happiness += rideEvent;
        if(happiness > rand.nextInt(100)){
            insertThought(AgentThoughts.WOW, null);
        }
        moveTo(lastEnter.x, lastEnter.y);
        this.resetAction();
    }

    public int getCashSpent() {
        return cashSpent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", cash=").append(cash);
        sb.append(", cashSpent=").append(cashSpent);
        sb.append(", thoughts=").append(thoughts);
        sb.append(", actionQueue=").append(actionQueue);
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

    public int getHunger() {
        return hunger;
    }

    public int getThirst() {
        return thirst;
    }

    public int getToilet() {
        return toilet;
    }

    public boolean shouldRender() {
        switch (state) {
            case IDLE:
            case WALKING:
            case WANDERING:
            case QUEUING:
            case SITTING:
            case FLOATING:
            case ENTERINGPARK:
            case LEAVINGPARK:
                return true;
            case ONRIDE:
            case BUYING:
            case EATING:
            case SHITTING:
            default:
                return false;
        }
    }
    
    public int getSkinID() {
        return skinID;
    }
}
