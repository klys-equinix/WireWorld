package WireOutputs;

import WireComponents.Board;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by Szymon on 22.05.2017.
 */
public class WireJPG extends WireImage {
    @Override
    public void saveBoard(Board board, File file, int zoom) throws IOException {
        super.saveBoard(board, file, zoom);
        ImageIO.write(img, "jpg", file);
    }
}
