package exceptions;


public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("Utilizador não existe");
    }

    public UserNotFoundException(String username) {
        super("Utilizador '" + username + "' não existe");
    }
}
