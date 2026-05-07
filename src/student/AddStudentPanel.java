package student;
import components.*;
import dashboard.MngMainFrame;
import database.DatabaseConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import static dashboard.MngMainFrame.studentPanel;

public class AddStudentPanel extends JPanel {
    MngMainFrame frame;

    public AddStudentPanel(MngMainFrame frame) {
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

        JLabel addNewStudent = new JLabel("Add New Student");
        addNewStudent.setFont(new Font("Century Gothic",Font.BOLD,33));
        addNewStudent.setForeground(new Color(33,37,40));
        addNewStudent.setBounds(20,20,300,40);
        addNewStudent.setVerticalAlignment(JLabel.BOTTOM);


        AnimatedButton backToList = new AnimatedButton("  Back to List",20,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));
        backToList.setBounds(860,18,150,40);
        backToList.setForeground(Color.WHITE);
        backToList.setFont(new Font("Montserrat",Font.PLAIN,14));
        backToList.setIcon(new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\back.png"));
        backToList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backToList.setBounds(860,18,150,40);
                backToList.setBounds(860,17,150,40);
                backToList.setBounds(860,16,150,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backToList.setBounds(860,16,150,40);
                backToList.setBounds(860,17,150,40);
                backToList.setBounds(860,18,150,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                studentPanel.visibility(true);
            }
        });


        JLabel stuDetails = new JLabel("Student Details");
        stuDetails.setBounds(40,17,150,30);
        stuDetails.setFont(new Font("Montserrat",Font.BOLD,14));
        stuDetails.setForeground(Color.darkGray);

        JLabel addStuLine = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        addStuLine.setBounds(0,43,990,20);
        addStuLine.setFont(new Font("Arial",Font.BOLD,14));
        addStuLine.setForeground(new Color(240,240,240));


        JLabel firstName = new JLabel("First Name");
        firstName.setForeground(new Color(120,120,120));
        firstName.setFont(new Font("Montserrat",Font.BOLD,14));
        firstName.setBounds(20, 72,150,30);

        RoundedTextField firstNameBox = new RoundedTextField("e.g. John",10);
        firstNameBox.setBounds(20,104,465,40);
        firstNameBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        firstNameBox.setForeground(new Color(105,105,105));


        JLabel lastName = new JLabel("Last Name");
        lastName.setForeground(new Color(120,120,120));
        lastName.setFont(new Font("Montserrat",Font.BOLD,14));
        lastName.setBounds(505, 72,150,30);

        RoundedTextField lastNameBox = new RoundedTextField("e.g. Doe",10);
        lastNameBox.setBounds(505,104,465,40);
        lastNameBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        lastNameBox.setForeground(new Color(105,105,105));


        JLabel emailAddress = new JLabel("Email Address");
        emailAddress.setForeground(new Color(120,120,120));
        emailAddress.setFont(new Font("Montserrat",Font.BOLD,14));
        emailAddress.setBounds(20, 152,150,30);

        RoundedTextField emailAddBox = new RoundedTextField("e.g. john123@gmail.com",10);
        emailAddBox.setBounds(20,184,465,40);
        emailAddBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        emailAddBox.setForeground(new Color(105,105,105));


        JLabel phone = new JLabel("Phone Number");
        phone.setForeground(new Color(120,120,120));
        phone.setFont(new Font("Montserrat",Font.BOLD,14));
        phone.setBounds(505, 152,150,30);

        RoundedTextField phoneBox = new RoundedTextField("e.g. +92 3012345678",10);
        phoneBox.setBounds(505,184,465,40);
        phoneBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        phoneBox.setForeground(new Color(105,105,105));


        JLabel age = new JLabel("Age");
        age.setForeground(new Color(120,120,120));
        age.setFont(new Font("Montserrat",Font.BOLD,14));
        age.setBounds(20, 232,150,30);

        RoundedTextField ageBox = new RoundedTextField("e.g. 19",10);
        ageBox.setBounds(20,264,465,40);
        ageBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        ageBox.setForeground(new Color(105,105,105));


        JLabel gender = new JLabel("Gender");
        gender.setForeground(new Color(120,120,120));
        gender.setFont(new Font("Montserrat",Font.BOLD,14));
        gender.setBounds(505, 232,150,30);


        RoundedTextField genderBox = new RoundedTextField("e.g. Male",10);
        genderBox.setBounds(505,264,465,40);
        genderBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        genderBox.setForeground(new Color(105,105,105));


        JLabel address = new JLabel("Address");
        address.setForeground(new Color(120,120,120));
        address.setFont(new Font("Montserrat",Font.BOLD,14));
        address.setBounds(20, 312,150,30);


        RoundedTextField addressBox = new RoundedTextField("Enter full address...",10);
        addressBox.setBounds(20,342,950,100);
        addressBox.setFont(new Font("Montserrat",Font.PLAIN,14));
        addressBox.setForeground(new Color(105,105,105));



        AnimatedButton resetBtn = new AnimatedButton("Reset",40,
                new Color(240, 239, 255),    // normal Color (light off-white)
                new Color(220, 218, 235),    // Hovered Color (slightly darker)
                new Color(200, 198, 215),    // Pressed Color (even darker)
                new Color(240, 239, 255));   // Border Color (same as normal)
        resetBtn.setBounds(710,458,120,40);
        resetBtn.setForeground(Color.DARK_GRAY);
        resetBtn.setFont(new Font("Montserrat",Font.BOLD,14));
        resetBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetBtn.setBounds(710,458,120,40);
                resetBtn.setBounds(710,457,120,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetBtn.setBounds(710,457,120,40);
                resetBtn.setBounds(710,458,120,40);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                firstNameBox.setText("");
                lastNameBox.setText("");
                emailAddBox.setText("");
                phoneBox.setText("");
                ageBox.setText("");
                genderBox.setText("");
                addressBox.setText("");
            }
        });



        AnimatedButton addBtn = new AnimatedButton("Add",40,
                new Color(134, 73, 255),     // normal Color (purple)
                new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                new Color(90, 50, 190),      // Pressed Color (even darker purple)
                new Color(134, 73, 255));    // Border Color (same as normal)
        addBtn.setBounds(850,458,120,40);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Montserrat",Font.BOLD,14));
        addBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addBtn.setBounds(850,458,120,40);
                addBtn.setBounds(850,457,120,40);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addBtn.setBounds(850,457,120,40);
                addBtn.setBounds(850,458,120,40);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                String f_name = firstNameBox.getText();
                String l_name = lastNameBox.getText();
                String stu_email = emailAddBox.getText();
                String stu_phone = phoneBox.getText();
                String stu_age = ageBox.getText();
                String stu_gender = genderBox.getText();
                String stu_address = addressBox.getText();

                if(f_name.isEmpty() || l_name.isEmpty() || stu_email.isEmpty() || stu_phone.isEmpty()
                        || stu_age.isEmpty() || stu_gender.isEmpty()|| stu_address.isEmpty()){
                    JOptionPane.showMessageDialog(null,"All fields are required!","Validation Error",JOptionPane.WARNING_MESSAGE);
                }
                else if(!stu_email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Invalid email format!\nExample: name@domain.com"
                            , "Validation Error", JOptionPane.WARNING_MESSAGE);
                    emailAddBox.requestFocus();
                }
                else if(!stu_phone.matches("[0-9+]{11,13}")) {
                    JOptionPane.showMessageDialog(null, "Invalid phone number!\ne.g. 03001234567 or +923001234567"
                            , "Validation Error", JOptionPane.WARNING_MESSAGE);
                    phoneBox.requestFocus();
                }
                else if(!stu_age.matches("[0-9]{1,2}")) {
                    JOptionPane.showMessageDialog(null, "Invalid age!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    ageBox.requestFocus();
                }
                else if(!stu_gender.matches("(?i)^(male|female|other)$")) {
                    JOptionPane.showMessageDialog(null, "Invalid gender!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    genderBox.requestFocus();
                }
                else{
                    try {
                        DatabaseConfig.setConnection();
                        Statement st = DatabaseConfig.getSt();

                        String query = String.format("INSERT INTO NewStudent(firstname,lastname,email,phone,age,gender,address) VALUES ('%s','%s','%s','%s','%s','%s','%s')"
                                ,f_name, l_name, stu_email, stu_phone, stu_age, stu_gender, stu_address);
                        st.executeUpdate(query);
                        DatabaseConfig.closeConnection();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    firstNameBox.setText("");
                    lastNameBox.setText("");
                    emailAddBox.setText("");
                    phoneBox.setText("");
                    ageBox.setText("");
                    genderBox.setText("");
                    addressBox.setText("");
                    try {
                        Connection con = DriverManager.getConnection(DatabaseConfig.URL,"root","Nisar123");
                        Statement st = con.createStatement(
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY
                        );
                        String query = "select stu_id,firstname,lastname,email,age,phone from NewStudent";
                        ResultSet resultset = st.executeQuery(query);
                        setVisible(false);
                        studentPanel.visibility(true);
                        if (resultset.last()) {
                            Object[] newData = {resultset.getString("stu_id")
                                    , resultset.getString("firstname") + " " + resultset.getString("lastname")
                                    , resultset.getString("email")
                                    , resultset.getString("age")
                                    , resultset.getString("phone")};
                            StudentPanel.model.addRow(newData);
                        }
                        resultset.close();
                        st.close();
                        con.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }}
        });

        RoundedPanel addStuPanel = new RoundedPanel(30);
        addStuPanel.setBounds(20,68,990,515);
        addStuPanel.setBackground(Color.WHITE);
        addStuPanel.setLayout(null);
        addStuPanel.add(stuDetails);
        addStuPanel.add(addStuLine);
        addStuPanel.add(firstName);
        addStuPanel.add(lastName);
        addStuPanel.add(firstNameBox);
        addStuPanel.add(lastNameBox);
        addStuPanel.add(emailAddress);
        addStuPanel.add(phone);
        addStuPanel.add(emailAddBox);
        addStuPanel.add(phoneBox);
        addStuPanel.add(age);
        addStuPanel.add(gender);
        addStuPanel.add(ageBox);
        addStuPanel.add(genderBox);
        addStuPanel.add(address);
        addStuPanel.add(addressBox);
        addStuPanel.add(resetBtn);
        addStuPanel.add(addBtn);


        setBounds(250,70,1030,650);
        setBackground(new Color(240, 239, 255));
        setLayout(null);
        add(addNewStudent);
        add(addStuPanel);
        add(backToList);
        setVisible(false);
    }

}