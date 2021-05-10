package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Weather;

public class HotDog extends FoodStall {

    public HotDog(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.value = BuildType.HOTDOGSTAND.getBuildCost();
        this.buildingType = BuildType.HOTDOGSTAND;
        this.serviceTime = Time.convRealLifeSecondToTick(1);
        this.foodPrice = 10;
        this.foodQuality.setRange(40, 75);
        this.upkeepCost = 13;
        this.upgradeCost = (int) (this.value * 0.75);
    }

    /**
     * Hotdogstand fejlesztése.
     */
    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.serviceTime += Time.convRealLifeSecondToTick(0.3);
                this.foodQuality.add(5, 5);
                this.upkeepCost += 5;
                this.upgradeCost *= 2;
                break;
            case 2:
                this.serviceTime += Time.convRealLifeSecondToTick(0.3);
                this.foodQuality.add(5, 5);
                this.upkeepCost += 5;
                break;
            default:
                break;
        }
    }
    
    @Override
    protected Pair<Double,Double> getWeatherMultiplier(){
        switch(Weather.getInstance().getWeather()){
            case SUNNY: return new Pair(1,1.1);
            case SNOWING: return new Pair(1,0.8);
            case CLOUDY: return new Pair(1.2,0.8);
            case RAINING: return new Pair(1.2,0.8);
            case NIGHT: return new Pair(1.2,0.8);
            case CLEAR: return new Pair(1.2,0.8);
            default: return new Pair(1,1);
        }
    }
}
