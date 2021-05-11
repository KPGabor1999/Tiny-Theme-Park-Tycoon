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
    
    public enum Reviews {
        GOOD("It's pretty good"),
        DECENT("It's alright"),
        BAD("It's pretty underwhelming"),
        GOODVALUE("It's a good bang for your buck"),
        BADVALUE("It's definitely not worth it."),
        AMAZING("Wow, absolutely amazing!"),
        BREAKDOWN("There was a breakdown, awful");
        
        private final String text;
        
        private Reviews(){
            this.text = "";
        }
        
        private Reviews(String text){
            this.text = text;
        }
        
        public String getReviewText(){
            return text;
        }
    }
}
