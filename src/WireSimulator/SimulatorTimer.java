package WireSimulator;

import Core.Settings.SettingsManager;
import WindowHandlers.GameHandler;

import java.util.TimerTask;

/**
 * Created by Szymon on 12.05.2017.
 */
public class SimulatorTimer extends TimerTask {
    private GameHandler gameHandler;

    public SimulatorTimer(GameHandler gameHandler)
    {
        this.gameHandler = gameHandler;
    }

    @Override
    public void run() {
        if(SettingsManager.getInstance().getAppMode() == SettingsManager.APP_MODE_FIXED)
        {
            if(SettingsManager.getInstance().getAppFixedMode() == SettingsManager.APP_FIXED_AUTO) {
                if(SettingsManager.getInstance().getAppFixedCurGen() < SettingsManager.getInstance().getAppFixedGen()) {
                    gameHandler.setGenSliderValue(SettingsManager.getInstance().getAppFixedCurGen()+1);
                    gameHandler.setBoard(BoardController.getInstance().getMemory().get(SettingsManager.getInstance().getAppFixedCurGen()));
                    SettingsManager.getInstance().setAppFixedCurGen(SettingsManager.getInstance().getAppFixedCurGen()+1);
                }
                else
                    SettingsManager.getInstance().setAppFixedCurGen(0);
            }
        }
        else {
            BoardController.getInstance().nextGeneration();
            gameHandler.setBoard(BoardController.getInstance().getCurrBoard());
            gameHandler.setGenField(SettingsManager.getInstance().getAppFixedCurGen());
            SettingsManager.getInstance().setAppFixedCurGen(SettingsManager.getInstance().getAppFixedCurGen()+1);
        }
    }
}