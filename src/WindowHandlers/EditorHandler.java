package WindowHandlers;

import Core.WindowHandler;
import WindowHandlers.BoardRenderer.BoardRenderer;

import javax.swing.*;
import java.util.List;

/**
 * Created by Szymon on 11.05.2017.
 */
public class EditorHandler implements WindowHandler {
    private JPanel contentPanel;
    private JList<BoardRenderer> elementList;
    private JButton loadButton;
    private JButton saveButton;
    private JFrame edFrame;

    public void createWindow() {
        edFrame = new JFrame();
        edFrame.setContentPane(this.contentPanel);
        edFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        edFrame.pack();
        edFrame.setLocationRelativeTo(null);

        BoardRenderer br = new BoardRenderer();
        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement(new BoardRenderer());
        elementList.setModel(listModel);
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

    private void createUIComponents() {
        elementList = new JList<BoardRenderer>();
    }
}
