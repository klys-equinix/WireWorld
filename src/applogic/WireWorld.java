package applogic;

import gamelogic.FileException;
import gamelogic.Simulation;
import gamelogic.Board;
import gui.ControlPanel;
import gui.GamePanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Konrad on 20.04.2017.
 */
public class WireWorld {
    private static JFrame controlFrame;
    private static JFrame gameFrame;

    public static void main(String args[]){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        /*
            It's time to open control frame
        */
        controlFrame = new JFrame();
        controlFrame.setContentPane(new ControlPanel().getPanel());
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.pack();
        controlFrame.setLocationRelativeTo(null);
        controlFrame.setResizable(false);
        controlFrame.setVisible(true);
        try {
            Simulation sim = new Simulation(20, 20);//creating a simulation with an empty board
            int[] loc = {5, 1};//location of topmost indexes of an element-where should it be placed on the board
            sim.imprintToBoard("ClockGen", loc, 0, false);//imprinting the element on the empty board
            int[] nloc = {2, 10};//location of the second element
            sim.imprintToBoard("ExORgate", nloc, 2, true);//if placed correctly , it will connect itself to other components
            sim.start(10);
            sim.writeGenToFile("newFile");
        }catch(IndexOutOfBoundsException err){
            System.out.print(err.getMessage());
        }catch(FileException ferr){
            System.out.print(ferr.getMessage());
        }
        Simulation sim1;
        try {
            sim1 = new Simulation("./newFile");
            sim1.getCurrBoard().drawBoard();
        }catch(FileException err){

        }
        ;
    }

    public static void initGameWindow(String filePath, int genNum) {
        final int genInt = genNum;
        Simulation sim;
        GamePanel gp;
        try {
           sim = new Simulation(filePath);
           gp = new GamePanel(sim.getCurrBoard());
            gameFrame = new JFrame();
            gameFrame.setContentPane(gp.getPanel());
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);

            ArrayList<Board> board = new ArrayList<Board>();

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(SettingsManager.getInstance().getAppMode() == SettingsManager.APP_MODE_FIXED)
                    {
                        if(genInt != 0)
                        {
                            sim.nextGeneration();
                            Board board = sim.getCurrBoard();
                            gp.getBoardRenderer().setBoard(board);
                            gp.getBoardRenderer().repaint();
                            //genInt--;
                        }
                    }
                    else {
                        sim.nextGeneration();
                        Board board = sim.getCurrBoard();
                        gp.getBoardRenderer().setBoard(board);
                        gp.getBoardRenderer().repaint();
                    }
                }
            }, 750, 750);
            // Closing control frame - no longer needed
            controlFrame.setVisible(false);
            controlFrame.dispose();
        }catch(FileException err){
            //handle it the way you want
        }


    }
}
