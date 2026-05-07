package attendance;

import components.*;
import dashboard.MngMainFrame;
import database.DatabaseConfig;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.Date;

public class AttendancePanel extends JPanel {
    MngMainFrame frame;

    private JTable attendanceTable;
    private DefaultTableModel attendanceTableModel;
    private JLabel total, presentStudents, absentStudents;
    private JLabel attendanceRecord, attendanceLine;
    private JScrollPane attendanceBgScrollBar;
    private JLabel noRecordFound, viewOrMark;
    private RoundedComboBox courseSelector;
    private ModernDateChooser dateChooser;

    public AttendancePanel(MngMainFrame frame) {
        this.frame = frame;
    }

    public void createIt() {
        setBounds(250, 70, 1030, 650);
        setBackground(new Color(240, 239, 255));
        setLayout(null);
        frame.addToMainFrame(this);
        initialize();
        setVisible(false);
    }

    public void visibility(boolean visible) {
        this.setVisible(visible);
    }

    public void refreshAttendanceTable(String course, LocalDate date) {
        try {
            DatabaseConfig.setConnection();
            Statement st = DatabaseConfig.getSt();
            String query = "SELECT studentName, courseName, attendance FROM Attendance WHERE courseName = '" + course + "' AND attendanceDate = '" + date + "'";
            ResultSet result = st.executeQuery(query);

            if (!result.isBeforeFirst()) {
                attendanceTableModel.setRowCount(0);
                showNoRecordMessage(true);
                DatabaseConfig.closeConnection();
                return;
            }

            showNoRecordMessage(false);
            attendanceTableModel.setRowCount(0);

            int presentCount = 0;
            while (result.next()) {
                String attendance = result.getString("attendance");
                if ("Present".equals(attendance)) presentCount++;

                Object[] newData = {
                        result.getString("studentName"),
                        result.getString("courseName"),
                        attendance
                };
                attendanceTableModel.addRow(newData);
            }

            total.setText("Total : " + attendanceTableModel.getRowCount());
            presentStudents.setText("Present : " + presentCount);
            absentStudents.setText("Absent : " + (attendanceTableModel.getRowCount() - presentCount));

            DatabaseConfig.closeConnection();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void showNoRecordMessage(boolean show) {
        attendanceRecord.setVisible(!show);
        attendanceLine.setVisible(!show);
        total.setVisible(!show);
        presentStudents.setVisible(!show);
        absentStudents.setVisible(!show);
        attendanceBgScrollBar.setVisible(!show);
        noRecordFound.setVisible(show);
        viewOrMark.setVisible(false);
    }

    public void initialize() {
        // Load courses from database
        String[] courses = {"Select a Course"};
        try {
            DatabaseConfig.setConnection();
            Statement st = DatabaseConfig.getSt();
            String query = "SELECT course_name FROM NewCourse";
            ResultSet rs = st.executeQuery(query);

            ArrayList<String> courseList = new ArrayList<>();
            courseList.add("Select a Course");

            while (rs.next()) {
                courseList.add(rs.getString("course_name"));
            }

            courses = courseList.toArray(new String[0]);
            DatabaseConfig.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JLabel AttendanceMng = new JLabel("Attendance");
        AttendanceMng.setFont(new Font("Century Gothic", Font.BOLD, 33));
        AttendanceMng.setForeground(new Color(33, 37, 40));
        AttendanceMng.setBounds(20, 20, 300, 40);

        AnimatedButton markAttendance = new AnimatedButton("Mark Attendance", 20,
                new Color(134, 73, 255),
                new Color(110, 60, 220),
                new Color(90, 50, 190),
                new Color(134, 73, 255));
        markAttendance.setBounds(850, 18, 160, 40);
        markAttendance.setForeground(Color.WHITE);
        markAttendance.setFont(new Font("Montserrat", Font.PLAIN, 14));
        markAttendance.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MarkAttendanceDialog dialog = new MarkAttendanceDialog((JFrame) SwingUtilities.getWindowAncestor(AttendancePanel.this));
                dialog.setVisible(true);

                String selectedCourse = courseSelector.getSelectedItem().toString();
                if (selectedCourse != null && !selectedCourse.equals("Select a Course")) {
                    LocalDate selectedDate = dateChooser.getDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    refreshAttendanceTable(selectedCourse, selectedDate);
                }
            }
        });

        courseSelector = new RoundedComboBox(courses, 10);
        courseSelector.setBounds(20, 14, 250, 40);

        dateChooser = new ModernDateChooser();
        dateChooser.setBounds(300, 14, 250, 40);

        AnimatedButton viewAttendance = new AnimatedButton("View Attendance", 20,
                new Color(134, 73, 255),
                new Color(110, 60, 220),
                new Color(90, 50, 190),
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

        attendanceRecord = new JLabel("Attendance Record");
        attendanceRecord.setBounds(30, 15, 200, 30);
        attendanceRecord.setForeground(Color.DARK_GRAY);
        attendanceRecord.setFont(new Font("Montserrat", Font.BOLD, 14));

        attendanceLine = new JLabel("_______________________________________________________________________________" +
                "___________________________________________");
        attendanceLine.setBounds(0, 37, 990, 30);
        attendanceLine.setFont(new Font("Arial", Font.BOLD, 14));
        attendanceLine.setForeground(new Color(240, 240, 240));
        attendanceLine.setHorizontalAlignment(JLabel.CENTER);

        total = new JLabel("Total : 00");
        total.setBounds(653, 15, 100, 30);
        total.setForeground(Color.DARK_GRAY);
        total.setFont(new Font("Montserrat", Font.PLAIN, 13));
        total.setHorizontalAlignment(JLabel.RIGHT);

        presentStudents = new JLabel("Present : 00");
        presentStudents.setBounds(753, 15, 100, 30);
        presentStudents.setForeground(Color.DARK_GRAY);
        presentStudents.setFont(new Font("Montserrat", Font.PLAIN, 13));
        presentStudents.setHorizontalAlignment(JLabel.RIGHT);

        absentStudents = new JLabel("Absent : 00");
        absentStudents.setBounds(853, 15, 100, 30);
        absentStudents.setForeground(Color.DARK_GRAY);
        absentStudents.setFont(new Font("Montserrat", Font.PLAIN, 13));
        absentStudents.setHorizontalAlignment(JLabel.RIGHT);

        noRecordFound = new JLabel("No Record Found for Particular Date and Course");
        noRecordFound.setBounds(0, 0, 990, 440);
        noRecordFound.setForeground(Color.DARK_GRAY);
        noRecordFound.setFont(new Font("Montserrat", Font.PLAIN, 14));
        noRecordFound.setHorizontalAlignment(JLabel.CENTER);
        noRecordFound.setVerticalAlignment(JLabel.CENTER);
        noRecordFound.setVisible(false);

        viewOrMark = new JLabel("<html>Attendance Tracking System<br>Use 'Mark Attendance' to record student presence.<br>View records by selecting date and course filters.</html>");
        viewOrMark.setBounds(0, 0, 990, 440);
        viewOrMark.setForeground(Color.DARK_GRAY);
        viewOrMark.setFont(new Font("Montserrat", Font.PLAIN, 14));
        viewOrMark.setHorizontalAlignment(JLabel.CENTER);
        viewOrMark.setVerticalAlignment(JLabel.CENTER);
        viewOrMark.setVisible(false);

        Object[] attendanceTableColName = {"STUDENT NAME", "COURSE", "ATTENDANCE"};
        attendanceTableModel = new DefaultTableModel(null, attendanceTableColName);
        attendanceTable = new JTable(attendanceTableModel);
        attendanceTable.setForeground(Color.GRAY);
        attendanceTable.setShowGrid(false);
        attendanceTable.setDefaultEditor(Object.class, null);
        attendanceTable.getColumnModel().getColumn(0).setPreferredWidth(257);
        attendanceTable.getColumnModel().getColumn(1).setPreferredWidth(257);
        attendanceTable.getColumnModel().getColumn(2).setPreferredWidth(256);
        attendanceTable.setRowHeight(55);
        attendanceTable.getTableHeader().setResizingAllowed(false);
        attendanceTable.getTableHeader().setReorderingAllowed(false);
        attendanceTable.setBorder(null);
        attendanceTable.setIntercellSpacing(new Dimension(0, 0));

        attendanceTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable attendanceTable, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(attendanceTable, value, isSelected, hasFocus, row, column);
                setOpaque(true);
                setFont(new Font("Montserrat", Font.PLAIN, 13));
                setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                setHorizontalAlignment(JLabel.LEFT);
                setForeground(Color.DARK_GRAY);
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(245, 245, 245)),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)));

                if (column == 0 || column == 1 || column == 2)
                    setHorizontalAlignment(JLabel.CENTER);

                if (row % 2 == 0)
                    setBackground(new Color(250, 250, 250));
                else
                    setBackground(Color.WHITE);
                return this;
            }
        });

        JTableHeader attendanceTableHeader = attendanceTable.getTableHeader();
        attendanceTableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable attendanceTable, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                label.setForeground(Color.GRAY);
                label.setFont(new Font("Montserrat", Font.BOLD, 12));
                label.setHorizontalAlignment(JLabel.LEFT);
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)));

                if (column == 0 || column == 1 || column == 2)
                    label.setHorizontalAlignment(JLabel.CENTER);

                return label;
            }
        });

        attendanceBgScrollBar = new RoundScrollPane(attendanceTable, 30);
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
                String course = courseSelector.getSelectedItem().toString();
                if (course.equals("Select a Course")) {
                    JOptionPane.showMessageDialog(AttendancePanel.this, "Please select a course");
                    return;
                }

                Date selectedDate = dateChooser.getDate();
                LocalDate date = selectedDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                refreshAttendanceTable(course, date);
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

        add(AttendanceMng);
        add(markAttendance);
        add(attendanceWhitePanel);
        add(attendanceWhite1Panel);
        setVisible(false);
    }
}