package Idlethemeparkworld.model;

import Idlethemeparkworld.model.buildable.Building;

/**
 * Tile data that holds location and building information
 */
public class Tile {
    private int x, y;
    private Building building;
    
    private boolean visited;
    
    /**
     * Creates a new tile at (0,0)
     */
    public Tile(){
        this(0,0);
    }
    /**
     * Creates a tile at the given position
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.building = null;
        this.visited = false;
    }

    /**
     * @return the x coordinate of this tile
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y coordinate of this tile
     */
    public int getY() {
        return y;
    }
    
    /**
     * @return whether this tile has been visited by the pathfinding
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Sets the visited value for pathfinding
     * @param visited whether the tile has been visited
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    /**
     * Checks whether the tile is empty.
     * 
     * This is only true if it is grass, no other buildings qualify.
     * @return whether the tile is empty or not
     */
    public boolean isEmpty(){
        return building == null;
    }
    
    /**
     * Gets the current building, null if the tile is empty
     * @return current building on this tile
     */
    public Building getBuilding(){
        return building;
    }
    
    /**
     * Sets the current building 
     * @param building The building to associate the tile with
     */
    public void setBuilding(Building building){
        this.building = building;
    }
    
    /**
     * Resets the current building binding to null
     */
    public void unsetBuilding(){
        building = null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.x;
        hash = 43 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Tile other = (Tile) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }
}
