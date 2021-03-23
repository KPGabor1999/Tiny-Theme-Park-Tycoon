package Idlethemeparkworld.model;

import Idlethemeparkworld.model.buildable.Building;

public class Tile {
    private int x, y;
    private Building building;
    
    private boolean visited; //for pathfinding
    
    public Tile(){
        this(0,0);
    }
    
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.building = null;
        this.visited = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    private boolean isNeighbour(Tile other) {
        if (x == other.x) {
            return Math.abs(y - other.y) <= 1;
        } else if (y == other.y) {
            return Math.abs(x - other.x) <= 1;
        }
        return false;
    }
    
    public boolean isEmpty(){
        return building == null;
    }
    
    public Building getBuilding(){
        return building;
    }
    
    public void setBuilding(Building building){
        this.building = building;
    }
    
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
