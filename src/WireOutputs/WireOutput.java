package WireOutputs;

import WireComponents.Board;

import java.io.File;
import java.io.IOException;

/**
 * <h1>WireOutput</h1>
 * Interface used to create output methods
 *
 * @author Szymon "Aitwar" Chmal
 */
public interface WireOutput {
    /**
     * Handle saving of board to file
     *
     * @param board Board to be saved
     * @param file  File to which board will be saved
     * @param zoom  Zoom which can be ignored
     * @throws IOException When something gone wrong
     */
    void saveBoard(Board board, File file, int zoom) throws IOException;
}
