package Page;

import Components.Button;
import Components.Input;
import Components.Table;
import Components.Label;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame {
    private JTextField inputField;
    private JTable multiSelectTable;
    private DefaultTableModel tableModel;

    public Test() {
        setTitle("Input and Table Multi-Select");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);

        JLabel inputLabel = new Label("Set Event Name :");

        // Input Field
        inputField = new Input();
        // Table Model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Name");

        for (int i = 0; i < 10; i++) {
            tableModel.addRow(new Object[] {
                    i,
                    "Name " + i
            });
        }

        JLabel tableLabel = new Label("Select Candidates from the table");

        // Multi-Select Table
        multiSelectTable = new Table(tableModel);
        multiSelectTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        multiSelectTable.setFillsViewportHeight(true);

        // Button
        Button submitButton = new Button("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                int[] selectedRows = multiSelectTable.getSelectedRows();

                // Perform desired action with the input and selected rows
                System.out.println("Input Text: " + inputText);
                System.out.println("Selected Rows:");
                for (int row : selectedRows) {
                    System.out.println("ID: " + tableModel.getValueAt(row, 0) +
                            ", Name: " + tableModel.getValueAt(row, 1) +
                            ", Email: " + tableModel.getValueAt(row, 2));
                }
            }
        });

        // Panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 0, 10));
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(tableLabel);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, -10, 10, 0));

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(multiSelectTable), BorderLayout.CENTER);

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new GridLayout(2, 1, 0, 10));
        submitPanel.add(new JLabel());
        submitPanel.add(submitButton);
        panel.add(submitPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Test();
            }
        });
    }
}
