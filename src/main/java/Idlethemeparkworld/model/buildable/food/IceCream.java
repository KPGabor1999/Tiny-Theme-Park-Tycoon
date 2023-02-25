package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.misc.utils.Range;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Weather;

public class IceCream extends FoodStall {

    public IceCream(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.value = BuildType.ICECREAMPARLOR.getBuildCost();
        this.buildingType = BuildType.ICECREAMPARLOR;
        this.serviceTime = Time.convRealLifeSecondToTick(1);
        this.foodPrice = 5;
        this.foodQuality.setRange(30, 70);
        this.drinkQuality = new Range(75, 90);
        this.upkeepCost = 10;
        this.upgradeCost = (int) (this.value * 0.75);
    }

    /**
     * Fagyizó fejlesztése.
     */
    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.serviceTime += Time.convRealLifeSecondToTick(0.3);
                this.foodQuality.add(5, 5);
                this.drinkQuality.add(5, 5);
                this.upkeepCost += 5;
                this.upgradeCost *= 2;
                break;
            case 2:
                this.serviceTime += Time.convRealLifeSecondToTick(0.3);
                this.foodQuality.add(5, 5);
                this.drinkQuality.add(10, 5);
                this.upkeepCost += 5;
                break;
            default:
                break;
        }
    }
    
    @Override
    protected Pair<Double,Double> getWeatherMultiplier(){
        switch(Weather.getInstance().getWeather()){
            case SUNNY: return new Pair(1.6,1.7);
            case SNOWING: return new Pair(0.3,0.3);
            case CLOUDY: return new Pair(1.0,0.9);
            case RAINING: return new Pair(0.6,0.6);
            default: return new Pair(1.0,1.0);
        }
    }
}
