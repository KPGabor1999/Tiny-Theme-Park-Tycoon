package Idlethemeparkworld.misc;

import Idlethemeparkworld.misc.Assets.Sounds;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private static double volume = 0.1;

    public static Clip playSound(Sounds sound, boolean continuous){
        URL fileURL = sound.getSoundFile();
        
        AudioInputStream audioIn;
        Clip clip = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(fileURL);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
            gain.setValue(20f * (float) Math.log10(volume));
            if(continuous){
                clip.loop(LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file!");
        } catch (IOException e) {
            System.err.println("File not found!");
        } catch (LineUnavailableException e) {
            System.err.println("Line parsing error!");
        }
        return clip;
    }
}
