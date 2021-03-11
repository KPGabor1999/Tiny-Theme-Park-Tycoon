package Idlethemeparkworld.model.administration;

public class Finance {
    private static final int HISTORY_SIZE = 15;
    
    private int initialFunds;
    private int funds;
    
    //Loan system maybe? 
    private int bankLoan;
    private int bankLoanInterestRate;
    private int maxBankLoan;
    
    private int currentExpenditure;
    private int currentProfit;

    private int historicalProfit;
    private int weeklyProfitAverageDividend;
    private int weeklyProfitAverageDivisor;

    private int[] fundsHistory;
    private int[] WeeklyProfitHistory;
    private int[] ParkValueHistory;
    //Adjust expenditure table to store the type of expenditure
    private int[] ExpenditureTable;

    public Finance() {
        this(0);
    }
    
    public Finance(int initialFunds) {
        this.initialFunds = initialFunds;
        init();
    }
    
    public void init(){
        this.funds = initialFunds;
        
        this.bankLoan = 0;
        this.bankLoanInterestRate = 5;
        this.maxBankLoan = 0;
        
        this.currentExpenditure = 0;
        this.currentProfit = 0;
        this.historicalProfit = 0;
        this.weeklyProfitAverageDividend = 0;
        this.weeklyProfitAverageDivisor = 0;
        
        this.fundsHistory = new int[HISTORY_SIZE];
        this.WeeklyProfitHistory = new int[HISTORY_SIZE];
        this.ParkValueHistory = new int[HISTORY_SIZE];
        this.ExpenditureTable = new int[HISTORY_SIZE];
    }

    public boolean canAfford(int cost){
        return cost<=funds;
    }
    
    //add expenditure type
    public void pay(int cost){
        funds -= cost;
        currentExpenditure += cost;
    }
    
    public void paySalary(){
       //Pay out all staff members 
    }
    
    public void payUpkeep(){
       //pay default upkeep for all buildings
       //upkeep depends on size, upgrade level and whether its closed or open
    }
    
    public void updateDailyProfit(){
        currentProfit = 10*currentExpenditure;
        currentExpenditure = 0;
        
        //Substract staff salaries
        //Substract loan costs
        //Substract upkeep costs
        //Substract staff salaries
    }
    
    public int getInitialFunds(){
        return initialFunds;
    }
    
    public int getLoan(){
        return bankLoan;
    }
    
    public int maxLoan(){
        return maxBankLoan;
    }
    
    public int getFunds(){
        return funds;
    }
    
    public void resetFunds(){
        funds = initialFunds;
    }

    @Override
    public String toString() {
        return "Funds: " + funds;
    }
}
