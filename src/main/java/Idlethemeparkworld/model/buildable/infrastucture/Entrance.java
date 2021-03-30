package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;

public class Entrance extends Infrastructure {
    
    public Entrance(int x, int y, GameManager gm) {
        super(gm);
        this.maxLevel = 0;
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.ENTRANCE;
    }
    
    @Override
    public void level2Upgrade(){}       //They're not meant to be upgradeable but they could be.
    
    @Override
    public void level3Upgrade(){}
}
