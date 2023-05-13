package Page;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Components.Frame;
import Components.Input;
import Components.Button;
import Components.Label;
import Components.Screen;

public class RegisterUser extends Frame {
    private JLabel titleLabel, nameLabel, emailLabel, phoneLabel;
    private JTextField nameField, emailField, phoneField;
    private JButton submitButton, backButton;

    public RegisterUser() {
        JFrame screen = Screen.frame;
        Container frame = screen.getContentPane();

        // Center the frame on the screen
        // frame.setLocationRelativeTo(null);

        titleLabel = new Label("Register New Voter");
        titleLabel.setBorder(new EmptyBorder(10, 10, 50, 10));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create the form components
        nameLabel = new Label("Name:");
        nameField = new Input(20);

        emailLabel = new Label("Phone:");
        emailField = new Input(20);

        phoneLabel = new Label("New generated Voter Id:");
        phoneField = new Input(20);

        backButton = new Button("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new Menu();
            }
        });
        submitButton = new Button("Submit");

        // Create the form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2));
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());
        formPanel.add(backButton);
        formPanel.add(submitButton);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.add(titleLabel, BorderLayout.NORTH);
        bodyPanel.add(formPanel, BorderLayout.CENTER);

        // Center the form panel on the screen
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(bodyPanel, new GridBagConstraints());

        // Add the form panel to the frame
        frame.add(centerPanel);
    }

    public static void main(String[] args) {
        new RegisterUser();
    }
}
