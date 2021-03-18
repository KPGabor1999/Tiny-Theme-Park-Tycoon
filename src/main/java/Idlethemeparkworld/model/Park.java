package Idlethemeparkworld.model;

import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Carousel;
import Idlethemeparkworld.model.buildable.attraction.FerrisWheel;
import Idlethemeparkworld.model.buildable.attraction.HauntedMansion;
import Idlethemeparkworld.model.buildable.attraction.RollerCoaster;
import Idlethemeparkworld.model.buildable.attraction.SwingingShip;
import Idlethemeparkworld.model.buildable.food.Hamburger;
import Idlethemeparkworld.model.buildable.food.HotDog;
import Idlethemeparkworld.model.buildable.food.IceCream;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.LockedTile;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;
import java.util.ArrayList;

public class Park implements Updatable {
    private static final int HISTORY_SIZE = 14;
    
    private int rating;
    private int entranceFee;
    private int parkValue;
    private int activeParkValue;
    private int[] ratingHistory;
    private int[] valueHistory;
    
    private boolean isOpen;
    
    private int maxGuests;
    
    private Tile[][] tiles;
    private ArrayList<Building> buildings;
    
    public Park(){
        initializePark(10);
    }
    
    public Park(int size){
        initializePark(size);
    }
    
    public Park(int rows, int columns){
        initializePark(rows, columns);
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    
    public void initializePark(int size){
        rating = 0;
        parkValue = 0;
        entranceFee = 100;
        activeParkValue = 0;
        resetHistories();
        
        tiles = new Tile[size][size];
        //1.Make sure all tiles are empty
        this.buildings = new ArrayList<>();
        for(int row=0; row<tiles.length; row++){
            for(int column=0; column<tiles[0].length; column++){
                tiles[row][column] = new Tile(column, row);
            }
        }
        //2.Spawn in the gate tile
        build(BuildType.ENTRANCE, 0, 0, true);
        //3.Spawn in 1 from each for debugging purpose
        //spawnAllBuildings();
    }
    
    public void initializePark(int rows, int columns){
        rating = 0;
        parkValue = 0;
        entranceFee = 100;
        activeParkValue = 0;
        resetHistories();
        
        tiles = new Tile[rows][columns];
        //1.Make sure all tiles are empty
        this.buildings = new ArrayList<>();
        for(int row=0; row<tiles.length; row++){
            for(int column=0; column<tiles[0].length; column++){
                tiles[row][column] = new Tile(column, row);
            }
        }
        //2.Spawn in the gate tile
        build(BuildType.ENTRANCE, 0, 0, true);
        for(int row = 0; row <tiles.length; row++){
            for(int column = tiles[0].length-5; column<tiles[0].length; column++){
                build(BuildType.LOCKEDTILE, column, row, true);
            }
        }
        //3.Spawn in 1 from each for debugging purpose
        //spawnAllBuildings();
    }
    
    public void spawnAllBuildings(){
        build(BuildType.CAROUSEL, 7, 0, true);
        build(BuildType.FERRISWHEEL, 7, 1, true);
        build(BuildType.HAUNTEDMANSION, 7, 2, true);
        build(BuildType.ROLLERCOASTER, 7, 3, true);
        build(BuildType.SWINGINGSHIP, 7, 4, true);
        
        build(BuildType.BURGERJOINT, 8, 0, true);
        build(BuildType.HOTDOGSTAND, 8, 1, true);
        build(BuildType.ICECREAMPARLOR, 8, 2, true);
        
        build(BuildType.PAVEMENT, 9, 0, true);
        build(BuildType.TOILET, 9, 1, true);
        build(BuildType.TRASHCAN, 9, 2, true);
    }
    
    public int getWidth(){
        return tiles[0].length;
    }
    
    public int getHeight(){
        return tiles.length;
    }
    
    public Tile getTile(int x, int y){
        return tiles[y][x];
    }
    
    public ArrayList<Building> getBuildings(){
        return buildings;
    }
    
    public Building findBuilding(String type){
        for(int i=0; i<buildings.size(); i++){
            if(buildings.get(i).getInfo().getName().equals(type)){
                return buildings.get(i);
            }
        }
        return null;
    }
    
    public boolean canBuild(BuildType type, int x, int y){
        boolean legal = true;
        if(tiles[y][x].isEmpty()){
            ArrayList<Tile> neighbours = getNeighbours(x,y);
            neighbours.removeIf(n -> !(n.getBuilding() instanceof Pavement || n.getBuilding() instanceof Entrance));
            return neighbours.size() > 0;
        } else {
            return false;
        }
    }
    
    private boolean checkLegalCoordinate(int x, int y){
        return (0 <= x && x < getWidth())
                && (0 <= y && y < getHeight());
    }
    
    private void addNeighbour(ArrayList<Tile> list, int x, int y){
        if(checkLegalCoordinate(x,y)){
            list.add(tiles[y][x]);
        }
    }
    
    private ArrayList<Tile> getNeighbours(int x, int y){
        ArrayList<Tile> neighbours = new ArrayList<>();
        addNeighbour(neighbours, x-1, y);
        addNeighbour(neighbours, x+1, y);
        addNeighbour(neighbours, x, y-1);
        addNeighbour(neighbours, x, y+1);
        return neighbours;
    }
    
    /*public void build(BuildType type, int x, int y, boolean force){
        if(canBuild(type,x,y) || force){
            Building newBuilding = null;
            try{
               newBuilding = (Building) BuildType.GetClass(type).newInstance();
            } catch (Exception e){
            }
            buildings.add(newBuilding);
            //We are assuming all buildings are 1x1 for the time being
            tiles[y][x].setBuilding(true, newBuilding);
        }
    }*/
    
    public void build(BuildType type, int x, int y, boolean force){
        if(canBuild(type,x,y) || force){
            Building newBuilding;
            switch(type){
                //Attrakciók:
                case LOCKEDTILE:
                    newBuilding = new LockedTile(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                case CAROUSEL:
                    newBuilding = new Carousel(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                case FERRISWHEEL:
                    newBuilding = new FerrisWheel(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                case HAUNTEDMANSION:
                    newBuilding = new HauntedMansion(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                case ROLLERCOASTER:
                    newBuilding = new RollerCoaster(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                case SWINGINGSHIP:
                    newBuilding = new SwingingShip(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                //Büfék:
                case BURGERJOINT:
                    newBuilding = new Hamburger(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                case HOTDOGSTAND:
                    newBuilding = new HotDog(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                case ICECREAMPARLOR:
                    newBuilding = new IceCream(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                //Infrastruktúra:
                case ENTRANCE:
                    newBuilding = new Entrance(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                case PAVEMENT:
                    newBuilding = new Pavement(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                case TOILET:
                    newBuilding = new Toilet(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
                case TRASHCAN:
                    newBuilding = new TrashCan(x, y);
                    buildings.add(newBuilding);
                    tiles[y][x].setBuilding(true, newBuilding);
                    break;
            }
        }
    }
    
    public void demolish(int x, int y){
        (this.tiles[y][x]).unsetBuilding();
        
        int demolitionIndex = 0;    
        for(int i=0; i<buildings.size(); i++){
            if(buildings.get(i).getxLocation() == x &&
               buildings.get(i).getyLocation() == y){
                demolitionIndex = i;
                break;
            }
        }
        
        buildings.remove(demolitionIndex);
    }
    
    public int getEntranceFee(){
        return entranceFee;
    }
    
    public boolean isOpen(){
        return isOpen;
    }
    
    public void openPark(){
        isOpen = true;
    }
    
    public void closePark(){
        isOpen = false;
    }
    
    //Used for debugging
    public void setRating(int value){
        rating = value;
    }
    
    public int getRating(){
        return rating;
    }
    
    public int getValue(){
        return parkValue;
    }
    
    public int getActiveValue(){
        return activeParkValue;
    }
    
    public int getMaxGuest(){
        return maxGuests;
    }
    
    public void update(){
        
    }
    
    private void calculateParkRating(){
        /*
        Start out with a base number
        1. Guest happiness and other status calculations
        2. Attraction ratings
        3. Substract points for environment(littering and toilet higiene)
        */
    }
    
    private void calculateValue(){
        /*
        Go through each attraction and evaluate them
        Go through each visitor and evaluate them
        */ 
    }
    
    private void calculateActiveValue(){
        /*
        Go through each ACTIVE attraction and evaluate how much they are actually worth
        */
    }
    
    private int calculateMaxGuests(){
        return 0;
    }
    
    private void resetHistories(){
        ratingHistory = new int[HISTORY_SIZE];
        valueHistory = new int[HISTORY_SIZE];
    }
    
    private void updateHistories(){
        
    }
}
