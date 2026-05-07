package components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ModernScrollBarUI extends BasicScrollBarUI {
    private Color thumbColor;
    private Color trackColor;

    public ModernScrollBarUI(Color ThumbColor, Color TrackColor){
        this.thumbColor = ThumbColor;
        this.trackColor = TrackColor;
    }

    public ModernScrollBarUI(){
        this.thumbColor = new Color(205, 205, 205);
        this.trackColor = new Color(240, 240, 240);
    }

    @Override
    protected void configureScrollBarColors() {
        this.maximumThumbSize = new Dimension(0,100);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(thumbColor);
        g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        g.setColor(trackColor);
        g.fillRect(r.x, r.y, r.width, r.height);
    }
}