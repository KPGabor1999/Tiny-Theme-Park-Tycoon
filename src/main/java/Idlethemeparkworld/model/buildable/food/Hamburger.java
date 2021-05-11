package Idlethemeparkworld.model.buildable.food;

import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.misc.utils.Range;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Weather;

public class Hamburger extends FoodStall {

    public Hamburger(int x, int y, GameManager gm) {
        super(gm);
        this.x = x;
        this.y = y;
        this.value = BuildType.BURGERJOINT.getBuildCost();
        this.buildingType = BuildType.BURGERJOINT;
        this.serviceTime = Time.convRealLifeSecondToTick(1);
        this.foodPrice = 15;
        this.foodQuality.setRange(50, 90);
        this.drinkQuality = new Range(50, 60);
        this.upkeepCost = 15;
        this.upgradeCost = (int) (this.value * 0.75);
    }

    /**
     * Hamburgerezõ fejlesztése.
     */
    @Override
    public void innerUpgrade() {
        switch (currentLevel) {
            case 1:
                this.serviceTime += Time.convRealLifeSecondToTick(0.1);
                this.foodQuality.add(5, 5);
                this.upkeepCost += 5;
                this.upgradeCost *= 2;
                break;
            case 2:
                this.serviceTime += Time.convRealLifeSecondToTick(0.1);
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
            case SUNNY: return new Pair(1.0,1.1);
            case SNOWING: return new Pair(1.0,0.8);
            case CLOUDY: return new Pair(1.2,0.8);
            default: return new Pair(1.0,1.0);
        }
    }
}
