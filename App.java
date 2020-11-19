import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
 
public class App {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://Full_2020_086303:3306/AJarszak";
 
    static final String USER = "root";
    static final String PASS = "root";
 
    static Scanner in = new Scanner( System.in);
    private static final String CREATE_TABLE_Users = "CREATE TABLE IF NOT EXISTS Users (ID int, Imie varchar(255), Nazwisko varchar(255));";
    private static final String SELECT_ALL_FROM_Users = "SELECT ID, Imie, Nazwisko FROM Users";
 
    public static void main(String[] args) {
 
        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement()) {
            Class.forName("com.mysql.jdbc.Driver");
            TimeUnit.SECONDS.sleep(10);
            System.out.println(",,,");
            stmt.executeUpdate(CREATE_TABLE_Users);
            String selectedOperation;
            do
            {
                System.out.println("1.Wyświetl użytkowników");
        	System.out.println("2.Dodaj nowego użytkownika");
        	System.out.println("3.Edytuj użytkownika");
        	System.out.println("4.Usuń użytkownika");
        	System.out.println("5.Wyjdź");
                selectedOperation = in.nextLine();
                switch( selectedOperation )
                {
                    case "1" :
                        getResults(stmt);
                        break;
                    case "2" :
                        insertUser(stmt);
                        break;
                    case "3" :
                        updateUser(stmt);
                        break;
                    case "4" :
                        deleteUserById(stmt);
                        break;
                }
            }while (!selectedOperation.toUpperCase().equals("E"));
        } catch (InterruptedException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
 
    private static void deleteUserById(Statement stmt) throws SQLException {
        ResultSet rsss = stmt.executeQuery(SELECT_ALL_FROM_Users);
        printOutHeader();
        printOutResult(rsss);
        rsss.close();
        System.out.println("Wprowadz ID uzytkownika do usuniecia");
        final String id = in.nextLine();
        final String deleteSql = " DELETE FROM Users WHERE ID= '"+id+"';";
        stmt.executeUpdate(deleteSql);
    }
 
    private static void updateUser(Statement stmt) throws SQLException {
        String id;
        String name;
        String lastname;
        String sql;
        ResultSet rss = stmt.executeQuery(SELECT_ALL_FROM_Users);
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
        stmt.executeUpdate(sql);
    }
 
    private static void insertUser(Statement stmt) throws SQLException {
        System.out.println("ID");
        final String id = in.nextLine();
 
        System.out.println("Name:");
        final String name = in.nextLine();
 
        System.out.println("Last name:");
        final String lastname = in.nextLine();

 
        String sql = " INSERT INTO Users (ID, Imie, Nazwisko) VALUES ('"+id+"', '"+name+"', '"+lastname+"')";
        stmt.executeUpdate(sql);
    }
 
    private static void getResults(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery(SELECT_ALL_FROM_Users);
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
