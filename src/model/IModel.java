package model;

import exceptions.*;

import java.util.Collection;


public interface IModel {
    void end();

    void addUser(String username, String password) throws UsernameAlreadyExistsException, RemoteModelException;

    boolean login(String username, String password) throws UserNotFoundException, InvalidPasswordException, RemoteModelException;

    boolean logout(String username) throws UserNotFoundException, RemoteModelException;

    Collection<User> listUsers() throws RemoteModelException;

    int upload(String title, String artist, int year, String[] tags, String filename) throws RemoteModelException;
}
