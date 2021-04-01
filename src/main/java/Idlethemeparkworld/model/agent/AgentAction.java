package Idlethemeparkworld.model.agent;

import java.util.Objects;

public class AgentAction{
    private AgentInnerLogic.AgentActionType action;
    private String subject;

    public AgentAction(AgentInnerLogic.AgentActionType action, String subject) {
        this.action = action;
        this.subject = subject;
    }

    public AgentInnerLogic.AgentActionType getAction() {
        return action;
    }

    public String getCreationTime() {
        return subject;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.action);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AgentAction other = (AgentAction) obj;
        if (this.action != other.action) {
            return false;
        }
        return true;
    }
    
    
}
