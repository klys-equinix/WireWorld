package gui;

import applogic.SettingsListener;
import applogic.SettingsManager;
import applogic.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Szymon on 23.04.2017.
 */
public class ControlPanel {
    private JButton wczytajButton;
    private JSpinner spinnerGenNum;
    private JTextField textFileDir;
    private JButton generujButton;
    private JPanel mainPanel;
    private JRadioButton nieograniczonaTylkoPodglądRadioButton;
    private JRadioButton ustalonaLiczbaGeneracjiRadioButton;
    private JPanel optionsPanel;
    private JPanel fixedOptPanels;
    private JTextField headElecColorField;
    private JTextField tailElecColorField;
    private JButton headColorButton;
    private JButton tailColorButton;
    private JTextField cableColorField;
    private JButton cableColorButton;
    private JButton wczytajKonfiguracjęButton;
    private JButton zapiszKonfiguracjęButton;
    private JFileChooser fc = new JFileChooser();

    private String filePath;

    public ControlPanel() {
        tailElecColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleTailColor()));
        headElecColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleHeadColor()));
        cableColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameCableColor()));

        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
        spinnerGenNum.setModel(model);
        wczytajButton.addActionListener(e -> {
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                textFileDir.setText(file.getName());
                filePath = file.getAbsolutePath();
                maintainGenButton();
            }
        });
        spinnerGenNum.addChangeListener(e -> maintainGenButton());
        generujButton.addActionListener(e -> {
            SettingsManager setman = SettingsManager.getInstance();
            if (ustalonaLiczbaGeneracjiRadioButton.isSelected())
                setman.setAppMode(SettingsManager.APP_MODE_FIXED);
            else
                setman.setAppMode(SettingsManager.APP_MODE_INF);

            // Time to open next window
            applogic.WireWorld.initGameWindow(filePath, (int) spinnerGenNum.getValue());
            System.out.println("WireWorld GW: "+filePath+","+spinnerGenNum.getValue());
        });
        nieograniczonaTylkoPodglądRadioButton.addItemListener(e -> {
            JRadioButton j = (JRadioButton) e.getSource();
            if (j.isEnabled())
                fixedOptPanels.setVisible(false);
            maintainGenButton();
        });
        ustalonaLiczbaGeneracjiRadioButton.addItemListener(e -> {
            JRadioButton j = (JRadioButton) e.getSource();
            if (j.isEnabled())
                fixedOptPanels.setVisible(true);
            maintainGenButton();
        });

        ButtonGroup group = new ButtonGroup();
        group.add(nieograniczonaTylkoPodglądRadioButton);
        group.add(ustalonaLiczbaGeneracjiRadioButton);
        headColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Wybierz kolor głowy elektronu", new Color(255, 255 ,255));
            if(newColor != null)
            {
                headElecColorField.setText(Utils.getColorAsVector(newColor));
                SettingsManager.getInstance().setGameEleHeadColor(newColor);
            }
        });
        tailColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Wybierz kolor ogonu elektronu", new Color(255, 255 ,255));
            if(newColor != null)
            {
                tailElecColorField.setText(Utils.getColorAsVector(newColor));
                SettingsManager.getInstance().setGameEleTailColor(newColor);
            }
        });
        cableColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Wybierz kolor ogonu elektronu", new Color(255, 255 ,255));
            if(newColor != null)
            {
                cableColorField.setText(Utils.getColorAsVector(newColor));
                SettingsManager.getInstance().setGameCableColor(newColor);
            }
        });
        zapiszKonfiguracjęButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                SettingsManager.getInstance().saveToFile(file);
                JOptionPane.showMessageDialog(null,"Konfiguracja została pomyślnie zapisana!","WireWorld - zapis konfiguracji",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        SettingsManager.getInstance().addListener(() -> {
            tailElecColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleTailColor()));
            headElecColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleHeadColor()));
            cableColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameCableColor()));
        });
        wczytajKonfiguracjęButton.addActionListener(e -> {
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                SettingsManager.getInstance().loadFromFile(fc.getSelectedFile());
                JOptionPane.showMessageDialog(null,"Konfiguracja została pomyślnie wczytana!","WireWorld - odczyt konfiguracji",JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void maintainGenButton() {
        if (ustalonaLiczbaGeneracjiRadioButton.isSelected()) {
            if ((Integer) spinnerGenNum.getValue() != 0) {
                if (textFileDir.getText().isEmpty() == false) {
                    if (generujButton.isEnabled() == false)
                        generujButton.setEnabled(true);
                }
            } else if (generujButton.isEnabled() == true)
                generujButton.setEnabled(false);
        } else {
            if (textFileDir.getText().isEmpty() == false) {
                if (generujButton.isEnabled() == false)
                    generujButton.setEnabled(true);
            }
        }
    }

    public JPanel getPanel() {
        return this.mainPanel;
    }
}
