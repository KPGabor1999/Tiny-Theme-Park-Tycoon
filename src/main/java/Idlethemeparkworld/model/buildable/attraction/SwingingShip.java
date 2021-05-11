package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.misc.Assets;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Weather;

public class SwingingShip extends Attraction {

    public SwingingShip(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.value = BuildType.SWINGINGSHIP.getBuildCost();
        this.buildingType = BuildType.SWINGINGSHIP;
        this.upkeepCost = 8;
        this.fun = 15;
        this.capacity = 10;
        this.runtime = 3;
        this.entryFee = 15;
        this.baseEntryFee = 20;
        this.upgradeCost = (int)(this.value * 0.75);
        this.sound = Assets.Sounds.YOU_ARE_A_PIRATE;
    }

    /**
     * Hajóhinta frissítése.
     */
    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.fun *= 1.4;
                this.capacity += 5;
                this.upgradeCost *= 2;
                break;
            case 2:
                this.fun *= 1.4;
                this.capacity += 5;
                break;
            default:
                break;
        }
    }
    
    @Override
    protected double getWeatherMultiplier(){
        switch(Weather.getInstance().getWeather()){
            case RAINING: return 0.7;
            case SNOWING: return 0.9;
            case NIGHT: return 0.8;
            case CLOUDY: return 1.2;
            case CLEAR: return 1.2;
            default: return 1;
        }
    }
}
