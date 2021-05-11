package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.misc.Assets;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Weather;

public class RollerCoaster extends Attraction {

    public RollerCoaster(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.value = BuildType.ROLLERCOASTER.getBuildCost();
        this.buildingType = BuildType.ROLLERCOASTER;
        this.upkeepCost = 10;
        this.fun = 30;
        this.capacity = 15;
        this.runtime = 2;
        this.entryFee = 25;
        this.baseEntryFee = 25;        
        this.upgradeCost = (int)(this.value * 0.75);
        this.sound = Assets.Sounds.PEOPLE_SCREAMS;
    }

    /**
     * Hullámvasút frissítése.
     */
    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.fun *= 1.35;
                this.capacity += 4;
                this.runtime -= 0.5;
                this.upgradeCost *= 2;
                break;
            case 2:
                this.fun *= 1.35;
                this.capacity += 4;
                this.runtime -= 0.5;
                break;
            default:
                break;
        }
    }
    
    @Override
    protected double getWeatherMultiplier(){
        switch(Weather.getInstance().getWeather()){
            case SUNNY: return 1.3;
            case RAINING: return 0.7;
            case SNOWING: return 0.6;
            case NIGHT: return 0.9;
            case CLOUDY: return 1.3;
            case CLEAR: return 1.1;
            default: return 1;
        }
    }
}
