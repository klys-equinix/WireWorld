package gui.BoardRenderer;

import gamelogic.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Created by Szymon on 23.04.2017.
 */
public class BoardRenderer extends JPanel {
    private Board board;
    private int zoom = 1;

    private int height = 1500;
    private int width = 1500;

    public BoardRenderer() {

    }

    private void doDrawing(Graphics g) {
        Graphics2D g1 = (Graphics2D) g;
        g1.scale(zoom, zoom);
        if (board == null) {
            Font font = g1.getFont();
            FontRenderContext fontContext = g1.getFontRenderContext();

            g1.setColor(Color.BLACK);
            g1.fillRect(0, 0, 100, 100);
            //g1.setColor(Color.WHITE);

            Rectangle2D stringBounds = font.getStringBounds("Loading.,..", fontContext);
            g1.drawString("Loading...", width / 2 - (int) stringBounds.getWidth() / 2, height / 2 - (int) stringBounds.getHeight() / 2);
        }
        else {
            for (int i = 0; i < board.rows; i++) {
                for (int j = 0; j < board.columns; j++) {
                    switch (board.getCellState(i, j)) {
                        case 0:
                            g1.setColor(new Color(0, 0, 0));
                            break;
                        case 1:
                            g1.setColor(new Color(0, 0, 123));
                            break;
                        case 2:
                            g1.setColor(new Color(123, 0, 0));
                            break;
                        case 3:
                            g1.setColor(new Color(255, 255, 255));
                            break;

                    }
                    g1.fillRect(j, i, 1, 1);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width * zoom, height * zoom);
    }

    @Override
    public int getWidth() {
        return width * zoom;
    }

    @Override
    public int getHeight() {
        return height * zoom;
    }

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }

    public void setBoard(Board board)
    {
        this.board = board;
    }

}
