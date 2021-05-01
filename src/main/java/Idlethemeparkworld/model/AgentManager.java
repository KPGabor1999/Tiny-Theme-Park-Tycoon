package Idlethemeparkworld.model;

import Idlethemeparkworld.model.administration.Finance;
import Idlethemeparkworld.model.agent.Agent;
import Idlethemeparkworld.model.agent.Janitor;
import Idlethemeparkworld.model.agent.Maintainer;
import Idlethemeparkworld.model.agent.Visitor;
import java.util.ArrayList;
import java.util.Random;

/**
 * Agent manager for managing all agents(visitors, janitors and maintainers).
 * 
 * It handles keeping track, updating all agents and responsible for spawning and removal of agents.
 */
public class AgentManager implements Updatable {
    private static final String[] FIRST_NAMES = {
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
    private static final String[] LAST_NAMES = {
        "Yates",
        "Foley",
        "Greene",
        "Donaldson",
        "Lowe",
        "Moreno",
        "Lucero",
        "Fleming",
        "Ayala",
        "Melton",
        "Chang",
        "Underwood",
        "Campos",
        "Munoz",
        "Saunders",
        "Moyer",
        "Case",
        "Watkins",
        "Trevino",
        "Finley",
        "Mccarthy",
        "Massey",
        "Perry",
        "Holland",
        "Lynch",
        "Silva",
        "Ellison",
        "Wang",
        "Frederick",
        "Thomas"
    };
    
    private double visitorProbability;
    private final Park park;
    private final GameManager gm;
    
    private Random rand;
    
    private ArrayList<Visitor> visitors;
    private ArrayList<Janitor> janitors;
    private ArrayList<Maintainer> maintainers;
    
    private Visitor activeVisitor;
    
    /**
     * Creates a new agent manager for a park
     * @param park The park to manage
     * @param gm The game manager
     */
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
        this.activeVisitor = null;
        spawnVisitor();
    }
    
    /**
     * Resets the agent manager to its initial state.
     * This means removing all agents. You should call this every time a new game is started.
     */
    public void reset(){
        Visitor.resetIDCounter();
        visitorProbability = 0;
        visitors.clear();
        janitors.clear();
        maintainers.clear();
        activeVisitor = null;
        spawnVisitor();
    }
    
    private void spawnVisitor(){
        visitors.add(new Visitor(getRandomName(), getRandomHappiness(), park, this));
    }
    
    private String getRandomName(){
        return FIRST_NAMES[rand.nextInt(FIRST_NAMES.length)] + " " + LAST_NAMES[rand.nextInt(LAST_NAMES.length)];
    }
            
    private int getRandomHappiness(){
        return rand.nextInt(50)+35;
    }

    /**
     * @return The highlighted visitor. This visitor will be visible on the map.
     */
    public Visitor getActiveVisitor() {
        return activeVisitor;
    }

    /**
     * Picks a visitor and sets it to be highlighted.
     * @param activeVisitor The visitor to be highlighted
     */
    public void setActiveVisitor(Visitor activeVisitor) {
        this.activeVisitor = activeVisitor;
    }
    
    /**
     * Get the overall visitors happiness rating. Happiness rating is just an average of all currently visiting people.
     * The happiness rating is clamped down to values between 0-10.
     * @return happiness rating
     */
    public double getVisitorHappinessRating(){
        double res = 0;
        for (int i = 0; i < visitors.size(); i++) {
            res += visitors.get(i).getHappiness();
        }
        return res/visitors.size()/10;
    }
    
    /**
     * Gets the total amount of money spent by all visitors
     * @return 
     */
    public int getVisitorValue(){
        int res = 0;
        for (int i = 0; i < visitors.size(); i++) {
            res += visitors.get(i).getCashSpent();
        }
        return res;
    }
    
    /**
     * Updates the probabilities of how often to spawn in a visitor.
     * 
     * This relies on visitor counts, park suggested max, happiness and overall rating.
     */
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
    
    /**
     * Removes an agent from the list
     * @param agent The agent to be removed
     */
    public void removeAgent(Agent agent){
        if(agent instanceof Visitor){
            visitors.remove((Visitor)agent);
        }
    }
    
    /**
     * The update cycle that runs each tick and will spawn in a new visitor based on the current probabilities
     */
    private void spawnUpdate(){ 
        if(rand.nextInt(100)<visitorProbability){
            spawnVisitor();
        }
    }
    
    /**
     * @return the number of currently visiting people
     */
    public int getVisitorCount(){
        return visitors.size();
    }
    
    /**
     * @param id The id of the visitor
     * @return the visitor with the ID of id
     */
    public Visitor getVisitor(int id){
        Visitor res = null;
        for (int i = 0; i < visitors.size() && res == null; i++) {
            res = visitors.get(i).getID() == id ? visitors.get(i) : null;
        }
        return res;
    }
    
    /** 
     * @return list all visitors
     */
    public ArrayList<Visitor> getVisitors(){
        return visitors;
    }

    /** 
    * @return list all janitors
    */
    public ArrayList<Janitor> getJanitors() {
        return janitors;
    }

    /** 
    * @return list all maintainers
    */
    public ArrayList<Maintainer> getMaintainers() {
        return maintainers;
    }
    
    /**
     * Adjust the number of janitors to match the given amount.
     * This means adding or removing depending on the situation.
     * @param newNumberOfJanitors The new number of janitors
     */
    public void manageJanitors(int newNumberOfJanitors){
        int currentNumberOfJanitors = janitors.size();
        
        if(currentNumberOfJanitors < newNumberOfJanitors){
            int numberOfNewJanitors = newNumberOfJanitors - currentNumberOfJanitors;
            for(int count = 1; count <= numberOfNewJanitors; count++){
                janitors.add(new Janitor(getRandomName(), park, this));
            }
        }else if (currentNumberOfJanitors > newNumberOfJanitors){
            int numberOfJanitorsToFire = currentNumberOfJanitors - newNumberOfJanitors;
            for(int count = 1; count <= numberOfJanitorsToFire; count++){
                int index = rand.nextInt(janitors.size());
                janitors.remove(index);
            }
        }
    }
    
    /**
     * Adjust the number of maintainers to match the given amount.
     * This means adding or removing depending on the situation.
     * @param newNumberOfMaintainers The new number of maintainers
     */
    public void manageMaintainers(int newNumberOfMaintainers){
        int currentNumberOfMaintainers = maintainers.size();
        
        if(currentNumberOfMaintainers < newNumberOfMaintainers){
            int numberOfNewMaintainers = newNumberOfMaintainers - currentNumberOfMaintainers;
            for(int count = 1; count <= numberOfNewMaintainers; count++){
                maintainers.add(new Maintainer(getRandomName(), park, this));
            }
        }else if (currentNumberOfMaintainers > newNumberOfMaintainers){
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
                gm.getFinance().pay(janitors.get(i).getSalary(), Finance.FinanceType.SALARY);
            }
        }
        for (int i = 0; i < maintainers.size(); i++) {
            maintainers.get(i).update(tickCount);
            if(gm.getTime().getTotalMinutes() % 60 == 0){
                gm.getFinance().pay(maintainers.get(i).getSalary(), Finance.FinanceType.SALARY);
            }
        }
        if(tickCount%24==0){
            updateVisitorProbability();
        }
    }
}
    