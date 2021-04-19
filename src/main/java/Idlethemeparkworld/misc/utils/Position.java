package Idlethemeparkworld.misc.utils;

public class Position {

    public int x, y;
    
    public Position() {
        this(0,0);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCoords(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Position translate(Direction d) {
        return new Position(x + d.x, y + d.y);
    }

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
