package Page;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Components.CenteredImage;
import Components.Frame;

public class Menu extends JFrame {

    public Menu() {
        new Frame();
        JFrame frame = Frame.screen;

        JPanel menuPanel = new JPanel(new GridLayout(2, 2));
        menuPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JPanel button1 = this.createMenuBox("./Assets/image/vote.png", "Start Voting");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    new StartVoting();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JPanel button2 = this.createMenuBox("./Assets/2.png", "Button 2");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new Welcome();
            }
        });
        JPanel button3 = this.createMenuBox("./Assets/3.png", "Button 3");
        JPanel button4 = this.createMenuBox("./Assets/4.png", "Button 4");

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
        label.setHorizontalAlignment(JLabel.CENTER);
        cardPanel.add(new JLabel());
        cardPanel.add(panel);
        cardPanel.add(label);
        return cardPanel;
    }
}
