package gamelogic;

/**
 * Created by Konrad on 13.05.2017.
 */
public interface IBoardController {
    public void init(int rows, int columns);
    public void init(String fileName) throws FileException;
    public void nextGeneration();
    public void writeGenToFile(String fileName) throws FileException;
}
