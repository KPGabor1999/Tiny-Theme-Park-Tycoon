package Idlethemeparkworld.model;

import Idlethemeparkworld.model.administration.Finance;
import Idlethemeparkworld.model.administration.Statistics;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.view.Board;
import java.util.ArrayList;

/**
 * Game manager class that manages all game components and holds the tick based update system.
 * 
 * Currently running on 24 ticks/second by default.
 */
public class GameManager {

    private static final int GAME_TICKRATE = 24;
    private static final int GAME_UPDATE_TIME_MS = 1000000000 / GAME_TICKRATE;
    private boolean updateCycleRunning;
    private double lastTime;
    private double currentDeltaTime;
    private long tickCount;
    private double updateCount;

    private boolean gamePaused;
    private boolean gameOver;
    private boolean gameWon;
    private boolean gameFroze;
    private boolean sandboxMode;

    private static final double[] GAME_SPEEDS = {0.5, 1, 2, 3};
    private int gameSpeed;

    private int entranceFee;

    private final Park park;
    private final Time time;
    private final Finance finance;
    private final AgentManager am;
    private final Statistics stats;
    private Board board;

    public GameManager() {
        this.updateCycleRunning = false;
        this.currentDeltaTime = 0;
        this.tickCount = 0;
        this.gamePaused = false;
        this.gameSpeed = 1;

        this.gameFroze = false;
        this.park = new Park(10, 10, this);
        this.time = new Time();
        Weather.getInstance().setTime(time);
        this.finance = new Finance(500000);
        this.am = new AgentManager(park, this);
        this.stats = new Statistics(this);
        this.board = null;
    }

    /**
     * Sets the board UI
     * @param board Board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * @return the current board UI 
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return the park that the class manages
     */
    public Park getPark() {
        return park;
    }

    /**
     * @return the time tracker
     */
    public Time getTime() {
        return time;
    }

    /**
     * @return the finance manager
     */
    public Finance getFinance() {
        return finance;
    }

    /**
     * @return the stats tracker
     */
    public Statistics getStats() {
        return stats;
    }

    /**
     * @return the agent manager
     */
    public AgentManager getAgentManager() {
        return am;
    }

    /**
     * @return whether the player has died
     */
    public boolean gameOver() {
        return gameOver;
    }
    
    /**
     * Check wether the player has won.
     * The win condition is having 1 from all building types and having 6 upgraded to max.
     * @return whether the player has won.
     */
    public boolean gameWon() {
        return gameWon && !sandboxMode;
    }
     
    /**
     * Check if the game should be frozen. This is used when the game is force paused for the first time
     * when the player wins.
     * @return wether the game is in the first freeze time
     */
    public boolean gameFroze(){
        return gameFroze;
    }
    
    /**
     * @return the entrance fee to the park
     */
    public int getEntranceFee() {
        return entranceFee;
    }

    /**
     * Sets the entrance fee
     * @param number Entrance fee
     */
    public void setEntranceFee(int number) {
        this.entranceFee = number;
    }

    /**
     * Resets all game components and starts a new game 
     */
    public void startNewGame() {
        unFreeze();
        sandboxMode = false;
        gameWon = false;
        gameOver = false;
        unpause();
        tickCount = 0;
        currentDeltaTime = 0;
        gameSpeed = 1;
        initAllComponents();
        //startUpdateCycle();
        gamePaused = false;
    }

    /**
     * Initializes and resets all components.
     * 
     * Note: All game component managers should have some sort of reset or init function.
     */
    private void initAllComponents() {
        park.initializePark(10, 10, this);
        time.reset();
        finance.init();
        am.reset();
    }

    /**
     * Starts the tick based update cycle
     */
    public void startUpdateCycle() {
        updateCycleRunning = true;
        currentDeltaTime = 0;
        lastTime = System.nanoTime();
        updateCycle();
    }
    
    /**
     * Calculate a new score that is based on the number of minutes that the player survived
     * @return the score of the player
     */
    public int getScore(){
        return time.getTotalMinutes();
    }
    
    /**
     * Ends the game when the player has died
     */
    public void endGame() {
        pause();
        gameFroze = true;
        gameOver = true;
    }
    
    /**
     * Enables sandbox mode, where the player can play endlessly as long as they don't go bankrupt
     */
    public void enableSandbox(){
        sandboxMode = true;
    }
    
    /**
     * Stops the forced freeze during the win
     */
    public void unFreeze(){
        gameFroze = false;
        unpause();
    }
    
    /**
     * Wins the game for the player.
     * 
     * The win condition is having 1 from all building types and having 6 upgraded to max.
     */
    private void winGame() {
        pause();
        gameFroze = true;
        gameWon = true;
    }

    /**
     * Checks if the player qualifies for winning and if yes, triggers the win sequence.
     * 
     * The win condition is having 1 from all building types and having 6 upgraded to max.
     */
    public void checkWin() {
        if(!gameWon && park != null) {
            ArrayList<Building> buildings = park.getBuildings();
            boolean win = true;
            for(BuildType type : BuildType.values()) {
                boolean found = type == BuildType.LOCKEDTILE;
                for (int i = 0; i < buildings.size() && !found; i++) {
                    found = buildings.get(i).getInfo() == type;
                }
                win = win && found;
            }
            int maxLevelCount = 0;
            for(Building building : buildings) {
                if((building instanceof Attraction || building instanceof FoodStall) && building.getCurrentLevel() >= building.getMaxLevel()){
                    maxLevelCount++;
                }
            }
            win = win && (maxLevelCount >= 2);
            win = win && park.getRating() > 6.5;
            if(win) {
                winGame();
            }
        }
    }
    
    /**
     * This is the outer layer of the update cycle. This will determine how much time has 
     * elapsed and then calls the appropriate number of updates
     */
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
    
    /**
     * The main update function. This will call the update function of all components.
     * 
     * This call should cascade down to every single little component.
     * 
     * Currently the tick rate should be set to consistently 24 ticks/seconds.
     * @param tickStep The current tick ID
     */
    private void update(int tickStep) {
        updateCount += tickStep * getGameSpeed();
        double actualUpdateCount = Math.floor(updateCount);
        updateCount -= actualUpdateCount;
        for (int i = 0; i < actualUpdateCount; i++) {
            tickCount++;
            park.update(tickCount);
            am.update(tickCount);
            time.update(tickCount);
            stats.update(tickCount);
            Weather.getInstance().update(tickCount);
        }
        if(finance.getFunds()<=0){
            endGame();
        }
    }

    /**
     * Resets the game speed to 1x.
     */
    public void resetGameSpeed() {
        gameSpeed = 1;
    }

    /**
     * Increases the game speed to a maximum of 2x.
     */
    public void increaseGameSpeed() {
        if (gameSpeed < GAME_SPEEDS.length - 1) {
            gameSpeed++;
        }
    }

    /**
     * Decrease the game speed to a minimum of 0.5x.
     */
    public void decreaseGameSpeed() {
        if (0 < gameSpeed) {
            gameSpeed--;
        }
    }

    /**
     * @return The current game simulation speed.
     */
    public double getGameSpeed() {
        return GAME_SPEEDS[gameSpeed];
    }
    
    /**
     * Pauses the game, setting the speed to 0x.
     */
    private void pause() {
        lastTime = System.nanoTime();
        gamePaused = true;
    }
    
    /**
     * Resumes with the old speed.
     */
    private void unpause() {
        lastTime = System.nanoTime();
        gamePaused = false;
    }
    
    /**
     * Toggles game pause.
     */
    public void togglePause() {
        lastTime = System.nanoTime();
        gamePaused = !gamePaused;
    }

    /**
     * @return wether the game is stopped or not.
     */
    public boolean isPaused() {
        return gamePaused;
    }

    /**
     * Get the current tick count, which is incremented with each update (24 times per second).
     * @return current tick count
     */
    public long getTickCount() {
        return tickCount;
    }
}
