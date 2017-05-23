package Core;

import WireComponents.Board;
import WireComponents.ComponentBoardFactory;

import javax.swing.*;

/**
 * <h1>DesktopLauncher</h1>
 * Class used to launch app.
 * @author Szymon "Aitwar" Chmal
 */
public class DesktopLauncher {
    public static void main(String args[]){
        /*try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        WireWorld ww = new WireWorld();
        ww.runControl();
    }
}
