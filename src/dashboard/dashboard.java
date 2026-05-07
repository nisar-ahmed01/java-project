package dashboard;

import components.*;
import course.CoursePanel;
import database.DatabaseConfig;
import student.StudentPanel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class dashboard extends JPanel {
    MngMainFrame frame;
    public static String[] recentEnrollColNames = {"ID","NAME","COURSE","ENROLL DATE","DURATION"};
    public static DefaultTableModel recentEnrollTableModel = new DefaultTableModel(null,recentEnrollColNames);
    public static JTable recentEnrolledTable;

    JLabel stuCount = new JLabel();
    JLabel courseCount = new JLabel();
    JLabel enrollCount = new JLabel();

    dashboard(MngMainFrame frame){
        this.frame = frame;
    }

    public void createIt(){
        setBounds(250,70,1030,650);
        setBackground(new Color(240, 239, 255));
        setLayout(null);
        frame.addToMainFrame(this);
        initialize();
        setVisible(true);
    }

    public void initialize(){
        dashboardTable();
        refreshCounts();
        uiCards();
    }

    public void visibility(boolean visible){
        this.setVisible(visible);
    }


    public void refreshCounts(){
        if (recentEnrolledTable != null) {
            if(recentEnrolledTable.getRowCount()<10) {
                enrollCount.setText("0" + recentEnrolledTable.getRowCount());
            }
            else
                enrollCount.setText(Integer.toString(recentEnrolledTable.getRowCount()));
        } else {
            enrollCount.setText("00");
        }



        if (StudentPanel.table != null) {
            if(StudentPanel.table.getRowCount()<10)
                stuCount.setText("0" + StudentPanel.table.getRowCount());
            else
                stuCount.setText(Integer.toString(StudentPanel.table.getRowCount()));
        }
        else {
            stuCount.setText("00");
        }



        if (CoursePanel.courseTable != null) {
            if(CoursePanel.courseTable.getRowCount()<10)
                courseCount.setText("0" + CoursePanel.courseTable.getRowCount());
            else
                courseCount.setText(Integer.toString(CoursePanel.courseTable.getRowCount()));
        }
        else {
            courseCount.setText("00");
        }
    }


    public void uiCards(){
        JLabel Dashboard = new JLabel("Dashboard");
        Dashboard.setFont(new Font("Century Gothic",Font.BOLD,33));
        Dashboard.setForeground(new Color(33,37,40));
        Dashboard.setBounds(20,0,200,40);
        add(Dashboard);



        ImageIcon image = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\totalStudent.png",30,30);

        RoundedPanel insideP1 = new RoundedPanel(60);
        insideP1.setLayout(new GridBagLayout()); // centers the icon perfectly
        insideP1.setBounds(50, 20, 60, 60);
        insideP1.setBackground(new Color(134, 73, 255));

        JLabel iconLabel = new JLabel(image);
        iconLabel.setPreferredSize(new Dimension(30, 30)); // lock size
        iconLabel.setOpaque(false);
        insideP1.add(iconLabel);


        JLabel totalStudents = new JLabel("Total Students");
        totalStudents.setFont(new Font("Montserrat",Font.PLAIN,13));
        totalStudents.setBounds(5,90,150,30);
        totalStudents.setHorizontalAlignment(JLabel.CENTER);
        totalStudents.setForeground(Color.darkGray);

        stuCount.setForeground(Color.darkGray);
        stuCount.setFont(new Font("Montserrat",Font.PLAIN,45));
        stuCount.setBorder(null);
        stuCount.setBackground(null);
        stuCount.setHorizontalAlignment(JLabel.CENTER);
        stuCount.setBounds(30,105,100,80);


        RoundedPanel p1 = new RoundedPanel(30);
        p1.setBounds(20,55,160,190);
        p1.setBackground(Color.WHITE);
        p1.setLayout(null);
        p1.add(totalStudents);
        p1.add(stuCount);
        p1.add(insideP1);
        p1.setBorder(new RoundedBorder(30,Color.lightGray));
        p1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p1.setBounds(20,55,160,190);
                p1.setBorder(new RoundedBorder(30,new Color(134, 73, 255)));
                p1.setBounds(20,54,160,190);
                p1.setBounds(20,53,160,190);
                stuCount.setForeground(new Color(134, 73, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p1.setBounds(20,53,160,190);
                p1.setBorder(new RoundedBorder(30,Color.lightGray));
                p1.setBounds(20,54,160,190);
                p1.setBounds(20,55,160,190);
                stuCount.setForeground(Color.darkGray);
            }
        });
        add(p1);

        ImageIcon img2 = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\totalCourse.png",35,35);

        RoundedPanel insideP2 = new RoundedPanel(60);
        insideP2.setBounds(50,20,60,60);
        insideP2.setBackground(new Color(251, 219, 179));
        insideP2.setLayout(new GridBagLayout());

        JLabel img2Label = new JLabel(img2);
        img2Label.setPreferredSize(new Dimension(35, 35)); // lock size
        img2Label.setOpaque(false);
        insideP2.add(img2Label);


        JLabel totalCourses = new JLabel("Total Courses");
        totalCourses.setFont(new Font("Montserrat",Font.PLAIN,13));
        totalCourses.setBounds(5,90,150,30);
        totalCourses.setForeground(Color.darkGray);
        totalCourses.setHorizontalAlignment(JLabel.CENTER);

        courseCount.setForeground(Color.darkGray);
        courseCount.setFont(new Font("Montserrat",Font.PLAIN,45));
        courseCount.setBorder(null);
        courseCount.setBackground(null);
        courseCount.setBounds(30,105,100,80);
        courseCount.setHorizontalAlignment(JLabel.CENTER);


        RoundedPanel p2 = new RoundedPanel(30);
        p2.setBounds(200,55,160,190);
        p2.setBackground(Color.WHITE);
        p2.setLayout(null);
        p2.setBorder(new RoundedBorder(30,Color.lightGray));
        p2.add(totalCourses);
        p2.add(courseCount);
        p2.add(insideP2);
        p2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p2.setBounds(200,55,160,190);
                p2.setBorder(new RoundedBorder(30,new Color(176, 144, 104)));
                p2.setBounds(200,54,160,190);
                p2.setBounds(200,53,160,190);
                courseCount.setForeground(new Color(176, 144, 104));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p2.setBounds(200,53,160,190);
                p2.setBorder(new RoundedBorder(30,Color.lightGray));
                p2.setBounds(200,54,160,190);
                p2.setBounds(200,55,160,190);
                courseCount.setForeground(Color.darkGray);
            }
        });
        add(p2);

        ImageIcon img3 = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\attendanceRate.png",30,30);


        RoundedPanel insideP3 = new RoundedPanel(60);
        insideP3.setBounds(50,20,60,60);
        insideP3.setBackground(new Color(148, 240, 151));//104, 196, 107
        insideP3.setLayout(new GridBagLayout());

        JLabel img3Label = new JLabel(img3);
        img3Label.setPreferredSize(new Dimension(30, 30));
        img3Label.setOpaque(false);
        insideP3.add(img3Label);

        JLabel attendanceRate = new JLabel("Attendance Rate");
        attendanceRate.setFont(new Font("Montserrat",Font.PLAIN,13));
        attendanceRate.setBounds(5,90,150,30);
        attendanceRate.setForeground(Color.darkGray);
        attendanceRate.setHorizontalAlignment(JLabel.CENTER);

        JLabel attendanceCount = new JLabel("03");
        attendanceCount.setForeground(Color.darkGray);
        attendanceCount.setFont(new Font("Montserrat",Font.PLAIN,45));
        attendanceCount.setBorder(null);
        attendanceCount.setBackground(null);
        attendanceCount.setHorizontalAlignment(JLabel.CENTER);
        attendanceCount.setBounds(30,105,100,80);


        RoundedPanel p3 = new RoundedPanel(30);
        p3.setBounds(380,55,160,190);
        p3.setBackground(Color.WHITE);
        p3.setLayout(null);
        p3.setBorder(new RoundedBorder(30,Color.lightGray));
        p3.add(attendanceRate);
        p3.add(insideP3);
        p3.add(attendanceCount);
        p3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p3.setBounds(380,55,160,190);
                p3.setBorder(new RoundedBorder(30,new Color(104, 196, 107)));
                p3.setBounds(380,54,160,190);
                p3.setBounds(380,53,160,190);
                attendanceCount.setForeground(new Color(104, 196, 107));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p3.setBounds(380,53,160,190);
                p3.setBorder(new RoundedBorder(30,Color.lightGray));
                p3.setBounds(380,54,160,190);
                p3.setBounds(380,55,160,190);
                attendanceCount.setForeground(Color.darkGray);
            }
        });
        add(p3);

        ImageIcon img4 = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\totalEnroll.png",30,30);


        RoundedPanel insideP4 = new RoundedPanel(60);
        insideP4.setBounds(50,20,60,60);
        insideP4.setBackground(new Color(165, 176, 254));
        insideP4.setLayout(new GridBagLayout());

        JLabel img4Label = new JLabel(img4);
        img4Label.setPreferredSize(new Dimension(30, 30)); // lock size
        img4Label.setOpaque(false);
        insideP4.add(img4Label);

        JLabel SETM = new JLabel("Student Enrolled");
        SETM.setFont(new Font("Montserrat",Font.PLAIN,13));
        SETM.setBounds(5,90,150,30);
        SETM.setForeground(Color.darkGray);
        SETM.setHorizontalAlignment(JLabel.CENTER);

        enrollCount.setForeground(Color.darkGray);
        enrollCount.setFont(new Font("Montserrat",Font.PLAIN,45));
        enrollCount.setBorder(null);
        enrollCount.setBackground(null);
        enrollCount.setHorizontalAlignment(JLabel.CENTER);
        enrollCount.setBounds(30,105,100,80);


        RoundedPanel p4 = new RoundedPanel(30);
        p4.setBounds(560,55,160,190);
        p4.setBackground(Color.WHITE);
        p4.setLayout(null);
        p4.setBorder(new RoundedBorder(30,Color.lightGray));
        p4.add(SETM);
        p4.add(insideP4);
        p4.add(enrollCount);
        p4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p4.setBounds(560,55,160,190);
                p4.setBorder(new RoundedBorder(30,new Color(115, 126, 204)));
                p4.setBounds(560,54,160,190);
                p4.setBounds(560,53,160,190);
                enrollCount.setForeground(new Color(115, 126, 204));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p4.setBounds(560,53,160,190);
                p4.setBorder(new RoundedBorder(30,Color.lightGray));
                p4.setBounds(560,54,160,190);
                p4.setBounds(560,55,160,190);
                enrollCount.setForeground(Color.darkGray);
            }
        });
        add(p4);


        RoundedPanel b1 = new RoundedPanel(10);
        b1.setBounds(30,90,8,60);
        b1.setBackground(new Color(255, 161, 49));


        RoundedPanel b2 = new RoundedPanel(10);
        b2.setBounds(44,40,8,110);
        b2.setBackground(new Color(107, 184, 255));

        JLabel b1Text = new JLabel("Mon");
        b1Text.setFont(new Font("Montserrat",Font.PLAIN,12));
        b1Text.setBounds(15,155,50,20);
        b1Text.setForeground(Color.WHITE);
        b1Text.setHorizontalAlignment(JLabel.CENTER);

        RoundedPanel b3 = new RoundedPanel(10);
        b3.setBounds(77,80,8,70);
        b3.setBackground(new Color(255, 161, 49));

        RoundedPanel b4 = new RoundedPanel(10);
        b4.setBounds(91,50,8,100);
        b4.setBackground(new Color(107, 184, 255));

        JLabel b2Text = new JLabel("Tue");
        b2Text.setFont(new Font("Montserrat",Font.PLAIN,12));
        b2Text.setBounds(63,155,50,20);
        b2Text.setForeground(Color.WHITE);
        b2Text.setHorizontalAlignment(JLabel.CENTER);

        RoundedPanel b5 = new RoundedPanel(10);
        b5.setBounds(124,60,8,90);
        b5.setBackground(new Color(255, 161, 49));

        RoundedPanel b6 = new RoundedPanel(10);
        b6.setBounds(138,20,8,130);
        b6.setBackground(new Color(107, 184, 255));

        JLabel b3Text = new JLabel("Wed");
        b3Text.setFont(new Font("Montserrat",Font.PLAIN,12));
        b3Text.setBounds(110,155,50,20);
        b3Text.setForeground(Color.WHITE);
        b3Text.setHorizontalAlignment(JLabel.CENTER);

        RoundedPanel b7 = new RoundedPanel(10);
        b7.setBounds(171,100,8,50);
        b7.setBackground(new Color(255, 161, 49));

        RoundedPanel b8 = new RoundedPanel(10);
        b8.setBounds(185,40,8,110);
        b8.setBackground(new Color(107, 184, 255));

        JLabel b4Text = new JLabel("Thu");
        b4Text.setFont(new Font("Montserrat",Font.PLAIN,12));
        b4Text.setBounds(158,155,50,20);
        b4Text.setForeground(Color.WHITE);
        b4Text.setHorizontalAlignment(JLabel.CENTER);

        RoundedPanel b9 = new RoundedPanel(10);
        b9.setBounds(218,85,8,65);
        b9.setBackground(new Color(255, 161, 49));

        RoundedPanel b10 = new RoundedPanel(10);
        b10.setBounds(232,50,8,100);
        b10.setBackground(new Color(107, 184, 255));

        JLabel b5Text = new JLabel("Fri");
        b5Text.setFont(new Font("Montserrat",Font.PLAIN,12));
        b5Text.setBounds(203,155,50,20);
        b5Text.setForeground(Color.WHITE);
        b5Text.setHorizontalAlignment(JLabel.CENTER);


        RoundedPanel p5 = new RoundedPanel(40);
        p5.setBounds(740,55,270,190);
        p5.setBackground(new Color(134, 73, 255));
        p5.setLayout(null);
        p5.setBorder(new RoundedBorder(40,new Color(255, 161, 49)));
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
                p5.setBounds(740,55,270,190);
                p5.setBounds(740,54,270,190);
                p5.setBounds(740,53,270,190);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p5.setBounds(740,53,270,190);
                p5.setBounds(740,54,270,190);
                p5.setBounds(740,55,270,190);
            }
        });
        add(p5);
    }


    public static void refreshRecentEnrollTable(DefaultTableModel recentEnrollTableModel){
        recentEnrollTableModel.setRowCount(0);
        try {
            DatabaseConfig.setConnection();
            Statement st = DatabaseConfig.getSt();

            String query = "Select * from Enrollment";
            ResultSet result = st.executeQuery(query);

            while(result.next()){
                Object[] newData = {result.getString("stu_id"),
                        result.getString("stu_name"),
                        result.getString("course_name"),
                        result.getString("enrollment_date"),
                        result.getString("duration")};
                recentEnrollTableModel.addRow(newData);
            }
            DatabaseConfig.closeConnection();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }




    public void dashboardTable(){

        JLabel recentRegText = new JLabel("Recent Student Registration");
        recentRegText.setFont(new Font("Montserrat",Font.BOLD,13));
        recentRegText.setBounds(30,15,200,30);
        recentRegText.setForeground(Color.DARK_GRAY);

        JLabel recentEnrollLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        recentEnrollLine.setBounds(0,40,990,20);
        recentEnrollLine.setFont(new Font("Arial",Font.BOLD,14));
        recentEnrollLine.setForeground(new Color(240,240,240));


        recentEnrolledTable = new JTable(recentEnrollTableModel);
        refreshRecentEnrollTable(recentEnrollTableModel);
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
                }
                else if (row == hoverRow3[0]) {
                    setBackground(new Color(230, 230, 255,100)); // hover color
                    setFont(new Font("Montserrat", Font.BOLD, 12));
                }

                else {
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
                                                new Color(235,235,235)),
                                        BorderFactory.createEmptyBorder(
                                                15, 15, 15, 15)
                                )
                        );

                        return label;
                    }
                });




        RoundScrollPane stuEnrollTableBg = new RoundScrollPane(recentEnrolledTable,30);
        stuEnrollTableBg.setBackground(Color.WHITE);
        stuEnrollTableBg.setBounds(20,65,950,253);
        stuEnrollTableBg.setBorder(BorderFactory.createEmptyBorder());
        stuEnrollTableBg.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        stuEnrollTableBg.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        stuEnrollTableBg.getVerticalScrollBar().setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        stuEnrollTableBg.getVerticalScrollBar().setUI(new ModernScrollBarUI(new Color(134, 73, 255,150),new Color(240,240,240)));



        RoundedPanel recentStuBgPanel = new RoundedPanel(30);
        recentStuBgPanel.setBackground(Color.white);
        recentStuBgPanel.setBounds(20,265,990,350);
        recentStuBgPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        recentStuBgPanel.setLayout(null);
        recentStuBgPanel.add(recentRegText);
        recentStuBgPanel.add(recentEnrollLine);
        recentStuBgPanel.add(stuEnrollTableBg);
        add(recentStuBgPanel);
    }
}
