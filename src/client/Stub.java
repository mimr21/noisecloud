package client;

import exceptions.RemoteModelException;
import common.*;
import static common.Noisecloud.downloadsPath;
import static common.Noisecloud.getFilename;

import java.io.*;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


// package-private
class Stub implements IModel {
    private final Socket socket;
    private final EpicInputStream in;
    private final EpicOutputStream out;


    public Stub(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new EpicInputStream(new DataInputStream(socket.getInputStream()));
        out = new EpicOutputStream(new DataOutputStream(socket.getOutputStream()));
    }

    public void end() throws IOException {
        out.println("quit");
        out.flush();
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

    public void addUser(String username, String password) throws RemoteModelException {
        try {
            out.println("addUser");
            out.println(username);
            out.println(password);
            out.flush();

            if (!in.readLineToBoolean())
                throw new RemoteModelException(in.readLine());
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }

    public boolean login(String username, String password) throws RemoteModelException {
        try {
            out.println("login");
            out.println(username);
            out.println(password);
            out.flush();

            if (in.readLineToBoolean())
                return in.readLineToBoolean();
            else
                throw new RemoteModelException(in.readLine());
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }

    public boolean logout(String username) throws RemoteModelException {
        try {
            out.println("logout");
            out.println(username);
            out.flush();

            if (in.readLineToBoolean())
                return in.readLineToBoolean();
            else
                throw new RemoteModelException(in.readLine());
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }

    public int upload(String title, String artist, int year, String[] tags, String filepath) throws FileNotFoundException, RemoteModelException {
        if (!(new File(filepath).isFile()))
            throw new FileNotFoundException(filepath);

        try {
            out.println("upload");
            out.println(title);
            out.println(artist);
            out.println(year);
            out.print(tags);
            out.println(getFilename(filepath));
            out.print(new File(filepath));
            out.flush();

            if (in.readLineToBoolean())
                return in.readLineToInt();
            else
                throw new RemoteModelException(in.readLine());
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }

    public Song download(int id) throws RemoteModelException {
        try {
            out.println("download");
            out.println(id);
            out.flush();

            if (in.readLineToBoolean()) {
                Song song = Song.descerealize(in.readStringArray());
                in.readFile(new File(downloadsPath(song.getFilename())));
                return song;
            } else {
                throw new RemoteModelException(in.readLine());
            }
        } catch (FileAlreadyExistsException e) {
            throw new RemoteModelException("'" + e.getMessage() + "' already exists");
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }

    public List<User> listUsers() throws RemoteModelException {
        try {
            out.println("listUsers");
            out.flush();

            if (in.readLineToBoolean()) {
                int size = in.readLineToInt();
                List<User> users = new ArrayList<>(size);
                while (size > 0) {
                    users.add(User.descerealize(in.readStringArray()));
                    --size;
                }
                return users;
            } else {
                throw new RemoteModelException(in.readLine());
            }
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }

    public List<Song> listSongs() throws RemoteModelException {
        try {
            out.println("listSongs");
            out.flush();

            if (in.readLineToBoolean()) {
                int size = in.readLineToInt();
                List<Song> songs = new ArrayList<>(size);
                while (size > 0) {
                    songs.add(Song.descerealize(in.readStringArray()));
                    --size;
                }
                return songs;
            } else {
                throw new RemoteModelException(in.readLine());
            }
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }

    public List<Song> searchTitle(String title) throws RemoteModelException {
        try {
            out.println("searchTitle");
            out.println(title);
            out.flush();

            if (in.readLineToBoolean()) {
                int size = in.readLineToInt();
                List<Song> songs = new ArrayList<>(size);
                while (size > 0) {
                    songs.add(Song.descerealize(in.readStringArray()));
                    --size;
                }
                return songs;
            } else {
                throw new RemoteModelException(in.readLine());
            }
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }

    public List<Song> searchArtist(String artist) throws RemoteModelException {
        try {
            out.println("searchArtist");
            out.println(artist);
            out.flush();

            if (in.readLineToBoolean()) {
                int size = in.readLineToInt();
                List<Song> songs = new ArrayList<>(size);
                while (size > 0) {
                    songs.add(Song.descerealize(in.readStringArray()));
                    --size;
                }
                return songs;
            } else {
                throw new RemoteModelException(in.readLine());
            }
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }

    public List<Song> searchTag(String tag) throws RemoteModelException {
        try {
            out.println("searchTag");
            out.println(tag);
            out.flush();

            if (in.readLineToBoolean()) {
                int size = in.readLineToInt();
                List<Song> songs = new ArrayList<>(size);
                while (size > 0) {
                    songs.add(Song.descerealize(in.readStringArray()));
                    --size;
                }
                return songs;
            } else {
                throw new RemoteModelException(in.readLine());
            }
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }

    public List<Song> mostDownloaded(int top) throws RemoteModelException {
        try {
            out.println("mostDownloaded");
            out.println(top);
            out.flush();

            if (in.readLineToBoolean()) {
                int size = in.readLineToInt();
                List<Song> songs = new ArrayList<>(size);
                while (size > 0) {
                    songs.add(Song.descerealize(in.readStringArray()));
                    --size;
                }
                return songs;
            } else {
                throw new RemoteModelException(in.readLine());
            }
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }
}
