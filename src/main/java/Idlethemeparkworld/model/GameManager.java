package Idlethemeparkworld.model;

import Idlethemeparkworld.model.administration.Finance;
import Idlethemeparkworld.model.administration.Statistics;
import Idlethemeparkworld.view.Board;

public class GameManager {

    private static final int GAME_TICKRATE = 24;
    private static final int GAME_UPDATE_TIME_MS = 1000000000 / GAME_TICKRATE;
    private boolean updateCycleRunning;
    private double lastTime;
    private double currentDeltaTime;
    private long tickCount;
    private double updateCount;

    private boolean gamePaused;

    private static final double[] GAME_SPEEDS = {0.5, 1, 2};
    private int gameSpeed;

    private double dayNightCycle;

    private int entranceFee;

    private Park park;
    private Time time;
    private Finance finance;
    private AgentManager am;
    private Statistics stats;
    private Board board;

    public GameManager() {
        this.updateCycleRunning = false;
        this.currentDeltaTime = 0;
        this.tickCount = 0;
        this.gamePaused = false;
        this.gameSpeed = 1;
        this.dayNightCycle = 0;

        this.park = new Park(10, 11, this);
        this.time = new Time();
        this.finance = new Finance(100000);
        this.am = new AgentManager(park, this);
        this.stats = new Statistics(this);
        this.board = null;
    }

    public void setBoard(Board board){
        this.board = board;
    }
    
    public Board getBoard(){
        return board;
    }
    
    public Park getPark() {
        return park;
    }

    public Time getTime() {
        return time;
    }

    public Finance getFinance() {
        return finance;
    }
    
    public Statistics getStats(){
        return stats;
    }
    
    public AgentManager getAgentManager(){
        return am;
    }

    public int getEntranceFee() {
        return entranceFee;
    }

    public void setEntranceFee(int number) {
        this.entranceFee = number;
    }

    public void startNewGame() {
        gamePaused = true;
        tickCount = 0;
        initAllComponents();
        //startUpdateCycle();
        gamePaused = false;
    }

    private void initAllComponents() {
        park.initializePark(10, 15, this);
        time.reset();
        finance.init();
    }

    public void startUpdateCycle() {
        updateCycleRunning = true;
        currentDeltaTime = 0;
        lastTime = System.nanoTime();
        updateCycle();
    }

    private void updateCycle() {
        while (updateCycleRunning) {
            long now = System.nanoTime();
            if (!gamePaused) {
                currentDeltaTime += now - lastTime;
                lastTime = now;
                int tickStep = (int) Math.floor(currentDeltaTime / GAME_UPDATE_TIME_MS);
                if (tickStep >= 1) {
                    update(tickStep);
                    currentDeltaTime = 0;
                }
            }
            
        }
    }
    
    public void stopUpdateCycle() {
        updateCycleRunning = false;
    }

    private void update(int tickStep) {
        updateCount += tickStep * getGameSpeed();
        double actualUpdateCount = Math.floor(updateCount);
        updateCount -= actualUpdateCount;
        //System.out.println(actualUpdateCount);
        for (int i = 0; i < actualUpdateCount; i++) {
            tickCount++;
            park.update(tickCount);
            am.update(tickCount);
            time.update(tickCount);
            stats.update(tickCount);
        }
    }

    public void resetGameSpeed() {
        gameSpeed = 1;
    }

    public void increaseGameSpeed() {
        if (gameSpeed < GAME_SPEEDS.length - 1) {
            gameSpeed++;
        }
    } 

    public void decreaseGameSpeed() {
        if (0 < gameSpeed) {
            gameSpeed--;
        }
    }

    public double getGameSpeed() {
        return GAME_SPEEDS[gameSpeed];
    }

    public void togglePause() {
        lastTime = System.nanoTime();
        gamePaused = !gamePaused;
    }

    public boolean isPaused() {
        return gamePaused;
    }
    
    public long getTickCount(){
        return tickCount;
    }

    //Perhaps game saving/loading and autosaving feature? *wink wink*
    public void saveGame() {
    }

    public void saveGame(String name) {
    }

    public void loadGame() {
    }

    public void setupAutosave() {
    }

    private void autosave() {
    }
}
