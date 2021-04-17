package Idlethemeparkworld.misc.pathfinding;

import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.buildable.Building;

public class Node {
    public int distance;
    public Node parent;
    public Position current;

    public Node(int distance, Node parent, Position current) {
        this.distance = distance;
        this.parent = parent;
        this.current = current;
    }
}
