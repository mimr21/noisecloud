package exceptions;


public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("Password incorreta");
    }
}
