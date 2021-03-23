package Idlethemeparkworld.misc.pathfinding;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Tile;
import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PathFinding {
    private Tile[][] tiles;
    private ArrayList<Tile> reachables;
    
    public PathFinding(Tile[][] tiles) {
        this.tiles = tiles;
        this.reachables = new ArrayList<>();
    }
    
    public void updateTiles(Tile[][] tiles){
        this.tiles = tiles;
    }
    
    public Set<Building> getReachableBuildings(){
        resetPathfinding();
        Set<Building> result = new HashSet<>();
        
        Tile current = null;
        reachables.add(tiles[0][0]);
        while(!reachables.isEmpty()){
            current = reachables.remove(0);
            current.setVisited(true);
            reachables.addAll(getUnvisitedNeighbourPavementList(current.getX(), current.getY()));
            result.add(current.getBuilding());
            
            ArrayList<Building> bs = getUnvisitedNeighbourBuildingList(current.getX(), current.getY());
            bs.forEach(b -> {
                result.add(b);
            });
        }
        
        return result;
    }
    
    private void resetPathfinding(){
        reachables.clear();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].setVisited(false);
            }
        }
    }
    
    private boolean checkCellExists(int x, int y) {
        return 0<=x && x<tiles.length && 0<=y && y<tiles.length;
    }
    
    private ArrayList<Tile> getUnvisitedNeighbourPavementList(int x, int y) {
        ArrayList<Tile> neighbours = getAllNeighbours(x, y);

        neighbours.removeIf(t -> t.isVisited() || t.getBuilding() == null || t.getBuilding().getInfo() != BuildType.PAVEMENT);
        
        return neighbours;
    }
    
    private ArrayList<Building> getUnvisitedNeighbourBuildingList(int x, int y) {
        ArrayList<Tile> neighbours = getAllNeighbours(x, y);
        ArrayList<Building> neighbourBuildings = new ArrayList<>();
        for (Tile t : neighbours) {
            if(t.getBuilding() != null && !t.isVisited()){
                neighbourBuildings.add(t.getBuilding());
            }
        }
                
        neighbourBuildings.removeIf(b -> b.getInfo() == BuildType.LOCKEDTILE);
        
        return neighbourBuildings;
    }

    private ArrayList<Tile> getAllNeighbours(int x, int y) {
        ArrayList<Tile> neighbours = new ArrayList<>();

        Tile[] n = new Tile[4];
        n[0] = checkCellExists(x, y-1) ? tiles[y-1][x] : null;
        n[1] = checkCellExists(x+1, y) ? tiles[y][x+1] : null;
        n[2] = checkCellExists(x, y+1) ? tiles[y+1][x] : null;
        n[3] = checkCellExists(x-1, y) ? tiles[y][x-1] : null;
        
        for(Tile t : n){
            if (t != null) {
                neighbours.add(t);
            }
        }
        return neighbours;
    }
}
