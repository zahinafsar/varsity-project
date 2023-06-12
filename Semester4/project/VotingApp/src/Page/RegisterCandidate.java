package Page;

import Components.Frame;
import Components.Input;
import Components.Label;
import Database.CandidateDB;
import Database.UserDB;
import Model.Candidate;
import Components.Button;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterCandidate {

    private DefaultTableModel tableModel;
    private JTable table;
    private Button backButton;
    private Button addButton;
    private Button deleteButton;
    private Button editButton;

    public RegisterCandidate() {
        new Frame();
        JFrame frame = Frame.screen;

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Name");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Symbol");

        loadData();

        table = new JTable(tableModel);

        Font tableFont = table.getFont().deriveFont(18f);
        table.setFont(tableFont);
        Font headerFont = table.getTableHeader().getFont().deriveFont(Font.BOLD, 18f);
        table.getTableHeader().setFont(headerFont);
        int rowHeight = 30;
        table.setRowHeight(rowHeight);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        backButton = new Button("Back");
        addButton = new Button("Add");
        deleteButton = new Button("Delete");
        editButton = new Button("Edit");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCandidate();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedCandidate();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedCandidate();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JLabel title = new Label("Candidate List");
        title.setBorder(new EmptyBorder(10, 10, 20, 10));
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(title, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);

        frame.setVisible(true);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        ResultSet rs = CandidateDB.getCandidates();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String image = rs.getString("image");
                tableModel.addRow(new Object[] {
                        id,
                        name,
                        phone,
                        image
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCandidate() {
        Input Name = new Input();
        Input Phone = new Input();
        Input Symbol = new Input();

        Object[] message = {
                "Candidate Name:", Name,
                "Phone Number:", Phone,
                "Symbol:", Symbol,
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add Candidate",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String name = Name.getText();
            String phone = Phone.getText();
            String symbol = Symbol.getText();

            String newId = CandidateDB.getNextId();
            Candidate user = new Candidate(newId, name, symbol, phone);
            CandidateDB.addCandidate(user);

            loadData();

            // Object[] rowData = { newId, name, phone, symbol };
            // tableModel.addRow(rowData);
        }
    }

    private void editSelectedCandidate() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Object idValue = (Integer) tableModel.getValueAt(selectedRow, 0);
            Object nameValue = tableModel.getValueAt(selectedRow, 1);
            Object phoneValue = tableModel.getValueAt(selectedRow, 2);
            Object symbolValue = tableModel.getValueAt(selectedRow, 3);

            String id = String.valueOf(idValue);
            String name = String.valueOf(nameValue);
            String phone = String.valueOf(phoneValue);
            String symbol = String.valueOf(symbolValue);

            Input NameField = new Input(name);
            Input PhoneField = new Input(phone);
            Input SymbolField = new Input(symbol);

            Object[] message = {
                    "Candidate Name:", NameField,
                    "Phone Number:", PhoneField,
                    "Symbol:", SymbolField,
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Edit Candidate",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String newName = NameField.getText();
                String newPhone = PhoneField.getText();
                String newSymbol = SymbolField.getText();

                CandidateDB.updateCandidate(id, newName, newPhone, newSymbol);

                loadData();
                // tableModel.setValueAt(newName, selectedRow, 1);
                // tableModel.setValueAt(newPhone, selectedRow, 2);
                // tableModel.setValueAt(newSymbol, selectedRow, 3);
            }
        }
    }

    private void deleteSelectedCandidate() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            int option = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this Candidate?", "Delete Candidate",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                CandidateDB.deleteCandidate(id);
                loadData();
                // tableModel.removeRow(selectedRow);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegisterCandidate();
            }
        });
    }
}
