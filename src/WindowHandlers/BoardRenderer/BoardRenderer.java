package WindowHandlers.BoardRenderer;

import Core.Settings.SettingsManager;
import WireComponents.Board;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Szymon on 23.04.2017.
 */
public class BoardRenderer extends JPanel {
    Board board;
    int zoom = 1;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D) g;

        if(board == null)
            return;

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                if(SettingsManager.getInstance().getGameDrawOutline()) {
                    g1.setColor(Color.white);
                    g1.drawRect(j * zoom - 1, i * zoom - 1, zoom + 1, zoom + 1);
                }
                switch (board.getCellState(i, j)) {
                    case 0:
                        g1.setColor(SettingsManager.getInstance().getGameBackgroundColor());
                        break;
                    case 1:
                        g1.setColor(SettingsManager.getInstance().getGameEleHeadColor());
                        break;
                    case 2:
                        g1.setColor(SettingsManager.getInstance().getGameEleTailColor());
                        break;
                    case 3:
                        g1.setColor(SettingsManager.getInstance().getGameCableColor());
                        break;

                }
                g1.fillRect(j*zoom, i*zoom, zoom, zoom);
            }
        }
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        if(board == null)
            return new Dimension(0, 0);

        return new Dimension(board.getColumns() * zoom, board.getRows() * zoom);
    }

    public void setBoard(Board board)
    {
        this.board = board;
        repaint();
    }
}
