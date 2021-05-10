package Idlethemeparkworld.model.agent;

public class AgentInnerLogic {

    public enum AgentThoughts {
        CANTAFFORD,
        LOWMONEY,
        NOMONEY,
        WANTTHRILL,
        BADVALUE,
        GOODVALUE,

        GOHOME,
        LOST,
        FEELINGGREAT,
        LONGQUEUE,
        TIRED,
        HUNGRY,
        NOTHUNGRY,
        THIRSTY,
        NOTTHIRSTY,
        TOILET,
        INJURED,

        TOOMUCHLITTER,
        CLEAN,
        CROWDED,

        MAP,
        /*DRINK,
        BURGER,
        CHIPS,
        ICECREAM,
        SHAVEDICE,
        SANDWICH,
        FISHCHIP,
        HOTDOG,*/
        WOW,

        NONE;
    }

    public enum AgentState {
        IDLE,
        WALKING,
        WANDERING,
        QUEUING,
        ONRIDE,
        BUYING,
        EATING,
        SITTING,
        SHITTING,
        FLOATING,
        FIXING,
        CLEANING,
        ENTERINGPARK,
        LEAVINGPARK
    }

    public enum AgentActionType {
        EAT,
        SIT,
        WANDER,
        RIDE,
        TOILET,
        THROWUP,
        LITTER,
        ENTERPARK,
        LEAVEPARK,
        STAFFREPAIR,
        STAFFCLEAN,

        NONE
    }
}
