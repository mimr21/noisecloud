package model;

import exceptions.*;

import java.util.Collection;


public interface IModel {
    void end();

    void addUser(String name, String pass) throws UsernameAlreadyExistsException;

    boolean login(String name, String pass) throws UserNotFoundException, InvalidPasswordException;

    boolean logout(String name) throws UserNotFoundException;

    Collection<User> listUsers();

    int upload(String name, String artist, int year, String tags);
}
