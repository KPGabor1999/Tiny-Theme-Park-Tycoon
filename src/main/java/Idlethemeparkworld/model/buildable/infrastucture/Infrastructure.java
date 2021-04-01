package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;

public abstract class Infrastructure extends Building {
    protected int rubbish;

    public Infrastructure(GameManager gm) {
        super(gm);
    }
    
    public int checkLittering(){
        return rubbish;
    }
    
    public void litter(int amount){
        rubbish += amount;
    }
    
    public void clean(){
        rubbish = 0;
    }
    
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        return res;
    }
}
