package Idlethemeparkworld.model;

import Idlethemeparkworld.model.agent.Visitor;
import java.util.ArrayList;
import java.util.Random;

public class AgentManager implements Updatable {
    //Will store these in a file in the future
    private static final String[] NAMES = {
        "Creola",
        "Josue",
        "Bradford",
        "Elouise",
        "Debera",
        "Lorelei",
        "Rafaela",
        "Nicholas",
        "Shanice",
        "Somer",
        "Vada",
        "Mireille",
        "Reita",
        "Masako",
        "Jacob",
        "Efrain",
        "Mee",
        "Marge",
        "Lucio",
        "Margurite",
        "Brigid",
        "Nida",
        "Coreen",
        "Lamonica",
        "Gil",
        "Caron",
        "Vrenda"
    };
    private static final int AGENT_UPDATE_TICK = 12;
    
    private int visitorProbability;
    private Park park;
    
    private Random rand;
    
    private ArrayList<Visitor> visitors;
    
    public AgentManager(Park park){
        this.park = park;
        this.visitorProbability = 0;
        this.rand = new Random();
        this.visitors = new ArrayList<>();
    }
    
    public void spawnVisitor(){
        visitors.add(new Visitor(getRandomName(), getRandomHappiness(), park));
    }
    
    private String getRandomName(){
        return NAMES[rand.nextInt(NAMES.length)];
    }
            
    private int getRandomHappiness(){
        return rand.nextInt(50)+35;
    }
    
    //Might need to make these penalties more gradual rather than the current threshold based one
    public void updateVisitorProbability(){
        int prob = 50;
        
        int rating = park.getRating();
        prob += rating > 100 ? 100 : rating;
        
        int visitorCount = 0;
        if(visitorCount > park.getMaxGuest()){
            if(visitorCount > park.getMaxGuest()*1.2){
                prob = 0;
            } else {
                prob *= 0.1;
            }
        }
        
        int entFee = park.getEntranceFee();
        if(entFee > park.getActiveValue()){
            prob *= 0.4;
        }
        
        visitorProbability = prob;
    }
    
    
    
    public void spawnUpdate(){
        if(rand.nextInt(1000)<visitorProbability){
            spawnVisitor();
        }
    }
    
    public int getStaffCount(){
        return 0;
    }
    
    public int getVisitorCount(){
        return visitors.size();
    }
    
    public void update(long tickCount){
        spawnUpdate();
        for (int i = 0; i < visitors.size(); i++) {
            visitors.get(i).update(tickCount);
        }
    }
}
