package components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedComboBox<E> extends JComboBox<E> {
    private final int radius;

    public RoundedComboBox(E[] items, int radius) {
        super(items);
        this.radius = radius;
        setOpaque(false);
        setFocusable(false);
        setFont(new Font("Montserrat", Font.PLAIN, 13));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setMaximumRowCount(3);
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 10));
                if (isSelected) {
                    label.setBackground(new Color(142, 83, 233));
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                }
                return label;
            }
        });
        setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton btn = new JButton("▼");
                btn.setBorder(null);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btn.setContentAreaFilled(false);
                btn.setForeground(Color.GRAY);
                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        btn.setForeground(new Color(142, 83, 233));
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        btn.setForeground(Color.GRAY);
                    }
                });
                return btn;
            }
            @Override
            protected void installListeners() {
                super.installListeners();
                if (comboBox != null) {
                    Object child = comboBox.getAccessibleContext().getAccessibleChild(0);
                    if (child instanceof JPopupMenu) {
                        JPopupMenu popup = (JPopupMenu) child;
                        Component[] components = popup.getComponents();
                        for (Component comp : components) {
                            if (comp instanceof JScrollPane) {
                                JScrollPane scrollPane = (JScrollPane) comp;
                                scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
                                scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
                                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}