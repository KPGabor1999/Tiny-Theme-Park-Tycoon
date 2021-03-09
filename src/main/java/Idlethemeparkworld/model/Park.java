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
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;
import java.util.ArrayList;

public class Park implements Updatable {
    private int funds;
    private Tile[][] tiles;
    private ArrayList<Building> buildings;
    
    public Park(){
        initializePark(10);
    }
    
    public Park(int size){
        initializePark(size);
    }
    
    public void initializePark(int size){
        tiles = new Tile[size][size];
        //1.Make sure all tiles are empty
        this.buildings = new ArrayList<>();
        for(int row=0; row<tiles.length; row++){
            for(int column=0; column<tiles[0].length; column++){
                tiles[row][column] = new Tile(column, row);
            }
        }
        //2.Spawn in the gate tile
        build(BuildType.ENTRANCE,0,0);
        //3.Spawn in 1 from each for debugging purpose
        build(BuildType.CAROUSEL, 7, 0);
        build(BuildType.FERRISWHEEL, 7, 1);
        build(BuildType.HAUNTEDMANSION, 7, 2);
        build(BuildType.ROLLERCOASTER, 7, 3);
        build(BuildType.SWINGINGSHIP, 7, 4);
        
        build(BuildType.BURGERJOINT, 8, 0);
        build(BuildType.HOTDOGSTAND, 8, 1);
        build(BuildType.ICECREAMPARLOR, 8, 2);
        
        build(BuildType.PAVEMENT, 9, 0);
        build(BuildType.TOILET, 9, 1);
        build(BuildType.TRASHCAN, 9, 2);
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
        return tiles[y][x].isEmpty(); 
    }
    
    public void build(BuildType type, int x, int y){
        if(canBuild(type,x,y)){
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
    
    public void update(){
        System.out.println("UWU");
    }
}
