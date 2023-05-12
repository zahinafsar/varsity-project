package Components;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    public Button(String label) {
        super(label);
        setMargin(new Insets(10, 20, 10, 20));
        setFont(new Font("Arial", Font.PLAIN, 18));
    }
}
