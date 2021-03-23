package Idlethemeparkworld.misc.pathfinding;

import Idlethemeparkworld.misc.Direction;
import java.util.ArrayList;
import java.util.List;

public class Cell {

    private final int x, y;

    private boolean visited = false;
    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private boolean isNeighbour(Cell other) {
        if (x == other.x) {
            return Math.abs(y - other.y) <= 1;
        } else if (y == other.y) {
            return Math.abs(x - other.x) <= 1;
        }
        return false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    private Cell checkCellExists(List<Cell> grid, Cell neighbour) {
        if (grid.contains(neighbour)) {
            return grid.get(grid.indexOf(neighbour));
        } else {
            return null;
        }
    }

    public List<Cell> getUnvisitedNeighboursList(List<Cell> grid) {

        List<Cell> neighbours = getAllNeighbours(grid);

        Cell top = checkCellExists(grid, new Cell(x, y - 1));
        Cell right = checkCellExists(grid, new Cell(x + 1, y));
        Cell bottom = checkCellExists(grid, new Cell(x, y + 1));
        Cell left = checkCellExists(grid, new Cell(x - 1, y));

        if (top != null) {
            if (!top.visited) {
                neighbours.add(top);
            }
        }
        if (right != null) {
            if (!right.visited) {
                neighbours.add(right);
            }
        }
        if (bottom != null) {
            if (!bottom.visited) {
                neighbours.add(bottom);
            }
        }
        if (left != null) {
            if (!left.visited) {
                neighbours.add(left);
            }
        }

        return neighbours;
    }

    public List<Cell> getAllNeighbours(List<Cell> grid) {
        List<Cell> neighbours = new ArrayList<Cell>();

        Cell top = checkCellExists(grid, new Cell(x, y - 1));
        Cell right = checkCellExists(grid, new Cell(x + 1, y));
        Cell bottom = checkCellExists(grid, new Cell(x, y + 1));
        Cell left = checkCellExists(grid, new Cell(x - 1, y));

        if (top != null) {
            neighbours.add(top);
        }
        if (right != null) {
            neighbours.add(right);
        }
        if (bottom != null) {
            neighbours.add(bottom);
        }
        if (left != null) {
            neighbours.add(left);
        }

        return neighbours;
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
        Cell other = (Cell) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }
}
