package Page;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Components.Frame;
import Components.Button;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome {

    public Welcome() {
        new Frame();
        JFrame frame = Frame.screen;

        frame.setLayout(new BorderLayout());

        JPanel bodyPanel = new JPanel();
        JPanel contentPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        FlowLayout bodLayout = new FlowLayout(FlowLayout.CENTER, 10, 50);
        BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
        FlowLayout buttonLayout = new FlowLayout(FlowLayout.CENTER, 0, 10);

        bodyPanel.setLayout(bodLayout);
        contentPanel.setLayout(contentLayout);
        buttonPanel.setLayout(buttonLayout);

        // welcome label
        JLabel welcomeLabel = new JLabel("Welcome to JVote app!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        welcomeLabel.setBorder(new EmptyBorder(0, 0, 50, 0));

        // next button
        JButton nextButton = new Button("Open Menu");
        nextButton.setMargin(new Insets(20, 20, 20, 20));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu();
            }
        });
        // Button nextButton = new Button("Next");

        contentPanel.add(welcomeLabel, contentPanel, 0);
        buttonPanel.add(nextButton, buttonPanel, 0);
        contentPanel.add(buttonPanel, contentPanel, 1);

        bodyPanel.add(contentPanel);

        frame.add(bodyPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JLabel creditLabel = new JLabel("Developed by Tanjila Akter");
        creditLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        footerPanel.add(creditLabel);
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
