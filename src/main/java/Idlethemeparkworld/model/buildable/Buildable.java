package Idlethemeparkworld.model.buildable;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Updatable;

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

    @Override
    public void update(long tickCount) {
        upkeepTimer++;
        if (upkeepTimer >= Time.convMinuteToTick(60)) {
            gm.getFinance().pay(upkeepCost);
            upkeepTimer = 0;
        }
    }
}
