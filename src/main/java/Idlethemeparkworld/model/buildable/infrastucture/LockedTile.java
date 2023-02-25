package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.misc.Assets;
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
        this.sound = Assets.Sounds.NATURE;
    }

    public int getUnlockCost() {
        return unlockCost;
    }
    
    /**
     * Ez nem tesz hozzá a parban lévő emberek számához.
     * @return 
     */
    @Override
    public int getRecommendedMax() {
        return 0;
    }
}
