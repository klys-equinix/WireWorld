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

    public Simulation(String fileName){
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
            this.currBoard.drawBoard();
        }catch(FileNotFoundException ferr){
            ferr.printStackTrace();
            return;
        }catch(IOException err){
            err.printStackTrace();
            return;
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
                if  (fr != null){
                    fr.close();
                }
            }catch(IOException err){
                err.printStackTrace();
            }
        }
        this.numGen=0;
    }

    public void imprintToBoard(String compType,int[] loc,int rotation){
        ComponentFactory compFact = new ComponentFactory();
        Component newComp = compFact.getComponent(compType,loc,rotation);
        if(newComp!=null) {
            this.currBoard.imprintComponent(newComp);
        }
    }

    public void start(){

        this.keepRunning=true;
        while(this.keepRunning){
                nextGeneration();
                numGen--;
                this.currBoard.drawBoard();
        }

    }
    public void start(int numGen){//changed-now there are no constructors specyfying the numGen
        this.numGen=numGen;

        if(this.numGen!=0) {
            while (numGen > 0) {
                nextGeneration();
                numGen--;
                this.currBoard.drawBoard();
            }
        }
    }

    private void nextGeneration(){
        if(firstGen){
            this.numGen=numGen;
            this.currBoard.drawBoard();
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
        this.memory.add(currBoard);//to be deleted after integration with drawing module

    }

    public void stop(){
        this.numGen=0;
        this.keepRunning=false;
    }

    public ArrayList<Board> getMemory() {
        return memory;
    }
}
