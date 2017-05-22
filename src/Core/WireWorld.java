package Core;

import Core.Settings.SettingsManager;
import WindowHandlers.GameHandler;
import WireComponents.FileException;
import WindowHandlers.ControlHandler;
import WindowHandlers.EditorHandler;
import WireSimulator.BoardController;

import javax.swing.*;

/**
 * <h1>WireWorld</h1>
 * Class used to control app at low-level base.
 * @author Szymon "Aitwar" Chmal
 */
public class WireWorld {
    private static EditorHandler editorHandler;
    private static ControlHandler controlHandler;
    private static GameHandler gameHandler;

    /**
     * Method used to prepare editor logic, create and show editor window.
     */
    static public void runEditor()
    {
        editorHandler = new EditorHandler();
        editorHandler.createWindow();
        editorHandler.showWindow();
    }

    /**
     * Method used to prepare game settings logic, create and show settings window.
     */
    static public void runControl()
    {
        controlHandler = new ControlHandler();
        controlHandler.createWindow();
        controlHandler.showWindow();
    }

    /**
     * Method used to prepare game logic, create and show game window.
     * @param filePath Path to file with board saved inside.
     * @param genNum Number of generations to generate (unused when app mode equals infinite)
     */
    static public void runGame(String filePath, int genNum)
    {
        try {
            BoardController.getInstance().init(filePath);
        } catch (FileException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),"WireWorld - błąd!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SettingsManager.getInstance().setAppFixedGen(genNum);

        if(SettingsManager.getInstance().getAppMode() == SettingsManager.APP_MODE_FIXED)
            for(int i = 0; i < genNum; i++)
                BoardController.getInstance().nextGeneration();
        else
            BoardController.getInstance().nextGeneration();

        gameHandler = new GameHandler();
        gameHandler.createWindow();
        gameHandler.showWindow();

        controlHandler.destroyWindow();
    }

}
