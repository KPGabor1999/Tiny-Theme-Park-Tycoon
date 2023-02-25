package Idlethemeparkworld.model.administration;

import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.LockedTile;
import java.util.ArrayList;

public class Statistics implements Updatable {

    private static final int HISTORY_LENGTH = 10;

    private final GameManager gm;

    private final ArrayList<String> times;
    private final ArrayList<Double> rating;
    private final ArrayList<Double> happiness;
    private final ArrayList<Integer> visitorCount;

    public Statistics(GameManager gm) {
        this.gm = gm;
        this.times = new ArrayList<>();
        this.rating = new ArrayList<>();
        this.happiness = new ArrayList<>();
        this.visitorCount = new ArrayList<>();
    }
    
    /**
     * Eddigi játékstatisztikák törlése.
     */
    public void reset(){
        times.clear();
        rating.clear();
        happiness.clear();
        visitorCount.clear();
    }

    /**
     * Valamelyik adat statisztikáinak lekérése.
     * @param <T>
     * @param list
     * @return 
     */
    private <T> ArrayList<Pair<String, Double>> combineLists(ArrayList<T> list) {
        ArrayList<Pair<String, Double>> res = new ArrayList<>();
        for (int i = 0; i < rating.size(); i++) {
            res.add(new Pair(times.get(i), list.get(i)));
        }
        return res;
    }

    /**
     * Park értékelési előzmények lekérése.
     * @return 
     */
    public ArrayList<Pair<String, Double>> getRatingHistory() {
        return combineLists(rating);
    }

    /**
     * Park boldogságszint előzményeinek lekérése.
     * @return 
     */
    public ArrayList<Pair<String, Double>> getHappinessHistory() {
        return combineLists(happiness);
    }
    
    /**
     * Látogatók számának előzményeinek lekérése.
     * @return 
     */
    public ArrayList<Pair<String, Double>> getVisitorCountHistory() {
        return combineLists(visitorCount);
    }
    
    /**
     * Látogatók jelenlegi akcióinak lekérése (az egyik tortadiagramhoz).
     * @return 
     */
    public ArrayList<Pair<String, Double>> getVisitorAction() {
        ArrayList<Pair<String, Double>> res = new ArrayList<>();
        ArrayList<Visitor> visitors = gm.getAgentManager().getVisitors();
        int eat = 0;
        int sit = 0;
        int ride = 0;
        int toilet = 0;
        int litter = 0;
        int enterpark = 0;
        int leavepark = 0;
        for (int i = 0; i < visitors.size(); i++) {
            if(visitors.get(i).getAction() != null){
                switch (visitors.get(i).getAction().getAction()) {
                    case EAT:
                        eat++; break;
                    case SIT:
                        sit++; break;
                    case RIDE:
                        ride++; break;
                    case TOILET:
                        toilet++; break;
                    case LITTER:
                        litter++; break;
                    case ENTERPARK:
                        enterpark++; break;
                    case LEAVEPARK:
                        leavepark++; break;
                    default: break;
                }
            }
        }
        res.add(new Pair("Eat", new Double(eat)));
        res.add(new Pair("Sit", new Double(sit)));
        res.add(new Pair("Ride", new Double(ride)));
        res.add(new Pair("Toilet", new Double(toilet)));
        res.add(new Pair("Litter", new Double(litter)));
        res.add(new Pair("Enterpark", new Double(enterpark)));
        res.add(new Pair("Leavepark", new Double(leavepark)));
        return res;
    }

    /**
     * Látogatók jelenlegi állapotainak lekérése (szintén tortadiagrammhoz).
     * @return 
     */
    public ArrayList<Pair<String, Double>> getVisitorState() {
        ArrayList<Pair<String, Double>> res = new ArrayList<>();
        ArrayList<Visitor> visitors = gm.getAgentManager().getVisitors();
        int entering = 0;
        int idle = 0;
        int wandering = 0;
        int walking = 0;
        int queuing = 0;
        int onride = 0;
        int eating = 0;
        int sitting = 0;
        int shitting = 0;
        int leaving = 0;
        int floating = 0;
        for (int i = 0; i < visitors.size(); i++) {
            switch (visitors.get(i).getState()) {
                case IDLE:
                    idle++; break;
                case WANDERING:
                    wandering++; break;
                case WALKING:
                    walking++; break;
                case QUEUING:
                    queuing++; break;
                case ONRIDE:
                    onride++; break;
                case EATING:
                    eating++; break;
                case SITTING:
                    sitting++; break;
                case SHITTING:
                    shitting++; break;
                case FLOATING:
                    floating++; break;
                case ENTERINGPARK:
                    entering++; break;
                case LEAVINGPARK:
                    leaving++; break;
                default: break;
            }
        }
        res.add(new Pair("Entering", new Double(entering)));
        res.add(new Pair("Idle", new Double(idle)));
        res.add(new Pair("Wandering", new Double(wandering)));
        res.add(new Pair("Walking", new Double(walking)));
        res.add(new Pair("Queuing", new Double(queuing)));
        res.add(new Pair("Riding", new Double(onride)));
        res.add(new Pair("Eating", new Double(eating)));
        res.add(new Pair("Sitting", new Double(sitting)));
        res.add(new Pair("Shitting", new Double(shitting)));
        res.add(new Pair("Leaving", new Double(leaving)));
        res.add(new Pair("Floating", new Double(floating)));
        return res;
    }

    /**
     * Jelenlegi épülettípusok lekérése (tortadiagramhoz).
     * @return 
     */
    public ArrayList<Pair<String, Double>> getBuildType() {
        ArrayList<Pair<String, Double>> res = new ArrayList<>();
        ArrayList<Building> buildings = gm.getPark().getBuildings();
        int attraction = 0;
        int foodstall = 0;
        int infrastructure = 0;
        for (int i = 0; i < buildings.size(); i++) {
            int size = buildings.get(i).getInfo().getLength() * buildings.get(i).getInfo().getWidth();
            if (buildings.get(i) instanceof Attraction) {
                attraction+=size;
            } else if (buildings.get(i) instanceof FoodStall) {
                foodstall+=size;
            } else if (!(buildings.get(i) instanceof LockedTile)) {
                infrastructure+=size;
            }
        }
        res.add(new Pair("Attraction", new Double(attraction)));
        res.add(new Pair("Foodstall", new Double(foodstall)));
        res.add(new Pair("Infrastructure", new Double(infrastructure)));
        return res;
    }

    /**
     * Előzménystatisztikai érték beszúrása a megfelelő listába.
     * @param <T>
     * @param list
     * @param value 
     */
    private <T> void insertHistory(ArrayList<T> list, T value) {
        list.add(value);
        if (list.size() >= HISTORY_LENGTH) {
            list.remove(0);
        }
    }

    /**
     * Előzménystatisztikák frissítése minden attribútumra.
     */
    private void updateHistories() {
        insertHistory(times, gm.getTime().toStringShort());
        insertHistory(rating, gm.getPark().getRating());
        insertHistory(happiness, gm.getAgentManager().getVisitorHappinessRating());
        insertHistory(visitorCount, gm.getAgentManager().getVisitorCount());
    }

    /**
     * Előzménystatisztikák frissítése az udatecycle-ben.
     * @param tickCount 
     */
    @Override
    public void update(long tickCount) {
        if (tickCount % Time.convMinuteToTick(10) == 0) {
            updateHistories();
        }
    }
}
