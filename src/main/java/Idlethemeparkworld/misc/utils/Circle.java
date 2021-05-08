package Idlethemeparkworld.misc.utils;

public class Circle {
    private int centerX;
    private int centerY;
    private double radius;
    
    public Circle() {
        this.centerX = 0;
        this.centerY = 0;
        this.radius = 0;
    }
    
    /**
     * Creates a new instance of Circle with a specified position and radius.
     * @param centerX the horizontal position of the center of the circle in pixels
     * @param centerY the vertical position of the center of the circle in pixels
     * @param radius the radius of the circle in pixels
     */
    public Circle(int centerX, int centerY, double radius) {
        setCenterX(centerX);
        setCenterY(centerY);
        setRadius(radius);
    }

    public final void setCenterX(int value) {
        centerX = value;
    }

    public final int getCenterX() {
        return centerX;
    }

    public final void setCenterY(int value) {
        centerY = value;
    }

    public final int getCenterY() {
        return centerY;
    }
    
    public final void setRadius(double value) {
        radius = value;
    }

    public final double getRadius() {
        return radius;
    }
    
    public boolean contains(int x, int y){
        Position p = new Position(x,y);
        Position c = new Position(centerX, centerY);
        return p.distance(c) < radius;
    }
}
