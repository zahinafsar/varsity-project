package Page;

import Components.Button;
import Components.Input;
import Components.Table;
import Database.CandidateDB;
import Database.EventDB;
import Database.VoteDB;
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

public class Result {
    private JTable table;
    private DefaultTableModel tableModel;

    public Result(String id) {
        new Frame();
        JFrame frame = Frame.screen;

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("No Of Votes");

        String[] candidates = EventDB.getEvent(id).getCandidates();
        for (int i = 0; i < candidates.length; i++) {
            String candidateId = candidates[i];
            int voteCount = VoteDB.getNumberOfVotes(candidateId, id);
            Candidate candidate = CandidateDB.getCandidate(candidateId);
            tableModel.addRow(new Object[] {
                    candidate.getName(),
                    voteCount
            });
        }

        table = new Table(tableModel);

        Button backButton = new Button("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new StartVoting(id);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new Label("Event Result");
        title.setBorder(new EmptyBorder(10, 10, 20, 10));
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(title, BorderLayout.NORTH);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new GridLayout(2, 1, 0, 10));
        submitPanel.add(new JLabel());
        submitPanel.add(backButton);
        panel.add(submitPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
