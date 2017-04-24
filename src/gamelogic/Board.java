package gamelogic;

/**
 * Created by Konrad on 20.04.2017.
 */
public class Board {
    private int[][] cellStates;
    int rows;
    int columns;

    public int[][] getCellStates() {
        return cellStates;
    }

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

    public Board(int[][] cellStates, int rows, int columns) {
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

    public void drawBoard() {
        for (int i = 0; i < this.rows; i++) {
            System.out.print("{");
            for (int j = 0; j < this.columns; j++) {
                System.out.print(this.cellStates[i][j] + ",");
            }
            System.out.print("}" + "\n");
        }
        System.out.print("\n");

    }

    public int evalCell(int row, int col) {
        int state;
        int count = 0;
        state = this.cellStates[row][col];

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
                    if (i < 0 || i >= this.rows) {
                        continue;
                    }
                    if (j >= this.columns) {
                        continue;
                    }
                    if (j < 0) {
                        if (this.cellStates[i][this.columns - 1] == 1) {
                            count++;
                        }
                        continue;
                    }
                        if (this.cellStates[i][j] == 1) {
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

    public void imprintComponent(Component newComp) {//nie spodoba ci się to Szymon więc lepiej nie patrz MACIEK
        int[][] tempStates = this.cellStates;
        for (int i = newComp.loc[0]; i < newComp.structure.length + newComp.loc[0]; i++) {
            for (int j = newComp.loc[1]; j < newComp.structure[0].length + newComp.loc[1]; j++) {
                if (i < 0 || i >= this.rows || j >= this.columns || j < 0) {
                    return;
                } else {
                    tempStates[i][j] = newComp.structure[i - newComp.loc[0]][j - newComp.loc[1]];
                }
            }
        }
        this.cellStates = tempStates;
        if (newComp.rotation == 0 || newComp.rotation == 2) {
            for(int []in :newComp.input) {
                for (int i = newComp.loc[1]+in[1]-1;i>=0;i--){
                    if(this.cellStates[in[0]+newComp.loc[0]][i]==3){
                        break;
                    }
                    this.cellStates[in[0]+newComp.loc[0]][i]=3;
                }
            }

            for(int []out :newComp.output) {
                for (int i = out[1]+newComp.loc[1]+1;i<this.columns;i++){
                    if(this.cellStates[out[0]+newComp.loc[0]][i]==3){
                        break;
                    }
                    this.cellStates[out[0]+newComp.loc[0]][i]=3;
                }
            }
        }else{
            for(int []in :newComp.input) {
                for (int i = newComp.loc[0]+in[0]-1;i>=0;i--){
                    if(this.cellStates[i][in[1]+newComp.loc[1]]==3){
                        break;
                    }
                    this.cellStates[i][in[1]+newComp.loc[1]]=3;
                }
            }
            for(int []out :newComp.output) {
                for (int i = out[0]+newComp.loc[0]+1;i<this.rows;i++){
                    if(this.cellStates[i][out[1]+newComp.loc[1]]==3){
                        break;
                    }
                    this.cellStates[i][out[1]+newComp.loc[1]]=3;
                }
            }
        }
    }
}

