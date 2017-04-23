/**
 * Created by Konrad on 22.04.2017.
 */

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Surface extends JPanel implements ActionListener {
    ArrayList<Board> boards = new ArrayList<Board>();
    private final int DELAY = 750;
    private Timer timer;
    int counter = 0;

    public Surface() {
        initTimer();
    }

    public void setSurfaceBoard(ArrayList<Board> boards) {
        this.boards = boards;
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Timer getTimer() {

        return timer;
    }

    private void doDrawing(Graphics g) {
        Graphics2D g1 = (Graphics2D) g;
        int zeroX = 20;
        int zeroY = 20;
        Board currBoard = boards.get(counter);
        for (int i = 0; i < currBoard.rows; i++) {
            for (int j = 0; j < currBoard.columns; j++) {

                switch (currBoard.getCellState(i, j)) {
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
                g1.fillRect(zeroX, zeroY,
                        10,
                        10);
                zeroX += 11;
            }
            zeroY += 11;
            zeroX = 20;
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
        counter++;
        if (counter == boards.size() - 1) {
            timer.stop();

        }

    }

}