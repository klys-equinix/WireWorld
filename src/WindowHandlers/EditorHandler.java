package WindowHandlers;

import Core.WindowHandler;
import WindowHandlers.BoardRenderer.BoardRenderer;
import WireSimulator.BoardController;

import javax.swing.*;
import java.util.List;

/**
 * Created by Szymon on 11.05.2017.
 */
public class EditorHandler implements WindowHandler {
    private JPanel contentPanel;
    private JButton loadButton;
    private JButton saveButton;
    private JScrollPane componentPane;
    private JPanel gamePanel;
    private JFrame edFrame;

    private BoardRenderer gameRender;

    public void createWindow() {
        edFrame = new JFrame();
        edFrame.setContentPane(this.contentPanel);
        edFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        edFrame.pack();
        edFrame.setLocationRelativeTo(null);

        BoardController.getInstance().init(50, 50);
        BoardController.getInstance().nextGeneration();
        gameRender = new BoardRenderer();
        gameRender.setBoard(BoardController.getInstance().getCurrBoard());
        gamePanel.add(gameRender);
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
}
