package Idlethemeparkworld.model;

import Idlethemeparkworld.misc.Assets;
import Idlethemeparkworld.misc.Sound;

public class BGM extends Thread implements Runnable {
    public BGM() {
        start();
    }
    
    @Override
    public void run() {
        Sound.playSound(Assets.Sounds.BGM, true);
    }
    
}
