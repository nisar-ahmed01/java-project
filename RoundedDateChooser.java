package components;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedDateChooser extends JDateChooser {
    private final int radius;
    private boolean focused = false;

    public RoundedDateChooser(int radius) {
        this.radius = radius;
        setDateFormatString("dd-MM-yyyy");
        setOpaque(false);
        setFont(new Font("Montserrat", Font.PLAIN, 13));
        setBackground(Color.WHITE);
        JTextField tf = (JTextField) getDateEditor().getUiComponent();
        tf.setOpaque(false);
        tf.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        tf.setFont(new Font("Montserrat", Font.PLAIN, 13));
        tf.setForeground(Color.BLACK);
        tf.setCaretColor(new Color(142,83,233));
        JButton btn = getCalendarButton();
        btn.setText("▼");
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setForeground(Color.GRAY);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(new Color(142,83,233));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(Color.GRAY);
            }
        });
        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                focused = true;
                repaint();
            }
            @Override
            public void focusLost(FocusEvent e) {
                focused = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (focused)
            g2.setColor(new Color(142,83,233));
        else
            g2.setColor(Color.LIGHT_GRAY);
        g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
        g2.dispose();
    }
}