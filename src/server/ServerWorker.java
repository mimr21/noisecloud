package server;

import exceptions.InvalidPasswordException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;
import model.IModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;


// package-private
class ServerWorker implements Runnable {
    private Socket clSock;
    private IModel model;

    public ServerWorker(Socket s, IModel m) {
        clSock = s;
        model = m;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clSock.getInputStream()));
            PrintWriter out = new PrintWriter(clSock.getOutputStream());
            String s;

            while ((s = in.readLine()) != null && !s.equals("quit")) {

                /*Debug*/
                System.out.println("Client says: " + s);

                String[] cmd = s.split(" ");

                try {
                    switch (cmd[0]) {
                        case "addUser":
                            out.println(model.addUser(cmd[1], cmd[2]));
                            break;

                        case "login":
                            out.println(model.login(cmd[1], cmd[2]));
                            /*Debug*/
                            System.out.println(model.login(cmd[1], cmd[2]));
                            break;

                        case "logout":
                            out.println(model.logout(cmd[1]));
                            break;

                        case "users":
                            out.println(model.listUsers());
                            break;

                        case "upload":
                            //out.println(model.upload(cmd[1], cmd[2], cmd[3], cmd[4], cmd[5]);
                            break;

                        default:
                            out.println("Operação desconhecida.");
                    }
                } catch (UsernameAlreadyExistsException | UserNotFoundException | InvalidPasswordException e) {
                    e.printStackTrace();
                    out.println(e);
                }
                out.flush();
            }
        } catch (SocketException e) {
            // Client closed
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                clSock.shutdownOutput();
                clSock.shutdownInput();
                clSock.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
