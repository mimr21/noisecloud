package model;

import exceptions.*;

import java.util.Collection;


public interface IModel {
    void end();

    void addUser(String name, String pass) throws UsernameAlreadyExistsException, RemoteModelException;

    boolean login(String name, String pass) throws UserNotFoundException, InvalidPasswordException, RemoteModelException;

    boolean logout(String name) throws UserNotFoundException, RemoteModelException;

    Collection<User> listUsers() throws RemoteModelException;

    int upload(String title, String artist, int year, String tags) throws RemoteModelException;
}
