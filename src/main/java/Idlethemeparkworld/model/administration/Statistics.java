package Idlethemeparkworld.model.administration;

import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.LockedTile;
import java.util.ArrayList;

public class Statistics implements Updatable {
    private static int HISTORY_LENGTH = 10;
    
    private GameManager gm;
    
    private final ArrayList<String> times;
    private final ArrayList<Double> rating;
    private final ArrayList<Double> happiness;
    
    public Statistics(GameManager gm){
        this.gm = gm;
        this.times = new ArrayList<>();
        this.rating = new ArrayList<>();
        this.happiness = new ArrayList<>();
    }
    
    private  ArrayList<Pair<String,Double>> combineLists(ArrayList<Double> list){
        ArrayList<Pair<String,Double>> res = new ArrayList<>();
        for (int i = 0; i < rating.size(); i++) {
            res.add(new Pair(times.get(i), list.get(i)));
        }
        return res;
    }
    
    public ArrayList<Pair<String,Double>> getRatingHistory(){
        return combineLists(rating);
    }
    
    public ArrayList<Pair<String,Double>> getHappinessHistory(){
        return combineLists(happiness);
    }
    
    public ArrayList<Pair<String,Double>> getBuildType(){
        ArrayList<Pair<String,Double>> res = new ArrayList<>();
        ArrayList<Building> buildings = gm.getPark().getBuildings();
        int attraction = 0;
        int foodstall = 0;
        int infrastructure = 0;
        for (int i = 0; i < buildings.size(); i++) {
            if(buildings.get(i) instanceof Attraction){
                attraction++;
            } else if(buildings.get(i) instanceof FoodStall){
                foodstall++;
            } else if(!(buildings.get(i) instanceof LockedTile)){
                infrastructure++;
            }
        }
        res.add(new Pair("Attraction", new Double(attraction)));
        res.add(new Pair("Foodstall", new Double(foodstall)));
        res.add(new Pair("Infrastructure", new Double(infrastructure)));
        return res;
    }
    
    private <T> void insertHistory(ArrayList<T> list, T value){
        list.add(value);
        if(list.size() >= HISTORY_LENGTH) {
            list.remove(0);
        }
    }
    
    private void updateHistories(){
        insertHistory(times, gm.getTime().toStringShort());
        insertHistory(rating, gm.getPark().getRating());
        insertHistory(happiness, gm.getAgentManager().getVisitorHappinessRating());
    }
    
    @Override
    public void update(long tickCount){
        if(tickCount%Time.convMinuteToTick(10) == 0){
            updateHistories();
        }
    }
}
