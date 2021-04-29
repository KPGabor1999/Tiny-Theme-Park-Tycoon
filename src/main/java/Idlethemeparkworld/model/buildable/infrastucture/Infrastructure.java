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
     * Szemetel�s az adott mez�n.
     * @param amount 
     */
    public void litter(double amount) {
        littering += amount;
        if(littering > 5) {
            Janitor.alertOfCriticalBuilding(this);
        }
    }
    
    /**
     * R�f�r-e az �p�letre a takar�t�s?
     * @return 
     */
    public boolean shouldClean() {
        return littering > 3;
    }

    /**
     * �p�let fels�pr�se.
     * @param amount 
     */
    public void sweep(double amount) {
        littering -= amount;
        littering = Math.max(littering, 0);
    }

    /**
     * �p�let adatainak lek�r�se (ezeket �rjuk ki a p�rbasz�dablakba).
     * @return 
     */
    @Override
    public ArrayList<Pair<String, String>> getAllData() {
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        return res;
    }
}
