package Idlethemeparkworld.model.administration;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Finance {
    
    public enum FinanceType {
        RIDE_SELL("Attraction sales"),
        FOOD_SELL("Food stall sales"),
        ENTRY_FEE("Entry fees"),
        DEMOLISH_BONUS("Demolish returns"),
        BONUS("Bonuses"),
        SALARY("Salaries"),
        UPKEEP("Upkeep costs"),
        BUILDING("Building costs"),
        UPGRADE("Upgrade costs");
        
        String name;
        
        private FinanceType(String name){
            this.name = name;
        }
        
        public String getName(){
            return name;
        }
    }

    private int initialFunds;
    private int funds;

    LinkedHashMap<FinanceType, Integer> financialData;
    
    public Finance() {
        this(0);
    }

    public Finance(int initialFunds) {
        this.initialFunds = initialFunds;
        init();
    }
    
    public int getFunds() {
        return funds;
    }

    /**
     * Kezdõtõke beállítása és elkezdimérni a statisztikákat.
     */
    public void init() {
        this.funds = initialFunds;

        this.financialData = new LinkedHashMap<>();
        for (FinanceType type : FinanceType.values()) {
            financialData.put(type, 0);
        }
    }
    
    /**
     * Eddigi pénzügyi statisztikák törlése.
     */
    public void resetFinanceData(){
        for (FinanceType type : FinanceType.values()) {
            financialData.put(type, 0);
        }
    }
    
    /**
     * Eddigi pénzügyi statisztikák lekérése.
     * @return 
     */
    public HashMap<FinanceType, Integer> getFinanceData(){
        return financialData;
    }

    /**
     * Van-e elég pénzed a vásárlás összegéhez?
     * @param cost
     * @return 
     */
    public boolean canAfford(int cost) {
        return cost <= funds;
    }

    /**
     * Pénz fizetése.
     * @param cost
     * @param type 
     */
    public void pay(int cost, FinanceType type) {
        funds -= cost;
        financialData.put(type, financialData.getOrDefault(type, 0)+cost);
    }

    /**
     * Pénz megkeresése.
     * @param amount
     * @param type 
     */
    public void earn(int amount, FinanceType type) {
        funds += amount;
        financialData.put(type, financialData.getOrDefault(type, 0)+amount);
    }
    
    /**
     * Eddigi pénzügyi statisztikák lekérése szövegesen.
     * @return 
     */
    public String getFinancialDataString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Money: ").append(funds).append("\n");
        sb.append("--------").append("\n");
        financialData.forEach((k, v) -> sb.append(k.getName()).append(": ").append(v).append("\n"));
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return "Funds: " + funds + " $";
    }
}
