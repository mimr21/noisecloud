package exceptions;


public class UsernameAlreadyExistsException extends Exception {
    public UsernameAlreadyExistsException() {
        super("Nome de utilizador já existe");
    }

    public UsernameAlreadyExistsException(String username) {
        super("Nome de utilizador '" + username + "' já existe");
    }
}
