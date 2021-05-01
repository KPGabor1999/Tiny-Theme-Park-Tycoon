package Idlethemeparkworld.misc;

public class Highscore {

    private final String name;
    private final int score;

    /**
     * Creates a new highscore log
     * @param name Name of the player
     * @param score The highscore of the player
     */
    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Highscore{" + "name=" + name + ", score=" + score + '}';
    }
}
