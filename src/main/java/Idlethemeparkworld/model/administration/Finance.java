package Idlethemeparkworld.model.administration;

import java.util.HashMap;

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

    HashMap<FinanceType, Integer> financialData;
    
    public Finance() {
        this(0);
    }

    public Finance(int initialFunds) {
        this.initialFunds = initialFunds;
        init();
    }

    public void init() {
        this.funds = initialFunds;

        this.financialData = new HashMap<>();
        for (FinanceType type : FinanceType.values()) {
            financialData.put(type, 0);
        }
    }
    
    public void resetFinanceData(){
        for (FinanceType type : FinanceType.values()) {
            financialData.put(type, 0);
        }
    }
    
    public HashMap<FinanceType, Integer> getFinanceData(){
        return financialData;
    }

    public boolean canAfford(int cost) {
        return cost <= funds;
    }

    public void pay(int cost, FinanceType type) {
        funds -= cost;
        financialData.put(type, financialData.getOrDefault(type, 0)+cost);
    }

    public void earn(int amount, FinanceType type) {
        funds += amount;
        financialData.put(type, financialData.getOrDefault(type, 0)+amount);
    }

    public int getFunds() {
        return funds;
    }
    
    public String getFinancialDataString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Money: ").append(funds).append("\n\n");
        sb.append("-- Spendings --").append("\n");
        financialData.forEach((k, v) -> sb.append(k.getName()).append(": ").append(v).append("\n"));
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return "Funds: " + funds + " $";
    }
}
