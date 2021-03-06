package Idlethemeparkworld.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Highscores {
    int maxscores;
    ArrayList<Highscore> highscores;

    public Highscores(int maxscores){
        this.maxscores = maxscores;
        this.highscores = new ArrayList<>();
        readHighscores();
    }

    private void readHighscores(){
        highscores.clear();
        try{
            File f = new File("leaderboard.txt");
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split("\t");
                highscores.add(new Highscore(data[0], Integer.parseInt(data[1])));
             }
        } catch(IOException e) {
            System.err.println("Error reading leaderboard");
        }
    }
    
    public ArrayList<Highscore> getHighscores() {
        return highscores;
    }

    public void putHighscore(String name, int score) {
        if (highscores.size() < maxscores) {
            insertScore(name, score);
        } else {
            int worstScore = highscores.get(highscores.size() - 1).getScore();
            if (worstScore < score) {
                highscores.remove(highscores.size() - 1);
                insertScore(name, score);
            }
        }
    }

    private void sortHighscores() {
        Collections.sort(highscores, (Highscore t, Highscore t1) -> t1.getScore() - t.getScore());
    }

    private void insertScore(String name, int score) {
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
