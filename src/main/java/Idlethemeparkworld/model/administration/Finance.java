package Idlethemeparkworld.model.administration;

public class Finance {

    private int initialFunds;
    private int funds;

    private int currentExpenditure;
    private int currentProfit;

    public Finance() {
        this(0);
    }

    public Finance(int initialFunds) {
        this.initialFunds = initialFunds;
        init();
    }

    public void init() {
        this.funds = initialFunds;


        this.currentExpenditure = 0;
        this.currentProfit = 0;
    }

    public boolean canAfford(int cost) {
        return cost <= funds;
    }

    public void pay(int cost) {
        funds -= cost;
        currentExpenditure += cost;
    }

    public void earn(int amount) {
        funds += amount;
        currentExpenditure += amount;
    }

    public void updateDailyProfit() {
        currentProfit = 10 * currentExpenditure;
        currentExpenditure = 0;

        //Substract staff salaries
        //Substract loan costs
        //Substract upkeep costs
        //Substract staff salaries
    }

    public int getInitialFunds() {
        return initialFunds;
    }

    public int getFunds() {
        return funds;
    }

    public void resetFunds() {
        funds = initialFunds;
    }

    @Override
    public String toString() {
        return "Funds: " + funds + " $";
    }
}
