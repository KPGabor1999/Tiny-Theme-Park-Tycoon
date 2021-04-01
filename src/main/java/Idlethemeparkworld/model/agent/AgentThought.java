package Idlethemeparkworld.model.agent;

import Idlethemeparkworld.model.buildable.Building;
import java.util.Objects;

public class AgentThought {
    public AgentThought(){
        this.thoughtType = AgentInnerLogic.AgentThoughts.NONE;
        this.subject = null;
        this.timeCreated = 0;
    }

    public AgentThought(AgentInnerLogic.AgentThoughts thoughtType, Building subject, long time) {
        this.thoughtType = thoughtType;
        this.subject = subject;
        this.timeCreated = time;
    }

    public AgentInnerLogic.AgentThoughts thoughtType;
    public Building subject;
    public long timeCreated;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.thoughtType);
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
        final AgentThought other = (AgentThought) obj;
        if (this.thoughtType != other.thoughtType) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AgentThought{" + thoughtType + '}';
    }
}
