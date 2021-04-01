package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.agent.Visitor;

public class Entrance extends Infrastructure {
    public Entrance(int x, int y, GameManager gm) {
        super(gm);
        this.maxLevel = 0;
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.ENTRANCE;
    }
    
    @Override
    public int getRecommendedMax(){
        return 5;
    }
    
    public void enterPark(Visitor visitor){
        if(visitor.canPay(value)){
            visitor.pay(gm.getEntranceFee());
            gm.getFinance().earn(gm.getEntranceFee());
        }
    }
}
