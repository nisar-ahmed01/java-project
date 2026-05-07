package course;

import components.*;
import dashboard.MngMainFrame;
import database.DatabaseConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import static course.CoursePanel.courseTableModel;
import static dashboard.MngMainFrame.coursePanel;

public class AddCoursePanel extends JPanel{

    MngMainFrame frame;
//    static String[] courseColumnNames = {"ID","COURSE NAME","COURSE CODE","DURATION","COURSE FEES"};
//    public static DefaultTableModel courseTableModel = new DefaultTableModel(null,courseColumnNames);
//    public static JTable courseTable = new JTable(courseTableModel);

    public AddCoursePanel(MngMainFrame frame){
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

    void initialize(){
        JLabel addNewCourse = new JLabel("Add New Course");
        addNewCourse.setFont(new Font("Century Gothic",Font.BOLD,33));
        addNewCourse.setForeground(new Color(33,37,40));
        addNewCourse.setBounds(20,20,300,40);

        //============ Back to List ================
        AnimatedButton backToListCourse = new AnimatedButton(" Back to List",20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        backToListCourse.setBounds(860,18,150,40);
        backToListCourse.setForeground(Color.WHITE);
        backToListCourse.setFont(new Font("Montserrat",Font.PLAIN,14));
        backToListCourse.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        backToListCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backToListCourse.setBounds(860,18,150,40);
                backToListCourse.setBounds(860,17,150,40);
                backToListCourse.setBounds(860,16,150,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backToListCourse.setBounds(860,16,150,40);
                backToListCourse.setBounds(860,17,150,40);
                backToListCourse.setBounds(860,18,150,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                coursePanel.setVisible(true);
            }
        });


        JLabel courseDetails = new JLabel("Course Details");
        courseDetails.setBounds(40,17,150,30);
        courseDetails.setFont(new Font("Montserrat",Font.BOLD,14));
        courseDetails.setForeground(Color.darkGray);

        JLabel addCourseLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        addCourseLine.setBounds(0,43,990,20);
        addCourseLine.setFont(new Font("Arial",Font.BOLD,14));
        addCourseLine.setForeground(new Color(240,240,240));

        JLabel courseName = new JLabel("Course Name");
        courseName.setForeground(new Color(120,120,120));
        courseName.setFont(new Font("Montserrat",Font.BOLD,14));
        courseName.setBounds(20, 72,150,30);

        RoundedTextField courseNameBox = new RoundedTextField("e.g. Web Development",10);
        courseNameBox.setBounds(20,104,465,40);
        courseNameBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        courseNameBox.setForeground(new Color(105,105,105));

        JLabel courseCode = new JLabel("Course Code");
        courseCode.setForeground(new Color(120,120,120));
        courseCode.setFont(new Font("Montserrat",Font.BOLD,14));
        courseCode.setBounds(505, 72,150,30);

        RoundedTextField courseCodeBox = new RoundedTextField("e.g. WEB101",10);
        courseCodeBox.setBounds(505,104,465,40);
        courseCodeBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        courseCodeBox.setForeground(new Color(105,105,105));

        JLabel courseDuration = new JLabel("Duration");
        courseDuration.setForeground(new Color(120,120,120));
        courseDuration.setFont(new Font("Montserrat",Font.BOLD,14));
        courseDuration.setBounds(20, 152,150,30);

        RoundedTextField courseDurationBox = new RoundedTextField("e.g. 6 Months",10);
        courseDurationBox.setBounds(20,184,465,40);
        courseDurationBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        courseDurationBox.setForeground(new Color(105,105,105));

        JLabel courseFee = new JLabel("Course Fee");
        courseFee.setForeground(new Color(120,120,120));
        courseFee.setFont(new Font("Montserrat",Font.BOLD,14));
        courseFee.setBounds(505, 152,150,30);

        RoundedTextField courseFeeBox = new RoundedTextField("e.g. $1200",10);
        courseFeeBox.setBounds(505,184,465,40);
        courseFeeBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        courseFeeBox.setForeground(new Color(105,105,105));

        JLabel courseDescription = new JLabel("Description");
        courseDescription.setForeground(new Color(120,120,120));
        courseDescription.setFont(new Font("Montserrat",Font.BOLD,14));
        courseDescription.setBounds(20, 232,150,30);

        RoundedTextField courseDescriptionBox = new RoundedTextField("Enter course description...",10);
        courseDescriptionBox.setBounds(20,264,950,100);
        courseDescriptionBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        courseDescriptionBox.setForeground(new Color(105,105,105));



        AnimatedButton resetCourseBtn = new AnimatedButton("Reset",40,
                new Color(240, 239, 255),    // normal Color (light off-white)
                new Color(220, 218, 235),    // Hovered Color (slightly darker)
                new Color(200, 198, 215),    // Pressed Color (even darker)
                new Color(240, 239, 255));
        resetCourseBtn.setBounds(710,380,120,40);
        resetCourseBtn.setForeground(Color.darkGray);
        resetCourseBtn.setFont(new Font("Montserrat",Font.BOLD,14));
        resetCourseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetCourseBtn.setBounds(710,380,120,40);
                resetCourseBtn.setBounds(710,379,120,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetCourseBtn.setBounds(710,379,120,40);
                resetCourseBtn.setBounds(710,380,120,40);
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



        AnimatedButton addBtn_Course = new AnimatedButton("Add",40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        addBtn_Course.setBounds(850,380,120,40);
        addBtn_Course.setForeground(Color.WHITE);
        addBtn_Course.setFont(new Font("Montserrat",Font.BOLD,14));
        addBtn_Course.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addBtn_Course.setBounds(850,380,120,40);
                addBtn_Course.setBounds(850,379,120,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addBtn_Course.setBounds(850,379,120,40);
                addBtn_Course.setBounds(850,380,120,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String course_Name = courseNameBox.getText().trim();
                String course_Code = courseCodeBox.getText().trim();
                String course_Duration = courseDurationBox.getText().trim();
                String course_Fee = courseFeeBox.getText().trim();
                String Description = courseDescriptionBox.getText().trim();

                if(course_Name.isEmpty() || course_Code.isEmpty() || course_Duration.isEmpty() || course_Fee.isEmpty() || Description.isEmpty()){
                    JOptionPane.showMessageDialog(null,"All fields are required!","Validation Error",JOptionPane.WARNING_MESSAGE);
                }
                else if (!course_Duration.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Duration must be numeric!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    courseDurationBox.requestFocus();
                }
                else if (!course_Fee.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Fee must be numeric!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    courseFeeBox.requestFocus();
                }
                else if (Integer.parseInt(course_Duration) <= 0 || Integer.parseInt(course_Duration) > 60) {
                    JOptionPane.showMessageDialog(null, "Duration must be between 1 and 60 months!","Validation Error", JOptionPane.WARNING_MESSAGE);
                    courseDurationBox.requestFocus();
                }
                else if (Integer.parseInt(course_Fee) <= 0 || Integer.parseInt(course_Fee) > 500000) {
                    JOptionPane.showMessageDialog(null, "Fee is out of valid range!","Validation Error", JOptionPane.WARNING_MESSAGE);
                    courseFeeBox.requestFocus();
                }
                else{
                    try {
                        Connection con = DriverManager.getConnection(DatabaseConfig.URL,"root","Nisar123");
                        Statement st = con.createStatement();
                        String query = String.format("Insert into NewCourse(course_name,course_code,duration,course_fee,course_description) values ('%s','%s','%s',%d,'%s')"
                                ,course_Name,course_Code,course_Duration + " Months",Integer.parseInt(course_Fee),Description);
                        st.executeUpdate(query);
                        courseNameBox.setText("");
                        courseCodeBox.setText("");
                        courseDurationBox.setText("");
                        courseFeeBox.setText("");
                        courseDescriptionBox.setText("");
                        setVisible(false);
                        Statement statement = con.createStatement(
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY
                        );
                        String query2 = "select * from NewCourse";
                        ResultSet result = statement.executeQuery(query2);
                        if(result.last()){
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
        addCoursePanel.setBounds(20,68,990,436);
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


        setBounds(250,70,1030,650);
        setBackground(new Color(240, 239, 255));
        setLayout(null);
        add(addNewCourse);
        add(backToListCourse);
        add(addCoursePanel);
        setVisible(false);
    }
}