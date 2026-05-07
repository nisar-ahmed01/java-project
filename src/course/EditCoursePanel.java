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
import static dashboard.MngMainFrame.coursePanel;

public class EditCoursePanel extends JPanel{

    MngMainFrame frame;

    public static RoundedTextField editCourseNameBox;
    public static RoundedTextField editCourseCodeBox;
    public static RoundedTextField editCourseDurationBox;
    public static RoundedTextField editCourseFeeBox;
    public static RoundedTextField editCourseDescriptionBox;

    public EditCoursePanel(MngMainFrame frame) {
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
        setVisible(visible);
    }


    void initialize(){
        JLabel editCourseText = new JLabel("Edit Course");
        editCourseText.setBounds(20,20,300,40);
        editCourseText.setForeground(new Color(33,37,40));
        editCourseText.setFont(new Font("Century Gothic",Font.BOLD,33));

        AnimatedButton editCourseBackToList = new AnimatedButton(" Back to List",20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        editCourseBackToList.setBounds(860,18,150,40);
        editCourseBackToList.setForeground(Color.WHITE);
        editCourseBackToList.setFont(new Font("Montserrat",Font.PLAIN,14));
        editCourseBackToList.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        editCourseBackToList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editCourseBackToList.setBounds(860,18,150,40);
                editCourseBackToList.setBounds(860,17,150,40);
                editCourseBackToList.setBounds(860,16,150,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editCourseBackToList.setBounds(860,16,150,40);
                editCourseBackToList.setBounds(860,17,150,40);
                editCourseBackToList.setBounds(860,18,150,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                coursePanel.setVisible(true);
            }
        });


        JLabel editCourseDetails = new JLabel("Edit Course Details");
        editCourseDetails.setBounds(40,17,150,30);
        editCourseDetails.setFont(new Font("Montserrat",Font.BOLD,14));
        editCourseDetails.setForeground(Color.darkGray);

        JLabel editCourseLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        editCourseLine.setBounds(0,43,990,20);
        editCourseLine.setFont(new Font("Arial",Font.BOLD,14));
        editCourseLine.setForeground(new Color(240,240,240));

        JLabel editCourseName = new JLabel("Course Name");
        editCourseName.setForeground(new Color(120,120,120));
        editCourseName.setFont(new Font("Montserrat",Font.BOLD,14));
        editCourseName.setBounds(20, 72,150,30);

        editCourseNameBox = new RoundedTextField("e.g. Web Development",10);
        editCourseNameBox.setBounds(20,104,465,40);
        editCourseNameBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        editCourseNameBox.setForeground(new Color(105,105,105));

        JLabel editCourseCode = new JLabel("Course Code");
        editCourseCode.setForeground(new Color(120,120,120));
        editCourseCode.setFont(new Font("Montserrat",Font.BOLD,14));
        editCourseCode.setBounds(505, 72,150,30);

        editCourseCodeBox = new RoundedTextField("e.g. WEB101",10);
        editCourseCodeBox.setBounds(505,104,465,40);
        editCourseCodeBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        editCourseCodeBox.setForeground(new Color(105,105,105));

        JLabel editCourseDuration = new JLabel("Duration");
        editCourseDuration.setForeground(new Color(120,120,120));
        editCourseDuration.setFont(new Font("Montserrat",Font.BOLD,14));
        editCourseDuration.setBounds(20, 152,150,30);

        editCourseDurationBox = new RoundedTextField("e.g. 6 Months",10);
        editCourseDurationBox.setBounds(20,184,465,40);
        editCourseDurationBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        editCourseDurationBox.setForeground(new Color(105,105,105));

        JLabel editCourseFee = new JLabel("Course Fee");
        editCourseFee.setForeground(new Color(120,120,120));
        editCourseFee.setFont(new Font("Montserrat",Font.BOLD,14));
        editCourseFee.setBounds(505, 152,150,30);

        editCourseFeeBox = new RoundedTextField("e.g. $1200",10);
        editCourseFeeBox.setBounds(505,184,465,40);
        editCourseFeeBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        editCourseFeeBox.setForeground(new Color(105,105,105));

        JLabel editCourseDescription = new JLabel("Description");
        editCourseDescription.setForeground(new Color(120,120,120));
        editCourseDescription.setFont(new Font("Montserrat",Font.BOLD,14));
        editCourseDescription.setBounds(20, 232,150,30);

        editCourseDescriptionBox = new RoundedTextField("Enter course description...",10);
        editCourseDescriptionBox.setBounds(20,264,950,100);
        editCourseDescriptionBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        editCourseDescriptionBox.setForeground(new Color(105,105,105));



        AnimatedButton resetEditCourseBtn = new AnimatedButton("Reset",40,
                new Color(240, 239, 255),    // normal Color (light off-white)
                new Color(220, 218, 235),    // Hovered Color (slightly darker)
                new Color(200, 198, 215),    // Pressed Color (even darker)
                new Color(240, 239, 255));
        resetEditCourseBtn.setBounds(710,380,120,40);
        resetEditCourseBtn.setForeground(Color.darkGray);
        resetEditCourseBtn.setFont(new Font("Montserrat",Font.BOLD,14));
        resetEditCourseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetEditCourseBtn.setBounds(710,380,120,40);
                resetEditCourseBtn.setBounds(710,379,120,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetEditCourseBtn.setBounds(710,379,120,40);
                resetEditCourseBtn.setBounds(710,380,120,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                editCourseNameBox.setText("");
                editCourseCodeBox.setText("");
                editCourseDurationBox.setText("");
                editCourseFeeBox.setText("");
                editCourseDescriptionBox.setText("");
            }
        });



        AnimatedButton addEditedCourseBtn = new AnimatedButton("Add",40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        addEditedCourseBtn.setBounds(850,380,120,40);
        addEditedCourseBtn.setForeground(Color.WHITE);
        addEditedCourseBtn.setFont(new Font("Montserrat",Font.BOLD,14));
        addEditedCourseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addEditedCourseBtn.setBounds(850,380,120,40);
                addEditedCourseBtn.setBounds(850,379,120,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addEditedCourseBtn.setBounds(850,379,120,40);
                addEditedCourseBtn.setBounds(850,380,120,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                int C_id = Integer.parseInt(courseTableModel.getValueAt(courseTable.getSelectedRow(),0).toString());
                String course_Name = editCourseNameBox.getText().trim();
                String course_Code = editCourseCodeBox.getText().trim();
                String course_Duration = editCourseDurationBox.getText().trim();
                String course_Fee = editCourseFeeBox.getText().trim();
                String Description = editCourseDescriptionBox.getText().trim();

                if(course_Name.isEmpty() || course_Code.isEmpty() || course_Duration.isEmpty() || course_Fee.isEmpty() || Description.isEmpty()){
                    JOptionPane.showMessageDialog(null,"All fields are required!","Validation Error",JOptionPane.WARNING_MESSAGE);
                }
                else if (!course_Duration.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Duration must be numeric!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    editCourseDurationBox.requestFocus();
                }

                else if (Integer.parseInt(course_Duration) <= 0 || Integer.parseInt(course_Duration) > 60) {
                    JOptionPane.showMessageDialog(null, "Duration must be between 1 and 60 months!","Validation Error", JOptionPane.WARNING_MESSAGE);
                    editCourseDurationBox.requestFocus();
                }
                else if (!course_Fee.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Fee must be numeric!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    editCourseFeeBox.requestFocus();
                }
                else if (Integer.parseInt(course_Fee) <= 0 || Integer.parseInt(course_Fee) > 500000) {
                    JOptionPane.showMessageDialog(null, "Fee is out of valid range!","Validation Error", JOptionPane.WARNING_MESSAGE);
                    editCourseFeeBox.requestFocus();
                }
                else{
                    try {
                        DatabaseConfig.setConnection();
                        Statement st = DatabaseConfig.getSt();
                        String query = String.format("Update NewCourse set course_name = '%s', course_code = '%s', duration = '%s', course_fee = %d, course_description = '%s' where id = %d"
                                ,course_Name,course_Code,course_Duration + " Months",Integer.parseInt(course_Fee),Description,C_id);
                        st.executeUpdate(query);
                        editCourseNameBox.setText("");
                        editCourseCodeBox.setText("");
                        editCourseDurationBox.setText("");
                        editCourseFeeBox.setText("");
                        editCourseDescriptionBox.setText("");
                        setVisible(false);

                        courseTableModel.setValueAt(course_Name,courseTable.getSelectedRow(),1);
                        courseTableModel.setValueAt(course_Code,courseTable.getSelectedRow(),2);
                        courseTableModel.setValueAt(course_Duration + " Months",courseTable.getSelectedRow(),3);
                        courseTableModel.setValueAt(course_Fee + " PKR",courseTable.getSelectedRow(),4);


                        coursePanel.setVisible(true);
                        DatabaseConfig.closeConnection();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }

        });




        RoundedPanel editCourseDetailsPanel = new RoundedPanel(30);
        editCourseDetailsPanel.setBounds(20,68,990,436);
        editCourseDetailsPanel.setBackground(Color.WHITE);
        editCourseDetailsPanel.setLayout(null);
        editCourseDetailsPanel.add(editCourseDetails);
        editCourseDetailsPanel.add(editCourseLine);
        editCourseDetailsPanel.add(editCourseName);
        editCourseDetailsPanel.add(editCourseNameBox);
        editCourseDetailsPanel.add(editCourseCode);
        editCourseDetailsPanel.add(editCourseCodeBox);
        editCourseDetailsPanel.add(editCourseDuration);
        editCourseDetailsPanel.add(editCourseDurationBox);
        editCourseDetailsPanel.add(editCourseFee);
        editCourseDetailsPanel.add(editCourseFeeBox);
        editCourseDetailsPanel.add(editCourseDescription);
        editCourseDetailsPanel.add(editCourseDescriptionBox);
        editCourseDetailsPanel.add(resetEditCourseBtn);
        editCourseDetailsPanel.add(addEditedCourseBtn);



        setBounds(250,70,1030,650);
        setBackground(new Color(240, 239, 255));
        setLayout(null);
        add(editCourseText);
        add(editCourseDetailsPanel);
        add(editCourseBackToList);
        setVisible(false);
    }
}