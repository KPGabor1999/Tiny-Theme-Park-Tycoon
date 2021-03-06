package Idlethemeparkworld.misc;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.io.PrintWriter;

public class Highscores {
    int maxscores;
    ArrayList<Highscore> highscores;

    public Highscores(int maxscores){
        this.maxscores = maxscores;
        this.highscores = readHighscores();
    }

    private ArrayList<Highscore> readHighscores(){
        ArrayList<Highscore> hs = new ArrayList<>();
        return hs;
    }
    
    public ArrayList<Highscore> getHighscores() {
        return highscores;
    }

    public void putHighscore(String name, int score) {
        if (highscores.size() < maxscores) {
            insertscore(name, score);
        } else {
            int leastscore = highscores.get(highscores.size() - 1).getScore();
            if (leastscore < score) {
                highscores.remove(highscores.size() - 1);
                insertscore(name, score);
            }
        }
    }

    private void sortHighscores() {
        Collections.sort(highscores, (Highscore t, Highscore t1) -> t1.getScore() - t.getScore());
    }

    private void insertscore(String name, int score) {
        highscores.add(new Highscore(name, score));
        sortHighscores();
        
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream("leaderboard.txt", false));
            for(int i=0; i<highscores.size(); i++){
                Highscore sc = highscores.get(i);
                writer.append(sc.getName() + "\t" + String.valueOf(sc.getScore()) + "\n");
            }
            writer.close();
        } catch (IOException e){
            System.err.println("Cannot open the file");
        }
    }
}
