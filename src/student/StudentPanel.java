package student;
import components.*;
import dashboard.MngMainFrame;
import database.DatabaseConfig;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import static dashboard.MngMainFrame.*;
import static student.EditStudentPanel.*;
import static student.ViewStudentPanel.*;

public class StudentPanel extends JPanel{
    MngMainFrame frame;
    public static JTable table;
    public static DefaultTableModel model;


    public static void filterTable(String searchText, JTable table, DefaultTableModel model) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null); // Show all rows
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
        }
    }

    public StudentPanel(MngMainFrame frame){
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
        basicUi();
        studentTable();
    }



    void basicUi(){

            JLabel Student = new JLabel("Students");
            Student.setFont(new Font("Century Gothic", Font.BOLD, 33));
            Student.setForeground(new Color(33, 37, 40));
            Student.setBounds(20, 20, 200, 40);
            add(Student);


            ImageIcon searchIconImg = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\search.png", 26, 26);
            JButton searchIcon = new JButton();
            searchIcon.setLayout(null);
            searchIcon.setBounds(560, 9, 26, 26);
            searchIcon.setContentAreaFilled(false);
            searchIcon.setFocusable(false);
            searchIcon.setBorder(null);
            searchIcon.setCursor(new Cursor(12));
            searchIcon.setIcon(searchIconImg);

            RoundedTextField searchBar = new RoundedTextField("Search", 44
                    , new Color(240, 239, 255) //bg Color
                    , new Color(134, 73, 255) // normal border Color
                    , new Color(134, 73, 255)); // focused Border Color
            searchBar.setCaretColor(new Color(134, 73, 255));
            searchBar.setBounds(20, 13, 600, 44);
            searchBar.setFont(new Font("Montserrat", Font.PLAIN, 14));
            searchBar.setBackground(new Color(240, 239, 255));
            searchBar.add(searchIcon);
            searchIcon.addActionListener(e -> {
                String searchText = searchBar.getText();
                filterTable(searchText,table,model);
            });
            add(searchBar);


            JLabel plusIcon = new JLabel("+");
            plusIcon.setBounds(0, 18, 30, 30);
            plusIcon.setForeground(Color.WHITE);
            plusIcon.setFont(new Font("Montserrat", Font.PLAIN, 25));

            AnimatedButton addStudent = new AnimatedButton("     Add New Student", 20,
                    new Color(134, 73, 255),     // normal Color (purple)
                    new Color(110, 60, 220),     // Hovered Color (slightly darker purple)
                    new Color(90, 50, 190),      // Pressed Color (even darker purple)
                    new Color(134, 73, 255));
            addStudent.setBounds(830, 18, 180, 40);
            addStudent.setForeground(Color.WHITE);
            addStudent.setFont(new Font("Montserrat", Font.PLAIN, 14));
            addStudent.add(plusIcon);
            addStudent.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    addStudent.setBounds(830, 18, 180, 40);
                    addStudent.setBounds(830, 17, 180, 40);
                    addStudent.setBounds(830, 16, 180, 40);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    addStudent.setBounds(830, 16, 180, 40);
                    addStudent.setBounds(830, 17, 180, 40);
                    addStudent.setBounds(830, 18, 180, 40);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    setVisible(false);
                    addStudentPanel.visibility(true);
                }
            });
            add(addStudent);


            AnimatedButton viewStu = new AnimatedButton("👁️", 44,
                    new Color(148, 240, 151),   // Normal Color (light green)
                    new Color(126, 220, 129),   // Hover Color
                    new Color(104, 196, 107),   // Pressed Color
                    new Color(84, 176, 87));  // Border Color
            viewStu.setBounds(813, 13, 44, 44);
            viewStu.setFont(new Font("", Font.PLAIN, 25));
            viewStu.setMargin(new Insets(0, 0, 0, 0));
            viewStu.setFocusable(false);
            viewStu.setForeground(new Color(84, 176, 87));
            viewStu.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    viewStu.setBounds(813, 13, 44, 44);
                    viewStu.setBounds(813, 12, 44, 44);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    viewStu.setBounds(813, 12, 44, 44);
                    viewStu.setBounds(813, 13, 44, 44);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                if(table.getSelectedRow()>=0){
                    String studentId = table.getValueAt(table.getSelectedRow(),0).toString();
                    try {
                        DatabaseConfig.setConnection();
                        Statement st = DatabaseConfig.getSt();
                        String query = String.format("select * from NewStudent where stu_id = '%s'",studentId);
                        ResultSet result = st.executeQuery(query);
                        result.next();
                        id_stu_ans.setText(result.getString("stu_id"));
                        f_Name_ans.setText(result.getString("firstname") + " " + result.getString("lastname"));
                        e_address_ans .setText(result.getString("email"));
                        isActive_ans.setText("Yes");
                        phone_number_ans.setText(result.getString("phone"));
                        stu_age_ans.setText(result.getString("age"));
                        stu_gender_ans.setText(result.getString("gender"));
                        Address_ans.setText(result.getString("address"));


                        String query1 = String.format("select * from NewStudent where stu_id = '%s'", studentId);
                        ResultSet result1 = st.executeQuery(query1);
                        result1.next();
                        firstNameEditBox.setText(result1.getString("firstname"));
                        lastNameEditBox.setText(result1.getString("lastname"));
                        emailAddEditBox.setText(result1.getString("email"));
                        phoneEditBox.setText(result1.getString("phone"));
                        ageEditBox.setText(result1.getString("age"));
                        genderEditBox.setText(result1.getString("gender"));
                        addressEditBox.setText(result1.getString("address"));
                        result.close();
                        DatabaseConfig.closeConnection();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    setVisible(false);
                    viewStudentPanel.setVisible(true);
                }
                }
            });
        add(viewStu);


            ImageIcon editIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\edit.png", 20, 20);
            AnimatedButton editStu = new AnimatedButton(null, 44,
                    new Color(251, 219, 179),   // Normal Color (light peach)
                    new Color(235, 203, 163),   // Hover Color
                    new Color(210, 178, 138),   // Pressed Color
                    new Color(176, 144, 104));    // Border Color// Border Color (unchanged)
            editStu.setBounds(869, 13, 44, 44);
            editStu.setFont(new Font("", Font.BOLD, 19));
            editStu.setMargin(new Insets(0, 0, 0, 0));
            editStu.setFocusable(false);
            editStu.setLayout(new GridBagLayout());
            editStu.setIcon(editIcon);
            editStu.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    editStu.setBounds(869, 13, 44, 44);
                    editStu.setBounds(869, 12, 44, 44);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    editStu.setBounds(869, 12, 44, 44);
                    editStu.setBounds(869, 13, 44, 44);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                if(table.getSelectedRow()>=0) {
                    String studentId = table.getValueAt(table.getSelectedRow(), 0).toString();
                    try {
                        DatabaseConfig.setConnection();
                        Statement st = DatabaseConfig.getSt();
                        String query = String.format("select * from NewStudent where stu_id = '%s'", studentId);
                        ResultSet result = st.executeQuery(query);
                        result.next();
                        firstNameEditBox.setText(result.getString("firstname"));
                        lastNameEditBox.setText(result.getString("lastname"));
                        emailAddEditBox.setText(result.getString("email"));
                        phoneEditBox.setText(result.getString("phone"));
                        ageEditBox.setText(result.getString("age"));
                        genderEditBox.setText(result.getString("gender"));
                        addressEditBox.setText(result.getString("address"));
                        result.close();
                        DatabaseConfig.closeConnection();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    studentPanel.setVisible(false);
                    editStudentPanel.setVisible(true);
                }
                }
            });
            add(editStu);


            ImageIcon deleteIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\delete.png", 18, 18);
            AnimatedButton delStu = new AnimatedButton(null, 44,
                    new Color(165, 176, 254),   // Normal Color (light periwinkle blue)
                    new Color(145, 156, 234),   // Hover Color
                    new Color(130, 141, 219),   // Pressed Color
                    new Color(115, 126, 204));    // Border Color
            delStu.setBounds(924, 13, 44, 44);
            delStu.setFont(new Font("", Font.BOLD, 20));
            delStu.setMargin(new Insets(0, 0, 0, 0));
            delStu.setFocusable(false);
            delStu.setLayout(new GridBagLayout());
            delStu.setIcon(deleteIcon);
            delStu.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    delStu.setBounds(924, 13, 44, 44);
                    delStu.setBounds(924, 12, 44, 44);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    delStu.setBounds(924, 12, 44, 44);
                    delStu.setBounds(924, 13, 44, 44);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    int selectedRow = table.getSelectedRow();

                    if (selectedRow != -1) {
                        try {
                            DatabaseConfig.setConnection();
                            Statement st = DatabaseConfig.getSt();
                            String stu_id = model.getValueAt(selectedRow, 0).toString();
                            String query = String.format("Delete from NewStudent where stu_id = '%s'", stu_id);
                            st.executeUpdate(query);
                            DatabaseConfig.closeConnection();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        model.removeRow(selectedRow);
                        int rowCount = table.getRowCount();
                        if (rowCount > 0) {
                            if (selectedRow >= rowCount) {
                                selectedRow = rowCount - 1;
                            }
                            table.setRowSelectionInterval(selectedRow, selectedRow);
                            table.scrollRectToVisible(table.getCellRect(selectedRow, 0, true));
                        }
                    }
                }
            });
            add(delStu);


            RoundedPanel buttonBelowPanel = new RoundedPanel(20);
            buttonBelowPanel.setBackground(Color.WHITE);
            buttonBelowPanel.setBounds(20, 68, 990, 70);
            buttonBelowPanel.setLayout(null);
            buttonBelowPanel.add(viewStu);
            buttonBelowPanel.add(editStu);
            buttonBelowPanel.add(delStu);
            buttonBelowPanel.add(searchBar);
            add(buttonBelowPanel);
        }



    public void refreshStudentTable(){
        try{
            DatabaseConfig.setConnection();
            Statement st = DatabaseConfig.getSt();
            String query = "select stu_id,firstname,lastname,email,age,phone from NewStudent";
            ResultSet resultset = st.executeQuery(query);

            while(resultset.next()){
                String stu_id = resultset.getString("stu_id");
                String firstname = resultset.getString("firstname");
                String lastname = resultset.getString("lastname");
                String email = resultset.getString("email");
                String age = resultset.getString("age");
                String phone = resultset.getString("phone");
                Object[] newData = {stu_id,firstname+" "+lastname,email,age,phone};
                model.addRow(newData);
            }
            DatabaseConfig.closeConnection();
            dashboardPanel.refreshCounts();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void studentTable(){
        String[] columnNames = {"ID", "NAME", "EMAIL", "AGE", "PHONE"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);

        refreshStudentTable();

        table.setForeground(Color.GRAY);
        table.setShowGrid(false);
        table.setDefaultEditor(Object.class, null);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(170);  // Name
        table.getColumnModel().getColumn(2).setPreferredWidth(260);  // Email
        table.getColumnModel().getColumn(3).setPreferredWidth(100);  // Age
        table.getColumnModel().getColumn(4).setPreferredWidth(140);  // Phone
        table.setRowHeight(55);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setBorder(null);
        table.setIntercellSpacing(new Dimension(0, 0));

        final int[] hoverRow2 = {-1};
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());

                if (hoverRow2[0] != row) {
                    hoverRow2[0] = row;
                    table.repaint();
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoverRow2[0] = -1;
                table.repaint();
            }
        });

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
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
                } else if (row == hoverRow2[0]) {
                    setBackground(new Color(230, 230, 255, 100)); // hover color
                    setFont(new Font("Montserrat", Font.BOLD, 13));
                } else {

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
        JTableHeader TableHeader = table.getTableHeader();
        TableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
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


        RoundScrollPane studentData = new RoundScrollPane(table, 30);
        studentData.setBackground(Color.WHITE);
        studentData.setBounds(0, 40, 990, 400);
        studentData.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        studentData.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        studentData.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        studentData.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        //================== Student Data table Ends Here==================

        ImageIcon stuListIcon = SmoothImage.getResizedImage("F:\\\\University\\\\Semester 2\\\\Database\\\\DBMS Project\\\\src\\\\list.png", 20, 20);
        JLabel studentList = new JLabel("Student List");
        studentList.setBounds(40, 17, 200, 30);
        studentList.setFont(new Font("Montserrat", Font.BOLD, 14));
        studentList.setForeground(Color.darkGray);
        studentList.setIcon(stuListIcon);

        JLabel line = new JLabel("_____________________________________________________________" +
                "______________________________________________________________");
        line.setBounds(0, 43, 990, 20);
        line.setFont(new Font("Arial", Font.BOLD, 14));
        line.setForeground(new Color(240, 240, 240));
        line.setHorizontalAlignment(JLabel.CENTER);

        RoundedPanel tablePanel = new RoundedPanel(30);
        tablePanel.setBounds(20, 150, 990, 440);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setLayout(null);
        tablePanel.add(studentList);
        tablePanel.add(line);
        tablePanel.add(studentData);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tablePanel.setBounds(20, 150, 990, 440);
                tablePanel.setBounds(20, 149, 990, 440);
                tablePanel.setBounds(20, 148, 990, 440);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tablePanel.setBounds(20, 148, 990, 440);
                tablePanel.setBounds(20, 149, 990, 440);
                tablePanel.setBounds(20, 150, 990, 440);
            }
        });
        add(tablePanel);
    }

}