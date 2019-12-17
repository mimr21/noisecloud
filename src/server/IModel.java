package server;

import exceptions.InvalidPasswordException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;

public interface IModel {
    public String addUser(String name, String pass) throws UsernameAlreadyExistsException;
    public String login(String name, String pass) throws UserNotFoundException, InvalidPasswordException;
    public String logout(String name) throws UserNotFoundException;
    public String listUsers();
    public String upload(String name, String artist, String year, String tags, byte[] file);
}
