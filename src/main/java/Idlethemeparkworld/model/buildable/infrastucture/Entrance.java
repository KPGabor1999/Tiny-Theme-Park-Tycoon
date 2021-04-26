package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.administration.Finance;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.BuildingStatus;

public class Entrance extends Infrastructure {

    public Entrance(int x, int y, GameManager gm) {
        super(gm);
        this.maxLevel = 0;
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.ENTRANCE;
        this.soundFileName = "crowd_ambience.wav";
    }

    @Override
    public int getRecommendedMax() {
        return (status == BuildingStatus.OPEN || status == BuildingStatus.OPEN) ? 5 : 0;
    }

    public void enterPark(Visitor visitor) {
        if (visitor.canPay(value)) {
            visitor.pay(gm.getEntranceFee());
            gm.getFinance().earn(gm.getEntranceFee(), Finance.FinanceType.ENTRY_FEE);
        }
    }
}
