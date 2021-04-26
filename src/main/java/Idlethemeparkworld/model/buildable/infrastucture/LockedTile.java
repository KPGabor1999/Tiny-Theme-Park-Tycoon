package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;

public class LockedTile extends Infrastructure {

    private final int unlockCost;

    public LockedTile(int x, int y, GameManager gm) {
        super(gm);
        this.maxLevel = 0;
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.LOCKEDTILE;
        this.unlockCost = BuildType.LOCKEDTILE.getBuildCost();
        this.soundFileName = "nature.wav";
    }

    @Override
    public int getRecommendedMax() {
        return 0;
    }

    public int getUnlockCost() {
        return unlockCost;
    }
}
