package gui;

import applogic.SettingsManager;
import javafx.scene.control.RadioButton;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private JPanel fixedOptions;
    private JFileChooser fc = new JFileChooser();

    public ControlPanel() {
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
        spinnerGenNum.setModel(model);
        wczytajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    textFileDir.setText(file.getName());
                    maintainGenButton();
                    //This is where a real application would open the file.
                    System.out.println("Opening: " + file.getName() + ".");
                } else {
                    System.out.println("Open command cancelled by user.");
                }
            }
        });
        spinnerGenNum.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                maintainGenButton();
            }
        });
        generujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsManager setman = SettingsManager.getInstance();
                if (ustalonaLiczbaGeneracjiRadioButton.isSelected())
                    setman.setAppMode(SettingsManager.APP_MODE_FIXED);
                else
                    setman.setAppMode(SettingsManager.APP_MODE_INF);

                // Time to open next window
                applogic.WireWorld.initGameWindow(textFileDir.getText(), (int) spinnerGenNum.getValue());
            }
        });
        nieograniczonaTylkoPodglądRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JRadioButton j = (JRadioButton) e.getSource();
                if (j.isEnabled())
                    fixedOptions.setVisible(false);
                maintainGenButton();
            }
        });
        ustalonaLiczbaGeneracjiRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JRadioButton j = (JRadioButton) e.getSource();
                if (j.isEnabled())
                    fixedOptions.setVisible(true);
                maintainGenButton();
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(nieograniczonaTylkoPodglądRadioButton);
        group.add(ustalonaLiczbaGeneracjiRadioButton);
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
