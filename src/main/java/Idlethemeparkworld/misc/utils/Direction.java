package Idlethemeparkworld.misc.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Direction enum that abstracts directional unit vectors
 */
public enum Direction {
    DOWN(0, 1), LEFT(-1, 0), UP(0, -1), RIGHT(1, 0);

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final int x, y;

    public Direction turnLeft() {
        return Direction.values()[((ordinal() - 1) + 4) % 4];
    }

    public Direction turnRight() {
        return Direction.values()[((ordinal() + 1) + 4) % 4];
    }

    public Direction turnOpposite() {
        return Direction.values()[((ordinal() + 2) + 4) % 4];
    }

    private static final List<Direction> VALUES
            = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Direction randomDirection() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
