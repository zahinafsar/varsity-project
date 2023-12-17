package Page;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Components.CenteredImage;
import Components.Frame;

public class Menu {

    public Menu() {
        new Frame();
        JFrame frame = Frame.screen;

        JPanel menuPanel = new JPanel(new GridLayout(2, 2));
        menuPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JPanel button1 = this.createMenuBox("src/Assets/image/vote.png", "Create Event");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new CreateEvent();
            }
        });

        JPanel button2 = this.createMenuBox("src/Assets/image/all.png", "All Event");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new AllEvent();
            }
        });

        JPanel button3 = this.createMenuBox("src/Assets/image/candidate.png", "Register Candidate");
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new RegisterCandidate();
            }
        });

        JPanel button4 = this.createMenuBox("src/Assets/image/user.png", "Register User");
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new RegisterUser();
            }
        });

        menuPanel.add(button1);
        menuPanel.add(button2);
        menuPanel.add(button3);
        menuPanel.add(button4);

        frame.add(menuPanel);

        frame.setVisible(true);
    }

    public JPanel createMenuBox(String image, String title) {
        JPanel cardPanel = new JPanel(new GridLayout(3, 1));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#c1c1c1"), 2));
        cardPanel.setPreferredSize(new Dimension(300, 300));
        ImageIcon cImage = new ImageIcon(image);
        Image resizedImageIcon = cImage.getImage().getScaledInstance(100, 100,
                java.awt.Image.SCALE_DEFAULT);
        ImageIcon resizedImage = new ImageIcon(resizedImageIcon);
        JPanel panel = new CenteredImage(resizedImage);
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        cardPanel.add(new JLabel());
        cardPanel.add(panel);
        cardPanel.add(label);
        return cardPanel;
    }
}
