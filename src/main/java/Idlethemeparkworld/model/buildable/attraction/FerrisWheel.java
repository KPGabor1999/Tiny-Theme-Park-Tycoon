package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.misc.Assets;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Weather;

public class FerrisWheel extends Attraction {

    public FerrisWheel(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.value = BuildType.FERRISWHEEL.getBuildCost();
        this.buildingType = BuildType.FERRISWHEEL;
        this.upkeepCost = 9;
        this.fun = 25;
        this.capacity = 25;
        this.runtime = 7;
        this.entryFee = 20;
        this.baseEntryFee = 20;        
        this.upgradeCost = (int)(this.value * 0.75);
        this.sound = Assets.Sounds.COG_SPINNING;
    }

    /**
     * Óriáskerék fejlesztése.
     */
    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.fun *= 1.1;
                this.runtime -= 1;                
                this.upgradeCost *= 2;
                break;
            case 2:
                this.fun *= 1.1;
                this.runtime -= 1;    
                this.upgradeCost *= 2;                
                break;
            case 3:
                this.fun *= 1.1;
                this.runtime -= 1;                    
                break;
            default:
                break;
        }
    }
    
    @Override
    protected double getWeatherMultiplier(){
        switch(Weather.getInstance().getWeather()){
            case SUNNY: return 0.9;
            case RAINING: return 0.8;
            case SNOWING: return 0.9;
            case NIGHT: return 1.3;
            case CLOUDY: return 0.9;
            case CLEAR: return 1.3;
            default: return 1;
        }
    }
}
