package server;

import exceptions.*;
import model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


// package-private
class Model implements IModel {
    private final LockableMap<String, User> users;        // (username, User)
    private final LockableMap<Integer, Song> songs;       // (id, Song)
    private final ID songID;                              // next song id
    private final ReentrantLock lockModel;


    public Model() {
        songID = new ID();
        users = new LockableHashMap<>();
        songs = new LockableHashMap<>();
        lockModel = new ReentrantLock(true);
    }

    public void end() {}

    public void addUser(String username, String password) throws UsernameAlreadyExistsException {
        lockModel.lock();
        users.lock();
        lockModel.unlock();

        try {
            if (users.containsKey(username))
                throw new UsernameAlreadyExistsException(username);

            users.put(username, new User(username, password));
        } finally {
            users.unlock();
        }
    }

    public boolean login(String username, String password) throws UserNotFoundException, InvalidPasswordException {
        lockModel.lock();
        users.lock();
        lockModel.unlock();

        if (!users.containsKey(username)) {
            users.unlock();
            throw new UserNotFoundException(username);
        }

        User u = users.get(username);
        u.lock();
        users.unlock();

        try {
            if (!u.isValid(password))
                throw new InvalidPasswordException();

            boolean r;
            if (u.getLog()) {
                r = false;           // se já tiver feito login
            } else {
                u.setLog(true);
                r = true;
            }

            return r;
        } finally {
            u.unlock();
        }
    }

    public boolean logout(String username) throws UserNotFoundException {
        lockModel.lock();
        users.lock();
        lockModel.unlock();

        if (!users.containsKey(username)) {
            users.unlock();
            throw new UserNotFoundException("Utilizador não existente.");
        }

        User u = users.get(username);
        u.lock();
        users.unlock();

        boolean r;
        if (u.getLog()) {
            u.setLog(false);
            r = true;
        } else {
            r = false;            // se já tiver feito logout
        }

        u.unlock();

        return r;
    }

    public Collection<User> listUsers() {
        lockModel.lock();
        users.lock();
        lockModel.unlock();

        try {
            return users.values();
        } finally {
            users.unlock();
        }
    }

    public int upload(String title, String artist, int year, String[] tags, String filename) {
        lockModel.lock();
        songs.lock();
        songID.lock();
        lockModel.unlock();

        int id = songID.getAndIncrement();
        songID.unlock();

        Song s = new Song(id, title, artist, year, tags, 0, filename);
        songs.put(id, s);

        songs.unlock();

        return id;
    }
}
