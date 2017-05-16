package WindowHandlers;

import Core.*;
import Core.Settings.SettingsManager;
import Core.Utils.Utils;
import com.sun.scenario.Settings;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Szymon on 23.04.2017.
 */
public class ControlHandler implements WindowHandler {
    private JButton loadBoardButton;
    private JSpinner spinnerGenNum;
    private JTextField boardDirField;
    private JButton generateButton;
    private JRadioButton infiniteRadioButton;
    private JRadioButton fixedRadioButton;
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
    private JButton runEditorButton;
    private JFileChooser fc = new JFileChooser();
    private JPanel contentPanel;
    private JTextField backgroundColorField;
    private JButton backgroundColorButton;
    private JFormattedTextField genTimeField;
    private JCheckBox rysujKrawędzieKomórekCheckBox;

    private JFrame controlFrame;

    private String filePath;

    public ControlHandler() {
        tailElecColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleTailColor()));
        headElecColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleHeadColor()));
        cableColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameCableColor()));
        backgroundColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameBackgroundColor()));
        genTimeField.setText(Integer.toString(SettingsManager.getInstance().getGameGenTime()));
        rysujKrawędzieKomórekCheckBox.setSelected(SettingsManager.getInstance().getGameDrawOutline());

        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
        spinnerGenNum.setModel(model);
        loadBoardButton.addActionListener(e -> {
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                boardDirField.setText(file.getName());
                filePath = file.getAbsolutePath();
                maintainGenButton();
            }
        });
        spinnerGenNum.addChangeListener(e -> maintainGenButton());
        generateButton.addActionListener(e -> {
            SettingsManager setman = SettingsManager.getInstance();
            if (fixedRadioButton.isSelected())
                setman.setAppMode(SettingsManager.APP_MODE_FIXED);
            else
                setman.setAppMode(SettingsManager.APP_MODE_INF);

            // Time to open next window
            Core.WireWorld.runGame(filePath, (int) spinnerGenNum.getValue());
        });
        infiniteRadioButton.addItemListener(e -> {
            JRadioButton j = (JRadioButton) e.getSource();
            if (j.isEnabled())
                fixedOptPanels.setVisible(false);
            maintainGenButton();
        });
        fixedRadioButton.addItemListener(e -> {
            JRadioButton j = (JRadioButton) e.getSource();
            if (j.isEnabled())
                fixedOptPanels.setVisible(true);
            maintainGenButton();
        });

        ButtonGroup group = new ButtonGroup();
        group.add(infiniteRadioButton);
        group.add(fixedRadioButton);
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
                Utils.saveSettingsToFile(file);
                JOptionPane.showMessageDialog(null,"Konfiguracja została pomyślnie zapisana!","WireWorld - zapis konfiguracji",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        SettingsManager.getInstance().addListener(() -> {
            tailElecColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleTailColor()));
            headElecColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameEleHeadColor()));
            cableColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameCableColor()));
            backgroundColorField.setText(Utils.getColorAsVector(SettingsManager.getInstance().getGameBackgroundColor()));
            genTimeField.setText(Integer.toString(SettingsManager.getInstance().getGameGenTime()));
            rysujKrawędzieKomórekCheckBox.setSelected(SettingsManager.getInstance().getGameDrawOutline());
        });
        wczytajKonfiguracjęButton.addActionListener(e -> {
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Utils.loadSettingsFromFile(fc.getSelectedFile());
                JOptionPane.showMessageDialog(null,"Konfiguracja została pomyślnie wczytana!","WireWorld - odczyt konfiguracji",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        runEditorButton.addActionListener(e -> WireWorld.runEditor());
        backgroundColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Wybierz kolor tła", new Color(255, 255 ,255));
            if(newColor != null)
            {
                backgroundColorField.setText(Utils.getColorAsVector(newColor));
                SettingsManager.getInstance().setGameBackgroundColor(newColor);
            }
        });

        genTimeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                JTextField field = (JTextField) e.getSource();
                if(!field.getText().isEmpty())
                    SettingsManager.getInstance().setGameGenTime(Integer.parseInt(field.getText()));
            }
        });
        rysujKrawędzieKomórekCheckBox.addActionListener(e -> {
            JCheckBox comp = (JCheckBox)e.getSource();
            SettingsManager.getInstance().setGameDrawOutline(comp.isSelected());
        });
    }

    private void maintainGenButton() {
        if (fixedRadioButton.isSelected()) {
            if ((Integer) spinnerGenNum.getValue() != 0) {
                if (boardDirField.getText().isEmpty() == false) {
                    if (generateButton.isEnabled() == false)
                        generateButton.setEnabled(true);
                }
            } else if (generateButton.isEnabled() == true)
                generateButton.setEnabled(false);
        } else {
            if (boardDirField.getText().isEmpty() == false) {
                if (generateButton.isEnabled() == false)
                    generateButton.setEnabled(true);
            }
        }
    }

    @Override
    public void createWindow() {
        controlFrame = new JFrame();
        controlFrame.setContentPane(this.getContentPanel());
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setTitle("WireWorld - ustawienia");
        controlFrame.pack();
        controlFrame.setLocationRelativeTo(null);
        controlFrame.setResizable(false);
        controlFrame.setVisible(true);
    }

    @Override
    public void destroyWindow() {
        if(controlFrame.isVisible() == true)
            controlFrame.setVisible(false);
        controlFrame.dispose();
    }

    @Override
    public void showWindow() {
        controlFrame.setVisible(true);
    }

    @Override
    public void hideWindow() {
        controlFrame.setVisible(false);
    }

    @Override
    public JPanel getContentPanel() {
        return contentPanel;
    }

    private void createUIComponents() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        formatter.setMinimum(0);
        formatter.setMaximum(100000);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        genTimeField = new JFormattedTextField(formatter);
    }
}
