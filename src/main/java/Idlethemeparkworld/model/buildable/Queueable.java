package Idlethemeparkworld.model.buildable;

import Idlethemeparkworld.model.agent.Visitor;

public interface Queueable {

    public void joinQueue(Visitor visitor);

    public boolean isFirstInQueue(Visitor visitor);

    public void leaveQueue(Visitor visitor);

    public boolean canService();
}
