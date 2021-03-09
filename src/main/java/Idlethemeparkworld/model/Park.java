package Idlethemeparkworld.model;

import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import java.util.ArrayList;

public class Park {
    private int funds;
    private Tile[][] tiles;
    private ArrayList<Building> buildings;
    
    public Park(){
        tiles = new Tile[10][10];
        initializePark();
    }
    
    public Park(int size){
        tiles = new Tile[size][size];
        initializePark();
    }
    
    private void initializePark(){
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
            if(buildings.get(i).getName().equals(type)){
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
    
    public void build(BuildType type, int x, int y, boolean force){
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
    }
}
