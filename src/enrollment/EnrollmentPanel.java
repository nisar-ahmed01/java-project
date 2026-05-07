package enrollment;

import components.*;
import dashboard.MngMainFrame;
import database.DatabaseConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.*;

import static dashboard.MngMainFrame.dashboardPanel;
import static dashboard.MngMainFrame.studentPanel;
import static dashboard.dashboard.recentEnrollTableModel;
import static dashboard.dashboard.refreshRecentEnrollTable;
import static student.StudentPanel.model;
import static student.StudentPanel.table;

public class EnrollmentPanel extends JPanel {
    MngMainFrame frame;
    String[] enrollColumnNames = {"S.No","COURSE NAME","FEE"};
    DefaultTableModel enrollTableModel = new DefaultTableModel(null,enrollColumnNames);
    JTable enrollTable = new JTable(enrollTableModel);
    private RoundedComboBox selectStudentField; // Made class-level for access

    private static void refreshEnrollTable(DefaultTableModel enrollTableModel) {
        // Clear existing rows
        enrollTableModel.setRowCount(0);

        try {
            Connection con = DriverManager.getConnection(DatabaseConfig.URL, "root", "Nisar123");
            Statement st = con.createStatement();
            String query = "select course_name, course_fee from NewCourse";
            ResultSet result = st.executeQuery(query);
            int j = 1;
            while(result.next()){
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

    // NEW METHOD: Load students into combo box
    private void loadStudentsToComboBox(RoundedComboBox comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("Select a Student");

        try {
            Connection con = DriverManager.getConnection(DatabaseConfig.URL, "root", "Nisar123");
            Statement st = con.createStatement();
            String query = "SELECT stu_id, firstname, lastname, email FROM NewStudent";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String stuId = rs.getString("stu_id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String displayText = firstName + " " + lastName + " (#" + stuId + ")";
                comboBox.addItem(displayText);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading students: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public EnrollmentPanel(MngMainFrame frame) {
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

    public void visibility(boolean visible){
        this.setVisible(visible);
    }

    void initialize(){
        JLabel enrollCourseText = new JLabel("Enroll Student To Courses");
        enrollCourseText.setForeground(Color.WHITE);
        enrollCourseText.setFont(new Font("Montserrat",Font.BOLD,22));
        enrollCourseText.setBounds(20,20,350,40);

        AnimatedButton backEnrollBtn = new AnimatedButton(" Back to Students",15
                ,new Color(255, 255, 255) //normal Color
                ,new Color(255, 255, 255) //Hovered Color
                ,new Color(255,255,255) // Pressed Color
                ,new Color(255, 255, 255)); // Border Color
        backEnrollBtn.setForeground(new Color(134, 73, 255));
        backEnrollBtn.setFont(new Font("Montserrat",Font.PLAIN,14));
        backEnrollBtn.setBounds(790,20,180,40);
        ImageIcon img7 = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\back1.png",15,15);
        backEnrollBtn.setIcon(img7);
        backEnrollBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backEnrollBtn.setBounds(790,20,180,40);
                backEnrollBtn.setBounds(790,19,180,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backEnrollBtn.setBounds(790,19,180,40);
                backEnrollBtn.setBounds(790,20,180,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                MngMainFrame.backPanel.setBounds(15,193,210,45);
                studentPanel.setVisible(true);
            }
        });

        RoundedPanel enrollStudentUpperPanel = new RoundedPanel(20);
        enrollStudentUpperPanel.setBounds(20,25,990,80);
        enrollStudentUpperPanel.setBackground(new Color(134, 73, 255,230));
        enrollStudentUpperPanel.setLayout(null);
        enrollStudentUpperPanel.add(backEnrollBtn);
        enrollStudentUpperPanel.add(enrollCourseText);

        JLabel courseSelection = new JLabel("Course Selection");
        courseSelection.setForeground(Color.DARK_GRAY);
        courseSelection.setFont(new Font("Montserrat",Font.BOLD,14));
        courseSelection.setBounds(28,15,340,30);

        JLabel multiNotAllowed = new JLabel("Multi-select not allowed");
        multiNotAllowed.setForeground(Color.DARK_GRAY);
        multiNotAllowed.setFont(new Font("Montserrat",Font.PLAIN,12));
        multiNotAllowed.setBounds(270,15,340,30);
        multiNotAllowed.setHorizontalAlignment(JLabel.RIGHT);

        JLabel courseSelectionLine = new JLabel("_______________________________________________________________________________");
        courseSelectionLine.setBounds(0,37,635,30);
        courseSelectionLine.setFont(new Font("Arial",Font.BOLD,14));
        courseSelectionLine.setForeground(new Color(240,240,240));
        courseSelectionLine.setHorizontalAlignment(JLabel.CENTER);

        // FIXED: Changed default values
        JLabel courseSelectedAns = new JLabel("00");
        JLabel totalFeeAns = new JLabel("0.00 Rs");  // Was "$2100.00"
        JLabel Course_Name = new JLabel("• None Selected");  // Was "• Web Development"

        refreshEnrollTable(enrollTableModel);
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
                int selectedRow = enrollTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String courseName = enrollTableModel.getValueAt(selectedRow, 1).toString();
                    String fee = enrollTableModel.getValueAt(selectedRow, 2).toString();
                    courseSelectedAns.setText("01");
                    totalFeeAns.setText(fee);
                    Course_Name.setText("• " + courseName);
                }
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
                }
                else if (row == hoverRow[0]) {
                    setBackground(new Color(230, 230, 255,100)); // hover color
                    setFont(new Font("Montserrat", Font.BOLD, 13));
                }
                else {
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
                                                new Color(240,240,240)),
                                        BorderFactory.createEmptyBorder(
                                                15, 15, 15, 15)
                                )
                        );

                        return label;
                    }
                });

        RoundScrollPane enrollTableBg = new RoundScrollPane (enrollTable,20);
        enrollTableBg.setBounds(20,70,595,375);
        enrollTableBg.setBackground(Color.WHITE);
        enrollTableBg.setBorder(BorderFactory.createEmptyBorder());
        enrollTableBg.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        enrollTableBg.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        enrollTableBg.getVerticalScrollBar().setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        enrollTableBg.getVerticalScrollBar().setUI(new ModernScrollBarUI(new Color(134, 73, 255,150),new Color(240,240,240)));

        RoundedPanel enrollLeftPanel = new RoundedPanel(20);
        enrollLeftPanel.setBounds(20,120,635,480);
        enrollLeftPanel.setBackground(Color.WHITE);
        enrollLeftPanel.setLayout(null);
        enrollLeftPanel.add(courseSelection);
        enrollLeftPanel.add(courseSelectionLine);
        enrollLeftPanel.add(multiNotAllowed);
        enrollLeftPanel.add(enrollTableBg);

        JLabel studentSummary = new JLabel("Student & Summary");
        studentSummary.setForeground(Color.DARK_GRAY);
        studentSummary.setFont(new Font("Montserrat",Font.BOLD,14));
        studentSummary.setBounds(28,15,340,30);

        JLabel summaryLine = new JLabel("__________________________________________");
        summaryLine.setBounds(0,37,340,30);
        summaryLine.setFont(new Font("Arial",Font.BOLD,14));
        summaryLine.setForeground(new Color(240,240,240));
        summaryLine.setHorizontalAlignment(JLabel.CENTER);

        JLabel selectStudent = new JLabel("Select Student");
        selectStudent.setForeground(Color.darkGray);
        selectStudent.setFont(new Font("Montserrat",Font.PLAIN,14));
        selectStudent.setBounds(25,75,340,30);

        JLabel selectedStudentName = new JLabel("John Doe");
        JLabel selectedStudentEmail = new JLabel("john@example.com");

        // FIXED: Populate combo box with students from database
        String[] studentsList = {"Select a Student"};
        selectStudentField = new RoundedComboBox(studentsList,15);
        selectStudentField.setBounds(25,108,290,40);
        selectStudentField.setFont(new Font("Montserrat",Font.PLAIN,14));

        // Load students from database
        loadStudentsToComboBox(selectStudentField);

        selectStudentField.addActionListener(e -> {
            String selectedItem = (String) selectStudentField.getSelectedItem();
            String id;
            if(selectedItem != null && !selectedItem.equals("Select a Student")){
                selectedStudentName.setText(selectedItem.substring(0, selectedItem.indexOf("#")-2));
                id = selectedItem.substring(selectedItem.indexOf("#")+1, selectedItem.indexOf(")"));
                for(int i = 0; i < table.getRowCount(); i++){
                    if(id.equals(model.getValueAt(i,0).toString())) {
                        selectedStudentEmail.setText(model.getValueAt(i, 2).toString());
                    }
                }
            }
            else {
                selectedStudentName.setText("John Doe");
                selectedStudentEmail.setText("john@example.com");
            }
        });

        JLabel selectedStudentText = new JLabel("Selected Student");
        selectedStudentText.setForeground(Color.DARK_GRAY);
        selectedStudentText.setFont(new Font("Montserrat",Font.PLAIN,13));
        selectedStudentText.setBounds(15,15,200,25);

        selectedStudentName.setForeground(Color.DARK_GRAY);
        selectedStudentName.setFont(new Font("Montserrat",Font.BOLD,13));
        selectedStudentName.setBounds(15,35,250,25);

        selectedStudentEmail.setForeground(Color.DARK_GRAY);
        selectedStudentEmail.setFont(new Font("Montserrat",Font.PLAIN,13));
        selectedStudentEmail.setBounds(15,55,250,25);

        RoundedPanel selectedStudent = new RoundedPanel(15);
        selectedStudent.setBounds(25,155,290,95);
        selectedStudent.setBackground(new Color(250, 250, 250));
        selectedStudent.setBorder(new RoundedBorder(15,Color.lightGray));
        selectedStudent.setLayout(null);
        selectedStudent.add(selectedStudentText);
        selectedStudent.add(selectedStudentName);
        selectedStudent.add(selectedStudentEmail);

        JLabel courseSelectedText = new JLabel("Courses Selected");
        courseSelectedText.setForeground(Color.DARK_GRAY);
        courseSelectedText.setFont(new Font("Montserrat",Font.PLAIN,14));
        courseSelectedText.setBounds(15,16,150,25);

        courseSelectedAns.setForeground(Color.DARK_GRAY);
        courseSelectedAns.setFont(new Font("Montserrat",Font.BOLD,14));
        courseSelectedAns.setBounds(175,16,100,25);
        courseSelectedAns.setHorizontalAlignment(JLabel.RIGHT);

        JLabel totalFeeText = new JLabel("Total Fee");
        totalFeeText.setForeground(Color.DARK_GRAY);
        totalFeeText.setFont(new Font("Montserrat",Font.PLAIN,14));
        totalFeeText.setBounds(15,40,100,25);

        totalFeeAns.setForeground(new Color(134, 73, 255));
        totalFeeAns.setFont(new Font("Montserrat",Font.BOLD,14));
        totalFeeAns.setBounds(175,40,100,25);
        totalFeeAns.setHorizontalAlignment(JLabel.RIGHT);

        RoundedPanel selectedCourse = new RoundedPanel(15);
        selectedCourse.setBounds(25,262,290,80);
        selectedCourse.setBackground(new Color(250, 250, 250));
        selectedCourse.setBorder(new RoundedBorder(15,Color.lightGray));
        selectedCourse.setLayout(null);
        selectedCourse.add(courseSelectedText);
        selectedCourse.add(courseSelectedAns);
        selectedCourse.add(totalFeeText);
        selectedCourse.add(totalFeeAns);

        JLabel selectedCourseText = new JLabel("Selected Courses");
        selectedCourseText.setForeground(Color.DARK_GRAY);
        selectedCourseText.setFont(new Font("Montserrat",Font.PLAIN,13));
        selectedCourseText.setBounds(25,355,200,25);

        Course_Name.setForeground(Color.DARK_GRAY);
        Course_Name.setFont(new Font("Montserrat",Font.BOLD,14));
        Course_Name.setBounds(40,380,200,25);

        AnimatedButton cancelBtn = new AnimatedButton("Cancel",40
                ,Color.WHITE
                ,new Color(250,250,250)
                ,new Color(245,245,245)
                ,Color.lightGray);
        cancelBtn.setForeground(Color.darkGray);
        cancelBtn.setFont(new Font("Montserrat",Font.PLAIN,16));
        cancelBtn.setBounds(25,420,135,40);
        cancelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                cancelBtn.setBounds(25,420,135,40);
                cancelBtn.setBounds(25,419,135,40);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cancelBtn.setBounds(25,419,135,40);
                cancelBtn.setBounds(25,420,135,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // FIXED: Reset all values properly
                courseSelectedAns.setText("00");
                totalFeeAns.setText("0.00 Rs");
                Course_Name.setText("• None Selected");
                enrollTable.clearSelection();
                selectStudentField.setSelectedIndex(0);
                refreshEnrollTable(enrollTableModel);
            }
        });

        AnimatedButton enrollBtn = new AnimatedButton("Enroll",40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        enrollBtn.setForeground(Color.WHITE);
        enrollBtn.setFont(new Font("Montserrat",Font.PLAIN,16));
        enrollBtn.setBounds(180,420,135,40);
        enrollBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                enrollBtn.setBounds(180,420,135,40);
                enrollBtn.setBounds(180,419,135,40);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                enrollBtn.setBounds(180,419,135,40);
                enrollBtn.setBounds(180,420,135,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(selectStudentField.getSelectedItem().equals("Select a Student")){
                    JOptionPane.showMessageDialog(null,
                            "Please select a student first!",
                            "No Student Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
                else if(enrollTable.getSelectedRow()<0){
                    JOptionPane.showMessageDialog(null,
                            "Please select a course first!",
                            "No Course Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
                else{
                    String selected_Item = selectStudentField.getSelectedItem().toString();
                    String id = selected_Item.substring(selected_Item.indexOf("#")+1, selected_Item.indexOf(")"));
                    String fullName = selectedStudentName.getText();
                    String courseName = enrollTableModel.getValueAt(enrollTable.getSelectedRow(),1).toString();
                    String paidFee = enrollTableModel.getValueAt(enrollTable.getSelectedRow(),2).toString();
                    int paid_Fee = Integer.parseInt(paidFee.substring(0,paidFee.indexOf(".")));
                    try {
                        DatabaseConfig.setConnection();
                        Statement st = DatabaseConfig.getSt();

                        String query = String.format("select * from NewCourse where course_name = '%s'",courseName);
                        ResultSet resultSet = st.executeQuery(query);
                        resultSet.next();

                        int course_ID = resultSet.getInt("id");
                        String Duration = resultSet.getString("duration");

                        String query1 = String.format("Insert into Enrollment(stu_id,stu_name,course_id,course_name,duration,fee_paid)"+
                                " values ('%s','%s',%d,'%s','%s',%d)",id,fullName,course_ID,courseName,Duration,paid_Fee);
                        st.executeUpdate(query1);
                        DatabaseConfig.closeConnection();

                        refreshRecentEnrollTable(recentEnrollTableModel);

                        setVisible(false);
                        MngMainFrame.backPanel.setBounds(15,143,210,45);
                        dashboardPanel.refreshCounts();
                        dashboardPanel.setVisible(true);
                    }
                    catch (SQLIntegrityConstraintViolationException exception){
                        JOptionPane.showMessageDialog(null,
                                "Duplicate Enrollment!\n" + fullName + " is already enrolled in " + courseName + ".",
                                "Enrollment Failed",
                                JOptionPane.WARNING_MESSAGE);
                        courseSelectedAns.setText("00");
                        totalFeeAns.setText("0.00 Rs");
                        Course_Name.setText("• None Selected");
                        refreshEnrollTable(enrollTableModel);
                        selectStudentField.setSelectedIndex(0);
                    }
                    catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        RoundedPanel enrollRightPanel = new RoundedPanel(20);
        enrollRightPanel.setBounds(670,120,340,480);
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

        setBounds(250,70,1030,650);
        setLayout(null);
        setBackground(new Color(240, 239, 255));
        add(enrollStudentUpperPanel);
        add(enrollLeftPanel);
        add(enrollRightPanel);
        setVisible(false);
    }
}