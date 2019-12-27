package server;

import exceptions.InvalidPasswordException;
import exceptions.RemoteModelException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;
import model.EpicInputStream;
import model.EpicOutputStream;
import model.IModel;
import model.User;

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
            String request, reply;

            while ((request = in.readLine()) != null && !request.equals("quit")) {

                /*Debug*/
                System.out.println("Client says: " + request);

                String[] cmd = request.split(" ");

                try {
                    switch (cmd[0]) {
                        case "addUser":
                            model.addUser(cmd[1], cmd[2]);
                            reply = "";
                            break;

                        case "login":
                            reply = model.login(cmd[1], cmd[2]) ? "" : "";
                            break;

                        case "logout":
                            reply = model.logout(cmd[1]) ? "" : "";
                            break;

                        case "users":
                            Collection<User> users = model.listUsers();
                            reply = users.isEmpty() ? "" : users.toString();
                            break;

                        case "upload":
                            int id = model.upload(cmd[1], cmd[2], Integer.parseInt(cmd[3]), cmd[4]/*, cmd[5]*/);
                            reply = "";
                            break;

                        default:
                            reply = "Operação desconhecida.";
                    }
                } catch (RemoteModelException | UsernameAlreadyExistsException
                        | UserNotFoundException | InvalidPasswordException | NumberFormatException e) {
                    reply = e.getMessage();
                }
                out.println(reply);
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
