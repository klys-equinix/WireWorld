package gui;

import applogic.SettingsManager;
import applogic.WireWorld;
import gamelogic.Board;
import gui.BoardRenderer.BoardRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Szymon on 23.04.2017.
 */
public class GamePanel {
    private JSlider genSlider;
    private JButton autoButton;
    private JButton manualButton;
    private JPanel mainPanel;
    private JTextField genField;
    private JPanel infPanel;
    private JPanel fixedPanel;
    private JSlider slider2;
    private BoardRenderer boardRender;
    private JScrollPane scrollPane;

    public GamePanel(Board board) {
        SettingsManager setman = SettingsManager.getInstance();
        if (setman.getAppMode() == SettingsManager.APP_MODE_FIXED)
            infPanel.setVisible(false);
        else
            fixedPanel.setVisible(false);

        boardRender.setBoard(board);
        genSlider.setMaximum(SettingsManager.getInstance().getAppFixedGen());

        slider2.addChangeListener(e -> {
            if (!((JSlider) e.getSource()).getValueIsAdjusting()) {
                System.out.println(((JSlider) e.getSource()).getValue());
                boardRender.setZoom(((JSlider) e.getSource()).getValue());
            }
        });
        genSlider.addChangeListener(e -> {
            if (!((JSlider) e.getSource()).getValueIsAdjusting()) {
                if(SettingsManager.getInstance().getAppFixedMode() == SettingsManager.APP_FIXED_MANUAL) {
                    JSlider instance = (JSlider) e.getSource();
                    SettingsManager.getInstance().setAppFixedCurGen(instance.getValue());
                    boardRender.setBoard(WireWorld.getGenBoard(instance.getValue()));
                    boardRender.repaint();
                }
            }
        });
        manualButton.addActionListener(e -> {
            autoButton.setEnabled(true);
            manualButton.setEnabled(false);
            genSlider.setEnabled(true);
            SettingsManager.getInstance().setAppFixedMode(SettingsManager.APP_FIXED_MANUAL);
        });
        autoButton.addActionListener(e -> {
            manualButton.setEnabled(true);
            autoButton.setEnabled(false);
            genSlider.setEnabled(false);
            SettingsManager.getInstance().setAppFixedMode(SettingsManager.APP_FIXED_AUTO);
        });
    }

    public JTextField getGenField() { return genField; }

    public JSlider getGenSlider() { return genSlider; }

    public JButton getAutoButton() { return autoButton; }
    public JButton getManualButton() { return manualButton; }

    public JPanel getPanel() {
        return mainPanel;
    }

    public BoardRenderer getBoardRenderer() {
        return boardRender;
    }
}
