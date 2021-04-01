package Idlethemeparkworld.model.agent;

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
    }
