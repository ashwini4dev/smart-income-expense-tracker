import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection connect() {
        Connection conn = null;
        try {
            // 1. Load the SQLite JDBC Driver
            Class.forName("org.sqlite.JDBC");

            // 2. Define the database file path
            // This will create a file named 'database.db' in your project folder
            String url = "jdbc:sqlite:database.db";

            // 3. Establish the connection
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (ClassNotFoundException e) {
            System.out.println("SQLite Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }

        return conn;
    }
}