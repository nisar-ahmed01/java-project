//package report;
//
//import components.*;
//import database.DatabaseConfig;
//
//import javax.swing.*;
//import javax.swing.table.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//
//public class ReportPanel {
//    private JPanel reportPanel;
//    private String url = DatabaseConfig.URL;
//
//    // Report type components
//    private JComboBox<String> reportTypeCombo;
//    private JPanel reportContentPanel;
//    private CardLayout cardLayout;
//
//    // Date range components
//    private ModernDateChooser startDateChooser, endDateChooser;
//
//    // Tables for different reports
//    private JTable studentReportTable, courseReportTable, enrollmentReportTable;
//    private DefaultTableModel studentReportModel, courseReportModel, enrollmentReportModel;
//
//    // Summary labels
//    private JLabel totalStudentsLabel, totalCoursesLabel, totalEnrollmentsLabel;
//    private JLabel totalRevenueLabel, monthlyEnrollmentLabel, attendanceRateLabel;
//
//    public ReportPanel() {
//        createReportPanel();
//    }
//
//    private void createReportPanel() {
//        reportPanel = new JPanel();
//        reportPanel.setBackground(new Color(240, 239, 255));
//        reportPanel.setLayout(null);
//
//        // Title
//        JLabel Report = new JLabel("Reports");
//        Report.setFont(new Font("Century Gothic", Font.BOLD, 33));
//        Report.setForeground(new Color(33, 37, 40));
//        Report.setBounds(20, 20, 200, 40);
//        reportPanel.add(Report);
//
//        // Report Type Selection
//        JLabel reportTypeLabel = new JLabel("Select Report Type:");
//        reportTypeLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
//        reportTypeLabel.setForeground(Color.DARK_GRAY);
//        reportTypeLabel.setBounds(30, 80, 150, 30);
//        reportPanel.add(reportTypeLabel);
//
//        String[] reportTypes = {"Student Report", "Course Report", "Enrollment Report",
//                "Financial Report", "Attendance Summary", "Dashboard Summary"};
//        reportTypeCombo = new JComboBox<>(reportTypes);
//        reportTypeCombo.setFont(new Font("Montserrat", Font.PLAIN, 14));
//        reportTypeCombo.setBounds(180, 80, 200, 35);
//        reportTypeCombo.setBackground(Color.WHITE);
//        reportTypeCombo.addActionListener(e -> switchReport());
//        reportPanel.add(reportTypeCombo);
//
//        // Date Range Selection
//        JLabel dateRangeLabel = new JLabel("Date Range:");
//        dateRangeLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
//        dateRangeLabel.setForeground(Color.DARK_GRAY);
//        dateRangeLabel.setBounds(420, 80, 100, 30);
//        reportPanel.add(dateRangeLabel);
//
//        startDateChooser = new ModernDateChooser();
//        startDateChooser.setBounds(520, 75, 180, 40);
//        reportPanel.add(startDateChooser);
//
//        JLabel toLabel = new JLabel("to");
//        toLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
//        toLabel.setBounds(710, 80, 30, 30);
//        reportPanel.add(toLabel);
//
//        endDateChooser = new ModernDateChooser();
//        endDateChooser.setBounds(740, 75, 180, 40);
//        reportPanel.add(endDateChooser);
//
//        // Generate Report Button
//        AnimatedButton generateBtn = new AnimatedButton("Generate Report", 20,
//                new Color(134, 73, 255),
//                new Color(110, 60, 220),
//                new Color(90, 50, 190),
//                new Color(134, 73, 255));
//        generateBtn.setBounds(940, 75, 160, 40);
//        generateBtn.setForeground(Color.WHITE);
//        generateBtn.setFont(new Font("Montserrat", Font.BOLD, 14));
//        generateBtn.addActionListener(e -> generateReport());
//        reportPanel.add(generateBtn);
//
//        // Report Content Panel with CardLayout
//        cardLayout = new CardLayout();
//        reportContentPanel = new JPanel(cardLayout);
//        reportContentPanel.setBounds(20, 140, 990, 490);
//        reportContentPanel.setBackground(Color.WHITE);
//        reportContentPanel.setBorder(new RoundedBorder(20, Color.LIGHT_GRAY));
//
//        // Create all report panels
//        createStudentReportPanel();
//        createCourseReportPanel();
//        createEnrollmentReportPanel();
//        createFinancialReportPanel();
//        createAttendanceSummaryPanel();
//        createDashboardSummaryPanel();
//
//        reportPanel.add(reportContentPanel);
//
//        // Initially show student report
//        switchReport();
//    }
//
//    private void switchReport() {
//        String selected = (String) reportTypeCombo.getSelectedItem();
//        switch (selected) {
//            case "Student Report":
//                cardLayout.show(reportContentPanel, "Student");
//                break;
//            case "Course Report":
//                cardLayout.show(reportContentPanel, "Course");
//                break;
//            case "Enrollment Report":
//                cardLayout.show(reportContentPanel, "Enrollment");
//                break;
//            case "Financial Report":
//                cardLayout.show(reportContentPanel, "Financial");
//                break;
//            case "Attendance Summary":
//                cardLayout.show(reportContentPanel, "Attendance");
//                break;
//            case "Dashboard Summary":
//                cardLayout.show(reportContentPanel, "Dashboard");
//                break;
//        }
//    }
//
//    private void createStudentReportPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBackground(Color.WHITE);
//        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        // Table
//        String[] columns = {"Student ID", "Student Name", "Email", "Phone", "Age", "Gender", "Enrolled Courses"};
//        studentReportModel = new DefaultTableModel(null, columns);
//        studentReportTable = new JTable(studentReportModel);
//        styleTable(studentReportTable, new int[]{100, 180, 200, 120, 60, 100, 150});
//
//        JScrollPane scrollPane = new JScrollPane(studentReportTable);
//        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
//        panel.add(scrollPane, BorderLayout.CENTER);
//
//        reportContentPanel.add(panel, "Student");
//    }
//
//    private void createCourseReportPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBackground(Color.WHITE);
//        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        // Table
//        String[] columns = {"Course ID", "Course Name", "Course Code", "Duration", "Fee (PKR)", "Enrolled Students", "Total Revenue"};
//        courseReportModel = new DefaultTableModel(null, columns);
//        courseReportTable = new JTable(courseReportModel);
//        styleTable(courseReportTable, new int[]{80, 200, 120, 100, 100, 150, 150});
//
//        JScrollPane scrollPane = new JScrollPane(courseReportTable);
//        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
//        panel.add(scrollPane, BorderLayout.CENTER);
//
//        reportContentPanel.add(panel, "Course");
//    }
//
//    private void createEnrollmentReportPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBackground(Color.WHITE);
//        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        // Table
//        String[] columns = {"Enrollment ID", "Student ID", "Student Name", "Course Name", "Enrollment Date", "Duration", "Fee Paid"};
//        enrollmentReportModel = new DefaultTableModel(null, columns);
//        enrollmentReportTable = new JTable(enrollmentReportModel);
//        styleTable(enrollmentReportTable, new int[]{100, 100, 180, 200, 120, 100, 120});
//
//        JScrollPane scrollPane = new JScrollPane(enrollmentReportTable);
//        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
//        panel.add(scrollPane, BorderLayout.CENTER);
//
//        reportContentPanel.add(panel, "Enrollment");
//    }
//
//    private void createFinancialReportPanel() {
//        JPanel panel = new JPanel();
//        panel.setBackground(Color.WHITE);
//        panel.setLayout(null);
//        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        // Summary Cards
//        JLabel revenueTitle = new JLabel("Financial Summary");
//        revenueTitle.setFont(new Font("Century Gothic", Font.BOLD, 24));
//        revenueTitle.setBounds(20, 20, 300, 40);
//        panel.add(revenueTitle);
//
//        // Total Revenue Card
//        RoundedPanel revenueCard = createSummaryCard("Total Revenue", "0 PKR", new Color(134, 73, 255));
//        revenueCard.setBounds(20, 80, 300, 120);
//        panel.add(revenueCard);
//
//        // Average Fee Card
//        RoundedPanel avgFeeCard = createSummaryCard("Average Course Fee", "0 PKR", new Color(251, 219, 179));
//        avgFeeCard.setBounds(340, 80, 300, 120);
//        panel.add(avgFeeCard);
//
//        // Total Enrollments Card
//        RoundedPanel enrollCard = createSummaryCard("Total Enrollments", "0", new Color(148, 240, 151));
//        enrollCard.setBounds(660, 80, 300, 120);
//        panel.add(enrollCard);
//
//        // Monthly Revenue Chart Placeholder
//        JLabel chartLabel = new JLabel("Monthly Revenue Trend");
//        chartLabel.setFont(new Font("Montserrat", Font.BOLD, 16));
//        chartLabel.setBounds(20, 230, 300, 30);
//        panel.add(chartLabel);
//
//        // Store references for updating
//        totalRevenueLabel = revenueCard.getComponentCount() > 2 ?
//                (JLabel) revenueCard.getComponent(2) : new JLabel();
//        totalRevenueLabel.setText("0 PKR");
//
//        reportContentPanel.add(panel, "Financial");
//    }
//
//    private void createAttendanceSummaryPanel() {
//        JPanel panel = new JPanel();
//        panel.setBackground(Color.WHITE);
//        panel.setLayout(null);
//        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        JLabel attendanceTitle = new JLabel("Attendance Summary");
//        attendanceTitle.setFont(new Font("Century Gothic", Font.BOLD, 24));
//        attendanceTitle.setBounds(20, 20, 300, 40);
//        panel.add(attendanceTitle);
//
//        // Overall Attendance Rate Card
//        RoundedPanel rateCard = createSummaryCard("Overall Attendance Rate", "0%", new Color(134, 73, 255));
//        rateCard.setBounds(20, 80, 300, 120);
//        panel.add(rateCard);
//
//        // Total Classes Card
//        RoundedPanel classesCard = createSummaryCard("Total Classes Conducted", "0", new Color(251, 219, 179));
//        classesCard.setBounds(340, 80, 300, 120);
//        panel.add(classesCard);
//
//        // Average Attendance Card
//        RoundedPanel avgCard = createSummaryCard("Average Present per Class", "0", new Color(148, 240, 151));
//        avgCard.setBounds(660, 80, 300, 120);
//        panel.add(avgCard);
//
//        attendanceRateLabel = rateCard.getComponentCount() > 2 ?
//                (JLabel) rateCard.getComponent(2) : new JLabel();
//        attendanceRateLabel.setText("0%");
//
//        reportContentPanel.add(panel, "Attendance");
//    }
//
//    private void createDashboardSummaryPanel() {
//        JPanel panel = new JPanel();
//        panel.setBackground(Color.WHITE);
//        panel.setLayout(null);
//        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        JLabel dashboardTitle = new JLabel("Dashboard Summary");
//        dashboardTitle.setFont(new Font("Century Gothic", Font.BOLD, 24));
//        dashboardTitle.setBounds(20, 20, 300, 40);
//        panel.add(dashboardTitle);
//
//        // Statistics Cards
//        RoundedPanel studentsCard = createSummaryCard("Total Students", "0", new Color(134, 73, 255));
//        studentsCard.setBounds(20, 80, 220, 100);
//        panel.add(studentsCard);
//
//        RoundedPanel coursesCard = createSummaryCard("Total Courses", "0", new Color(251, 219, 179));
//        coursesCard.setBounds(260, 80, 220, 100);
//        panel.add(coursesCard);
//
//        RoundedPanel enrollmentsCard = createSummaryCard("Total Enrollments", "0", new Color(148, 240, 151));
//        enrollmentsCard.setBounds(500, 80, 220, 100);
//        panel.add(enrollmentsCard);
//
//        RoundedPanel revenueCard = createSummaryCard("Total Revenue", "0 PKR", new Color(165, 176, 254));
//        revenueCard.setBounds(740, 80, 220, 100);
//        panel.add(revenueCard);
//
//        // Store references
//        totalStudentsLabel = studentsCard.getComponentCount() > 2 ?
//                (JLabel) studentsCard.getComponent(2) : new JLabel();
//        totalCoursesLabel = coursesCard.getComponentCount() > 2 ?
//                (JLabel) coursesCard.getComponent(2) : new JLabel();
//        totalEnrollmentsLabel = enrollmentsCard.getComponentCount() > 2 ?
//                (JLabel) enrollmentsCard.getComponent(2) : new JLabel();
//        totalRevenueLabel = revenueCard.getComponentCount() > 2 ?
//                (JLabel) revenueCard.getComponent(2) : new JLabel();
//
//        reportContentPanel.add(panel, "Dashboard");
//    }
//
//    private RoundedPanel createSummaryCard(String title, String value, Color color) {
//        RoundedPanel card = new RoundedPanel(15);
//        card.setBackground(Color.WHITE);
//        card.setBorder(new RoundedBorder(15, color));
//        card.setLayout(null);
//
//        RoundedPanel colorBar = new RoundedPanel(10);
//        colorBar.setBackground(color);
//        colorBar.setBounds(0, 0, 8, 120);
//        colorBar.setLayout(null);
//        card.add(colorBar);
//
//        JLabel titleLabel = new JLabel(title);
//        titleLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
//        titleLabel.setForeground(Color.DARK_GRAY);
//        titleLabel.setBounds(20, 25, 250, 25);
//        card.add(titleLabel);
//
//        JLabel valueLabel = new JLabel(value);
//        valueLabel.setFont(new Font("Montserrat", Font.BOLD, 24));
//        valueLabel.setForeground(color);
//        valueLabel.setBounds(20, 55, 250, 40);
//        card.add(valueLabel);
//
//        return card;
//    }
//
//    private void styleTable(JTable table, int[] columnWidths) {
//        table.setForeground(Color.GRAY);
//        table.setShowGrid(false);
//        table.setDefaultEditor(Object.class, null);
//        table.setRowHeight(45);
//        table.getTableHeader().setResizingAllowed(false);
//        table.getTableHeader().setReorderingAllowed(false);
//        table.setBorder(null);
//        table.setIntercellSpacing(new Dimension(0, 0));
//
//        for (int i = 0; i < columnWidths.length && i < table.getColumnCount(); i++) {
//            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
//        }
//
//        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
//                                                           boolean hasFocus, int row, int column) {
//                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
//                setOpaque(true);
//                setFont(new Font("Montserrat", Font.PLAIN, 12));
//                setBorder(BorderFactory.createCompoundBorder(
//                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
//                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
//                setForeground(Color.DARK_GRAY);
//
//                if (row % 2 == 0) setBackground(new Color(250, 250, 250));
//                else setBackground(Color.WHITE);
//                return this;
//            }
//        });
//
//        JTableHeader header = table.getTableHeader();
//        header.setDefaultRenderer(new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
//                                                           boolean hasFocus, int row, int column) {
//                JLabel label = new JLabel(value.toString());
//                label.setOpaque(true);
//                label.setBackground(new Color(240, 239, 255));
//                label.setForeground(Color.DARK_GRAY);
//                label.setFont(new Font("Montserrat", Font.BOLD, 12));
//                label.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
//                return label;
//            }
//        });
//    }
//
//    private void generateReport() {
//        String selectedReport = (String) reportTypeCombo.getSelectedItem();
//        Date startDate = startDateChooser.getDate();
//        Date endDate = endDateChooser.getDate();
//
//        switch (selectedReport) {
//            case "Student Report":
//                generateStudentReport();
//                break;
//            case "Course Report":
//                generateCourseReport();
//                break;
//            case "Enrollment Report":
//                generateEnrollmentReport(startDate, endDate);
//                break;
////            case "Financial Report":
////                generateFinancialReport(startDate, endDate);
////                break;
//            case "Attendance Summary":
//                generateAttendanceSummary(startDate, endDate);
//                break;
//            case "Dashboard Summary":
//                generateDashboardSummary();
//                break;
//        }
//    }
//
//    private void generateStudentReport() {
//        studentReportModel.setRowCount(0);
//
//        try {
//            Connection con = DriverManager.getConnection(url, "root", "Nisar123");
//            Statement st = con.createStatement();
//
//            String query = "SELECT stu_id, firstname, lastname, email, phone, age, gender FROM NewStudent";
//            ResultSet rs = st.executeQuery(query);
//
//            while (rs.next()) {
//                String stuId = rs.getString("stu_id");
//                String name = rs.getString("firstname") + " " + rs.getString("lastname");
//
//                // Get enrolled courses count
//                String countQuery = String.format("SELECT COUNT(*) as count FROM Enrollment WHERE stu_id = '%s'", stuId);
//                Statement countSt = con.createStatement();
//                ResultSet countRs = countSt.executeQuery(countQuery);
//                int courseCount = countRs.next() ? countRs.getInt("count") : 0;
//                countRs.close();
//                countSt.close();
//
//                Object[] row = {stuId, name, rs.getString("email"), rs.getString("phone"),
//                        rs.getString("age"), rs.getString("gender"), courseCount};
//                studentReportModel.addRow(row);
//            }
//            con.close();
//
//            JOptionPane.showMessageDialog(reportPanel, "Student Report Generated Successfully!",
//                    "Success", JOptionPane.INFORMATION_MESSAGE);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(reportPanel, "Error generating report: " + ex.getMessage(),
//                    "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void generateCourseReport() {
//        courseReportModel.setRowCount(0);
//
//        try {
//            Connection con = DriverManager.getConnection(url, "root", "Nisar123");
//            Statement st = con.createStatement();
//
//            String query = "SELECT id, course_name, course_code, duration, course_fee FROM NewCourse";
//            ResultSet rs = st.executeQuery(query);
//
//            while (rs.next()) {
//                int courseId = rs.getInt("id");
//                String courseName = rs.getString("course_name");
//
//                // Get enrolled students count and total revenue
//                String enrollQuery = String.format("SELECT COUNT(*) as count, SUM(fee_paid) as revenue FROM Enrollment WHERE course_id = %d", courseId);
//                Statement enrollSt = con.createStatement();
//                ResultSet enrollRs = enrollSt.executeQuery(enrollQuery);
//                int studentCount = 0;
//                int revenue = 0;
//                if (enrollRs.next()) {
//                    studentCount = enrollRs.getInt("count");
//                    revenue = enrollRs.getInt("revenue");
//                }
//                enrollRs.close();
//                enrollSt.close();
//
//                Object[] row = {courseId, courseName, rs.getString("course_code"),
//                        rs.getString("duration"), rs.getInt("course_fee"), studentCount, revenue + " PKR"};
//                courseReportModel.addRow(row);
//            }
//            con.close();
//
//            JOptionPane.showMessageDialog(reportPanel, "Course Report Generated Successfully!",
//                    "Success", JOptionPane.INFORMATION_MESSAGE);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(reportPanel, "Error generating report: " + ex.getMessage(),
//                    "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void generateEnrollmentReport(Date startDate, Date endDate) {
//        enrollmentReportModel.setRowCount(0);
//
//        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//        try {
//            Connection con = DriverManager.getConnection(url, "root", "Nisar123");
//            String query = "SELECT * FROM Enrollment WHERE enrollment_date BETWEEN ? AND ?";
//            PreparedStatement pst = con.prepareStatement(query);
//            pst.setString(1, start.toString());
//            pst.setString(2, end.toString());
//            ResultSet rs = pst.executeQuery();
//
//            int count = 1;
//            while (rs.next()) {
//                Object[] row = {count++, rs.getString("stu_id"), rs.getString("stu_name"),
//                        rs.getString("course_name"), rs.getString("enrollment_date"),
//                        rs.getString("duration"), rs.getInt("fee_paid") + " PKR"};
//                enrollmentReportModel.addRow(row);
//            }
//            con.close();
//
//            JOptionPane.showMessageDialog(reportPanel, "Enrollment Report Generated Successfully!",
//                    "Success", JOptionPane.INFORMATION_MESSAGE);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(reportPanel, "Error generating report: " + ex.getMessage(),
//                    "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
////    private void generateFinancialReport(Date startDate, Date endDate) {
////        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
////        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
////
////        try {
////            Connection con = DriverManager.getConnection(url, "root", "Nisar123");
////
////            // Total Revenue
////            String revenueQuery = "SELECT SUM(fee_paid) as total FROM Enrollment WHERE enrollment_date BETWEEN ? AND ?";
////            PreparedStatement pst = con.prepareStatement(revenueQuery);
////            pst.setString(1, start.toString());
////            pst.setString(2, end.toString());
////            ResultSet rs = pst.executeQuery();
////            int totalRevenue = rs.next() ? rs.getInt("total") : 0;
////            totalRevenueLabel.setText(totalRevenue + " PKR");
////            rs.close();
////            pst.close();
////
////            // Average Course Fee
////            String avgQuery = "SELECT AVG(course_fee) as avg FROM NewCourse";
////            Statement st = con.createStatement();
////            ResultSet avgRs = st.executeQuery(avgQuery);
////            int avgFee = avgRs.next() ? avgRs.getInt("avg") : 0;
////            avgRs.close();
////
////            // Total Enrollments in date range
////            String enrollQuery = "SELECT COUNT(*) as count FROM Enrollment WHERE enrollment_date BETWEEN ? AND ?";
////            pst = con.prepareStatement(enrollQuery);
////            pst.setString(1, start.toString());
////            pst.setString(2, end.toString());
////            rs = pst.executeQuery();
////            int enrollCount = rs.next() ? rs.getInt("count") : 0;
////            rs.close();
////            pst.close();
////            con.close();
////
////            // Update the card values - need to find the labels in the panel
////            updateFinancialCardValues(totalRevenue + " PKR", avgFee + " PKR", String.valueOf(enrollCount));
////
////            JOptionPane.showMessageDialog(reportPanel, "Financial Report Generated Successfully!",
////                    "Success", JOptionPane.INFORMATION_MESSAGE);
////        } catch (SQLException ex) {
////            ex.printStackTrace();
////            JOptionPane.showMessageDialog(reportPanel, "Error generating report: " + ex.getMessage(),
////                    "Error", JOptionPane.ERROR_MESSAGE);
////        }
////    }
////
////    private void updateFinancialCardValues(String revenue, String avgFee, String enrollments) {
////        // Find and update the labels in the financial report panel
////        Component[] components = reportContentPanel.getComponents();
////        for (Component comp : components) {
////            if (comp instanceof JPanel && cardLayout.getConstraints(comp).equals("Financial")) {
////                JPanel panel = (JPanel) comp;
////                for (Component child : panel.getComponents()) {
////                    if (child instanceof RoundedPanel) {
////                        RoundedPanel card = (RoundedPanel) child;
////                        for (Component label : card.getComponents()) {
////                            if (label instanceof JLabel) {
////                                JLabel lbl = (JLabel) label;
////                                if (lbl.getFont().getSize() == 24) {
////                                    if (lbl.getText().equals("0 PKR")) {
////                                        lbl.setText(revenue);
////                                        break;
////                                    }
////                                }
////                            }
////                        }
////                    }
////                }
////                break;
////            }
////        }
////    }
//
//    private void generateAttendanceSummary(Date startDate, Date endDate) {
//        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//        try {
//            Connection con = DriverManager.getConnection(url, "root", "Nisar123");
//
//            // Total attendance records
//            String query = "SELECT COUNT(*) as total, SUM(CASE WHEN attendance = 'Present' THEN 1 ELSE 0 END) as present " +
//                    "FROM Attendance WHERE attendanceDate BETWEEN ? AND ?";
//            PreparedStatement pst = con.prepareStatement(query);
//            pst.setString(1, start.toString());
//            pst.setString(2, end.toString());
//            ResultSet rs = pst.executeQuery();
//
//            int total = 0, present = 0;
//            if (rs.next()) {
//                total = rs.getInt("total");
//                present = rs.getInt("present");
//            }
//            rs.close();
//            pst.close();
//
//            // Unique courses count
//            String courseQuery = "SELECT COUNT(DISTINCT courseName) as courses FROM Attendance WHERE attendanceDate BETWEEN ? AND ?";
//            pst = con.prepareStatement(courseQuery);
//            pst.setString(1, start.toString());
//            pst.setString(2, end.toString());
//            rs = pst.executeQuery();
//            int courses = rs.next() ? rs.getInt("courses") : 0;
//            rs.close();
//            pst.close();
//            con.close();
//
//            double attendanceRate = total > 0 ? (present * 100.0 / total) : 0;
//            double avgPresentPerClass = courses > 0 ? (present * 1.0 / courses) : 0;
//
//            attendanceRateLabel.setText(String.format("%.1f%%", attendanceRate));
//
//            JOptionPane.showMessageDialog(reportPanel, "Attendance Summary Generated Successfully!",
//                    "Success", JOptionPane.INFORMATION_MESSAGE);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(reportPanel, "Error generating report: " + ex.getMessage(),
//                    "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void generateDashboardSummary() {
//        try {
//            Connection con = DriverManager.getConnection(url, "root", "Nisar123");
//
//            // Total Students
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT COUNT(*) as count FROM NewStudent");
//            int totalStudents = rs.next() ? rs.getInt("count") : 0;
//            rs.close();
//
//            // Total Courses
//            rs = st.executeQuery("SELECT COUNT(*) as count FROM NewCourse");
//            int totalCourses = rs.next() ? rs.getInt("count") : 0;
//            rs.close();
//
//            // Total Enrollments
//            rs = st.executeQuery("SELECT COUNT(*) as count FROM Enrollment");
//            int totalEnrollments = rs.next() ? rs.getInt("count") : 0;
//            rs.close();
//
//            // Total Revenue
//            rs = st.executeQuery("SELECT SUM(fee_paid) as revenue FROM Enrollment");
//            int totalRevenue = rs.next() ? rs.getInt("revenue") : 0;
//            rs.close();
//            con.close();
//
//            totalStudentsLabel.setText(String.valueOf(totalStudents));
//            totalCoursesLabel.setText(String.valueOf(totalCourses));
//            totalEnrollmentsLabel.setText(String.valueOf(totalEnrollments));
//            totalRevenueLabel.setText(totalRevenue + " PKR");
//
//            JOptionPane.showMessageDialog(reportPanel, "Dashboard Summary Generated Successfully!",
//                    "Success", JOptionPane.INFORMATION_MESSAGE);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(reportPanel, "Error generating report: " + ex.getMessage(),
//                    "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    public JPanel getPanel() {
//        return reportPanel;
//    }
//
//    public void showPanel() {
//        reportPanel.setVisible(true);
//    }
//
//    public void hidePanel() {
//        reportPanel.setVisible(false);
//    }
//
//    public void setBounds(int x, int y, int width, int height) {
//        reportPanel.setBounds(x, y, width, height);
//    }
//}