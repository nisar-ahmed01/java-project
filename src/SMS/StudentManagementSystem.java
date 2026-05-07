package SMS;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.swing.border.Border;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

// Add JCalendar library or create custom date picker

// Custom Rounded Date Picker Component
class RoundedDateChooser extends JDateChooser {

    private final int radius;
    private boolean focused = false;

    public RoundedDateChooser(int radius) {

        this.radius = radius;

        setDateFormatString("dd-MM-yyyy");
        setOpaque(false);
        setFont(new Font("Montserrat", Font.PLAIN, 13));
        setBackground(Color.WHITE);

        // Inner text field
        JTextField tf =
                (JTextField) getDateEditor().getUiComponent();

        tf.setOpaque(false);
        tf.setBorder(BorderFactory.createEmptyBorder(
                5, 10, 5, 10));
        tf.setFont(new Font("Montserrat",
                Font.PLAIN, 13));
        tf.setForeground(Color.BLACK);
        tf.setCaretColor(new Color(142,83,233));

        // Calendar button
        JButton btn = getCalendarButton();

        btn.setText("▼");
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR));

        btn.setForeground(Color.GRAY);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(
                        new Color(142,83,233));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(Color.GRAY);
            }
        });

        // Focus effect
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

        Graphics2D g2 =
                (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);

        g2.fillRoundRect(
                0,0,getWidth(),getHeight(),
                radius,radius);

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (focused)
            g2.setColor(
                    new Color(142,83,233));
        else
            g2.setColor(Color.LIGHT_GRAY);

        g2.drawRoundRect(
                0,0,getWidth()-1,getHeight()-1,
                radius,radius);

        g2.dispose();
    }
}


class ModernDateChooser extends JPanel {
    private JTextField dateField;
    private JButton calendarButton;
    private JDialog calendarDialog;
    private JPanel calendarPanel;
    private JLabel monthYearLabel;
    private JButton prevMonthBtn, nextMonthBtn;
    private JPanel daysPanel;
    private Calendar calendar;
    private Date selectedDate;
    private Color purpleColor = new Color(134, 73, 255);
    private Color lightPurple = new Color(200, 180, 240);

    public ModernDateChooser() {
        setLayout(null);
        setOpaque(false);
        setSize(200, 40);

        calendar = Calendar.getInstance();
        selectedDate = new Date();

        // Date text field
        dateField = new RoundedTextField("dd/MM/yyyy", 10);
        dateField.setBounds(0, 0, 160, 40);
        dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        dateField.setForeground(Color.DARK_GRAY);
        dateField.setBackground(Color.WHITE);
        dateField.setBorder(new RoundedBorder(10, lightPurple));
        dateField.setHorizontalAlignment(JTextField.LEFT);

        // Set current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateField.setText(sdf.format(new Date()));

        // Calendar button
        calendarButton = new JButton("▼");
        calendarButton.setBounds(160, 0, 40, 40);
        calendarButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        calendarButton.setBackground(purpleColor);
        calendarButton.setForeground(Color.WHITE);
        calendarButton.setBorder(new RoundedBorder(10, purpleColor));
        calendarButton.setFocusable(false);
        calendarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calendarButton.setContentAreaFilled(false);
        calendarButton.setOpaque(true);

        // Button hover effect
        calendarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                calendarButton.setBackground(new Color(110, 60, 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                calendarButton.setBackground(purpleColor);
            }
        });

        // Show calendar on button click
        calendarButton.addActionListener(e -> showCalendarDialog());

        // Date field focus effect
        dateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                dateField.setBorder(new RoundedBorder(10, purpleColor));
            }

            @Override
            public void focusLost(FocusEvent e) {
                dateField.setBorder(new RoundedBorder(10, lightPurple));
                // Parse date when focus lost
                try {
                    Date date = (Date) sdf.parse(dateField.getText());
                    selectedDate = date;
                } catch (Exception ex) {
                    dateField.setText(sdf.format(new Date()));
                }
            }
        });

        add(dateField);
        add(calendarButton);
    }

    private void showCalendarDialog() {
        calendarDialog = new JDialog();
        calendarDialog.setTitle("Select Date");
        calendarDialog.setModal(true);
        calendarDialog.setSize(300, 320);
        calendarDialog.setLocationRelativeTo(this);
        calendarDialog.setUndecorated(true);
        calendarDialog.getContentPane().setBackground(Color.WHITE);
        ((JDialog) calendarDialog).getRootPane().setBorder(new RoundedBorder(15, purpleColor));

        // Create calendar panel
        calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBackground(Color.WHITE);
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        prevMonthBtn = new JButton("◀");
        prevMonthBtn.setFont(new Font("Montserrat", Font.BOLD, 12));
        prevMonthBtn.setBackground(Color.WHITE);
        prevMonthBtn.setForeground(purpleColor);
        prevMonthBtn.setBorder(null);
        prevMonthBtn.setFocusable(false);
        prevMonthBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        prevMonthBtn.addActionListener(e -> changeMonth(-1));

        nextMonthBtn = new JButton("▶");
        nextMonthBtn.setFont(new Font("Montserrat", Font.BOLD, 12));
        nextMonthBtn.setBackground(Color.WHITE);
        nextMonthBtn.setForeground(purpleColor);
        nextMonthBtn.setBorder(null);
        nextMonthBtn.setFocusable(false);
        nextMonthBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextMonthBtn.addActionListener(e -> changeMonth(1));

        monthYearLabel = new JLabel();
        monthYearLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
        monthYearLabel.setForeground(Color.DARK_GRAY);
        monthYearLabel.setHorizontalAlignment(JLabel.CENTER);

        headerPanel.add(prevMonthBtn, BorderLayout.WEST);
        headerPanel.add(monthYearLabel, BorderLayout.CENTER);
        headerPanel.add(nextMonthBtn, BorderLayout.EAST);

        // Days panel
        daysPanel = new JPanel(new GridLayout(0, 7, 5, 5));
        daysPanel.setBackground(Color.WHITE);

        // Day names header
        String[] dayNames = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        for (String day : dayNames) {
            JLabel dayLabel = new JLabel(day, JLabel.CENTER);
            dayLabel.setFont(new Font("Montserrat", Font.BOLD, 11));
            dayLabel.setForeground(purpleColor);
            daysPanel.add(dayLabel);
        }

        calendarPanel.add(headerPanel, BorderLayout.NORTH);
        calendarPanel.add(daysPanel, BorderLayout.CENTER);

        // Bottom panel with OK button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Montserrat", Font.BOLD, 12));
        okButton.setBackground(purpleColor);
        okButton.setForeground(Color.WHITE);
        okButton.setBorder(new RoundedBorder(8, purpleColor));
        okButton.setFocusable(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dateField.setText(sdf.format(selectedDate));
            calendarDialog.dispose();
        });

        bottomPanel.add(okButton);
        calendarPanel.add(bottomPanel, BorderLayout.SOUTH);

        updateCalendarDisplay();

        calendarDialog.add(calendarPanel);
        calendarDialog.setVisible(true);
    }

    private void updateCalendarDisplay() {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
        monthYearLabel.setText(monthFormat.format(calendar.getTime()));

        daysPanel.removeAll();

        // Add day names
        String[] dayNames = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        for (String day : dayNames) {
            JLabel dayLabel = new JLabel(day, JLabel.CENTER);
            dayLabel.setFont(new Font("Montserrat", Font.BOLD, 11));
            dayLabel.setForeground(purpleColor);
            daysPanel.add(dayLabel);
        }

        // Get first day of month
        Calendar tempCal = (Calendar) calendar.clone();
        tempCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1;

        // Add empty cells for days before month starts
        for (int i = 0; i < firstDayOfWeek; i++) {
            daysPanel.add(new JLabel(""));
        }

        // Add days of month
        int daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int day = 1; day <= daysInMonth; day++) {
            JLabel dayLabel = new JLabel(String.valueOf(day), JLabel.CENTER);
            dayLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
            dayLabel.setForeground(Color.DARK_GRAY);

            // Check if this is the selected date
            Calendar todayCal = Calendar.getInstance();
            todayCal.setTime(selectedDate);
            if (todayCal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                    todayCal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                    todayCal.get(Calendar.DAY_OF_MONTH) == day) {
                dayLabel.setBackground(purpleColor);
                dayLabel.setForeground(Color.WHITE);
                dayLabel.setOpaque(true);
                dayLabel.setBorder(new RoundedBorder(15, purpleColor));
            }

            // Add selection listener
            final int selectedDay = day;
            dayLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    selectedDate = calendar.getTime();
                    updateCalendarDisplay();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!dayLabel.getForeground().equals(Color.WHITE)) {
                        dayLabel.setBackground(lightPurple);
                        dayLabel.setOpaque(true);
                        dayLabel.setBorder(new RoundedBorder(15, lightPurple));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!dayLabel.getForeground().equals(Color.WHITE)) {
                        dayLabel.setBackground(null);
                        dayLabel.setOpaque(false);
                        dayLabel.setBorder(null);
                    }
                }
            });

            daysPanel.add(dayLabel);
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private void changeMonth(int delta) {
        calendar.add(Calendar.MONTH, delta);
        updateCalendarDisplay();
    }

    public Date getDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(dateField.getText());
        } catch (Exception e) {
            return new Date();
        }
    }

    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateField.setText(sdf.format(date));
        selectedDate = date;
        calendar.setTime(date);
    }
}

class SmoothImage {
    public static ImageIcon getResizedImage(String path,int width,int height){
        ImageIcon image = new ImageIcon(
                new ImageIcon(path).getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH)
        );
        return image;
    }
}

class CustomRadiusPanel extends JPanel {

    private int tl, tr, br, bl;

    public CustomRadiusPanel(int tl, int tr, int br, int bl) {
        this.tl = tl;
        this.tr = tr;
        this.br = br;
        this.bl = bl;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Path2D path = new Path2D.Float();

        // Start top-left
        path.moveTo(tl, 0);

        // Top edge
        path.lineTo(w - tr, 0);
        path.quadTo(w, 0, w, tr);

        // Right edge
        path.lineTo(w, h - br);
        path.quadTo(w, h, w - br, h);

        // Bottom edge
        path.lineTo(bl, h);
        path.quadTo(0, h, 0, h - bl);

        // Left edge
        path.lineTo(0, tl);
        path.quadTo(0, 0, tl, 0);

        path.closePath();

        // Fill background
        g2.setColor(getBackground());
        g2.fill(path);

        g2.dispose();

        super.paintComponent(g);
    }
}



class RoundedComboBox<E> extends JComboBox<E> {

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

        // Set maximum visible row count to 3
        setMaximumRowCount(3);

        // Custom dropdown style
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

        // Custom arrow button
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
                // Customize the popup list scroll pane
                if (comboBox != null) {
                    Object child = comboBox.getAccessibleContext().getAccessibleChild(0);
                    if (child instanceof JPopupMenu) {
                        JPopupMenu popup = (JPopupMenu) child;
                        Component[] components = popup.getComponents();
                        for (Component comp : components) {
                            if (comp instanceof JScrollPane) {
                                JScrollPane scrollPane = (JScrollPane) comp;
                                // Apply modern scroll bar UI
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





class ModernScrollBarUI extends BasicScrollBarUI {
    ModernScrollBarUI(Color ThumbColor,Color TrackColor){
        this.thumbColor = ThumbColor;
        this.trackColor = TrackColor;
    }

    ModernScrollBarUI(){
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




class AnimatedButton extends JButton {

    private int radius;
    private boolean hovered = false;
    private boolean pressed = false;

    private Color normalColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color borderColor;

    public AnimatedButton(String text, int radius,
                          Color normalColor,
                          Color hoverColor,
                          Color pressedColor,
                          Color borderColor) {

        super(text);
        this.radius = radius;
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;
        this.borderColor = borderColor;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Montserrat", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }

            public void mousePressed(MouseEvent e) {
                pressed = true;
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                pressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (pressed) {
            g2.setColor(pressedColor);
        } else if (hovered) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(normalColor);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);

        g2.dispose();
    }
}




class RoundedTextField extends JTextField {

    private int radius;
    private String placeholder;
    private boolean focused = false;

    // Custom Colors
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


    public RoundedTextField(String placeholder, int radius,Color bgColor,Color borderColor, Color focusBorderColor) {
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

        // Background
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
        g2.dispose();

        // Placeholder
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

        // Border color switch
        if (focused) {
            g2.setColor(focusBorderColor);
        } else {
            g2.setColor(borderColor);
        }

        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        g2.dispose();
    }
}



class RoundedPasswordField extends JPasswordField{

    private int radius;
    private String placeholder;
    private boolean focused = false;

    public RoundedPasswordField(String placeholder, int radius) {
        this.placeholder = placeholder;
        this.radius = radius;

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Focus effect (animated border trigger)
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

        // background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
        g2.dispose();

        // placeholder text
        if (getText().isEmpty() && !focused) {
            Graphics2D g3 = (Graphics2D) g.create();
            g3.setColor(Color.GRAY);
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

        // Animated border effect
        if (focused) {
            g2.setColor(new Color(142, 83, 233)); // purple glow
        } else {
            g2.setColor(new Color(200, 180, 240)); // normal border
        }

        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        g2.dispose();
    }
}


class RoundedBorder implements Border {
    private int radius;
    private Color borderColor;

    public RoundedBorder(int radius, Color borderColor) {
        this.radius = radius;
        this.borderColor = borderColor;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius + 1, radius + 1, radius + 2, radius + 1);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g,
                            int x, int y, int width, int height) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(borderColor);

        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);

        g2.dispose();
    }
}

class GradientPanel extends JPanel {

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

        // ✔ Correct: rounded gradient panel
        g2d.fillRoundRect(0, 0, w, h, radius, radius);
    }
}

class RoundedPanel extends JPanel {

    private int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false); // important (makes background transparent)
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}

class RoundScrollPane extends JScrollPane {

    private int radius;

    public RoundScrollPane(Component view , int radius) {
        super(view);
        this.radius = radius;

        setOpaque(false);
        getViewport().setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();

        super.paintComponent(g);
    }
}

public class StudentManagementSystem {
    private static void filterTable(String searchText, JTable table, DefaultTableModel model) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null); // Show all rows
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
        }
    }

    private static void refreshEnrollTable(DefaultTableModel enrollTableModel, String url) {
        // Clear existing rows
        enrollTableModel.setRowCount(0);

        try {
            Connection con = DriverManager.getConnection(url, "root", "Nisar123");
            Statement st = con.createStatement();
            String query = "select course_name, course_fee from NewCourse";
            ResultSet result = st.executeQuery(query);
            int j = 1;
            while (result.next()) {
                Object[] newData = {j++, result.getString("course_name"), result.getInt("course_fee") + ".00 Rs"};
                enrollTableModel.addRow(newData);
            }
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error refreshing course list: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void refreshRecentEnrollTable(DefaultTableModel recentEnrollTableModel, String url) {
        recentEnrollTableModel.setRowCount(0);
        try {
            Connection con = DriverManager.getConnection(url, "root", "Nisar123");
            Statement st = con.createStatement();

            String query = "Select * from Enrollment";
            ResultSet result = st.executeQuery(query);

            while (result.next()) {
                Object[] newData = {result.getString("stu_id"),
                        result.getString("stu_name"),
                        result.getString("course_name"),
                        result.getString("enrollment_date"),
                        result.getString("duration")};
                recentEnrollTableModel.addRow(newData);
            }
            con.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) throws Exception {

        final String url = "jdbc:mysql://localhost:3306/SMS";


        JFrame frame = new JFrame();
        JFrame dashboard = new JFrame("Student Management System");
        RoundedPanel backPanel = new RoundedPanel(20);
        JButton dashb = new JButton("  Dashboard");
        JButton student = new JButton("  Student");
        JButton course = new JButton("  Courses");
        JButton enroll = new JButton("  Enroll to Course");
        JButton attendance = new JButton("  Attendance");
        JButton report = new JButton("  Report");
        JButton logout = new JButton("  Logout");
        JLabel stuCount = new JLabel();
        JLabel courseCount = new JLabel();
        JPanel studentPanel = new JPanel();
        JPanel coursePanel = new JPanel();
        JPanel dashPanel = new JPanel();
        JPanel addNewStuPanel = new JPanel();
        JPanel addNewCoursePanel = new JPanel();
        JPanel viewStuPanel = new JPanel();
        JPanel editStuPanel = new JPanel();
        JPanel viewCoursePanel = new JPanel();
        JPanel editCoursePanel = new JPanel();
        JPanel enrollToCourse = new JPanel();
        JPanel attendancePanel = new JPanel();
        JLabel enrollCount = new JLabel();

        String[] columnNames = {"ID", "NAME", "EMAIL", "AGE", "PHONE"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        JTable table = new JTable(model);


        String[] courseColumnNames = {"ID", "COURSE NAME", "COURSE CODE", "DURATION", "COURSE FEES"};
        DefaultTableModel courseTableModel = new DefaultTableModel(null, courseColumnNames);
        JTable courseTable = new JTable(courseTableModel);

        try {
            Connection con = DriverManager.getConnection(url, "root", "Nisar123");
            Statement st = con.createStatement();
            String query = "select stu_id,firstname,lastname,email,age,phone from NewStudent";
            ResultSet resultset = st.executeQuery(query);

            while (resultset.next()) {
                String stu_id = resultset.getString("stu_id");
                String firstname = resultset.getString("firstname");
                String lastname = resultset.getString("lastname");
                String email = resultset.getString("email");
                String age = resultset.getString("age");
                String phone = resultset.getString("phone");
                Object[] newData = {stu_id, firstname + " " + lastname, email, age, phone};
                model.addRow(newData);
            }
            resultset.close();

            String query1 = "select id,course_name,course_code,duration,course_fee from NewCourse";
            ResultSet result = st.executeQuery(query1);
            while (result.next()) {
                int id = result.getInt("id");
                String course_name = result.getString("course_name");
                String course_code = result.getString("course_code");
                String duration = result.getString("duration");
                int course_fee = result.getInt("course_fee");
                Object[] newData = {id, course_name, course_code, duration, course_fee + " PKR"};
                courseTableModel.addRow(newData);
            }
            st.close();
            con.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


        String[] enrollColumnNames = {"S.No", "COURSE NAME", "FEE"};
        DefaultTableModel enrollTableModel = new DefaultTableModel(null, enrollColumnNames);
        JTable enrollTable = new JTable(enrollTableModel);
        refreshEnrollTable(enrollTableModel, url);


        String[] recentEnrollColNames = {"ID", "NAME", "COURSE", "ENROLL DATE", "DURATION"};
        DefaultTableModel recentEnrollTableModel = new DefaultTableModel(null, recentEnrollColNames);
        JTable recentEnrolledTable = new JTable(recentEnrollTableModel);
        refreshRecentEnrollTable(recentEnrollTableModel, url);


        if (recentEnrolledTable.getRowCount() < 10)
            enrollCount.setText("0" + recentEnrolledTable.getRowCount());
        else
            enrollCount.setText(Integer.toString(recentEnrolledTable.getRowCount()));

        //====================== login Panel & its components ======================
        RoundedPanel loginPanel = new RoundedPanel(30);
        loginPanel.setBackground(new Color(245, 240, 255));
        loginPanel.setMaximumSize(new Dimension(330, 345)); // because of box layout of panel1
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        //Username
        JLabel label5 = new JLabel();
        label5.setForeground(Color.BLACK);
        label5.setFont(new Font("Montserrat", Font.PLAIN, 16));
        label5.setText("Username:");
        gbc.insets = new Insets(0, 0, 3, 0);
        loginPanel.add(label5, gbc);

        //Username Textbox
        RoundedTextField username = new RoundedTextField("Enter Username", 10);
        username.setFont(new Font("Montserrat", Font.PLAIN, 13));
        username.setPreferredSize(new Dimension(250, 35));
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = 1;
        username.setCaretColor(new Color(142, 83, 233));
        loginPanel.add(username, gbc);

        //Password
        JLabel label6 = new JLabel();
        label6.setForeground(Color.BLACK);
        label6.setFont(new Font("Montserrat", Font.PLAIN, 16));
        label6.setText("Password:");
        gbc.insets = new Insets(25, 0, 3, 0);
        gbc.gridy = 2;
        loginPanel.add(label6, gbc);


        //Password textbox
        RoundedPasswordField password = new RoundedPasswordField("Enter Password", 10);
        boolean[] eye = {true};

        //Eye Button
        JButton btn = new JButton("👁️");
        btn.setContentAreaFilled(false);
        btn.setBorder(null);
        btn.setFocusable(false);
        btn.setFont(new Font("", Font.PLAIN, 18));
        btn.setForeground(new Color(200, 180, 240));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(20, 15));
        btn.setMargin(new Insets(0, 0, 0, 0));
        GridBagConstraints z = new GridBagConstraints();
        z.insets = new Insets(0, 225, 0, 25);
        btn.addActionListener(e -> {
            if (eye[0]) {
                password.setEchoChar((char) 0);
                btn.setForeground(new Color(142, 83, 233));
                eye[0] = false;
            } else {
                password.setEchoChar('•');
                btn.setForeground(new Color(200, 180, 240));
                eye[0] = true;
            }
        });


        password.setFont(new Font("Montserrat", Font.PLAIN, 13));
        password.setPreferredSize(new Dimension(250, 35));
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 3;
        password.setLayout(new GridBagLayout());
        password.setCaretColor(new Color(142, 83, 233));
        password.add(btn, z);
        loginPanel.add(password, gbc);

        //Forget Password?
        JLabel label7 = new JLabel();
        label7.setForeground(new Color(142, 83, 233));
        label7.setFont(new Font("Montserrat", Font.PLAIN, 11));
        label7.setText("Forget Password?");
        gbc.insets = new Insets(5, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 4;
        loginPanel.add(label7, gbc);

        //Login Button
        AnimatedButton Login = new AnimatedButton("Login", 40
                , new Color(142, 83, 233) //normal Color
                , new Color(160, 120, 255) //Hovered Color
                , new Color(120, 60, 210) // Pressed Color
                , new Color(160, 120, 255)); // Border Color
        Login.setPreferredSize(new Dimension(220, 35));
        gbc.insets = new Insets(30, 20, 20, 12);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 5;
        Login.addActionListener(e -> {
            try {
                String usrname = username.getText();
                String pass = password.getText();
                Connection connection = DriverManager.getConnection(url, "root", "Nisar123");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("select username,confirm_pass from reg_users");

                boolean check = true;
                if (usrname.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username field can't be empty", "Warning", JOptionPane.WARNING_MESSAGE);
                    check = false;
                } else if (pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Password field can't be empty", "Warning", JOptionPane.WARNING_MESSAGE);
                    check = false;
                }

                boolean[] temp = {true};
                if (check) {
                    while (result.next()) {
                        if (usrname.equals(result.getString("username")) && pass.equals(result.getString("confirm_pass"))) {
                            int ok = JOptionPane.showConfirmDialog(null, "Login Successful"
                                    , "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                            if (ok == 0) {
                                if (table.getRowCount() < 10)
                                    stuCount.setText("0" + table.getRowCount());
                                else
                                    stuCount.setText(Integer.toString(table.getRowCount()));

                                if (courseTable.getRowCount() < 10)
                                    courseCount.setText("0" + courseTable.getRowCount());
                                else
                                    courseCount.setText(Integer.toString(courseTable.getRowCount()));

                                if (recentEnrolledTable.getRowCount() < 10)
                                    enrollCount.setText("0" + recentEnrolledTable.getRowCount());
                                else
                                    enrollCount.setText(Integer.toString(recentEnrolledTable.getRowCount()));

                                frame.dispose();
                                studentPanel.setVisible(false);
                                addNewStuPanel.setVisible(false);
                                viewStuPanel.setVisible(false);
                                editStuPanel.setVisible(false);
                                coursePanel.setVisible(false);
                                addNewCoursePanel.setVisible(false);
                                viewCoursePanel.setVisible(false);
                                editCoursePanel.setVisible(false);
                                enrollToCourse.setVisible(false);
                                attendancePanel.setVisible(false);
                                backPanel.setBounds(15, 143, 210, 45);
                                dashb.setForeground(Color.WHITE);
                                course.setForeground(new Color(240, 239, 255));
                                student.setForeground(new Color(240, 239, 255));
                                enroll.setForeground(new Color(240, 239, 255));
                                attendance.setForeground(new Color(240, 239, 255));
                                report.setForeground(new Color(240, 239, 255));
                                logout.setForeground(new Color(240, 239, 255));
                                ImageIcon logoutLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\logout.png", 23, 23);
                                logout.setIcon(logoutLogo);
                                dashPanel.setVisible(true);
                                dashboard.setVisible(true);
                            }
                            temp[0] = false;
                        }
                    }
                    if (temp[0]) {
                        JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                    username.setText("");
                    password.setText("");
                }
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        loginPanel.add(Login, gbc);

        //===================== Login Panel Ends Here ========================


        //==================== Register Panel Components =======================
        RoundedPanel registerContentPanel = new RoundedPanel(30); //Panel above Jscrollpane
        registerContentPanel.setBackground(new Color(245, 240, 255));
        registerContentPanel.setLayout(new GridBagLayout());

        GridBagConstraints xyz = new GridBagConstraints();
        xyz.gridx = 0;
        xyz.gridy = 0;

        //Fullname
        JLabel register_fullname = new JLabel("Full Name");
        register_fullname.setForeground(Color.BLACK);
        register_fullname.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(1, 0, 7, 0);
        registerContentPanel.add(register_fullname, xyz);

        //Fullname Textbox
        RoundedTextField fullname_textbox = new RoundedTextField("Enter your name", 10);
        fullname_textbox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        fullname_textbox.setPreferredSize(new Dimension(250, 32));
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 1;
        fullname_textbox.setCaretColor(new Color(142, 83, 233));
        registerContentPanel.add(fullname_textbox, xyz);

        //Username
        JLabel register_username = new JLabel("Username");
        register_username.setForeground(Color.BLACK);
        register_username.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(0, 0, 7, 0);
        xyz.gridy = 2;
        registerContentPanel.add(register_username, xyz);

        //Username Textbox
        RoundedTextField username_textbox = new RoundedTextField("Enter a username", 10);
        username_textbox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        username_textbox.setPreferredSize(new Dimension(250, 32));
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 3;
        username_textbox.setCaretColor(new Color(142, 83, 233));
        registerContentPanel.add(username_textbox, xyz);

        //Email
        JLabel register_email = new JLabel("Email");
        register_email.setForeground(Color.BLACK);
        register_email.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(0, 0, 7, 0);
        xyz.gridy = 4;
        registerContentPanel.add(register_email, xyz);

        //Email textbox
        RoundedTextField email_textbox = new RoundedTextField("Enter a email", 10);
        email_textbox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        email_textbox.setPreferredSize(new Dimension(250, 32));
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 5;
        email_textbox.setCaretColor(new Color(142, 83, 233));
        registerContentPanel.add(email_textbox, xyz);

        //Password
        JLabel register_password = new JLabel("Password");
        register_password.setForeground(Color.BLACK);
        register_password.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(0, 0, 7, 0);
        xyz.gridy = 6;
        registerContentPanel.add(register_password, xyz);

        //Password textbox
        RoundedPasswordField password_textbox = new RoundedPasswordField("Enter a password", 10);
        password_textbox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        password_textbox.setPreferredSize(new Dimension(250, 32));
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 7;
        password_textbox.setCaretColor(new Color(142, 83, 233));
        registerContentPanel.add(password_textbox, xyz);

        //Confirm Password
        JLabel register_confirmPass = new JLabel("Confirm Password");
        register_confirmPass.setForeground(Color.BLACK);
        register_confirmPass.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(0, 0, 7, 0);
        xyz.gridy = 8;
        registerContentPanel.add(register_confirmPass, xyz);

        //Confirm Password textbox
        RoundedPasswordField confirmPass_textbox = new RoundedPasswordField("Enter confirm password", 10);
        confirmPass_textbox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        confirmPass_textbox.setPreferredSize(new Dimension(250, 32));
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 9;
        confirmPass_textbox.setCaretColor(new Color(142, 83, 233));
        registerContentPanel.add(confirmPass_textbox, xyz);

        //Role
        JLabel role = new JLabel("Role");
        role.setForeground(Color.BLACK);
        role.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(0, 0, 7, 0);
        xyz.gridy = 10;
        registerContentPanel.add(role, xyz);

        //Role textbox
        String[] roleOptions = {"Admin", "Staff"};
        RoundedComboBox roleComboBox = new RoundedComboBox(roleOptions, 8);
        roleComboBox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        roleComboBox.setPreferredSize(new Dimension(250, 32));
        roleComboBox.setFocusable(false);
        roleComboBox.setBackground(Color.white);
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 11;
        registerContentPanel.add(roleComboBox, xyz);

        //Register Button
        AnimatedButton regButton = new AnimatedButton("Register", 40
                , new Color(142, 83, 233) //normal Color
                , new Color(160, 120, 255) //Hovered Color
                , new Color(120, 60, 210) // Pressed Color
                , new Color(160, 120, 255)); // Border Color
        regButton.setPreferredSize(new Dimension(220, 35));
        xyz.insets = new Insets(10, 0, 1, 0);
        xyz.gridy = 12;
        registerContentPanel.add(regButton, xyz);
        regButton.addActionListener(e -> {
            String fullname = fullname_textbox.getText();
            String usrname = username_textbox.getText();
            String email = email_textbox.getText();
            String pas = password_textbox.getText();
            String pass = confirmPass_textbox.getText();
            String userRole = roleComboBox.getSelectedItem().toString();

            boolean[] empty = {true};
            if (fullname.isEmpty() || usrname.isEmpty() || email.isEmpty() || pas.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please fill all required fields",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                empty[0] = false;
            } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(null, "Invalid Email Format", "Error", JOptionPane.ERROR_MESSAGE);
                empty[0] = false;
            } else if (!pas.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
                JOptionPane.showMessageDialog(null
                        , "Password must contain:\n- At least 8 characters\n- 1 uppercase letter\n- 1 lowercase letter\n- 1 number\n- 1 special character"
                        , "Error", JOptionPane.ERROR_MESSAGE);
                empty[0] = false;
            } else if (!pas.equals(pass)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                empty[0] = false;
            }


            boolean[] temp = {true};
            try {
                Connection connection = DriverManager.getConnection(url, "root", "Nisar123");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("select username from reg_users");
                while (result.next()) {
                    if (usrname.equals(result.getString("username"))) {
                        JOptionPane.showMessageDialog(null, "This Username Already Exists", "Error Occur", JOptionPane.ERROR_MESSAGE);
                        temp[0] = false;
                    }
                }
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            if (temp[0] && empty[0]) {
                fullname_textbox.setText("");
                username_textbox.setText("");
                email_textbox.setText("");
                password_textbox.setText("");
                confirmPass_textbox.setText("");
                roleComboBox.setSelectedIndex(0);

                String query = String.format("Insert into reg_users(fullname,username,email,confirm_pass,role) values('%s','%s','%s','%s','%s')"
                        , fullname, usrname, email, pass, userRole);
                try {
                    Connection connection = DriverManager.getConnection(url, "root", "Nisar123");
                    Statement statement = connection.createStatement();
                    int resultAffect = statement.executeUpdate(query);
                    if (resultAffect > 0) {
                        JOptionPane.showMessageDialog(null, "Data Inserted Successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Data not Inserted", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                    connection.close();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

        //======================= REGISTER PANEL ENDS HERE =======================

        //Because these are used in login and register button
        RoundedPanel mainPanel = new RoundedPanel(30);
        RoundScrollPane registerPanel = new RoundScrollPane(registerContentPanel, 30); //register panel


        //login and register button of small panel
        JButton login = new JButton("Login");
        JButton register = new JButton("Register");

        login.setForeground(new Color(142, 83, 233));
        login.setFont(new Font("Montserrat", Font.BOLD, 11));
        login.setMargin(new Insets(0, 0, 0, 0));
        login.setContentAreaFilled(false);
        login.setBorderPainted(false);
        login.setFocusable(false);
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginPanel.setVisible(true);
                registerPanel.setVisible(false);
                login.setForeground(new Color(142, 83, 233));
                register.setForeground(new Color(38, 38, 38));
            }
        });


        register.setForeground(new Color(38, 38, 38));
        register.setFont(new Font("Montserrat", Font.BOLD, 11));
        register.setContentAreaFilled(false); //background transparent
        register.setBorderPainted(false);
        register.setFocusable(false);
        register.setCursor(new Cursor(Cursor.HAND_CURSOR));
        register.setMargin(new Insets(0, 0, 0, 0)); //Margin inside button
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                loginPanel.setVisible(false);
                registerPanel.setVisible(true);
                login.setForeground(new Color(38, 38, 38));
                register.setForeground(new Color(142, 83, 233));
            }
        });


        RoundedPanel smallPanel = new RoundedPanel(10);
        smallPanel.setBackground(new Color(235, 225, 255));
        smallPanel.setMaximumSize(new Dimension(120, 30)); // because of box layout of panel1
        smallPanel.setLayout(new GridBagLayout());
        smallPanel.add(login);
        GridBagConstraints a = new GridBagConstraints();
        a.insets = new Insets(0, 6, 0, 0); // for left margin of button
        smallPanel.add(register, a);


        registerPanel.setBackground(new Color(245, 240, 255));
        registerPanel.setMaximumSize(new Dimension(330, 345));
        registerPanel.setMinimumSize(new Dimension(330, 345));
        registerPanel.setPreferredSize(new Dimension(330, 345));
        registerPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        registerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        registerPanel.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        registerPanel.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        registerPanel.setVisible(false);


        //Student Management System
        JLabel SMS = new JLabel();
        SMS.setForeground(new Color(43, 45, 47));
        SMS.setFont(new Font("Aptos Black", Font.BOLD, 21));
        SMS.setText("STUDENT MANAGEMENT SYSTEM");
        SMS.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Secure Login Portal
        JLabel SLP = new JLabel();
        SLP.setForeground(new Color(43, 45, 47));
        SLP.setFont(new Font("Montserrat", Font.PLAIN, 14));
        SLP.setText("Secure Login Portal");
        SLP.setAlignmentX(Component.CENTER_ALIGNMENT);


        mainPanel.setBackground(Color.white);
        mainPanel.setPreferredSize(new Dimension(400, 500)); // for grid layout in frame
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); //because we want labels in new rows
        mainPanel.add(Box.createVerticalStrut(28)); // vertical box space between text
        mainPanel.add(SMS);
        mainPanel.add(SLP);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(smallPanel);
        mainPanel.add(Box.createVerticalStrut(-15));
        mainPanel.add(loginPanel);
        mainPanel.add(registerPanel);

        ImageIcon icon = new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\sms.png");

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new GridBagLayout());
        frame.setTitle("Student Management System");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(142, 83, 233));
        frame.setIconImage(icon.getImage());
        frame.add(mainPanel);
        frame.setVisible(true);


        //=================== DASHBOARD =================

        //================= Side Panel and Its components ===================
        ImageIcon sidePanelLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\SMS.png", 45, 45);

        JLabel sms = new JLabel("SMS");
        sms.setFont(new Font("Montserrat", Font.BOLD, 42));
        sms.setForeground(Color.WHITE);
        sms.setBounds(0, 30, 240, 50);
        sms.setHorizontalAlignment(JLabel.CENTER);
        sms.setIcon(sidePanelLogo);

        JLabel ms = new JLabel("MANAGEMENT SYSTEM");
        ms.setFont(new Font("Montserrat", Font.PLAIN, 12));
        ms.setForeground(Color.WHITE);
        ms.setBounds(0, 65, 240, 50);
        ms.setHorizontalAlignment(JLabel.CENTER);

        JLabel line1 = new JLabel("_________________________________________");
        line1.setFont(new Font("Montserrat", Font.PLAIN, 12));
        line1.setForeground(new Color(210, 210, 210));
        line1.setBounds(0, 100, 250, 37);
        line1.setHorizontalAlignment(JLabel.CENTER);
        line1.setVerticalAlignment(JLabel.BOTTOM);


        backPanel.setBounds(15, 143, 210, 45);
        backPanel.setBackground(new Color(104, 45, 236));
        backPanel.setLayout(null);

        RoundedPanel duplicateBackPanel = new RoundedPanel(20);
        duplicateBackPanel.setBounds(15, 143, 210, 45);
        duplicateBackPanel.setBackground(new Color(104, 45, 236));
        duplicateBackPanel.setVisible(false);


        ImageIcon dashLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\dashboard.png", 23, 23);
        dashb.setFont(new Font("Montserrat", Font.PLAIN, 15));
        dashb.setForeground(Color.WHITE);
        dashb.setBounds(35, 140, 175, 50);
        dashb.setHorizontalAlignment(JButton.LEFT);
        dashb.setBorder(null);
        dashb.setContentAreaFilled(false);
        dashb.setFocusable(false);
        dashb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dashb.setIcon(dashLogo);
        dashb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                duplicateBackPanel.setVisible(true);
                duplicateBackPanel.setBounds(15, 143, 210, 45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236, 150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }
        });

        ImageIcon studentLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\stu.png", 23, 23);
        student.setFont(new Font("Montserrat", Font.PLAIN, 15));
        student.setForeground(new Color(145, 145, 145));
        student.setBounds(35, 190, 175, 50);
        student.setHorizontalAlignment(JButton.LEFT);
        student.setBorder(null);
        student.setContentAreaFilled(false);
        student.setFocusable(false);
        student.setCursor(new Cursor(Cursor.HAND_CURSOR));
        student.setIcon(studentLogo);
        student.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                duplicateBackPanel.setVisible(true);
                duplicateBackPanel.setBounds(15, 193, 210, 45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236, 150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }
        });


        ImageIcon courseLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\course.png", 23, 23);
        course.setFont(new Font("Montserrat", Font.PLAIN, 15));
        course.setForeground(new Color(145, 145, 145));
        course.setBounds(35, 240, 175, 50);
        course.setHorizontalAlignment(JButton.LEFT);
        course.setBorder(null);
        course.setContentAreaFilled(false);
        course.setFocusable(false);
        course.setCursor(new Cursor(Cursor.HAND_CURSOR));
        course.setIcon(courseLogo);
        course.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                duplicateBackPanel.setVisible(true);
                duplicateBackPanel.setBounds(15, 243, 210, 45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236, 150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }
        });


        ImageIcon enrollLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\enroll.png", 23, 23);
        enroll.setFont(new Font("Montserrat", Font.PLAIN, 15));
        enroll.setForeground(new Color(145, 145, 145));
        enroll.setBounds(35, 290, 175, 50);
        enroll.setHorizontalAlignment(JButton.LEFT);
        enroll.setBorder(null);
        enroll.setContentAreaFilled(false);
        enroll.setFocusable(false);
        enroll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        enroll.setIcon(enrollLogo);
        enroll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                duplicateBackPanel.setVisible(true);
                duplicateBackPanel.setBounds(15, 293, 210, 45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236, 150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }
        });


        ImageIcon attendanceLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\attendance.png", 23, 23);
        attendance.setFont(new Font("Montserrat", Font.PLAIN, 15));
        attendance.setForeground(new Color(145, 145, 145));
        attendance.setBounds(35, 340, 175, 50);
        attendance.setHorizontalAlignment(JButton.LEFT);
        attendance.setBorder(null);
        attendance.setContentAreaFilled(false);
        attendance.setFocusable(false);
        attendance.setCursor(new Cursor(Cursor.HAND_CURSOR));
        attendance.setIcon(attendanceLogo);
        attendance.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                duplicateBackPanel.setVisible(true);
                duplicateBackPanel.setBounds(15, 343, 210, 45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236, 150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }
        });


        ImageIcon reportLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\report.png", 23, 23);
        report.setFont(new Font("Montserrat", Font.PLAIN, 15));
        report.setForeground(new Color(145, 145, 145));
        report.setBounds(35, 390, 175, 50);
        report.setHorizontalAlignment(JButton.LEFT);
        report.setBorder(null);
        report.setContentAreaFilled(false);
        report.setFocusable(false);
        report.setCursor(new Cursor(Cursor.HAND_CURSOR));
        report.setIcon(reportLogo);
        report.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                duplicateBackPanel.setVisible(true);
                duplicateBackPanel.setBounds(15, 393, 210, 45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236, 150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }
        });


        JLabel line2 = new JLabel("_________________________________________");
        line2.setFont(new Font("Montserrat", Font.PLAIN, 12));
        line2.setForeground(new Color(210, 210, 210));
        line2.setBounds(0, 420, 250, 40);
        line2.setHorizontalAlignment(JLabel.CENTER);


        ImageIcon logoutLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\logout.png", 23, 23);
        logout.setFont(new Font("Montserrat", Font.PLAIN, 15));
        logout.setForeground(new Color(145, 145, 145));
        logout.setBounds(42, 600, 175, 40);
        logout.setHorizontalAlignment(JButton.LEFT);
        login.setVerticalAlignment(JButton.CENTER);
        logout.setBorder(null);
        logout.setContentAreaFilled(false);
        logout.setFocusable(false);
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logout.setIcon(logoutLogo);
        logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon logoutLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\coloredLogout.png", 23, 23);
                logout.setBounds(42, 600, 175, 40);
                logout.setBounds(42, 599, 175, 40);
                logout.setBounds(42, 598, 175, 40);
                logout.setForeground(new Color(84, 23, 205));
                logout.setIcon(logoutLogo);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ImageIcon logoutLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\logout.png", 23, 23);
                logout.setBounds(42, 598, 175, 40);
                logout.setBounds(42, 599, 175, 40);
                logout.setBounds(42, 600, 175, 40);
                logout.setForeground(new Color(240, 239, 255));
                logout.setIcon(logoutLogo);
            }
        });


        RoundedPanel sidePanel = new RoundedPanel(50);
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setBounds(10, 10, 240, 678);
        sidePanel.setBackground(new Color(134, 73, 255));
        sidePanel.setLayout(null);
        sidePanel.add(sms);
        sidePanel.add(ms);
        sidePanel.add(line1);
        sidePanel.add(dashb);
        sidePanel.add(student);
        sidePanel.add(course);
        sidePanel.add(enroll);
        sidePanel.add(attendance);
        sidePanel.add(report);
        sidePanel.add(line2);
        sidePanel.add(logout);
        sidePanel.add(backPanel);
        sidePanel.add(duplicateBackPanel);
        //====================== side panel ends here ======================


        //========================== Attendance Panel ==========================

        JLabel AttendanceMng = new JLabel("Attendance");
        AttendanceMng.setFont(new Font("Century Gothic", Font.BOLD, 33));
        AttendanceMng.setForeground(new Color(33, 37, 40));
        AttendanceMng.setBounds(20, 20, 300, 40);

        AnimatedButton markAttendance = new AnimatedButton("Mark Attendance", 20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        markAttendance.setBounds(850, 18, 160, 40);
        markAttendance.setForeground(Color.WHITE);
        markAttendance.setFont(new Font("Montserrat", Font.PLAIN, 14));
        markAttendance.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                markAttendance.setBounds(850, 18, 160, 40);
                markAttendance.setBounds(850, 17, 160, 40);
                markAttendance.setBounds(850, 16, 160, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                markAttendance.setBounds(850, 16, 160, 40);
                markAttendance.setBounds(850, 17, 160, 40);
                markAttendance.setBounds(850, 18, 160, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                attendancePanel.setVisible(false);
//                addNewStuPanel.setVisible(true);
            }
        });


        String[] courses = {"Select a Course"};
        RoundedComboBox courseSelector = new RoundedComboBox(courses, 10);
        courseSelector.setBounds(20, 14, 250, 40);


        ModernDateChooser dateChooser = new ModernDateChooser();
        dateChooser.setBounds(300, 14, 250, 40);


        AnimatedButton viewAttendance = new AnimatedButton("View Attendance", 20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        viewAttendance.setBounds(750, 14, 160, 40);
        viewAttendance.setForeground(Color.WHITE);
        viewAttendance.setFont(new Font("Montserrat", Font.PLAIN, 14));


        RoundedPanel attendanceWhitePanel = new RoundedPanel(20);
        attendanceWhitePanel.setBounds(20, 68, 990, 70);
        attendanceWhitePanel.setBackground(Color.WHITE);
        attendanceWhitePanel.setLayout(null);
        attendanceWhitePanel.add(dateChooser);
        attendanceWhitePanel.add(courseSelector);
        attendanceWhitePanel.add(viewAttendance);


        JLabel attendanceRecord = new JLabel("Attendance Record");
        attendanceRecord.setBounds(30, 15, 200, 30);
        attendanceRecord.setForeground(Color.DARK_GRAY);
        attendanceRecord.setFont(new Font("Montserrat", Font.BOLD, 14));


        JLabel attendanceLine = new JLabel("_______________________________________________________________________________" +
                "___________________________________________");
        attendanceLine.setBounds(0, 37, 990, 30);
        attendanceLine.setFont(new Font("Arial", Font.BOLD, 14));
        attendanceLine.setForeground(new Color(240, 240, 240));
        attendanceLine.setHorizontalAlignment(JLabel.CENTER);


        JLabel total = new JLabel("Total : 08");
        total.setBounds(653, 15, 100, 30);
        total.setForeground(Color.DARK_GRAY);
        total.setFont(new Font("Montserrat", Font.PLAIN, 13));
        total.setHorizontalAlignment(JLabel.RIGHT);

        JLabel presentStudents = new JLabel("Present : 05");
        presentStudents.setBounds(753, 15, 100, 30);
        presentStudents.setForeground(Color.DARK_GRAY);
        presentStudents.setFont(new Font("Montserrat", Font.PLAIN, 13));
        presentStudents.setHorizontalAlignment(JLabel.RIGHT);

        JLabel absentStudents = new JLabel("Absent : 03");
        absentStudents.setBounds(853, 15, 100, 30);
        absentStudents.setForeground(Color.DARK_GRAY);
        absentStudents.setFont(new Font("Montserrat", Font.PLAIN, 13));
        absentStudents.setHorizontalAlignment(JLabel.RIGHT);

        JLabel noRecordFound = new JLabel("No Record Found for Particular Date and Course");
        noRecordFound.setBounds(0, 0, 990, 440);
        noRecordFound.setForeground(Color.DARK_GRAY);
        noRecordFound.setFont(new Font("Montserrat", Font.PLAIN, 14));
        noRecordFound.setHorizontalAlignment(JLabel.CENTER);
        noRecordFound.setVerticalAlignment(JLabel.CENTER);
        noRecordFound.setVisible(false);


        JLabel viewOrMark = new JLabel("<html>Attendance Tracking System" +
                "<br>Use 'Mark Attendance' to record student presence." +
                "<br>View records by selecting date and course filters.</html>");
        viewOrMark.setBounds(0, 0, 990, 440);
        viewOrMark.setForeground(Color.DARK_GRAY);
        viewOrMark.setFont(new Font("Montserrat", Font.PLAIN, 14));
        viewOrMark.setHorizontalAlignment(JLabel.CENTER);
        viewOrMark.setVerticalAlignment(JLabel.CENTER);
        viewOrMark.setVisible(false);


        Object[] attendanceTableColName = {"STUDENT NAME", "COURSE", "ATTENDANCE"};
        DefaultTableModel attendanceTableModel = new DefaultTableModel(null, attendanceTableColName);

        JTable attendanceTable = new JTable(attendanceTableModel);
        attendanceTable.setForeground(Color.GRAY);
        attendanceTable.setShowGrid(false);
        attendanceTable.setDefaultEditor(Object.class, null);
        attendanceTable.getColumnModel().getColumn(0).setPreferredWidth(257);   // Stu Name
        attendanceTable.getColumnModel().getColumn(1).setPreferredWidth(257);  // Course
        attendanceTable.getColumnModel().getColumn(2).setPreferredWidth(256);  // Attendance
        attendanceTable.setRowHeight(55);
        attendanceTable.getTableHeader().setResizingAllowed(false);
        attendanceTable.getTableHeader().setReorderingAllowed(false);
        attendanceTable.setBorder(null);
        attendanceTable.setIntercellSpacing(new Dimension(0, 0));


        attendanceTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable attendanceTable, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                // Use super to get the basic setup
                super.getTableCellRendererComponent(attendanceTable, value, isSelected, hasFocus, row, column);

                setOpaque(true);
                setFont(new Font("Montserrat", Font.PLAIN, 13));
                setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                setHorizontalAlignment(JLabel.LEFT);
                setForeground(Color.DARK_GRAY);

                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0, 0, 1, 0,
                                new Color(245, 245, 245)),
                        BorderFactory.createEmptyBorder(
                                5, 15, 5, 15)
                ));

                if (column == 0 || column == 1 || column == 2)
                    setHorizontalAlignment(JLabel.CENTER);

                if (row % 2 == 0)
                    setBackground(new Color(250, 250, 250));
                else
                    setBackground(Color.WHITE);
                return this;
            }
        });


// ================= HEADER STYLE =================
        JTableHeader attendanceTableHeader = attendanceTable.getTableHeader();
        attendanceTableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable attendanceTable, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                JLabel label = new JLabel(value.toString());
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                label.setForeground(Color.GRAY);
                label.setFont(new Font("Montserrat", Font.BOLD, 12));
                label.setHorizontalAlignment(JLabel.LEFT);
                label.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createMatteBorder(
                                        0, 0, 1, 0,
                                        new Color(240, 240, 240)),
                                BorderFactory.createEmptyBorder(
                                        15, 15, 15, 15)
                        )
                );

                if (column == 0 || column == 1 || column == 2)
                    label.setHorizontalAlignment(JLabel.CENTER);

                return label;
            }
        });


        RoundScrollPane attendanceBgScrollBar = new RoundScrollPane(attendanceTable, 30);
        attendanceBgScrollBar.setBackground(Color.WHITE);
        attendanceBgScrollBar.setBounds(0, 40, 990, 400);
        attendanceBgScrollBar.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        attendanceBgScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        attendanceBgScrollBar.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        attendanceBgScrollBar.getVerticalScrollBar().setUI(new ModernScrollBarUI());


        RoundedPanel Roundedbg = new RoundedPanel(35);
        Roundedbg.setBounds(675, 13, 300, 35);
        Roundedbg.setBackground(null);
        Roundedbg.setLayout(null);
        Roundedbg.setBorder(new RoundedBorder(40, Color.LIGHT_GRAY));


        viewAttendance.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String Course = courseSelector.getSelectedItem().toString();
                Date selectedDate = dateChooser.getDate();
                LocalDate date = selectedDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                try {
                    Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                    Statement st = con.createStatement();
                    String query = String.format("select studentName,courseName,attendance from Attendance where courseName = '%s' and attendanceDate = '%s'"
                            , Course, date);
                    ResultSet result = st.executeQuery(query);
                    if (!result.isBeforeFirst()) {
                        attendanceRecord.setVisible(false);
                        attendanceLine.setVisible(false);
                        total.setVisible(false);
                        presentStudents.setVisible(false);
                        attendanceBgScrollBar.setVisible(false);
                        absentStudents.setVisible(false);
                        Roundedbg.setVisible(false);
                        viewOrMark.setVisible(false);
                        noRecordFound.setVisible(true);
                        return;
                    } else {
                        attendanceTableModel.setRowCount(0);
                        noRecordFound.setVisible(false);
                        viewOrMark.setVisible(false);
                        attendanceRecord.setVisible(true);
                        attendanceLine.setVisible(true);
                        total.setVisible(true);
                        presentStudents.setVisible(true);
                        absentStudents.setVisible(true);
                        Roundedbg.setVisible(true);

                        while (result.next()) {
                            Object[] newData = {result.getString("studentName"),
                                    result.getString("courseName"),
                                    result.getString("attendance")};
                            attendanceTableModel.addRow(newData);
                        }
                        total.setText(Integer.toString(attendanceTableModel.getRowCount()));
                        attendanceBgScrollBar.setVisible(true);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        RoundedPanel attendanceWhite1Panel = new RoundedPanel(20);
        attendanceWhite1Panel.setBounds(20, 150, 990, 440);
        attendanceWhite1Panel.setBackground(Color.WHITE);
        attendanceWhite1Panel.setLayout(null);
        attendanceWhite1Panel.add(attendanceRecord);
        attendanceWhite1Panel.add(attendanceLine);
        attendanceWhite1Panel.add(total);
        attendanceWhite1Panel.add(presentStudents);
        attendanceWhite1Panel.add(absentStudents);
        attendanceWhite1Panel.add(Roundedbg);
        attendanceWhite1Panel.add(attendanceBgScrollBar);
        attendanceWhite1Panel.add(noRecordFound);
        attendanceWhite1Panel.add(viewOrMark);

        attendancePanel.setBounds(250, 70, 1030, 650);
        attendancePanel.setBackground(new Color(240, 239, 255));
        attendancePanel.setLayout(null);
        attendancePanel.add(AttendanceMng);
        attendancePanel.add(markAttendance);
        attendancePanel.add(attendanceWhitePanel);
        attendancePanel.add(attendanceWhite1Panel);
        attendancePanel.setVisible(false);

        // ========================== Attendance Panel Ends Here ==================


        //===================== Enroll to Course ===================

        JLabel enrollCourseText = new JLabel("Enroll Student To Courses");
        enrollCourseText.setForeground(Color.WHITE);
        enrollCourseText.setFont(new Font("Montserrat", Font.BOLD, 22));
        enrollCourseText.setBounds(20, 20, 350, 40);

        AnimatedButton backEnrollBtn = new AnimatedButton(" Back to Students", 15
                , new Color(255, 255, 255) //normal Color
                , new Color(255, 255, 255) //Hovered Color
                , new Color(255, 255, 255) // Pressed Color
                , new Color(255, 255, 255)); // Border Color
        backEnrollBtn.setForeground(new Color(134, 73, 255));
        backEnrollBtn.setFont(new Font("Montserrat", Font.PLAIN, 14));
        backEnrollBtn.setBounds(790, 20, 180, 40);
        ImageIcon img7 = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\back1.png", 15, 15);
        backEnrollBtn.setIcon(img7);
        backEnrollBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backEnrollBtn.setBounds(790, 20, 180, 40);
                backEnrollBtn.setBounds(790, 19, 180, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backEnrollBtn.setBounds(790, 19, 180, 40);
                backEnrollBtn.setBounds(790, 20, 180, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                enrollToCourse.setVisible(false);
                studentPanel.setVisible(true);
            }
        });


        RoundedPanel enrollStudentUpperPanel = new RoundedPanel(20);
        enrollStudentUpperPanel.setBounds(20, 25, 990, 80);
        enrollStudentUpperPanel.setBackground(new Color(134, 73, 255, 230));
        enrollStudentUpperPanel.setLayout(null);
        enrollStudentUpperPanel.add(backEnrollBtn);
        enrollStudentUpperPanel.add(enrollCourseText);


        JLabel courseSelection = new JLabel("Course Selection");
        courseSelection.setForeground(Color.DARK_GRAY);
        courseSelection.setFont(new Font("Montserrat", Font.BOLD, 14));
        courseSelection.setBounds(28, 15, 340, 30);

        JLabel multiNotAllowed = new JLabel("Multi-select not allowed");
        multiNotAllowed.setForeground(Color.DARK_GRAY);
        multiNotAllowed.setFont(new Font("Montserrat", Font.PLAIN, 12));
        multiNotAllowed.setBounds(270, 15, 340, 30);
        multiNotAllowed.setHorizontalAlignment(JLabel.RIGHT);

        JLabel courseSelectionLine = new JLabel("_______________________________________________________________________________");
        courseSelectionLine.setBounds(0, 37, 635, 30);
        courseSelectionLine.setFont(new Font("Arial", Font.BOLD, 14));
        courseSelectionLine.setForeground(new Color(240, 240, 240));
        courseSelectionLine.setHorizontalAlignment(JLabel.CENTER);


        JLabel courseSelectedAns = new JLabel("00");
        JLabel totalFeeAns = new JLabel("$2100.00");
        JLabel Course_Name = new JLabel("• Web Development");


        enrollTable.setForeground(Color.GRAY);
        enrollTable.setShowGrid(false);
        enrollTable.setDefaultEditor(Object.class, null);
        enrollTable.getColumnModel().getColumn(0).setPreferredWidth(70);   // S.No
        enrollTable.getColumnModel().getColumn(1).setPreferredWidth(390);  // Enroll Course Name
        enrollTable.getColumnModel().getColumn(2).setPreferredWidth(135);  // Fees
        enrollTable.setRowHeight(55);
        enrollTable.getTableHeader().setResizingAllowed(false);
        enrollTable.getTableHeader().setReorderingAllowed(false);
        enrollTable.setBorder(null);
        enrollTable.setIntercellSpacing(new Dimension(0, 0));

        final int[] hoverRow = {-1};
        enrollTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = enrollTable.rowAtPoint(e.getPoint());

                if (hoverRow[0] != row) {
                    hoverRow[0] = row;
                    enrollTable.repaint();
                }
            }
        });

        enrollTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoverRow[0] = -1;
                enrollTable.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String courseName = enrollTableModel.getValueAt(enrollTable.getSelectedRow(), 1).toString();
                String fee = enrollTableModel.getValueAt(enrollTable.getSelectedRow(), 2).toString();
                courseSelectedAns.setText("01");
                totalFeeAns.setText(fee);
                Course_Name.setText("• " + courseName);
            }
        });
        enrollTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable enrollTable, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                super.getTableCellRendererComponent(
                        enrollTable, value, isSelected,
                        hasFocus, row, column);

                setOpaque(true);
                setFont(new Font("Montserrat", Font.PLAIN, 13));

                // Added bottom border
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0, 0, 1, 0,
                                new Color(245, 245, 245)),
                        BorderFactory.createEmptyBorder(
                                5, 15, 5, 15)
                ));

                if (column == 2)
                    setHorizontalAlignment(JLabel.RIGHT);
                else
                    setHorizontalAlignment(JLabel.LEFT);
                setForeground(Color.DARK_GRAY);

                if (isSelected) {
                    setBackground(new Color(230, 230, 255));
                    setFont(new Font("Montserrat", Font.BOLD, 13));
                } else if (row == hoverRow[0]) {
                    setBackground(new Color(230, 230, 255, 100)); // hover color
                    setFont(new Font("Montserrat", Font.BOLD, 13));
                } else {
                    setBackground(Color.WHITE);
                }

                return this;
            }
        });


// ================= HEADER STYLE =================
        JTableHeader enrollTableHeader = enrollTable.getTableHeader();

        enrollTableHeader.setDefaultRenderer(
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable enrollTable, Object value,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {

                        JLabel label = new JLabel(value.toString());

                        label.setOpaque(true);
                        label.setBackground(Color.WHITE);
                        label.setForeground(Color.GRAY);
                        label.setFont(new Font("Montserrat",
                                Font.BOLD, 12));
                        if (column == 2)
                            label.setHorizontalAlignment(JLabel.RIGHT);
                        else
                            label.setHorizontalAlignment(JLabel.LEFT);

                        // Added bottom border
                        label.setBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createMatteBorder(
                                                0, 0, 1, 0,
                                                new Color(240, 240, 240)),
                                        BorderFactory.createEmptyBorder(
                                                15, 15, 15, 15)
                                )
                        );

                        return label;
                    }
                });


        RoundScrollPane enrollTableBg = new RoundScrollPane(enrollTable, 20);
        enrollTableBg.setBounds(20, 70, 595, 375);
        enrollTableBg.setBackground(Color.WHITE);
        enrollTableBg.setBorder(BorderFactory.createEmptyBorder());
        enrollTableBg.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        enrollTableBg.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        enrollTableBg.getVerticalScrollBar().setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        enrollTableBg.getVerticalScrollBar().setUI(new ModernScrollBarUI(new Color(134, 73, 255, 150), new Color(240, 240, 240)));


        RoundedPanel enrollLeftPanel = new RoundedPanel(20);
        enrollLeftPanel.setBounds(20, 120, 635, 480);
        enrollLeftPanel.setBackground(Color.WHITE);
        enrollLeftPanel.setLayout(null);
        enrollLeftPanel.add(courseSelection);
        enrollLeftPanel.add(courseSelectionLine);
        enrollLeftPanel.add(multiNotAllowed);
        enrollLeftPanel.add(enrollTableBg);


        JLabel studentSummary = new JLabel("Student & Summary");
        studentSummary.setForeground(Color.DARK_GRAY);
        studentSummary.setFont(new Font("Montserrat", Font.BOLD, 14));
        studentSummary.setBounds(28, 15, 340, 30);

        JLabel summaryLine = new JLabel("__________________________________________");
        summaryLine.setBounds(0, 37, 340, 30);
        summaryLine.setFont(new Font("Arial", Font.BOLD, 14));
        summaryLine.setForeground(new Color(240, 240, 240));
        summaryLine.setHorizontalAlignment(JLabel.CENTER);

        JLabel selectStudent = new JLabel("Select Student");
        selectStudent.setForeground(Color.darkGray);
        selectStudent.setFont(new Font("Montserrat", Font.PLAIN, 14));
        selectStudent.setBounds(25, 75, 340, 30);

        JLabel selectedStudentName = new JLabel("John Doe");
        JLabel selectedStudentEmail = new JLabel("john@example.com");

        String[] studentsList = {"Select a Student"};
        RoundedComboBox selectStudentField = new RoundedComboBox(studentsList, 15);
        selectStudentField.setBounds(25, 108, 290, 40);
        selectStudentField.setFont(new Font("Montserrat", Font.PLAIN, 14));
        selectStudentField.addActionListener(e -> {
            String selectedItem = (String) selectStudentField.getSelectedItem();
            String id;
            if (selectedItem != null && !selectedItem.equals("Select a Student")) {
                selectedStudentName.setText(selectedItem.substring(0, selectedItem.indexOf("#") - 2));
                id = selectedItem.substring(selectedItem.indexOf("#"), selectedItem.indexOf(")"));
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (id.equals(model.getValueAt(i, 0).toString())) {
                        selectedStudentEmail.setText(model.getValueAt(i, 2).toString());
                    }
                }
            } else {
                selectedStudentName.setText("John Doe");
                selectedStudentEmail.setText("john@example.com");
            }
        });

        JLabel selectedStudentText = new JLabel("Selected Student");
        selectedStudentText.setForeground(Color.DARK_GRAY);
        selectedStudentText.setFont(new Font("Montserrat", Font.PLAIN, 13));
        selectedStudentText.setBounds(15, 15, 200, 25);


        selectedStudentName.setForeground(Color.DARK_GRAY);
        selectedStudentName.setFont(new Font("Montserrat", Font.BOLD, 13));
        selectedStudentName.setBounds(15, 35, 250, 25);

        selectedStudentEmail.setForeground(Color.DARK_GRAY);
        selectedStudentEmail.setFont(new Font("Montserrat", Font.PLAIN, 13));
        selectedStudentEmail.setBounds(15, 55, 250, 25);

        RoundedPanel selectedStudent = new RoundedPanel(15);
        selectedStudent.setBounds(25, 155, 290, 95);
        selectedStudent.setBackground(new Color(250, 250, 250));
        selectedStudent.setBorder(new RoundedBorder(15, Color.lightGray));
        selectedStudent.setLayout(null);
        selectedStudent.add(selectedStudentText);
        selectedStudent.add(selectedStudentName);
        selectedStudent.add(selectedStudentEmail);


        JLabel courseSelectedText = new JLabel("Courses Selected");
        courseSelectedText.setForeground(Color.DARK_GRAY);
        courseSelectedText.setFont(new Font("Montserrat", Font.PLAIN, 14));
        courseSelectedText.setBounds(15, 16, 150, 25);


        courseSelectedAns.setForeground(Color.DARK_GRAY);
        courseSelectedAns.setFont(new Font("Montserrat", Font.BOLD, 14));
        courseSelectedAns.setBounds(175, 16, 100, 25);
        courseSelectedAns.setHorizontalAlignment(JLabel.RIGHT);

        JLabel totalFeeText = new JLabel("Total Fee");
        totalFeeText.setForeground(Color.DARK_GRAY);
        totalFeeText.setFont(new Font("Montserrat", Font.PLAIN, 14));
        totalFeeText.setBounds(15, 40, 100, 25);


        totalFeeAns.setForeground(new Color(134, 73, 255));
        totalFeeAns.setFont(new Font("Montserrat", Font.BOLD, 14));
        totalFeeAns.setBounds(175, 40, 100, 25);
        totalFeeAns.setHorizontalAlignment(JLabel.RIGHT);


        RoundedPanel selectedCourse = new RoundedPanel(15);
        selectedCourse.setBounds(25, 262, 290, 80);
        selectedCourse.setBackground(new Color(250, 250, 250));
        selectedCourse.setBorder(new RoundedBorder(15, Color.lightGray));
        selectedCourse.setLayout(null);
        selectedCourse.add(courseSelectedText);
        selectedCourse.add(courseSelectedAns);
        selectedCourse.add(totalFeeText);
        selectedCourse.add(totalFeeAns);


        JLabel selectedCourseText = new JLabel("Selected Courses");
        selectedCourseText.setForeground(Color.DARK_GRAY);
        selectedCourseText.setFont(new Font("Montserrat", Font.PLAIN, 13));
        selectedCourseText.setBounds(25, 355, 200, 25);


        Course_Name.setForeground(Color.DARK_GRAY);
        Course_Name.setFont(new Font("Montserrat", Font.BOLD, 14));
        Course_Name.setBounds(40, 380, 200, 25);


        AnimatedButton cancelBtn = new AnimatedButton("Cancel", 40
                , Color.WHITE
                , new Color(250, 250, 250)
                , new Color(245, 245, 245)
                , Color.lightGray);
        cancelBtn.setForeground(Color.darkGray);
        cancelBtn.setFont(new Font("Montserrat", Font.PLAIN, 16));
        cancelBtn.setBounds(25, 420, 135, 40);
        cancelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                cancelBtn.setBounds(25, 420, 135, 40);
                cancelBtn.setBounds(25, 419, 135, 40);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cancelBtn.setBounds(25, 419, 135, 40);
                cancelBtn.setBounds(25, 420, 135, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                courseSelectedAns.setText("00");
                totalFeeAns.setText("0.00 Rs");
                Course_Name.setText("• None Selected");
                refreshEnrollTable(enrollTableModel, url);
                selectStudentField.setSelectedItem("Select a Student");
            }
        });


        AnimatedButton enrollBtn = new AnimatedButton("Enroll", 40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        enrollBtn.setForeground(Color.WHITE);
        enrollBtn.setFont(new Font("Montserrat", Font.PLAIN, 16));
        enrollBtn.setBounds(180, 420, 135, 40);
        enrollBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                enrollBtn.setBounds(180, 420, 135, 40);
                enrollBtn.setBounds(180, 419, 135, 40);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                enrollBtn.setBounds(180, 419, 135, 40);
                enrollBtn.setBounds(180, 420, 135, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectStudentField.getSelectedItem().equals("Select a Student")) {
                    JOptionPane.showMessageDialog(null,
                            "Please select a student first!",
                            "No Student Selected",
                            JOptionPane.WARNING_MESSAGE);
                } else if (enrollTable.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(null,
                            "Please select a course first!",
                            "No Course Selected",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    String selected_Item = selectStudentField.getSelectedItem().toString();
                    String id = selected_Item.substring(selected_Item.indexOf("#"), selected_Item.indexOf(")"));
                    String fullName = selectedStudentName.getText();
                    String courseName = enrollTableModel.getValueAt(enrollTable.getSelectedRow(), 1).toString();
                    String paidFee = enrollTableModel.getValueAt(enrollTable.getSelectedRow(), 2).toString();
                    int paid_Fee = Integer.parseInt(paidFee.substring(0, paidFee.indexOf(".")));
                    try {
                        Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = con.createStatement();

                        String query = String.format("select * from NewCourse where course_name = '%s'", courseName);
                        ResultSet resultSet = st.executeQuery(query);
                        resultSet.next();

                        int course_ID = resultSet.getInt("id");
                        String Duration = resultSet.getString("duration");

                        String query1 = String.format("Insert into Enrollment(stu_id,stu_name,course_id,course_name,duration,fee_paid)" +
                                " values ('%s','%s',%d,'%s','%s',%d)", id, fullName, course_ID, courseName, Duration, paid_Fee);
                        st.executeUpdate(query1);
                        con.close();

                        refreshRecentEnrollTable(recentEnrollTableModel, url);

                        enrollToCourse.setVisible(false);
                        dashPanel.setVisible(true);
                    } catch (SQLIntegrityConstraintViolationException exception) {
                        JOptionPane.showMessageDialog(null,
                                "Duplicate Enrollment!\n" + fullName + " is already enrolled in " + courseName + ".",
                                "Enrollment Failed",
                                JOptionPane.WARNING_MESSAGE);
                        courseSelectedAns.setText("00");
                        totalFeeAns.setText("0.00 Rs");
                        Course_Name.setText("• None Selected");
                        refreshEnrollTable(enrollTableModel, url);
                        selectStudentField.setSelectedItem("Select a Student");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });


        RoundedPanel enrollRightPanel = new RoundedPanel(20);
        enrollRightPanel.setBounds(670, 120, 340, 480);
        enrollRightPanel.setBackground(Color.WHITE);
        enrollRightPanel.setLayout(null);
        enrollRightPanel.add(studentSummary);
        enrollRightPanel.add(summaryLine);
        enrollRightPanel.add(selectStudent);
        enrollRightPanel.add(selectStudentField);
        enrollRightPanel.add(selectedStudent);
        enrollRightPanel.add(selectedCourse);
        enrollRightPanel.add(selectedCourseText);
        enrollRightPanel.add(Course_Name);
        enrollRightPanel.add(cancelBtn);
        enrollRightPanel.add(enrollBtn);


        enrollToCourse.setBounds(250, 70, 1030, 650);
        enrollToCourse.setLayout(null);
        enrollToCourse.setBackground(new Color(240, 239, 255));
        enrollToCourse.add(enrollStudentUpperPanel);
        enrollToCourse.add(enrollLeftPanel);
        enrollToCourse.add(enrollRightPanel);
        enrollToCourse.setVisible(false);


        //===================== Enroll to Course Ends Here =====================


        //=========== Course Panel================
        JLabel editCourseText = new JLabel("Edit Course");
        editCourseText.setBounds(20, 20, 300, 40);
        editCourseText.setForeground(new Color(33, 37, 40));
        editCourseText.setFont(new Font("Century Gothic", Font.BOLD, 33));

        AnimatedButton editCourseBackToList = new AnimatedButton(" Back to List", 20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        editCourseBackToList.setBounds(860, 18, 150, 40);
        editCourseBackToList.setForeground(Color.WHITE);
        editCourseBackToList.setFont(new Font("Montserrat", Font.PLAIN, 14));
        editCourseBackToList.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        editCourseBackToList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editCourseBackToList.setBounds(860, 18, 150, 40);
                editCourseBackToList.setBounds(860, 17, 150, 40);
                editCourseBackToList.setBounds(860, 16, 150, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editCourseBackToList.setBounds(860, 16, 150, 40);
                editCourseBackToList.setBounds(860, 17, 150, 40);
                editCourseBackToList.setBounds(860, 18, 150, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                editCoursePanel.setVisible(false);
                coursePanel.setVisible(true);
            }
        });


        JLabel editCourseDetails = new JLabel("Edit Course Details");
        editCourseDetails.setBounds(40, 17, 150, 30);
        editCourseDetails.setFont(new Font("Montserrat", Font.BOLD, 14));
        editCourseDetails.setForeground(Color.darkGray);

        JLabel editCourseLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        editCourseLine.setBounds(0, 43, 990, 20);
        editCourseLine.setFont(new Font("Arial", Font.BOLD, 14));
        editCourseLine.setForeground(new Color(240, 240, 240));

        JLabel editCourseName = new JLabel("Course Name");
        editCourseName.setForeground(new Color(120, 120, 120));
        editCourseName.setFont(new Font("Montserrat", Font.BOLD, 14));
        editCourseName.setBounds(20, 72, 150, 30);

        RoundedTextField editCourseNameBox = new RoundedTextField("e.g. Web Development", 10);
        editCourseNameBox.setBounds(20, 104, 465, 40);
        editCourseNameBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        editCourseNameBox.setForeground(new Color(105, 105, 105));

        JLabel editCourseCode = new JLabel("Course Code");
        editCourseCode.setForeground(new Color(120, 120, 120));
        editCourseCode.setFont(new Font("Montserrat", Font.BOLD, 14));
        editCourseCode.setBounds(505, 72, 150, 30);

        RoundedTextField editCourseCodeBox = new RoundedTextField("e.g. WEB101", 10);
        editCourseCodeBox.setBounds(505, 104, 465, 40);
        editCourseCodeBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        editCourseCodeBox.setForeground(new Color(105, 105, 105));

        JLabel editCourseDuration = new JLabel("Duration");
        editCourseDuration.setForeground(new Color(120, 120, 120));
        editCourseDuration.setFont(new Font("Montserrat", Font.BOLD, 14));
        editCourseDuration.setBounds(20, 152, 150, 30);

        RoundedTextField editCourseDurationBox = new RoundedTextField("e.g. 6 Months", 10);
        editCourseDurationBox.setBounds(20, 184, 465, 40);
        editCourseDurationBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        editCourseDurationBox.setForeground(new Color(105, 105, 105));

        JLabel editCourseFee = new JLabel("Course Fee");
        editCourseFee.setForeground(new Color(120, 120, 120));
        editCourseFee.setFont(new Font("Montserrat", Font.BOLD, 14));
        editCourseFee.setBounds(505, 152, 150, 30);

        RoundedTextField editCourseFeeBox = new RoundedTextField("e.g. $1200", 10);
        editCourseFeeBox.setBounds(505, 184, 465, 40);
        editCourseFeeBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        editCourseFeeBox.setForeground(new Color(105, 105, 105));

        JLabel editCourseDescription = new JLabel("Description");
        editCourseDescription.setForeground(new Color(120, 120, 120));
        editCourseDescription.setFont(new Font("Montserrat", Font.BOLD, 14));
        editCourseDescription.setBounds(20, 232, 150, 30);

        RoundedTextField editCourseDescriptionBox = new RoundedTextField("Enter course description...", 10);
        editCourseDescriptionBox.setBounds(20, 264, 950, 100);
        editCourseDescriptionBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        editCourseDescriptionBox.setForeground(new Color(105, 105, 105));


        AnimatedButton resetEditCourseBtn = new AnimatedButton("Reset", 40,
                new Color(240, 239, 255),    // normal Color (light off-white)
                new Color(220, 218, 235),    // Hovered Color (slightly darker)
                new Color(200, 198, 215),    // Pressed Color (even darker)
                new Color(240, 239, 255));
        resetEditCourseBtn.setBounds(710, 380, 120, 40);
        resetEditCourseBtn.setForeground(Color.darkGray);
        resetEditCourseBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
        resetEditCourseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetEditCourseBtn.setBounds(710, 380, 120, 40);
                resetEditCourseBtn.setBounds(710, 379, 120, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetEditCourseBtn.setBounds(710, 379, 120, 40);
                resetEditCourseBtn.setBounds(710, 380, 120, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                editCourseNameBox.setText("");
                editCourseCodeBox.setText("");
                editCourseDurationBox.setText("");
                editCourseFeeBox.setText("");
                editCourseDescriptionBox.setText("");
            }
        });


        AnimatedButton addEditedCourseBtn = new AnimatedButton("Add", 40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        addEditedCourseBtn.setBounds(850, 380, 120, 40);
        addEditedCourseBtn.setForeground(Color.WHITE);
        addEditedCourseBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
        addEditedCourseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addEditedCourseBtn.setBounds(850, 380, 120, 40);
                addEditedCourseBtn.setBounds(850, 379, 120, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addEditedCourseBtn.setBounds(850, 379, 120, 40);
                addEditedCourseBtn.setBounds(850, 380, 120, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                int C_id = Integer.parseInt(courseTableModel.getValueAt(courseTable.getSelectedRow(), 0).toString());
                String course_Name = editCourseNameBox.getText().trim();
                String course_Code = editCourseCodeBox.getText().trim();
                String course_Duration = editCourseDurationBox.getText().trim();
                String course_Fee = editCourseFeeBox.getText().trim();
                String Description = editCourseDescriptionBox.getText().trim();

                if (course_Name.isEmpty() || course_Code.isEmpty() || course_Duration.isEmpty() || course_Fee.isEmpty() || Description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                } else if (!course_Duration.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Duration must be numeric!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    editCourseDurationBox.requestFocus();
                } else if (Integer.parseInt(course_Duration) <= 0 || Integer.parseInt(course_Duration) > 60) {
                    JOptionPane.showMessageDialog(null, "Duration must be between 1 and 60 months!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    editCourseDurationBox.requestFocus();
                } else if (!course_Fee.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Fee must be numeric!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    editCourseFeeBox.requestFocus();
                } else if (Integer.parseInt(course_Fee) <= 0 || Integer.parseInt(course_Fee) > 500000) {
                    JOptionPane.showMessageDialog(null, "Fee is out of valid range!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    editCourseFeeBox.requestFocus();
                } else {
                    try {
                        Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = con.createStatement();
                        String query = String.format("Update NewCourse set course_name = '%s', course_code = '%s', duration = '%s', course_fee = %d, course_description = '%s' where id = %d"
                                , course_Name, course_Code, course_Duration + " Months", Integer.parseInt(course_Fee), Description, C_id);
                        st.executeUpdate(query);
                        editCourseNameBox.setText("");
                        editCourseCodeBox.setText("");
                        editCourseDurationBox.setText("");
                        editCourseFeeBox.setText("");
                        editCourseDescriptionBox.setText("");
                        editCoursePanel.setVisible(false);

                        courseTableModel.setValueAt(course_Name, courseTable.getSelectedRow(), 1);
                        courseTableModel.setValueAt(course_Code, courseTable.getSelectedRow(), 2);
                        courseTableModel.setValueAt(course_Duration + " Months", courseTable.getSelectedRow(), 3);
                        courseTableModel.setValueAt(course_Fee + " PKR", courseTable.getSelectedRow(), 4);


                        coursePanel.setVisible(true);
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }

        });


        RoundedPanel editCourseDetailsPanel = new RoundedPanel(30);
        editCourseDetailsPanel.setBounds(20, 68, 990, 436);
        editCourseDetailsPanel.setBackground(Color.WHITE);
        editCourseDetailsPanel.setLayout(null);
        editCourseDetailsPanel.add(editCourseDetails);
        editCourseDetailsPanel.add(editCourseLine);
        editCourseDetailsPanel.add(editCourseName);
        editCourseDetailsPanel.add(editCourseNameBox);
        editCourseDetailsPanel.add(editCourseCode);
        editCourseDetailsPanel.add(editCourseCodeBox);
        editCourseDetailsPanel.add(editCourseDuration);
        editCourseDetailsPanel.add(editCourseDurationBox);
        editCourseDetailsPanel.add(editCourseFee);
        editCourseDetailsPanel.add(editCourseFeeBox);
        editCourseDetailsPanel.add(editCourseDescription);
        editCourseDetailsPanel.add(editCourseDescriptionBox);
        editCourseDetailsPanel.add(resetEditCourseBtn);
        editCourseDetailsPanel.add(addEditedCourseBtn);


        editCoursePanel.setBounds(250, 70, 1030, 650);
        editCoursePanel.setBackground(new Color(240, 239, 255));
        editCoursePanel.setLayout(null);
        editCoursePanel.add(editCourseText);
        editCoursePanel.add(editCourseDetailsPanel);
        editCoursePanel.add(editCourseBackToList);
        editCoursePanel.setVisible(false);


        //============================================================


        JLabel viewCourseText = new JLabel("Course Details");
        viewCourseText.setBounds(20, 20, 300, 40);
        viewCourseText.setForeground(new Color(33, 37, 40));
        viewCourseText.setFont(new Font("Century Gothic", Font.BOLD, 33));

        AnimatedButton viewCourseBackToList = new AnimatedButton(" Back to List", 20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        viewCourseBackToList.setBounds(860, 18, 150, 40);
        viewCourseBackToList.setForeground(Color.WHITE);
        viewCourseBackToList.setFont(new Font("Montserrat", Font.PLAIN, 14));
        viewCourseBackToList.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        viewCourseBackToList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewCourseBackToList.setBounds(860, 18, 150, 40);
                viewCourseBackToList.setBounds(860, 17, 150, 40);
                viewCourseBackToList.setBounds(860, 16, 150, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewCourseBackToList.setBounds(860, 16, 150, 40);
                viewCourseBackToList.setBounds(860, 17, 150, 40);
                viewCourseBackToList.setBounds(860, 18, 150, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                viewCoursePanel.setVisible(false);
                coursePanel.setVisible(true);
            }
        });


        JLabel viewCourseInfoText = new JLabel("Information");
        viewCourseInfoText.setBounds(40, 17, 200, 30);
        viewCourseInfoText.setFont(new Font("Montserrat", Font.BOLD, 14));
        viewCourseInfoText.setForeground(Color.darkGray);

        JLabel viewCourseLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        viewCourseLine.setBounds(0, 43, 990, 20);
        viewCourseLine.setFont(new Font("Arial", Font.BOLD, 14));
        viewCourseLine.setForeground(new Color(240, 240, 240));


        JLabel id_course = new JLabel("Course ID:");
        id_course.setFont(new Font("Montserrat", Font.BOLD, 14));
        id_course.setForeground(Color.darkGray);
        id_course.setBorder(null);
        id_course.setBackground(null);
        id_course.setVerticalAlignment(JLabel.CENTER);
        id_course.setBounds(40, 90, 150, 30);

        JLabel id_course_ans = new JLabel();
        id_course_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        id_course_ans.setForeground(Color.darkGray);
        id_course_ans.setBorder(null);
        id_course_ans.setBackground(null);
        id_course_ans.setVerticalAlignment(JLabel.CENTER);
        id_course_ans.setBounds(290, 90, 200, 30);

        JLabel C_Name = new JLabel("Course Name:");
        C_Name.setFont(new Font("Montserrat", Font.BOLD, 14));
        C_Name.setForeground(Color.darkGray);
        C_Name.setBorder(null);
        C_Name.setBackground(null);
        C_Name.setVerticalAlignment(JLabel.CENTER);
        C_Name.setBounds(40, 132, 150, 30);

        JLabel C_Name_ans = new JLabel();
        C_Name_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        C_Name_ans.setForeground(Color.darkGray);
        C_Name_ans.setBorder(null);
        C_Name_ans.setBackground(null);
        C_Name_ans.setVerticalAlignment(JLabel.CENTER);
        C_Name_ans.setBounds(290, 132, 300, 30);

        JLabel C_Code = new JLabel("Course Code:");
        C_Code.setFont(new Font("Montserrat", Font.BOLD, 14));
        C_Code.setForeground(Color.darkGray);
        C_Code.setBorder(null);
        C_Code.setBackground(null);
        C_Code.setVerticalAlignment(JLabel.CENTER);
        C_Code.setBounds(40, 174, 150, 30);

        JLabel C_Code_ans = new JLabel();
        C_Code_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        C_Code_ans.setForeground(Color.darkGray);
        C_Code_ans.setBorder(null);
        C_Code_ans.setBackground(null);
        C_Code_ans.setVerticalAlignment(JLabel.CENTER);
        C_Code_ans.setBounds(290, 174, 400, 30);

        JLabel C_Duration = new JLabel("Duration:");
        C_Duration.setFont(new Font("Montserrat", Font.BOLD, 14));
        C_Duration.setForeground(Color.darkGray);
        C_Duration.setBorder(null);
        C_Duration.setBackground(null);
        C_Duration.setVerticalAlignment(JLabel.CENTER);
        C_Duration.setBounds(40, 216, 150, 30);

        JLabel C_Duration_ans = new JLabel();
        C_Duration_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        C_Duration_ans.setForeground(Color.darkGray);
        C_Duration_ans.setBorder(null);
        C_Duration_ans.setBackground(null);
        C_Duration_ans.setVerticalAlignment(JLabel.CENTER);
        C_Duration_ans.setBounds(290, 216, 300, 30);

        JLabel C_fee = new JLabel("Course Fee:");
        C_fee.setFont(new Font("Montserrat", Font.BOLD, 14));
        C_fee.setForeground(Color.darkGray);
        C_fee.setBorder(null);
        C_fee.setBackground(null);
        C_fee.setVerticalAlignment(JLabel.CENTER);
        C_fee.setBounds(40, 258, 150, 30);

        JLabel C_fee_ans = new JLabel();
        C_fee_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        C_fee_ans.setForeground(Color.darkGray);
        C_fee_ans.setBorder(null);
        C_fee_ans.setBackground(null);
        C_fee_ans.setVerticalAlignment(JLabel.CENTER);
        C_fee_ans.setBounds(290, 258, 150, 30);


        JLabel courseIsActive = new JLabel("Is Active:");
        courseIsActive.setFont(new Font("Montserrat", Font.BOLD, 14));
        courseIsActive.setForeground(Color.darkGray);
        courseIsActive.setBorder(null);
        courseIsActive.setBackground(null);
        courseIsActive.setVerticalAlignment(JLabel.CENTER);
        courseIsActive.setBounds(40, 300, 150, 30);

        JLabel courseIsActive_ans = new JLabel("Yes");
        courseIsActive_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        courseIsActive_ans.setForeground(Color.darkGray);
        courseIsActive_ans.setBorder(null);
        courseIsActive_ans.setBackground(null);
        courseIsActive_ans.setVerticalAlignment(JLabel.CENTER);
        courseIsActive_ans.setBounds(290, 300, 100, 30);


        JLabel C_Description = new JLabel("Description:");
        C_Description.setFont(new Font("Montserrat", Font.BOLD, 14));
        C_Description.setForeground(Color.darkGray);
        C_Description.setBorder(null);
        C_Description.setBackground(null);
        C_Description.setVerticalAlignment(JLabel.CENTER);
        C_Description.setBounds(40, 342, 150, 30);

        JLabel C_Description_ans = new JLabel();
        C_Description_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        C_Description_ans.setForeground(Color.darkGray);
        C_Description_ans.setBorder(null);
        C_Description_ans.setBackground(null);
        C_Description_ans.setVerticalAlignment(JLabel.CENTER);
        C_Description_ans.setBounds(290, 342, 700, 30);


        JLabel viewCourseDownLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        viewCourseDownLine.setBounds(0, 387, 990, 20);
        viewCourseDownLine.setFont(new Font("Arial", Font.BOLD, 14));
        viewCourseDownLine.setForeground(new Color(240, 240, 240));


        AnimatedButton editCourseBtn = new AnimatedButton(("Edit Course"), 40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        editCourseBtn.setBounds(40, 418, 145, 40);
        editCourseBtn.setForeground(Color.WHITE);
        editCourseBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
        editCourseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editCourseBtn.setBounds(40, 418, 145, 40);
                editCourseBtn.setBounds(40, 417, 145, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editCourseBtn.setBounds(40, 417, 145, 40);
                editCourseBtn.setBounds(40, 418, 145, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                viewCoursePanel.setVisible(false);
                int C_id = Integer.parseInt(courseTableModel.getValueAt(courseTable.getSelectedRow(), 0).toString());
                try {
                    Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                    Statement st = con.createStatement();
                    String query = String.format("select * from NewCourse where id = %d", C_id);
                    ResultSet result = st.executeQuery(query);
                    result.next();
                    editCourseNameBox.setText(result.getString("course_name"));
                    editCourseCodeBox.setText(result.getString("course_code"));
                    String text = result.getString("duration");
                    String output = text.replaceAll("[^0-9]", "");
                    editCourseDurationBox.setText(output);
                    editCourseFeeBox.setText(Integer.toString(result.getInt("course_fee")));
                    editCourseDescriptionBox.setText(result.getString("course_description"));

                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                editCoursePanel.setVisible(true);
            }
        });


        RoundedPanel viewCourseDetailsPanel = new RoundedPanel(30);
        viewCourseDetailsPanel.setBounds(20, 68, 990, 473);
        viewCourseDetailsPanel.setBackground(Color.WHITE);
        viewCourseDetailsPanel.setLayout(null);
        viewCourseDetailsPanel.add(viewCourseInfoText);
        viewCourseDetailsPanel.add(viewCourseLine);
        viewCourseDetailsPanel.add(id_course);
        viewCourseDetailsPanel.add(id_course_ans);
        viewCourseDetailsPanel.add(C_Name);
        viewCourseDetailsPanel.add(C_Name_ans);
        viewCourseDetailsPanel.add(C_Code);
        viewCourseDetailsPanel.add(C_Code_ans);
        viewCourseDetailsPanel.add(C_Duration);
        viewCourseDetailsPanel.add(C_Duration_ans);
        viewCourseDetailsPanel.add(C_fee);
        viewCourseDetailsPanel.add(C_fee_ans);
        viewCourseDetailsPanel.add(courseIsActive);
        viewCourseDetailsPanel.add(courseIsActive_ans);
        viewCourseDetailsPanel.add(C_Description);
        viewCourseDetailsPanel.add(C_Description_ans);
        viewCourseDetailsPanel.add(viewCourseDownLine);
        viewCourseDetailsPanel.add(editCourseBtn);


        viewCoursePanel.setBounds(250, 70, 1030, 650);
        viewCoursePanel.setBackground(new Color(240, 239, 255));
        viewCoursePanel.setLayout(null);
        viewCoursePanel.add(viewCourseText);
        viewCoursePanel.add(viewCourseBackToList);
        viewCoursePanel.add(viewCourseDetailsPanel);
        viewCoursePanel.setVisible(false);


        //=========== Course Text ==========
        JLabel Course = new JLabel("Courses");
        Course.setFont(new Font("Century Gothic", Font.BOLD, 33));
        Course.setForeground(new Color(33, 37, 40));
        Course.setBounds(20, 20, 200, 40);
        //=========== Course Text ==========

        //============ ADD Course ================
        JLabel plusCourseIcon = new JLabel("+");
        plusCourseIcon.setBounds(0, 18, 30, 30);
        plusCourseIcon.setForeground(Color.WHITE);
        plusCourseIcon.setFont(new Font("Montserrat", Font.PLAIN, 25));


        AnimatedButton addCourseBtn = new AnimatedButton("     Add New Course", 20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        addCourseBtn.setBounds(830, 18, 180, 40);
        addCourseBtn.setForeground(Color.WHITE);
        addCourseBtn.setFont(new Font("Montserrat", Font.PLAIN, 14));
        addCourseBtn.add(plusCourseIcon);
        addCourseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addCourseBtn.setBounds(830, 18, 180, 40);
                addCourseBtn.setBounds(830, 17, 180, 40);
                addCourseBtn.setBounds(830, 16, 180, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addCourseBtn.setBounds(830, 16, 180, 40);
                addCourseBtn.setBounds(830, 17, 180, 40);
                addCourseBtn.setBounds(830, 18, 180, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                coursePanel.setVisible(false);
                addNewCoursePanel.setVisible(true);
            }

        });


        JLabel addNewCourse = new JLabel("Add New Course");
        addNewCourse.setFont(new Font("Century Gothic", Font.BOLD, 33));
        addNewCourse.setForeground(new Color(33, 37, 40));
        addNewCourse.setBounds(20, 20, 300, 40);

        //============ Back to List ================
        AnimatedButton backToListCourse = new AnimatedButton(" Back to List", 20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        backToListCourse.setBounds(860, 18, 150, 40);
        backToListCourse.setForeground(Color.WHITE);
        backToListCourse.setFont(new Font("Montserrat", Font.PLAIN, 14));
        backToListCourse.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        backToListCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backToListCourse.setBounds(860, 18, 150, 40);
                backToListCourse.setBounds(860, 17, 150, 40);
                backToListCourse.setBounds(860, 16, 150, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backToListCourse.setBounds(860, 16, 150, 40);
                backToListCourse.setBounds(860, 17, 150, 40);
                backToListCourse.setBounds(860, 18, 150, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                addNewCoursePanel.setVisible(false);
                coursePanel.setVisible(true);
            }
        });


        JLabel courseDetails = new JLabel("Course Details");
        courseDetails.setBounds(40, 17, 150, 30);
        courseDetails.setFont(new Font("Montserrat", Font.BOLD, 14));
        courseDetails.setForeground(Color.darkGray);

        JLabel addCourseLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        addCourseLine.setBounds(0, 43, 990, 20);
        addCourseLine.setFont(new Font("Arial", Font.BOLD, 14));
        addCourseLine.setForeground(new Color(240, 240, 240));

        JLabel courseName = new JLabel("Course Name");
        courseName.setForeground(new Color(120, 120, 120));
        courseName.setFont(new Font("Montserrat", Font.BOLD, 14));
        courseName.setBounds(20, 72, 150, 30);

        RoundedTextField courseNameBox = new RoundedTextField("e.g. Web Development", 10);
        courseNameBox.setBounds(20, 104, 465, 40);
        courseNameBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        courseNameBox.setForeground(new Color(105, 105, 105));

        JLabel courseCode = new JLabel("Course Code");
        courseCode.setForeground(new Color(120, 120, 120));
        courseCode.setFont(new Font("Montserrat", Font.BOLD, 14));
        courseCode.setBounds(505, 72, 150, 30);

        RoundedTextField courseCodeBox = new RoundedTextField("e.g. WEB101", 10);
        courseCodeBox.setBounds(505, 104, 465, 40);
        courseCodeBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        courseCodeBox.setForeground(new Color(105, 105, 105));

        JLabel courseDuration = new JLabel("Duration");
        courseDuration.setForeground(new Color(120, 120, 120));
        courseDuration.setFont(new Font("Montserrat", Font.BOLD, 14));
        courseDuration.setBounds(20, 152, 150, 30);

        RoundedTextField courseDurationBox = new RoundedTextField("e.g. 6 Months", 10);
        courseDurationBox.setBounds(20, 184, 465, 40);
        courseDurationBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        courseDurationBox.setForeground(new Color(105, 105, 105));

        JLabel courseFee = new JLabel("Course Fee");
        courseFee.setForeground(new Color(120, 120, 120));
        courseFee.setFont(new Font("Montserrat", Font.BOLD, 14));
        courseFee.setBounds(505, 152, 150, 30);

        RoundedTextField courseFeeBox = new RoundedTextField("e.g. $1200", 10);
        courseFeeBox.setBounds(505, 184, 465, 40);
        courseFeeBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        courseFeeBox.setForeground(new Color(105, 105, 105));

        JLabel courseDescription = new JLabel("Description");
        courseDescription.setForeground(new Color(120, 120, 120));
        courseDescription.setFont(new Font("Montserrat", Font.BOLD, 14));
        courseDescription.setBounds(20, 232, 150, 30);

        RoundedTextField courseDescriptionBox = new RoundedTextField("Enter course description...", 10);
        courseDescriptionBox.setBounds(20, 264, 950, 100);
        courseDescriptionBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        courseDescriptionBox.setForeground(new Color(105, 105, 105));


        AnimatedButton resetCourseBtn = new AnimatedButton("Reset", 40,
                new Color(240, 239, 255),    // normal Color (light off-white)
                new Color(220, 218, 235),    // Hovered Color (slightly darker)
                new Color(200, 198, 215),    // Pressed Color (even darker)
                new Color(240, 239, 255));
        resetCourseBtn.setBounds(710, 380, 120, 40);
        resetCourseBtn.setForeground(Color.darkGray);
        resetCourseBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
        resetCourseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetCourseBtn.setBounds(710, 380, 120, 40);
                resetCourseBtn.setBounds(710, 379, 120, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetCourseBtn.setBounds(710, 379, 120, 40);
                resetCourseBtn.setBounds(710, 380, 120, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                courseNameBox.setText("");
                courseCodeBox.setText("");
                courseDurationBox.setText("");
                courseFeeBox.setText("");
                courseDescriptionBox.setText("");
            }
        });


        AnimatedButton addBtn_Course = new AnimatedButton("Add", 40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        addBtn_Course.setBounds(850, 380, 120, 40);
        addBtn_Course.setForeground(Color.WHITE);
        addBtn_Course.setFont(new Font("Montserrat", Font.BOLD, 14));
        addBtn_Course.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addBtn_Course.setBounds(850, 380, 120, 40);
                addBtn_Course.setBounds(850, 379, 120, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addBtn_Course.setBounds(850, 379, 120, 40);
                addBtn_Course.setBounds(850, 380, 120, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String course_Name = courseNameBox.getText().trim();
                String course_Code = courseCodeBox.getText().trim();
                String course_Duration = courseDurationBox.getText().trim();
                String course_Fee = courseFeeBox.getText().trim();
                String Description = courseDescriptionBox.getText().trim();

                if (course_Name.isEmpty() || course_Code.isEmpty() || course_Duration.isEmpty() || course_Fee.isEmpty() || Description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                } else if (!course_Duration.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Duration must be numeric!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    courseDurationBox.requestFocus();
                } else if (!course_Fee.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Fee must be numeric!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    courseFeeBox.requestFocus();
                } else if (Integer.parseInt(course_Duration) <= 0 || Integer.parseInt(course_Duration) > 60) {
                    JOptionPane.showMessageDialog(null, "Duration must be between 1 and 60 months!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    courseDurationBox.requestFocus();
                } else if (Integer.parseInt(course_Fee) <= 0 || Integer.parseInt(course_Fee) > 500000) {
                    JOptionPane.showMessageDialog(null, "Fee is out of valid range!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    courseFeeBox.requestFocus();
                } else {
                    try {
                        Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = con.createStatement();
                        String query = String.format("Insert into NewCourse(course_name,course_code,duration,course_fee,course_description) values ('%s','%s','%s',%d,'%s')"
                                , course_Name, course_Code, course_Duration + " Months", Integer.parseInt(course_Fee), Description);
                        st.executeUpdate(query);
                        courseNameBox.setText("");
                        courseCodeBox.setText("");
                        courseDurationBox.setText("");
                        courseFeeBox.setText("");
                        courseDescriptionBox.setText("");
                        addNewCoursePanel.setVisible(false);
                        Statement statement = con.createStatement(
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY
                        );
                        String query2 = "select * from NewCourse";
                        ResultSet result = statement.executeQuery(query2);
                        if (result.last()) {
                            Object[] newData = {
                                    result.getInt("id"),
                                    result.getString("course_name"),
                                    result.getString("course_code"),
                                    result.getString("duration"),
                                    result.getInt("course_fee") + " PKR"};
                            courseTableModel.addRow(newData);
                        }
                        coursePanel.setVisible(true);
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });


        RoundedPanel addCoursePanel = new RoundedPanel(30);
        addCoursePanel.setBounds(20, 68, 990, 436);
        addCoursePanel.setBackground(Color.WHITE);
        addCoursePanel.setLayout(null);
        addCoursePanel.add(courseDetails);
        addCoursePanel.add(addCourseLine);
        addCoursePanel.add(courseName);
        addCoursePanel.add(courseNameBox);
        addCoursePanel.add(courseCode);
        addCoursePanel.add(courseCodeBox);
        addCoursePanel.add(courseDuration);
        addCoursePanel.add(courseDurationBox);
        addCoursePanel.add(courseFee);
        addCoursePanel.add(courseFeeBox);
        addCoursePanel.add(courseDescription);
        addCoursePanel.add(courseDescriptionBox);
        addCoursePanel.add(resetCourseBtn);
        addCoursePanel.add(addBtn_Course);


        addNewCoursePanel.setBounds(250, 70, 1030, 650);
        addNewCoursePanel.setBackground(new Color(240, 239, 255));
        addNewCoursePanel.setLayout(null);
        addNewCoursePanel.add(addNewCourse);
        addNewCoursePanel.add(backToListCourse);
        addNewCoursePanel.add(addCoursePanel);
        addNewCoursePanel.setVisible(false);


        //================= Search Button ================

        ImageIcon searchCourseIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\search.png", 26, 26);
        JButton searchCourseBtn = new JButton();
        searchCourseBtn.setLayout(null);
        searchCourseBtn.setBounds(560, 9, 26, 26);
        searchCourseBtn.setContentAreaFilled(false);
        searchCourseBtn.setFocusable(false);
        searchCourseBtn.setBorder(null);
        searchCourseBtn.setCursor(new Cursor(12));
        searchCourseBtn.setIcon(searchCourseIcon);


        RoundedTextField courseSearchBar = new RoundedTextField("Search", 44
                , new Color(240, 239, 255)
                , new Color(134, 73, 255)
                , new Color(134, 73, 255));
        courseSearchBar.setBounds(20, 13, 600, 44);
        courseSearchBar.setFont(new Font("Montserrat", Font.PLAIN, 14));
        courseSearchBar.setBackground(new Color(240, 239, 255));
        courseSearchBar.add(searchCourseBtn);
        courseSearchBar.setCaretColor(new Color(134, 73, 255));

        searchCourseBtn.addActionListener(e -> {
            String searchText = courseSearchBar.getText();
            filterTable(searchText, courseTable, courseTableModel);
        });


        AnimatedButton viewCourse = new AnimatedButton("👁️", 44,
                new Color(148, 240, 151),   // Normal Color (light green)
                new Color(126, 220, 129),   // Hover Color
                new Color(104, 196, 107),   // Pressed Color
                new Color(84, 176, 87));  // Border Color
        viewCourse.setBounds(813, 13, 44, 44);
        viewCourse.setFont(new Font("", Font.PLAIN, 25));
        viewCourse.setMargin(new Insets(0, 0, 0, 0));
        viewCourse.setFocusable(false);
        viewCourse.setForeground(new Color(84, 176, 87));
        viewCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewCourse.setBounds(813, 13, 44, 44);
                viewCourse.setBounds(813, 12, 44, 44);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewCourse.setBounds(813, 12, 44, 44);
                viewCourse.setBounds(813, 13, 44, 44);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (courseTable.getSelectedRow() >= 0) {
                    coursePanel.setVisible(false);
                    int CourseId = Integer.parseInt(courseTableModel.getValueAt(courseTable.getSelectedRow(), 0).toString());
                    try {
                        Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = con.createStatement();
                        String query = String.format("select * from NewCourse where id = %d", CourseId);
                        ResultSet result = st.executeQuery(query);
                        result.next();
                        id_course_ans.setText(Integer.toString(result.getInt("id")));
                        C_Name_ans.setText(result.getString("course_name"));
                        C_Code_ans.setText(result.getString("course_code"));
                        C_Duration_ans.setText(result.getString("duration"));
                        C_fee_ans.setText(result.getInt("course_fee") + "/=");
                        C_Description_ans.setText(result.getString("course_description"));

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    viewCoursePanel.setVisible(true);
                }
            }
        });


        ImageIcon editCourseIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\edit.png", 20, 20);
        AnimatedButton editCourse = new AnimatedButton(null, 44,
                new Color(251, 219, 179),   // Normal Color (light peach)
                new Color(235, 203, 163),   // Hover Color
                new Color(210, 178, 138),   // Pressed Color
                new Color(176, 144, 104));    // Border Color// Border Color (unchanged)
        editCourse.setBounds(869, 13, 44, 44);
        editCourse.setFont(new Font("", Font.BOLD, 19));
        editCourse.setMargin(new Insets(0, 0, 0, 0));
        editCourse.setFocusable(false);
        editCourse.setLayout(new GridBagLayout());
        editCourse.setIcon(editCourseIcon);
        editCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editCourse.setBounds(869, 13, 44, 44);
                editCourse.setBounds(869, 12, 44, 44);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editCourse.setBounds(869, 12, 44, 44);
                editCourse.setBounds(869, 13, 44, 44);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (courseTable.getSelectedRow() >= 0) {
                    coursePanel.setVisible(false);
                    int C_id = Integer.parseInt(courseTableModel.getValueAt(courseTable.getSelectedRow(), 0).toString());
                    try {
                        Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = con.createStatement();
                        String query = String.format("select * from NewCourse where id = %d", C_id);
                        ResultSet result = st.executeQuery(query);
                        result.next();
                        editCourseNameBox.setText(result.getString("course_name"));
                        editCourseCodeBox.setText(result.getString("course_code"));
                        String text = result.getString("duration");
                        String output = text.replaceAll("[^0-9]", "");
                        editCourseDurationBox.setText(output);
                        editCourseFeeBox.setText(Integer.toString(result.getInt("course_fee")));
                        editCourseDescriptionBox.setText(result.getString("course_description"));

                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    editCoursePanel.setVisible(true);
                }
            }
        });


        ImageIcon deleteCourseIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\delete.png", 18, 18);
        AnimatedButton delCourse = new AnimatedButton(null, 44,
                new Color(165, 176, 254),   // Normal Color (light periwinkle blue)
                new Color(145, 156, 234),   // Hover Color
                new Color(130, 141, 219),   // Pressed Color
                new Color(115, 126, 204));    // Border Color
        delCourse.setBounds(924, 13, 44, 44);
        delCourse.setFont(new Font("", Font.BOLD, 20));
        delCourse.setMargin(new Insets(0, 0, 0, 0));
        delCourse.setFocusable(false);
        delCourse.setLayout(new GridBagLayout());
        delCourse.setIcon(deleteCourseIcon);
        delCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                delCourse.setBounds(924, 13, 44, 44);
                delCourse.setBounds(924, 12, 44, 44);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                delCourse.setBounds(924, 12, 44, 44);
                delCourse.setBounds(924, 13, 44, 44);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = courseTable.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = con.createStatement();
                        int course_id = Integer.parseInt(courseTableModel.getValueAt(selectedRow, 0).toString());
                        String query = String.format("Delete from NewCourse where id = %d", course_id);
                        st.executeUpdate(query);
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    courseTableModel.removeRow(selectedRow);
                    int rowCount = courseTable.getRowCount();
                    if (rowCount > 0) {
                        if (selectedRow >= rowCount) {
                            selectedRow = rowCount - 1;
                        }
                        courseTable.setRowSelectionInterval(selectedRow, selectedRow);
                        courseTable.scrollRectToVisible(courseTable.getCellRect(selectedRow, 0, true));
                    }
                }
            }
        });


        RoundedPanel buttonBelowCoursePanel = new RoundedPanel(20);
        buttonBelowCoursePanel.setBackground(Color.WHITE);
        buttonBelowCoursePanel.setBounds(20, 68, 990, 70);
        buttonBelowCoursePanel.setLayout(null);
        buttonBelowCoursePanel.add(viewCourse);
        buttonBelowCoursePanel.add(editCourse);
        buttonBelowCoursePanel.add(delCourse);
        buttonBelowCoursePanel.add(courseSearchBar);


        //================= Search Button Ends Here ============


        courseTable.setForeground(Color.GRAY);
        courseTable.setShowGrid(false);
        courseTable.setDefaultEditor(Object.class, null);
        courseTable.getColumnModel().getColumn(0).setPreferredWidth(55);   // ID
        courseTable.getColumnModel().getColumn(1).setPreferredWidth(325);  // Course Name
        courseTable.getColumnModel().getColumn(2).setPreferredWidth(130);  // Course Code
        courseTable.getColumnModel().getColumn(3).setPreferredWidth(130);  // Fees
        courseTable.getColumnModel().getColumn(4).setPreferredWidth(130);  // Duration
        courseTable.setRowHeight(55);
        courseTable.getTableHeader().setResizingAllowed(false);
        courseTable.getTableHeader().setReorderingAllowed(false);
        courseTable.setBorder(null);
        courseTable.setIntercellSpacing(new Dimension(0, 0));


        final int[] hoverRow1 = {-1};
        courseTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = courseTable.rowAtPoint(e.getPoint());

                if (hoverRow1[0] != row) {
                    hoverRow1[0] = row;
                    courseTable.repaint();
                }
            }
        });

        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoverRow1[0] = -1;
                courseTable.repaint();
            }
        });


        courseTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable courseTable, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                // Use super to get the basic setup
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                setOpaque(true);
                setFont(new Font("Montserrat", Font.PLAIN, 13));
                setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                setHorizontalAlignment(JLabel.LEFT);
                setForeground(Color.DARK_GRAY);

                // HANDLE SELECTION STYLE
                if (isSelected) {
                    setBackground(new Color(230, 230, 255));
                    setFont(new Font("Montserrat", Font.BOLD, 13));
                } else if (row == hoverRow1[0]) {
                    setBackground(new Color(230, 230, 255, 100)); // hover color
                    setFont(new Font("Montserrat", Font.BOLD, 13));
                } else {

                    if (row % 2 == 0) {
                        setBackground(new Color(250, 250, 250));
                    } else {
                        setBackground(Color.WHITE);
                    }
                }
                return this;
            }
        });


        // ================= HEADER STYLE =================
        JTableHeader courseTableHeader = courseTable.getTableHeader();
        courseTableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable courseTable, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                JLabel label = new JLabel(value.toString());
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                label.setForeground(Color.GRAY);
                label.setFont(new Font("Montserrat", Font.BOLD, 12));
                label.setHorizontalAlignment(JLabel.LEFT);
                label.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

                return label;
            }
        });


        RoundScrollPane courseData = new RoundScrollPane(courseTable, 30);
        courseData.setBackground(Color.WHITE);
        courseData.setBounds(0, 40, 990, 400);
        courseData.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        courseData.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        courseData.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        courseData.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        //================== Student Data table Ends Here==================

        JLabel courseList = new JLabel("Course List");
        courseList.setBounds(40, 17, 100, 30);
        courseList.setFont(new Font("Montserrat", Font.BOLD, 14));
        courseList.setForeground(Color.darkGray);

        JLabel courseline = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        courseline.setBounds(0, 43, 990, 20);
        courseline.setFont(new Font("Arial", Font.BOLD, 14));
        courseline.setForeground(new Color(240, 240, 240));

        RoundedPanel courseTablePanel = new RoundedPanel(30);
        courseTablePanel.setBounds(20, 150, 990, 440);
        courseTablePanel.setBackground(Color.WHITE);
        courseTablePanel.setLayout(null);
        courseTablePanel.add(courseList);
        courseTablePanel.add(courseline);
        courseTablePanel.add(courseData);
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                courseTablePanel.setBounds(20, 150, 990, 440);
                courseTablePanel.setBounds(20, 149, 990, 440);
                courseTablePanel.setBounds(20, 148, 990, 440);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                courseTablePanel.setBounds(20, 148, 990, 440);
                courseTablePanel.setBounds(20, 149, 990, 440);
                courseTablePanel.setBounds(20, 150, 990, 440);
            }
        });

        coursePanel.setBounds(250, 70, 1030, 650);
        coursePanel.setBackground(new Color(240, 239, 255));
        coursePanel.setLayout(null);
        coursePanel.add(Course);
        coursePanel.add(addCourseBtn);
        coursePanel.add(buttonBelowCoursePanel);
        coursePanel.add(courseTablePanel);
        coursePanel.setVisible(false);

        //=========== Course Panel Ends Here ================


        //======================== edit student ========================
        JLabel editStudentText = new JLabel("Edit Student");
        editStudentText.setFont(new Font("Century Gothic", Font.BOLD, 33));
        editStudentText.setForeground(new Color(33, 37, 40));
        editStudentText.setBounds(20, 20, 300, 40);

        AnimatedButton editBackToList = new AnimatedButton("  Back to List", 20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        editBackToList.setBounds(860, 18, 150, 40);
        editBackToList.setForeground(Color.WHITE);
        editBackToList.setFont(new Font("Montserrat", Font.PLAIN, 14));
        editBackToList.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        editBackToList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editBackToList.setBounds(860, 18, 150, 40);
                editBackToList.setBounds(860, 17, 150, 40);
                editBackToList.setBounds(860, 16, 150, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editBackToList.setBounds(860, 16, 150, 40);
                editBackToList.setBounds(860, 17, 150, 40);
                editBackToList.setBounds(860, 18, 150, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                editStuPanel.setVisible(false);
                studentPanel.setVisible(true);
            }
        });

        JLabel editStuDetails = new JLabel("Edit Student Details");
        editStuDetails.setBounds(40, 17, 200, 30);
        editStuDetails.setFont(new Font("Montserrat", Font.BOLD, 14));
        editStuDetails.setForeground(Color.darkGray);

        JLabel editStuLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        editStuLine.setBounds(0, 43, 990, 20);
        editStuLine.setFont(new Font("Arial", Font.BOLD, 14));
        editStuLine.setForeground(new Color(240, 240, 240));


        JLabel firstNameEdit = new JLabel("First Name");
        firstNameEdit.setForeground(new Color(120, 120, 120));
        firstNameEdit.setFont(new Font("Montserrat", Font.BOLD, 14));
        firstNameEdit.setBounds(20, 72, 150, 30);

        RoundedTextField firstNameEditBox = new RoundedTextField("e.g. John", 10);
        firstNameEditBox.setBounds(20, 104, 465, 40);
        firstNameEditBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        firstNameEditBox.setForeground(new Color(105, 105, 105));


        JLabel lastNameEdit = new JLabel("Last Name");
        lastNameEdit.setForeground(new Color(120, 120, 120));
        lastNameEdit.setFont(new Font("Montserrat", Font.BOLD, 14));
        lastNameEdit.setBounds(505, 72, 150, 30);

        RoundedTextField lastNameEditBox = new RoundedTextField("e.g. Doe", 10);
        lastNameEditBox.setBounds(505, 104, 465, 40);
        lastNameEditBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        lastNameEditBox.setForeground(new Color(105, 105, 105));


        JLabel emailAddressEdit = new JLabel("Email Address");
        emailAddressEdit.setForeground(new Color(120, 120, 120));
        emailAddressEdit.setFont(new Font("Montserrat", Font.BOLD, 14));
        emailAddressEdit.setBounds(20, 152, 150, 30);

        RoundedTextField emailAddEditBox = new RoundedTextField("e.g. john123@gmail.com", 10);
        emailAddEditBox.setBounds(20, 184, 465, 40);
        emailAddEditBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        emailAddEditBox.setForeground(new Color(105, 105, 105));


        JLabel phoneEdit = new JLabel("Phone Number");
        phoneEdit.setForeground(new Color(120, 120, 120));
        phoneEdit.setFont(new Font("Montserrat", Font.BOLD, 14));
        phoneEdit.setBounds(505, 152, 150, 30);

        RoundedTextField phoneEditBox = new RoundedTextField("e.g. +92 3012345678", 10);
        phoneEditBox.setBounds(505, 184, 465, 40);
        phoneEditBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        phoneEditBox.setForeground(new Color(105, 105, 105));


        JLabel ageEdit = new JLabel("Age");
        ageEdit.setForeground(new Color(120, 120, 120));
        ageEdit.setFont(new Font("Montserrat", Font.BOLD, 14));
        ageEdit.setBounds(20, 232, 150, 30);

        RoundedTextField ageEditBox = new RoundedTextField("e.g. 19", 10);
        ageEditBox.setBounds(20, 264, 465, 40);
        ageEditBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        ageEditBox.setForeground(new Color(105, 105, 105));


        JLabel genderEdit = new JLabel("Gender");
        genderEdit.setForeground(new Color(120, 120, 120));
        genderEdit.setFont(new Font("Montserrat", Font.BOLD, 14));
        genderEdit.setBounds(505, 232, 150, 30);


        RoundedTextField genderEditBox = new RoundedTextField("e.g. Male", 10);
        genderEditBox.setBounds(505, 264, 465, 40);
        genderEditBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        genderEditBox.setForeground(new Color(105, 105, 105));


        JLabel addressEdit = new JLabel("Address");
        addressEdit.setForeground(new Color(120, 120, 120));
        addressEdit.setFont(new Font("Montserrat", Font.BOLD, 14));
        addressEdit.setBounds(20, 312, 150, 30);


        RoundedTextField addressEditBox = new RoundedTextField("Enter full address...", 10);
        addressEditBox.setBounds(20, 342, 950, 100);
        addressEditBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        addressEditBox.setForeground(new Color(105, 105, 105));


        AnimatedButton cancelEditBtn = new AnimatedButton("Cancel", 40,
                new Color(240, 239, 255),    // normal Color (light off-white)
                new Color(220, 218, 235),    // Hovered Color (slightly darker)
                new Color(200, 198, 215),    // Pressed Color (even darker)
                new Color(240, 239, 255));   // Border Color (same as normal)
        cancelEditBtn.setBounds(710, 458, 120, 40);
        cancelEditBtn.setForeground(Color.darkGray);
        cancelEditBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
        cancelEditBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelEditBtn.setBounds(710, 458, 120, 40);
                cancelEditBtn.setBounds(710, 457, 120, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelEditBtn.setBounds(710, 457, 120, 40);
                cancelEditBtn.setBounds(710, 458, 120, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                firstNameEditBox.setText("");
                lastNameEditBox.setText("");
                emailAddEditBox.setText("");
                phoneEditBox.setText("");
                ageEditBox.setText("");
                genderEditBox.setText("");
                addressEditBox.setText("");
            }
        });


        AnimatedButton editStuBtn = new AnimatedButton("Edit", 40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        editStuBtn.setBounds(850, 458, 120, 40);
        editStuBtn.setForeground(Color.WHITE);
        editStuBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
        editStuBtn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                editStuBtn.setBounds(850, 458, 120, 40);
                editStuBtn.setBounds(850, 457, 120, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editStuBtn.setBounds(850, 457, 120, 40);
                editStuBtn.setBounds(850, 458, 120, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String studentId = table.getValueAt(table.getSelectedRow(), 0).toString();

                String f_name = firstNameEditBox.getText();
                String l_name = lastNameEditBox.getText();
                String stu_email = emailAddEditBox.getText();
                String stu_phone = phoneEditBox.getText();
                String stu_age = ageEditBox.getText();
                String stu_gender = genderEditBox.getText();
                String stu_address = addressEditBox.getText();

                if (f_name.isEmpty() || l_name.isEmpty() || stu_email.isEmpty() || stu_phone.isEmpty()
                        || stu_age.isEmpty() || stu_gender.isEmpty() || stu_address.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                } else if (!stu_email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Invalid email format!\nExample: name@domain.com"
                            , "Validation Error", JOptionPane.WARNING_MESSAGE);
                    emailAddEditBox.requestFocus();
                } else if (!stu_phone.matches("[0-9+]{11,13}")) {
                    JOptionPane.showMessageDialog(null, "Invalid phone number!\ne.g. 03001234567 or +923001234567"
                            , "Validation Error", JOptionPane.WARNING_MESSAGE);
                    phoneEditBox.requestFocus();
                } else if (!stu_age.matches("[0-9]{1,2}")) {
                    JOptionPane.showMessageDialog(null, "Invalid age!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    ageEditBox.requestFocus();
                } else if (!stu_gender.matches("(?i)^(male|female|other)$")) {
                    JOptionPane.showMessageDialog(null, "Invalid gender!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    genderEditBox.requestFocus();
                } else {
                    try {
                        Connection connection = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = connection.createStatement();

                        String query = String.format("update NewStudent set firstname = '%s',lastname = '%s',email = '%s', phone='%s',age='%s',gender = '%s',address = '%s' where stu_id = '%s'"
                                , f_name, l_name, stu_email, stu_phone, stu_age, stu_gender, stu_address, studentId);
                        st.executeUpdate(query);
                        connection.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    model.setValueAt(f_name + " " + l_name, table.getSelectedRow(), 1);
                    model.setValueAt(stu_email, table.getSelectedRow(), 2);
                    model.setValueAt(stu_age, table.getSelectedRow(), 3);
                    model.setValueAt(stu_phone, table.getSelectedRow(), 4);

                    editStuPanel.setVisible(false);
                    studentPanel.setVisible(true);

                    firstNameEditBox.setText("");
                    lastNameEditBox.setText("");
                    emailAddEditBox.setText("");
                    phoneEditBox.setText("");
                    ageEditBox.setText("");
                    genderEditBox.setText("");
                    addressEditBox.setText("");
                }
            }
        });


        RoundedPanel editStuDetailsPanel = new RoundedPanel(30);
        editStuDetailsPanel.setBounds(20, 68, 990, 515);
        editStuDetailsPanel.setBackground(Color.WHITE);
        editStuDetailsPanel.setLayout(null);
        editStuDetailsPanel.add(editStuDetails);
        editStuDetailsPanel.add(editStuLine);
        editStuDetailsPanel.add(firstNameEdit);
        editStuDetailsPanel.add(firstNameEditBox);
        editStuDetailsPanel.add(lastNameEdit);
        editStuDetailsPanel.add(lastNameEditBox);
        editStuDetailsPanel.add(emailAddressEdit);
        editStuDetailsPanel.add(emailAddEditBox);
        editStuDetailsPanel.add(phoneEdit);
        editStuDetailsPanel.add(phoneEditBox);
        editStuDetailsPanel.add(ageEdit);
        editStuDetailsPanel.add(ageEditBox);
        editStuDetailsPanel.add(genderEdit);
        editStuDetailsPanel.add(genderEditBox);
        editStuDetailsPanel.add(addressEdit);
        editStuDetailsPanel.add(addressEditBox);
        editStuDetailsPanel.add(cancelEditBtn);
        editStuDetailsPanel.add(editStuBtn);

        editStuPanel.setBounds(250, 70, 1030, 650);
        editStuPanel.setBackground(new Color(240, 239, 255));
        editStuPanel.setLayout(null);
        editStuPanel.add(editStudentText);
        editStuPanel.add(editStuDetailsPanel);
        editStuPanel.add(editBackToList);
        editStuPanel.setVisible(false);
        //======================== edit student ends here================


        //===================== view Student==========================
        JLabel viewStudentText = new JLabel("Student Details");
        viewStudentText.setFont(new Font("Century Gothic", Font.BOLD, 33));
        viewStudentText.setForeground(new Color(33, 37, 40));
        viewStudentText.setBounds(20, 20, 300, 40);
        viewStudentText.setVerticalAlignment(JLabel.BOTTOM);


        AnimatedButton viewBackToList = new AnimatedButton("  Back to List", 20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        viewBackToList.setBounds(860, 18, 150, 40);
        viewBackToList.setForeground(Color.WHITE);
        viewBackToList.setFont(new Font("Montserrat", Font.PLAIN, 14));
        viewBackToList.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        viewBackToList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewBackToList.setBounds(860, 18, 150, 40);
                viewBackToList.setBounds(860, 17, 150, 40);
                viewBackToList.setBounds(860, 16, 150, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewBackToList.setBounds(860, 16, 150, 40);
                viewBackToList.setBounds(860, 17, 150, 40);
                viewBackToList.setBounds(860, 18, 150, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                viewStuPanel.setVisible(false);
                studentPanel.setVisible(true);
            }
        });

        JLabel viewStuDetails = new JLabel("Information");
        viewStuDetails.setBounds(40, 17, 200, 30);
        viewStuDetails.setFont(new Font("Montserrat", Font.BOLD, 14));
        viewStuDetails.setForeground(Color.darkGray);

        JLabel viewStuLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        viewStuLine.setBounds(0, 43, 990, 20);
        viewStuLine.setFont(new Font("Arial", Font.BOLD, 14));
        viewStuLine.setForeground(new Color(240, 240, 240));

        JLabel id_stu = new JLabel("Student ID:");
        id_stu.setFont(new Font("Montserrat", Font.BOLD, 14));
        id_stu.setForeground(Color.darkGray);
        id_stu.setBorder(null);
        id_stu.setBackground(null);
        id_stu.setVerticalAlignment(JLabel.CENTER);
        id_stu.setBounds(40, 90, 150, 30);

        JLabel id_stu_ans = new JLabel();
        id_stu_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        id_stu_ans.setForeground(Color.darkGray);
        id_stu_ans.setBorder(null);
        id_stu_ans.setBackground(null);
        id_stu_ans.setVerticalAlignment(JLabel.CENTER);
        id_stu_ans.setBounds(290, 90, 200, 30);

        JLabel f_Name = new JLabel("Full Name:");
        f_Name.setFont(new Font("Montserrat", Font.BOLD, 14));
        f_Name.setForeground(Color.darkGray);
        f_Name.setBorder(null);
        f_Name.setBackground(null);
        f_Name.setVerticalAlignment(JLabel.CENTER);
        f_Name.setBounds(40, 132, 150, 30);

        JLabel f_Name_ans = new JLabel();
        f_Name_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        f_Name_ans.setForeground(Color.darkGray);
        f_Name_ans.setBorder(null);
        f_Name_ans.setBackground(null);
        f_Name_ans.setVerticalAlignment(JLabel.CENTER);
        f_Name_ans.setBounds(290, 132, 300, 30);

        JLabel e_address = new JLabel("Email Address:");
        e_address.setFont(new Font("Montserrat", Font.BOLD, 14));
        e_address.setForeground(Color.darkGray);
        e_address.setBorder(null);
        e_address.setBackground(null);
        e_address.setVerticalAlignment(JLabel.CENTER);
        e_address.setBounds(40, 174, 150, 30);

        JLabel e_address_ans = new JLabel();
        e_address_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        e_address_ans.setForeground(Color.darkGray);
        e_address_ans.setBorder(null);
        e_address_ans.setBackground(null);
        e_address_ans.setVerticalAlignment(JLabel.CENTER);
        e_address_ans.setBounds(290, 174, 400, 30);

        JLabel phone_number = new JLabel("Phone Number:");
        phone_number.setFont(new Font("Montserrat", Font.BOLD, 14));
        phone_number.setForeground(Color.darkGray);
        phone_number.setBorder(null);
        phone_number.setBackground(null);
        phone_number.setVerticalAlignment(JLabel.CENTER);
        phone_number.setBounds(40, 216, 150, 30);

        JLabel phone_number_ans = new JLabel();
        phone_number_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        phone_number_ans.setForeground(Color.darkGray);
        phone_number_ans.setBorder(null);
        phone_number_ans.setBackground(null);
        phone_number_ans.setVerticalAlignment(JLabel.CENTER);
        phone_number_ans.setBounds(290, 216, 300, 30);

        JLabel stu_age = new JLabel("Age:");
        stu_age.setFont(new Font("Montserrat", Font.BOLD, 14));
        stu_age.setForeground(Color.darkGray);
        stu_age.setBorder(null);
        stu_age.setBackground(null);
        stu_age.setVerticalAlignment(JLabel.CENTER);
        stu_age.setBounds(40, 258, 150, 30);

        JLabel stu_age_ans = new JLabel();
        stu_age_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        stu_age_ans.setForeground(Color.darkGray);
        stu_age_ans.setBorder(null);
        stu_age_ans.setBackground(null);
        stu_age_ans.setVerticalAlignment(JLabel.CENTER);
        stu_age_ans.setBounds(290, 258, 150, 30);

        JLabel stu_gender = new JLabel("Gender:");
        stu_gender.setFont(new Font("Montserrat", Font.BOLD, 14));
        stu_gender.setForeground(Color.darkGray);
        stu_gender.setBorder(null);
        stu_gender.setBackground(null);
        stu_gender.setVerticalAlignment(JLabel.CENTER);
        stu_gender.setBounds(40, 300, 150, 30);

        JLabel stu_gender_ans = new JLabel();
        stu_gender_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        stu_gender_ans.setForeground(Color.darkGray);
        stu_gender_ans.setBorder(null);
        stu_gender_ans.setBackground(null);
        stu_gender_ans.setVerticalAlignment(JLabel.CENTER);
        stu_gender_ans.setBounds(290, 300, 200, 30);

        JLabel isActive = new JLabel("Is Active:");
        isActive.setFont(new Font("Montserrat", Font.BOLD, 14));
        isActive.setForeground(Color.darkGray);
        isActive.setBorder(null);
        isActive.setBackground(null);
        isActive.setVerticalAlignment(JLabel.CENTER);
        isActive.setBounds(40, 342, 150, 30);

        JLabel isActive_ans = new JLabel();
        isActive_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        isActive_ans.setForeground(Color.darkGray);
        isActive_ans.setBorder(null);
        isActive_ans.setBackground(null);
        isActive_ans.setVerticalAlignment(JLabel.CENTER);
        isActive_ans.setBounds(290, 342, 300, 30);

        JLabel Address = new JLabel("Address:");
        Address.setFont(new Font("Montserrat", Font.BOLD, 14));
        Address.setForeground(Color.darkGray);
        Address.setBorder(null);
        Address.setBackground(null);
        Address.setVerticalAlignment(JLabel.CENTER);
        Address.setBounds(40, 384, 150, 30);

        JLabel Address_ans = new JLabel();
        Address_ans.setFont(new Font("Montserrat", Font.PLAIN, 14));
        Address_ans.setForeground(Color.darkGray);
        Address_ans.setBorder(null);
        Address_ans.setBackground(null);
        Address_ans.setVerticalAlignment(JLabel.CENTER);
        Address_ans.setBounds(290, 384, 600, 30);


        JLabel viewStuDownLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        viewStuDownLine.setBounds(0, 429, 990, 20);
        viewStuDownLine.setFont(new Font("Arial", Font.BOLD, 14));
        viewStuDownLine.setForeground(new Color(240, 240, 240));


        AnimatedButton editStudentBtn = new AnimatedButton(("Edit Student"), 40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        editStudentBtn.setBounds(40, 461, 145, 40);
        editStudentBtn.setForeground(Color.WHITE);
        editStudentBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
        editStudentBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editStudentBtn.setBounds(40, 461, 145, 40);
                editStudentBtn.setBounds(40, 460, 145, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editStudentBtn.setBounds(40, 460, 145, 40);
                editStudentBtn.setBounds(40, 461, 145, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                viewStuPanel.setVisible(false);
                editStuPanel.setVisible(true);
            }
        });


        RoundedPanel viewStuDetailsPanel = new RoundedPanel(30);
        viewStuDetailsPanel.setBounds(20, 68, 990, 515);
        viewStuDetailsPanel.setBackground(Color.WHITE);
        viewStuDetailsPanel.setLayout(null);
        viewStuDetailsPanel.add(viewStuDetails);
        viewStuDetailsPanel.add(viewStuLine);
        viewStuDetailsPanel.add(id_stu);
        viewStuDetailsPanel.add(id_stu_ans);
        viewStuDetailsPanel.add(f_Name);
        viewStuDetailsPanel.add(f_Name_ans);
        viewStuDetailsPanel.add(e_address);
        viewStuDetailsPanel.add(e_address_ans);
        viewStuDetailsPanel.add(phone_number);
        viewStuDetailsPanel.add(phone_number_ans);
        viewStuDetailsPanel.add(stu_age);
        viewStuDetailsPanel.add(stu_age_ans);
        viewStuDetailsPanel.add(stu_gender);
        viewStuDetailsPanel.add(stu_gender_ans);
        viewStuDetailsPanel.add(isActive);
        viewStuDetailsPanel.add(isActive_ans);
        viewStuDetailsPanel.add(Address);
        viewStuDetailsPanel.add(Address_ans);
        viewStuDetailsPanel.add(viewStuDownLine);
        viewStuDetailsPanel.add(editStudentBtn);


        viewStuPanel.setBounds(250, 70, 1030, 650);
        viewStuPanel.setBackground(new Color(240, 239, 255));
        viewStuPanel.setLayout(null);
        viewStuPanel.add(viewStudentText);
        viewStuPanel.add(viewStuDetailsPanel);
        viewStuPanel.add(viewBackToList);
        viewStuPanel.setVisible(false);
        //=============== view Student Ends here =====================


        //=========== Dashboard Text ==========
        JLabel addNewStudent = new JLabel("Add New Student");
        addNewStudent.setFont(new Font("Century Gothic", Font.BOLD, 33));
        addNewStudent.setForeground(new Color(33, 37, 40));
        addNewStudent.setBounds(20, 20, 300, 40);
        addNewStudent.setVerticalAlignment(JLabel.BOTTOM);
        //=========== Dashboard Text ==========

        //============ Back to List ================
        AnimatedButton backToList = new AnimatedButton("  Back to List", 20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        backToList.setBounds(860, 18, 150, 40);
        backToList.setForeground(Color.WHITE);
        backToList.setFont(new Font("Montserrat", Font.PLAIN, 14));
        backToList.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        backToList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backToList.setBounds(860, 18, 150, 40);
                backToList.setBounds(860, 17, 150, 40);
                backToList.setBounds(860, 16, 150, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backToList.setBounds(860, 16, 150, 40);
                backToList.setBounds(860, 17, 150, 40);
                backToList.setBounds(860, 18, 150, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                addNewStuPanel.setVisible(false);
                studentPanel.setVisible(true);
            }
        });


        JLabel stuDetails = new JLabel("Student Details");
        stuDetails.setBounds(40, 17, 150, 30);
        stuDetails.setFont(new Font("Montserrat", Font.BOLD, 14));
        stuDetails.setForeground(Color.darkGray);

        JLabel addStuLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        addStuLine.setBounds(0, 43, 990, 20);
        addStuLine.setFont(new Font("Arial", Font.BOLD, 14));
        addStuLine.setForeground(new Color(240, 240, 240));


        JLabel firstName = new JLabel("First Name");
        firstName.setForeground(new Color(120, 120, 120));
        firstName.setFont(new Font("Montserrat", Font.BOLD, 14));
        firstName.setBounds(20, 72, 150, 30);

        RoundedTextField firstNameBox = new RoundedTextField("e.g. John", 10);
        firstNameBox.setBounds(20, 104, 465, 40);
        firstNameBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        firstNameBox.setForeground(new Color(105, 105, 105));


        JLabel lastName = new JLabel("Last Name");
        lastName.setForeground(new Color(120, 120, 120));
        lastName.setFont(new Font("Montserrat", Font.BOLD, 14));
        lastName.setBounds(505, 72, 150, 30);

        RoundedTextField lastNameBox = new RoundedTextField("e.g. Doe", 10);
        lastNameBox.setBounds(505, 104, 465, 40);
        lastNameBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        lastNameBox.setForeground(new Color(105, 105, 105));


        JLabel emailAddress = new JLabel("Email Address");
        emailAddress.setForeground(new Color(120, 120, 120));
        emailAddress.setFont(new Font("Montserrat", Font.BOLD, 14));
        emailAddress.setBounds(20, 152, 150, 30);

        RoundedTextField emailAddBox = new RoundedTextField("e.g. john123@gmail.com", 10);
        emailAddBox.setBounds(20, 184, 465, 40);
        emailAddBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        emailAddBox.setForeground(new Color(105, 105, 105));


        JLabel phone = new JLabel("Phone Number");
        phone.setForeground(new Color(120, 120, 120));
        phone.setFont(new Font("Montserrat", Font.BOLD, 14));
        phone.setBounds(505, 152, 150, 30);

        RoundedTextField phoneBox = new RoundedTextField("e.g. +92 3012345678", 10);
        phoneBox.setBounds(505, 184, 465, 40);
        phoneBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        phoneBox.setForeground(new Color(105, 105, 105));


        JLabel age = new JLabel("Age");
        age.setForeground(new Color(120, 120, 120));
        age.setFont(new Font("Montserrat", Font.BOLD, 14));
        age.setBounds(20, 232, 150, 30);

        RoundedTextField ageBox = new RoundedTextField("e.g. 19", 10);
        ageBox.setBounds(20, 264, 465, 40);
        ageBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        ageBox.setForeground(new Color(105, 105, 105));


        JLabel gender = new JLabel("Gender");
        gender.setForeground(new Color(120, 120, 120));
        gender.setFont(new Font("Montserrat", Font.BOLD, 14));
        gender.setBounds(505, 232, 150, 30);


        RoundedTextField genderBox = new RoundedTextField("e.g. Male", 10);
        genderBox.setBounds(505, 264, 465, 40);
        genderBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        genderBox.setForeground(new Color(105, 105, 105));


        JLabel address = new JLabel("Address");
        address.setForeground(new Color(120, 120, 120));
        address.setFont(new Font("Montserrat", Font.BOLD, 14));
        address.setBounds(20, 312, 150, 30);


        RoundedTextField addressBox = new RoundedTextField("Enter full address...", 10);
        addressBox.setBounds(20, 342, 950, 100);
        addressBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        addressBox.setForeground(new Color(105, 105, 105));


        AnimatedButton resetBtn = new AnimatedButton("Reset", 40,
                new Color(240, 239, 255),    // normal Color (light off-white)
                new Color(220, 218, 235),    // Hovered Color (slightly darker)
                new Color(200, 198, 215),    // Pressed Color (even darker)
                new Color(240, 239, 255));   // Border Color (same as normal)
        resetBtn.setBounds(710, 458, 120, 40);
        resetBtn.setForeground(Color.DARK_GRAY);
        resetBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
        resetBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetBtn.setBounds(710, 458, 120, 40);
                resetBtn.setBounds(710, 457, 120, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetBtn.setBounds(710, 457, 120, 40);
                resetBtn.setBounds(710, 458, 120, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                firstNameBox.setText("");
                lastNameBox.setText("");
                emailAddBox.setText("");
                phoneBox.setText("");
                ageBox.setText("");
                genderBox.setText("");
                addressBox.setText("");
            }
        });


        AnimatedButton addBtn = new AnimatedButton("Add", 40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));    // Border Color (same as normal)
        addBtn.setBounds(850, 458, 120, 40);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
        addBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addBtn.setBounds(850, 458, 120, 40);
                addBtn.setBounds(850, 457, 120, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addBtn.setBounds(850, 457, 120, 40);
                addBtn.setBounds(850, 458, 120, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String f_name = firstNameBox.getText();
                String l_name = lastNameBox.getText();
                String stu_email = emailAddBox.getText();
                String stu_phone = phoneBox.getText();
                String stu_age = ageBox.getText();
                String stu_gender = genderBox.getText();
                String stu_address = addressBox.getText();

                if (f_name.isEmpty() || l_name.isEmpty() || stu_email.isEmpty() || stu_phone.isEmpty()
                        || stu_age.isEmpty() || stu_gender.isEmpty() || stu_address.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                } else if (!stu_email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Invalid email format!\nExample: name@domain.com"
                            , "Validation Error", JOptionPane.WARNING_MESSAGE);
                    emailAddBox.requestFocus();
                } else if (!stu_phone.matches("[0-9+]{11,13}")) {
                    JOptionPane.showMessageDialog(null, "Invalid phone number!\ne.g. 03001234567 or +923001234567"
                            , "Validation Error", JOptionPane.WARNING_MESSAGE);
                    phoneBox.requestFocus();
                } else if (!stu_age.matches("[0-9]{1,2}")) {
                    JOptionPane.showMessageDialog(null, "Invalid age!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    ageBox.requestFocus();
                } else if (!stu_gender.matches("(?i)^(male|female|other)$")) {
                    JOptionPane.showMessageDialog(null, "Invalid gender!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    genderBox.requestFocus();
                } else {
                    try {
                        Connection connection = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = connection.createStatement();

                        String query = String.format("INSERT INTO NewStudent(firstname,lastname,email,phone,age,gender,address) VALUES ('%s','%s','%s','%s','%s','%s','%s')"
                                , f_name, l_name, stu_email, stu_phone, stu_age, stu_gender, stu_address);
                        st.executeUpdate(query);
                        connection.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    firstNameBox.setText("");
                    lastNameBox.setText("");
                    emailAddBox.setText("");
                    phoneBox.setText("");
                    ageBox.setText("");
                    genderBox.setText("");
                    addressBox.setText("");
                    try {
                        Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = con.createStatement(
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY
                        );
                        String query = "select stu_id,firstname,lastname,email,age,phone from NewStudent";
                        ResultSet resultset = st.executeQuery(query);
                        addNewStuPanel.setVisible(false);
                        studentPanel.setVisible(true);
                        if (resultset.last()) {
                            Object[] newData = {resultset.getString("stu_id")
                                    , resultset.getString("firstname") + " " + resultset.getString("lastname")
                                    , resultset.getString("email")
                                    , resultset.getString("age")
                                    , resultset.getString("phone")};
                            model.addRow(newData);
                        }
                        resultset.close();
                        st.close();
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        RoundedPanel addStuPanel = new RoundedPanel(30);
        addStuPanel.setBounds(20, 68, 990, 515);
        addStuPanel.setBackground(Color.WHITE);
        addStuPanel.setLayout(null);
        addStuPanel.add(stuDetails);
        addStuPanel.add(addStuLine);
        addStuPanel.add(firstName);
        addStuPanel.add(lastName);
        addStuPanel.add(firstNameBox);
        addStuPanel.add(lastNameBox);
        addStuPanel.add(emailAddress);
        addStuPanel.add(phone);
        addStuPanel.add(emailAddBox);
        addStuPanel.add(phoneBox);
        addStuPanel.add(age);
        addStuPanel.add(gender);
        addStuPanel.add(ageBox);
        addStuPanel.add(genderBox);
        addStuPanel.add(address);
        addStuPanel.add(addressBox);
        addStuPanel.add(resetBtn);
        addStuPanel.add(addBtn);


        addNewStuPanel.setBounds(250, 70, 1030, 650);
        addNewStuPanel.setBackground(new Color(240, 239, 255));
        addNewStuPanel.setLayout(null);
        addNewStuPanel.add(addNewStudent);
        addNewStuPanel.add(addStuPanel);
        addNewStuPanel.add(backToList);
        addNewStuPanel.setVisible(false);
        // ============= Add new Students Ends Here ============


        //==================== Student Panel =====================

        //=========== Student Text ==========
        JLabel Student = new JLabel("Students");
        Student.setFont(new Font("Century Gothic", Font.BOLD, 33));
        Student.setForeground(new Color(33, 37, 40));
        Student.setBounds(20, 20, 200, 40);
        //=========== Student Text ==========

        //============ ADD Student ================
        ImageIcon searchIconImg = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\search.png", 26, 26);
        JButton searchIcon = new JButton();
        searchIcon.setLayout(null);
        searchIcon.setBounds(560, 9, 26, 26);
        searchIcon.setContentAreaFilled(false);
        searchIcon.setFocusable(false);
        searchIcon.setBorder(null);
        searchIcon.setCursor(new Cursor(12));
        searchIcon.setIcon(searchIconImg);

        RoundedTextField searchBar = new RoundedTextField("Search", 44
                , new Color(240, 239, 255) //bg Color
                , new Color(134, 73, 255) // normal border Color
                , new Color(134, 73, 255)); // focused Border Color
        searchBar.setCaretColor(new Color(134, 73, 255));
        searchBar.setBounds(20, 13, 600, 44);
        searchBar.setFont(new Font("Montserrat", Font.PLAIN, 14));
        searchBar.setBackground(new Color(240, 239, 255));
        searchBar.add(searchIcon);


        searchIcon.addActionListener(e -> {
            String searchText = searchBar.getText();
            filterTable(searchText, table, model);
        });


        JLabel plusIcon = new JLabel("+");
        plusIcon.setBounds(0, 18, 30, 30);
        plusIcon.setForeground(Color.WHITE);
        plusIcon.setFont(new Font("Montserrat", Font.PLAIN, 25));

        AnimatedButton addStudent = new AnimatedButton("     Add New Student", 20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        addStudent.setBounds(830, 18, 180, 40);
        addStudent.setForeground(Color.WHITE);
        addStudent.setFont(new Font("Montserrat", Font.PLAIN, 14));
        addStudent.add(plusIcon);
        addStudent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addStudent.setBounds(830, 18, 180, 40);
                addStudent.setBounds(830, 17, 180, 40);
                addStudent.setBounds(830, 16, 180, 40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addStudent.setBounds(830, 16, 180, 40);
                addStudent.setBounds(830, 17, 180, 40);
                addStudent.setBounds(830, 18, 180, 40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                studentPanel.setVisible(false);
                addNewStuPanel.setVisible(true);
            }
        });
        //============ Add Students Ends Here ==============


        //================= Search Button ================


        AnimatedButton viewStu = new AnimatedButton("👁️", 44,
                new Color(148, 240, 151),   // Normal Color (light green)
                new Color(126, 220, 129),   // Hover Color
                new Color(104, 196, 107),   // Pressed Color
                new Color(84, 176, 87));  // Border Color
        viewStu.setBounds(813, 13, 44, 44);
        viewStu.setFont(new Font("", Font.PLAIN, 25));
        viewStu.setMargin(new Insets(0, 0, 0, 0));
        viewStu.setFocusable(false);
        viewStu.setForeground(new Color(84, 176, 87));
        viewStu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewStu.setBounds(813, 13, 44, 44);
                viewStu.setBounds(813, 12, 44, 44);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewStu.setBounds(813, 12, 44, 44);
                viewStu.setBounds(813, 13, 44, 44);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedRow() >= 0) {
                    String studentId = table.getValueAt(table.getSelectedRow(), 0).toString();
                    try {
                        Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = con.createStatement();
                        String query = String.format("select * from NewStudent where stu_id = '%s'", studentId);
                        ResultSet result = st.executeQuery(query);
                        result.next();
                        id_stu_ans.setText(result.getString("stu_id"));
                        f_Name_ans.setText(result.getString("firstname") + " " + result.getString("lastname"));
                        e_address_ans.setText(result.getString("email"));
                        isActive_ans.setText("Yes");
                        phone_number_ans.setText(result.getString("phone"));
                        stu_age_ans.setText(result.getString("age"));
                        stu_gender_ans.setText(result.getString("gender"));
                        Address_ans.setText(result.getString("address"));


                        String query1 = String.format("select * from NewStudent where stu_id = '%s'", studentId);
                        ResultSet result1 = st.executeQuery(query1);
                        result1.next();
                        firstNameEditBox.setText(result1.getString("firstname"));
                        lastNameEditBox.setText(result1.getString("lastname"));
                        emailAddEditBox.setText(result1.getString("email"));
                        phoneEditBox.setText(result1.getString("phone"));
                        ageEditBox.setText(result1.getString("age"));
                        genderEditBox.setText(result1.getString("gender"));
                        addressEditBox.setText(result1.getString("address"));
                        result.close();
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    studentPanel.setVisible(false);
                    viewStuPanel.setVisible(true);
                }
            }
        });


        ImageIcon editIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\edit.png", 20, 20);
        AnimatedButton editStu = new AnimatedButton(null, 44,
                new Color(251, 219, 179),   // Normal Color (light peach)
                new Color(235, 203, 163),   // Hover Color
                new Color(210, 178, 138),   // Pressed Color
                new Color(176, 144, 104));    // Border Color// Border Color (unchanged)
        editStu.setBounds(869, 13, 44, 44);
        editStu.setFont(new Font("", Font.BOLD, 19));
        editStu.setMargin(new Insets(0, 0, 0, 0));
        editStu.setFocusable(false);
        editStu.setLayout(new GridBagLayout());
        editStu.setIcon(editIcon);
        editStu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editStu.setBounds(869, 13, 44, 44);
                editStu.setBounds(869, 12, 44, 44);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editStu.setBounds(869, 12, 44, 44);
                editStu.setBounds(869, 13, 44, 44);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedRow() >= 0) {
                    String studentId = table.getValueAt(table.getSelectedRow(), 0).toString();
                    try {
                        Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = con.createStatement();
                        String query = String.format("select * from NewStudent where stu_id = '%s'", studentId);
                        ResultSet result = st.executeQuery(query);
                        result.next();
                        firstNameEditBox.setText(result.getString("firstname"));
                        lastNameEditBox.setText(result.getString("lastname"));
                        emailAddEditBox.setText(result.getString("email"));
                        phoneEditBox.setText(result.getString("phone"));
                        ageEditBox.setText(result.getString("age"));
                        genderEditBox.setText(result.getString("gender"));
                        addressEditBox.setText(result.getString("address"));
                        result.close();
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    studentPanel.setVisible(false);
                    editStuPanel.setVisible(true);
                }
            }
        });


        ImageIcon deleteIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\delete.png", 18, 18);
        AnimatedButton delStu = new AnimatedButton(null, 44,
                new Color(165, 176, 254),   // Normal Color (light periwinkle blue)
                new Color(145, 156, 234),   // Hover Color
                new Color(130, 141, 219),   // Pressed Color
                new Color(115, 126, 204));    // Border Color
        delStu.setBounds(924, 13, 44, 44);
        delStu.setFont(new Font("", Font.BOLD, 20));
        delStu.setMargin(new Insets(0, 0, 0, 0));
        delStu.setFocusable(false);
        delStu.setLayout(new GridBagLayout());
        delStu.setIcon(deleteIcon);
        delStu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                delStu.setBounds(924, 13, 44, 44);
                delStu.setBounds(924, 12, 44, 44);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                delStu.setBounds(924, 12, 44, 44);
                delStu.setBounds(924, 13, 44, 44);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    try {
                        Connection con = DriverManager.getConnection(url, "root", "Nisar123");
                        Statement st = con.createStatement();
                        String stu_id = model.getValueAt(selectedRow, 0).toString();
                        String query = String.format("Delete from NewStudent where stu_id = '%s'", stu_id);
                        st.executeUpdate(query);
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    model.removeRow(selectedRow);
                    int rowCount = table.getRowCount();
                    if (rowCount > 0) {
                        if (selectedRow >= rowCount) {
                            selectedRow = rowCount - 1;
                        }
                        table.setRowSelectionInterval(selectedRow, selectedRow);
                        table.scrollRectToVisible(table.getCellRect(selectedRow, 0, true));
                    }
                }
            }
        });


        RoundedPanel buttonBelowPanel = new RoundedPanel(20);
        buttonBelowPanel.setBackground(Color.WHITE);
        buttonBelowPanel.setBounds(20, 68, 990, 70);
        buttonBelowPanel.setLayout(null);
        buttonBelowPanel.add(viewStu);
        buttonBelowPanel.add(editStu);
        buttonBelowPanel.add(delStu);
        buttonBelowPanel.add(searchBar);


        //================= Search Button Ends Here ============


        //================== Student Data table ==================


        table.setForeground(Color.GRAY);
        table.setShowGrid(false);
        table.setDefaultEditor(Object.class, null);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(170);  // Name
        table.getColumnModel().getColumn(2).setPreferredWidth(260);  // Email
        table.getColumnModel().getColumn(3).setPreferredWidth(100);  // Age
        table.getColumnModel().getColumn(4).setPreferredWidth(140);  // Phone
        table.setRowHeight(55);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setBorder(null);
        table.setIntercellSpacing(new Dimension(0, 0));

        final int[] hoverRow2 = {-1};
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());

                if (hoverRow2[0] != row) {
                    hoverRow2[0] = row;
                    table.repaint();
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoverRow2[0] = -1;
                table.repaint();
            }
        });

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                // Use super to get the basic setup
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                setOpaque(true);
                setFont(new Font("Montserrat", Font.PLAIN, 13));
                setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                setHorizontalAlignment(JLabel.LEFT);
                setForeground(Color.DARK_GRAY);

                // HANDLE SELECTION STYLE
                if (isSelected) {
                    setBackground(new Color(230, 230, 255));
                    setFont(new Font("Montserrat", Font.BOLD, 13));
                } else if (row == hoverRow2[0]) {
                    setBackground(new Color(230, 230, 255, 100)); // hover color
                    setFont(new Font("Montserrat", Font.BOLD, 13));
                } else {

                    if (row % 2 == 0) {
                        setBackground(new Color(250, 250, 250));
                    } else {
                        setBackground(Color.WHITE);
                    }
                }
                return this;
            }
        });


        // ================= HEADER STYLE =================
        JTableHeader TableHeader = table.getTableHeader();
        TableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                JLabel label = new JLabel(value.toString());
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                label.setForeground(Color.GRAY);
                label.setFont(new Font("Montserrat", Font.BOLD, 12));
                label.setHorizontalAlignment(JLabel.LEFT);
                label.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

                return label;
            }
        });


        RoundScrollPane studentData = new RoundScrollPane(table, 30);
        studentData.setBackground(Color.WHITE);
        studentData.setBounds(0, 40, 990, 400);
        studentData.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        studentData.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        studentData.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        studentData.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        //================== Student Data table Ends Here==================

        ImageIcon stuListIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\list.png", 20, 20);
        JLabel studentList = new JLabel("Student List");
        studentList.setBounds(40, 17, 200, 30);
        studentList.setFont(new Font("Montserrat", Font.BOLD, 14));
        studentList.setForeground(Color.darkGray);
        studentList.setIcon(stuListIcon);

        JLabel line = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        line.setBounds(0, 43, 990, 20);
        line.setFont(new Font("Arial", Font.BOLD, 14));
        line.setForeground(new Color(240, 240, 240));
        line.setHorizontalAlignment(JLabel.CENTER);

        RoundedPanel tablePanel = new RoundedPanel(30);
        tablePanel.setBounds(20, 150, 990, 440);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setLayout(null);
        tablePanel.add(studentList);
        tablePanel.add(line);
        tablePanel.add(studentData);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tablePanel.setBounds(20, 150, 990, 440);
                tablePanel.setBounds(20, 149, 990, 440);
                tablePanel.setBounds(20, 148, 990, 440);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tablePanel.setBounds(20, 148, 990, 440);
                tablePanel.setBounds(20, 149, 990, 440);
                tablePanel.setBounds(20, 150, 990, 440);
            }
        });

        studentPanel.setBounds(250, 70, 1030, 650);
        studentPanel.setBackground(new Color(240, 239, 255));
        studentPanel.setLayout(null);
        studentPanel.add(Student);
        studentPanel.add(addStudent);
        studentPanel.add(buttonBelowPanel);
        studentPanel.add(tablePanel);
        studentPanel.setVisible(false);

        //=================== Student Panel Ends Here =====================


        // ============= dashboard main panel ============

        //=========== Dashboard Text ==========
        JLabel Dashboard = new JLabel("Dashboard");
        Dashboard.setFont(new Font("Century Gothic", Font.BOLD, 33));
        Dashboard.setForeground(new Color(33, 37, 40));
        Dashboard.setBounds(20, 0, 200, 40);
        //=========== Dashboard Text ==========


        //============ 4 Panels =============

        ImageIcon image = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\totalStudent.png", 30, 30);

        RoundedPanel insideP1 = new RoundedPanel(60);
        insideP1.setLayout(new GridBagLayout()); // centers the icon perfectly
        insideP1.setBounds(50, 20, 60, 60);
        insideP1.setBackground(new Color(134, 73, 255));

        JLabel iconLabel = new JLabel(image);
        iconLabel.setPreferredSize(new Dimension(30, 30)); // lock size
        iconLabel.setOpaque(false);
        insideP1.add(iconLabel);


        JLabel totalStudents = new JLabel("Total Students");
        totalStudents.setFont(new Font("Montserrat", Font.PLAIN, 13));
        totalStudents.setBounds(5, 90, 150, 30);
        totalStudents.setHorizontalAlignment(JLabel.CENTER);
        totalStudents.setForeground(Color.darkGray);

        stuCount.setForeground(Color.darkGray);
        stuCount.setFont(new Font("Montserrat", Font.PLAIN, 45));
        stuCount.setBorder(null);
        stuCount.setBackground(null);
        stuCount.setHorizontalAlignment(JLabel.CENTER);
        stuCount.setBounds(30, 105, 100, 80);

        RoundedPanel p1 = new RoundedPanel(30);
        p1.setBounds(20, 55, 160, 190);
        p1.setBackground(Color.WHITE);
        p1.setLayout(null);
        p1.add(totalStudents);
        p1.add(stuCount);
        p1.add(insideP1);
        p1.setBorder(new RoundedBorder(30, Color.lightGray));
        p1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p1.setBounds(20, 55, 160, 190);
                p1.setBorder(new RoundedBorder(30, new Color(134, 73, 255)));
                p1.setBounds(20, 54, 160, 190);
                p1.setBounds(20, 53, 160, 190);
                stuCount.setForeground(new Color(134, 73, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p1.setBounds(20, 53, 160, 190);
                p1.setBorder(new RoundedBorder(30, Color.lightGray));
                p1.setBounds(20, 54, 160, 190);
                p1.setBounds(20, 55, 160, 190);
                stuCount.setForeground(Color.darkGray);
            }
        });

        ImageIcon img2 = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\totalCourse.png", 35, 35);

        RoundedPanel insideP2 = new RoundedPanel(60);
        insideP2.setBounds(50, 20, 60, 60);
        insideP2.setBackground(new Color(251, 219, 179));
        insideP2.setLayout(new GridBagLayout());

        JLabel img2Label = new JLabel(img2);
        img2Label.setPreferredSize(new Dimension(35, 35)); // lock size
        img2Label.setOpaque(false);
        insideP2.add(img2Label);


        JLabel totalCourses = new JLabel("Total Courses");
        totalCourses.setFont(new Font("Montserrat", Font.PLAIN, 13));
        totalCourses.setBounds(5, 90, 150, 30);
        totalCourses.setForeground(Color.darkGray);
        totalCourses.setHorizontalAlignment(JLabel.CENTER);

        courseCount.setForeground(Color.darkGray);
        courseCount.setFont(new Font("Montserrat", Font.PLAIN, 45));
        courseCount.setBorder(null);
        courseCount.setBackground(null);
        courseCount.setBounds(30, 105, 100, 80);
        courseCount.setHorizontalAlignment(JLabel.CENTER);


        RoundedPanel p2 = new RoundedPanel(30);
        p2.setBounds(200, 55, 160, 190);
        p2.setBackground(Color.WHITE);
        p2.setLayout(null);
        p2.setBorder(new RoundedBorder(30, Color.lightGray));
        p2.add(totalCourses);
        p2.add(courseCount);
        p2.add(insideP2);
        p2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p2.setBounds(200, 55, 160, 190);
                p2.setBorder(new RoundedBorder(30, new Color(176, 144, 104)));
                p2.setBounds(200, 54, 160, 190);
                p2.setBounds(200, 53, 160, 190);
                courseCount.setForeground(new Color(176, 144, 104));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p2.setBounds(200, 53, 160, 190);
                p2.setBorder(new RoundedBorder(30, Color.lightGray));
                p2.setBounds(200, 54, 160, 190);
                p2.setBounds(200, 55, 160, 190);
                courseCount.setForeground(Color.darkGray);
            }
        });


        ImageIcon img3 = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\attendanceRate.png", 30, 30);


        RoundedPanel insideP3 = new RoundedPanel(60);
        insideP3.setBounds(50, 20, 60, 60);
        insideP3.setBackground(new Color(148, 240, 151));//104, 196, 107
        insideP3.setLayout(new GridBagLayout());

        JLabel img3Label = new JLabel(img3);
        img3Label.setPreferredSize(new Dimension(30, 30));
        img3Label.setOpaque(false);
        insideP3.add(img3Label);

        JLabel attendanceRate = new JLabel("Attendance Rate");
        attendanceRate.setFont(new Font("Montserrat", Font.PLAIN, 13));
        attendanceRate.setBounds(5, 90, 150, 30);
        attendanceRate.setForeground(Color.darkGray);
        attendanceRate.setHorizontalAlignment(JLabel.CENTER);

        JLabel attendanceCount = new JLabel("03");
        attendanceCount.setForeground(Color.darkGray);
        attendanceCount.setFont(new Font("Montserrat", Font.PLAIN, 45));
        attendanceCount.setBorder(null);
        attendanceCount.setBackground(null);
        attendanceCount.setHorizontalAlignment(JLabel.CENTER);
        attendanceCount.setBounds(30, 105, 100, 80);


        RoundedPanel p3 = new RoundedPanel(30);
        p3.setBounds(380, 55, 160, 190);
        p3.setBackground(Color.WHITE);
        p3.setLayout(null);
        p3.setBorder(new RoundedBorder(30, Color.lightGray));
        p3.add(attendanceRate);
        p3.add(insideP3);
        p3.add(attendanceCount);
        p3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p3.setBounds(380, 55, 160, 190);
                p3.setBorder(new RoundedBorder(30, new Color(104, 196, 107)));
                p3.setBounds(380, 54, 160, 190);
                p3.setBounds(380, 53, 160, 190);
                attendanceCount.setForeground(new Color(104, 196, 107));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p3.setBounds(380, 53, 160, 190);
                p3.setBorder(new RoundedBorder(30, Color.lightGray));
                p3.setBounds(380, 54, 160, 190);
                p3.setBounds(380, 55, 160, 190);
                attendanceCount.setForeground(Color.darkGray);
            }
        });

        ImageIcon img4 = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\totalEnroll.png", 30, 30);


        RoundedPanel insideP4 = new RoundedPanel(60);
        insideP4.setBounds(50, 20, 60, 60);
        insideP4.setBackground(new Color(165, 176, 254));
        insideP4.setLayout(new GridBagLayout());

        JLabel img4Label = new JLabel(img4);
        img4Label.setPreferredSize(new Dimension(30, 30)); // lock size
        img4Label.setOpaque(false);
        insideP4.add(img4Label);

        JLabel SETM = new JLabel("Student Enrolled");
        SETM.setFont(new Font("Montserrat", Font.PLAIN, 13));
        SETM.setBounds(5, 90, 150, 30);
        SETM.setForeground(Color.darkGray);
        SETM.setHorizontalAlignment(JLabel.CENTER);

        enrollCount.setForeground(Color.darkGray);
        enrollCount.setFont(new Font("Montserrat", Font.PLAIN, 45));
        enrollCount.setBorder(null);
        enrollCount.setBackground(null);
        enrollCount.setHorizontalAlignment(JLabel.CENTER);
        enrollCount.setBounds(30, 105, 100, 80);


        RoundedPanel p4 = new RoundedPanel(30);
        p4.setBounds(560, 55, 160, 190);
        p4.setBackground(Color.WHITE);
        p4.setLayout(null);
        p4.setBorder(new RoundedBorder(30, Color.lightGray));
        p4.add(SETM);
        p4.add(insideP4);
        p4.add(enrollCount);
        p4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p4.setBounds(560, 55, 160, 190);
                p4.setBorder(new RoundedBorder(30, new Color(115, 126, 204)));
                p4.setBounds(560, 54, 160, 190);
                p4.setBounds(560, 53, 160, 190);
                enrollCount.setForeground(new Color(115, 126, 204));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p4.setBounds(560, 53, 160, 190);
                p4.setBorder(new RoundedBorder(30, Color.lightGray));
                p4.setBounds(560, 54, 160, 190);
                p4.setBounds(560, 55, 160, 190);
                enrollCount.setForeground(Color.darkGray);
            }
        });


        RoundedPanel b1 = new RoundedPanel(10);
        b1.setBounds(30, 90, 8, 60);
        b1.setBackground(new Color(255, 161, 49));


        RoundedPanel b2 = new RoundedPanel(10);
        b2.setBounds(44, 40, 8, 110);
        b2.setBackground(new Color(107, 184, 255));

        JLabel b1Text = new JLabel("Mon");
        b1Text.setFont(new Font("Montserrat", Font.PLAIN, 12));
        b1Text.setBounds(15, 155, 50, 20);
        b1Text.setForeground(Color.WHITE);
        b1Text.setHorizontalAlignment(JLabel.CENTER);

        RoundedPanel b3 = new RoundedPanel(10);
        b3.setBounds(77, 80, 8, 70);
        b3.setBackground(new Color(255, 161, 49));

        RoundedPanel b4 = new RoundedPanel(10);
        b4.setBounds(91, 50, 8, 100);
        b4.setBackground(new Color(107, 184, 255));

        JLabel b2Text = new JLabel("Tue");
        b2Text.setFont(new Font("Montserrat", Font.PLAIN, 12));
        b2Text.setBounds(63, 155, 50, 20);
        b2Text.setForeground(Color.WHITE);
        b2Text.setHorizontalAlignment(JLabel.CENTER);

        RoundedPanel b5 = new RoundedPanel(10);
        b5.setBounds(124, 60, 8, 90);
        b5.setBackground(new Color(255, 161, 49));

        RoundedPanel b6 = new RoundedPanel(10);
        b6.setBounds(138, 20, 8, 130);
        b6.setBackground(new Color(107, 184, 255));

        JLabel b3Text = new JLabel("Wed");
        b3Text.setFont(new Font("Montserrat", Font.PLAIN, 12));
        b3Text.setBounds(110, 155, 50, 20);
        b3Text.setForeground(Color.WHITE);
        b3Text.setHorizontalAlignment(JLabel.CENTER);

        RoundedPanel b7 = new RoundedPanel(10);
        b7.setBounds(171, 100, 8, 50);
        b7.setBackground(new Color(255, 161, 49));

        RoundedPanel b8 = new RoundedPanel(10);
        b8.setBounds(185, 40, 8, 110);
        b8.setBackground(new Color(107, 184, 255));

        JLabel b4Text = new JLabel("Thu");
        b4Text.setFont(new Font("Montserrat", Font.PLAIN, 12));
        b4Text.setBounds(158, 155, 50, 20);
        b4Text.setForeground(Color.WHITE);
        b4Text.setHorizontalAlignment(JLabel.CENTER);

        RoundedPanel b9 = new RoundedPanel(10);
        b9.setBounds(218, 85, 8, 65);
        b9.setBackground(new Color(255, 161, 49));

        RoundedPanel b10 = new RoundedPanel(10);
        b10.setBounds(232, 50, 8, 100);
        b10.setBackground(new Color(107, 184, 255));

        JLabel b5Text = new JLabel("Fri");
        b5Text.setFont(new Font("Montserrat", Font.PLAIN, 12));
        b5Text.setBounds(203, 155, 50, 20);
        b5Text.setForeground(Color.WHITE);
        b5Text.setHorizontalAlignment(JLabel.CENTER);


        RoundedPanel p5 = new RoundedPanel(40);
        p5.setBounds(740, 55, 270, 190);
        p5.setBackground(new Color(134, 73, 255));
        p5.setLayout(null);
        p5.setBorder(new RoundedBorder(40, new Color(255, 161, 49)));
        p5.add(b1);
        p5.add(b2);
        p5.add(b1Text);
        p5.add(b3);
        p5.add(b4);
        p5.add(b2Text);
        p5.add(b5);
        p5.add(b6);
        p5.add(b3Text);
        p5.add(b7);
        p5.add(b8);
        p5.add(b4Text);
        p5.add(b9);
        p5.add(b10);
        p5.add(b5Text);
        p5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p5.setBounds(740, 55, 270, 190);
                p5.setBounds(740, 54, 270, 190);
                p5.setBounds(740, 53, 270, 190);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p5.setBounds(740, 53, 270, 190);
                p5.setBounds(740, 54, 270, 190);
                p5.setBounds(740, 55, 270, 190);
            }
        });
        //============ 4 Panels Ends Here =============


        //================= Recent Student Registrations ====================
        JLabel recentRegText = new JLabel("Recent Student Registration");
        recentRegText.setFont(new Font("Montserrat", Font.BOLD, 13));
        recentRegText.setBounds(30, 15, 200, 30);
        recentRegText.setForeground(Color.DARK_GRAY);

        JLabel recentEnrollLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        recentEnrollLine.setBounds(0, 40, 990, 20);
        recentEnrollLine.setFont(new Font("Arial", Font.BOLD, 14));
        recentEnrollLine.setForeground(new Color(240, 240, 240));

        recentEnrolledTable.setForeground(Color.WHITE);
        recentEnrolledTable.setShowGrid(false);
        recentEnrolledTable.setDefaultEditor(Object.class, null);
        recentEnrolledTable.getColumnModel().getColumn(0).setPreferredWidth(110);   // ID
        recentEnrolledTable.getColumnModel().getColumn(1).setPreferredWidth(200);  // Name
        recentEnrolledTable.getColumnModel().getColumn(2).setPreferredWidth(310);  // Course
        recentEnrolledTable.getColumnModel().getColumn(3).setPreferredWidth(180);  // Enroll date
        recentEnrolledTable.getColumnModel().getColumn(4).setPreferredWidth(150);  // Duration
        recentEnrolledTable.setRowHeight(52);
        recentEnrolledTable.getTableHeader().setResizingAllowed(false);
        recentEnrolledTable.getTableHeader().setReorderingAllowed(false);
        recentEnrolledTable.setBorder(null);
        recentEnrolledTable.setIntercellSpacing(new Dimension(0, 0));

        final int[] hoverRow3 = {-1};
        recentEnrolledTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = recentEnrolledTable.rowAtPoint(e.getPoint());

                if (hoverRow3[0] != row) {
                    hoverRow3[0] = row;
                    recentEnrolledTable.repaint();
                }
            }
        });

        recentEnrolledTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoverRow3[0] = -1;
                recentEnrolledTable.repaint();
            }
        });


        recentEnrolledTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable recentEnrolledTable, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                super.getTableCellRendererComponent(
                        recentEnrolledTable, value, isSelected,
                        hasFocus, row, column);

                setOpaque(true);
                setFont(new Font("Montserrat", Font.PLAIN, 12));

                // Added bottom border
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0, 0, 1, 0,
                                new Color(240, 240, 240)),
                        BorderFactory.createEmptyBorder(
                                5, 15, 5, 15)
                ));

                setHorizontalAlignment(JLabel.LEFT);
                setForeground(Color.DARK_GRAY);

                if (isSelected) {
                    setBackground(new Color(230, 230, 255));
                    setFont(new Font("Montserrat", Font.BOLD, 12));
                } else if (row == hoverRow3[0]) {
                    setBackground(new Color(230, 230, 255, 100)); // hover color
                    setFont(new Font("Montserrat", Font.BOLD, 13));
                } else {
                    setBackground(Color.WHITE);
                }

                return this;
            }
        });


// ================= HEADER STYLE =================
        JTableHeader recentEnrolledTableHeader = recentEnrolledTable.getTableHeader();

        recentEnrolledTableHeader.setDefaultRenderer(
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable recentEnrolledTable, Object value,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {

                        JLabel label = new JLabel(value.toString());

                        label.setOpaque(true);
                        label.setBackground(Color.WHITE);
                        label.setForeground(Color.GRAY);
                        label.setFont(new Font("Montserrat",
                                Font.BOLD, 12));
                        label.setHorizontalAlignment(JLabel.LEFT);

                        // Added bottom border
                        label.setBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createMatteBorder(
                                                0, 0, 1, 0,
                                                new Color(235, 235, 235)),
                                        BorderFactory.createEmptyBorder(
                                                15, 15, 15, 15)
                                )
                        );

                        return label;
                    }
                });


        RoundScrollPane stuEnrollTableBg = new RoundScrollPane(recentEnrolledTable, 30);
        stuEnrollTableBg.setBackground(Color.WHITE);
        stuEnrollTableBg.setBounds(20, 65, 950, 253);
        stuEnrollTableBg.setBorder(BorderFactory.createEmptyBorder());
        stuEnrollTableBg.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        stuEnrollTableBg.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        stuEnrollTableBg.getVerticalScrollBar().setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        stuEnrollTableBg.getVerticalScrollBar().setUI(new ModernScrollBarUI(new Color(134, 73, 255, 150), new Color(240, 240, 240)));


        RoundedPanel recentStuBgPanel = new RoundedPanel(30);
        recentStuBgPanel.setBackground(Color.white);
        recentStuBgPanel.setBounds(20, 265, 990, 350);
        recentStuBgPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        recentStuBgPanel.setLayout(null);
        recentStuBgPanel.add(recentRegText);
        recentStuBgPanel.add(recentEnrollLine);
        recentStuBgPanel.add(stuEnrollTableBg);
        //================= Recent Student Registrations Ends Here ====================


        dashPanel.setBounds(250, 70, 1030, 650);
        dashPanel.setBackground(new Color(240, 239, 255));
        dashPanel.setLayout(null);
        dashPanel.add(Dashboard);
        dashPanel.add(p1);
        dashPanel.add(p2);
        dashPanel.add(p3);
        dashPanel.add(p4);
        dashPanel.add(p5);
        dashPanel.add(recentStuBgPanel);
        // ============= dashboard main panel Ends Here ============


        JButton settingBtn = new JButton("≡");
        settingBtn.setLayout(null);
        settingBtn.setFont(new Font(null, Font.BOLD, 32));
        settingBtn.setForeground(new Color(134, 73, 255));
        settingBtn.setBounds(270, 10, 50, 50);
        settingBtn.setBorder(null);
        settingBtn.setFocusable(false);
        settingBtn.setCursor(new Cursor(12));
        settingBtn.setContentAreaFilled(false);


        JLabel one = new JLabel("1");
        one.setFont(new Font("Montserrat", Font.BOLD, 7));
        one.setForeground(Color.WHITE);
        one.setBounds(4, 1, 7, 7);

        RoundedPanel notificationPanel = new RoundedPanel(10);
        notificationPanel.setBounds(347, 23, 10, 10);
        notificationPanel.setBackground(new Color(246, 38, 129, 170));
        notificationPanel.setLayout(null);
        notificationPanel.add(one);


        JButton bellBtn = new JButton("\uD83D\uDD14");
        bellBtn.setLayout(null);
        bellBtn.setFont(new Font(null, Font.PLAIN, 18));
        bellBtn.setForeground(new Color(134, 73, 255));
        bellBtn.setBounds(320, 12, 50, 50);
        bellBtn.setBorder(null);
        bellBtn.setFocusable(false);
        bellBtn.setCursor(new Cursor(12));
        bellBtn.setContentAreaFilled(false);


        ImageIcon profileImg = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\profile.png", 25, 25);


        JLabel profileName = new JLabel("Nisar Ahmed");
        profileName.setBounds(1110, 6, 150, 50);
        profileName.setFont(new Font("Montserrat", Font.PLAIN, 14));

        JLabel viewProfile = new JLabel("View Profile");
        viewProfile.setBounds(1135, 23, 150, 50);
        viewProfile.setFont(new Font("Montserrat", Font.PLAIN, 11));


        RoundedPanel profilePanel = new RoundedPanel(45);
        profilePanel.setBackground(null);
        profilePanel.setBorder(new RoundedBorder(50, new Color(134, 73, 255)));
        profilePanel.setBounds(1215, 15, 45, 45);
        profilePanel.setLayout(new GridBagLayout());


        JLabel profileImgLabel = new JLabel(profileImg);
        profileImgLabel.setPreferredSize(new Dimension(25, 25)); // lock size
        profileImgLabel.setOpaque(false);
        profilePanel.add(profileImgLabel);

        dashboard.setExtendedState(JFrame.MAXIMIZED_BOTH);
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboard.getContentPane().setBackground(new Color(240, 239, 255));
        dashboard.setLayout(null);
        dashboard.setResizable(false);
        dashboard.add(settingBtn);
        dashboard.add(bellBtn);
        dashboard.add(notificationPanel);
        dashboard.add(profileName);
        dashboard.add(viewProfile);
        dashboard.add(profilePanel);
        dashboard.add(sidePanel);
        dashboard.add(dashPanel);
        dashboard.add(studentPanel);
        dashboard.add(coursePanel);
        dashboard.add(addNewStuPanel);
        dashboard.add(addNewCoursePanel);
        dashboard.add(viewStuPanel);
        dashboard.add(editStuPanel);
        dashboard.add(viewCoursePanel);
        dashboard.add(editCoursePanel);
        dashboard.add(enrollToCourse);
        dashboard.add(attendancePanel);
        //=================== DASHBOARD ENDS HERE =================


        dashb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getRowCount() < 10)
                    stuCount.setText("0" + table.getRowCount());
                else
                    stuCount.setText(Integer.toString(table.getRowCount()));


                if (courseTable.getRowCount() < 10)
                    courseCount.setText("0" + courseTable.getRowCount());
                else
                    courseCount.setText(Integer.toString(courseTable.getRowCount()));

                if (recentEnrolledTable.getRowCount() < 10)
                    enrollCount.setText("0" + recentEnrolledTable.getRowCount());
                else
                    enrollCount.setText(Integer.toString(recentEnrolledTable.getRowCount()));

                refreshRecentEnrollTable(recentEnrollTableModel, url);

                backPanel.setBounds(15, 143, 210, 45);
                dashb.setForeground(Color.WHITE);
                course.setForeground(new Color(240, 239, 255));
                student.setForeground(new Color(240, 239, 255));
                enroll.setForeground(new Color(240, 239, 255));
                attendance.setForeground(new Color(240, 239, 255));
                report.setForeground(new Color(240, 239, 255));
                logout.setForeground(new Color(240, 239, 255));
                addNewStuPanel.setVisible(false);
                addNewCoursePanel.setVisible(false);
                viewStuPanel.setVisible(false);
                editStuPanel.setVisible(false);
                viewCoursePanel.setVisible(false);
                editCoursePanel.setVisible(false);
                studentPanel.setVisible(false);
                coursePanel.setVisible(false);
                enrollToCourse.setVisible(false);
                attendancePanel.setVisible(false);
                dashPanel.setVisible(true);
            }
        });

        student.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (recentEnrolledTable.getRowCount() < 10)
                    enrollCount.setText("0" + recentEnrolledTable.getRowCount());
                else
                    enrollCount.setText(Integer.toString(recentEnrolledTable.getRowCount()));
                backPanel.setBounds(15, 193, 210, 45);
                student.setForeground(Color.WHITE);
                course.setForeground(new Color(240, 239, 255));
                dashb.setForeground(new Color(240, 239, 255));
                enroll.setForeground(new Color(240, 239, 255));
                attendance.setForeground(new Color(240, 239, 255));
                report.setForeground(new Color(240, 239, 255));
                logout.setForeground(new Color(240, 239, 255));
                addNewStuPanel.setVisible(false);
                addNewCoursePanel.setVisible(false);
                viewStuPanel.setVisible(false);
                editStuPanel.setVisible(false);
                viewCoursePanel.setVisible(false);
                editCoursePanel.setVisible(false);
                coursePanel.setVisible(false);
                dashPanel.setVisible(false);
                enrollToCourse.setVisible(false);
                attendancePanel.setVisible(false);
                studentPanel.setVisible(true);
            }
        });

        course.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (recentEnrolledTable.getRowCount() < 10)
                    enrollCount.setText("0" + recentEnrolledTable.getRowCount());
                else
                    enrollCount.setText(Integer.toString(recentEnrolledTable.getRowCount()));
                backPanel.setBounds(15, 243, 210, 45);
                course.setForeground(Color.WHITE);
                dashb.setForeground(new Color(240, 239, 255));
                student.setForeground(new Color(240, 239, 255));
                enroll.setForeground(new Color(240, 239, 255));
                attendance.setForeground(new Color(240, 239, 255));
                report.setForeground(new Color(240, 239, 255));
                logout.setForeground(new Color(240, 239, 255));
                addNewStuPanel.setVisible(false);
                addNewCoursePanel.setVisible(false);
                viewStuPanel.setVisible(false);
                editStuPanel.setVisible(false);
                viewCoursePanel.setVisible(false);
                editCoursePanel.setVisible(false);
                studentPanel.setVisible(false);
                dashPanel.setVisible(false);
                enrollToCourse.setVisible(false);
                attendancePanel.setVisible(false);
                coursePanel.setVisible(true);
            }
        });


        enroll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (recentEnrolledTable.getRowCount() < 10)
                    enrollCount.setText("0" + recentEnrolledTable.getRowCount());
                else
                    enrollCount.setText(Integer.toString(recentEnrolledTable.getRowCount()));
                backPanel.setBounds(15, 293, 210, 45);
                enroll.setForeground(Color.WHITE);
                course.setForeground(new Color(240, 239, 255));
                student.setForeground(new Color(240, 239, 255));
                dashb.setForeground(new Color(240, 239, 255));
                attendance.setForeground(new Color(240, 239, 255));
                report.setForeground(new Color(240, 239, 255));
                logout.setForeground(new Color(240, 239, 255));
                addNewStuPanel.setVisible(false);
                addNewCoursePanel.setVisible(false);
                viewStuPanel.setVisible(false);
                editStuPanel.setVisible(false);
                viewCoursePanel.setVisible(false);
                editCoursePanel.setVisible(false);
                studentPanel.setVisible(false);
                dashPanel.setVisible(false);
                attendancePanel.setVisible(false);

                courseSelectedAns.setText("00");
                totalFeeAns.setText("0.00 Rs");
                Course_Name.setText("• None Selected");

                refreshEnrollTable(enrollTableModel, url);

                selectStudentField.removeAllItems();
                selectStudentField.addItem("Select a Student");
                selectStudentField.setSelectedItem("Select a Student");
                coursePanel.setVisible(false);
                for (int i = 0; i < table.getRowCount(); i++)
                    selectStudentField.addItem(model.getValueAt(i, 1) + " (" + model.getValueAt(i, 0) + ")");

                enrollToCourse.setVisible(true);
            }
        });

        attendance.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backPanel.setBounds(15, 343, 210, 45);
                attendance.setForeground(Color.WHITE);
                course.setForeground(new Color(240, 239, 255));
                student.setForeground(new Color(240, 239, 255));
                enroll.setForeground(new Color(240, 239, 255));
                dashb.setForeground(new Color(240, 239, 255));
                report.setForeground(new Color(240, 239, 255));
                logout.setForeground(new Color(240, 239, 255));
                addNewStuPanel.setVisible(false);
                addNewCoursePanel.setVisible(false);
                viewStuPanel.setVisible(false);
                editStuPanel.setVisible(false);
                viewCoursePanel.setVisible(false);
                editCoursePanel.setVisible(false);
                studentPanel.setVisible(false);
                dashPanel.setVisible(false);
                coursePanel.setVisible(false);
                enrollToCourse.setVisible(false);

                if (courseSelector.getSelectedItem().equals("Select a Course")) {
                    attendanceRecord.setVisible(false);
                    attendanceLine.setVisible(false);
                    total.setVisible(false);
                    presentStudents.setVisible(false);
                    attendanceBgScrollBar.setVisible(false);
                    absentStudents.setVisible(false);
                    Roundedbg.setVisible(false);
                    viewOrMark.setVisible(true);
                }

                courseSelector.removeAllItems();
                courseSelector.addItem("Select a Course");
                courseSelector.setSelectedItem("Select a Course");
                for (int i = 0; i < courseTable.getRowCount(); i++)
                    courseSelector.addItem(courseTableModel.getValueAt(i, 1));


                attendancePanel.setVisible(true);
            }
        });

        report.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backPanel.setBounds(15, 393, 210, 45);
                report.setForeground(Color.WHITE);
                course.setForeground(new Color(240, 239, 255));
                student.setForeground(new Color(240, 239, 255));
                enroll.setForeground(new Color(240, 239, 255));
                attendance.setForeground(new Color(240, 239, 255));
                dashb.setForeground(new Color(240, 239, 255));
                logout.setForeground(new Color(240, 239, 255));
                addNewStuPanel.setVisible(false);
                addNewCoursePanel.setVisible(false);
                viewStuPanel.setVisible(false);
                editStuPanel.setVisible(false);
                viewCoursePanel.setVisible(false);
                editCoursePanel.setVisible(false);
                studentPanel.setVisible(false);
                dashPanel.setVisible(false);
                coursePanel.setVisible(false);
                enrollToCourse.setVisible(false);
                attendancePanel.setVisible(false);
            }
        });

        logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                course.setForeground(new Color(240, 239, 255));
                student.setForeground(new Color(240, 239, 255));
                enroll.setForeground(new Color(240, 239, 255));
                attendance.setForeground(new Color(240, 239, 255));
                report.setForeground(new Color(240, 239, 255));
                dashb.setForeground(new Color(240, 239, 255));
                int result = JOptionPane.showConfirmDialog(null, "Do you want to Logout?"
                        , "Logout", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    dashboard.dispose();
                    frame.setVisible(true);
                }
            }
        });
    }
}
