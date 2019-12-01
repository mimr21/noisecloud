import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {

    public void startClient() throws  IOException{
        Socket socket = new Socket("127.0.0.1", 12345);
        BufferedReader in;
        PrintWriter out;


        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String r = new String();
        String s = new String();

        while(true){
            s = stdin.readLine();
            out.println(s);
            out.flush();
            r = in.readLine();
            System.out.println("Server says: " + r);
            if(s.equals("quit"))
                break;
        }

        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

    public static void main(String[] args) {
        Client c = new Client();
        try {
            c.startClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
