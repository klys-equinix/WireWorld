package applogic;

/**
 * Created by Szymon on 23.04.2017.
 */
public class SettingsManager {
    private static SettingsManager setman = new SettingsManager();

    /*
        App modes
    */
    public final static int APP_MODE_FIXED = 0;
    public final static int APP_MODE_INF = 1;

    private int appMode;

    private SettingsManager() {
    }

    public static SettingsManager getInstance() {
        return setman;
    }

    public void setAppMode(int mode) {
        assert (appMode < 0 || appMode > 1);
        appMode = mode;
    }

    public int getAppMode() {
        return appMode;
    }
}
