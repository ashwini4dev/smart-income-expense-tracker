import javax.swing.*;
import java.awt.*;

public class FinanceGUI extends JFrame {
    private FinanceManager manager = new FinanceManager();
    private JTextField catField = new JTextField(10);
    private JTextField amtField = new JTextField(10);
    private JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Income", "Expense"});
    private JTextArea display = new JTextArea(12, 35);
    private JLabel balanceLabel = new JLabel("Total Balance: ₹0.0");

    public FinanceGUI() {
        manager.loadFromDB();
        setTitle("Smart Income & Expense Manager");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeCombo);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(catField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amtField);

        JButton addBtn = new JButton("Add Transaction");
        addBtn.addActionListener(e -> {
            try {
                String type = (String) typeCombo.getSelectedItem();
                String cat = catField.getText();
                double amt = Double.parseDouble(amtField.getText());
                manager.addTransaction(new Transaction(type, cat, amt));
                updateUI();
                catField.setText(""); amtField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        add(inputPanel);
        add(addBtn);
        add(new JScrollPane(display));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(balanceLabel);

        updateUI();
        setVisible(true);
    }

    private void updateUI() {
        display.setText("Type\tCategory\tAmount\n");
        display.append("----------------------------------------------------------\n");
        for (Transaction t : manager.getTransactions()) {
            display.append(t.getType() + "\t" + t.getCategory() + "\t₹" + t.getAmount() + "\n");
        }
        double bal = manager.getBalance();
        balanceLabel.setText("Total Balance: ₹" + bal);
        balanceLabel.setForeground(bal < 0 ? Color.RED : new Color(0, 150, 0));
    }
}