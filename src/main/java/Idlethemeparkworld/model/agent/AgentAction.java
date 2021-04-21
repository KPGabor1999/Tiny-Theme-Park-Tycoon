package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.buildable.Building;
import java.util.Objects;

public class AgentAction {

    private final AgentInnerLogic.AgentActionType action;
    private final Building subject;

    public AgentAction(AgentInnerLogic.AgentActionType action, Building subject) {
        this.action = action;
        this.subject = subject;
    }

    public AgentInnerLogic.AgentActionType getAction() {
        return action;
    }

    public Building getSubject() {
        return subject;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.action);
        hash = 59 * hash + Objects.hashCode(this.subject);
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
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "AgentAction{" + action + '}';
    }
}
