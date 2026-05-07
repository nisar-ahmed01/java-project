package course;

import components.*;
import dashboard.MngMainFrame;
import database.DatabaseConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import static course.CoursePanel.courseTable;
import static course.CoursePanel.courseTableModel;
import static course.EditCoursePanel.*;
import static dashboard.MngMainFrame.coursePanel;
import static dashboard.MngMainFrame.editCoursePanel;

public class ViewCoursePanel extends JPanel{
    MngMainFrame frame;
    public static JLabel id_course_ans;
    public static JLabel C_Name_ans;
    public static JLabel C_Code_ans;
    public static JLabel C_Duration_ans;
    public static JLabel C_fee_ans;
    public static JLabel C_Description_ans;

    public ViewCoursePanel(MngMainFrame frame) {
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
    void initialize(){
        JLabel viewCourseText = new JLabel("Course Details");
        viewCourseText.setBounds(20,20,300,40);
        viewCourseText.setForeground(new Color(33,37,40));
        viewCourseText.setFont(new Font("Century Gothic",Font.BOLD,33));

        AnimatedButton viewCourseBackToList = new AnimatedButton(" Back to List",20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        viewCourseBackToList.setBounds(860,18,150,40);
        viewCourseBackToList.setForeground(Color.WHITE);
        viewCourseBackToList.setFont(new Font("Montserrat",Font.PLAIN,14));
        viewCourseBackToList.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        viewCourseBackToList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewCourseBackToList.setBounds(860,18,150,40);
                viewCourseBackToList.setBounds(860,17,150,40);
                viewCourseBackToList.setBounds(860,16,150,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewCourseBackToList.setBounds(860,16,150,40);
                viewCourseBackToList.setBounds(860,17,150,40);
                viewCourseBackToList.setBounds(860,18,150,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                coursePanel.setVisible(true);
            }
        });


        JLabel viewCourseInfoText = new JLabel("Information");
        viewCourseInfoText.setBounds(40,17,200,30);
        viewCourseInfoText.setFont(new Font("Montserrat",Font.BOLD,14));
        viewCourseInfoText.setForeground(Color.darkGray);

        JLabel viewCourseLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        viewCourseLine.setBounds(0,43,990,20);
        viewCourseLine.setFont(new Font("Arial",Font.BOLD,14));
        viewCourseLine.setForeground(new Color(240,240,240));


        JLabel id_course = new JLabel("Course ID:");
        id_course.setFont(new Font("Montserrat",Font.BOLD,14));
        id_course.setForeground(Color.darkGray);
        id_course.setBorder(null);
        id_course.setBackground(null);
        id_course.setVerticalAlignment(JLabel.CENTER);
        id_course.setBounds(40,90,150,30);

        id_course_ans = new JLabel();
        id_course_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        id_course_ans.setForeground(Color.darkGray);
        id_course_ans.setBorder(null);
        id_course_ans.setBackground(null);
        id_course_ans.setVerticalAlignment(JLabel.CENTER);
        id_course_ans.setBounds(290,90,200,30);

        JLabel C_Name = new JLabel("Course Name:");
        C_Name.setFont(new Font("Montserrat",Font.BOLD,14));
        C_Name.setForeground(Color.darkGray);
        C_Name.setBorder(null);
        C_Name.setBackground(null);
        C_Name.setVerticalAlignment(JLabel.CENTER);
        C_Name.setBounds(40,132,150,30);

        C_Name_ans = new JLabel();
        C_Name_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        C_Name_ans.setForeground(Color.darkGray);
        C_Name_ans.setBorder(null);
        C_Name_ans.setBackground(null);
        C_Name_ans.setVerticalAlignment(JLabel.CENTER);
        C_Name_ans.setBounds(290,132,300,30);

        JLabel C_Code = new JLabel("Course Code:");
        C_Code.setFont(new Font("Montserrat",Font.BOLD,14));
        C_Code.setForeground(Color.darkGray);
        C_Code.setBorder(null);
        C_Code.setBackground(null);
        C_Code.setVerticalAlignment(JLabel.CENTER);
        C_Code.setBounds(40,174,150,30);

        C_Code_ans = new JLabel();
        C_Code_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        C_Code_ans.setForeground(Color.darkGray);
        C_Code_ans.setBorder(null);
        C_Code_ans.setBackground(null);
        C_Code_ans.setVerticalAlignment(JLabel.CENTER);
        C_Code_ans.setBounds(290,174,400,30);

        JLabel C_Duration = new JLabel("Duration:");
        C_Duration.setFont(new Font("Montserrat",Font.BOLD,14));
        C_Duration.setForeground(Color.darkGray);
        C_Duration.setBorder(null);
        C_Duration.setBackground(null);
        C_Duration.setVerticalAlignment(JLabel.CENTER);
        C_Duration.setBounds(40,216,150,30);

        C_Duration_ans = new JLabel();
        C_Duration_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        C_Duration_ans.setForeground(Color.darkGray);
        C_Duration_ans.setBorder(null);
        C_Duration_ans.setBackground(null);
        C_Duration_ans.setVerticalAlignment(JLabel.CENTER);
        C_Duration_ans.setBounds(290,216,300,30);

        JLabel C_fee = new JLabel("Course Fee:");
        C_fee.setFont(new Font("Montserrat",Font.BOLD,14));
        C_fee.setForeground(Color.darkGray);
        C_fee.setBorder(null);
        C_fee.setBackground(null);
        C_fee.setVerticalAlignment(JLabel.CENTER);
        C_fee.setBounds(40,258,150,30);

        C_fee_ans = new JLabel();
        C_fee_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        C_fee_ans.setForeground(Color.darkGray);
        C_fee_ans.setBorder(null);
        C_fee_ans.setBackground(null);
        C_fee_ans.setVerticalAlignment(JLabel.CENTER);
        C_fee_ans.setBounds(290,258,150,30);


        JLabel courseIsActive = new JLabel("Is Active:");
        courseIsActive.setFont(new Font("Montserrat",Font.BOLD,14));
        courseIsActive.setForeground(Color.darkGray);
        courseIsActive.setBorder(null);
        courseIsActive.setBackground(null);
        courseIsActive.setVerticalAlignment(JLabel.CENTER);
        courseIsActive.setBounds(40,300,150,30);

        JLabel courseIsActive_ans = new JLabel("Yes");
        courseIsActive_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        courseIsActive_ans.setForeground(Color.darkGray);
        courseIsActive_ans.setBorder(null);
        courseIsActive_ans.setBackground(null);
        courseIsActive_ans.setVerticalAlignment(JLabel.CENTER);
        courseIsActive_ans.setBounds(290,300,100,30);


        JLabel C_Description = new JLabel("Description:");
        C_Description.setFont(new Font("Montserrat",Font.BOLD,14));
        C_Description.setForeground(Color.darkGray);
        C_Description.setBorder(null);
        C_Description.setBackground(null);
        C_Description.setVerticalAlignment(JLabel.CENTER);
        C_Description.setBounds(40,342,150,30);

        C_Description_ans = new JLabel();
        C_Description_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        C_Description_ans.setForeground(Color.darkGray);
        C_Description_ans.setBorder(null);
        C_Description_ans.setBackground(null);
        C_Description_ans.setVerticalAlignment(JLabel.CENTER);
        C_Description_ans.setBounds(290,342,700,30);



        JLabel viewCourseDownLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        viewCourseDownLine.setBounds(0,387,990,20);
        viewCourseDownLine.setFont(new Font("Arial",Font.BOLD,14));
        viewCourseDownLine.setForeground(new Color(240,240,240));


        AnimatedButton editCourseBtn= new AnimatedButton(("Edit Course"),40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        editCourseBtn.setBounds(40,418,145,40);
        editCourseBtn.setForeground(Color.WHITE);
        editCourseBtn.setFont(new Font("Montserrat",Font.BOLD,14));
        editCourseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editCourseBtn.setBounds(40,418,145,40);
                editCourseBtn.setBounds(40,417,145,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editCourseBtn.setBounds(40,417,145,40);
                editCourseBtn.setBounds(40,418,145,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
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
        });


        RoundedPanel viewCourseDetailsPanel = new RoundedPanel(30);
        viewCourseDetailsPanel.setBounds(20,68,990,473);
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


        setBounds(250,70,1030,650);
        setBackground(new Color(240, 239, 255));
        setLayout(null);
        add(viewCourseText);
        add(viewCourseBackToList);
        add(viewCourseDetailsPanel);
        setVisible(false);
    }
}