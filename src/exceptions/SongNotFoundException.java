package exceptions;

public class SongNotFoundException extends Exception {
    public SongNotFoundException() {
        super("Música não existe");
    }

    public SongNotFoundException(int id) {
        super("Música com id " + id + " não existe");
    }
}
