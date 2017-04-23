import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
                // Time to open next window
                WireWorld.initGameWindow(textFileDir.getText(), (int) spinnerGenNum.getValue());
            }
        });
    }

    private void maintainGenButton() {
        if ((Integer) spinnerGenNum.getValue() != 0) {
            if (textFileDir.getText().isEmpty() == false) {
                if (generujButton.isEnabled() == false)
                    generujButton.setEnabled(true);
            }
        } else if (generujButton.isEnabled() == true)
            generujButton.setEnabled(false);
    }

    public JPanel getPanel() {
        return this.mainPanel;
    }
}
