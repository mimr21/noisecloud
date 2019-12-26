package exceptions;


public class UsernameAlreadyExistsException extends Exception {
    public UsernameAlreadyExistsException() {
        super();
    }

    public UsernameAlreadyExistsException(String username) {
        super("Nome de utilizador '" + username + "' já existe.");
    }
}
