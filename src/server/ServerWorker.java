package server;

import exceptions.InvalidPasswordException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;
import model.IModel;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;


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
            String received, send;

            while ((received = in.readLine()) != null && !received.equals("quit")) {

                /*Debug*/
                System.out.println("Client says: " + received);

                String[] cmd = received.split(" ");

                try {
                    switch (cmd[0]) {
                        case "addUser":
                            model.addUser(cmd[1], cmd[2]);
                            send = "Utilizador adicionado com sucesso.";
                            break;

                        case "login":
                            send = model.login(cmd[1], cmd[2]) ? "Login com sucesso." : "Login já realizado.";
                            break;

                        case "logout":
                            send = model.logout(cmd[1]) ? "Logout com sucesso." : "Logout já realizado.";
                            break;

                        case "users":
                            Collection<User> users = model.listUsers();
                            send = users.isEmpty() ? "Não existem utilizadores." : users.toString();
                            break;

                        case "upload":
                            int id = model.upload(cmd[1], cmd[2], Integer.parseInt(cmd[3]), cmd[4]/*, cmd[5]*/);
                            send = "Upload feito com sucesso. ID da música: " + id;
                            break;

                        default:
                            send = "Operação desconhecida.";
                    }
                } catch (UsernameAlreadyExistsException | UserNotFoundException
                        | InvalidPasswordException | NumberFormatException e) {
                    send = e.getMessage();
                }
                out.println(send);
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
