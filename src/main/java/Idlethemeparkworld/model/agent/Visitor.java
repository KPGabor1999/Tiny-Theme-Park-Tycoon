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
import java.util.ArrayList;
import java.util.LinkedList;

public class Visitor extends Agent {
    
    private static int ID = 0;

    protected static final int AGENT_HUNGER_WARNING_THRESHOLD = 45;
    protected static final int AGENT_THIRST_WARNING_THRESHOLD = 45;
    protected static final int AGENT_TOILET_WARNING_THRESHOLD = 45;
    protected static final int AGENT_ENERGY_WARNING_THRESHOLD = 45;

    protected static final int AGENT_STATUS_MAXIMUM = 100;

    private int id;
    
    private int cash;
    private int cashSpent;

    int patience;
    double energy;
    double happiness;
    double hunger;
    double thirst;
    double toilet;

    ArrayList<AgentThought> thoughts;
    LinkedList<AgentAction> actionQueue;

    BuildType[] visitHistory;
    private Position lastEnter;
    private FoodItem item;
    
    private final int skinID;
    
    private long tickCount;
    
    public static void resetIDCounter(){
        ID = 0;
    }

    public Visitor(String name, int startingHappiness, Park park, AgentManager am) {
        super(name, park, am);
        this.id = ID;
        ID++;
        
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
                addHappiness(-5);
                break;
            case LOST:
            case TOOMUCHLITTER:
            case CROWDED:
                addHappiness(-10);
                break;
            case LONGQUEUE:
            case HUNGRY:
            case THIRSTY:
            case TIRED:
                addHappiness(-7);
                break;
            case NOTHUNGRY:
            case NOTTHIRSTY:
            case FEELINGGREAT:
                addHappiness(1);
                break;
            case WOW:
                addHappiness(8);
                break;
            case GOODVALUE:
            case CLEAN:
                addHappiness(5);
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

    private boolean conditionToThought(double condition, int lowerThreshold, long tickCount, AgentThoughts positive, AgentThoughts negative, double leaveHappinessMultiplier, boolean homeOnNegative) {
        if (condition < lowerThreshold) {
            if (condition <= 0) {
                happiness *= leaveHappinessMultiplier;
                insertThought(AgentThoughts.GOHOME, null, tickCount);
                return true;
            } else {
                if(homeOnNegative){
                    if(rand.nextInt(10) > 4){
                        insertThought(negative, null, tickCount);
                    } else {
                        happiness *= leaveHappinessMultiplier;
                        insertThought(AgentThoughts.GOHOME, null, tickCount);
                    }
                } else {
                    insertThought(negative, null, tickCount);
                }
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
        
        boolean hungerUrgent = conditionToThought(hunger, AGENT_HUNGER_WARNING_THRESHOLD, tickCount, AgentThoughts.NOTHUNGRY, AgentThoughts.HUNGRY, 0.6, false);
        boolean thirstUrgent = conditionToThought(thirst, AGENT_THIRST_WARNING_THRESHOLD, tickCount, AgentThoughts.NOTTHIRSTY, AgentThoughts.THIRSTY, 0.75, false);
        boolean energyUrgent = conditionToThought(energy, AGENT_ENERGY_WARNING_THRESHOLD, tickCount, AgentThoughts.FEELINGGREAT, AgentThoughts.TIRED, 0.95, true);
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
                energy += 0.5;
                break;
            case ENTERINGPARK:
            case LEAVINGPARK:
            case WANDERING:
            case WALKING:
                energy -= 0.45;
                hunger -= 0.35;
                thirst -= 0.35;
                break;
            case ONRIDE:
                energy -= 0.35;
                hunger -= 0.25;
                thirst -= 0.25;
                break;
            case SITTING:
                energy += 2;
                hunger -= 0.2;
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
        energy -= 0.25;
        hunger -= 0.45;
        thirst -= 0.35;
        toilet -= 0.5;
    }

    private void performAction(long tickCount) {
        if (currentAction != null) {
            switch (currentAction.getAction()) {
                case ENTERPARK: enterCycle(); break;
                case EAT: eatCycle(); break;
                case SIT: sitCycle(); break;
                case WANDER: moveToRandomNeighbourPavement(); break;
                case TOILET: toiletCycle(); break;
                case LEAVEPARK: leaveParkCycle(); break;
                case RIDE: attractionCycle(); break;
                case LITTER: litterCycle(); break;
                case NONE: break;
                default: break;
            }
        }
    }

    private void normalizeStatuses() {
        energy = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, energy));
        hunger = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, hunger));
        thirst = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, thirst));
        toilet = Math.min(AGENT_STATUS_MAXIMUM, Math.max(0, toilet));
    }

    private void leaveParkCycle() {
        switch (state) {
            case IDLE:
                Building entrance = findType(Entrance.class);
                path = park.getPathfinding().getPath(new Position(x,y), entrance);
                setState(AgentState.LEAVINGPARK);
                break;
            case LEAVINGPARK:
                if (currentBuilding instanceof Entrance) {
                    am.removeAgent(this);
                } else {
                    moveOnPath();
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
                if(rand.nextInt(10)>3){
                    Building attraction = findType(Attraction.class);
                    if(attraction == null){
                        setState(AgentState.WANDERING);
                    } else {
                        path = park.getPathfinding().getPath(new Position(x,y), attraction);
                        setState(AgentState.WALKING);
                    }
                } else {
                    setState(AgentState.WANDERING);
                }
                break;
            case WALKING:
                if (currentBuilding instanceof Attraction) {
                    ((Attraction) currentBuilding).joinQueue(this);
                    setState(AgentState.QUEUING);
                } else {
                    moveOnPath();
                }
                break;
            case WANDERING:
                wandering(Attraction.class); break;
            case QUEUING:
                attr = ((Attraction) currentBuilding);
                if (statusTimer > this.patience) {
                    attr.leaveQueue(this);
                    moveTo(lastEnter.x, lastEnter.y);
                    addHappiness(-5);
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
                if(rand.nextInt(10)>3){
                    Building chosenToilet = findType(Toilet.class);
                    if(chosenToilet == null){
                        setState(AgentState.WANDERING);
                    } else {
                        path = park.getPathfinding().getPath(new Position(x,y), chosenToilet);
                        setState(AgentState.WALKING);
                    }
                } else {
                    setState(AgentState.WANDERING);
                }
                break;
            case WANDERING:
                wandering(Toilet.class); break;
            case WALKING:
                if (currentBuilding instanceof Toilet) {
                    ((Toilet) currentBuilding).joinQueue(this);
                    setState(AgentState.QUEUING);
                } else {
                    moveOnPath();
                }
                break;
            case QUEUING:
                tlt = ((Toilet) currentBuilding);
                if (statusTimer > this.patience) {
                    insertThought(AgentThoughts.LONGQUEUE, currentBuilding);
                    tlt.leaveQueue(this);
                    moveTo(lastEnter.x, lastEnter.y);
                    addHappiness(-10);
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
                    if(tlt.getCleanliness() > 85){
                        if(rand.nextInt(10) < 3){
                            insertThought(AgentThoughts.CLEAN, null);
                        }
                    } else if(tlt.getCleanliness() < 25){
                        insertThought(AgentThoughts.TOOMUCHLITTER, null);
                    }
                    toilet = 100;
                    thirst += rand.nextInt(40)+50;
                    tlt.decreaseHygiene(rand.nextDouble()*1.5);
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
                    addHappiness(-5);
                    resetAction();
                }
                if (rand.nextBoolean()) {
                    statusTimer = 0;
                    statusMaxTimer = Time.convMinuteToTick(rand.nextInt(6)+6);
                    setState(AgentState.SITTING);
                } else {
                    moveToRandomNeighbourPavement();
                }
                break;
            case SITTING:
                if(statusTimer > statusMaxTimer || energy >= 100){
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
                if(rand.nextInt(10)>3){
                    Building foodStall = findType(FoodStall.class);
                    if(foodStall == null){
                        setState(AgentState.WANDERING);
                    } else {
                        path = park.getPathfinding().getPath(new Position(x,y), foodStall);
                        setState(AgentState.WALKING);
                    }
                } else {
                    setState(AgentState.WANDERING);
                }
                break;
            case WANDERING:
                wandering(FoodStall.class); break;
            case WALKING:
                if (currentBuilding instanceof FoodStall) {
                    ((FoodStall) currentBuilding).joinQueue(this);
                    setState(AgentState.QUEUING);
                } else {
                    moveOnPath();
                }
                break;
            case QUEUING:
                stall = ((FoodStall) currentBuilding);
                if (statusTimer > patience) {
                    stall.leaveQueue(this);
                    moveTo(lastEnter.x, lastEnter.y);
                    addHappiness(-10);
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
                        if(rand.nextInt(10)>7){
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
                    if(rand.nextInt(200)<item.hunger+thirst){
                        insertThought(AgentThoughts.WOW, null);
                    }
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
                if(rand.nextInt(10)>2){
                    Building trashcan = findType(TrashCan.class);
                    if(trashcan == null){
                        setState(AgentState.WANDERING);
                    } else {
                        path = park.getPathfinding().getPath(new Position(x,y), trashcan);
                        setState(AgentState.WALKING);
                    }
                } else {
                    setState(AgentState.WANDERING);
                }
                break;
            case WALKING:
                if (currentBuilding instanceof TrashCan) {
                    ((TrashCan) currentBuilding).use(item.consumeTime * 0.05);
                    checkLittering();
                    item = null;
                    moveTo(lastEnter.x, lastEnter.y);
                    resetAction();
                } else {
                    moveOnPath();
                }
                break;
            case WANDERING:
                wandering(TrashCan.class); break;
            default:
                break;
        }
    }
    
    private void wandering(Class buildingClass){
        if (statusTimer > patience) {
            addHappiness(-10);
            if(this.currentAction.getAction() == AgentActionType.LITTER){
                ((Infrastructure) currentBuilding).litter(item.consumeTime * 0.07);
            }
            item = null;
            resetAction();
        } else {
            ArrayList<Building> bs = park.getNonPavementOrEntranceNeighbours(x, y);
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
                            if (rand.nextDouble() < (((double)statusTimer)/patience/2) + 0.5) {
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
    
    
    @Override
    protected void moveOnPath(){
        if(path.size() > 0){
            Position nextPos = path.remove(0);
            if(park.getTile(nextPos.x, nextPos.y).isEmpty()
                    || park.getTile(nextPos.x, nextPos.y).getBuilding().getStatus() == BuildingStatus.DECAYED){
                addHappiness(-5);
                resetAction();
            } else {
                if(path.isEmpty()) {
                    lastEnter = new Position(x, y);
                }
                moveTo(nextPos.x, nextPos.y);
            }
        }
    }
    
    private void checkLittering(){
        if(((Infrastructure)currentBuilding).getLittering() < 1){
            if(rand.nextInt(10) < 3){
                insertThought(AgentThoughts.CLEAN, null);
            }
        } else if(((Infrastructure)currentBuilding).getLittering() > 7){
            if(rand.nextInt(10) < 4){
                insertThought(AgentThoughts.TOOMUCHLITTER, null);
            }
        }
    }

    private Building findType(Class clazz){
        ArrayList<Building> buildings = new ArrayList<>();
        park.getBuildings().forEach((b) -> {
            if(clazz.isInstance(b) && (b.getStatus() == BuildingStatus.OPEN || b.getStatus() == BuildingStatus.RUNNING)){
                buildings.add(b);
            }
        });
        if(buildings.isEmpty()){
            return null;
        } else {
            return buildings.get(rand.nextInt(buildings.size()));
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
        addHappiness(rideEvent);
        if(happiness > rand.nextInt(100)){
            insertThought(AgentThoughts.WOW, null);
        }
        moveTo(lastEnter.x, lastEnter.y);
        this.resetAction();
    }

    public int getCashSpent() {
        return cashSpent;
    }
    
    public ArrayList<String> getAllData() {
        ArrayList<String> res = new ArrayList<>();
        res.add(state.toString());
        res.add("X:" + x + "/Y:" + y);
        res.add(Integer.toString(cash));
        res.add(Integer.toString(cashSpent));
        res.add(thoughts.toString());
        if(currentAction == null){
            res.add("None");
        } else {
            res.add(currentAction.getAction().toString());
        }
        res.add(String.format("%.1f",happiness));
        res.add(String.format("%.1f",hunger));
        res.add(String.format("%.1f",thirst));
        res.add(String.format("%.1f",toilet));
        res.add(String.format("%.1f",energy));
        return res;
    }

    public int getPatience() {
        return patience;
    }

    public double getEnergy() {
        return energy;
    }

    public double getHappiness() {
        return happiness;
    }
    
    public void addHappiness(int value) {
        happiness = Math.max(0, Math.min(happiness+value, 100));
    }

    public double getHunger() {
        return hunger;
    }

    public double getThirst() {
        return thirst;
    }

    public double getToilet() {
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
            case EATING:
            case BUYING:
                return true;
            case ONRIDE:
            case SHITTING:
            default:
                return false;
        }
    }
    
    public int getID(){
        return id;
    }
    
    public int getSkinID() {
        return skinID;
    }
    
    public AgentAction getAction(){
        return currentAction;
    }
}
