package applogic;

import java.awt.*;

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
}
