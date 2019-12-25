package model;

import exceptions.InvalidPasswordException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;


public interface IModel {
    void end();

    String addUser(String name, String pass) throws UsernameAlreadyExistsException;

    String login(String name, String pass) throws UserNotFoundException, InvalidPasswordException;

    String logout(String name) throws UserNotFoundException;

    String listUsers();

    String upload(String name, String artist, String year, String tags, byte[] file);
}
