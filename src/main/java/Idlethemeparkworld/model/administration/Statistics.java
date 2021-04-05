package Idlethemeparkworld.model.administration;

import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.LockedTile;
import java.util.ArrayList;

public class Statistics implements Updatable {
    private static int HISTORY_LENGTH = 10;
    
    private GameManager gm;
    
    public Statistics(GameManager gm){
        this.gm = gm;
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
    
    @Override
    public void update(long tickCount){
        
    }
}
