package dashboard;

import attendance.AttendancePanel;
import components.*;
import course.*;
import enrollment.*;
import login.LoginFrame;
import student.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MngMainFrame extends JFrame {
    public static dashboard dashboardPanel;
    public static StudentPanel studentPanel;
    public static CoursePanel coursePanel;
    public static EnrollmentPanel enrollPanel;
    public static AttendancePanel attendancePanel;
    private static LoginFrame loginFrame;

    public static AddStudentPanel addStudentPanel;
    public static EditStudentPanel editStudentPanel;
    public static ViewStudentPanel viewStudentPanel;

    public static AddCoursePanel addCoursePanel;
    public static EditCoursePanel editCoursePanel;
    public static ViewCoursePanel viewCoursePanel;

    public static RoundedPanel backPanel;

    public MngMainFrame(LoginFrame loginFrame){
        this.loginFrame = loginFrame;
    }

    public void createIt(){
        setName("Student Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 239, 255));
        setLayout(null);
        setVisible(true);
        initialize();
    }
    public void initialize(){
        basicDesigns();
        sidePanelCreate();
        allPanelCreation();
    }
    public void visibility(boolean visible){
        this.setVisible(visible);
    }
    public void addToMainFrame(Object obj){
        add((Component) obj);
    }

    public void hideAllPanels(){
        if (dashboardPanel != null) dashboardPanel.visibility(false);
        if (studentPanel != null) studentPanel.visibility(false);
        if (coursePanel != null) coursePanel.visibility(false);
        if (enrollPanel != null) enrollPanel.visibility(false);
        if (attendancePanel != null) attendancePanel.visibility(false);

        // Student sub-panels
        if (addStudentPanel != null) addStudentPanel.setVisible(false);
        if (editStudentPanel != null) editStudentPanel.setVisible(false);
        if (viewStudentPanel != null) viewStudentPanel.setVisible(false);

        // Course sub-panels
        if (addCoursePanel != null) addCoursePanel.setVisible(false);
        if (editCoursePanel != null) editCoursePanel.setVisible(false);
        if (viewCoursePanel != null) viewCoursePanel.setVisible(false);

    }

    public void basicDesigns(){
        JButton settingBtn = new JButton("≡");
        settingBtn.setLayout(null);
        settingBtn.setFont(new Font(null,Font.BOLD,32));
        settingBtn.setForeground(new Color(134, 73, 255));
        settingBtn.setBounds(270,10,50,50);
        settingBtn.setBorder(null);
        settingBtn.setFocusable(false);
        settingBtn.setCursor(new Cursor(12));
        settingBtn.setContentAreaFilled(false);
        add(settingBtn);


        JLabel one = new JLabel("1");
        one.setFont(new Font("Montserrat",Font.BOLD,7));
        one.setForeground(Color.WHITE);
        one.setBounds(4,1,7,7);

        RoundedPanel notificationPanel = new RoundedPanel(10);
        notificationPanel.setBounds(347,23,10,10);
        notificationPanel.setBackground(new Color(246, 38, 129,170));
        notificationPanel.setLayout(null);
        notificationPanel.add(one);
        add(notificationPanel);


        JButton bellBtn = new JButton("\uD83D\uDD14");
        bellBtn.setLayout(null);
        bellBtn.setFont(new Font(null,Font.PLAIN,18));
        bellBtn.setForeground(new Color(134, 73, 255));
        bellBtn.setBounds(320,12,50,50);
        bellBtn.setBorder(null);
        bellBtn.setFocusable(false);
        bellBtn.setCursor(new Cursor(12));
        bellBtn.setContentAreaFilled(false);
        add(bellBtn);


        ImageIcon profileImg = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\profile.png",25,25);


        JLabel profileName = new JLabel("Nisar Ahmed");
        profileName.setBounds(1110,6,150,50);
        profileName.setFont(new Font("Montserrat",Font.PLAIN,14));
        add(profileName);

        JLabel viewProfile = new JLabel("View Profile");
        viewProfile.setBounds(1135,23,150,50);
        viewProfile.setFont(new Font("Montserrat",Font.PLAIN,11));
        add(viewProfile);


        RoundedPanel profilePanel = new RoundedPanel(45);
        profilePanel.setBackground(null);
        profilePanel.setBorder(new RoundedBorder(50,new Color(134, 73, 255)));
        profilePanel.setBounds(1215,15,45,45);
        profilePanel.setLayout(new GridBagLayout());


        JLabel profileImgLabel = new JLabel(profileImg);
        profileImgLabel.setPreferredSize(new Dimension(25, 25)); // lock size
        profileImgLabel.setOpaque(false);
        profilePanel.add(profileImgLabel);
        add(profilePanel);
    }




    public void sidePanelCreate(){

        backPanel = new RoundedPanel(20);
        JButton dashb = new JButton("  Dashboard");
        JButton student = new JButton("  Student");
        JButton course = new JButton("  Courses");
        JButton enroll = new JButton("  Enroll to Course");
        JButton attendance = new JButton("  Attendance");
        JButton report = new JButton("  Report");
        JButton logout = new JButton("  Logout");


        ImageIcon sidePanelLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\SMS.png",45,45);

        JLabel sms = new JLabel("SMS");
        sms.setFont(new Font("Montserrat",Font.BOLD,42));
        sms.setForeground(Color.WHITE);
        sms.setBounds(0,30,240,50);
        sms.setHorizontalAlignment(JLabel.CENTER);
        sms.setIcon(sidePanelLogo);

        JLabel ms = new JLabel("MANAGEMENT SYSTEM");
        ms.setFont(new Font("Montserrat",Font.PLAIN,12));
        ms.setForeground(Color.WHITE);
        ms.setBounds(0,65,240,50);
        ms.setHorizontalAlignment(JLabel.CENTER);

        JLabel line1 = new JLabel("_________________________________________");
        line1.setFont(new Font("Montserrat",Font.PLAIN,12));
        line1.setForeground(new Color(210,210,210));
        line1.setBounds(0,100,250,37);
        line1.setHorizontalAlignment(JLabel.CENTER);
        line1.setVerticalAlignment(JLabel.BOTTOM);


        backPanel.setBounds(15,143,210,45);
        backPanel.setBackground(new Color(104, 45, 236));
        backPanel.setLayout(null);

        RoundedPanel duplicateBackPanel = new RoundedPanel(20);
        duplicateBackPanel.setBounds(15,143,210,45);
        duplicateBackPanel.setBackground(new Color(104, 45, 236));
        duplicateBackPanel.setVisible(false);



        ImageIcon dashLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\dashboard.png",23,23);
        dashb.setFont(new Font("Montserrat",Font.PLAIN,15));
        dashb.setForeground(Color.WHITE);
        dashb.setBounds(35,140,175,50);
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
                duplicateBackPanel.setBounds(15,143,210,45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236,150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                hideAllPanels();
                dashboardPanel.refreshCounts();
                dashboardPanel.visibility(true);
                backPanel.setBounds(15,143,210,45);
            }
        });

        ImageIcon studentLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\stu.png",23,23);
        student.setFont(new Font("Montserrat",Font.PLAIN,15));
        student.setForeground(Color.WHITE);
        student.setBounds(35,190,175,50);
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
                duplicateBackPanel.setBounds(15,193,210,45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236,150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                hideAllPanels();
                studentPanel.visibility(true);
                backPanel.setBounds(15,193,210,45);
            }
        });


        ImageIcon courseLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\course.png",23,23);
        course.setFont(new Font("Montserrat",Font.PLAIN,15));
        course.setForeground(Color.WHITE);
        course.setBounds(35,240,175,50);
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
                duplicateBackPanel.setBounds(15,243,210,45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236,150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                hideAllPanels();
                coursePanel.visibility(true);
                backPanel.setBounds(15,243,210,45);
            }
        });


        ImageIcon enrollLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\enroll.png",23,23);
        enroll.setFont(new Font("Montserrat",Font.PLAIN,15));
        enroll.setForeground(Color.WHITE);
        enroll.setBounds(35,290,175,50);
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
                duplicateBackPanel.setBounds(15,293,210,45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236,150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                hideAllPanels();
                enrollPanel.visibility(true);
                backPanel.setBounds(15,293,210,45);
            }
        });



        ImageIcon attendanceLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\attendance.png",23,23);
        attendance.setFont(new Font("Montserrat",Font.PLAIN,15));
        attendance.setForeground(Color.WHITE);
        attendance.setBounds(35,340,175,50);
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
                duplicateBackPanel.setBounds(15,343,210,45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236,150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                hideAllPanels();
                attendancePanel.visibility(true);
                backPanel.setBounds(15,343,210,45);
            }
        });


        ImageIcon reportLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\report.png",23,23);
        report.setFont(new Font("Montserrat",Font.PLAIN,15));
        report.setForeground(Color.WHITE);
        report.setBounds(35,390,175,50);
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
                duplicateBackPanel.setBounds(15,393,210,45);
                duplicateBackPanel.setBackground(new Color(104, 45, 236,150));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                duplicateBackPanel.setVisible(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                hideAllPanels();
                backPanel.setBounds(15,393,210,45);
            }
        });


        JLabel line2 = new JLabel("_________________________________________");
        line2.setFont(new Font("Montserrat",Font.PLAIN,12));
        line2.setForeground(new Color(210,210,210));
        line2.setBounds(0,420,250,40);
        line2.setHorizontalAlignment(JLabel.CENTER);


        ImageIcon logoutLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\logout.png",23,23);
        logout.setFont(new Font("Montserrat",Font.PLAIN,15));
        logout.setForeground(Color.WHITE);
        logout.setBounds(42,600,175,40);
        logout.setHorizontalAlignment(JButton.LEFT);
        logout.setVerticalAlignment(JButton.CENTER);
        logout.setBorder(null);
        logout.setContentAreaFilled(false);
        logout.setFocusable(false);
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logout.setIcon(logoutLogo);
        logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon logoutLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\coloredLogout.png",23,23);
                logout.setBounds(42,600,175,40);
                logout.setBounds(42,599,175,40);
                logout.setBounds(42,598,175,40);
                logout.setForeground(new Color(84, 23, 205));
                logout.setIcon(logoutLogo);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ImageIcon logoutLogo = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\logout.png",23,23);
                logout.setBounds(42,598,175,40);
                logout.setBounds(42,599,175,40);
                logout.setBounds(42,600,175,40);
                logout.setForeground(new Color(240, 239, 255));
                logout.setIcon(logoutLogo);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                visibility(false);
                loginFrame.visibility(true);
            }
        });




        RoundedPanel sidePanel = new RoundedPanel(50);
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setBounds(10,10,240,678);
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
        add(sidePanel);
    }
    public void allPanelCreation(){
        dashboardPanel = new dashboard(this);
        dashboardPanel.createIt();
        studentPanel = new StudentPanel(this);
        studentPanel.createIt();
        coursePanel = new CoursePanel(this);
        coursePanel.createIt();
        enrollPanel = new EnrollmentPanel(this);
        enrollPanel.createIt();
        attendancePanel = new AttendancePanel(this);
        attendancePanel.createIt();
        addStudentPanel = new AddStudentPanel(this);
        addStudentPanel.createIt();
        editStudentPanel = new EditStudentPanel(this);
        editStudentPanel.createIt();
        viewStudentPanel = new ViewStudentPanel(this);
        viewStudentPanel.createIt();
        addCoursePanel = new AddCoursePanel(this);
        addCoursePanel.createIt();
        editCoursePanel = new EditCoursePanel(this);
        editCoursePanel.createIt();
        viewCoursePanel = new ViewCoursePanel(this);
        viewCoursePanel.createIt();
    }
}