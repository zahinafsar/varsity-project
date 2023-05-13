package Components;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Input extends JTextField {
    public Input(int columns) {
        super(20);
        super.setBorder(new EmptyBorder(12, 12, 12, 12));
    }
}
