package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.buildable.Building;

public class AgentInnerLogic {
    public class AgentThought {
        public void AgentThought(){
            this.thoughtType = AgentThoughts.NONE;
            this.subject = null;
            this.timeElapsed = 0;
        }
        
        public AgentThoughts thoughtType;
        public Building subject;
        public int timeElapsed;
    }
    
    public enum AgentThoughts {
        CANTAFFORD,   // "I can't afford"
        LOWCASH,         // "I'm running out of cash!"
        NOMONEY,       // "I've spent all my money"
        SICK,             // "I feel sick"
        WANTTHRILL,    // "I want to go on something more thrilling than X"
        TOOINTENSE,          // "X looks too intense for me"
        BADVALUE,         // "I'm not paying that much to go on X"
        GOODVALUE,           // "X is really good value"
        
        GOHOME,          // "I want to go home"
        LOST,            // "I'm lost!"
        FEELINGGREAT,        // "X was great"
        LONGQUEUE,     // "I've been queuing for X for ages"
        TIRED,           // "I'm tired"
        HUNGRY,          // "I'm hungry"
        NOTHUNGRY,       // "I'm not hungry"
        THIRSTY,         // "I'm thirsty"
        NOTTHIRSTY,      // "I'm not thirsty"
        TOILET,          // "I need to go to the toilet"
        
        TOOMUCHLITTER,       // "The litter here is really bad"
        CLEAN,       // "This park is very clean and tidy"
        
        CROWDED,         // "It's too crowded here"
        VANDALISM,       // "The vandalism here is really bad"
        //Scenery,         // "Great scenery!"
        
        MAP,
        
        DRINK,
        BURGER,
        CHIPS,
        ICECREAM,
        SHAVEDICE,
        SANDWICH,
        FISHCHIP,
        HOTDOG,

        WOW, // "Wow!"

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
        
        FIXING,
        CLEANING,
        
        ENTERINGPARK,
        LEAVINGPARK
    }
    
    public class AgentAction{
        private AgentActionType action;
        private int creationTime;

        public AgentAction(AgentActionType action, int creationTime) {
            this.action = action;
            this.creationTime = creationTime;
        }

        public AgentActionType getAction() {
            return action;
        }

        public int getCreationTime() {
            return creationTime;
        }
    }
    
    public enum AgentActionType {
        EAT,
        SIT,
        WANDER,
        RIDE,
        TOILET,
        THROWUP,

        LEAVEPARK,
        
        STAFFREPAIR,
        STAFFCLEAN,
        
        //READMAP,
        //TAKEPHOTO,
        //CLAP,

        NONE
    }
}
