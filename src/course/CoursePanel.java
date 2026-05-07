package course;

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

import static course.EditCoursePanel.*;
import static course.ViewCoursePanel.*;
import static dashboard.MngMainFrame.*;
import static student.StudentPanel.filterTable;
import static student.StudentPanel.table;

public class CoursePanel extends JPanel{
    MngMainFrame frame;
    static String[] courseColumnNames = {"ID","COURSE NAME","COURSE CODE","DURATION","COURSE FEES"};
    public static DefaultTableModel courseTableModel = new DefaultTableModel(null,courseColumnNames);
    public static JTable courseTable = new JTable(courseTableModel);

    public CoursePanel(MngMainFrame frame){
        this.frame = frame;
    }


    public void createIt() {
        setBounds(250,70,1030,650);
        setBackground(new Color(240, 239, 255));
        setLayout(null);
        frame.addToMainFrame(this);
        initialize();
        setVisible(false);
    }
    public void visibility(boolean visible){
        this.setVisible(visible);
    }

    void refreshCourseTable(){
        try{
            DatabaseConfig.setConnection();
            Statement st = DatabaseConfig.getSt();
            String query1 = "select id,course_name,course_code,duration,course_fee from NewCourse";
            ResultSet result = st.executeQuery(query1);
            while(result.next()){
                int id = result.getInt("id");
                String course_name = result.getString("course_name");
                String course_code = result.getString("course_code");
                String duration = result.getString("duration");
                int course_fee = result.getInt("course_fee");
                Object[] newData = {id,course_name,course_code,duration,course_fee + " PKR"};
                courseTableModel.addRow(newData);
            }
        DatabaseConfig.closeConnection();
    } catch (Exception ex) {
        throw new RuntimeException(ex);
    }
    }

    public void initialize(){

        //=========== Course Text ==========
        JLabel Course = new JLabel("Courses");
        Course.setFont(new Font("Century Gothic",Font.BOLD,33));
        Course.setForeground(new Color(33,37,40));
        Course.setBounds(20,20,200,40);
        //=========== Course Text ==========

        //============ ADD Course ================
        JLabel plusCourseIcon = new JLabel("+");
        plusCourseIcon.setBounds(0,18,30,30);
        plusCourseIcon.setForeground(Color.WHITE);
        plusCourseIcon.setFont(new Font("Montserrat",Font.PLAIN,25));


        AnimatedButton addCourseBtn = new AnimatedButton("     Add New Course",20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        addCourseBtn.setBounds(830,18,180,40);
        addCourseBtn.setForeground(Color.WHITE);
        addCourseBtn.setFont(new Font("Montserrat",Font.PLAIN,14));
        addCourseBtn.add(plusCourseIcon);
        addCourseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addCourseBtn.setBounds(830,18,180,40);
                addCourseBtn.setBounds(830,17,180,40);
                addCourseBtn.setBounds(830,16,180,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addCourseBtn.setBounds(830,16,180,40);
                addCourseBtn.setBounds(830,17,180,40);
                addCourseBtn.setBounds(830,18,180,40);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                addCoursePanel.setVisible(true);
            }

        });

        ImageIcon searchCourseIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\search.png",26,26);
        JButton searchCourseBtn = new JButton();
        searchCourseBtn.setLayout(null);
        searchCourseBtn.setBounds(560,9,26,26);
        searchCourseBtn.setContentAreaFilled(false);
        searchCourseBtn.setFocusable(false);
        searchCourseBtn.setBorder(null);
        searchCourseBtn.setCursor(new Cursor(12));
        searchCourseBtn.setIcon(searchCourseIcon);



        RoundedTextField courseSearchBar = new RoundedTextField("Search",44
                ,new Color(240, 239, 255)
                ,new Color(134, 73, 255)
                ,new Color(134, 73, 255));
        courseSearchBar.setBounds(20,13,600,44);
        courseSearchBar.setFont(new Font("Montserrat",Font.PLAIN,14));
        courseSearchBar.setBackground(new Color(240, 239, 255));
        courseSearchBar.add(searchCourseBtn);
        courseSearchBar.setCaretColor(new Color(134, 73, 255));

        searchCourseBtn.addActionListener(e -> {
            String searchText = courseSearchBar.getText();
            filterTable(searchText,courseTable,courseTableModel);
        });



        AnimatedButton viewCourse = new AnimatedButton("👁️",44,
                new Color(148, 240, 151),   // Normal Color (light green)
                new Color(126, 220, 129),   // Hover Color
                new Color(104, 196, 107),   // Pressed Color
                new Color(84, 176, 87)    );  // Border Color
        viewCourse.setBounds(813,13,44,44);
        viewCourse.setFont(new Font("",Font.PLAIN,25));
        viewCourse.setMargin(new Insets(0,0,0,0));
        viewCourse.setFocusable(false);
        viewCourse.setForeground(new Color(84, 176, 87));
        viewCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewCourse.setBounds(813,13,44,44);
                viewCourse.setBounds(813,12,44,44);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewCourse.setBounds(813,12,44,44);
                viewCourse.setBounds(813,13,44,44);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(courseTable.getSelectedRow() >= 0){
                    setVisible(false);
                    int CourseId = Integer.parseInt(courseTableModel.getValueAt(courseTable.getSelectedRow(),0).toString());
                    try {
                        DatabaseConfig.setConnection();
                        Statement st = DatabaseConfig.getSt();
                        String query = String.format("select * from NewCourse where id = %d",CourseId);
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


        ImageIcon editCourseIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\edit.png",20,20);
        AnimatedButton editCourse = new AnimatedButton(null,44,
                new Color(251, 219, 179),   // Normal Color (light peach)
                new Color(235, 203, 163),   // Hover Color
                new Color(210, 178, 138),   // Pressed Color
                new Color(176, 144, 104));    // Border Color// Border Color (unchanged)
        editCourse.setBounds(869,13,44,44);
        editCourse.setFont(new Font("",Font.BOLD,19));
        editCourse.setMargin(new Insets(0,0,0,0));
        editCourse.setFocusable(false);
        editCourse.setLayout(new GridBagLayout());
        editCourse.setIcon(editCourseIcon);
        editCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editCourse.setBounds(869,13,44,44);
                editCourse.setBounds(869,12,44,44);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editCourse.setBounds(869,12,44,44);
                editCourse.setBounds(869,13,44,44);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(courseTable.getSelectedRow() >= 0) {
                    setVisible(false);
                    int C_id = Integer.parseInt(courseTableModel.getValueAt(courseTable.getSelectedRow(), 0).toString());
                    try {
                        DatabaseConfig.setConnection();
                        Statement st = DatabaseConfig.getSt();
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

                        DatabaseConfig.closeConnection();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    editCoursePanel.setVisible(true);
                }
            }
        });



        ImageIcon deleteCourseIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\delete.png",18,18);
        AnimatedButton delCourse = new AnimatedButton(null,44,
                new Color(165, 176, 254),   // Normal Color (light periwinkle blue)
                new Color(145, 156, 234),   // Hover Color
                new Color(130, 141, 219),   // Pressed Color
                new Color(115, 126, 204));    // Border Color
        delCourse.setBounds(924,13,44,44);
        delCourse.setFont(new Font("",Font.BOLD,20));
        delCourse.setMargin(new Insets(0,0,0,0));
        delCourse.setFocusable(false);
        delCourse.setLayout(new GridBagLayout());
        delCourse.setIcon(deleteCourseIcon);
        delCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                delCourse.setBounds(924,13,44,44);
                delCourse.setBounds(924,12,44,44);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                delCourse.setBounds(924,12,44,44);
                delCourse.setBounds(924,13,44,44);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = courseTable.getSelectedRow();
                if(selectedRow != -1){
                    try {
                        DatabaseConfig.setConnection();
                        Statement st = DatabaseConfig.getSt();
                        int course_id = Integer.parseInt(courseTableModel.getValueAt(selectedRow,0).toString());
                        String query = String.format("Delete from NewCourse where id = %d",course_id);
                        st.executeUpdate(query);
                        DatabaseConfig.closeConnection();
                    }
                    catch (SQLException ex) {
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
        buttonBelowCoursePanel.setBounds(20,68,990,70);
        buttonBelowCoursePanel.setLayout(null);
        buttonBelowCoursePanel.add(viewCourse);
        buttonBelowCoursePanel.add(editCourse);
        buttonBelowCoursePanel.add(delCourse);
        buttonBelowCoursePanel.add(courseSearchBar);



        //================= Search Button Ends Here ============

        refreshCourseTable();
        dashboardPanel.refreshCounts();

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
                }
                else if (row == hoverRow1[0]) {
                    setBackground(new Color(230, 230, 255,100)); // hover color
                    setFont(new Font("Montserrat", Font.BOLD, 13));
                }
                else {

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



        RoundScrollPane courseData = new RoundScrollPane(courseTable,30);
        courseData.setBackground(Color.WHITE);
        courseData.setBounds(0,40,990,400);
        courseData.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        courseData.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        courseData.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        courseData.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        //================== Student Data table Ends Here==================

        JLabel courseList = new JLabel("Course List");
        courseList.setBounds(40,17,100,30);
        courseList.setFont(new Font("Montserrat",Font.BOLD,14));
        courseList.setForeground(Color.darkGray);

        JLabel courseline = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        courseline.setBounds(0,43,990,20);
        courseline.setFont(new Font("Arial",Font.BOLD,14));
        courseline.setForeground(new Color(240,240,240));

        RoundedPanel courseTablePanel = new RoundedPanel(30);
        courseTablePanel.setBounds(20,150,990,440);
        courseTablePanel.setBackground(Color.WHITE);
        courseTablePanel.setLayout(null);
        courseTablePanel.add(courseList);
        courseTablePanel.add(courseline);
        courseTablePanel.add(courseData);
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                courseTablePanel.setBounds(20,150,990,440);
                courseTablePanel.setBounds(20,149,990,440);
                courseTablePanel.setBounds(20,148,990,440);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                courseTablePanel.setBounds(20,148,990,440);
                courseTablePanel.setBounds(20,149,990,440);
                courseTablePanel.setBounds(20,150,990,440);
            }
        });

        setBounds(250,70,1030,650);
        setBackground(new Color(240, 239, 255));
        setLayout(null);
        add(Course);
        add(addCourseBtn);
        add(buttonBelowCoursePanel);
        add(courseTablePanel);
        setVisible(false);
    }

}