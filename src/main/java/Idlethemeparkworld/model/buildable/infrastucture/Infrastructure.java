package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;

public abstract class Infrastructure extends Building {
    protected double littering;

    public Infrastructure(GameManager gm) {
        super(gm);
        this.littering = 0;
    }
    
    public double checkLittering(){
        return littering;
    }
    
    public void setLittering(double littering) {
        this.littering = littering;
    }
    
    public void litter(double amount){  //This should only be in the Visitor class
        littering += amount;
    }
    
    public void clean(){    //This should only be in the Janitor class.
        littering = 0;
    }
    
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        return res;
    }
}
