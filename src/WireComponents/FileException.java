package WireComponents;

/**
 * Created by Konrad on 28.04.2017.
 */
public class FileException extends Exception//Custom exception fot all file operation errors encountered
{
    public FileException() {}

    public FileException(String message)
    {
        super(message);
    }
}