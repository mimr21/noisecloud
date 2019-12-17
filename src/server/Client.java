package server;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;


public class Client {

    private String username;                                 //pode ser estúpido, para o logout

    public void startClient() throws  IOException{
        Socket socket = new Socket("127.0.0.1", 12345);
        BufferedReader in;
        PrintWriter out;

        this.username = "";

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String r, s;
        String [] meta;

        while(true){
            s = stdin.readLine();

            if( s.equals("upload")){
                meta = s.split(" ");                    //upload, nome do ficheiro , artista, ano, tag1, tag2 ...
                if(meta.length >= 5) {
                    String currentDirectory = System.getProperty("user.dir");
                    String path = currentDirectory + "\\" + meta[1];
                    byte[] file = Files.readAllBytes(new File(path).toPath());
                    out.println(meta[0] + " " + meta[1] + " " + meta[2] + " " + meta[3] + " " + meta[4] + " " + meta[5] + " " + file);
                }
                else {
                    System.out.println("Dados incorretos. Insira novamente.");
                }
            }

            if(s.equals("logout")){
                meta = s.split(" ");           //logout, username
                if(!meta[1].equals(this.username)){
                    //objetivo, se não der o username pedir para dar reenter
                }
            }

            out.println(s);
            out.flush();
            r = in.readLine();
            System.out.println(r);
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
