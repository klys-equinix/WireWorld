
/**
 * Created by Konrad on 20.04.2017.
 */
public class Board {
    private int[][] cellStates;
    int rows;
    int columns;
    public Board(int rows,int columns){
        cellStates = new int[rows][columns];
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                cellStates[i][j]=0;
            }
        }
        this.rows=rows;
        this.columns=columns;
    }
    public Board(int[][] cellStates,int rows,int columns){
        this.cellStates=cellStates;
        this.rows=rows;
        this.columns=columns;
    }
    public void setCellState(int i,int j,int state){
        this.cellStates[i][j]=state;
    }
    public int getCellState(int i,int j){
        return cellStates[i][j];
    }
    public void drawBoard(){
        for(int i=0;i<this.rows;i++){
            for(int j=0;j<this.columns;j++){
                System.out.print(this.cellStates[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

    }
    public int evalCell(int row,int col){
        int state;
        int count=0;
        state=this.cellStates[row][col];

        switch (state){
            case 0:
                return 0;
            case 1:
                return 2;
            case 2:
                return 3;
        }
        if(state==3){
            for(int i=row-1;i<row+2;i++){
                for(int j=col-1;j<col+2;j++) {
                    if (i < 0 || i > this.rows) {
                        continue;
                    }
                    if (j >= this.columns||j<0) {
                        continue;
                    }
                    if (this.cellStates[i][j] == 1) {
                        count++;
                    }
                }
            }
            if(col==0&&this.cellStates[row][this.columns-1]==1){
                return 1;
            }
        }
        if(count==2||count==1){
            return 1;
        }
        else
        {
            return 3;
        }
    }
}


