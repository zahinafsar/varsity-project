package Components;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class Label extends JLabel {
    public Label(String text) {
        super(text);
        super.setBorder(new EmptyBorder(10, 10, 10, 10));
        super.setFont(new Font("Arial", Font.BOLD, 15));
    }
}
