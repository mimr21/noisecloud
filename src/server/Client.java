package server;

import java.io.*;
import java.net.Socket;


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
                meta = s.split(" ");          //upload, nome(ou path) e outros dados (mais à frente)
                String currentDirectory = System.getProperty("user.dir");
                File f = new File(currentDirectory + "\\" + meta[1]);
                int c = 0;
                byte[] m = new byte[1024];
                while(c < f.length()){
                        //i need to converter um file em binário para mandar ao server kthxbai
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
