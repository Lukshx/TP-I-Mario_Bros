package tp1.exceptions;
public class FileGameException extends GameModelException {
    private static final long serialVersionUID = 1L;
    public FileGameException(String message) { super(message); }
    public FileGameException(String message, Throwable cause) { super(message, cause); }
}
