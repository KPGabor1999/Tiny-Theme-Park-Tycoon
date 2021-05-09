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

    /**
     * Creates a new highscores representation table
     * @param maxscores The maximum amount of scores that can be in the leaderboards
     * @param ascending Wether the sorting is by ascending order or descending
     * @param filename The filename where the highscore table will be stored
     */
    public Highscores(int maxscores, boolean ascending, String filename) {
        this.maxscores = maxscores;
        this.highscores = new ArrayList<>();
        this.ascending = ascending;
        this.filename = filename + ".txt";
        readHighscores();
        saveHighscores();
    }

    /**
     * Loads and reads in the highscores from the specified file in the constructor
     */
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

    /**
     * @return a list of highscores presorted
     */
    public ArrayList<Highscore> getHighscores() {
        return highscores;
    }
    
    /**
     * Resets all highscores and saves it
     */
    public void reset() {
        highscores.clear();
        saveHighscores();
    }

    /**
     * Adds a new highscore in the right position and removes the extra one if the number of highscores
     * exceed the max
     * @param name The name of the user/player
     * @param score The highscore of the player
     */
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

    /**
     * Sorts the highscores based on the specified ordering in the constructor
     */
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
    
    /**
     * Saves the highscore table into the specified file given in the constructor.
     * 
     * Each highscore will be saved in a separate line in the following format: "name" \t "score"
     */
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
