package Idlethemeparkworld.model;

import Idlethemeparkworld.model.agent.Agent;
import Idlethemeparkworld.model.agent.Janitor;
import Idlethemeparkworld.model.agent.Maintainer;
import Idlethemeparkworld.model.agent.Visitor;
import java.util.ArrayList;
import java.util.Random;

public class AgentManager implements Updatable {
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
    
    private double visitorProbability;
    private final Park park;
    private final GameManager gm;
    
    private Random rand;
    
    private ArrayList<Visitor> visitors;
    private ArrayList<Janitor> janitors;
    private ArrayList<Maintainer> maintainers;
    
    public AgentManager(Park park, GameManager gm){
        this.gm = gm;
        this.park = park;
        init();
    }
    
    private void init(){
        this.visitorProbability = 0;
        this.rand = new Random();
        this.visitors = new ArrayList<>();
        this.janitors = new ArrayList<>(5);
        this.maintainers = new ArrayList<>(5);
        spawnVisitor();
    }
    
    public void reset(){
        this.visitorProbability = 0;
        this.visitors.clear();
        this.janitors.clear();
        this.maintainers.clear();
        spawnVisitor();
    }
    
    private void spawnVisitor(){
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
    
    private void updateVisitorProbability(){
        double prob = park.getRating()*5;
        
        int visitorCount = visitors.size();
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
    }
    
    public void removeAgent(Agent agent){
        if(agent instanceof Visitor){
            visitors.remove((Visitor)agent);
        } else if(agent instanceof Janitor){
            janitors.remove((Janitor)agent);
        }
    }
    
    private void spawnUpdate(){ 
        if(rand.nextInt(100)<visitorProbability){
            spawnVisitor();
        }
    }
    
    public int getVisitorCount(){
        return visitors.size();
    }
    
    public ArrayList<Visitor> getVisitors(){
        return visitors;
    }

    public ArrayList<Janitor> getJanitors() {
        return janitors;
    }

    public ArrayList<Maintainer> getMaintainers() {
        return maintainers;
    }
    
    public void manageJanitors(int newNumberOfJanitors){
        int currentNumberOfJanitors = janitors.size();
        
        if(currentNumberOfJanitors < newNumberOfJanitors){
        //Annyi új takarítót adunk a listához, amennyit szükséges és minden létrehozást kiírunk a konzolra.
            int numberOfNewJanitors = newNumberOfJanitors - currentNumberOfJanitors;
            for(int count = 1; count <= numberOfNewJanitors; count++){
                janitors.add(new Janitor(getRandomName(), park, this));
            }
        }else if (currentNumberOfJanitors > newNumberOfJanitors){
        //Annyi új takarítót adunk a listából (randomra), amennyit szükséges és minden törlést kiírunk a konzolra.
            int numberOfJanitorsToFire = currentNumberOfJanitors - newNumberOfJanitors;
            for(int count = 1; count <= numberOfJanitorsToFire; count++){
                int index = rand.nextInt(janitors.size());
                janitors.remove(index);
            }
        }
    }
    
    public void manageMaintainers(int newNumberOfMaintainers){
        int currentNumberOfMaintainers = maintainers.size();
        
        if(currentNumberOfMaintainers < newNumberOfMaintainers){
        //Annyi új karbantartót adunk a listához, amennyit szükséges és minden létrehozást kiírunk a konzolra.
            int numberOfNewMaintainers = newNumberOfMaintainers - currentNumberOfMaintainers;
            for(int count = 1; count <= numberOfNewMaintainers; count++){
                maintainers.add(new Maintainer(getRandomName(), park, this));
            }
        }else if (currentNumberOfMaintainers > newNumberOfMaintainers){
        //Annyi új takarítót adunk a listából (randomra), amennyit szükséges és minden törlést kiírunk a konzolra.
            int numberOfMaintainersToFire = currentNumberOfMaintainers - newNumberOfMaintainers;
            for(int count = 1; count <= numberOfMaintainersToFire; count++){
                int index = rand.nextInt(maintainers.size());
                maintainers.remove(index);
            }
        }
    }
    
    @Override
    public void update(long tickCount){
        spawnUpdate();
        for (int i = 0; i < visitors.size(); i++) {
            visitors.get(i).update(tickCount);
        }
        for (int i = 0; i < janitors.size(); i++) {
            janitors.get(i).update(tickCount);
            if(gm.getTime().getTotalMinutes() % 60 == 0){
                gm.getFinance().pay(janitors.get(i).getSalary());
            }
        }
        for (int i = 0; i < maintainers.size(); i++) {
            maintainers.get(i).update(tickCount);
            if(gm.getTime().getTotalMinutes() % 60 == 0){
                gm.getFinance().pay(maintainers.get(i).getSalary());
            }
        }
        if(tickCount%24==0){
            updateVisitorProbability();
        }
    }
}
    