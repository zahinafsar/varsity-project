package Components;

import javax.swing.JFrame;

public class Frame {
    public static JFrame screen = new JFrame();
    public Frame() {
        screen.getContentPane().removeAll();
        screen.getContentPane().repaint();
    }
}
