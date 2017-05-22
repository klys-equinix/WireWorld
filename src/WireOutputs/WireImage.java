package WireOutputs;

import Core.Utils.Utils;
import WireComponents.Board;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Szymon on 22.05.2017.
 */
public class WireImage implements WireOutput {
    protected BufferedImage img;
    @Override
    public void saveBoard(Board board, File file, int zoom) throws IOException {
        img = new BufferedImage(board.columns*zoom, board.rows*zoom, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        Utils.drawBoard(board, g, zoom);
    }
}
