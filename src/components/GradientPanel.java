package components;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private Color color1;
    private Color color2;
    private String direction;
    private int radius;

    public GradientPanel(int radius, Color color1, Color color2, String direction) {
        this.color1 = color1;
        this.color2 = color2;
        this.direction = direction;
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp;
        switch (direction.toLowerCase()) {
            case "horizontal":
                gp = new GradientPaint(0, 0, color1, w, 0, color2);
                break;
            case "vertical":
                gp = new GradientPaint(0, 0, color1, 0, h, color2);
                break;
            case "diagonal":
                gp = new GradientPaint(0, 0, color1, w, h, color2);
                break;
            case "reverse-diagonal":
                gp = new GradientPaint(0, h, color1, w, 0, color2);
                break;
            default:
                gp = new GradientPaint(0, 0, color1, 0, h, color2);
        }
        g2d.setPaint(gp);
        g2d.fillRoundRect(0, 0, w, h, radius, radius);
    }
}