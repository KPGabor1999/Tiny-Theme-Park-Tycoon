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
     * Egy járdaelem +1 ember jelenlétét engedélyezi a parkban.
     * @return 
     */
    @Override
    public int getRecommendedMax() {
        return (status == BuildingStatus.OPEN || status == BuildingStatus.OPEN) ? 1 : 0;
    }

    /**
     * Járda adatainak lekérése (ezt írjuk ki a párbeszédablakba).
     * @return 
     */
    @Override
    public ArrayList<Pair<String, String>> getAllData() {
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Littering: ", String.format("%.2f", littering)));
        return res;
    }
}
