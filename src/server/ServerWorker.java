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

            while (!((s = in.readLine()).equals("quit"))) {

                /*Debug*/
                System.out.println("Client says: " + s);

                String cmd[] = s.split(" ");

                switch (cmd[0]) {
                    case "addUser":
                        try {
                            out.println(model.addUser(cmd[1], cmd[2]));
                        } catch (UsernameAlreadyExistsException e) {
                            e.printStackTrace();
                            out.println(e);
                        }
                        break;

                    case "login":
                        try {
                            out.println(model.login(cmd[1], cmd[2]));
                            /*Debug*/
                            System.out.println(model.login(cmd[1], cmd[2]));
                        } catch (UserNotFoundException | InvalidPasswordException e) {
                            e.printStackTrace();
                            out.println(e);
                        }
                        break;

                    case "logout":
                        try {
                            out.println(model.logout(cmd[1]));
                        } catch (UserNotFoundException e) {
                            e.printStackTrace();
                            out.println(e);
                        }
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
                out.flush();
            }
        } catch (SocketException se) {
            // Client closed
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            try {
                clSock.shutdownOutput();
                clSock.shutdownInput();
                clSock.close();
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            }
        }
    }
}
