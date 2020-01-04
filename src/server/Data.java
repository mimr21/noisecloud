package server;

import exceptions.*;
import common.*;

import java.util.*;


// package-private
class Data implements IModel {
    private final LockableMap<String, User> users;        // (username, User)
    private final LockableMap<Integer, Song> songs;       // (id, Song)
    private final ID songID;                              // next song id


    public Data() {
        songID = new ID();
        users = new LockableHashMap<>();
        songs = new LockableHashMap<>();
    }

    public void end() {}

    public void addUser(String username, String password) throws UsernameAlreadyExistsException {
        users.lock();

        try {
            if (users.containsKey(username))
                throw new UsernameAlreadyExistsException(username);

            users.put(username, new User(username, password));
        } finally {
            users.unlock();
        }
    }

    public boolean login(String username, String password) throws UserNotFoundException, InvalidPasswordException {
        users.lock();

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
        users.lock();

        if (!users.containsKey(username)) {
            users.unlock();
            throw new UserNotFoundException(username);
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

    public int upload(String title, String artist, int year, String[] tags, String filename) {
        songs.lock();
        songID.lock();

        int id = songID.getAndIncrement();
        songID.unlock();

        Song s = new Song(id, title, artist, year, tags, 0, filename);
        songs.put(id, s);

        songs.unlock();

        return id;
    }

    public Song download(int id) throws SongNotFoundException {
        songs.lock();

        if (!songs.containsKey(id)) {
            songs.unlock();
            throw new SongNotFoundException(id);
        }

        Song song = songs.get(id);
        song.lock();

        songs.unlock();

        song.incrementDownloads();

        try {
            return song.clone();
        } finally {
            song.unlock();
        }
    }

    public Set<User> listUsers() {
        users.lock();

        Collection<User> users_aux = users.values();

        for (User user : users_aux)
            user.lock();

        users.unlock();

        SortedSet<User> r = new TreeSet<>();

        for (User user : users_aux) {
            r.add(user.clone());
            user.unlock();
        }

        return r;
    }

    public Set<Song> listSongs() {
        songs.lock();

        Collection<Song> songs_aux = songs.values();

        for (Song song : songs_aux)
            song.lock();

        songs.unlock();

        SortedSet<Song> r = new TreeSet<>();

        for (Song song : songs_aux) {
            r.add(song.clone());
            song.unlock();
        }

        return r;
    }

    public Set<Song> searchTitle(String title) {
        songs.lock();

        Collection<Song> songs_aux = songs.values();

        for (Song song : songs_aux)
            song.lock();

        songs.unlock();

        SortedSet<Song> match = new TreeSet<>();

        for (Song song : songs_aux) {
            if (song.containsTitle(title))
                match.add(song.clone());
            song.unlock();
        }

        return match;
    }

    public Set<Song> searchArtist(String artist) {
        songs.lock();

        Collection<Song> songs_aux = songs.values();

        for (Song song : songs_aux)
            song.lock();

        songs.unlock();

        SortedSet<Song> match = new TreeSet<>();

        for (Song song : songs_aux) {
            if (song.containsArtist(artist))
                match.add(song.clone());
            song.unlock();
        }

        return match;
    }

    public Set<Song> searchTag(String tag) {
        songs.lock();

        Collection<Song> songs_aux = songs.values();

        for (Song song : songs_aux)
            song.lock();

        songs.unlock();

        SortedSet<Song> match = new TreeSet<>();

        for (Song song : songs_aux) {
            if (song.containsTag(tag))
                match.add(song.clone());
            song.unlock();
        }

        return match;
    }

    public List<Song> mostDownloaded(int top) {
        songs.lock();

        Collection<Song> songs_aux = songs.values();

        for (Song song : songs_aux)
            song.lock();

        songs.unlock();

        System.out.println(songs_aux);

        // ordenação
        SortedSet<Song> sorted_songs = new TreeSet<>(Song.topDownloadsComparator);

        for (Song song : songs_aux) {
            sorted_songs.add(song.clone());
            song.unlock();
        }

        System.out.println(sorted_songs);

        // top
        List<Song> r = new ArrayList<>();

        Iterator<Song> itr = sorted_songs.iterator();

        for (int i = 0; itr.hasNext() && i < top; ++i)
            r.add(itr.next());

        System.out.println(r);

        return r;
    }
}
