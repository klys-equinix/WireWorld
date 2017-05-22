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
     * @see SettingsManager
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
     * @see SettingsManager
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
     * Get color of cell based on state
     * @param cellState State of the cell
     * @return Color Color of the cell
     */
    public static Color getCellColor(int cellState)
    {
        if(cellState == 0)
            return SettingsManager.getInstance().getGameBackgroundColor();
        else if(cellState == 1)
            return SettingsManager.getInstance().getGameEleHeadColor();
        else if(cellState == 2)
            return SettingsManager.getInstance().getGameEleTailColor();
        else
            return SettingsManager.getInstance().getGameCableColor();
    }

    /**
     * Draw board using given Graphics object.
     * @param board Board to be draw
     * @param g Graphics object
     * @param zoom Zoom integer to be used
     * @see Graphics
     * @see Board
     */
    public static void drawBoard(Board board, Graphics g, int zoom)
    {
        for(int i = 0; i < board.columns; i++) {
            for (int j = 0; j < board.rows; j++) {
                if (SettingsManager.getInstance().getGameDrawOutline()) {
                    g.setColor(Color.white);
                    g.drawRect(j * zoom - 1, i * zoom - 1, zoom + 1, zoom + 1);
                }
                g.setColor(Utils.getCellColor(board.getCellState(i, j)));
                g.fillRect(j * zoom, i * zoom, zoom, zoom);
            }
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

    /**
     * Get extension of provided file
     * @param file File to get extension from
     * @return File's extension
     * @see File
     */
    public static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }
}
