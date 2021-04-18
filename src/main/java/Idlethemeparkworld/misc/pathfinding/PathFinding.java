package Idlethemeparkworld.misc.pathfinding;

import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.Tile;
import Idlethemeparkworld.model.buildable.Building;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PathFinding {
    private Tile[][] tiles;
    private final ArrayList<Tile> reachables;
    private final ArrayList<Node> discovered;
    private Park park;

    public PathFinding(Tile[][] tiles, Park park) {
        this.tiles = tiles;
        this.reachables = new ArrayList<>();
        this.discovered = new ArrayList<>();
        this.park = park;
    }

    public void updateTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Set<Building> getReachableBuildings() {
        resetReachables();
        Set<Building> result = new HashSet<>();

        Tile current = null;
        reachables.add(tiles[0][0]);
        while (!reachables.isEmpty()) {
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
    
    public ArrayList<Position> getPath(Position start, Building destination){
        resetPathFinding();
        
        ArrayList<Position> path = new ArrayList<>();
        
        Node current;
        Node parser = null;
        
        ArrayList<Node> leaves = new ArrayList<>();
        ArrayList<Position> foundPositions = new ArrayList<>();
        leaves.add(new Node(0, null, start));
        
        boolean found = false;
        while (!leaves.isEmpty() && !found) {
            current = leaves.remove(0);
            discovered.add(current);
            found = found || park.getTile(current.current.x, current.current.y).getBuilding().equals(destination);
            foundPositions.add(current.current);
            if(found){
                parser = current;
            } else {
                ArrayList<Building> pavements = park.getPavementNeighbours(current.current.x, current.current.y);
                for (int i = 0; i < pavements.size(); i++) {
                    if(!foundPositions.contains(pavements.get(i).getPos())){
                        leaves.add(new Node(current.distance+1, current, pavements.get(i).getPos()));
                        foundPositions.add(pavements.get(i).getPos());
                    }
                }

                ArrayList<Building> buildings = park.getNonPavementNeighbours(current.current.x, current.current.y);
                for (int i = 0; i < buildings.size() && !found; i++) {
                    if(!foundPositions.contains(buildings.get(i).getPos())){
                        Node newNode = new Node(current.distance+1, current, buildings.get(i).getPos());
                        discovered.add(newNode);
                        found = found || park.getTile(buildings.get(i).getPos().x, buildings.get(i).getPos().y).getBuilding().equals(destination);
                        foundPositions.add(buildings.get(i).getPos());
                        if(found){
                            parser = newNode;
                        }
                    }
                }
            }
        }
        
        if(parser != null){
            while (!parser.current.equals(start)) {
                path.add(parser.current);
                parser = parser.parent;
            }
        }
        
        Collections.reverse(path);
        return path;
    }
    
    private void resetPathFinding() {
        discovered.clear();
        park.getBuildings().forEach((b) -> b.setVisited(false));
    }

    private void resetReachables() {
        reachables.clear();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].setVisited(false);
            }
        }
    }

    private boolean checkCellExists(int x, int y) {
        return 0 <= x && x < tiles[0].length && 0 <= y && y < tiles.length;
    }

    public ArrayList<Tile> getNeighbourPavementList(int x, int y) {
        ArrayList<Tile> neighbours = getAllNeighbours(x, y);

        neighbours.removeIf(t -> t.getBuilding() == null || t.getBuilding().getInfo() != BuildType.PAVEMENT);

        return neighbours;
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
            if (t.getBuilding() != null && !t.isVisited()) {
                neighbourBuildings.add(t.getBuilding());
            }
        }

        neighbourBuildings.removeIf(b -> b.getInfo() == BuildType.LOCKEDTILE);

        return neighbourBuildings;
    }

    private ArrayList<Tile> getAllNeighbours(int x, int y) {
        ArrayList<Tile> neighbours = new ArrayList<>();

        Tile[] n = new Tile[4];
        n[0] = checkCellExists(x, y - 1) ? tiles[y - 1][x] : null;
        n[1] = checkCellExists(x + 1, y) ? tiles[y][x + 1] : null;
        n[2] = checkCellExists(x, y + 1) ? tiles[y + 1][x] : null;
        n[3] = checkCellExists(x - 1, y) ? tiles[y][x - 1] : null;

        for (Tile t : n) {
            if (t != null) {
                neighbours.add(t);
            }
        }
        return neighbours;
    }
}
