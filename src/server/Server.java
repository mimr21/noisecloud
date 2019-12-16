package server;

import exceptions.InvalidPasswordException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private void startServer() throws IOException{
        ServerSocket sSock;
        BufferedReader in;
        PrintWriter out;

        Model model = new Model();

        sSock = new ServerSocket(12345);

        while (true) {

            Socket clSock = sSock.accept();

            in = new BufferedReader(new InputStreamReader(clSock.getInputStream()));

            out = new PrintWriter(clSock.getOutputStream());
            String s;

            while (! ((s = in.readLine()).equals("quit")) ) {

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
                    case "users":
                        out.println(model.listUsers());
                        break;
                    case "upload":
                        out.println(model.upload(cmd[1], cmd[2], cmd[3], cmd[4], cmd[5]));
                        break;
                    default:
                        out.println("Operação desconhecida.");
                }
                out.flush();
            }
            clSock.shutdownOutput();
            clSock.shutdownInput();
            clSock.close();
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        try {
            s.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
