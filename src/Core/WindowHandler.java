package Core;

import javax.swing.*;

/**
 * <h1>WindowHandler</h1>
 * Interface used to create classes maintaining logic
 * of the parts of app.
 * @author Szymon "Aitwar" Chmal
 */
public interface WindowHandler {
    void createWindow();
    void destroyWindow();
    void showWindow();
    void hideWindow();
    JPanel getContentPanel();
}
