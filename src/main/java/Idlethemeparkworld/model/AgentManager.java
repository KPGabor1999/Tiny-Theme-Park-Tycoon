package Idlethemeparkworld.model;

import Idlethemeparkworld.model.agent.Agent;
import Idlethemeparkworld.model.agent.Janitor;
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
    
    private double visitorProbability;
    private Park park;
    private GameManager gm;
    
    private Random rand;
    
    private ArrayList<Visitor> visitors;
    private ArrayList<Janitor> janitors;
    private Visitor highlighted;
    
    public AgentManager(Park park, GameManager gm){
        this.gm = gm;
        this.park = park;
        this.visitorProbability = 0;
        this.rand = new Random();
        this.visitors = new ArrayList<>();
        this.janitors = new ArrayList<>(5);     //0-5 janitors allowed at a time.
        spawnVisitor();
        highlighted = visitors.get(0);
    }
    
    public Visitor getHighlightedVisitor(){
        return highlighted;
    }
    
    public void spawnVisitor(){
        visitors.add(new Visitor(getRandomName(), getRandomHappiness(), park, this));
    }
    
    private String getRandomName(){
        return NAMES[rand.nextInt(NAMES.length)];
    }
            
    private int getRandomHappiness(){
        return rand.nextInt(50)+35;
    }
    
    public double getVisitorHappinessRating(){
        double res = 0;
        for (int i = 0; i < visitors.size(); i++) {
            res += visitors.get(i).getHappiness();
        }
        return res/visitors.size()/10;
    }
    
    public int getVisitorValue(){
        int res = 0;
        for (int i = 0; i < visitors.size(); i++) {
            res += visitors.get(i).getCashSpent();
        }
        return res;
    }
    
    public void updateVisitorProbability(){
        double prob = park.getRating()*5;
        
        int visitorCount = visitors.size();
        //System.out.println(visitorCount + " - " + park.getMaxGuest());
        if(visitorCount > park.getMaxGuest()){
            if(visitorCount > park.getMaxGuest()*1.2){
                prob = 0;
            } else {
                prob *= 0.1;
            }
        }
        
        int entFee = gm.getEntranceFee();
        if(entFee > park.getActiveValue()/10000){
            prob *= 0.4;
        }
        
        visitorProbability = prob;
        //System.out.println(visitorProbability);
    }
    
    public void removeAgent(Agent agent){
        if(agent instanceof Visitor){
            visitors.remove((Visitor)agent);
        }
    }
    
    private void spawnUpdate(){ 
        if(rand.nextInt(100)<visitorProbability){
            spawnVisitor();
        }
    }
    
    public int getStaffCount(){
        return 0;
    }
    
    public int getVisitorCount(){
        return visitors.size();
    }
    
    public ArrayList<Visitor> getVisitors(){
        return visitors;
    }
    
    public void manageJanitors(int newNumberOfJanitors){
        int currentNumberOfJanitors = janitors.size();
        
        if(currentNumberOfJanitors < newNumberOfJanitors){
        //Annyi új takarítót adunk a listához, amennyit szükséges és minden létrehozást kiírunk a konzolra.
            int numberOfNewJanitors = newNumberOfJanitors - currentNumberOfJanitors;
            for(int count = 1; count <= numberOfNewJanitors; count++){
                janitors.add(new Janitor(getRandomName(), park, this));
                System.out.println("New janitor hired. Now we've got " + janitors.size() + " janitors.");
            }
        }else if (currentNumberOfJanitors > newNumberOfJanitors){
        //Annyi új takarítót adunk a listából (randomra), amennyit szükséges és minden törlést kiírunk a konzolra.
            int numberOfJanitorsToFire = currentNumberOfJanitors - newNumberOfJanitors;
            for(int count = 1; count <= numberOfJanitorsToFire; count++){
                Random rand = new Random();
                int index = rand.nextInt(janitors.size());
                janitors.remove(index);
                System.out.println("One janitor fired. Now we've got " + janitors.size() + " janitors.");
            }
        }
    }
    
    @Override
    public void update(long tickCount){
        //System.out.println(tickCount);
        spawnUpdate();
        for (int i = 0; i < visitors.size(); i++) {
            visitors.get(i).update(tickCount);
        }
        for (int i = 0; i < janitors.size(); i++) {
            janitors.get(i).update(tickCount);
        }
        if(tickCount%24==0){
            updateVisitorProbability();
            gm.getBoard().repaint();
        }
    }
}
    