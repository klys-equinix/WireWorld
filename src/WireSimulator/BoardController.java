package WireSimulator;

import WireComponents.Board;
import WireComponents.Component;
import WireComponents.ComponentFactory;
import WireComponents.FileException;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Konrad on 13.05.2017.
 */
public class BoardController implements IBoardController {
    private static final boolean DEV_MODE = true;
    private static BoardController ourInstance = new BoardController();
    private ArrayList<Board> memory = new ArrayList<>();
    private Board currBoard;
    private boolean firstGen = true;

    private BoardController() {
    }

    public static BoardController getInstance() {
        return ourInstance;
    }

    public ArrayList<Board> getMemory() {
        return memory;
    }

    @Override
    public Board getCurrBoard() {
        return currBoard;
    }

    public static void reset() {
        ourInstance = new BoardController();
    }

    @Override
    public void init(int rows, int columns) {//Initialize an empty board of specified size
        this.currBoard = new Board(rows, columns);
    }

    @Override
    public void init(String fileName) throws FileException {//Initialize a board with a structure specified in a file
        FileInputStream fis = null;
        ObjectInputStream in = null;
        Board readBoard=null;
        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            readBoard = (Board) in.readObject();
            in.close();
        } catch (Exception ex) {
            throw new FileException("Cannot read file" + ex.getStackTrace());
        }
        if(readBoard!=null){
            this.currBoard=readBoard;
        }
    }

    public void readFromUserFormat(String fileName) throws FileException {//Read from a file of understandable format
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] lineContents;
                String rowIndexes[];
                String colIndexes[];
                if (line.contains(" ")) {
                    lineContents = line.split(" ");
                    this.currBoard = new Board(Integer.parseInt(lineContents[0]), Integer.parseInt(lineContents[1]));
                } else if (line.contains(":")) {
                    lineContents = line.split("[.:]");
                    int state = Integer.parseInt(lineContents[2]);
                    if (state != 1 && state != 2) {
                        throw new FileException("This value cannot be put on board" +":"+ state);
                    }
                    this.currBoard.setCellState(Integer.parseInt(lineContents[0]), Integer.parseInt(lineContents[1]), state);
                } else {
                    lineContents = line.split("[.]");
                    if (lineContents[0].contains("-")) {
                        rowIndexes = lineContents[0].split("-");
                    } else {
                        rowIndexes = new String[]{lineContents[0], lineContents[0]};
                    }
                    if (lineContents[1].contains("-")) {
                        colIndexes = lineContents[1].split("-");
                    } else {
                        colIndexes = new String[]{lineContents[1], lineContents[1]};
                    }
                    for (int i = Integer.parseInt(rowIndexes[0]); i <= Integer.parseInt(rowIndexes[1]); i++) {
                        for (int j = Integer.parseInt(colIndexes[0]); j <= Integer.parseInt(colIndexes[1]); j++) {
                            this.currBoard.setCellState(i, j, 3);
                        }
                    }
                }
            }
        } catch (FileNotFoundException ferr) {
            throw new FileException("File not found" + ferr.getMessage());
        } catch (IOException err) {
            throw new FileException("Failed to load Board file");
        }
    }

    @Deprecated
    public void start(int numGen) {//Method existing solely for unit tests
        int numGenenerations = numGen;

        if (numGenenerations != 0) {
            while (numGenenerations > 0) {
                nextGeneration();
                numGenenerations--;
            }
        }
    }

    @Override
    public void nextGeneration() {//Call to make board go to the next state resulting from current state
        if (firstGen) {
            this.memory.add(currBoard);
            for (int i = 0; i < currBoard.rows; i++) {
                if (currBoard.getCellState(i, 0) == 3) {
                    currBoard.setCellState(i, 0, 1);
                }
            }
            for (int j = 0; j < currBoard.columns; j++) {
                if (currBoard.getCellState(0, j) == 3) {
                    currBoard.setCellState(0, j, 1);
                }
            }
            if(DEV_MODE){
                drawBoard();
            }
            this.memory.add(currBoard);
            firstGen = false;
            return;
        }
        Board nextGen = new Board(currBoard.rows, currBoard.columns);
        for (int i = 0; i < currBoard.rows; i++) {
            for (int j = 0; j < currBoard.columns; j++) {
                int newState = evalCell(i, j);
                nextGen.setCellState(i, j, newState);
            }
        }
        this.currBoard = nextGen;
        if(DEV_MODE){
            drawBoard();
        }
        this.memory.add(currBoard);

    }
    public void drawBoard() {
        for (int i = 0; i < currBoard.rows; i++) {
            System.out.print("{");
            for (int j = 0; j < currBoard.columns; j++) {
                System.out.print(currBoard.getCellState(i,j) + ",");
            }
            System.out.print("}" + "\n");
        }
        System.out.print("\n");

    }
    public void writeToUserFormat(String fileName) throws FileException {//Writing the current generetion to a file which is understandable to humans
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(currBoard.rows + " " + currBoard.columns + "\n");
            for (int i = 0; i < currBoard.rows; i++) {
                for (int j = 0; j < currBoard.columns; j++) {
                    if (currBoard.getCellState(i, j) == 3) {
                        bw.write(i + "." + j + "\n");
                    } else if (currBoard.getCellState(i, j) == 1 || currBoard.getCellState(i, j) == 2) {
                        bw.write(i + "." + j + ":" + currBoard.getCellState(i, j) + "\n");
                    }
                }
            }

        } catch (FileNotFoundException ferr) {
            throw new FileException(ferr.getMessage());
        } catch (IOException err) {
            throw new FileException(err.getMessage());
        }
    }
    @Override
    public void writeGenToFile(String fileName) throws FileException{

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
    public Board readObjFromFile(String fileName){
        FileInputStream fis = null;
        ObjectInputStream in = null;
        Board readBoard=null;
        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            readBoard = (Board) in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return readBoard;
    }
    @Override
    public void placeOnBoard(String compType, int[] loc, int rotation, boolean isConnected) throws IndexOutOfBoundsException {//Producing a component element, and than placing it on board
        ComponentFactory compFact = new ComponentFactory();
        Component newComp = compFact.getComponent(compType, loc, rotation, isConnected);
        if (newComp != null) {
            try {
                imprintComponent(newComp);//actually handling the printing of an component
            } catch (IndexOutOfBoundsException out) {
                throw new IndexOutOfBoundsException(out.getMessage());
            }
        }
    }

    private int evalCell(int row, int col) {
        int state;
        int count = 0;
        state = currBoard.getCellState(row, col);

        switch (state) {
            case 0:
                return 0;
            case 1:
                return 2;
            case 2:
                return 3;
        }
        if (state == 3) {
            for (int i = row - 1; i < row + 2; i++) {
                for (int j = col - 1; j < col + 2; j++) {
                    if (i < 0 || i >= currBoard.rows) {
                        continue;
                    }
                    if (j >= currBoard.columns || j < 0) {
                        continue;
                    }

                    if (currBoard.getCellState(i, j) == 1) {
                        count++;

                    }

                }
            }
        }
        if (count == 2 || count == 1) {
            return 1;
        } else {
            return 3;
        }
    }

    private void imprintComponent(Component newComp) {//Method handling the printing of a component on the board
        int[][] tempState = new int[currBoard.rows][currBoard.columns];
        for (int i = 0; i < currBoard.getCellStates().length; i++) {
            System.arraycopy(currBoard.getCellStates()[i], 0, tempState[i], 0, currBoard.getCellStates()[0].length);
        }
        for (int i = newComp.loc[0]; i < newComp.structure.length + newComp.loc[0]; i++) {
            for (int j = newComp.loc[1]; j < newComp.structure[0].length + newComp.loc[1]; j++) {
                if (i < 0 || i >= currBoard.rows || j >= currBoard.columns || j < 0) {
                    throw new IndexOutOfBoundsException("Component does not fit in the board");
                } else {
                    tempState[i][j] = newComp.structure[i - newComp.loc[0]][j - newComp.loc[1]];
                }
            }
        }
        currBoard.setCellStates(tempState);
        if (newComp.wire) {//Wireing the component if requested
            switch (newComp.rotation) {
                case 0: {
                    for (int[] in : newComp.input) {
                        for (int i = newComp.loc[1] + in[1] - 1; i >= 0; i--) {
                            if (isWire(in[0] + newComp.loc[0], i, false)) {
                                break;
                            }
                            currBoard.setCellState(in[0] + newComp.loc[0], i, 3);
                        }
                    }

                    for (int[] out : newComp.output) {
                        for (int i = out[1] + newComp.loc[1] + 1; i < currBoard.columns; i++) {
                            if (isWire(out[0] + newComp.loc[0], i, false)) {
                                break;
                            }
                            currBoard.setCellState(out[0] + newComp.loc[0], i, 3);
                        }
                    }
                    break;
                }
                case 1: {
                    for (int[] in : newComp.input) {
                        for (int i = newComp.loc[0] + in[0] - 1; i >= 0; i--) {
                            if (isWire(i, in[1] + newComp.loc[1], true)) {
                                break;
                            }
                            currBoard.setCellState(i, in[i] + newComp.loc[1], 3);
                        }
                    }
                    for (int[] out : newComp.output) {
                        for (int i = out[0] + newComp.loc[0] + 1; i < currBoard.rows; i++) {
                            if (isWire(i, out[1] + newComp.loc[1], true)) {
                                break;
                            }
                            currBoard.setCellState(i, out[1] + newComp.loc[1], 3);
                        }
                    }
                    break;
                }
                case 2: {
                    for (int[] in : newComp.input) {
                        for (int i = newComp.loc[1] + in[1] + 1; i < currBoard.columns; i++) {
                            if (isWire(in[0] + newComp.loc[0], i, false)) {
                                break;
                            }
                            currBoard.setCellState(in[0] + newComp.loc[0], i, 3);
                        }
                    }

                    for (int[] out : newComp.output) {
                        for (int i = out[1] + newComp.loc[1] - 1; i >= 0; i--) {
                            if (isWire(out[0] + newComp.loc[0], i, false)) {
                                break;
                            }
                            currBoard.setCellState(out[0] + newComp.loc[0], i, 3);
                        }
                    }
                    break;
                }
                case 3: {
                    for (int[] in : newComp.input) {
                        for (int i = newComp.loc[0] + in[0] + 1; i < currBoard.rows; i++) {
                            if (isWire(i, in[1] + newComp.loc[1], true)) {
                                break;
                            }
                            currBoard.setCellState(i, in[1] + newComp.loc[1], 3);
                        }
                    }
                    for (int[] out : newComp.output) {
                        for (int i = out[0] + newComp.loc[0] - 1; i >= 0; i--) {
                            if (isWire(i, out[1] + newComp.loc[1], true)) {
                                break;
                            }
                            currBoard.setCellState(i, out[1] + newComp.loc[1], 3);
                        }
                    }
                    break;
                }
            }
        }
    }

    private boolean isWire(int i, int j, boolean vertical) {//helper function for imprintComponent, to check if the next cell is wire or empty
        if (vertical) {
            for (int k = j - 1; k <= j + 1; k++) {
                if (k < 0 || k >= currBoard.columns) {
                    continue;
                }
                if (currBoard.getCellState(i, k) == 3) {
                    return true;
                }
            }
        } else {
            for (int k = i - 1; k <= i + 1; k++) {
                if (k < 0 || k >= currBoard.rows) {
                    continue;
                }
                if (currBoard.getCellState(k, j) == 3) {
                    return true;
                }
            }
        }
        return false;
    }


}
