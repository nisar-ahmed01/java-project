package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RoundedTextField extends JTextField {
    private int radius;
    private String placeholder;
    private boolean focused = false;
    private Color bgColor = Color.WHITE;
    private Color borderColor = new Color(220,220,220);
    private Color focusBorderColor = Color.GRAY;
    private Color textColor = Color.BLACK;
    private Color placeholderColor = Color.GRAY;

    public RoundedTextField(String placeholder, int radius) {
        this.placeholder = placeholder;
        this.radius = radius;
        setOpaque(false);
        setForeground(textColor);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        addFocusListener(new FocusAdapter() {
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

    public RoundedTextField(String placeholder, int radius, Color bgColor, Color borderColor, Color focusBorderColor) {
        this.placeholder = placeholder;
        this.radius = radius;
        this.bgColor = bgColor;
        this.borderColor = borderColor;
        this.focusBorderColor = focusBorderColor;
        setOpaque(false);
        setForeground(textColor);
        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
        addFocusListener(new FocusAdapter() {
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
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
        g2.dispose();
        if (getText().isEmpty() && !focused) {
            Graphics2D g3 = (Graphics2D) g.create();
            g3.setColor(placeholderColor);
            g3.setFont(getFont().deriveFont(Font.PLAIN));
            g3.drawString(placeholder, 12, getHeight() / 2 + 5);
            g3.dispose();
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (focused) {
            g2.setColor(focusBorderColor);
        } else {
            g2.setColor(borderColor);
        }
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}