package Idlethemeparkworld.model;

public class GameManager {
    private static final int GAME_TICKRATE = 24;
    private static final int GAME_UPDATE_TIME_MS = 1000000000 / GAME_TICKRATE;   
    private boolean updateCycleRunning;
    private double lastTick;
    private double currentDeltaTime;
    private int tickCount;
    private double updateCount;
    
    private boolean gamePaused;
    
    private static final double[] GAME_SPEEDS = {0.5, 1, 2};
    private int gameSpeed;
    
    private double dayNightCycle;
    
    private Park park;
    private Time time;
    
    public GameManager(){
        this.updateCycleRunning = false;
        this.currentDeltaTime = 0;
        this.tickCount = 0;
        this.gamePaused = false;
        this.gameSpeed = 1;
        this.dayNightCycle = 0;
        
        this.park = new Park();
        this.time = new Time();
    }
    
    public Park getPark(){
        return park;
    }
    
    public Time getTime(){
        return time;
    }
    
    public void startNewGame(){
        gamePaused = true;
        tickCount = 0;
        initAllComponents();
        //startUpdateCycle();
        gamePaused = false;
    }
    
    private void initAllComponents(){
        park.initializePark(10);
        time.reset();
    }
    
    public void startUpdateCycle(){
        updateCycleRunning = true;
        lastTick = System.nanoTime();
        updateCycle();
    }
    
    private void updateCycle(){
        while(updateCycleRunning){
            if(!gamePaused){
                long now = System.nanoTime();
                currentDeltaTime = now - lastTick;
                int tickStep = (int)Math.floor(currentDeltaTime/GAME_UPDATE_TIME_MS);
                if(tickStep >= 1){
                    update(tickStep);
                    lastTick += tickStep * GAME_UPDATE_TIME_MS;
                }
            }
        }
    }
    
    public void stopUpdateCycle(){
        updateCycleRunning = false;
    }
    
    // Might need to separate ingame ticks and reallife ticks depending on what we plan to do
    private void update(int tickStep){
        tickCount += tickStep;

        updateCount += tickStep * getGameSpeed();
        double actualUpdateCount = Math.floor(updateCount);
        updateCount -= actualUpdateCount;
        //System.out.println(tickStep+" - "+actualUpdateCount+" - "+updateCount);
        //System.out.println(tickCount);
        for (int i = 0; i < actualUpdateCount; i++)
        {
            park.update();
            time.update();
        }
    }
    
    public void resetGameSpeed(){
        gameSpeed=2;
    }
    
    public void increaseGameSpeed(){
        if(gameSpeed < GAME_SPEEDS.length){
            gameSpeed++;
        }
    }
    
    public void decreaseGameSpeed(){
        if(0 < gameSpeed){
            gameSpeed--;
        }
    }
    
    public double getGameSpeed(){
        return GAME_SPEEDS[gameSpeed];
    }
    
    public void togglePause(){
        gamePaused = !gamePaused;
    }
    
    public boolean isPaused(){
        return gamePaused;
    }
    
    //Perhaps game saving/loading and autosaving feature? *wink wink*
    public void saveGame(){}
    public void saveGame(String name){}
    public void loadGame(){}
    public void setupAutosave(){}
    private void autosave(){}
}
