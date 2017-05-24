package WireOutputs;

import Core.Utils.Utils;
import WireComponents.Board;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <h1>WireImage</h1>
 * Output class used to prepare image to being saved
 *
 * @author Szymon "Aitwar" Chmal
 */
public class WireImage implements WireOutput {
    protected BufferedImage img;

    @Override
    public void saveBoard(Board board, File file, int zoom) throws IOException {
        img = new BufferedImage(board.getColumns() * zoom, board.getRows() * zoom, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        Utils.drawBoard(board, g, zoom);
    }
}
