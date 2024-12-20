package Page;

import Components.Frame;
import Components.Input;
import Components.Label;
import Database.UserDB;
import Model.Vote;
import Model.Voter;
import Components.Button;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterUser {

    private DefaultTableModel tableModel;
    private JTable table;
    private Button backButton;
    private Button addButton;
    private Button deleteButton;
    private Button editButton;

    public RegisterUser() {
        new Frame();
        JFrame frame = Frame.screen;

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Name");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Voter Code");

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
                addUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedUser();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedUser();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JLabel title = new Label("User List");
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
        ResultSet rs = UserDB.getVoters();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String code = rs.getString("code");
                tableModel.addRow(new Object[] {
                        id,
                        name,
                        phone,
                        code
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addUser() {
        Input Name = new Input();
        Input Phone = new Input();

        Object[] message = {
                "Voter Name:", Name,
                "Phone Number:", Phone,
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add User",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String name = Name.getText();
            String phone = Phone.getText();

            String newId = UserDB.getNextId();
            Voter user = new Voter(newId, name, "", phone);
            UserDB.addVoter(user);
            loadData();
        }
    }

    private void editSelectedUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Object idValue = (Integer) tableModel.getValueAt(selectedRow, 0);
            Object nameValue = tableModel.getValueAt(selectedRow, 1);
            Object phoneValue = tableModel.getValueAt(selectedRow, 2);

            String id = String.valueOf(idValue);
            String name = String.valueOf(nameValue);
            String phone = String.valueOf(phoneValue);

            Input NameField = new Input(name);
            Input PhoneField = new Input(phone);

            Object[] message = {
                    "Voter Name:", NameField,
                    "Phone Number:", PhoneField,
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Edit User", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String newName = NameField.getText();
                String newPhone = PhoneField.getText();

                UserDB.updateVoter(id, newName, newPhone);

                loadData();
            }
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Object idValue = (Integer) tableModel.getValueAt(selectedRow, 0);
            String id = String.valueOf(idValue);
            int option = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this user?", "Delete User",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                UserDB.deleteVoter(id);
                loadData();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegisterUser();
            }
        });
    }
}
