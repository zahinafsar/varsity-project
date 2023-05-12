import java.net.MalformedURLException;
import javax.swing.*;
import Components.Frame;
import Page.Welcome;

public class VotingApp extends JFrame {
    public static void main(String[] args) throws MalformedURLException {
        JFrame frame = Frame.screen;

        new Welcome();

        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
