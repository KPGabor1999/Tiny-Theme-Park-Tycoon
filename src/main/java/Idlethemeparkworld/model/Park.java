package Idlethemeparkworld.model;

import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import java.lang.reflect.Constructor;
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
        initializePark(10, 10);
    }
    
    public Park(int size){
        initializePark(size, size);
    }
    
    public Park(int rows, int columns){
        initializePark(rows, columns);
    }

    public Tile[][] getTiles() {
        return tiles;
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
        if(checkEmptyArea(x,y,type.getWidth(),type.getLength())){
            ArrayList<Tile> neighbours = getNeighbours(x,y,type.getLength(),type.getWidth());
            neighbours.removeIf(n -> !(n.getBuilding() instanceof Pavement || n.getBuilding() instanceof Entrance));
            return neighbours.size() > 0;
        } else {
            return false;
        }
    }
    
    private boolean checkEmptyArea(int x, int y, int width, int height){
        boolean isEmpty = true;
        for (int i = y; i < y+height; i++) {
            for (int j = x; j < x+width; j++) {
                isEmpty = isEmpty && tiles[i][j].isEmpty();
            }
        }
        return isEmpty;
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
    
    private void addNeighbourRange(ArrayList<Tile> list, int startX, int startY, int range, boolean isHorizontal){
        for (int i = 0; i < range; i++) {
            if(isHorizontal){
                addNeighbour(list, startX+i, startY);
            } else {
                addNeighbour(list, startX, startY+i);
            }
        }
    }
    
    private ArrayList<Tile> getNeighbours(int x, int y, int height, int width){
        ArrayList<Tile> neighbours = new ArrayList<>();
        addNeighbourRange(neighbours, x, y-1, width, true); //top neighbours
        addNeighbourRange(neighbours, x-1, y, height, false); //left neighbours
        addNeighbourRange(neighbours, x, y+height, width, true); //bottom neighbours
        addNeighbourRange(neighbours, x+width, y, height, false); //right neighbours
        return neighbours;
    }
    
    private void setAreaToBuilding(int x, int y, int height, int width, Building building){
        for (int i = y; i < y+height; i++) {
            for (int j = x; j < x+width; j++) {
                tiles[i][j].setBuilding(building);
            }
        }
    }
    
    public void build(BuildType type, int x, int y, boolean force){
        if(canBuild(type,x,y) || force){
            Building newBuilding = null;
            try{
                Class buildingClass = BuildType.GetClass(type);
                Class[] paramType = {int.class, int.class};
                Constructor cons = buildingClass.getConstructor(paramType);
                newBuilding = (Building) cons.newInstance(x,y);
            } catch (Exception e){
                System.err.println(type.toString());
                e.printStackTrace();
            }
            buildings.add(newBuilding);
            setAreaToBuilding(x,y,type.getLength(),type.getWidth(),newBuilding);
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
    
    public void setEntranceFee(int fee){
        entranceFee = fee;
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
    
    public void update(long tickCount){
        
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
