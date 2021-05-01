package Idlethemeparkworld.misc.utils;

/**
 * A position in a cartesian coordinate system with integer coordinates
 */
public class Position {

    public int x, y;
    
    /**
     * Creates a new position at (0,0)
     */
    public Position() {
        this(0,0);
    }

    /**
     * Creates a new position at (x,y)
     * @param x The X coord
     * @param y The Y coord
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the coordinates of the position
     * @param x The X coord
     * @param y The Y coord
     */
    public void setCoords(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Translates the current position with the given direction unit vector.
     * Basically adds the x and y to the current position.
     * @param d the direction in which to move in
     */
    public Position translate(Direction d) {
        return new Position(x + d.x, y + d.y);
    }

    /**
     * Lerps between two positions, with the ratio giving which point in between the two positions to return.
     * 
     * A ratio of 0 will be at exactly current, and 1 will be at other
     * @param other The other position to lerp to
     * @param ratio The lerping amount
     * @return The lerped position in between the two
     */
    public Position lerp(Position other, double ratio) {
        return new Position(
                (int) Math.round(x + (other.x - x) * ratio),
                (int) Math.round(y + (other.y - y) * ratio));
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Position other = (Position) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + "x=" + x + ", y=" + y + ')';
    }
}
