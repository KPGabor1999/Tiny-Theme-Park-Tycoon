package Idlethemeparkworld.model;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BGM extends Thread implements Runnable {
    private String fileName;

    public BGM(String fileName) {
        this.fileName = fileName;
        start();
    }
    
    @Override
    public void run() {
        File file = new File("C:\\Users\\KrazyXL\\idle-theme-park-world\\src\\main\\resources\\resources\\sounds\\" + fileName);
        
        AudioInputStream audioIn;
        try {
            audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip;
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException ex) {
            System.err.println("A megadott hangfájl nem támogatott!");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("IOException!");
        } catch (LineUnavailableException ex) {
            System.err.println("LineUnavailableException!");
        }
    }
    
}
