package WireComponents;

/**
 * <h1>FileException</h1>
 * Class for
 */
public class FileException extends Exception//Custom exception fot all file operation errors encountered
{
    public FileException() {
    }

    public FileException(String message) {
        super(message);
    }
}