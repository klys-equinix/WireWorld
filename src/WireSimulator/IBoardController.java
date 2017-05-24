package WireSimulator;

import WireComponents.Board;
import WireComponents.FileException;

/**
 * The IBoardController interface is an interface for all controllers of Board model,
 * designed to provide basic functionality needed in WireWorld, and provide Controller interchangeability.
 *
 * @author Konrad Lys
 * @version 1.0
 * @since 2017-05-13
 */
public interface IBoardController {
    /**
     * init is used to create first board as an empty one (not in constructor because BoardController might be a Singleton)
     *
     * @param rows    used to specify rows number of an empty board
     * @param columns used to specify columns number of an empty board
     */
    void init(int rows, int columns);

    /**
     * init might also be used to create first board from a file with a Board object saved
     *
     * @param fileName file name with the saved board we want to start with
     * @throws FileException in case reading from a file fails
     */
    void init(String fileName) throws FileException;

    /**
     * Goes to next generation of currBoard(current Board)
     */
    void nextGeneration();

    /**
     * Allows saving a Board object to a file(currBoard)
     *
     * @param fileName name of a file where we want to keep our board
     * @throws FileException in case writing failed
     */
    void writeGenToFile(String fileName) throws FileException;

    /**
     * Places a selected Component on the currBoard
     *
     * @param compType    type of a component
     * @param loc         location of a left top corner of an component
     * @param rotation    0,1,2,3 - how we want the component rotated
     * @param isConnected true if we want the component to connect itself, false otherwise
     * @throws IndexOutOfBoundsException in case the component does not fit in Board borders
     */
    void placeOnBoard(String compType, int[] loc, int rotation, boolean isConnected) throws IndexOutOfBoundsException;

    /**
     * @return currBoard
     */
    Board getCurrBoard();
}
