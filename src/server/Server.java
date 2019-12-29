package server;

import model.IModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("A iniciar...");
        ServerSocket sSock = new ServerSocket(12345);

        System.out.println("A carregar dados...");
        IModel model = new Model();

        int id = 0;
        System.out.println("À espera de clients...");
        while (true) {
            Socket clSock = sSock.accept();
            System.out.println("Client #" + ++id + " entrou");

            Runnable r = new ServerWorker(id, clSock, model);
            Thread t = new Thread(r);
            t.start();
        }
    }
}
