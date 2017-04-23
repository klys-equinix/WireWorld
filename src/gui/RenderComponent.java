package gui;

import gamelogic.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Created by Szymon on 23.04.2017.
 */
public class RenderComponent extends JPanel implements ActionListener {
    private Board board;

    public RenderComponent() {

    }

    private void doDrawing(Graphics g) {
        if (board == null) {
            Graphics2D g1 = (Graphics2D) g;
            Font font = g1.getFont();
            FontRenderContext fontContext = g1.getFontRenderContext();

            g1.setColor(Color.BLACK);
            g1.fillRect(0, 0, this.getWidth(), this.getHeight());
            g1.setColor(Color.WHITE);

            Rectangle2D stringBounds = font.getStringBounds("Loading.,..", fontContext);
            g1.drawString("Loading...", this.getWidth() / 2 - (int) stringBounds.getWidth() / 2, this.getHeight() / 2 - (int) stringBounds.getHeight() / 2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
