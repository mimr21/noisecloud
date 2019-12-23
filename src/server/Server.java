package server;

import model.IModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket sSock = new ServerSocket(12345);

        IModel model = new Model();

        while (true) {
            Socket clSock = sSock.accept();

            Runnable sw = new ServerWorker(clSock, model);
            Thread t = new Thread(sw);
            t.start();
        }
    }
}
