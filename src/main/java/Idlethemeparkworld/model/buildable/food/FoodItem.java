package Idlethemeparkworld.model.buildable.food;

public class FoodItem {

    public final int cost;
    public final int hunger;
    public final int thirst;
    public final int consumeTime;
    public final boolean empty;

    public FoodItem() {
        this.cost = 0;
        this.hunger = 0;
        this.thirst = 0;
        this.consumeTime = 0;
        this.empty = true;
    }

    public FoodItem(int hunger, int thirst, int consumeTime, int cost) {
        this.hunger = hunger;
        this.thirst = thirst;
        this.consumeTime = consumeTime;
        this.empty = false;
        this.cost = cost;
    }
}
