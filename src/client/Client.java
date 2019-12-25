package client;

import exceptions.InvalidPasswordException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;
import model.IModel;

import java.io.*;
import java.nio.file.Files;


public class Client {
    public static void main(String[] args) throws IOException {
        IModel model = new Stub("127.0.0.1", 12345);

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        // todo: falta checkar se têm o numero de argumentos necessários (como está no upload)
        String r, s;

        while (!(s = stdin.readLine()).equals("quit")) {
            String[] cmd = s.split(" ");
            try {
                switch (cmd[0]) {
                    case "adicionar":
                        r = model.addUser(cmd[1], cmd[2]);
                        break;

                    case "login":
                        r = model.login(cmd[1], cmd[2]);
                        break;

                    case "listar":
                        r = model.listUsers();
                        break;

                    case "logout":
                        r = model.logout(cmd[1]);
                        break;

                    case "upload":                                          //upload name artist year tag1/tag2/tag3 ... file
                                                                            //tem de ter pelo menos uma tag
                        String currentDirectory = System.getProperty("user.dir");
                        String path = currentDirectory + "\\" + cmd[1];
                        byte[] file = Files.readAllBytes(new File(path).toPath());
                        r = model.upload(cmd[1], cmd[2], cmd[3], cmd[4], file);
                        break;

                    default:
                        r = "Operação não reconhecida. Insira novamente";
                }
            } catch (InvalidPasswordException | UserNotFoundException | UsernameAlreadyExistsException e) {
                r = e.getMessage();
            } catch (ArrayIndexOutOfBoundsException e) {
                r = "Dados incorretos. Insira novamente.";
            }

            System.out.println(r);
        }

        model.end();
    }
}
