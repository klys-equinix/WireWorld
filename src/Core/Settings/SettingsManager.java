package Core.Settings;

import Core.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Created by Szymon on 23.04.2017.
 */
public class SettingsManager {
    private static SettingsManager setman = new SettingsManager();

    private java.util.List<SettingsListener> listeners = new ArrayList<SettingsListener>();

    public void addListener(SettingsListener toAdd) {
        listeners.add(toAdd);
    }

    /*
        App modes
    */
    public final static int APP_MODE_FIXED = 0;
    public final static int APP_MODE_INF = 1;

    public final static int APP_FIXED_AUTO = 0;
    public final static int APP_FIXED_MANUAL = 1;

    private int appMode;
    private int appFixedMode;

    private int appFixedGen;
    private int appFixedCurGen;

    private Color gameEleHeadColor;
    private Color gameEleTailColor;
    private Color gameCableColor;
    private Color gameBackgroundColor;

    private int gameGenTime;

    private SettingsManager() {
        gameEleHeadColor = new Color(255, 0, 0);
        gameEleTailColor = new Color(255, 255, 0);
        gameCableColor = new Color(255, 255, 255);
        gameBackgroundColor = new Color(0, 0, 0);

        gameGenTime = 500;
    }

    public static SettingsManager getInstance() {
        return setman;
    }

    public void loadSettings(BufferedReader br) throws IOException
    {
        String str;
        while((str = br.readLine()) != null) {
            String var[] = str.split(":");
            if (var.length != 3)
                continue;
            if (var[0].equals("COLOR")) {
                if (var[1].equals("TAIL"))
                    SettingsManager.getInstance().setGameEleTailColor(Utils.getColorFromVector(var[2]));
                else if (var[1].equals("HEAD"))
                    SettingsManager.getInstance().setGameEleHeadColor(Utils.getColorFromVector(var[2]));
                else if (var[1].equals("CABLE"))
                    SettingsManager.getInstance().setGameCableColor(Utils.getColorFromVector(var[2]));
                else if (var[1].equals("BACKGROUND"))
                    SettingsManager.getInstance().setGameBackgroundColor(Utils.getColorFromVector(var[2]));
            }
            if(var[0].equals("SIM")) {
            if (var[1].equals("TIME"))
                SettingsManager.getInstance().setGameGenTime(Integer.parseInt(var[2]));
            }
        }
    }

    public void saveSettings(BufferedWriter bw) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("COLOR:TAIL:").append(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleTailColor()))
                .append("\n").append("COLOR:HEAD:").append(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleHeadColor()))
                .append("\n").append("COLOR:CABLE:").append(Utils.getColorAsVector(SettingsManager.getInstance().getGameCableColor()))
                .append("\nSIM:TIME").append(SettingsManager.getInstance().getGameGenTime());
        bw.write(sb.toString());
    }

    private void fireChangedEvent()
    {
        for (SettingsListener hl : listeners)
            hl.changed();
    }

    public void setAppMode(int mode) {
        assert (mode < 0 || mode > 1);
        appMode = mode;
        fireChangedEvent();
    }

    public int getAppMode() {
        return appMode;
    }

    public void setAppFixedMode(int mode) {
        assert (mode < 0 || mode > 1);
        appFixedMode = mode;
        fireChangedEvent();
    }

    public int getAppFixedMode() {
        return appFixedMode;
    }

    public void setAppFixedGen(int gen) {
        assert (gen < 0);
        appFixedGen = gen;
        fireChangedEvent();
    }

    public int getAppFixedGen() {
        return appFixedGen;
    }

    public Color getGameEleHeadColor() {
        return gameEleHeadColor;
    }

    public void setGameEleHeadColor(Color gameEleHeadColor) {
        this.gameEleHeadColor = gameEleHeadColor;
        fireChangedEvent();
    }

    public Color getGameEleTailColor() {
        return gameEleTailColor;
    }

    public void setGameEleTailColor(Color gameEleTailColor) {
        this.gameEleTailColor = gameEleTailColor;
        fireChangedEvent();
    }

    public Color getGameCableColor() {
        return gameCableColor;
    }

    public void setGameCableColor(Color gameCableColor) {
        this.gameCableColor = gameCableColor;
        fireChangedEvent();
    }

    public int getAppFixedCurGen() {
        return appFixedCurGen;
    }

    public void setAppFixedCurGen(int appFixedCurGen) {
        this.appFixedCurGen = appFixedCurGen;
        fireChangedEvent();
    }

    public Color getGameBackgroundColor() {
        return gameBackgroundColor;
    }

    public void setGameBackgroundColor(Color gameBackgroundColor) {
        this.gameBackgroundColor = gameBackgroundColor;
        fireChangedEvent();
    }

    public int getGameGenTime() {
        return gameGenTime;
    }

    public void setGameGenTime(int gameGenTime) {
        this.gameGenTime = gameGenTime;
        fireChangedEvent();
    }
}
