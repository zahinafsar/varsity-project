package Page;

import Components.Frame;
import Model.Candidate;
import Components.Button;
import Utils.RandomAvatar;

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

    public StartVoting() throws MalformedURLException {
        new Frame();
        JFrame frame = Frame.screen;

        RandomAvatar randomAvatar = new RandomAvatar();

        Candidate[] candidates = {
                new Candidate("34d523433cq34c", "Zahin Afsar", randomAvatar.getAvatar()),
                new Candidate("5fwywyvw5ycw4t", "Nurul Huda", randomAvatar.getAvatar()),
                new Candidate("d43534ct34tcww", "Jubayer Al Mamun", randomAvatar.getAvatar()),
                new Candidate("d43534ct34tcww", "Shamim", randomAvatar.getAvatar())
        };

        JPanel candidatesPanel = new JPanel(new GridLayout(1, 4, 50, 50));
        JPanel actionPanel = new JPanel(new FlowLayout());

        for (Candidate candidate : candidates) {
            JPanel candidatePanel = this.generateCandidate(candidate);
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

        JPanel formPanel = new JPanel(new FlowLayout());

        JLabel inputLabel = new JLabel("Voter ID ");
        inputLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JTextField textField = new JTextField(20);
        textField.setBorder(new EmptyBorder(12, 12, 12, 12));

        JButton submitButton = new Button("Add Vote");
        submitButton.setMargin(new Insets(12, 12, 12, 12));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 14));

        formPanel.add(inputLabel);
        formPanel.add(textField);
        formPanel.add(submitButton);

        JButton finishButton = new Button("Finish");
        finishButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu();
            }
        });
        JButton backButton = new Button("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu();
            }
        });
        actionPanel.add(backButton);
        actionPanel.add(finishButton);

        Container contentPane = frame
                .getContentPane();
        contentPane.setLayout(new GridLayout(3, 1, 10, 10));
        contentPane.add(candidatesPanel);
        contentPane.add(formPanel);
        contentPane.add(actionPanel);

        frame.setVisible(true);
    }

    public JPanel generateCandidate(Candidate candidate) {
        JPanel candidatePanel = new JPanel(new GridLayout(2, 1));
        candidatePanel.setBackground(Color.WHITE);
        candidatePanel.setBorder(BorderFactory.createLineBorder(Color.decode("#c1c1c1"), 2));
        JLabel label = new JLabel(candidate.getName());
        label.setHorizontalAlignment(JLabel.CENTER);
        // String url = randomAvatar.getAvatar();
        ImageIcon realImageIcon = new ImageIcon(candidate.getImage());
        Image resizedImageIcon = realImageIcon.getImage().getScaledInstance(50, 50,
                java.awt.Image.SCALE_DEFAULT);
        ImageIcon resizedImage = new ImageIcon(resizedImageIcon);
        JLabel imageLabel = new JLabel(resizedImage);
        imageLabel.setBorder(new EmptyBorder(30, 0, 0, 0));
        candidatePanel.add(imageLabel);
        candidatePanel.add(label);
        return candidatePanel;
    }
}
