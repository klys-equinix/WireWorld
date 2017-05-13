package gamelogic;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Konrad on 13.05.2017.
 */
public class BoardController implements IBoardController{
    private static BoardController ourInstance = new BoardController();
    private ArrayList<Board> memory = new ArrayList<>();
    private int numGen;
    private Board currBoard;
    private boolean firstGen=true;
    private boolean keepRunning;

    public static BoardController getInstance() {
        return ourInstance;
    }
    public static void reset(){
        ourInstance = new BoardController();
    }
    private BoardController(){}
    public void init(int rows, int columns){
        this.currBoard = new Board(rows,columns);
        this.numGen=0;
    }
    public void init(String fileName) throws FileException {
        BufferedReader br = null;
        FileReader fr =null;
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {

                String[] cont;
                String rowIndexes[];
                String colIndexes[];
                if (line.contains(" ")) {
                    cont = line.split(" ");
                    this.currBoard = new Board(Integer.parseInt(cont[0]), Integer.parseInt(cont[1]));
                } else if (line.contains(":")) {
                    cont = line.split("[.:]");
                    int state=Integer.parseInt(cont[2]);
                    if(state!=1&&state!=2){
                        throw new FileException("This value cannot be put on board"+state);
                    }
                    this.currBoard.setCellState(Integer.parseInt(cont[0]),Integer.parseInt(cont[1]),state);
                } else {
                    cont = line.split("[.]");
                    if (cont[0].contains("-")) {
                        rowIndexes = cont[0].split("-");
                    } else {
                        rowIndexes = new String[]{cont[0], cont[0]};
                    }
                    if (cont[1].contains("-")) {
                        colIndexes = cont[1].split("-");
                    } else {
                        colIndexes = new String[]{cont[1], cont[1]};
                    }
                    for (int i = Integer.parseInt(rowIndexes[0]); i <= Integer.parseInt(rowIndexes[1]); i++) {
                        for (int j = Integer.parseInt(colIndexes[0]); j <= Integer.parseInt(colIndexes[1]); j++) {
                            this.currBoard.setCellState(i, j, 3);
                        }
                    }
                }


            }

        }catch(FileNotFoundException ferr){
            throw new FileException("File not found"+ferr.getMessage());
        }catch(IOException err){
            throw new FileException("Failed to load file"+err.getMessage());
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
                if  (fr != null){
                    fr.close();
                }
            }catch(IOException err){
                throw new FileException("Failed to close readers"+err.getMessage());
            }
        }
        this.numGen=0;
    }
    @Deprecated
    public void start(int numGen){//changed-now there are no constructors specifying the numGen
        this.numGen=numGen;

        if(this.numGen!=0) {
            while (numGen > 0) {
                nextGeneration();
                numGen--;
            }
        }
    }
    public void nextGeneration(){
        if(firstGen){
            this.numGen=numGen;
            this.memory.add(currBoard);
            for(int i=0; i<currBoard.rows; i++){
                if(currBoard.getCellState(i,0)==3){
                    currBoard.setCellState(i,0,1);
                }
            }
            for(int j=0; j<currBoard.columns; j++){
                if(currBoard.getCellState(0,j)==3){
                    currBoard.setCellState(0,j,1);
                }
            }
            this.currBoard.drawBoard();
            this.memory.add(currBoard);
            firstGen=false;
            return;
        }
        Board nextGen = new Board(currBoard.rows,currBoard.columns);
        boolean isDead = true;
        for(int i=0; i<currBoard.rows; i++){
            for(int j=0; j<currBoard.columns; j++){
                int newState = evalCell(i,j);
                nextGen.setCellState(i,j,newState);
                if(newState==1){
                    isDead=false;
                }

            }
        }
        if(isDead){
            this.keepRunning=false;
        }
        this.currBoard = nextGen;
        this.currBoard.drawBoard();
        this.memory.add(currBoard);//to be deleted after integration with drawing module

    }
    public void writeGenToFile(String fileName) throws FileException{
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(fileName);
            bw = new BufferedWriter(fw);
            bw.write(currBoard.rows+" "+currBoard.columns+"\n");
            for(int i=0; i<currBoard.rows; i++){
                for(int j=0; j<currBoard.columns; j++){
                    if(currBoard.getCellState(i,j)==3){
                        bw.write(i+"."+j+"\n");
                    }else if(currBoard.getCellState(i,j)==1||currBoard.getCellState(i,j)==2){
                        bw.write(i+"."+j+":"+currBoard.getCellState(i,j)+"\n");
                    }
                }
            }

        }catch(FileNotFoundException ferr){
            throw new FileException(ferr.getMessage());
        }catch(IOException err) {
            throw new FileException(err.getMessage());
        }finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if  (fw != null){
                    fw.close();
                }
            }catch(IOException err){
                err.printStackTrace();
            }
        }
    }
    public void imprintToBoard(String compType,int[] loc,int rotation,boolean isConnected) throws IndexOutOfBoundsException{
        ComponentFactory compFact = new ComponentFactory();
        Component newComp = compFact.getComponent(compType,loc,rotation,isConnected);
        if(newComp!=null) {
            try {
                imprintComponent(newComp);
            }catch(IndexOutOfBoundsException out){
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
                    if (j >= currBoard.columns||j<0) {
                        continue;
                    }

                    if (currBoard.getCellState(i,j) == 1) {
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
    public void stop(){
        this.numGen=0;
        this.keepRunning=false;
    }
    private void imprintComponent(Component newComp) {//nie spodoba ci się to Szymon więc lepiej nie patrz MACIEK
        int[][] tempState=new int[currBoard.rows][currBoard.columns];
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
        if(newComp.wire) {
            if (newComp.rotation == 0) {
                for (int[] in : newComp.input) {
                    for (int i = newComp.loc[1] + in[1] - 1; i >= 0; i--) {
                        if (isWire(in[0] + newComp.loc[0],i,false)) {
                            break;
                        }
                        currBoard.setCellState(in[0] + newComp.loc[0],i,3);
                    }
                }

                for (int[] out : newComp.output) {
                    for (int i = out[1] + newComp.loc[1] + 1; i < currBoard.columns; i++) {
                        if (isWire(out[0] + newComp.loc[0],i,false)) {
                            break;
                        }
                        //this.cellStates[out[0] + newComp.loc[0]][i] = 3;
                        currBoard.setCellState(out[0] + newComp.loc[0],i,3);

                    }
                }
            } else if (newComp.rotation == 1) {
                for (int[] in : newComp.input) {
                    for (int i = newComp.loc[0] + in[0] - 1; i >= 0; i--) {
                        if (isWire(i,in[1] + newComp.loc[1],true)) {
                            break;
                        }
                        //this.cellStates[i][in[1] + newComp.loc[1]] = 3;
                        currBoard.setCellState(i,in[i]+newComp.loc[1],3);
                    }
                }
                for (int[] out : newComp.output) {
                    for (int i = out[0] + newComp.loc[0] + 1; i < currBoard.rows; i++) {
                        if (isWire(i,out[1] + newComp.loc[1],true)) {
                            break;
                        }
                        //this.cellStates[i][out[1] + newComp.loc[1]] = 3;
                        currBoard.setCellState(i,out[1]+newComp.loc[1],3);
                    }
                }
            } else if (newComp.rotation == 2) {
                for (int[] in : newComp.input) {
                    for (int i = newComp.loc[1] + in[1] + 1; i < currBoard.columns; i++) {
                        if (isWire(in[0] + newComp.loc[0],i,false)) {
                            break;
                        }
                        //this.cellStates[in[0] + newComp.loc[0]][i] = 3;
                        currBoard.setCellState(in[0] + newComp.loc[0],i,3);
                    }
                }

                for (int[] out : newComp.output) {
                    for (int i = out[1] + newComp.loc[1] - 1; i >= 0; i--) {
                        if (isWire(out[0] + newComp.loc[0],i,false)) {
                            break;
                        }
                        //this.cellStates[out[0] + newComp.loc[0]][i] = 3;
                        currBoard.setCellState(out[0] + newComp.loc[0],i,3);
                    }
                }
            } else if (newComp.rotation == 3) {
                for (int[] in : newComp.input) {
                    for (int i = newComp.loc[0] + in[0] + 1; i < currBoard.rows; i++) {
                        if (isWire(i,in[1] + newComp.loc[1],true)) {
                            break;
                        }
                        //this.cellStates[i][in[1] + newComp.loc[1]] = 3;
                        currBoard.setCellState(i,in[1] + newComp.loc[1],3);
                    }
                }
                for (int[] out : newComp.output) {
                    for (int i = out[0] + newComp.loc[0] - 1; i >= 0; i--) {
                        if (isWire(i,out[1] + newComp.loc[1],true)) {
                            break;
                        }
                        //this.cellStates[i][out[1] + newComp.loc[1]] = 3;
                        currBoard.setCellState(i,out[1] + newComp.loc[1],3);
                    }
                }
            }
        }
    }
    private boolean isWire(int i,int j,boolean vertical){//helper function
        if(vertical){
            for(int k=j-1;k<=j+1;k++){
                if(k<0||k>=currBoard.columns){
                    continue;
                }
                if(currBoard.getCellState(i,k) == 3){
                    return true;
                }
            }
        }else{
            for(int k=i-1;k<=i+1;k++){
                if(k<0||k>=currBoard.rows){
                    continue;
                }
                if(currBoard.getCellState(k,j) == 3){
                    return true;
                }
            }
        }
        return false;
    }
    public ArrayList<Board> getMemory() {
        return memory;
    }
    public Board getCurrBoard() {
        return currBoard;
    }


}
