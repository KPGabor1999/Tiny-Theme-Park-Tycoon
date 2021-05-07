package Idlethemeparkworld.model.administration;

import Idlethemeparkworld.misc.utils.Range;
import Idlethemeparkworld.model.Time;
import Idlethemeparkworld.model.Updatable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Finance implements Updatable {
    
    public enum FinanceType {
        RIDE_SELL("Attraction sales"),
        FOOD_SELL("Food stall sales"),
        ENTRY_FEE("Entry fees"),
        DEMOLISH_BONUS("Demolish returns"),
        BONUS("Bonuses"),
        SALARY("Salaries"),
        UPKEEP("Upkeep costs"),
        BUILDING("Building costs"),
        UPGRADE("Upgrade costs"),
        LOANED("Loaned bonus"),
        LOAN_PAYBACK("Loan payback costs");
        
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
    
    private static final Range dayDurationRange = new Range(5,20);
    private int principal;
    private int calculatedPay;
    private int dailyFee;
    private double interestRate;
    private int paybackDuration;
    private int dayProgress;

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
        
        principal = -1;
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

    public static Range getDayDurationRange() {
        return dayDurationRange;
    }

    public int getPrincipal() {
        return principal;
    }

    public int getCalculatedPay() {
        return calculatedPay;
    }

    public int getDailyFee() {
        return dailyFee;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getPaybackDuration() {
        return paybackDuration;
    }

    public int getDayProgress() {
        return dayProgress;
    }
    
    private Range getLoanRange(){
        Range range = new Range(0,0);
        range.setRange(Math.max((int)Math.floor(funds*0.1), 10000),
                        Math.min((int)Math.floor(funds), 250000));
        return range;
    }
    
    public double getInterestRate(int loanAmount, int dayDuration){
        Range range = getLoanRange();
        int normalizedRange = range.getHigh()-range.getLow();
        int normalizedLoan = loanAmount-range.getLow();
        double rate = 1.05;
        rate += 0.25*normalizedLoan/normalizedRange;
        rate += 0.15*(dayDuration-dayDurationRange.getLow())/(dayDurationRange.getHigh()-dayDurationRange.getLow());
        return rate;
    }
    
    public boolean getLoan(int amount, int dayDuration ){
        Range range = getLoanRange();
        if(range.inRange(amount)){
            interestRate = getInterestRate(amount, dayDuration);
            principal = amount;
            calculatedPay = (int)Math.floor(interestRate*5*principal);
            dailyFee = Math.floorDiv(calculatedPay, dayDuration);
            paybackDuration = dayDuration;
            earn(amount, FinanceType.LOANED);
            dayProgress = 0;
            return true;
        } else {
            return false;
        }
    }
    
    public void paybackLoan(){
        pay(dailyFee, FinanceType.LOAN_PAYBACK);
        dayProgress++;
        if(paybackDuration <= dayProgress){
            principal = -1;
        }
    }
    
    @Override
    public void update(long tickCount) {
        if (tickCount % Time.convMinuteToTick(10) == 0) {
            if(principal != -1){
                paybackLoan();
            }
        }
    }
    
    @Override
    public String toString() {
        return "Funds: " + funds + " $";
    }
}
