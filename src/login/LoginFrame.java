package login;

import components.*;
import dashboard.MngMainFrame;
import database.DatabaseConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class LoginFrame extends JFrame{

    public LoginFrame() {
        initialize();
    }

    public void visibility(boolean visible){
        setVisible(visible);
    }

    private void initialize() {

        // Login Panel
        RoundedPanel loginPanel = new RoundedPanel(30);
        loginPanel.setBackground(new Color(245, 240, 255));
        loginPanel.setMaximumSize(new Dimension(330, 345));
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Username
        JLabel label5 = new JLabel();
        label5.setForeground(Color.BLACK);
        label5.setFont(new Font("Montserrat", Font.PLAIN, 16));
        label5.setText("Username:");
        gbc.insets = new Insets(0, 0, 3, 0);
        loginPanel.add(label5, gbc);

        // Username Textbox
        RoundedTextField username = new RoundedTextField("Enter Username", 10);
        username.setFont(new Font("Montserrat", Font.PLAIN, 13));
        username.setPreferredSize(new Dimension(250, 35));
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = 1;
        username.setCaretColor(new Color(142, 83, 233));
        loginPanel.add(username, gbc);

        // Password
        JLabel label6 = new JLabel();
        label6.setForeground(Color.BLACK);
        label6.setFont(new Font("Montserrat", Font.PLAIN, 16));
        label6.setText("Password:");
        gbc.insets = new Insets(25, 0, 3, 0);
        gbc.gridy = 2;
        loginPanel.add(label6, gbc);

        // Password textbox
        RoundedPasswordField password = new RoundedPasswordField("Enter Password", 10);
        boolean[] eye = {true};

        // Eye Button
        JButton btn = new JButton("👁️");
        btn.setContentAreaFilled(false);
        btn.setBorder(null);
        btn.setFocusable(false);
        btn.setFont(new Font("", Font.PLAIN, 18));
        btn.setForeground(new Color(200, 180, 240));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(20, 15));
        btn.setMargin(new Insets(0, 0, 0, 0));
        GridBagConstraints z = new GridBagConstraints();
        z.insets = new Insets(0, 225, 0, 25);
        btn.addActionListener(e -> {
            if (eye[0]) {
                password.setEchoChar((char) 0);
                btn.setForeground(new Color(142, 83, 233));
                eye[0] = false;
            } else {
                password.setEchoChar('•');
                btn.setForeground(new Color(200, 180, 240));
                eye[0] = true;
            }
        });

        password.setFont(new Font("Montserrat", Font.PLAIN, 13));
        password.setPreferredSize(new Dimension(250, 35));
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 3;
        password.setLayout(new GridBagLayout());
        password.setCaretColor(new Color(142, 83, 233));
        password.add(btn, z);
        loginPanel.add(password, gbc);

        // Forget Password?
        JLabel label7 = new JLabel();
        label7.setForeground(new Color(142, 83, 233));
        label7.setFont(new Font("Montserrat", Font.PLAIN, 11));
        label7.setText("Forget Password?");
        gbc.insets = new Insets(5, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 4;
        loginPanel.add(label7, gbc);

        // Login Button
        AnimatedButton Login = new AnimatedButton("Login", 40,
                new Color(142, 83, 233),
                new Color(160, 120, 255),
                new Color(120, 60, 210),
                new Color(160, 120, 255));
        Login.setPreferredSize(new Dimension(220, 35));
        gbc.insets = new Insets(30, 20, 20, 12);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 5;

        Login.addActionListener(e -> {
            try {
                String usrname = username.getText();
                String pass = password.getText();
                DatabaseConfig.setConnection();
                Statement st = DatabaseConfig.getSt();
                ResultSet result = st.executeQuery("select username,confirm_pass from reg_users");

                boolean check = true;
                if (usrname.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username field can't be empty", "Warning", JOptionPane.WARNING_MESSAGE);
                    check = false;
                } else if (pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Password field can't be empty", "Warning", JOptionPane.WARNING_MESSAGE);
                    check = false;
                }

                boolean[] temp = {true};
                if (check) {
                    while (result.next()) {
                        if (usrname.equals(result.getString("username")) && pass.equals(result.getString("confirm_pass"))) {
                            int ok = JOptionPane.showConfirmDialog(null, "Login Successful", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                            if (ok == 0) {
                                dispose();
                                new MngMainFrame(this).createIt();
                            }
                            temp[0] = false;
                        }
                    }
                    if (temp[0]) {
                        JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                    username.setText("");
                    password.setText("");
                }
                DatabaseConfig.closeConnection();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        loginPanel.add(Login, gbc);

        // Register Panel Components
        RoundedPanel registerContentPanel = new RoundedPanel(30);
        registerContentPanel.setBackground(new Color(245, 240, 255));
        registerContentPanel.setLayout(new GridBagLayout());

        GridBagConstraints xyz = new GridBagConstraints();
        xyz.gridx = 0;
        xyz.gridy = 0;

        // Fullname
        JLabel register_fullname = new JLabel("Full Name");
        register_fullname.setForeground(Color.BLACK);
        register_fullname.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(1, 0, 7, 0);
        registerContentPanel.add(register_fullname, xyz);

        RoundedTextField fullname_textbox = new RoundedTextField("Enter your name", 10);
        fullname_textbox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        fullname_textbox.setPreferredSize(new Dimension(250, 32));
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 1;
        fullname_textbox.setCaretColor(new Color(142, 83, 233));
        registerContentPanel.add(fullname_textbox, xyz);

        // Username
        JLabel register_username = new JLabel("Username");
        register_username.setForeground(Color.BLACK);
        register_username.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(0, 0, 7, 0);
        xyz.gridy = 2;
        registerContentPanel.add(register_username, xyz);

        RoundedTextField username_textbox = new RoundedTextField("Enter a username", 10);
        username_textbox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        username_textbox.setPreferredSize(new Dimension(250, 32));
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 3;
        username_textbox.setCaretColor(new Color(142, 83, 233));
        registerContentPanel.add(username_textbox, xyz);

        // Email
        JLabel register_email = new JLabel("Email");
        register_email.setForeground(Color.BLACK);
        register_email.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(0, 0, 7, 0);
        xyz.gridy = 4;
        registerContentPanel.add(register_email, xyz);

        RoundedTextField email_textbox = new RoundedTextField("Enter a email", 10);
        email_textbox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        email_textbox.setPreferredSize(new Dimension(250, 32));
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 5;
        email_textbox.setCaretColor(new Color(142, 83, 233));
        registerContentPanel.add(email_textbox, xyz);

        // Password
        JLabel register_password = new JLabel("Password");
        register_password.setForeground(Color.BLACK);
        register_password.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(0, 0, 7, 0);
        xyz.gridy = 6;
        registerContentPanel.add(register_password, xyz);

        RoundedPasswordField password_textbox = new RoundedPasswordField("Enter a password", 10);
        password_textbox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        password_textbox.setPreferredSize(new Dimension(250, 32));
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 7;
        password_textbox.setCaretColor(new Color(142, 83, 233));
        registerContentPanel.add(password_textbox, xyz);

        // Confirm Password
        JLabel register_confirmPass = new JLabel("Confirm Password");
        register_confirmPass.setForeground(Color.BLACK);
        register_confirmPass.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(0, 0, 7, 0);
        xyz.gridy = 8;
        registerContentPanel.add(register_confirmPass, xyz);

        RoundedPasswordField confirmPass_textbox = new RoundedPasswordField("Enter confirm password", 10);
        confirmPass_textbox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        confirmPass_textbox.setPreferredSize(new Dimension(250, 32));
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 9;
        confirmPass_textbox.setCaretColor(new Color(142, 83, 233));
        registerContentPanel.add(confirmPass_textbox, xyz);

        // Role
        JLabel role = new JLabel("Role");
        role.setForeground(Color.BLACK);
        role.setFont(new Font("Montserrat", Font.PLAIN, 13));
        xyz.insets = new Insets(0, 0, 7, 0);
        xyz.gridy = 10;
        registerContentPanel.add(role, xyz);

        String[] roleOptions = {"Admin", "Staff"};
        RoundedComboBox roleComboBox = new RoundedComboBox(roleOptions, 8);
        roleComboBox.setFont(new Font("Montserrat", Font.PLAIN, 13));
        roleComboBox.setPreferredSize(new Dimension(250, 32));
        roleComboBox.setFocusable(false);
        roleComboBox.setBackground(Color.white);
        xyz.insets = new Insets(0, 0, 20, 0);
        xyz.gridy = 11;
        registerContentPanel.add(roleComboBox, xyz);

        // Register Button
        AnimatedButton regButton = new AnimatedButton("Register", 40,
                new Color(142, 83, 233),
                new Color(160, 120, 255),
                new Color(120, 60, 210),
                new Color(160, 120, 255));
        regButton.setPreferredSize(new Dimension(220, 35));
        xyz.insets = new Insets(10, 0, 1, 0);
        xyz.gridy = 12;
        registerContentPanel.add(regButton, xyz);

        regButton.addActionListener(e -> {
            String fullname = fullname_textbox.getText();
            String usrname = username_textbox.getText();
            String email = email_textbox.getText();
            String pas = password_textbox.getText();
            String pass = confirmPass_textbox.getText();
            String userRole = roleComboBox.getSelectedItem().toString();

            boolean[] empty = {true};
            if (fullname.isEmpty() || usrname.isEmpty() || email.isEmpty() || pas.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all required fields", "Warning", JOptionPane.WARNING_MESSAGE);
                empty[0] = false;
            } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(null, "Invalid Email Format", "Error", JOptionPane.ERROR_MESSAGE);
                empty[0] = false;
            } else if (!pas.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
                JOptionPane.showMessageDialog(null, "Password must contain:\n- At least 8 characters\n- 1 uppercase letter\n- 1 lowercase letter\n- 1 number\n- 1 special character", "Error", JOptionPane.ERROR_MESSAGE);
                empty[0] = false;
            } else if (!pas.equals(pass)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                empty[0] = false;
            }

            boolean[] temp = {true};
            try {
                DatabaseConfig.setConnection();
                Statement st = DatabaseConfig.getSt();
                ResultSet result = st.executeQuery("select username from reg_users");
                while (result.next()) {
                    if (usrname.equals(result.getString("username"))) {
                        JOptionPane.showMessageDialog(null, "This Username Already Exists", "Error Occur", JOptionPane.ERROR_MESSAGE);
                        temp[0] = false;
                    }
                }
                DatabaseConfig.closeConnection();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            if (temp[0] && empty[0]) {
                fullname_textbox.setText("");
                username_textbox.setText("");
                email_textbox.setText("");
                password_textbox.setText("");
                confirmPass_textbox.setText("");
                roleComboBox.setSelectedIndex(0);

                String query = String.format("Insert into reg_users(fullname,username,email,confirm_pass,role) values('%s','%s','%s','%s','%s')",
                        fullname, usrname, email, pass, userRole);
                try {
                    DatabaseConfig.setConnection();
                    Statement st = DatabaseConfig.getSt();
                    int resultAffect = st.executeUpdate(query);
                    if (resultAffect > 0) {
                        JOptionPane.showMessageDialog(null, "Data Inserted Successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Data not Inserted", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                    DatabaseConfig.closeConnection();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        RoundScrollPane registerPanel = new RoundScrollPane(registerContentPanel, 30);
        registerPanel.setBackground(new Color(245, 240, 255));
        registerPanel.setMaximumSize(new Dimension(330, 345));
        registerPanel.setMinimumSize(new Dimension(330, 345));
        registerPanel.setPreferredSize(new Dimension(330, 345));
        registerPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        registerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        registerPanel.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        registerPanel.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        registerPanel.setVisible(false);

        // Login and Register toggle buttons
        JButton login = new JButton("Login");
        JButton register = new JButton("Register");

        login.setForeground(new Color(142, 83, 233));
        login.setFont(new Font("Montserrat", Font.BOLD, 11));
        login.setMargin(new Insets(0, 0, 0, 0));
        login.setContentAreaFilled(false);
        login.setBorderPainted(false);
        login.setFocusable(false);
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginPanel.setVisible(true);
                registerPanel.setVisible(false);
                login.setForeground(new Color(142, 83, 233));
                register.setForeground(new Color(38, 38, 38));
            }
        });

        register.setForeground(new Color(38, 38, 38));
        register.setFont(new Font("Montserrat", Font.BOLD, 11));
        register.setContentAreaFilled(false);
        register.setBorderPainted(false);
        register.setFocusable(false);
        register.setCursor(new Cursor(Cursor.HAND_CURSOR));
        register.setMargin(new Insets(0, 0, 0, 0));
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                loginPanel.setVisible(false);
                registerPanel.setVisible(true);
                login.setForeground(new Color(38, 38, 38));
                register.setForeground(new Color(142, 83, 233));
            }
        });

        RoundedPanel smallPanel = new RoundedPanel(10);
        smallPanel.setBackground(new Color(235, 225, 255));
        smallPanel.setMaximumSize(new Dimension(120, 30));
        smallPanel.setLayout(new GridBagLayout());
        smallPanel.add(login);
        GridBagConstraints a = new GridBagConstraints();
        a.insets = new Insets(0, 6, 0, 0);
        smallPanel.add(register, a);

        // Main Panel
        JLabel SMS = new JLabel();
        SMS.setForeground(new Color(43, 45, 47));
        SMS.setFont(new Font("Aptos Black", Font.BOLD, 21));
        SMS.setText("STUDENT MANAGEMENT SYSTEM");
        SMS.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel SLP = new JLabel();
        SLP.setForeground(new Color(43, 45, 47));
        SLP.setFont(new Font("Montserrat", Font.PLAIN, 14));
        SLP.setText("Secure Login Portal");
        SLP.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedPanel mainPanel = new RoundedPanel(30);
        mainPanel.setBackground(Color.white);
        mainPanel.setPreferredSize(new Dimension(400, 500));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalStrut(28));
        mainPanel.add(SMS);
        mainPanel.add(SLP);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(smallPanel);
        mainPanel.add(Box.createVerticalStrut(-15));
        mainPanel.add(loginPanel);
        mainPanel.add(registerPanel);

        ImageIcon icon = new ImageIcon("F:\\University\\Semester 2\\Database\\DBMS Project\\src\\sms.png");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        setTitle("Student Management System");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(142, 83, 233));
        setIconImage(icon.getImage());
        add(mainPanel);
        setVisible(true);
    }
}