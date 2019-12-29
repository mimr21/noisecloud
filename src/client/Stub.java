package client;

import exceptions.RemoteModelException;
import model.*;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
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

            if (!Boolean.parseBoolean(in.readLine()))
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

            if (Boolean.parseBoolean(in.readLine()))
                return Boolean.parseBoolean(in.readLine());
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

            if (Boolean.parseBoolean(in.readLine()))
                return Boolean.parseBoolean(in.readLine());
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

            if (Boolean.parseBoolean(in.readLine())) {
                int size = Integer.parseInt(in.readLine());
                Collection<User> users = new TreeSet<>();
                while (size > 0) {
                    users.add(User.descerealize(in.readLine()));
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
            out.println(Cerealizable.cerealize(tags));
            out.println(Noisecloud.getFilename(filepath));
            out.sendFile(new File(filepath));
            out.flush();

            if (Boolean.parseBoolean(in.readLine()))
                return Integer.parseInt(in.readLine());
            else
                throw new RemoteModelException(in.readLine());
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
    }
}
