package Core.Utils;

import Core.Settings.SettingsManager;
import WireComponents.Board;
import WireComponents.FileException;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Created by Szymon on 09.05.2017.
 */
public class Utils {
    public static String getColorAsVector(Color color)
    {
        return new StringBuilder("[")
                .append(color.getRed()).append(",")
                .append(color.getGreen()).append(",")
                .append(color.getBlue()).append("]")
                .toString();
    }

    public static Color getColorFromVector(String vector)
    {
        vector = vector.substring(1, vector.length()-1);
        String rgb[] = vector.split(",");
        return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }

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
    public static void writeGenToFile(String fileName, Board currBoard) throws FileException {//Writing the current generetion to a file which can be loaded later
        new Thread(new Runnable() {
            @Override
            public void run(){
                FileOutputStream fos = null;
                ObjectOutputStream out = null;
                try {
                    fos = new FileOutputStream(fileName);
                    out = new ObjectOutputStream(fos);
                    out.writeObject(currBoard);
                    out.close();
                } catch (Exception ex) {
                }
            }
        }).start();
    }
}
