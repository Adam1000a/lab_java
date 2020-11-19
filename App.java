import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
 
public class App {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_CONNECTION = "jdbc:mysql://Full_2020_086303:3306/AJarszak"; 
    static final String USER = "root";
    static final String PASSWORD = "root";
 
    static Scanner in = new Scanner( System.in);
    private static final String CREATE_TABLE_Users = "CREATE TABLE IF NOT EXISTS Users (ID int, Imie varchar(255), Nazwisko varchar(255));";
    private static final String SELECT_ALL_FROM_Users = "SELECT ID, Imie, Nazwisko FROM Users";
 
    public static void main(String[] args) {
 
        try(Connection conn = DriverManager.getConnection(DB_CONNECTION,USER,PASSWORD);
            Statement statement1 = conn.createStatement()) {
            Class.forName("com.mysql.jdbc.Driver");
            TimeUnit.SECONDS.sleep(10);
            statement1.executeUpdate(CREATE_TABLE_Users);
            String selectedOperation;
            do
            {
                System.out.println("1.Wyświetl użytkowników");
        	       System.out.println("2.Dodaj nowego użytkownika");
        	       System.out.println("3.Edytuj użytkownika");
        	       System.out.println("4.Usuń użytkownika");
               	System.out.println("EXIT");
                selectedOperation = in.nextLine();
                switch( selectedOperation )
                {
                    case "1" :
                        getResults(statement1);
                        break;
                    case "2" :
                        insertUser(statement1);
                        break;
                    case "3" :
                        updateUser(statement1);
                        break;
                    case "4" :
                        deleteUserById(statement1);
                        break;
                }
            }while (!selectedOperation.toUpperCase().equals("EXIT"));
        } catch (InterruptedException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
 
    private static void deleteUserById(Statement statement1) throws SQLException {
        ResultSet rsss = statement1.executeQuery(SELECT_ALL_FROM_Users);
        printOutHeader();
        printOutResult(rsss);
        rsss.close();
        System.out.println("Wprowadz ID uzytkownika do usuniecia");
        final String id = in.nextLine();
        final String deleteSql = " DELETE FROM Users WHERE ID= '"+id+"';";
        statement1.executeUpdate(deleteSql);
    }
 
    private static void updateUser(Statement statement1) throws SQLException {
        String id;
        String name;
        String lastname;
        String sql;
        ResultSet rss = statement1.executeQuery(SELECT_ALL_FROM_Users);
        printOutHeader();
 
        printOutResult(rss);
        rss.close();
        System.out.println("Wprowadz ID uzytkownika do edycji");
        id = in.nextLine();
 
        System.out.println("Imie = ");
        name = in.nextLine();
 
        System.out.println("Nazwisko = ");
        lastname = in.nextLine();
 
        sql = " UPDATE Users SET Imie = '"+name+"' , Nazwisko = '"+lastname+"' WHERE ID= '"+id+"';";
        statement1.executeUpdate(sql);
    }
 
    private static void insertUser(Statement statement1) throws SQLException {
        System.out.println("ID");
        final String id = in.nextLine();
 
        System.out.println("Imie = ");
        final String name = in.nextLine();
 
        System.out.println("Nazwisko = ");
        final String lastname = in.nextLine();

 
        String sql = " INSERT INTO Users (ID, Imie, Nazwisko) VALUES ('"+id+"', '"+name+"', '"+lastname+"')";
        statement1.executeUpdate(sql);
    }
 
    private static void getResults(Statement statement1) throws SQLException {
        ResultSet rs = statement1.executeQuery(SELECT_ALL_FROM_Users);
        printOutHeader();
        printOutResult(rs);
        rs.close();
    }
 
    private static void printOutHeader() {
        System.out.println("ID | Imie | Nazwisko");
    }
 
    private static void printOutResult(ResultSet rs) throws SQLException {
        int id;
        String first;
        String last;
        while (rs.next()) {
            id = rs.getInt("ID");
            first = rs.getString("Imie");
            last = rs.getString("Nazwisko"); 
            System.out.println(id + "|" + first + "|" + last);
        }
    }
}
