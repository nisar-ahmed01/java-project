package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ModernDateChooser extends JPanel {
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
        dateField = new RoundedTextField("dd/MM/yyyy", 10);
        dateField.setBounds(0, 0, 160, 40);
        dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        dateField.setForeground(Color.DARK_GRAY);
        dateField.setBackground(Color.WHITE);
        dateField.setBorder(new RoundedBorder(10, lightPurple));
        dateField.setHorizontalAlignment(JTextField.LEFT);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateField.setText(sdf.format(new Date()));
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
        calendarButton.addActionListener(e -> showCalendarDialog());
        dateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                dateField.setBorder(new RoundedBorder(10, purpleColor));
            }
            @Override
            public void focusLost(FocusEvent e) {
                dateField.setBorder(new RoundedBorder(10, lightPurple));
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
        calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBackground(Color.WHITE);
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
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
        daysPanel = new JPanel(new GridLayout(0, 7, 5, 5));
        daysPanel.setBackground(Color.WHITE);
        String[] dayNames = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        for (String day : dayNames) {
            JLabel dayLabel = new JLabel(day, JLabel.CENTER);
            dayLabel.setFont(new Font("Montserrat", Font.BOLD, 11));
            dayLabel.setForeground(purpleColor);
            daysPanel.add(dayLabel);
        }
        calendarPanel.add(headerPanel, BorderLayout.NORTH);
        calendarPanel.add(daysPanel, BorderLayout.CENTER);
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
        String[] dayNames = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        for (String day : dayNames) {
            JLabel dayLabel = new JLabel(day, JLabel.CENTER);
            dayLabel.setFont(new Font("Montserrat", Font.BOLD, 11));
            dayLabel.setForeground(purpleColor);
            daysPanel.add(dayLabel);
        }
        Calendar tempCal = (Calendar) calendar.clone();
        tempCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < firstDayOfWeek; i++) {
            daysPanel.add(new JLabel(""));
        }
        int daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int day = 1; day <= daysInMonth; day++) {
            JLabel dayLabel = new JLabel(String.valueOf(day), JLabel.CENTER);
            dayLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
            dayLabel.setForeground(Color.DARK_GRAY);
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