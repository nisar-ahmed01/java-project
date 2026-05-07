import java.awt.Dimension;
import java.awt.Toolkit;



public class resolution{

    public static void main(String[] args) {
        // Get the default toolkit
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // Get the screen size
        Dimension screenSize = toolkit.getScreenSize();
    int width = screenSize.width;
    int height = screenSize.height;
        System.out.println(width);
        System.out.println(height);
    }
}

