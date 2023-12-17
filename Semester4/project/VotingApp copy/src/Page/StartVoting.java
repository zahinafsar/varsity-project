package Page;

import Components.Frame;
import Database.CandidateDB;
import Database.EventDB;
import Database.VoteDB;
import Model.Candidate;
import Components.Button;
import Utils.RandomAvatar;

import Model.Event;
import Model.Vote;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;

public class StartVoting {

    private String selectedCandidateId = "";

    public StartVoting(String id) throws MalformedURLException {
        new Frame();
        JFrame frame = Frame.screen;

        Event event = EventDB.getEvent(id);

        JPanel candidatesPanel = new JPanel(new GridLayout(1, 4, 20, 50));

        for (int i = 0; i < event.getCandidates().length; i++) {
            Candidate candidate = CandidateDB.getCandidate(event.getCandidates()[i]);
            if (candidate != null) {
                JPanel candidatePanel = generateCandidate(candidate);
                candidatesPanel.add(candidatePanel);
                candidatesPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
                candidatePanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        selectedCandidateId = candidate.getId();
                        for (Component component : candidatesPanel.getComponents()) {
                            if (component instanceof JPanel) {
                                JPanel panel = (JPanel) component;
                                panel.setBorder(BorderFactory.createLineBorder(Color.decode("#c1c1c1"), 2));
                            }
                        }
                        candidatePanel.setBorder(BorderFactory.createLineBorder(Color.decode("#00aa00"), 3));
                    }
                });
            }
        }

        JPanel actionPanel = new JPanel(new FlowLayout());
        JPanel formPanel = new JPanel(new FlowLayout());

        JLabel inputLabel = new JLabel("Voter Code ");
        inputLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JTextField textField = new JTextField(20);
        textField.setBorder(new EmptyBorder(12, 12, 12, 12));

        JButton submitButton = new Button("Add Vote");
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String idInput = textField.getText();
                if (selectedCandidateId == "") {
                    JOptionPane.showMessageDialog(frame, "Please select a candidate");
                    return;
                } else if (idInput.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter your Voter Code");
                    return;
                } else {
                    boolean isValidVote = VoteDB.idValidVote(idInput, id);
                    boolean isValidCode = VoteDB.idValidCode(idInput);
                    if (!isValidCode) {
                        JOptionPane.showMessageDialog(frame, "Invalid Voter Code");
                        return;
                    } else if (!isValidVote) {
                        JOptionPane.showMessageDialog(frame, "You have already voted for this event");
                        return;
                    } else {
                        VoteDB.addVote(new Vote(selectedCandidateId, idInput, id));
                        JOptionPane.showMessageDialog(frame, "Vote added successfully");
                        textField.setText("");
                        selectedCandidateId = "";
                        for (Component component : candidatesPanel.getComponents()) {
                            if (component instanceof JPanel) {
                                JPanel panel = (JPanel) component;
                                panel.setBorder(BorderFactory.createLineBorder(Color.decode("#c1c1c1"), 2));
                            }
                        }
                    }
                }
            }
        });

        submitButton.setMargin(new Insets(12, 12, 12, 12));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 14));

        formPanel.add(inputLabel);
        formPanel.add(textField);
        formPanel.add(submitButton);

        JButton finishButton = new Button("Result");
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Result(id);
            }
        });
        JButton backButton = new Button("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AllEvent();
            }
        });

        actionPanel.add(backButton);
        actionPanel.add(finishButton);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        contentPane.setLayout(new BorderLayout());
        contentPane.add(candidatesPanel, BorderLayout.NORTH);
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(actionPanel, BorderLayout.SOUTH);
        frame.add(contentPane);

        frame.setVisible(true);
    }

    public JPanel generateCandidate(Candidate candidate) {
        JPanel candidatePanel = new JPanel(new GridLayout(2, 1));
        candidatePanel.setBackground(Color.WHITE);
        candidatePanel.setBorder(BorderFactory.createLineBorder(Color.decode("#c1c1c1"), 2));
        JLabel label = new JLabel(candidate.getName());
        label.setHorizontalAlignment(JLabel.CENTER);
        // ImageIcon realImageIcon = new ImageIcon(candidate.getImage());
        ImageIcon realImageIcon = new ImageIcon("src/Assets/" + candidate.getImage() + ".png");
        Image resizedImageIcon = realImageIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT);
        ImageIcon resizedImage = new ImageIcon(resizedImageIcon);
        JLabel imageLabel = new JLabel(resizedImage);
        imageLabel.setBorder(new EmptyBorder(30, 0, 0, 0));
        candidatePanel.add(imageLabel);
        candidatePanel.add(label);
        return candidatePanel;
    }
}
