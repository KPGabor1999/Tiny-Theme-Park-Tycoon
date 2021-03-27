package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import java.util.ArrayList;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.agent.Visitor;

public class Toilet extends Infrastructure {
    protected ArrayList<Visitor> waitingLine;     //visitors wait in line
    protected int capacity;                       //how many stalls does it have
    private int cleanliness;
    
    public Toilet(int xLocation, int yLocation){
        this.maxLevel = 0;
        this.currentLevel = 1;
        this.x = xLocation;
        this.y = yLocation;
        this.buildingType = BuildType.TOILET;
        this.waitingLine = new ArrayList<>();
        this.capacity = 10;
        this.cleanliness = 100;
        this.value = BuildType.TOILET.getBuildCost();
    }

    public int getCleanliness() {
        return cleanliness;
    }
    
    public ArrayList<Pair<String, String>> getAllData(){
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        res.add(new Pair<>("Capacity: ", occupied + "/" + capacity));
        res.add(new Pair<>("Cleanliness: ", Integer.toString(cleanliness)));
        return res;
    }   
        
    @Override
    public void level2Upgrade(){}       //They're not meant to be upgradeable but they could be.
    
    @Override
    public void level3Upgrade(){}
    
    
    //Methods for managing visitors:
        
    public void joinLine(Visitor visitor){
        waitingLine.add(visitor);
        //Visitor starts waiting.
    }
    
    public void letVisitorsIn(){   //Multiple people can use it at a time.
        if(waitingLine.size() >= capacity){
            for(int nextInLine = 1; nextInLine <= capacity; nextInLine++){
                letVisitorIn();
            }
        } else {
            for(int nextInLine = 1; nextInLine <= waitingLine.size(); nextInLine++){
                letVisitorIn();
            }
        }
    }
    
    public void letVisitorIn(){
        waitingLine.remove(0);  //Visitor gets in.
        //Visitor uses the toilet (takes time)
        decreaseHygiene();
        //Now the visitor is happier and their toilet stat becomes 100. They go looking for the next activity.
    }
    
    public void decreaseHygiene(){
        cleanliness--;
    }
    
    public void leaveLine(Visitor visitor){
        waitingLine.remove(visitor);
        //If they can't use the bathroom in time, they quit the line and go home.
    }
    
    public void clean(){        //This is the janitor's job.
        this.cleanliness = 100;
    }

}
