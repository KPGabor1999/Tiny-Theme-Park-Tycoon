package Idlethemeparkworld.model.buildable;

public abstract class Building extends Buildable{
    protected int width, length;
    protected String name;

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }
    
    public void build(){
        
    }
    
    public abstract void interact();
}
