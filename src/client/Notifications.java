package client;

import common.EpicInputStream;
import common.EpicOutputStream;
import exceptions.RemoteModelException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Notifications {
    private final Socket socket;
    private final EpicInputStream in;
    private final EpicOutputStream out;


    public Notifications(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new EpicInputStream(new DataInputStream(socket.getInputStream()));
        out = new EpicOutputStream(new DataOutputStream(socket.getOutputStream()));
    }

    public void end() throws IOException {
        out.println("quit");
        out.flush();
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

    public String hear() throws RemoteModelException {
        String s = "";
        try {
            out.println("hear");
            out.flush();

            if (in.readLine().equals("something"))
                return s = in.readLine();
        } catch (IOException e) {
            throw new RemoteModelException(e.getMessage());
        }
        return s;
    }

}
