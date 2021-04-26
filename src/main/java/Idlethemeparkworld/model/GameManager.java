package Idlethemeparkworld.model;

import Idlethemeparkworld.model.administration.Finance;
import Idlethemeparkworld.model.administration.Statistics;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.view.Board;
import java.util.ArrayList;

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

    private static final double[] GAME_SPEEDS = {0.5, 1, 2};
    private int gameSpeed;

    private int entranceFee;

    private final Park park;
    private final Time time;
    private final Finance finance;
    private final AgentManager am;
    private final Statistics stats;
    private Board board;
    private BGM bgm;

    public GameManager() {
        this.updateCycleRunning = false;
        this.currentDeltaTime = 0;
        this.tickCount = 0;
        this.gamePaused = false;
        this.gameSpeed = 1;

        this.gameFroze = false;
        this.park = new Park(10, 10, this);
        this.time = new Time();
        this.finance = new Finance(1000000);       //Normálisan 50K
        this.am = new AgentManager(park, this);
        this.stats = new Statistics(this);
        this.board = null;
        this.bgm = new BGM("Chessington Entrance - Theme Park Music.wav");
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
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

    public Statistics getStats() {
        return stats;
    }

    public AgentManager getAgentManager() {
        return am;
    }

    public boolean gameOver() {
        return gameOver;
    }
    
    public boolean gameWon() {
        return gameWon && !sandboxMode;
    }
     
    public boolean gameFroze(){
        return gameFroze;
    }
    
    public int getEntranceFee() {
        return entranceFee;
    }

    public void setEntranceFee(int number) {
        this.entranceFee = number;
    }

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

    private void initAllComponents() {
        park.initializePark(10, 10, this);
        time.reset();
        finance.init();
        am.reset();
    }

    public void startUpdateCycle() {
        updateCycleRunning = true;
        currentDeltaTime = 0;
        lastTime = System.nanoTime();
        updateCycle();
    }
    
    public int getScore(){
        return time.getTotalMinutes();
    }
    
    public void endGame() {
        pause();
        gameFroze = true;
        gameOver = true;
    }
    
    public void enableSandbox(){
        sandboxMode = true;
    }
    
    public void unFreeze(){
        gameFroze = false;
        unpause();
    }
    
    public void winGame() {
        pause();
        gameFroze = true;
        gameWon = true;
    }

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
                if((building instanceof Attraction || building instanceof FoodStall) && building.getCurrentLevel() == building.getMaxLevel()){
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
        }
        if(finance.getFunds()<=0){
            endGame();
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
    
    private void pause() {
        lastTime = System.nanoTime();
        gamePaused = true;
    }
    
    private void unpause() {
        lastTime = System.nanoTime();
        gamePaused = false;
    }
    
    public void togglePause() {
        lastTime = System.nanoTime();
        gamePaused = !gamePaused;
    }

    public boolean isPaused() {
        return gamePaused;
    }

    public long getTickCount() {
        return tickCount;
    }
}
