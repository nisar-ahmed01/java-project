import java.sql.*;
import java.util.Scanner;

interface DbSetup {
    public void doSetup();
    public void doRelease();
}

interface TblDepartments {
    public void insertDepartment();
    public void updateDepartment();
    public void removeDepartment();
    public void displayDepartments();
}

class Departments implements DbSetup, TblDepartments {
    private String url = "jdbc:mysql://localhost:3306/OopAssignment2";
    private String username = "root";
    private String password = "Nisar123";
    Scanner sc = new Scanner(System.in);

    Connection con;
    Statement st;
    String query;

    public void doSetup() {
        try {
            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("Database Not Connected");
        }
    }

    public void doRelease() {
        try {
            st.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Connection not closed yet");
        }
    }

    public void insertDepartment() {
        try {
            System.out.print("\nEnter Department Name: ");
            String deptName = sc.nextLine();
            query = String.format("Insert into department values('%s')", deptName);
            int result = st.executeUpdate(query);
            if (result > 0)
                System.out.println("Data Inserted Successfully\n");
            else
                System.out.println("Error in Inserting Data");

        } catch (Exception e) {
            System.out.println(
                    "Error in Inserting Data " + e);
        }
    }

    public void updateDepartment() {

        try {
            System.out.print("Enter Old Department Name: ");
            String oldDept = sc.nextLine();
            System.out.print("Enter New Department Name: ");
            String newDept = sc.nextLine();
            query = String.format("Update department set dept='%s' where dept='%s'", newDept, oldDept);

            int result = st.executeUpdate(query);

            if (result > 0)
                System.out.println("Data Updated Successfully\n");
            else
                System.out.println("Error in Updating Data");

        } catch (Exception e) {
            System.out.println("Error in Updating Data");
        }
    }

    public void removeDepartment() {

        try {
            System.out.print("\nEnter Department Name: ");
            String deptName = sc.nextLine();
            query = String.format("delete from department where dept='%s'", deptName);

            int result = st.executeUpdate(query);

            if (result > 0)
                System.out.println("Data Deleted Successfully\n");
            else
                System.out.println("Error in Deleting Data");

        } catch (Exception e) {
            System.out.println("Error in Removing Data");
        }
    }

    public void displayDepartments() {
        try {
            query = "select * from department";
            ResultSet resultSet = st.executeQuery(query);

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No departments found!\n");
                return;
            }

            System.out.println("\nDEPARTMENT LIST:");
            System.out.println("==================");

            int i = 1;
            while (resultSet.next()) {
                System.out.println(i++ + ". " + resultSet.getString("dept"));
            }
            System.out.println("==================\n");

        } catch (Exception e) {
            System.out.println("Error in Displaying Data: " + e.getMessage());
        }
    }
}

class Assignment02 {

    public static void main(String[] args) {

        Departments dept = new Departments();
        Scanner sc = new Scanner(System.in);
        dept.doSetup();
        String run;

        do {

            System.out.println("Enter:");
            System.out.println("1. To Display Departments");
            System.out.println("2. To Insert Department");
            System.out.println("3. To Remove Department");
            System.out.println("4. To Update Department");
            System.out.println("5. To Exit");

            System.out.print("\nChoose a number: ");

            int input = sc.nextInt();
            sc.nextLine();

            switch (input) {

                case 1:
                    dept.displayDepartments();
                    break;

                case 2:
                    dept.insertDepartment();
                    break;

                case 3:
                    dept.removeDepartment();
                    break;

                case 4:
                    dept.updateDepartment();
                    break;

                case 5:
                    System.out.println("\n👋 Exiting program... Goodbye!");
                    dept.doRelease();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Input.");
            }

            System.out.print("Do you want to Continue(y/n): ");

            run = sc.nextLine();

            System.out.println();

        } while (run.equalsIgnoreCase("y"));
        dept.doRelease();
    }
}
