package WindowHandlers.BoardRenderer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by Szymon on 15.05.2017.
 */
public class EditorRenderer extends BoardRenderer {
    int mouseCellX = -1;
    int mouseCellY = -1;

    public EditorRenderer() {
    }

    public void setMouseCell(int x, int y)
    {
        mouseCellX = x;
        mouseCellY = y;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D) g;
        g1.setColor(Color.blue);
        if(board == null)
            return;
        if(mouseCellX != -1 && mouseCellY != -1 && mouseCellX < board.columns && mouseCellY < board.rows)
            g1.fillRect(mouseCellX*zoom, mouseCellY*zoom, zoom, zoom);
    }
}
