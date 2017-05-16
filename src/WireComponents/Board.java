package WireComponents;

import java.io.Serializable;

/**
 * Created by Konrad on 20.04.2017.
 */
public class Board implements Serializable {//Model for our application
    private int[][] cellStates;
    public int rows;
    public int columns;


    public Board(int rows, int columns) {
        cellStates = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cellStates[i][j] = 0;
            }
        }
        this.rows = rows;
        this.columns = columns;
    }

    Board(int[][] cellStates, int rows, int columns) {
        this.cellStates = cellStates;
        this.rows = rows;
        this.columns = columns;
    }

    public void setCellState(int i, int j, int state) {
        this.cellStates[i][j] = state;
    }

    public int getCellState(int i, int j) {
        return cellStates[i][j];
    }
    public int[][] getCellStates() {
        return cellStates;
    }
    public void setCellStates(int[][] cellStates) {
        this.cellStates = cellStates;
    }



}

