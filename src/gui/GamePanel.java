package gui;

import applogic.SettingsManager;
import gui.BoardRenderer.BoardRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
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
    private JSlider slider1;
    private JButton autoButton;
    private JButton manualButton;
    private JPanel mainPanel;
    private JTextField textField1;
    private JPanel infPanel;
    private JPanel fixedPanel;
    private JSlider slider2;
    private BoardRenderer boardRender;
    private JScrollPane scrollPane;

    public GamePanel() {
        SettingsManager setman = SettingsManager.getInstance();
        if (setman.getAppMode() == SettingsManager.APP_MODE_FIXED)
            infPanel.setVisible(false);
        else
            fixedPanel.setVisible(false);

        scrollPane.setViewport(null);
        scrollPane.setViewportView(boardRender);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Rectangle bounds = scrollPane.getViewport().getViewRect();

                JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
                horizontal.setValue((horizontal.getMaximum() - bounds.width) / 2);

                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue((vertical.getMaximum() - bounds.height) / 2);
            }
        });

        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!((JSlider) e.getSource()).getValueIsAdjusting()) {
                    System.out.println(((JSlider) e.getSource()).getValue());
                    boardRender.setZoom(((JSlider) e.getSource()).getValue());
                    scrollPane.setViewport(null);
                    scrollPane.setViewportView(boardRender);
                }
            }
        });
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}
