package server;

import exceptions.*;
import model.*;
import static model.Noisecloud.*;

import java.io.*;
import java.net.Socket;
import java.util.Collection;


// package-private
class ServerWorker implements Runnable {
    private final int id;
    private final Socket socket;
    private final IModel model;


    public ServerWorker(int id, Socket socket, IModel model) {
        this.id = id;
        this.socket = socket;
        this.model = model;
    }

    public void run() {
        try {
            EpicInputStream in = new EpicInputStream(new DataInputStream(socket.getInputStream()));
            EpicOutputStream out = new EpicOutputStream(new DataOutputStream(socket.getOutputStream()));
            String request;

            while ((request = in.readLine()) != null && !request.equals("quit")) {
                try {
                    switch (request) {
                        case "addUser":
                            String username0 = in.readLine();
                            String password0 = in.readLine();
                            model.addUser(username0, password0);
                            out.println(true);
                            break;

                        case "login":
                            String username1 = in.readLine();
                            String password1 = in.readLine();
                            boolean login = model.login(username1, password1);
                            out.println(true);
                            out.println(login);
                            break;

                        case "logout":
                            String username2 = in.readLine();
                            boolean logout = model.logout(username2);
                            out.println(true);
                            out.println(logout);
                            break;

                        case "listUsers":
                            Collection<User> users = model.listUsers();
                            out.println(true);
                            out.println(users.size());
                            for (User user : users)
                                out.print(user.cerealize());
                            break;

                        case "upload":
                            String title = in.readLine();
                            String artist = in.readLine();
                            int year = in.readLineToInt();
                            String[] tags = in.readStringArray();
                            String filename = in.readLine();

                            File file = new File(storagePath(filename));
                            in.readFile(file);
                            int id = model.upload(title, artist, year, tags, filename);
                            out.println(true);
                            out.println(id);
                            break;

                        case "search":
                            String tag = in.readLine();
                            Collection<Song> songs = model.search(tag);
                            out.println(true);
                            out.println(songs.size());
                            for (Song song : songs)
                                out.print(song.cerealize());
                            break;

                        default:
                            out.println(false);
                            out.println("Operação desconhecida.");
                    }
                } catch (RemoteModelException | UsernameAlreadyExistsException
                        | UserNotFoundException | InvalidPasswordException | NumberFormatException e) {
                    out.println(false);
                    out.println(e.getMessage());
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
            System.out.println("Client #" + id + " saiu");
        }
    }

    public void printerr(Exception e) {
        System.out.println("Client #" + id + ": " + e.toString() + ": " + e.getMessage());
    }
}
