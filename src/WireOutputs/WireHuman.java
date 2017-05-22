package WireOutputs;

import WireComponents.Board;
import WireComponents.FileException;
import WireSimulator.BoardController;

import java.io.File;
import java.io.IOException;

/**
 * Created by Szymon on 22.05.2017.
 */
public class WireHuman implements WireOutput {
    @Override
    public void saveBoard(Board board, File file, int zoom) throws IOException {
        try {
            BoardController.getInstance().writeToUserFormat(file.getName());
        }catch(FileException ex){
            System.out.println("Cannot write to file");
        }
    }
}
