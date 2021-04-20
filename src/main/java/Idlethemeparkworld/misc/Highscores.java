package Idlethemeparkworld.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Highscores {

    private int maxscores;
    private ArrayList<Highscore> highscores;
    private boolean ascending;
    private String filename;

    public Highscores(int maxscores, boolean ascending, String filename) {
        this.maxscores = maxscores;
        this.highscores = new ArrayList<>();
        this.ascending = ascending;
        this.filename = filename;
        readHighscores();
        saveHighscores();
    }

    private void readHighscores() {
        highscores.clear();
        try {
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split("\t");
                highscores.add(new Highscore(data[0], Integer.parseInt(data[1])));
            }
        } catch (IOException e) {

        }
    }

    public ArrayList<Highscore> getHighscores() {
        return highscores;
    }
    
    public void reset() {
        highscores.clear();
        saveHighscores();
    }

    public void putHighscore(String name, int score) {
        if (highscores.size() < maxscores) {
        insertScore(name, score);
        } else {
            int worstScore = highscores.get(highscores.size() - 1).getScore();
            if(ascending){
                if (worstScore > score) {
                    highscores.remove(highscores.size() - 1);
                    insertScore(name, score);
                }
            } else {
                if (worstScore < score) {
                    highscores.remove(highscores.size() - 1);
                    insertScore(name, score);
                }
            }
        }
    }

    private void sortHighscores() {
        if(ascending){
            Collections.sort(highscores, (Highscore t, Highscore t1) -> t.getScore() - t1.getScore());
        } else {
            Collections.sort(highscores, (Highscore t, Highscore t1) -> t1.getScore() - t.getScore());
        }
    }

    private void insertScore(String name, int score) {
        highscores.add(new Highscore(name, score));
        saveHighscores();
    }
    
    private void saveHighscores(){
        sortHighscores();

        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(filename, false));
            for (int i = 0; i < highscores.size(); i++) {
                Highscore sc = highscores.get(i);
                writer.append(sc.getName() + "\t" + String.valueOf(sc.getScore()) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot open the file");
        }
    }
}
