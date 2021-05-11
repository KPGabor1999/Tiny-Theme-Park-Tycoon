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

/**
 * The sounds class that's responsible for playing any kind of sound defined within the assets.
 */
public class Sound {
    private static double volume = 0.5;
    private static boolean soundsDisabled = false;
    
    /**
     * Sets the output volume of all new sounds.
     * Doesn't set the bgm and other looping sounds.
     * @param vol New volume from 0-1
     */
    public static void setVolume(double vol){
        volume = vol;
    }
    
    /**
     * @return The current volume between 0-1
     */
    public static double getVolume(){
        return volume;
    }
    
    /**
     * Disables all sounds
     */
    public static void disableSound(){
        soundsDisabled = true;
    }

    /**
     * Plays a particular sound either continously or one time.
     * The list of sounds can be found in assets.
     * @param sound The sound asset
     * @param continuous Wether to play the sound continously looping or just once
     * @return A clip containing the playing sound
     */
    public static Clip playSound(Sounds sound, boolean continuous){
        if(!soundsDisabled){
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
            } catch (IllegalArgumentException e) {
                System.err.println("No line matching!");
            }
            return clip;
        }
        return null;
    }
}
