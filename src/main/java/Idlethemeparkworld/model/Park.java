package Idlethemeparkworld.model;

import Idlethemeparkworld.misc.pathfinding.PathFinding;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Park implements Updatable {
    private static final int HISTORY_SIZE = 14;
    
    private GameManager gm;
    
    private double rating;
    private int parkValue;
    private int activeParkValue;

    private int maxGuests;

    private Tile[][] tiles;
    private ArrayList<Building> buildings;

    private PathFinding pf;
    private Set<Building> reachable;

    public Park() {
        initializePark(10, 10, null);
    }

    public Park(int size, GameManager gm) {
        initializePark(size, size, gm);
    }

    public Park(int rows, int columns, GameManager gm) {
        initializePark(rows, columns, gm);
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void initializePark(int rows, int columns, GameManager gm) {
        this.gm = gm;
        rating = 0;
        parkValue = 0;
        activeParkValue = 0;
        maxGuests = 0;

        tiles = new Tile[rows][columns];
        pf = new PathFinding(tiles);
        reachable = new HashSet<>();
        
        //1.Make sure all tiles are empty
        this.buildings = new ArrayList<>();
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles[0].length; column++) {
                tiles[row][column] = new Tile(column, row);
            }
        }
        //2.Spawn in the gate tile
        build(BuildType.ENTRANCE, 0, 0, true);
        
        //3. Install locked tiles
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column<tiles[0].length; column++) {
                if(row > 5 || column >5){
                    build(BuildType.LOCKEDTILE, column, row, true);
                }
            }
        }
    }

    public int getWidth() {
        return tiles[0].length;
    }

    public int getHeight() {
        return tiles.length;
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public Building findBuilding(String type) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo().getName().equals(type)) {
                return buildings.get(i);
            }
        }
        return null;
    }

    public boolean canBuild(BuildType type, int x, int y) {
        if (checkEmptyArea(x, y, type.getWidth(), type.getLength())) {
            ArrayList<Tile> neighbours = getNeighbours(x, y, type.getLength(), type.getWidth());
            neighbours.removeIf(n -> !(n.getBuilding() instanceof Pavement || n.getBuilding() instanceof Entrance));
            return neighbours.size() > 0;
        } else {
            return false;
        }
    }

    private boolean checkEmptyArea(int x, int y, int width, int height) {
        boolean isEmpty = true;
        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                isEmpty = isEmpty && tiles[i][j].isEmpty();
            }
        }
        return isEmpty;
    }

    private boolean checkLegalCoordinate(int x, int y) {
        return (0 <= x && x < getWidth())
                && (0 <= y && y < getHeight());
    }

    private void addNeighbour(ArrayList<Tile> list, int x, int y) {
        if (checkLegalCoordinate(x, y)) {
            list.add(tiles[y][x]);
        }
    }

    private void addNeighbourRange(ArrayList<Tile> list, int startX, int startY, int range, boolean isHorizontal) {
        for (int i = 0; i < range; i++) {
            if (isHorizontal) {
                addNeighbour(list, startX + i, startY);
            } else {
                addNeighbour(list, startX, startY + i);
            }
        }
    }
    
    public ArrayList<Building> getPavementNeighbours(int x, int y){
        ArrayList<Building> res = new ArrayList<>();
        ArrayList<Tile> neighbours = getNeighbours(x,y,1,1);
        neighbours.removeIf(n -> !(n.getBuilding() instanceof Pavement || n.getBuilding() instanceof Entrance));
        neighbours.forEach(n -> res.add(n.getBuilding()));
        return res;
    }
    
    public ArrayList<Building> getNonPavementNeighbours(int x, int y){
        ArrayList<Building> res = new ArrayList<>();
        ArrayList<Tile> neighbours = getNeighbours(x,y,1,1);
        neighbours.removeIf(n -> (n.getBuilding() instanceof Pavement || n.getBuilding() instanceof Entrance));
        neighbours.forEach(n -> res.add(n.getBuilding()));
        return res;
    }
    
    private ArrayList<Tile> getNeighbours(int x, int y, int height, int width){
        ArrayList<Tile> neighbours = new ArrayList<>();
        addNeighbourRange(neighbours, x, y - 1, width, true); //top neighbours
        addNeighbourRange(neighbours, x - 1, y, height, false); //left neighbours
        addNeighbourRange(neighbours, x, y + height, width, true); //bottom neighbours
        addNeighbourRange(neighbours, x + width, y, height, false); //right neighbours
        return neighbours;
    }

    private void setAreaToBuilding(int x, int y, int height, int width, Building building) {
        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                tiles[i][j].setBuilding(building);
            }
        }
    }

    public void build(BuildType type, int x, int y, boolean force) {
        if (canBuild(type, x, y) || force) {
            Building newBuilding = null;
            try {
                Class buildingClass = BuildType.GetClass(type);
                Class[] paramType = {int.class, int.class, GameManager.class};
                Constructor cons = buildingClass.getConstructor(paramType);
                newBuilding = (Building) cons.newInstance(x, y, gm);
            } catch (Exception e) {
                e.printStackTrace();
            }
            buildings.add(newBuilding);
            setAreaToBuilding(x, y, type.getLength(), type.getWidth(), newBuilding);
            updateBuildings();
        }
    }

    private void updateBuildings() {
        pf.updateTiles(tiles);
        reachable = pf.getReachableBuildings();

        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo() != BuildType.LOCKEDTILE) {
                if (!reachable.contains(buildings.get(i))) {
                    buildings.get(i).setStatus(BuildingStatus.FLOATING);
                } else if (buildings.get(i).getStatus() == BuildingStatus.FLOATING) {
                    buildings.get(i).setStatus(BuildingStatus.OPEN);
                }
            }
        }
    }

    public void updateFoodPrices(int hotdog, int icecream, int hamburger, int fishchips) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo() != BuildType.LOCKEDTILE && 
               (buildings.get(i).getInfo() == BuildType.HOTDOGSTAND
               || buildings.get(i).getInfo() == BuildType.ICECREAMPARLOR
               || buildings.get(i).getInfo() == BuildType.BURGERJOINT)) {
                switch(buildings.get(i).getInfo()){
                    case HOTDOGSTAND:
                        ((FoodStall)buildings.get(i)).setFoodPrice(hotdog);
                        break;
                    case ICECREAMPARLOR:
                        ((FoodStall)buildings.get(i)).setFoodPrice(icecream);
                        break;
                    case BURGERJOINT:
                        ((FoodStall)buildings.get(i)).setFoodPrice(hamburger);
                        break;
                    default:
                        throw new AssertionError(buildings.get(i).getInfo().name());
                }
            }
        }
    }

    public void demolish(int x, int y) {
        (this.tiles[y][x]).unsetBuilding();

        int demolitionIndex = 0;
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getX() == x
                    && buildings.get(i).getY() == y) {
                demolitionIndex = i;
                break;
            }
        }

        buildings.remove(demolitionIndex);
        updateBuildings();
    }

    public double getRating() {
        calculateParkRating();
        return rating;
    }

    public int getValue() {
        calculateValue();
        return parkValue;
    }

    public int getActiveValue() {
        calculateActiveValue();
        return activeParkValue;
    }

    public int getMaxGuest() {
        calculateMaxGuests();
        return maxGuests;
    }

    @Override
    public void update(long tickCount) {
        buildings.forEach(b -> b.update(tickCount));
    }

    private void calculateParkRating() {
        rating = 9;
        double sum = 0;
        double negative = 0;
        for (int i = 0; i < buildings.size(); i++) {
            //sum += buildings.get(i).getRating();
            if(buildings.get(i) instanceof Toilet){
                negative += 100-((Toilet)buildings.get(i)).getCleanliness();
            } else if(buildings.get(i) instanceof Infrastructure){
                negative += ((Infrastructure)buildings.get(i)).checkLittering();
            } 
        }
        //rating = sum/buildings.size();
        negative *= 0.05;
        negative = Math.min(negative, 2);
        rating -= negative;
        rating = (rating + gm.getAgentManager().getVisitorHappinessRating()) / 2.0;
        rating -= 3;
        rating += Math.min(gm.getAgentManager().getVisitorCount()/200.0, 3.0);
        rating = Math.min(rating, 10);
    }

    private void calculateValue() {
        parkValue = 0;
        for (int i = 0; i < buildings.size(); i++) {
            parkValue += buildings.get(i).getValue();
        }
        parkValue += gm.getAgentManager().getVisitorValue();
    }

    private void calculateActiveValue() {
        activeParkValue = 0;
        for (int i = 0; i < buildings.size(); i++) {
            switch(buildings.get(i).getStatus()){
                case RUNNING:
                case OPEN:
                case CLOSED:
                    activeParkValue += buildings.get(i).getValue();
                    break;
                default:
                    break;    
            }
        }
    }

    private void calculateMaxGuests() {
        maxGuests = 0;
        for (int i = 0; i < buildings.size(); i++) {
            maxGuests += buildings.get(i).getRecommendedMax();
        }
    }
}
