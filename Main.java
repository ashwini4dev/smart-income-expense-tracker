public class Main {
    public static void main(String[] args) {
        // Initialize Database Table
        //DBConnection.createTable();

        // Launch GUI
        javax.swing.SwingUtilities.invokeLater(() -> {
            new FinanceGUI();
        });
    }
}