package common;

import exceptions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;


public interface IModel {
    void end() throws IOException;

    void addUser(String username, String password) throws UsernameAlreadyExistsException, RemoteModelException;

    boolean login(String username, String password) throws UserNotFoundException, InvalidPasswordException, RemoteModelException;

    boolean logout(String username) throws UserNotFoundException, RemoteModelException;

    int upload(String title, String artist, int year, String[] tags, String filename) throws FileNotFoundException, RemoteModelException;

    Song download(int id) throws SongNotFoundException, RemoteModelException;

    Collection<User> listUsers() throws RemoteModelException;

    Collection<Song> listSongs() throws RemoteModelException;

    Collection<Song> searchTitle(String title) throws RemoteModelException;

    Collection<Song> searchArtist(String artist) throws RemoteModelException;

    Collection<Song> searchTag(String tag) throws RemoteModelException;

    Collection<Song> mostDownloaded(int top) throws RemoteModelException;
}
