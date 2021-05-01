package Idlethemeparkworld.misc.pathfinding;

import Idlethemeparkworld.misc.utils.Position;

/**
 * Wrapper class for nodes for graph algorithms
 */
public class Node {
    public int distance;
    public Node parent;
    public Position current;

    /**
     * Create a struct-like wrapper class for graph nodes
     * @param distance Distance from origin, usually used in graph traversal
     * @param parent Parent node, usually used in graph traversal
     * @param current The position of the current node
     */
    public Node(int distance, Node parent, Position current) {
        this.distance = distance;
        this.parent = parent;
        this.current = current;
    }
}
