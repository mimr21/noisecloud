package client;

import exceptions.RemoteModelException;
import model.*;
import static model.Noisecloud.*;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.TreeSet;


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

    public Collection<User> listUsers() throws RemoteModelException {
        try {
            out.println("listUsers");
            out.flush();

            if (in.readLineToBoolean()) {
                int size = in.readLineToInt();
                Collection<User> users = new TreeSet<>();
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

    public int upload(String title, String artist, int year, String[] tags, String filepath) throws RemoteModelException {
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

    public Collection<Song> search(String tag) throws RemoteModelException {
        try {
            out.println("search");
            out.println(tag);
            out.flush();

            if (in.readLineToBoolean()) {
                int size = in.readLineToInt();
                Collection<Song> songs = new TreeSet<>();
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
