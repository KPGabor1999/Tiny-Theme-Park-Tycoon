package Idlethemeparkworld.model;

import Idlethemeparkworld.misc.pathfinding.PathFinding;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.LockedTile;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Park implements Updatable {

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

    public void initializePark(int rows, int columns, GameManager gm) {
        this.gm = gm;
        rating = 0;
        parkValue = 0;
        activeParkValue = 0;
        maxGuests = 0;

        tiles = new Tile[rows][columns];
        pf = new PathFinding(tiles, this);
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
            for (int column = 0; column < tiles[0].length; column++) {
                if (row > 10 || column > 5) {
                    build(BuildType.LOCKEDTILE, column, row, true);
                }
            }
        }
    }
    
    public PathFinding getPathfinding(){
        return pf;
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

    public boolean canBuild(BuildType type, int x, int y) {
        if (checkEmptyArea(x, y, type.getWidth(), type.getLength())) {
            ArrayList<Tile> neighbours = getNeighbours(x, y, type.getLength(), type.getWidth());
            neighbours.removeIf(n -> !(n.getBuilding() instanceof Pavement || n.getBuilding() instanceof Entrance));
            return neighbours.size() > 0;
        } else {
            return false;
        }
    }
    
    private boolean checkLegalArea(int x, int y, int width, int height) {
        return (0 <= x && x+width < getWidth())
                && (0 <= y && y+height < getHeight());
    }

    private boolean checkEmptyArea(int x, int y, int width, int height) {
        boolean isEmpty = true;
        if(!checkLegalArea(x, y, width, height)) {
            return false;
        }
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

    public ArrayList<Building> getPavementNeighbours(int x, int y) {
        ArrayList<Building> res = new ArrayList<>();
        ArrayList<Tile> neighbours = getNeighbours(x, y, 1, 1);
        neighbours.removeIf(n -> !(n.getBuilding() instanceof Pavement || n.getBuilding() instanceof Entrance));
        neighbours.forEach(n -> res.add(n.getBuilding()));
        return res;
    }

    public ArrayList<Building> getNonPavementOrEntranceNeighbours(int x, int y) {
        ArrayList<Building> res = new ArrayList<>();
        ArrayList<Tile> neighbours = getNeighbours(x, y, 1, 1);
        neighbours.removeIf(n -> n.getBuilding() == null || n.getBuilding() instanceof Pavement || n.getBuilding() instanceof Entrance || n.getBuilding() instanceof LockedTile || n.getBuilding().getStatus() == BuildingStatus.DECAYED);
        neighbours.forEach(n -> res.add(n.getBuilding()));
        return res;
    }
    
    public ArrayList<Building> getNonPavementNeighbours(int x, int y) {
        ArrayList<Building> res = new ArrayList<>();
        ArrayList<Tile> neighbours = getNeighbours(x, y, 1, 1);
        neighbours.removeIf(n -> n.getBuilding() == null || n.getBuilding() instanceof Pavement || n.getBuilding() instanceof LockedTile || n.getBuilding().getStatus() == BuildingStatus.DECAYED);
        neighbours.forEach(n -> res.add(n.getBuilding()));
        return res;
    }

    public ArrayList<Building> getWalkableNeighbours(int x, int y) {
        ArrayList<Building> res = new ArrayList<>();
        ArrayList<Tile> neighbours = getNeighbours(x, y, 1, 1);
        neighbours.removeIf(n -> (n.getBuilding() == null || n.getBuilding() instanceof LockedTile));
        neighbours.forEach(n -> res.add(n.getBuilding()));
        return res;
    }

    private ArrayList<Tile> getNeighbours(int x, int y, int height, int width) {
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

    public Building build(BuildType type, int x, int y, boolean force) {
        if (force || canBuild(type, x, y)) {
            Building newBuilding = null;
            try {
                Class buildingClass = BuildType.GetClass(type);
                Class[] paramType = {int.class, int.class, GameManager.class};
                Constructor cons = buildingClass.getConstructor(paramType);
                newBuilding = (Building) cons.newInstance(x, y, gm);
            } catch (Exception e) {
                return null;
            }
            buildings.add(newBuilding);
            setAreaToBuilding(x, y, type.getLength(), type.getWidth(), newBuilding);
            updateBuildings();
            return newBuilding;
        }
        return null;
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

    public int checkFoodPrice(BuildType type) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo() != BuildType.LOCKEDTILE
                    && (buildings.get(i).getInfo() == type)) {
                return ((FoodStall) buildings.get(i)).getFoodPrice();
            }
        }
        return 0;
    }

    public void updateFoodPrice(BuildType type, int price) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo() != BuildType.LOCKEDTILE
                    && (buildings.get(i).getInfo() == type)) {
                ((FoodStall) buildings.get(i)).setFoodPrice(price);
            }
        }
    }
    
    public int checkTicketPrice(BuildType type) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo() != BuildType.LOCKEDTILE
                    && (buildings.get(i).getInfo() == type)) {
                return ((Attraction) buildings.get(i)).getEntryFee();
            }
        }
        return 0;
    }

    public void updateTicketPrice(BuildType type, int price) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo() != BuildType.LOCKEDTILE
                    && (buildings.get(i).getInfo() == type)) {
                ((Attraction) buildings.get(i)).setEntryFee(price);
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
    public synchronized void update(long tickCount) {
        buildings.forEach(b -> b.update(tickCount));
        if(tickCount % Time.convMinuteToTick(15) == 0){
            gm.getFinance().pay(500);
        }
    }

    private void calculateParkRating() {
        rating = 9;
        //double sum = 0;
        double negative = 0;
        for (int i = 0; i < buildings.size(); i++) {
            //sum += buildings.get(i).getRating();
            if (buildings.get(i) instanceof Toilet) {
                negative += ((Toilet) buildings.get(i)).getCleanliness();
            } else if (buildings.get(i) instanceof Infrastructure) {
                negative += ((Infrastructure) buildings.get(i)).getLittering();
            }
        }
        //rating = sum/buildings.size();
        rating = (rating + gm.getAgentManager().getVisitorHappinessRating()) / 2;
        rating -= 3;
        rating += Math.min(gm.getAgentManager().getVisitorCount() / 200.0, 3.0);
        negative *= 0.05;
        negative = Math.min(negative, 2);
        rating -= negative;
        rating = Math.min(rating, 10);
        rating = Math.max(rating, 0);
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
            switch (buildings.get(i).getStatus()) {
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
