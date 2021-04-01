package Idlethemeparkworld.model.buildable.food;

public class FoodItem {
    public final int hunger;
    public final int thirst;
    public final int consumeTime;
    public final boolean empty;
    
    
    public FoodItem(){
        this.hunger = 0;
        this.thirst = 0;
        this.consumeTime = 0;
        this.empty = true;
    }

    public FoodItem(int hunger, int thirst, int consumeTime) {
        this.hunger = hunger;
        this.thirst = thirst;
        this.consumeTime = consumeTime;
        this.empty = false;
    }
}
