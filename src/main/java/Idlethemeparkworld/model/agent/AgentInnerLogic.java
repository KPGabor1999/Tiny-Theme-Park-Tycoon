package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.buildable.Building;
import java.util.Objects;

public class AgentInnerLogic {
    public enum AgentThoughts {
        CANTAFFORD,   // "I can't afford"
        LOWMONEY,         // "I'm running out of cash!"
        NOMONEY,       // "I've spent all my money"
        //SICK,             // "I feel sick"
        WANTTHRILL,    // "I want to go on something more thrilling than X"
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
        //Scenery,         // "Great scenery!"
        
        MAP,
        
        /*DRINK,
        BURGER,
        CHIPS,
        ICECREAM,
        SHAVEDICE,
        SANDWICH,
        FISHCHIP,
        HOTDOG,*/

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
        
        //READMAP,
        //TAKEPHOTO,
        //CLAP,

        NONE
    }
}
