package WireOutputs;

import WireComponents.Board;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * <h1>WireJPG</h1>
 * Output class used to generate JPG file
 *
 * @author Szymon "Aitwar" Chmal
 */
public class WireJPG extends WireImage {
    @Override
    public void saveBoard(Board board, File file, int zoom) throws IOException {
        super.saveBoard(board, file, zoom);
        ImageIO.write(img, "jpg", file);
    }
}
