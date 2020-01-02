package common;

import exceptions.*;

import java.io.IOException;
import java.util.Collection;


public interface IModel {
    void end() throws IOException;

    void addUser(String username, String password) throws UsernameAlreadyExistsException, RemoteModelException;

    boolean login(String username, String password) throws UserNotFoundException, InvalidPasswordException, RemoteModelException;

    boolean logout(String username) throws UserNotFoundException, RemoteModelException;

    int upload(String title, String artist, int year, String[] tags, String filename) throws RemoteModelException;

    Song download(int id) throws SongNotFoundException, RemoteModelException;

    Collection<User> listUsers() throws RemoteModelException;

    Collection<Song> listSongs() throws RemoteModelException;

    Collection<Song> search(String tag) throws RemoteModelException;
}
