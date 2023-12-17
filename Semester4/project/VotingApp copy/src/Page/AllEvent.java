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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllEvent {
    private JTable table;
    private DefaultTableModel tableModel;

    public AllEvent() {
        new Frame();
        JFrame frame = Frame.screen;

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Name");

        // for (int i = 0; i < EventDB.eventCount; i++) {
        // Event user = EventDB.events[i];
        // tableModel.addRow(new Object[] {
        // user.getId(),
        // user.getName()
        // });
        // }

        loadData();

        table = new Table(tableModel);

        Button submitButton = new Button("Open");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(frame, "Please select a row");
                        return;
                    } else {
                        Object idValue = (Integer) tableModel.getValueAt(selectedRow, 0);
                        String id = String.valueOf(idValue);
                        new StartVoting(id);
                    }
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Button backButton = new Button("Back");
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new Label("Events List");
        title.setBorder(new EmptyBorder(10, 10, 20, 10));
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(title, BorderLayout.NORTH);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

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

    private void loadData() {
        tableModel.setRowCount(0);
        ResultSet rs = EventDB.getEvents();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                tableModel.addRow(new Object[] {
                        id,
                        name,
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
