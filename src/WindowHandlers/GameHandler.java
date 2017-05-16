package WindowHandlers;

import Core.Settings.SettingsManager;
import Core.WindowHandler;
import WireComponents.Board;
import WindowHandlers.BoardRenderer.BoardRenderer;
import WireSimulator.BoardController;
import WireSimulator.SimulatorTimer;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Szymon on 23.04.2017.
 */
public class GameHandler implements WindowHandler {
    private JSlider genSlider;
    private JButton autoButton;
    private JButton manualButton;
    private JPanel contentPanel;
    private JTextField genField;
    private JPanel infPanel;
    private JPanel fixedPanel;
    private JSlider zoomSlider;
    private BoardRenderer boardRenderer;
    private JScrollPane scrollPane;
    private JButton pauseButton;
    private JButton infSaveButton;
    private JButton fixedSaveButton;

    private JFrame gameFrame;

    private java.util.Timer timer = new java.util.Timer();

    private boolean pauseButtonState = true;

    public GameHandler() {
        SettingsManager setman = SettingsManager.getInstance();
        if (setman.getAppMode() == SettingsManager.APP_MODE_FIXED)
            infPanel.setVisible(false);
        else
            fixedPanel.setVisible(false);

        genSlider.setMaximum(SettingsManager.getInstance().getAppFixedGen());
        boardRenderer.setBoard(BoardController.getInstance().getCurrBoard());

        zoomSlider.addChangeListener(e -> {
            if (!((JSlider) e.getSource()).getValueIsAdjusting()) {
                boardRenderer.setZoom(((JSlider) e.getSource()).getValue());
            }
        });
        genSlider.addChangeListener(e -> {
            if (!((JSlider) e.getSource()).getValueIsAdjusting()) {
                if(SettingsManager.getInstance().getAppFixedMode() == SettingsManager.APP_FIXED_MANUAL) {
                    JSlider instance = (JSlider) e.getSource();
                    SettingsManager.getInstance().setAppFixedCurGen(instance.getValue());
                    boardRenderer.setBoard(BoardController.getInstance().getMemory().get(instance.getValue()));
                    boardRenderer.repaint();
                }
            }
        });
        manualButton.addActionListener(e -> {
            autoButton.setEnabled(true);
            manualButton.setEnabled(false);
            genSlider.setEnabled(true);
            SettingsManager.getInstance().setAppFixedMode(SettingsManager.APP_FIXED_MANUAL);
            fixedSaveButton.setEnabled(true);
        });
        autoButton.addActionListener(e -> {
            manualButton.setEnabled(true);
            autoButton.setEnabled(false);
            genSlider.setEnabled(false);
            SettingsManager.getInstance().setAppFixedMode(SettingsManager.APP_FIXED_AUTO);
            fixedSaveButton.setEnabled(false);
        });
        scrollPane.setBackground(SettingsManager.getInstance().getGameBackgroundColor());
        boardRenderer.setBackground(SettingsManager.getInstance().getGameBackgroundColor());
        pauseButton.addActionListener(e -> {
            if(pauseButtonState) {
                stopSimTimer();
                pauseButton.setText("Start");
                infSaveButton.setEnabled(true);
                pauseButtonState = false;
            } else {
                startSimTimer();
                pauseButton.setText("Pauza");
                infSaveButton.setEnabled(false);
                pauseButtonState = true;
            }
        });
    }

    @Override
    public void createWindow() {
        gameFrame = new JFrame();
        gameFrame.setContentPane(this.contentPanel);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setTitle("WireWorld - symulacja");
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
    }

    @Override
    public void destroyWindow() {
        if(gameFrame.isVisible() == true)
            gameFrame.setVisible(false);
        gameFrame.dispose();
    }

    @Override
    public void showWindow() {
        gameFrame.setVisible(true);
        startSimTimer();
    }

    @Override
    public void hideWindow() {
        gameFrame.setVisible(false);
        stopSimTimer();
    }

    @Override
    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setGenSliderValue(int value)
    {
        genSlider.setValue(value);
    }

    public void setBoard(Board board)
    {
        boardRenderer.setBoard(board);
        boardRenderer.repaint();
    }

    public void setGenField(int value)
    {
        genField.setText(Integer.toString(value));
    }

    private void startSimTimer()
    {
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new SimulatorTimer(this), SettingsManager.getInstance().getGameGenTime(), SettingsManager.getInstance().getGameGenTime());
    }

    private void stopSimTimer()
    {
        timer.cancel();
        timer.purge();
    }
}
