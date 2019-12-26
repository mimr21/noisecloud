package exceptions;


public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String username) {
        super("Utilizador '" + username + "' não existe.");
    }
}
