
package Idlethemeparkworld.model.buildable;

public interface Repairable {
    
    public boolean shouldRepair();
    
    public double getCondition();
    
    public void setCondition(double newCondition);
}
