package Idlethemeparkworld.model.buildable;

import Idlethemeparkworld.model.agent.AgentInnerLogic.Reviews;
import java.util.ArrayList;

public interface Reviewable {
    public void addReview(String name, Reviews review);
    
    public ArrayList<String> getReviews();
}
