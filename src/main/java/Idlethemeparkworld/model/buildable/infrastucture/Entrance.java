package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.misc.Assets;
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
        this.sound = Assets.Sounds.CROWD_AMBIANCE;
    }

    /**
     * A bej�rat +5 ember jelenl�t�t enged�lyezi a parkban.
     * @return 
     */
    @Override
    public int getRecommendedMax() {
        return (status == BuildingStatus.OPEN || status == BuildingStatus.OPEN) ? 5 : 0;
    }

    /**
     * �j l�togat�k bet�tele a parkba.
     * @param visitor 
     */
    public void enterPark(Visitor visitor) {
        if (visitor.canPay(value)) {
            visitor.pay(gm.getEntranceFee());
            gm.getFinance().earn(gm.getEntranceFee(), Finance.FinanceType.ENTRY_FEE);
        }
    }
}
