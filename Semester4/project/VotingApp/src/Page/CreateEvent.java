package Page;

import Components.Button;
import Components.Input;
import Components.Table;
import Database.CandidateDB;
import Database.EventDB;
import Model.Candidate;
import Model.Event;
import Components.Label;
import Components.Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateEvent {
    private JTextField inputField;
    private JTable multiSelectTable;
    private DefaultTableModel tableModel;

    public CreateEvent() {
        new Frame();
        JFrame frame = Frame.screen;

        JLabel inputLabel = new Label("Set Event Name :");

        // Input Field
        inputField = new Input();
        // Table Model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Name");

        for (int i = 0; i < CandidateDB.userCount; i++) {
            Candidate user = CandidateDB.users[i];
            tableModel.addRow(new Object[] {
                    user.getId(),
                    user.getName()
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

                String[] selectedCandidates = new String[selectedRows.length];
                for (int i = 0; i < selectedRows.length; i++) {
                    selectedCandidates[i] = tableModel.getValueAt(selectedRows[i], 0).toString();
                }

                String newId = EventDB.getNextId();
                Event event = new Event(newId, inputText, selectedCandidates);
                EventDB.addEvent(event);

                new Menu();
            }
        });

        Button backButton = new Button("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu();
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
        submitPanel.setLayout(new GridLayout(2, 2, 0, 10));
        submitPanel.add(new JLabel());
        submitPanel.add(new JLabel());
        submitPanel.add(backButton);
        submitPanel.add(submitButton);
        panel.add(submitPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
