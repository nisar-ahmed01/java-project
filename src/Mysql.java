import java.sql.*;
public class Mysql{
public static void main(String[] args) throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/scnew","root","Nisar123");
    Statement statement = connection.createStatement();
    int cms = 202312;
    String name = "Amanullah";
    String country = "Pakistan";
    int age = 21;
    int score = 99;
    String query = String.format("Insert into tab(cms,name,country,age,score) values(%d,'%s','%s',%d,%d)",cms,name,country,age,score);
    int resultAffected = statement.executeUpdate(query);
    if(resultAffected>0){
        System.out.println("Data inserted Successfully");
    }
    else
        System.out.println("Ohhhh");
    connection.close();
    }
}

