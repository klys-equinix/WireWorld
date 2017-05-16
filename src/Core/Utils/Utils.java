package Core.Utils;

import Core.Settings.SettingsManager;
import WireComponents.Board;
import WireComponents.FileException;

import javax.swing.*;
import java.awt.*;
import java.io.*;


/**
 * <h1>Utility</h1>
 * The collection of utility functions.
 * @author Szymon "V3RON" Chmal
 */
public class Utils {

    /**
     * Get color as vector (pattern: [r,g,b])
     * @param color Color to be converted
     * @return String (pattern: [r,g,b])
     */
    public static String getColorAsVector(Color color)
    {
        return new StringBuilder("[")
                .append(color.getRed()).append(",")
                .append(color.getGreen()).append(",")
                .append(color.getBlue()).append("]")
                .toString();
    }

    /**
     * Get color from vector (pattern: [r,g,b])
     * @param vector Vector to be converted
     * @return Color object
     */
    public static Color getColorFromVector(String vector)
    {
        vector = vector.substring(1, vector.length()-1);
        String rgb[] = vector.split(",");
        return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }

    /**
     * Load settings from compatible file.
     * @param f File to load from
     */
    public static void loadSettingsFromFile(File f)
    {
        FileReader fr;
        try {
            fr = new FileReader(f);
        }
        catch(FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null,"Wybrany plik nie został znaleziony!","WireWorld - odczyt konfiguracji",JOptionPane.ERROR_MESSAGE);
            return;
        }
        BufferedReader br = new BufferedReader(fr);
        try {
            SettingsManager.getInstance().loadSettings(br);
            br.close();
            fr.close();
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null,"Wystąpił problem w czasie czytania konfiguracji!","WireWorld - odczyt konfiguracji",JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    /**
     * Save settings to compatible file.
     * @param f File to save to
     */
    public static void saveSettingsToFile(File f)
    {
        FileWriter fw;
        try {
            fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            SettingsManager.getInstance().saveSettings(bw);
            bw.close();
            fw.close();
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null,"Wystąpił problem w czasie zapisu konfiguracji!","WireWorld - zapis konfiguracji",JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Todo
     */
    public static void writeGenToFile(String fileName, Board currBoard) throws FileException {
        new Thread(() -> {
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
                fos = new FileOutputStream(fileName);
                out = new ObjectOutputStream(fos);
                out.writeObject(currBoard);
                out.close();
            } catch (Exception ex) {
            }
        }).start();
    }
}
