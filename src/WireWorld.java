import javax.swing.*;
import java.awt.*;

/**
 * Created by Konrad on 20.04.2017.
 */
public class WireWorld {
    private static JFrame controlFrame;
    private static JFrame gameFrame;

    public static void main(String args[]){
        /*
            It's time to open control frame
        */
        controlFrame = new JFrame();
        controlFrame.setContentPane(new ControlPanel().getPanel());
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.pack();
        controlFrame.setLocationRelativeTo(null);
        controlFrame.setVisible(true);
    }

    public static void initGameWindow(String filePath, int genNum) {
        gameFrame = new JFrame();
        gameFrame.setContentPane(new GamePanel().getPanel());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        // Closing control frame - no longer needed
        controlFrame.setVisible(false);
        controlFrame.dispose();
    }
}
