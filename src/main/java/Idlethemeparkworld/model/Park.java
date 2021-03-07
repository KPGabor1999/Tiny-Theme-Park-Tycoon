package Idlethemeparkworld.model;

import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Carousel;
import Idlethemeparkworld.model.buildable.attraction.FerrisWheel;
import Idlethemeparkworld.model.buildable.attraction.HauntedHouse;
import Idlethemeparkworld.model.buildable.attraction.RollerCoaster;
import Idlethemeparkworld.model.buildable.attraction.SwingingShip;
import Idlethemeparkworld.model.buildable.food.Hamburger;
import Idlethemeparkworld.model.buildable.food.HotDog;
import Idlethemeparkworld.model.buildable.food.IceCream;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;
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
        Building newBuilding = null;
        //Extremely quick code, will move this gigantic switch inside the enum itself for better encapsulation
        //Also will be much easier to maintain
        switch(type){
            case PAVEMENT:
                newBuilding = new Pavement();
                break;
            case TRASHCAN:
                newBuilding = new TrashCan();
                break;
            case TOILET:
                newBuilding = new Toilet();
                break;
            case HOTDOGSTAND:
                newBuilding = new HotDog();
                break;
            case ICECREAMPARLOR:
                newBuilding = new IceCream();
                break;
            case BURGERJOINT:
                newBuilding = new Hamburger();
                break;
            case CAROUSEL:
                newBuilding = new Carousel();
                break;
            case FERRISWHEEL:
                newBuilding = new FerrisWheel();
                break;
            case SWINGINGSHIP:
                newBuilding = new SwingingShip();
                break;
            case ROLLERCOASTER:
                newBuilding = new RollerCoaster();
                break;
            case HAUNTEDMANSION:
                newBuilding = new HauntedHouse();
                break;
        }
        
        buildings.add(newBuilding);
        //We are assuming all buildings are 1x1 for the time being
        tiles[y][x].setBuilding(true, newBuilding);
    }
}
