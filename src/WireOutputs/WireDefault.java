package WireOutputs;

import WireComponents.Board;
import WireComponents.FileException;
import WireSimulator.BoardController;

import java.io.File;
import java.io.IOException;

/**
 * Created by Konrad on 22.05.2017.
 */
public class WireDefault implements WireOutput{
    @Override
    public void saveBoard(Board board, File file, int zoom) throws IOException {
        try {
            BoardController.getInstance().writeGenToFile(file.getName());
        }catch(FileException ex){
            System.out.println("Cannot write to file");
        }
    }
}
