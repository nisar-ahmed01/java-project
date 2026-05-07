package database;

import java.sql.*;

public class DatabaseConfig{
    public static final String URL = "jdbc:mysql://localhost:3306/SMS";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Nisar123";

    static Connection con;
    static Statement st;

    public static void setConnection(){
        try {
            con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            st = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Statement getSt(){
        return st;
    }

    public static void closeConnection(){
        try {
            st.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Connection is not closed");
        }
    }

}