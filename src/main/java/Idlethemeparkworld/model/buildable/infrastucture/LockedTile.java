/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;

/**
 *
 * @author KrazyXL
 */
public class LockedTile extends Infrastructure{
    private final int unlockCost;
    
    public LockedTile(int x, int y){
        this.xLocation = x;
        this.yLocation = y;
        this.buildingType = BuildType.LOCKEDTILE;
        this.unlockCost = BuildType.LOCKEDTILE.getBuildCost();
    }

    public int getUnlockCost() {
        return unlockCost;
    }
    

    @Override
    public void level2Upgrade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void level3Upgrade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
