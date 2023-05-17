import java.net.MalformedURLException;
import javax.swing.*;
import Components.Frame;
import Page.Welcome;

public class VotingApp {
    public static void main(String[] args) throws MalformedURLException {
        JFrame frame = Frame.screen;

        new Welcome();

        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
