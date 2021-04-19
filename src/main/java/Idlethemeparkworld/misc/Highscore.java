package Idlethemeparkworld.misc;

public class Highscore {

    private final String name;
    private final int score;

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
