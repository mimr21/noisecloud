package server;

import exceptions.*;
import common.*;
import static common.Noisecloud.*;

import java.io.*;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.util.Collection;


// package-private
class ServerWorker implements Runnable {
    private final int id;
    private final Socket socket;
    private final IModel model;
    private final EpicInputStream in;
    private final EpicOutputStream out;


    public ServerWorker(int id, Socket socket, IModel model) throws IOException {
        this.id = id;
        this.socket = socket;
        this.model = model;
        this.in = new EpicInputStream(new DataInputStream(socket.getInputStream()));
        this.out = new EpicOutputStream(new DataOutputStream(socket.getOutputStream()));
    }

    public void run() {
        try {
            String request;

            while ((request = in.readLine()) != null && !request.equals("quit")) {
                try {
                    switch (request) {
                        case "addUser":
                            addUser();
                            break;
                        case "login":
                            login();
                            break;
                        case "logout":
                            logout();
                            break;
                        case "upload":
                            upload();
                            break;
                        case "download":
                            download();
                            break;
                        case "listUsers":
                            listUsers();
                            break;
                        case "listSongs":
                            listSongs();
                            break;
                        case "search":
                            search();
                            break;
                        default:
                            out.println(false);
                            out.println("Operação desconhecida.");
                    }
                } catch (RemoteModelException
                        | UserNotFoundException | SongNotFoundException | UsernameAlreadyExistsException | InvalidPasswordException
                        | NumberFormatException e) {
                    out.println(false);
                    out.println(e.getMessage());
                } catch (FileNotFoundException e) {
                    out.println(false);
                    out.println("'" + e.getMessage() + "' not found");
                } catch (FileAlreadyExistsException e) {
                    out.println(false);
                    out.println("'" + e.getMessage() + "' already exists");
                }
                out.flush();
            }
        } catch (IOException e) {
            printerr(e);
        } finally {
            try {
                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            } catch (IOException e) {
                printerr(e);
            }
            System.out.println("Client #" + id + ": saiu");
        }
    }

    private void printerr(Exception e) {
        System.out.println("Client #" + id + ": " + e.toString());
    }

    private void addUser() throws IOException, UsernameAlreadyExistsException, RemoteModelException {
        String username = in.readLine();
        String password = in.readLine();

        model.addUser(username, password);

        out.println(true);
    }

    private void login() throws IOException, UserNotFoundException, InvalidPasswordException, RemoteModelException {
        String username = in.readLine();
        String password = in.readLine();

        boolean login = model.login(username, password);

        out.println(true);
        out.println(login);
    }

    private void logout() throws IOException, UserNotFoundException, RemoteModelException {
        String username = in.readLine();

        boolean logout = model.logout(username);

        out.println(true);
        out.println(logout);
    }

    private void upload() throws IOException, RemoteModelException {
        String title = in.readLine();
        String artist = in.readLine();
        int year = in.readLineToInt();
        String[] tags = in.readStringArray();
        String filename = in.readLine();

        in.readFile(new File(storagePath(filename)));

        int id = model.upload(title, artist, year, tags, filename);

        out.println(true);
        out.println(id);
    }

    private void download() throws IOException, SongNotFoundException, RemoteModelException {
        int id = in.readLineToInt();

        Song song = model.download(id);

        out.println(true);
        out.print(song.cerealize());
        out.print(new File(storagePath(song.getFilename())));
    }

    private void listUsers() throws IOException, RemoteModelException {
        Collection<User> users = model.listUsers();

        out.println(true);
        out.println(users.size());
        for (User user : users)
            out.print(user.cerealize());
    }

    private void listSongs() throws IOException, RemoteModelException {
        Collection<Song> songs0 = model.listSongs();

        out.println(true);
        out.println(songs0.size());
        for (Song song1 : songs0)
            out.print(song1.cerealize());
    }

    private void search() throws IOException, RemoteModelException {
        String tag = in.readLine();

        Collection<Song> songs1 = model.search(tag);

        out.println(true);
        out.println(songs1.size());
        for (Song song2 : songs1)
            out.print(song2.cerealize());
    }
}
