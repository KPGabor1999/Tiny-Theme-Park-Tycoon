package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;

public class LockedTile extends Infrastructure {
    private final int unlockCost;
    
    public LockedTile(int x, int y){
        this.maxLevel = 0;
        this.currentLevel = 1;
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.LOCKEDTILE;
        this.unlockCost = BuildType.LOCKEDTILE.getBuildCost();
    }

    public int getUnlockCost() {
        return unlockCost;
    }
    

    @Override
    public void level2Upgrade() {}

    @Override
    public void level3Upgrade() {}
    
}
