package Core;

import javax.swing.*;

/**
 * Created by Szymon on 11.05.2017.
 */
public interface WindowHandler {
    void createWindow();
    void destroyWindow();
    void showWindow();
    void hideWindow();
    JPanel getContentPanel();
}
