package server;

import exceptions.InvalidPasswordException;
import exceptions.RemoteModelException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;
import model.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;


// package-private
class ServerWorker implements Runnable {
    private Socket socket;
    private IModel model;


    public ServerWorker(Socket socket, IModel model) {
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
                                out.println(user);
                            break;

                        case "upload":
                            String title = in.readLine();
                            String artist = in.readLine();
                            int year = Integer.parseInt(in.readLine());
                            String[] tags = in.readLine().split(Cerealizable.ARRAY_SEPARATOR);
                            String filename = in.readLine();

                            File file = new File(Noisecloud.storagePath(filename));
                            in.readFileTo(file);
                            int id = model.upload(title, artist, year, tags, filename);
                            out.println(true);
                            out.println(id);
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
        } catch (SocketException e) {
            // Client closed
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
