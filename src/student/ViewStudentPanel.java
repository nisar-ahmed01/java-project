package student;
import components.*;
import dashboard.MngMainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import static dashboard.MngMainFrame.editStudentPanel;
import static dashboard.MngMainFrame.studentPanel;

public class ViewStudentPanel extends JPanel {
    MngMainFrame frame;

    static JLabel id_stu_ans;
    static JLabel f_Name_ans;
    static JLabel e_address_ans;
    static JLabel isActive_ans;
    static JLabel phone_number_ans;
    static JLabel stu_age_ans;
    static JLabel stu_gender_ans;
    static JLabel Address_ans;

    public ViewStudentPanel(MngMainFrame frame) {
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

    void initialize() {
        //===================== view Student==========================
        JLabel viewStudentText = new JLabel("Student Details");
        viewStudentText.setFont(new Font("Century Gothic",Font.BOLD,33));
        viewStudentText.setForeground(new Color(33,37,40));
        viewStudentText.setBounds(20,20,300,40);
        viewStudentText.setVerticalAlignment(JLabel.BOTTOM);


        AnimatedButton viewBackToList = new AnimatedButton("  Back to List",20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        viewBackToList.setBounds(860,18,150,40);
        viewBackToList.setForeground(Color.WHITE);
        viewBackToList.setFont(new Font("Montserrat",Font.PLAIN,14));
        viewBackToList.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        viewBackToList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewBackToList.setBounds(860,18,150,40);
                viewBackToList.setBounds(860,17,150,40);
                viewBackToList.setBounds(860,16,150,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewBackToList.setBounds(860,16,150,40);
                viewBackToList.setBounds(860,17,150,40);
                viewBackToList.setBounds(860,18,150,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                studentPanel.setVisible(true);
            }
        });

        JLabel viewStuDetails = new JLabel("Information");
        viewStuDetails.setBounds(40,17,200,30);
        viewStuDetails.setFont(new Font("Montserrat",Font.BOLD,14));
        viewStuDetails.setForeground(Color.darkGray);

        JLabel viewStuLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        viewStuLine.setBounds(0,43,990,20);
        viewStuLine.setFont(new Font("Arial",Font.BOLD,14));
        viewStuLine.setForeground(new Color(240,240,240));

        JLabel id_stu = new JLabel("Student ID:");
        id_stu.setFont(new Font("Montserrat",Font.BOLD,14));
        id_stu.setForeground(Color.darkGray);
        id_stu.setBorder(null);
        id_stu.setBackground(null);
        id_stu.setVerticalAlignment(JLabel.CENTER);
        id_stu.setBounds(40,90,150,30);

        id_stu_ans = new JLabel();
        id_stu_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        id_stu_ans.setForeground(Color.darkGray);
        id_stu_ans.setBorder(null);
        id_stu_ans.setBackground(null);
        id_stu_ans.setVerticalAlignment(JLabel.CENTER);
        id_stu_ans.setBounds(290,90,200,30);

        JLabel f_Name = new JLabel("Full Name:");
        f_Name.setFont(new Font("Montserrat",Font.BOLD,14));
        f_Name.setForeground(Color.darkGray);
        f_Name.setBorder(null);
        f_Name.setBackground(null);
        f_Name.setVerticalAlignment(JLabel.CENTER);
        f_Name.setBounds(40,132,150,30);

        f_Name_ans = new JLabel();
        f_Name_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        f_Name_ans.setForeground(Color.darkGray);
        f_Name_ans.setBorder(null);
        f_Name_ans.setBackground(null);
        f_Name_ans.setVerticalAlignment(JLabel.CENTER);
        f_Name_ans.setBounds(290,132,300,30);

        JLabel e_address = new JLabel("Email Address:");
        e_address.setFont(new Font("Montserrat",Font.BOLD,14));
        e_address.setForeground(Color.darkGray);
        e_address.setBorder(null);
        e_address.setBackground(null);
        e_address.setVerticalAlignment(JLabel.CENTER);
        e_address.setBounds(40,174,150,30);

        e_address_ans = new JLabel();
        e_address_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        e_address_ans.setForeground(Color.darkGray);
        e_address_ans.setBorder(null);
        e_address_ans.setBackground(null);
        e_address_ans.setVerticalAlignment(JLabel.CENTER);
        e_address_ans.setBounds(290,174,400,30);

        JLabel phone_number = new JLabel("Phone Number:");
        phone_number.setFont(new Font("Montserrat",Font.BOLD,14));
        phone_number.setForeground(Color.darkGray);
        phone_number.setBorder(null);
        phone_number.setBackground(null);
        phone_number.setVerticalAlignment(JLabel.CENTER);
        phone_number.setBounds(40,216,150,30);

        phone_number_ans = new JLabel();
        phone_number_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        phone_number_ans.setForeground(Color.darkGray);
        phone_number_ans.setBorder(null);
        phone_number_ans.setBackground(null);
        phone_number_ans.setVerticalAlignment(JLabel.CENTER);
        phone_number_ans.setBounds(290,216,300,30);

        JLabel stu_age = new JLabel("Age:");
        stu_age.setFont(new Font("Montserrat",Font.BOLD,14));
        stu_age.setForeground(Color.darkGray);
        stu_age.setBorder(null);
        stu_age.setBackground(null);
        stu_age.setVerticalAlignment(JLabel.CENTER);
        stu_age.setBounds(40,258,150,30);

        stu_age_ans = new JLabel();
        stu_age_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        stu_age_ans.setForeground(Color.darkGray);
        stu_age_ans.setBorder(null);
        stu_age_ans.setBackground(null);
        stu_age_ans.setVerticalAlignment(JLabel.CENTER);
        stu_age_ans.setBounds(290,258,150,30);

        JLabel stu_gender = new JLabel("Gender:");
        stu_gender.setFont(new Font("Montserrat",Font.BOLD,14));
        stu_gender.setForeground(Color.darkGray);
        stu_gender.setBorder(null);
        stu_gender.setBackground(null);
        stu_gender.setVerticalAlignment(JLabel.CENTER);
        stu_gender.setBounds(40,300,150,30);

        stu_gender_ans = new JLabel();
        stu_gender_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        stu_gender_ans.setForeground(Color.darkGray);
        stu_gender_ans.setBorder(null);
        stu_gender_ans.setBackground(null);
        stu_gender_ans.setVerticalAlignment(JLabel.CENTER);
        stu_gender_ans.setBounds(290,300,200,30);

        JLabel isActive = new JLabel("Is Active:");
        isActive.setFont(new Font("Montserrat",Font.BOLD,14));
        isActive.setForeground(Color.darkGray);
        isActive.setBorder(null);
        isActive.setBackground(null);
        isActive.setVerticalAlignment(JLabel.CENTER);
        isActive.setBounds(40,342,150,30);

        isActive_ans = new JLabel();
        isActive_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        isActive_ans.setForeground(Color.darkGray);
        isActive_ans.setBorder(null);
        isActive_ans.setBackground(null);
        isActive_ans.setVerticalAlignment(JLabel.CENTER);
        isActive_ans.setBounds(290,342,300,30);

        JLabel Address = new JLabel("Address:");
        Address.setFont(new Font("Montserrat",Font.BOLD,14));
        Address.setForeground(Color.darkGray);
        Address.setBorder(null);
        Address.setBackground(null);
        Address.setVerticalAlignment(JLabel.CENTER);
        Address.setBounds(40,384,150,30);

        Address_ans = new JLabel();
        Address_ans.setFont(new Font("Montserrat",Font.PLAIN,14));
        Address_ans.setForeground(Color.darkGray);
        Address_ans.setBorder(null);
        Address_ans.setBackground(null);
        Address_ans.setVerticalAlignment(JLabel.CENTER);
        Address_ans.setBounds(290,384,600,30);


        JLabel viewStuDownLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        viewStuDownLine.setBounds(0,429,990,20);
        viewStuDownLine.setFont(new Font("Arial",Font.BOLD,14));
        viewStuDownLine.setForeground(new Color(240,240,240));


        AnimatedButton editStudentBtn= new AnimatedButton(("Edit Student"),40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        editStudentBtn.setBounds(40,461,145,40);
        editStudentBtn.setForeground(Color.WHITE);
        editStudentBtn.setFont(new Font("Montserrat",Font.BOLD,14));
        editStudentBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editStudentBtn.setBounds(40,461,145,40);
                editStudentBtn.setBounds(40,460,145,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editStudentBtn.setBounds(40,460,145,40);
                editStudentBtn.setBounds(40,461,145,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                editStudentPanel.setVisible(true);
            }
        });



        RoundedPanel viewStuDetailsPanel = new RoundedPanel(30);
        viewStuDetailsPanel.setBounds(20,68,990,515);
        viewStuDetailsPanel.setBackground(Color.WHITE);
        viewStuDetailsPanel.setLayout(null);
        viewStuDetailsPanel.add(viewStuDetails);
        viewStuDetailsPanel.add(viewStuLine);
        viewStuDetailsPanel.add(id_stu);
        viewStuDetailsPanel.add(id_stu_ans);
        viewStuDetailsPanel.add(f_Name);
        viewStuDetailsPanel.add(f_Name_ans);
        viewStuDetailsPanel.add(e_address);
        viewStuDetailsPanel.add(e_address_ans);
        viewStuDetailsPanel.add(phone_number);
        viewStuDetailsPanel.add(phone_number_ans);
        viewStuDetailsPanel.add(stu_age);
        viewStuDetailsPanel.add(stu_age_ans);
        viewStuDetailsPanel.add(stu_gender);
        viewStuDetailsPanel.add(stu_gender_ans);
        viewStuDetailsPanel.add(isActive);
        viewStuDetailsPanel.add(isActive_ans);
        viewStuDetailsPanel.add(Address);
        viewStuDetailsPanel.add(Address_ans);
        viewStuDetailsPanel.add(viewStuDownLine);
        viewStuDetailsPanel.add(editStudentBtn);


        setBounds(250,70,1030,650);
        setBackground(new Color(240, 239, 255));
        setLayout(null);
        add(viewStudentText);
        add(viewStuDetailsPanel);
        add(viewBackToList);
        setVisible(false);
    }

}