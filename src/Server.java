import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void startServer() throws IOException {
        ServerSocket sSock;
        BufferedReader in;
        PrintWriter out;

        sSock = new ServerSocket(12345);

        while (true) {

            Socket clSock = null;
            clSock = sSock.accept();

            in = new BufferedReader(new InputStreamReader(clSock.getInputStream()));

            out = new PrintWriter(clSock.getOutputStream());
            String s = new String();

            while (! ( (s = in.readLine()).equals("quit")) ){
                System.out.println("Client says: " + s);
                out.println(s);
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
