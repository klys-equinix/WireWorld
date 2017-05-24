package WindowHandlers.BoardRenderer;

import Core.Utils.Utils;
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

        if (board == null)
            return;

        Utils.drawBoard(board, g1, zoom);
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        if (board == null)
            return new Dimension(0, 0);

        return new Dimension(board.getColumns() * zoom, board.getRows() * zoom);
    }

    public void setBoard(Board board) {
        this.board = board;
        repaint();
    }
}
