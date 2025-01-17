package server;

import common.IModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("A iniciar...");
        ServerSocket sSocket = new ServerSocket(12345);

        IModel model = new Data();
        DownloadQueue downQueue = new DownloadQueue();

        int id = 0;
        System.out.println("À espera de clients...");

        while (true) {
            Socket clSocket = sSocket.accept();

            try {
                Runnable r = new ServerWorker(id+1, clSocket, model, downQueue);
                Thread t = new Thread(r);
                t.start();

                System.out.println("Client #" + ++id + ": entrou");
            } catch (IOException e1) {
                try {
                    clSocket.shutdownOutput();
                    clSocket.shutdownInput();
                    clSocket.close();
                } catch (IOException e2) {}
            }
        }
    }
}
