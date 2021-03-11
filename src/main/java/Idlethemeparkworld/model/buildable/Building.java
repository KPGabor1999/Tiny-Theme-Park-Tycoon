package Idlethemeparkworld.model.buildable;

public abstract class Building extends Buildable{
    protected int x, y;
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public abstract void interact();
}
