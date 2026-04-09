import java.sql.*;
import java.util.ArrayList;

public class FinanceManager {
    private ArrayList<Transaction> transactions = new ArrayList<>();

    // Constructor (NEW)
    public FinanceManager() {
        createTable();   // table create कर
        loadFromDB();    // data load कर
    }

    // Table create method (NEW)
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT," +
                "category TEXT," +
                "amount REAL)";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (Exception e) {
            System.out.println("Table Error: " + e.getMessage());
        }
    }

    // Add transaction
    public void addTransaction(Transaction t) {
        transactions.add(t);
        saveToDB(t);
    }

    // Save to DB (UPDATED - added null safety)
    private void saveToDB(Transaction t) {
        String sql = "INSERT INTO transactions(type, category, amount) VALUES(?,?,?)";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                System.out.println("DB connection failed ");
                return;
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, t.getType());
            pstmt.setString(2, t.getCategory());
            pstmt.setDouble(3, t.getAmount());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Save Error: " + e.getMessage());
        }
    }

    // Load data (UPDATED - safer)
    public void loadFromDB() {
        transactions.clear();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                System.out.println("DB connection failed ");
                return;
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getString("type"),
                        rs.getString("category"),
                        rs.getDouble("amount")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Load Error: " + e.getMessage());
        }
    }

    // Balance calculate
    public double getBalance() {
        double balance = 0;

        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Income"))
                balance += t.getAmount();
            else
                balance -= t.getAmount();
        }

        return balance;
    }

    // Get list
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}