package tests;

import WireComponents.Board;
import WireComponents.FileException;
import WireSimulator.BoardController;
import org.junit.*;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

/**
 * Created by Konrad on 23.04.2017.
 */
public class BoardControllerTest {
    private int [][] expInitBoard={{0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0},
    {3,3,3,3,2,1,3,3,3,3},
    {0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0}};
    private int [][] expFinalBoard={{0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {3,3,3,3,3,3,3,3,2,1},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0}};
    private int [][] expImprintBoard={{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3},
    {0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,3,3,3,3,0,0,0,0,0,0},
    {0,0,2,1,3,3,0,3,3,3,3,0,0,3,0,0,0,0,0,0},
    {0,3,0,0,0,0,3,0,0,0,3,3,3,3,0,0,0,0,0,0},
    {0,0,3,3,3,3,0,0,0,0,0,0,3,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
    @org.junit.Before
    public void setUp() throws Exception {
        System.out.println("setting it up");
        BoardController.getInstance().readFromUserFormat("./testFile");


    }

    /**
     * Checking if initializing from a readable format file works
     */
    @Test
    public void checkInitBoard(){
        System.out.println("Running init test");
        assertArrayEquals(BoardController.getInstance().getCurrBoard().getCellStates(), expInitBoard);
    }

    /**
     * Checking if simulation works
     */
    @Test
    public void checkFinalBoard(){
        System.out.println("Running final test");
        BoardController.getInstance().start(10);
        assertArrayEquals(BoardController.getInstance().getCurrBoard().getCellStates(), expFinalBoard);

    }

    /**
     * Checking if imprinting works
     */
    @Test
    public void checkImprintBoard(){
        System.out.println("Running imprint test");
        BoardController.getInstance().reset();
        BoardController.getInstance().init(20,20);
        int[] loc = {5, 1};//location of topmost indexes of an element-where should it be placed on the board
        BoardController.getInstance().placeOnBoard("ClockGen", loc, 0, false);//imprinting the element on the empty board
        int[] nloc = {2, 10};//location of the second element
        BoardController.getInstance().placeOnBoard("ExORgate", nloc, 2, true);
        BoardController.getInstance().drawBoard();
        assertArrayEquals(BoardController.getInstance().getCurrBoard().getCellStates(),expImprintBoard);
    }
    /**
     * Checking if reading from byte stream format file works
     */
    @Test
    public void checkSerializeSave(){
        System.out.println("Running load test");
        BoardController.getInstance().reset();
        try {
            BoardController.getInstance().init("newFile");
        }catch (FileException fx){

        }
        assertArrayEquals(BoardController.getInstance().getCurrBoard().getCellStates(),expImprintBoard);
    }



    @org.junit.After
    public void tearDown() throws Exception {

    }

}