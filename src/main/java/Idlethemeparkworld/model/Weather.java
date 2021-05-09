package Idlethemeparkworld.model;

import java.awt.Color;
import java.util.Random;

public class Weather implements Updatable {

    public enum weatherType{
        SUNNY(240, 240, 0, 60),
        RAINING(95, 134, 173, 60),
        CLOUDY(84, 184, 184, 60),
        SNOWING(255, 255, 255, 60),
        CLEAR(0,0,0,0);
        
        private final Color color;
        
        private weatherType(int r, int g, int b, int a){
            color = new Color(r,g,b,a);
        }
        
        public Color getColor(){
            return color;
        }
    }
    
    private static Weather instance = null;
    private weatherType weather;
    private int weatherTimer;
    private final Random rand;
    
    private Weather(){
        this.rand = new Random();
        setNewWeather();
    }
    
    public void reset(){
        setNewWeather();
    }
    
    private void setNewWeather(){
        weather = weatherType.values()[rand.nextInt(weatherType.values().length)];
        weatherTimer = rand.nextInt(60*5)+60*2;
    }
    
    public Color getSkyColor(){
        return weather.getColor();
    }
    
    public weatherType getWeather(){
        return weather;
    }
    
    @Override
    public void update(long tickCount) {
        if(tickCount % 24 == 0){
            weatherTimer--;
            if(weatherTimer <= 0){
                setNewWeather();
            }
        }
    }
    
    public static Weather getInstance()
    {
        if (instance == null){
            instance = new Weather();
        }
  
        return instance;
    }
}
