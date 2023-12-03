import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureConverterUI extends JFrame {
    private JTextField fahrenheitField;
    private JTextField celsiusField;

    public TemperatureConverterUI() {
        setTitle("Temperature Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel fahrenheitLabel = new JLabel("Fahrenheit: ");
        fahrenheitField = new JTextField(10);
        JLabel celsiusLabel = new JLabel("Celsius: ");
        celsiusField = new JTextField(10);
        celsiusField.setEditable(false);

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertTemperature();
            }
        });

        JButton backspaceButton = new JButton("Backspace");
        backspaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backspace();
            }
        });

        add(fahrenheitLabel);
        add(fahrenheitField);
        add(celsiusLabel);
        add(celsiusField);
        add(convertButton);
        add(backspaceButton);

        pack();
        setVisible(true);
    }

    private void convertTemperature() {
        try {
            String fahrenheitText = fahrenheitField.getText();
            double fahrenheit = Double.parseDouble(fahrenheitText);
            double celsius = (fahrenheit - 32) * 5 / 9;
            celsiusField.setText(Double.toString(celsius));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid temperature in Fahrenheit.");
        }
    }

    private void backspace() {
        String currentText = fahrenheitField.getText();
        if (!currentText.isEmpty()) {
            fahrenheitField.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TemperatureConverterUI();
            }
        });
    }
}
