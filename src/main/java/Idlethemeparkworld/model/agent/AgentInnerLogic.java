package Idlethemeparkworld.model.agent;

public class AgentInnerLogic {
    public class AgentThough {
        public AgentThoughts thoughtType;
        public int subject;
        public int timeElapsed;
    }
    
    public enum AgentThoughts {
        CANTAFFORD,   // "I can't afford"
        LOWCASH,         // "I'm running out of cash!"
        NOMONEY,       // "I've spent all my money"
        SICK,             // "I feel sick"
        EXTREMELYSICK,         // "I feel very sick"
        WANTTHRILL,    // "I want to go on something more thrilling than X"
        TOOINTENSE,          // "X looks too intense for me"
        NOTFINISHED,   // "I haven't finished my X yet"
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
        
        CANTEXIT,    // "I can't find the exit"
        GETOUT,          // "I want to get off X"
        NOTSAFE,         // "I'm not going on X - it isn't safe"
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
        SUSPICIOUS,     // "I have the strangest feeling someone is watching me"
        HELP,               // "Help! Put me down!"
        
        NEWRIDE,
        NICERIDE,            // "Wow! A new ride being built!"

        NONE;
    }
    
    public enum AgentState{
        WALKING,
        QUEUING,
        ENTERINGBUILDING,
        ONRIDE,
        LEAVINGBUILDING,
        
        HELPING,
        SITTING,
        SWEEPING,
        FIXING,
        CLEANING,
        
        PATROLLING,
        PURSUING,
        INVESTIGATING,
        
        HIDING,
        STEALING,
        
        ENTERINGPARK,
        LEAVINGPARK,

        BUYING,
        WATCHING,
        REPORTING
    }
    
    public enum AgentActionType {
        IDLE,
        WATCH,
        EAT,
        SIT,
        
        WOW,
        THROWUP,
        
        STAFFANSWER,
        STAFFCHECK,
        STAFFREPAIR,
        STAFFSWEEP,
        STAFFCLEAN,
        
        READMAP,
        TAKEPHOTO,
        CLAP,

        NONE
    }
}
