package applogic;

import gamelogic.Simulation;
import gamelogic.Board;
import gamelogic.Simulation;
import gui.ControlPanel;
import gui.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        Simulation sim = new Simulation(10,10);//creating a simulation with an empty board
        int[] loc ={1,1};//location of topmost indexes of an element-where should it be placed on the board
        sim.imprintToBoard("ClockGen",loc,2);//imprinting the element on the empty board
        sim.start(10);
        sim.getCurrBoard().drawBoard();
        sim.writeGenToFile("newFile");
        Simulation sim1 = new Simulation("./newFile");
        sim1.getCurrBoard().drawBoard();
    }

    public static void initGameWindow(String filePath, int genNum) {
        final int genInt = genNum;
        Simulation sim = new Simulation(filePath);
        GamePanel gp = new GamePanel(sim.getCurrBoard());
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
    }
}
