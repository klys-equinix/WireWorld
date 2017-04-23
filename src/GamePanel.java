import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Szymon on 23.04.2017.
 */
public class GamePanel {
    private JSlider slider1;
    private JButton autoButton;
    private JButton manualButton;
    private JPanel mainPanel;
    private JLabel imgLabel;

    public GamePanel() {
        try {
            imgLabel.setIcon(new ImageIcon(new URL("http://i0.kym-cdn.com/entries/icons/original/000/001/030/dickbutt.jpg")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        imgLabel.setText("");
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}
