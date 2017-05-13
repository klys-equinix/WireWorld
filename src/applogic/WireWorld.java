package applogic;

import gamelogic.FileException;
import gamelogic.Board;
import gamelogic.BoardController;
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

    private static GamePanel gp;

    private static int genNum;
    private static ArrayList boardArray;

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
            BoardController.getInstance().init(20,20);//creating a simulation with an empty board
            int[] loc = {5, 1};//location of topmost indexes of an element-where should it be placed on the board
            BoardController.getInstance().placeOnBoard("ClockGen", loc, 0, false);//imprinting the element on the empty board
            int[] nloc = {2, 10};//location of the second element
            BoardController.getInstance().placeOnBoard("ExORgate", nloc, 2, true);//if placed correctly , it will connect itself to other components
            BoardController.getInstance().start(10);
            BoardController.getInstance().writeGenToFile("newFile");
        }catch(IndexOutOfBoundsException err){
            System.out.print(err.getMessage());
        }catch(FileException ferr){
            System.out.print(ferr.getMessage());
        }
        try {
            BoardController.reset();
            BoardController.getInstance().init("./newFile");
            BoardController.getInstance().drawBoard();
            BoardController.reset();
        }catch(FileException err){

        }

    }
    public static Board getGenBoard(int genNum)
    {
        return (Board) boardArray.get(genNum - 1);
    }
    public static void initGameWindow(String filePath, int genNum) {
        try {
            BoardController.getInstance().init(filePath);
        }
        catch(FileException e)
        {
            // To catch
            return;
        }

        SettingsManager.getInstance().setAppFixedGen(genNum);

        gp = new GamePanel(BoardController.getInstance().getCurrBoard());
        gameFrame = new JFrame();
        gameFrame.setContentPane(gp.getPanel());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        if(SettingsManager.getInstance().getAppMode() == SettingsManager.APP_MODE_FIXED)
        {
            boardArray = new ArrayList<Board>();//Po co ci boardArray skoro BoardController trzyma w pamieci wszytskie generacje?
            for(int i = 0; i < genNum; i++) {
                BoardController.getInstance().nextGeneration();
                boardArray.add(BoardController.getInstance().getCurrBoard());
            }
        }


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SimHandler(), 750, 750);

        // Closing control frame - no longer needed
        controlFrame.setVisible(false);
        controlFrame.dispose();
    }
    static class SimHandler extends TimerTask {
        @Override
        public void run() {
            if(SettingsManager.getInstance().getAppMode() == SettingsManager.APP_MODE_FIXED)
            {
                if(SettingsManager.getInstance().getAppFixedMode() == SettingsManager.APP_FIXED_AUTO) {
                    if(SettingsManager.getInstance().getAppFixedCurGen() < SettingsManager.getInstance().getAppFixedGen()) {
                        gp.getGenSlider().setValue(SettingsManager.getInstance().getAppFixedCurGen()+1);
                        gp.getBoardRenderer().setBoard((Board) boardArray.get(SettingsManager.getInstance().getAppFixedCurGen()));
                        gp.getBoardRenderer().repaint();
                        SettingsManager.getInstance().setAppFixedCurGen(SettingsManager.getInstance().getAppFixedCurGen()+1);
                    }
                    else
                        SettingsManager.getInstance().setAppFixedCurGen(0);
                }
            }
            else {
                BoardController.getInstance().nextGeneration();
                Board board = BoardController.getInstance().getCurrBoard();
                gp.getBoardRenderer().setBoard(board);
                gp.getBoardRenderer().repaint();
                gp.getGenField().setText("" + SettingsManager.getInstance().getAppFixedCurGen());
                SettingsManager.getInstance().setAppFixedCurGen(SettingsManager.getInstance().getAppFixedCurGen()+1);
            }
        }
    }
}
