package components;

import javax.swing.*;
import java.awt.*;

public class SmoothImage {
    public static ImageIcon getResizedImage(String path, int width, int height){
        ImageIcon image = new ImageIcon(
                new ImageIcon(path).getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH)
        );
        return image;
    }
}