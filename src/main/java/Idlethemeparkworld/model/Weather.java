package Idlethemeparkworld.model;

import java.awt.Color;
import java.util.Random;

public class Weather implements Updatable {

    public enum WeatherType {
        SUNNY(240, 240, 0, 60),
        RAINING(20, 60, 150, 80),
        CLOUDY(150, 184, 184, 60),
        SNOWING(255, 255, 255, 110),
        NIGHT(0, 0, 65, 145),
        CLEAR(0, 0, 0, 0);

        private final Color color;

        private WeatherType(int r, int g, int b, int a) {
            color = new Color(r, g, b, a);
        }

        public Color getColor() {
            return color;
        }
        
        public static WeatherType[] getDayTypes(){
            return new WeatherType[] {SUNNY, RAINING, CLOUDY, SNOWING, CLEAR};
        }
        
        public static boolean isNight(WeatherType type){
            return type == NIGHT;
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private static Weather instance = null;
    private WeatherType oldWeather;
    private WeatherType weather;
    private int weatherTimer;
    private int transitionDuration = Time.convMinuteToTick(15);
    private int transitionTimer;

    private Time time;
    private final Random rand;

    private Weather() {
        this.rand = new Random();
        this.weather = null;
        setNewRandomWeather();
        transitionTimer = 0;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void reset() {
        setNewRandomWeather();
    }

    private int lerpValue(int from, int to, double ratio) {
        return (int) Math.round(from + (to - from) * ratio);
    }

    private Color lerpColor(Color from, Color to, double ratio) {
        return new Color(lerpValue(from.getRed(), to.getRed(), ratio),
                lerpValue(from.getGreen(), to.getGreen(), ratio),
                lerpValue(from.getBlue(), to.getBlue(), ratio),
                lerpValue(from.getAlpha(), to.getAlpha(), ratio));
    }

    private void setNewWeather(WeatherType nextWeather, int duration, int transitionTime){
        oldWeather = weather;
        weather = nextWeather;
        weatherTimer = duration;
        transitionDuration = Time.convMinuteToTick(transitionTime);
        transitionTimer = transitionDuration;
        News.getInstance().addNews("The weather is changing to " + weather.toString());
    }
    
    private void setNewRandomWeather() {
        WeatherType nextWeather;
        if(rand.nextDouble() > 0.6){
            nextWeather = WeatherType.getDayTypes()[rand.nextInt(WeatherType.getDayTypes().length)];
        } else {
            nextWeather = WeatherType.CLEAR;
        }
        setNewWeather(nextWeather, rand.nextInt(60*4)+60*1, 15);
    }

    public Color getSkyColor() {
        if (transitionTimer > 0) {
            return lerpColor(oldWeather.getColor(), weather.getColor(), 1 - (double) transitionTimer / transitionDuration);
        } else {
            return weather.getColor();
        }
    }

    public WeatherType getWeather() {
        return weather;
    }

    @Override
    public void update(long tickCount) {
        if (tickCount % 24 == 0) {
            if(!WeatherType.isNight(weather)){
                if(time.getHours() >= 21){
                    setNewWeather(WeatherType.NIGHT, 7*60, 60);
                }
            }
            weatherTimer--;
            if (weatherTimer <= 0) {
                setNewRandomWeather();
            }
        }
        if (transitionTimer > 0) {
            transitionTimer--;
        }
    }

    public static Weather getInstance() {
        if (instance == null) {
            instance = new Weather();
        }

        return instance;
    }
}
