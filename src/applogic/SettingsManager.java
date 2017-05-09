package applogic;

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

    private SettingsManager() {
        gameEleHeadColor = new Color(255, 0, 0);
        gameEleTailColor = new Color(255, 255, 0);
        gameCableColor = new Color(255, 255, 255);
    }

    public static SettingsManager getInstance() {
        return setman;
    }

    public void loadFromFile(File f)
    {
        FileReader fr;
        try {
            fr = new FileReader(f);
        }
        catch(FileNotFoundException e)
        {
            // TODO
            return;
        }
        BufferedReader br = new BufferedReader(fr);
        try {
            this.load(br);
            br.close();
            fr.close();
        }
        catch(IOException e)
        {
            // TODO
        }
    }

    public void saveToFile(File f)
    {
        FileWriter fw;
        try {
           fw = new FileWriter(f);
        }
        catch(IOException e)
        {
            // TODO
            return;
        }
        BufferedWriter bw = new BufferedWriter(fw);
        this.save(bw);
        try {
            bw.close();
            fw.close();
        }
        catch(IOException e)
        {
            // TODO
        }
    }

    private void load(BufferedReader br) throws IOException
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
            }
        }
    }

    private void save(BufferedWriter bw)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("COLOR:TAIL:").append(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleTailColor()))
                .append("\n").append("COLOR:HEAD:").append(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleHeadColor()))
                .append("\n").append("COLOR:CABLE:").append(Utils.getColorAsVector(SettingsManager.getInstance().getGameCableColor()));
        try{
            bw.write(sb.toString());
        }
        catch(IOException e)
        {
            // TODO
        }
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
}
