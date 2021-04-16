package Idlethemeparkworld.misc.utils;

public class Position {

    public int x, y;

    public Position(int x, int y) {
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
}
