package WireOutputs;

import WireComponents.Board;
import WireComponents.FileException;
import WireSimulator.BoardController;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Konrad on 22.05.2017.
 */
public class WireDefault implements WireOutput{
    @Override
    public void saveBoard(Board board, File file, int zoom) throws IOException {
        try {
            BoardController.getInstance().writeGenToFile(file.getAbsolutePath());
        }catch(FileException ex){
            throw new IOException("Cannot write to file");
        }
    }
}
