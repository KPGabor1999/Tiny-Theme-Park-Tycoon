package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.agent.Janitor;

public abstract class Infrastructure extends Building {

    protected double littering;

    public Infrastructure(GameManager gm) {
        super(gm);
        this.littering = 0;
    }

    public double getLittering() {
        return littering;
    }

    public void setLittering(double littering) {
        this.littering = littering;
    }

    /**
     * Szemetelés az adott mezõn.
     * @param amount 
     */
    public void litter(double amount) {
        littering += amount;
        if(littering > 5) {
            Janitor.alertOfCriticalBuilding(this);
        }
    }
    
    /**
     * Ráfér-e az épületre a takarítás?
     * @return 
     */
    public boolean shouldClean() {
        return littering > 3;
    }

    /**
     * Épület felsöprése.
     * @param amount 
     */
    public void sweep(double amount) {
        littering -= amount;
        littering = Math.max(littering, 0);
    }

    /**
     * Épület adatainak lekérése (ezeket írjuk ki a párbaszédablakba).
     * @return 
     */
    @Override
    public ArrayList<Pair<String, String>> getAllData() {
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        return res;
    }
}
