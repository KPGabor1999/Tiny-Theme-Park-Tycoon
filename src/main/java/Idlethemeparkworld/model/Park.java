package Idlethemeparkworld.model;

import Idlethemeparkworld.misc.Assets;
import Idlethemeparkworld.misc.Sound;
import Idlethemeparkworld.misc.pathfinding.PathFinding;
import Idlethemeparkworld.misc.utils.Circle;
import Idlethemeparkworld.model.administration.Finance;
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

/**
 * A park that holds all tile and building information.
 */
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
    
    private ArrayList<Popup> popups;

    /**
     * Creates a 10x10 park
     */
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
        popups = new ArrayList<>();

        tiles = new Tile[rows][columns];
        pf = new PathFinding(tiles, this);
        reachable = new HashSet<>();

        this.buildings = new ArrayList<>();
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles[0].length; column++) {
                tiles[row][column] = new Tile(column, row);
            }
        }
        build(BuildType.ENTRANCE, 0, 0, true);

        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles[0].length; column++) {
                if (row > 10 || column > 5) {
                    build(BuildType.LOCKEDTILE, column, row, true);
                }
            }
        }
    }

    public PathFinding getPathfinding() {
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

    /**
     * Get a list of all buildings
     * @return list of all buildings
     */
    public synchronized ArrayList<Building> getBuildings() {
        return buildings;
    }

    /**
     * Checks if you can build a building type on a certain area.
     * The x and y specify the upper left corner of the building.
     * 
     * We can build if the area is empty and there is at least one neighbouring pavement.
     * 
     * @param type The building type
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return whether we can build on this location
     */
    public boolean canBuild(BuildType type, int x, int y) {
        if (checkEmptyArea(x, y, type.getWidth(), type.getLength())) {
            ArrayList<Tile> neighbours = getNeighbours(x, y, type.getLength(), type.getWidth());
            neighbours.removeIf(n -> !(n.getBuilding() instanceof Pavement || n.getBuilding() instanceof Entrance));
            return neighbours.size() > 0;
        } else {
            return false;
        }
    }
    
    /**
     * Check if the area is legal, meaning all parts are inside the tile matrix
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param width The width of the building
     * @param height The height of the building
     * @return wether the location is legal
     */
    private boolean checkLegalArea(int x, int y, int width, int height) {
        return (0 <= x && x+width-1 < getWidth())
                && (0 <= y && y+height-1 < getHeight());
    }

    /**
     * Check if the area is empty, meaning there is only grass there(locked tiles will prevent building)
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param width The width of the building
     * @param height The height of the building
     * @return wether the location is free
     */
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

    /**
     * Check if a singular coordinate is within the tile matrix
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return wether the coordinate is legal
     */
    private boolean checkLegalCoordinate(int x, int y) {
        return (0 <= x && x < getWidth())
                && (0 <= y && y < getHeight());
    }

    /**
     * Adds a single to a given list
     * @param list The list to add the neighbour to
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    private void addNeighbour(ArrayList<Tile> list, int x, int y) {
        if (checkLegalCoordinate(x, y)) {
            list.add(tiles[y][x]);
        }
    }

    /**
     * Adds all neighbours in a certain line
     * @param list The list to add the neighbours to
     * @param startX X coordinate of the top left
     * @param startY Y coordinate of the top left
     * @param range The length of the line to scan
     * @param isHorizontal whether the scan line should be horizontal or vertical
     */
    private void addNeighbourRange(ArrayList<Tile> list, int startX, int startY, int range, boolean isHorizontal) {
        for (int i = 0; i < range; i++) {
            if (isHorizontal) {
                addNeighbour(list, startX + i, startY);
            } else {
                addNeighbour(list, startX, startY + i);
            }
        }
    }

    /**
     * Get pavement neighbours. This includes the entrance as that is walkable too.
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return list of pavement neighbours
     */
    public ArrayList<Building> getPavementNeighbours(int x, int y) {
        ArrayList<Building> res = new ArrayList<>();
        ArrayList<Tile> neighbours = getNeighbours(x, y, 1, 1);
        neighbours.removeIf(n -> !(n.getBuilding() instanceof Pavement || n.getBuilding() instanceof Entrance));
        neighbours.forEach(n -> res.add(n.getBuilding()));
        return res;
    }

    /**
     * Get non-pavement neighbours. This includes the entrance as that is walkable too.
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return list of non-pavement neighbours
     */
    public ArrayList<Building> getNonPavementOrEntranceNeighbours(int x, int y) {
        ArrayList<Building> res = new ArrayList<>();
        ArrayList<Tile> neighbours = getNeighbours(x, y, 1, 1);
        neighbours.removeIf(n -> n.getBuilding() == null || n.getBuilding() instanceof Pavement || n.getBuilding() instanceof Entrance || n.getBuilding() instanceof LockedTile || n.getBuilding().getStatus() == BuildingStatus.DECAYED);
        neighbours.forEach(n -> res.add(n.getBuilding()));
        return res;
    }

    /**
     * Get pavement strictly non-eighbours
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return list of strictly non-pavement neighbours
     */
    public ArrayList<Building> getNonPavementNeighbours(int x, int y) {
        ArrayList<Building> res = new ArrayList<>();
        ArrayList<Tile> neighbours = getNeighbours(x, y, 1, 1);
        neighbours.removeIf(n -> n.getBuilding() == null || n.getBuilding() instanceof Pavement || n.getBuilding() instanceof LockedTile || n.getBuilding().getStatus() == BuildingStatus.DECAYED);
        neighbours.forEach(n -> res.add(n.getBuilding()));
        return res;
    }

    /**
     * Get infrastructure neighbours
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return list of infrastructure neighbours
     */
    public ArrayList<Building> getInfrastructureNeighbours(int x, int y) {
        ArrayList<Building> res = new ArrayList<>();
        ArrayList<Tile> neighbours = getNeighbours(x, y, 1, 1);
        neighbours.removeIf(n -> (n.getBuilding() == null || !(n.getBuilding() instanceof Infrastructure) || n.getBuilding() instanceof LockedTile || n.getBuilding().getStatus() == BuildingStatus.DECAYED));
        neighbours.forEach(n -> res.add(n.getBuilding()));
        return res;
    }

    /**
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param width The width of the building
     * @param height The height of the building
     * @return neighbours of the building
     */
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

    /**
     * Tries to build a building at a specified location.
     * The location gives the top left tile of the building.
     * 
     * If using the force functionality, no buildable checks will be done and no monew will
     * be deducted.
     * 
     * @param type The building type
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param force Whether to apply force building rules
     * @return 
     */
    public synchronized Building build(BuildType type, int x, int y, boolean force) {
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
            if(gm != null){
                gm.checkWin();
            }
            if(!force){
                //Sound.playSound(Assets.Sounds.CONSTRUCTION, false);
            }
            return newBuilding;
        }
        return null;
    }

    /**
     * Updates all buildings.
     */
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

    /**
     * @param type Building type
     * @return associated food price
     */
    public int checkFoodPrice(BuildType type) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo() != BuildType.LOCKEDTILE
                    && (buildings.get(i).getInfo() == type)) {
                return ((FoodStall) buildings.get(i)).getFoodPrice();
            }
        }
        return 0;
    }

    /**
     * Updates food prices in all buildings
     * @param type building type
     * @param price The new food price
     */
    public void updateFoodPrice(BuildType type, int price) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo() != BuildType.LOCKEDTILE
                    && (buildings.get(i).getInfo() == type)) {
                ((FoodStall) buildings.get(i)).setFoodPrice(price);
            }
        }
    }

    /**
     * @param type Building type
     * @return associated base ticket price
     */
    public int checkTicketPrice(BuildType type) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo() != BuildType.LOCKEDTILE
                    && (buildings.get(i).getInfo() == type)) {
                return ((Attraction) buildings.get(i)).getBaseEntryFee();
            }
        }
        return 0;
    }

    /**
     * @param type Building type
     * @return associated ticket price
     */
    public int getTicketPrice(BuildType type) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo() != BuildType.LOCKEDTILE
                    && (buildings.get(i).getInfo() == type)) {
                return ((Attraction) buildings.get(i)).getEntryFee();
            }
        }
        return 0;
    }

    /**
     * Updates entry prices in all buildings
     * @param type building type
     * @param price The new entry cost
     */
    public void updateTicketPrice(BuildType type, int price) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getInfo() != BuildType.LOCKEDTILE
                    && (buildings.get(i).getInfo() == type)) {
                ((Attraction) buildings.get(i)).setEntryFee(price);
            }
        }
    }

    /**
     * Demolishes a building at the given position.
     * Position gives the top left tile of the building.
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public synchronized void demolish(int x, int y) {
        Building building = tiles[y][x].getBuilding();
        setAreaToBuilding(x, y, building.getInfo().getLength(), building.getInfo().getWidth(), null);

        int demolitionIndex = 0;
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getX() == x && buildings.get(i).getY() == y) {
                demolitionIndex = i;
                break;
            }
        }

        buildings.remove(demolitionIndex);
        updateBuildings();
    }

    /**
     * Get the current park rating.
     * 
     * The calculation include factors such as cleanliness and littering, current happiness rating,
     * visitor count and others.
     * @return the current rating of the park
     */
    public double getRating() {
        calculateParkRating();
        return rating;
    }

    /**
     * @return the total value of buildings including decayed
     */
    public int getValue() {
        calculateValue();
        return parkValue;
    }

    /**
     * @return the total value of only active buildings
     */
    public int getActiveValue() {
        calculateActiveValue();
        return activeParkValue;
    }

    /**
     * @return the recommended maximum amount of visitors
     */
    public int getMaxGuest() {
        calculateMaxGuests();
        return maxGuests;
    }

    @Override
    public synchronized void update(long tickCount) {
        buildings.forEach(b -> b.update(tickCount));
        if (tickCount % Time.convMinuteToTick(15) == 0) {
            gm.getFinance().pay(500, Finance.FinanceType.UPKEEP);
        }
        if (tickCount % Time.convMinuteToTick(1) == 0){
            for (int i = 0; i < popups.size(); i++) {
                popups.get(i).update(tickCount);
                if(popups.get(i).getDurationSeconds() == 0){
                    popups.remove(i);
                }
            }
        }
    }

    /**
     * Calculates the park rating and updates it.
     * 
     * The calculation include factors such as cleanliness and littering, current happiness rating,
     * visitor count and others.
     */
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

    /**
     * Calculates the total value of the park, including decayed and inactive buildings.
     * Values are the total amount of money spend on a building.
     */
    private void calculateValue() {
        parkValue = 0;
        for (int i = 0; i < buildings.size(); i++) {
            parkValue += buildings.get(i).getValue();
        }
        parkValue += gm.getAgentManager().getVisitorValue();
    }

    /**
     * Calculates the total value of the park including only active buildings.
     * Values are the total amount of money spend on a building.
     */
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

    /**
     * Sums together the total recommended max visitor count
     */
    private void calculateMaxGuests() {
        maxGuests = 0;
        for (int i = 0; i < buildings.size(); i++) {
            maxGuests += buildings.get(i).getRecommendedMax();
        }
    }

    public ArrayList<Popup> getPopups() {
        return popups;
    }
    
    public Popup getPopup(int x, int y){
        Popup res = null;
        boolean found = false;
        for (int i = 0; i < popups.size() && !found; i++){
            if(popups.get(i).getCircle().contains(x,y)){
                res = popups.get(i);
                found = true;
            }
        }
        return res;
    }
    
    public void addPopup(int x, int y){
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(15);
        popups.add(new Popup(circle));
    }
    
    public void popPopup(Popup p){
        popups.remove(p);
    }
}
