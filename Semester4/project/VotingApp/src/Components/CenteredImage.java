package Components;

import java.awt.*;
import javax.swing.*;

public class CenteredImage extends JPanel {
    private ImageIcon image;

    public CenteredImage(ImageIcon image) {
        this.image = image;
        setLayout(new GridBagLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = (getWidth() - image.getIconWidth()) / 2;
        int y = (getHeight() - image.getIconHeight()) / 2;

        g.drawImage(image.getImage(), x, y, null);
    }
}
