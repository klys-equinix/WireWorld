package WindowHandlers.BoardRenderer;

import Core.Settings.SettingsManager;
import WireComponents.Board;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Szymon on 23.04.2017.
 */
public class BoardRenderer extends JPanel {
    private Board board;
    private int zoom = 1;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D) g;
        g1.scale(zoom, zoom);
        for (int i = 0; i < board.rows; i++) {
            for (int j = 0; j < board.columns; j++) {
                switch (board.getCellState(i, j)) {
                    case 0:
                        g1.setColor(new Color(0, 0, 0));
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
                g1.fillRect(j, i, 1, 1);
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
        return new Dimension(board.columns * zoom, board.rows * zoom);
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

}
