package Idlethemeparkworld.model.buildable;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Updatable;
import Idlethemeparkworld.model.administration.Finance.FinanceType;

public abstract class Buildable implements Updatable {
   
    protected BuildType buildingType;
    protected int upkeepCost;
    protected int upkeepTimer;
    protected boolean underConstruction;
    protected GameManager gm;

    public Buildable(GameManager gm) {
        this.gm = gm;
    }

    public BuildType getInfo() {
        return buildingType;
    }

    public int getUpkeepCost() {
        return upkeepCost;
    }

    public boolean isUnderConstruction() {
        return underConstruction;
    }

    /**
     * Frissítéskor minden épület után levonódik a fenntartási költsége.
     * @param tickCount 
     */
    @Override
    public void update(long tickCount) {
        upkeepTimer++;
        if (upkeepTimer >= Time.convRealLifeSecondToTick(60)) {
            gm.getFinance().pay(upkeepCost, FinanceType.UPKEEP);
            upkeepTimer = 0;
        }
    }
}
