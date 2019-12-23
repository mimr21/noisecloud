package client;

import model.IModel;

import java.io.*;
import java.nio.file.Files;


public class Client {
    public void startClient() throws  IOException{
        Stub stub = new Stub("127.0.0.1", 12345);

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        // todo: falta checkar se têm o numero de argumentos necessários (como está no upload)
        String r, s;
        while(true){
            s = stdin.readLine();
            String[] cmd = s.split(" ");
            switch (cmd[0]) {
                case "adicionar":
                    if(cmd.length == 3)
                        r = stub.addUser(cmd[1], cmd[2]);
                    else
                        r = "Dados incorretos. Insira novamente.";
                    break;
                case "login":
                    if(cmd.length == 3)
                        r = stub.login(cmd[1], cmd[2]);
                    else
                        r = "Dados incorretos. Insira novamente.";
                    break;
                case "listar":
                    if(cmd.length == 1)
                        r = stub.listUsers();
                    else
                        r = "Dados incorretos. Insira novamente.";
                    break;
                case "logout":
                    if(cmd.length == 2)
                        r = stub.logout(cmd[1]);
                    else
                        r = "Dados incorretos. Insira novamente.";
                    break;
                case "upload":                                          //upload name artist year tag1/tag2/tag3 ... file
                    if(cmd.length == 6) {                              //tem de ter pelo menos uma tag
                        String currentDirectory = System.getProperty("user.dir");
                        String path = currentDirectory + "\\" + cmd[1];
                        byte[] file = Files.readAllBytes(new File(path).toPath());
                        r = stub.upload( cmd[1], cmd[2] , cmd[3] , cmd[4], file);
                    }
                    else
                        r = "Dados incorretos. Insira novamente.";
                    break;
                default: r = "Operação não reconhecida. Insira novamente";
            }

            System.out.println(r);
            if(s.equals("quit")) {
                stub.end();
                break;
            }
        }
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
