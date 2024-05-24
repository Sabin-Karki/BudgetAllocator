import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class BudgetAllocator extends JFrame {
    private JTextField incomeField;
    private JTextField expensesField;
    private JTextField savingsGoalField;
    private JTextField currentSavingsField;
    private JTextArea resultArea;
    private JButton saveButton;
    private JButton loadButton;

    public BudgetAllocator() {
        setTitle("Personal Budget Allocator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel incomeLabel = new JLabel("Income:");
        incomeLabel.setBounds(10, 20, 100, 25);
        panel.add(incomeLabel);

        incomeField = new JTextField();
        incomeField.setBounds(120, 20, 160, 25);
        panel.add(incomeField);

        JLabel expensesLabel = new JLabel("Expenses:");
        expensesLabel.setBounds(10, 60, 100, 25);
        panel.add(expensesLabel);

        expensesField = new JTextField();
        expensesField.setBounds(120, 60, 160, 25);
        panel.add(expensesField);

        JLabel savingsGoalLabel = new JLabel("Savings Goal:");
        savingsGoalLabel.setBounds(10, 100, 100, 25);
        panel.add(savingsGoalLabel);

        savingsGoalField = new JTextField();
        savingsGoalField.setBounds(120, 100, 160, 25);
        panel.add(savingsGoalField);

        JLabel currentSavingsLabel = new JLabel("Current Savings:");
        currentSavingsLabel.setBounds(10, 140, 100, 25);
        panel.add(currentSavingsLabel);

        currentSavingsField = new JTextField();
        currentSavingsField.setBounds(120, 140, 160, 25);
        panel.add(currentSavingsField);

        JButton calculateButton = new JButton("Calculate Budget");
        calculateButton.setBounds(10, 180, 160, 25);
        panel.add(calculateButton);

        saveButton = new JButton("Save Data");
        saveButton.setBounds(180, 180, 100, 25);
        panel.add(saveButton);

        loadButton = new JButton("Load Data");
        loadButton.setBounds(290, 180, 100, 25);
        panel.add(loadButton);

        resultArea = new JTextArea();
        resultArea.setBounds(10, 220, 460, 130);
        resultArea.setEditable(false);
        panel.add(resultArea);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBudget();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        add(panel);
    }

    private void calculateBudget() {
        try {
            double income = Double.parseDouble(incomeField.getText());
            double expenses = Double.parseDouble(expensesField.getText());
            double savingsGoal = Double.parseDouble(savingsGoalField.getText());
            double currentSavings = Double.parseDouble(currentSavingsField.getText());

            double remainingBudget = income - expenses;
            double remainingToSave = savingsGoal - currentSavings;

            String result = "Total Income: $" + income + "\n" +
                            "Total Expenses: $" + expenses + "\n" +
                            "Remaining Budget: $" + remainingBudget + "\n";

            if (remainingBudget >= 0) {
                result += "You're within your budget.\n";
            } else {
                result += "You've exceeded your budget. Consider reducing expenses.\n";
            }

            result += "Total Savings Goal: $" + savingsGoal + "\n" +
                      "Total Savings: $" + currentSavings + "\n" +
                      "Remaining to Save: $" + remainingToSave;

            resultArea.setText(result);
        } catch (NumberFormatException e) {
            resultArea.setText("Please enter valid numbers for all fields.");
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("budget_data.txt"))) {
            writer.write(incomeField.getText() + "\n");
            writer.write(expensesField.getText() + "\n");
            writer.write(savingsGoalField.getText() + "\n");
            writer.write(currentSavingsField.getText() + "\n");
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("budget_data.txt"))) {
            incomeField.setText(reader.readLine());
            expensesField.setText(reader.readLine());
            savingsGoalField.setText(reader.readLine());
            currentSavingsField.setText(reader.readLine());
            JOptionPane.showMessageDialog(this, "Data loaded successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BudgetAllocator().setVisible(true);
            }
        });
    }
}
