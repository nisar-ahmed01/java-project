package attendance;

import components.*;
import database.DatabaseConfig;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class MarkAttendanceDialog extends JDialog {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> courseCombo;
    private JLabel dateLabel;
    private JButton saveButton;
    private JButton cancelButton;
    private LocalDate attendanceDate;

    public MarkAttendanceDialog(JFrame parent) {
        super(parent, "Mark Attendance", true);
        setSize(800, 550);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 239, 255));

        attendanceDate = LocalDate.now();

        initialize();
        loadCourses();
    }

    private void initialize() {
        // Top Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(new Font("Montserrat", Font.BOLD, 13));
        courseLabel.setForeground(new Color(33, 37, 40));

        courseCombo = new JComboBox<>();
        courseCombo.setPreferredSize(new Dimension(200, 35));
        courseCombo.setFont(new Font("Montserrat", Font.PLAIN, 13));
        courseCombo.setBackground(Color.WHITE);
        courseCombo.setForeground(new Color(33, 37, 40));
        courseCombo.addActionListener(e -> loadStudentsForCourse());

        JLabel dateTitle = new JLabel("Date:");
        dateTitle.setFont(new Font("Montserrat", Font.BOLD, 13));
        dateTitle.setForeground(new Color(33, 37, 40));

        dateLabel = new JLabel(attendanceDate.toString());
        dateLabel.setFont(new Font("Montserrat", Font.PLAIN, 13));
        dateLabel.setForeground(new Color(100, 100, 100));

        topPanel.add(courseLabel);
        topPanel.add(courseCombo);
        topPanel.add(dateTitle);
        topPanel.add(dateLabel);

        // Table
        String[] columns = {"Student ID", "Student Name", "Attendance"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) return Boolean.class;
                return String.class;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(45);
        studentTable.setFont(new Font("Montserrat", Font.PLAIN, 13));
        studentTable.setForeground(new Color(33, 37, 40));
        studentTable.setShowGrid(false);
        studentTable.setSelectionBackground(new Color(134, 73, 255, 50));
        studentTable.setSelectionForeground(new Color(33, 37, 40));

        studentTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(120);

        studentTable.getColumnModel().getColumn(2).setCellRenderer(new CheckBoxRenderer());
        studentTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        studentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setOpaque(true);
                setFont(new Font("Montserrat", Font.PLAIN, 13));
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(245, 245, 245)),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)));

                if (column == 0 || column == 1) {
                    setHorizontalAlignment(JLabel.LEFT);
                } else {
                    setHorizontalAlignment(JLabel.CENTER);
                }

                setForeground(new Color(33, 37, 40));

                if (row % 2 == 0) {
                    setBackground(new Color(250, 250, 250));
                } else {
                    setBackground(Color.WHITE);
                }

                if (isSelected) {
                    setBackground(new Color(134, 73, 255, 30));
                }

                return this;
            }
        });

        JTableHeader header = studentTable.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(new Color(33, 37, 40));
        header.setFont(new Font("Montserrat", Font.BOLD, 12));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(134, 73, 255)));
        header.setPreferredSize(new Dimension(0, 40));

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                label.setForeground(new Color(33, 37, 40));
                label.setFont(new Font("Montserrat", Font.BOLD, 12));
                label.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

                if (column == 0 || column == 1) {
                    label.setHorizontalAlignment(JLabel.LEFT);
                } else {
                    label.setHorizontalAlignment(JLabel.CENTER);
                }
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBackground(Color.WHITE);

        // Bottom Panel with Purple and White buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));

        // Save Button - Purple theme
        saveButton = new JButton("Save Attendance");
        saveButton.setBackground(new Color(134, 73, 255)); // Purple
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Montserrat", Font.BOLD, 13));
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.setPreferredSize(new Dimension(150, 40));
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(new Color(110, 60, 220)); // Darker purple on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(new Color(134, 73, 255)); // Back to purple
            }
        });
        saveButton.addActionListener(e -> saveAttendance());

        // Cancel Button - White with purple border
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(new Color(134, 73, 255)); // Purple text
        cancelButton.setFont(new Font("Montserrat", Font.BOLD, 13));
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(134, 73, 255), 2));
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setBackground(new Color(134, 73, 255));
                cancelButton.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setBackground(Color.WHITE);
                cancelButton.setForeground(new Color(134, 73, 255));
            }
        });
        cancelButton.addActionListener(e -> dispose());

        bottomPanel.add(saveButton);
        bottomPanel.add(cancelButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadCourses() {
        try {
            DatabaseConfig.setConnection();
            Statement st = DatabaseConfig.getSt();
            String query = "SELECT course_name FROM NewCourse";
            ResultSet rs = st.executeQuery(query);

            courseCombo.removeAllItems();
            while (rs.next()) {
                courseCombo.addItem(rs.getString("course_name"));
            }

            DatabaseConfig.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage());
        }
    }

    private void loadStudentsForCourse() {
        String selectedCourse = (String) courseCombo.getSelectedItem();
        if (selectedCourse == null || selectedCourse.isEmpty()) return;

        tableModel.setRowCount(0);

        try {
            DatabaseConfig.setConnection();
            Statement st = DatabaseConfig.getSt();

            String query = "SELECT s.stu_id, s.firstname, s.lastname, a.attendance " +
                    "FROM NewStudent s " +
                    "LEFT JOIN Attendance a ON s.stu_id = a.studentId " +
                    "AND a.courseName = '" + selectedCourse + "' " +
                    "AND a.attendanceDate = '" + java.sql.Date.valueOf(attendanceDate) + "' " +
                    "WHERE s.stu_id IN (SELECT DISTINCT stu_id FROM Enrollment WHERE course_name = '" + selectedCourse + "')";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String studentId = rs.getString("stu_id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String fullName = firstName + " " + lastName;
                String attendanceValue = rs.getString("attendance");

                boolean isPresent = "Present".equals(attendanceValue);

                tableModel.addRow(new Object[]{studentId, fullName, isPresent});
            }

            DatabaseConfig.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        }
    }

    private void saveAttendance() {
        String selectedCourse = (String) courseCombo.getSelectedItem();
        if (selectedCourse == null || selectedCourse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a course");
            return;
        }

        try {
            DatabaseConfig.setConnection();
            Statement st = DatabaseConfig.getSt();

            String deleteQuery = "DELETE FROM Attendance WHERE courseName = '" + selectedCourse +
                    "' AND attendanceDate = '" + java.sql.Date.valueOf(attendanceDate) + "'";
            st.executeUpdate(deleteQuery);

            int presentCount = 0;

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String studentId = (String) tableModel.getValueAt(i, 0);
                String studentName = (String) tableModel.getValueAt(i, 1);
                Boolean isPresent = (Boolean) tableModel.getValueAt(i, 2);

                if (isPresent != null) {
                    String attendanceStatus = isPresent ? "Present" : "Absent";
                    if (isPresent) presentCount++;

                    String insertQuery = "INSERT INTO Attendance (studentId, studentName, courseName, attendance, attendanceDate) " +
                            "VALUES ('" + studentId + "', '" + studentName + "', '" + selectedCourse + "', '" +
                            attendanceStatus + "', '" + java.sql.Date.valueOf(attendanceDate) + "')";
                    st.executeUpdate(insertQuery);
                }
            }

            DatabaseConfig.closeConnection();

            JOptionPane.showMessageDialog(this,
                    "✓ Attendance saved successfully!\n\n" +
                            "Present: " + presentCount + "\n" +
                            "Absent: " + (tableModel.getRowCount() - presentCount) + "\n" +
                            "Total: " + tableModel.getRowCount(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving attendance: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
        public CheckBoxRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
            setBackground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Boolean) {
                setSelected((Boolean) value);
            }

            if (isSelected) {
                setBackground(new Color(134, 73, 255, 50));
            } else {
                if (row % 2 == 0) {
                    setBackground(new Color(250, 250, 250));
                } else {
                    setBackground(Color.WHITE);
                }
            }

            setHorizontalAlignment(JLabel.CENTER);
            return this;
        }
    }
}