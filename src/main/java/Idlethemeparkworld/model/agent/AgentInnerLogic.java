package Idlethemeparkworld.model.agent;

import java.util.Random;

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
        GOOD(new String[]{"It's pretty good", "Enjoyable!", "Very nice", "Had fun", "5/5", "Enjoyed it", "I liked it!"}),
        DECENT(new String[]{"It's alright", "It's not bad", "Decent", "4/5", "It's pretty okay"}),
        BAD(new String[]{"It's pretty underwhelming", "It's pretty bad", "I didn't like it", "It's boring", "Not good at all"}),
        GOODVALUE("It's a good bang for your buck"),
        BADVALUE("It's definitely not worth it."),
        AMAZING(new String[]{"Wow, absolutely amazing!", "11/10", "The absolute best!", "Wow, just wow!", "Simply the best"}),
        BREAKDOWN(new String[]{"There was a breakdown, awful", "No service"});
        
        private final String[] texts;
        private Random rand = new Random();
        
        private Reviews(){
            this.texts = new String[0];
        }
        
        private Reviews(String text){
            this.texts = new String[]{text};
        }
        
        private Reviews(String[] texts){
            this.texts = texts;
        }
        
        public String getReviewText(){
            return texts[rand.nextInt(texts.length)];
        }
    }
}
