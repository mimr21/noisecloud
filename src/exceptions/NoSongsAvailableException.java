package exceptions;

public class NoSongsAvailableException extends Exception{
    public NoSongsAvailableException() {
        super();
    }

    public NoSongsAvailableException(String message) {
        super(message);
    }
}
