package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import java.util.ArrayList;

public class Pavement extends Infrastructure {

    public Pavement(int x, int y, GameManager gm) {
        super(gm);
        this.upkeepCost = 10;
        this.maxLevel = 0;
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.PAVEMENT;
        this.value = BuildType.PAVEMENT.getBuildCost();
    }

    /**
     * Egy j�rdaelem +1 ember jelenl�t�t enged�lyezi a parkban.
     * @return 
     */
    @Override
    public int getRecommendedMax() {
        return (status == BuildingStatus.OPEN || status == BuildingStatus.OPEN) ? 1 : 0;
    }

    /**
     * J�rda adatainak lek�r�se (ezt �rjuk ki a p�rbesz�dablakba).
     * @return 
     */
    @Override
    public ArrayList<Pair<String, String>> getAllData() {
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Littering: ", String.format("%.2f", littering)));
        return res;
    }
}
