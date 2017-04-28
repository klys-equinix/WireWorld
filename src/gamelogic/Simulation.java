package gamelogic;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Konrad on 20.04.2017.
 */
public class Simulation {
    private ArrayList<Board> memory = new ArrayList<>();
    private int numGen;
    private Board currBoard;
    private boolean firstGen=true;
    public Board getCurrBoard() {
        return currBoard;
    }

    private boolean keepRunning;

    public Simulation(int rows, int columns){
        this.currBoard = new Board(rows,columns);
        this.numGen=0;
    }

    public Simulation(String fileName) throws FileException {
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

    public void imprintToBoard(String compType,int[] loc,int rotation,boolean isConnected) throws IndexOutOfBoundsException{
        ComponentFactory compFact = new ComponentFactory();
        Component newComp = compFact.getComponent(compType,loc,rotation,isConnected);
        if(newComp!=null) {
            try {
                this.currBoard.imprintComponent(newComp);
            }catch(IndexOutOfBoundsException out){
                throw new IndexOutOfBoundsException(out.getMessage());
            }
        }
    }

    public void start(){

        this.keepRunning=true;
        while(this.keepRunning){
                nextGeneration();
                numGen--;
        }

    }
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
                int newState = currBoard.evalCell(i,j);
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
    public void stop(){
        this.numGen=0;
        this.keepRunning=false;
    }

    public ArrayList<Board> getMemory() {
        return memory;
    }

}
