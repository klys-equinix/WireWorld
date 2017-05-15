package Core;

import Core.Settings.SettingsManager;
import WindowHandlers.GameHandler;
import WireComponents.FileException;
import WindowHandlers.ControlHandler;
import WindowHandlers.EditorHandler;
import WireSimulator.SimulatorTimer;
import WireSimulator.BoardController;

import javax.swing.*;
import java.util.Timer;

/**
 * Created by Konrad on 20.04.2017.
 */
public class WireWorld {
    private static EditorHandler editorHandler;
    private static ControlHandler controlHandler;
    private static GameHandler gameHandler;

    static public void runEditor()
    {
        editorHandler = new EditorHandler();
        editorHandler.createWindow();
        editorHandler.showWindow();
    }
    static public void runControl()
    {
        controlHandler = new ControlHandler();
        controlHandler.createWindow();
        controlHandler.showWindow();
    }
    static public void runGame(String filePath, int genNum)
    {
        try {
            BoardController.getInstance().init(filePath);
        } catch (FileException e) {//Ten wyjątek ma całkiem konkretne wiadomości o tym co się stało - drukuj e.getMessage();
            JOptionPane.showMessageDialog(null,"Wystąpił problem w czasie odczytywania pliku z matrycą!","WireWorld - błąd!", JOptionPane.ERROR_MESSAGE);
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

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SimulatorTimer(gameHandler), SettingsManager.getInstance().getGameGenTime(), SettingsManager.getInstance().getGameGenTime());

        controlHandler.destroyWindow();
    }

}
