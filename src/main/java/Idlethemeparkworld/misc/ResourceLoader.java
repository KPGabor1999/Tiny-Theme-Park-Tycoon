package Idlethemeparkworld.misc;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

public class ResourceLoader {
    /**
     * Loads in a recourse as an inputstream. You can use it for anything not an image, like a text file or config file.
     * 
     * Note: The files should be in the resources folder 
     * 
     * @param resName A relative path in the resources folder
     * @return an inputstream from the file
     */
    public static InputStream loadResource(String resName) {
        return ResourceLoader.class.getClassLoader().getResourceAsStream(resName);
    }

    /**
     * Loads in a recourse as an image. You can use it for any kind of image.
     * 
     * Note: The files should be in the resources folder 
     * 
     * @param resName A relative path in the resources folder
     * @return an image from the file
     */
    public static BufferedImage loadImage(String resName) throws IOException {
        URL url = ResourceLoader.class.getClassLoader().getResource(resName);
        return ImageIO.read(url);
    }
}
