package WindowHandlers;

import Core.Utils.Utils;
import Core.WindowHandler;
import WindowHandlers.BoardRenderer.BoardRenderer;
import WindowHandlers.BoardRenderer.EditorRenderer;
import WireComponents.FileException;
import WireSimulator.BoardController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

/**
 * Created by Szymon on 11.05.2017.
 */
public class EditorHandler implements WindowHandler {
    private JPanel contentPanel;
    private JButton loadButton;
    private JButton saveButton;
    private JFrame edFrame;

    private EditorRenderer editorRenderer;
    private JScrollPane scrollPane;
    private JSlider zoomSlider;
    private JRadioButton diodaRadioButton;
    private JRadioButton bramkaORRadioButton;
    private JRadioButton cableButton;
    private JRadioButton elecHeadButton;
    private JRadioButton elecTailButton;
    private JButton nowyButton;
    private JPanel gamePanel;
    private JPanel controlPanel;
    private JSpinner rotationSpinner;
    private JCheckBox połączCheckBox;
    private JRadioButton pulsarButton;
    private JRadioButton exORButton;

    private JFileChooser fc = new JFileChooser();

    private int mouseCellX;
    private int mouseCellY;

    public EditorHandler() {
        zoomSlider.addChangeListener(e -> {
            if (!((JSlider) e.getSource()).getValueIsAdjusting()) {
                editorRenderer.setZoom(((JSlider) e.getSource()).getValue());
            }
        });
        editorRenderer.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseCellX = (int) Math.floor(e.getX()/zoomSlider.getValue());
                mouseCellY = (int) Math.floor(e.getY()/zoomSlider.getValue());
                editorRenderer.setMouseCell(mouseCellX, mouseCellY);
                editorRenderer.repaint();
            }
        });
        editorRenderer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int position[] = {mouseCellY, mouseCellX};
                if(cableButton.isSelected())
                    BoardController.getInstance().getCurrBoard().setCellState(mouseCellY, mouseCellX, 3);
                else if(elecHeadButton.isSelected())
                    BoardController.getInstance().getCurrBoard().setCellState(mouseCellY, mouseCellX, 1);
                else if(elecTailButton.isSelected())
                    BoardController.getInstance().getCurrBoard().setCellState(mouseCellY, mouseCellX, 2);
                else if(diodaRadioButton.isSelected())
                    BoardController.getInstance().placeOnBoard("Diode", position, (int) rotationSpinner.getValue()/90, połączCheckBox.isSelected());
                else if(bramkaORRadioButton.isSelected())
                    BoardController.getInstance().placeOnBoard("ORgate", position, (int) rotationSpinner.getValue()/90, połączCheckBox.isSelected());
                else if(exORButton.isSelected())
                    BoardController.getInstance().placeOnBoard("ExORgate", position, (int) rotationSpinner.getValue()/90, połączCheckBox.isSelected());
                else if(pulsarButton.isSelected())
                    BoardController.getInstance().placeOnBoard("ClockGen", position, (int) rotationSpinner.getValue()/90, połączCheckBox.isSelected());
                editorRenderer.setBoard(BoardController.getInstance().getCurrBoard());
            }
        });
        ButtonGroup group = new ButtonGroup();
        group.add(diodaRadioButton);
        group.add(bramkaORRadioButton);
        group.add(cableButton);
        group.add(elecHeadButton);
        group.add(elecTailButton);
        group.add(exORButton);
        group.add(pulsarButton);
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    BoardController.getInstance().writeGenToFile(file.getAbsolutePath());
                } catch (FileException e1) {
                    JOptionPane.showMessageDialog(null, e.toString(),"WireWorld - zapis matrycy", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(null,"Matryca została zapisana pomyślnie!","WireWorld - zapis matrycy", JOptionPane.INFORMATION_MESSAGE);
            }
            makeEditorAccessible();
        });
        loadButton.addActionListener(e -> {
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    BoardController.getInstance().init(file.getAbsolutePath());
                } catch (FileException e1) {
                    JOptionPane.showMessageDialog(null, e.toString(),"WireWorld - odczyt matrycy", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(null,"Matryca została wczytana pomyślnie!","WireWorld - odczyt matrycy", JOptionPane.INFORMATION_MESSAGE);
            }
            makeEditorAccessible();
        });
        nowyButton.addActionListener(e -> {
            String str = (String) JOptionPane.showInputDialog(
                null,
                "Wielkość matrycy w formacie: [KOLUMNY]x[WIERSZE]",
                "WireWorld - nowa matryca",
                JOptionPane.PLAIN_MESSAGE);
            String val[] = str.split("x");
            if(val.length != 2) {
                JOptionPane.showMessageDialog(null, "Ups.. Format wprowadzonych danych niezgdony z oczekiwanym!", "WireWorld - stworzenie matrycy", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BoardController.getInstance().init(Integer.parseInt(val[0]), Integer.parseInt(val[1]));
            makeEditorAccessible();
        });
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 270, 90);
        rotationSpinner.setModel(model);
    }

    public void createWindow() {
        edFrame = new JFrame();
        edFrame.setContentPane(this.contentPanel);
        edFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        edFrame.setTitle("WireWorld - edytor");
        edFrame.pack();
        edFrame.setLocationRelativeTo(null);
    }

    @Override
    public void showWindow() {
        edFrame.setVisible(true);
    }

    @Override
    public void hideWindow() {
        edFrame.setVisible(false);
    }

    @Override
    public JPanel getContentPanel() {
        return contentPanel;
    }

    @Override
    public void destroyWindow() {
        if(edFrame.isVisible() == true)
            edFrame.setVisible(false);
        edFrame.dispose();;
    }

    private void makeEditorAccessible()
    {
        editorRenderer.setBoard(BoardController.getInstance().getCurrBoard());
        gamePanel.setVisible(true);
        controlPanel.setVisible(true);
        edFrame.setSize(720, 480);
    }
}
