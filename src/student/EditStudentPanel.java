package student;
import components.*;
import dashboard.MngMainFrame;
import database.DatabaseConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import static dashboard.MngMainFrame.studentPanel;
import static student.StudentPanel.model;
import static student.StudentPanel.table;

public class EditStudentPanel extends JPanel {
    MngMainFrame frame;

    static RoundedTextField firstNameEditBox;
    static RoundedTextField lastNameEditBox;
    static RoundedTextField emailAddEditBox;
    static RoundedTextField phoneEditBox;
    static RoundedTextField ageEditBox;
    static RoundedTextField genderEditBox;
    static RoundedTextField addressEditBox;


    public EditStudentPanel(MngMainFrame frame) {
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

    void initialize() {
        JLabel editStudentText = new JLabel("Edit Student");
        editStudentText.setFont(new Font("Century Gothic",Font.BOLD,33));
        editStudentText.setForeground(new Color(33,37,40));
        editStudentText.setBounds(20,20,300,40);

        AnimatedButton editBackToList = new AnimatedButton("  Back to List",20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        editBackToList.setBounds(860,18,150,40);
        editBackToList.setForeground(Color.WHITE);
        editBackToList.setFont(new Font("Montserrat",Font.PLAIN,14));
        editBackToList.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        editBackToList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editBackToList.setBounds(860,18,150,40);
                editBackToList.setBounds(860,17,150,40);
                editBackToList.setBounds(860,16,150,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editBackToList.setBounds(860,16,150,40);
                editBackToList.setBounds(860,17,150,40);
                editBackToList.setBounds(860,18,150,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                studentPanel.visibility(true);
            }
        });

        JLabel editStuDetails = new JLabel("Edit Student Details");
        editStuDetails.setBounds(40,17,200,30);
        editStuDetails.setFont(new Font("Montserrat",Font.BOLD,14));
        editStuDetails.setForeground(Color.darkGray);

        JLabel editStuLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        editStuLine.setBounds(0,43,990,20);
        editStuLine.setFont(new Font("Arial",Font.BOLD,14));
        editStuLine.setForeground(new Color(240,240,240));


        JLabel firstNameEdit = new JLabel("First Name");
        firstNameEdit.setForeground(new Color(120,120,120));
        firstNameEdit.setFont(new Font("Montserrat",Font.BOLD,14));
        firstNameEdit.setBounds(20, 72,150,30);

        firstNameEditBox = new RoundedTextField("e.g. John",10);
        firstNameEditBox.setBounds(20,104,465,40);
        firstNameEditBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        firstNameEditBox.setForeground(new Color(105,105,105));


        JLabel lastNameEdit = new JLabel("Last Name");
        lastNameEdit.setForeground(new Color(120,120,120));
        lastNameEdit.setFont(new Font("Montserrat",Font.BOLD,14));
        lastNameEdit.setBounds(505, 72,150,30);

        lastNameEditBox = new RoundedTextField("e.g. Doe",10);
        lastNameEditBox.setBounds(505,104,465,40);
        lastNameEditBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        lastNameEditBox.setForeground(new Color(105,105,105));


        JLabel emailAddressEdit = new JLabel("Email Address");
        emailAddressEdit.setForeground(new Color(120,120,120));
        emailAddressEdit.setFont(new Font("Montserrat",Font.BOLD,14));
        emailAddressEdit.setBounds(20, 152,150,30);

        emailAddEditBox = new RoundedTextField("e.g. john123@gmail.com",10);
        emailAddEditBox.setBounds(20,184,465,40);
        emailAddEditBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        emailAddEditBox.setForeground(new Color(105,105,105));


        JLabel phoneEdit = new JLabel("Phone Number");
        phoneEdit.setForeground(new Color(120,120,120));
        phoneEdit.setFont(new Font("Montserrat",Font.BOLD,14));
        phoneEdit.setBounds(505, 152,150,30);

        phoneEditBox = new RoundedTextField("e.g. +92 3012345678",10);
        phoneEditBox.setBounds(505,184,465,40);
        phoneEditBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        phoneEditBox.setForeground(new Color(105,105,105));


        JLabel ageEdit = new JLabel("Age");
        ageEdit.setForeground(new Color(120,120,120));
        ageEdit.setFont(new Font("Montserrat",Font.BOLD,14));
        ageEdit.setBounds(20, 232,150,30);

        ageEditBox = new RoundedTextField("e.g. 19",10);
        ageEditBox.setBounds(20,264,465,40);
        ageEditBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        ageEditBox.setForeground(new Color(105,105,105));


        JLabel genderEdit = new JLabel("Gender");
        genderEdit.setForeground(new Color(120,120,120));
        genderEdit.setFont(new Font("Montserrat",Font.BOLD,14));
        genderEdit.setBounds(505, 232,150,30);


        genderEditBox = new RoundedTextField("e.g. Male",10);
        genderEditBox.setBounds(505,264,465,40);
        genderEditBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        genderEditBox.setForeground(new Color(105,105,105));


        JLabel addressEdit = new JLabel("Address");
        addressEdit.setForeground(new Color(120,120,120));
        addressEdit.setFont(new Font("Montserrat",Font.BOLD,14));
        addressEdit.setBounds(20, 312,150,30);


        addressEditBox = new RoundedTextField("Enter full address...",10);
        addressEditBox.setBounds(20,342,950,100);
        addressEditBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        addressEditBox.setForeground(new Color(105,105,105));



        AnimatedButton cancelEditBtn = new AnimatedButton("Cancel",40,
                new Color(240, 239, 255),    // normal Color (light off-white)
                new Color(220, 218, 235),    // Hovered Color (slightly darker)
                new Color(200, 198, 215),    // Pressed Color (even darker)
                new Color(240, 239, 255));   // Border Color (same as normal)
        cancelEditBtn.setBounds(710,458,120,40);
        cancelEditBtn.setForeground(Color.darkGray);
        cancelEditBtn.setFont(new Font("Montserrat",Font.BOLD,14));
        cancelEditBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelEditBtn.setBounds(710,458,120,40);
                cancelEditBtn.setBounds(710,457,120,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelEditBtn.setBounds(710,457,120,40);
                cancelEditBtn.setBounds(710,458,120,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                firstNameEditBox.setText("");
                lastNameEditBox.setText("");
                emailAddEditBox.setText("");
                phoneEditBox.setText("");
                ageEditBox.setText("");
                genderEditBox.setText("");
                addressEditBox.setText("");
            }
        });


        AnimatedButton editStuBtn = new AnimatedButton("Edit",40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        editStuBtn.setBounds(850,458,120,40);
        editStuBtn.setForeground(Color.WHITE);
        editStuBtn.setFont(new Font("Montserrat",Font.BOLD,14));
        editStuBtn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                editStuBtn.setBounds(850,458,120,40);
                editStuBtn.setBounds(850,457,120,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editStuBtn.setBounds(850,457,120,40);
                editStuBtn.setBounds(850,458,120,40);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                String studentId = table.getValueAt(table.getSelectedRow(),0).toString();

                String f_name = firstNameEditBox.getText();
                String l_name = lastNameEditBox.getText();
                String stu_email = emailAddEditBox.getText();
                String stu_phone = phoneEditBox.getText();
                String stu_age = ageEditBox.getText();
                String stu_gender = genderEditBox.getText();
                String stu_address = addressEditBox.getText();

                if(f_name.isEmpty() || l_name.isEmpty() || stu_email.isEmpty() || stu_phone.isEmpty()
                        || stu_age.isEmpty() || stu_gender.isEmpty()|| stu_address.isEmpty()){
                    JOptionPane.showMessageDialog(null,"All fields are required!","Validation Error",JOptionPane.WARNING_MESSAGE);
                }
                else if(!stu_email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Invalid email format!\nExample: name@domain.com"
                            , "Validation Error", JOptionPane.WARNING_MESSAGE);
                    emailAddEditBox.requestFocus();
                }
                else if(!stu_phone.matches("[0-9+]{11,13}")) {
                    JOptionPane.showMessageDialog(null, "Invalid phone number!\ne.g. 03001234567 or +923001234567"
                            , "Validation Error", JOptionPane.WARNING_MESSAGE);
                    phoneEditBox.requestFocus();
                }
                else if(!stu_age.matches("[0-9]{1,2}")) {
                    JOptionPane.showMessageDialog(null, "Invalid age!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    ageEditBox.requestFocus();
                }
                else if(!stu_gender.matches("(?i)^(male|female|other)$")) {
                    JOptionPane.showMessageDialog(null, "Invalid gender!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    genderEditBox.requestFocus();
                }
                else{
                    try {
                        DatabaseConfig.setConnection();
                        Statement st = DatabaseConfig.getSt();

                        String query = String.format("update NewStudent set firstname = '%s',lastname = '%s',email = '%s', phone='%s',age='%s',gender = '%s',address = '%s' where stu_id = '%s'"
                                ,f_name, l_name, stu_email, stu_phone, stu_age, stu_gender, stu_address,studentId);
                        st.executeUpdate(query);
                        DatabaseConfig.closeConnection();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    model.setValueAt(f_name + " " + l_name,table.getSelectedRow(),1);
                    model.setValueAt(stu_email,table.getSelectedRow(),2);
                    model.setValueAt(stu_age,table.getSelectedRow(),3);
                    model.setValueAt(stu_phone,table.getSelectedRow(),4);

                    setVisible(false);
                    studentPanel.visibility(true);

                    firstNameEditBox.setText("");
                    lastNameEditBox.setText("");
                    emailAddEditBox.setText("");
                    phoneEditBox.setText("");
                    ageEditBox.setText("");
                    genderEditBox.setText("");
                    addressEditBox.setText("");
                }
            }
        });


        RoundedPanel editStuDetailsPanel = new RoundedPanel(30);
        editStuDetailsPanel.setBounds(20,68,990,515);
        editStuDetailsPanel.setBackground(Color.WHITE);
        editStuDetailsPanel.setLayout(null);
        editStuDetailsPanel.add(editStuDetails);
        editStuDetailsPanel.add(editStuLine);
        editStuDetailsPanel.add(firstNameEdit);
        editStuDetailsPanel.add(firstNameEditBox);
        editStuDetailsPanel.add(lastNameEdit);
        editStuDetailsPanel.add(lastNameEditBox);
        editStuDetailsPanel.add(emailAddressEdit);
        editStuDetailsPanel.add(emailAddEditBox);
        editStuDetailsPanel.add(phoneEdit);
        editStuDetailsPanel.add(phoneEditBox);
        editStuDetailsPanel.add(ageEdit);
        editStuDetailsPanel.add(ageEditBox);
        editStuDetailsPanel.add(genderEdit);
        editStuDetailsPanel.add(genderEditBox);
        editStuDetailsPanel.add(addressEdit);
        editStuDetailsPanel.add(addressEditBox);
        editStuDetailsPanel.add(cancelEditBtn);
        editStuDetailsPanel.add(editStuBtn);

        setBounds(250,70,1030,650);
        setBackground(new Color(240, 239, 255));
        setLayout(null);
        add(editStudentText);
        add(editStuDetailsPanel);
        add(editBackToList);
        setVisible(false);
    }

}