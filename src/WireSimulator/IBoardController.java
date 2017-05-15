package WireSimulator;

import WireComponents.FileException;

/**
 * Created by Konrad on 13.05.2017.
 */
public interface IBoardController {
    public void init(int rows, int columns);
    public void init(String fileName) throws FileException;
    public void nextGeneration();
    public void writeGenToFile(String fileName) throws FileException;
    public void placeOnBoard(String compType, int[] loc, int rotation, boolean isConnected) throws IndexOutOfBoundsException;
}
